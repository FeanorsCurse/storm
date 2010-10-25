package org.xiph.system;

import org.xiph.system.Bytes;

import flash.Vector;

import flash.events.EventDispatcher;
import flash.media.Sound;
import flash.media.SoundChannel;
import flash.media.SoundLoaderContext;
import flash.media.SoundTransform;
import flash.media.ID3Info;

import flash.events.SampleDataEvent;

import flash.net.URLRequest;
import flash.net.URLStream;
import flash.utils.IDataInput;

import org.xiph.foggy.Demuxer;

import org.xiph.fogg.Packet;

import org.xiph.fvorbis.Info;
import org.xiph.fvorbis.Comment;
import org.xiph.fvorbis.DspState;
import org.xiph.fvorbis.Block;


class TheRightWayDemuxer extends Demuxer {
    public var eos(default, null) : Bool;

    public function new() {
        eos = false;
        super();
    }

    override public function read(data : IDataInput, len : Int,
                                  pos : Int = -1) : Int {
        //@ trace('TRWD::read(' + len + ')');
        var buffer : Bytes;
        var index : Int = oy.buffer(len);
        buffer = oy.data;

        // ignore pos, read from the data's current position
        data.readBytes(buffer, index, len);
        oy.wrote(len);

        if (len == 0)
            eos = true;

        return len;
    }

    public function process(pages : Int) : Int {
        var processed : Int = 0;
        var ret : Int;
        var buffer : Bytes = oy.data;

        if (buffer == null)
            return processed;

        while (processed < pages) {
            if ((ret = oy.pageout(og)) != 1) {
                if (ret == 0 && processed == 0 && eos) {
                    return Demuxer.EOF;
                } else if (buffer.length < 16384 || ret == 0) {
                    return processed;
                } else {
                    return Demuxer.ENOTOGG;
                }
            }

            _process_page(og);
            // TODO: check for returns from _process_page()
            processed += 1;
        }

        return processed;
    }
}

class VSoundDecoder {
    var _packets : Int;
    var vi : Info;
    var vc : Comment;
    var vd : DspState;
    var vb : Block;

    var _pcm : Array<Array<Vector<Float>>>;
    var _index : Vector<Int>;

    public var dmx(default, null) : TheRightWayDemuxer;

    public var decoded_cb(null, default) :
        Array<Vector<Float>> -> Vector<Int> -> Int -> Void;

    function _proc_packet_head(p : Packet, sn : Int) : DemuxerStatus {
        vi.init();
        vc.init();
        if (vi.synthesis_headerin(vc, p) < 0) {
            // not vorbis - clean up and ignore
            vc.clear();
            vi.clear();
        } else {
            // vorbis - detach this cb and attach the main decoding cb
            // to the specific serialno
            dmx.remove_packet_cb(-1);
            dmx.set_packet_cb(sn, _proc_packet);
        }

        _packets++;
        return dmx_ok;
    }

    function _proc_packet(p : Packet, sn : Int) : DemuxerStatus {
        var samples : Int;

        switch(_packets) {
        case 0:
            /*
            vi.init();
            vc.init();
            if (vi.synthesis_headerin(vc, p) < 0) {
                return dmx_ok;
            } else {
                dmx.set_packet_cb(sn, _proc_packet);
                dmx.remove_packet_cb(-1);
            }
            */
        case 1:
            vi.synthesis_headerin(vc, p);

        case 2:
            vi.synthesis_headerin(vc, p);

            {
                var ptr : Array<Bytes> = vc.user_comments;
                var j : Int = 0;
                // trace("");
                while (j < ptr.length) {
                    if (ptr[j] == null) {
                        break;
                    };
                    // trace(System.fromBytes(ptr[j], 0, ptr[j].length - 1));
                    j++;
                };

//                 trace("Bitstream is " + vi.channels + " channel, " +
//                       vi.rate + "Hz");
//                 trace(("Encoded by: " +
//                        System.fromBytes(vc.vendor, 0, vc.vendor.length - 1)) +
//                       "\n");
            }

            vd.synthesis_init(vi);
            vb.init(vd);

            _pcm = [null];
            _index = new Vector(vi.channels, true);

        default:
            if (vb.synthesis(p) == 0) {
                vd.synthesis_blockin(vb);
            }

            while ((samples = vd.synthesis_pcmout(_pcm, _index)) > 0) {
                //asink.write(_pcm[0], _index, samples);
                if (decoded_cb != null)
                    decoded_cb(_pcm[0], _index, samples);
                vd.synthesis_read(samples);
            }
        }

        _packets++;

        return dmx_ok;
    }

    public function new() {
        // ???
        dmx = new TheRightWayDemuxer();

        vi = new Info();
        vc = new Comment();
        vd = new DspState();
        vb = new Block(vd);

        _packets = 0;

        dmx.set_packet_cb(-1, _proc_packet_head);
        // ...
    }
}

class ADQueue {
    public var over_min_cb(null, default) : Void -> Void;
    public var over_max_cb(null, default) : Void -> Void;
    public var under_max_cb(null, default) : Void -> Void;

    public var min(get_min, set_min) : Int;
    public var max(get_max, set_max) : Int;

    var _min : Int;
    var _max : Int;

    /* data ready to be written to a Sound object:
       2 ch * 4 bytes (float) per sample */
    /* TODO: make it a ring buffer */
    var _buf : Bytes;
    var _read : Int;
    public var _samples : Int;

    public function new(min : Int = 44100, ?max : Int) {
        _min = min;
        if (max == null)
            max = 2 * _min;
        _max = max;

        _buf = new Bytes();
        _read = 0;
        _samples = 0;
    }

    function get_min() : Int {
        return _min;
    }

    function get_max() : Int {
        return _max;
    }

    function set_min(v : Int) : Int {
        if (v == _min)
            return _min;

        var old = _min;
        _min = v;

        if (_min < old) {
            if (over_min_cb != null && _samples < old && _samples >= _min) {
                //@ trace('TRIGGERED: over_min_cb');
                //over_min_cb();
                haxe.Timer.delay(over_min_cb, 0);
            }
        }
        /* no need, we don't notify about that case...
        else if (_min > old)
            // ...
        */

        return _min;
    }

    function set_max(v : Int) : Int {
        if (v == _max)
            return _max;

        var old = _max;
        _max = v;

        if (_max < old) {
            if (over_max_cb != null && _samples < old && _samples >= _max) {
                //@ trace('TRIGGERED: over_max_cb');
                over_max_cb();
            }
        } else if (_max > old) {
            if (under_max_cb != null && _samples >= old && _samples < _max) {
                //@ trace('TRIGGERED: under_max_cb');
                under_max_cb();
            }
        }

        return _max;
    }

    public function write(pcm : Array<Vector<Float>>, index : Vector<Int>,
                          samples : Int) : Void
    {
        //trace('write: ' + samples + ', (' + _samples + ', ' + _read + ')');

        var i : Int;
        var end : Int;

        _buf.position = _samples * 8;
        if (pcm.length == 1) {
            // single channel source data
            var c = pcm[0];
            var s : Float;
            i = index[0];
            end = i + samples;
            while (i < end) {
                s = c[i++];
                _buf.writeFloat(s);
                _buf.writeFloat(s);
            }
        } else if (pcm.length == 2) {
            // two channels
            var c1 = pcm[0];
            var c2 = pcm[1];
            i = index[0];
            var i2 = index[1];
            end = i + samples;
            while (i < end) {
                _buf.writeFloat(c1[i]);
                _buf.writeFloat(c2[i2++]);
                i++;
            }
        } else {
            throw "-EWRONGNUMCHANNELS";
        }

        var old_samples : Int = _samples;
        _samples += samples;

        if (over_max_cb != null && old_samples < _max && _samples >= _max) {
            //@ trace('TRIGGERED: over_max_cb');
            over_max_cb();
        }

        if (over_min_cb != null && old_samples < _min && _samples >= _min) {
            //@ trace('TRIGGERED: over_min_cb');
            //over_min_cb();
            haxe.Timer.delay(over_min_cb, 0);
        }
    }

    public function read(dst : Bytes, samples : Int) : Bool {
        //trace('read: ' + samples + ', (' + _samples + ', ' +
        //      _read + ')');

        var avail : Int = _samples - _read;

        if (avail < samples)
            return false;

        dst.writeBytes(_buf, _read * 8, samples * 8);
        _read += samples;

        haxe.Timer.delay(_sync, 0);

        return true;
    }

    function _sync() : Void {
        if (_read == 0)
            return;

        //@ trace('_sync: (' + _samples + ', ' + _read + ')');

        var new_samples : Int = _samples - _read;
        if (new_samples != 0)
            System.bytescopy(_buf, _read * 8, _buf, 0, new_samples * 8);
        _read = 0;
        var old_samples : Int = _samples;
        _samples = new_samples;

        if (under_max_cb != null && old_samples >= _max && new_samples < _max) {
            //@ trace('TRIGGERED: under_max_cb');
            under_max_cb();
        }

        // we don't notify if the level falls under the set minimum
        if (old_samples >= _min && new_samples < _min) {
            // trace('UNDER MIN');
        }
    }
}


class VSound extends flash.events.EventDispatcher {
    public var bytesLoaded(default,null) : UInt;
    public var bytesTotal(default,null) : Int;
    public var id3(default,null) : ID3Info;
    public var isBuffering(default,null) : Bool;
    public var length(default,null) : Float;
    public var url(default,null) : String;

    var _slc : SoundLoaderContext;
    var _req : URLRequest;
    var _ul : URLStream;

    var _aq : ADQueue; // audio data queue
    var _dec : VSoundDecoder;

    var _decoding : Bool;
    var _need_data : Bool;
    var _need_samples : Bool;
    var _data_min : Bool;
    var _data_complete : Bool;
    var _read_pending : Bool;

    var _s : Sound;
    var _sch : SoundChannel;

    var _c1 : Int;

    public static inline var SAMPLERATE : Int = 44100;
    public static inline var DATA_CHUNK_SIZE : Int = 16384;

    public function new(?stream : URLRequest,
                        ?context : SoundLoaderContext) {
        super();

        _aq = null;
        _dec = null;

        _need_data = false;
        _need_samples = false;
        _data_min = false; // FIXME: should be _samples_min !!
        _data_complete = false;
        _read_pending = false;
        _decoding = false;

        _c1 = 0;

        _ul = new URLStream();

        _ul.addEventListener(flash.events.Event.OPEN, _on_open);
        _ul.addEventListener(flash.events.ProgressEvent.PROGRESS, _on_progress);
        _ul.addEventListener(flash.events.Event.COMPLETE, _on_complete);
        _ul.addEventListener(flash.events.IOErrorEvent.IO_ERROR, _on_error);
        _ul.addEventListener(flash.events.SecurityErrorEvent.SECURITY_ERROR,
                             _on_security);


        if (stream != null) {
            load(stream, context);
        } else {
            _slc = context;
            if (_slc == null)
                _slc = new SoundLoaderContext();
        }
    }

    public function close() : Void {
    }

    public function load(stream : URLRequest,
                         ?context : SoundLoaderContext) : Void {
        if (_req != null)
            return;

        if (context != null)
            _slc = context;
        else if (_slc == null)
            _slc = new SoundLoaderContext();
        _req = stream;

        _aq = new ADQueue(Std.int(_slc.bufferTime * SAMPLERATE / 1000));
        _dec = new VSoundDecoder();

        _aq.over_min_cb = _on_over_min;
        _aq.over_max_cb = _on_over_max;
        _aq.under_max_cb = _on_under_max;

        _dec.decoded_cb = _on_decoded;

        _ul.load(_req);
    }

    public function play(?startTime : Float, ?loops : Int,
                         ?sndTransform : SoundTransform) : SoundChannel {
        if (_req == null) {
            throw "-ENOTLOADING";
        }

        if (_decoding)
            return _sch;

        //@ trace('play: ' + _decoding + ', (' + _ul.bytesAvailable + ')');
        if (_decoding)
            return _sch;

        _decoding = true;
        _need_samples = true;

        _s = new Sound();
        _sch = null;
        _s.addEventListener("sampleData", _data_cb);

        haxe.Timer.delay(_decode, 0);

        if (_data_min) {
            _sch = _s.play();
        }

        return _sch;
    }

    function _q_min_filled() : Void {
        // trace("_q_min_filled");
    }

    public function extract(?target : Bytes, ?length : Float,
                            ?startPosition : Float) : Float {
        return 0.0;
    }


    // private data / sample handling methods

    function _try_write_data() : Void {
        _read_pending = false;

        if (! _need_data)
            return;

        //trace('_try_write_data: ' + [_need_data, _ul.bytesAvailable,
        //                             _data_complete, _need_samples]);

        var to_read : Int = _ul.bytesAvailable;
        if (to_read >= DATA_CHUNK_SIZE) {
            to_read = DATA_CHUNK_SIZE;
        } else if (_data_complete) {
            if (_dec.dmx.eos) {
                _need_data = false;
                return;
            }
            // pass
        } else {
            // we could reshedule read here, but if we don't have
            // enough data and we're still downloading then
            // on_progress should call us again... right?
            return;
        }

        _need_data = false;

        _dec.dmx.read(_ul, to_read);

        //if (_data_complete)
        //    _dec.dmx.read(_ul, 0);

        if (_need_samples)
            haxe.Timer.delay(_decode, 0);
    }

    function _decode() : Void {
        var result : Int = 0;

        while(_need_samples && (result = _dec.dmx.process(1)) == 1) {
            // pass
        }

        if (result == Demuxer.EOF) {
            // pass
        } else if (result == 0) {
            _need_data = true;
            if (!_read_pending) {
                _read_pending = true;
                haxe.Timer.delay(_try_write_data, 0);
            }
        }
    }

    // URLStream callbacks

    function _on_open(e : flash.events.Event) : Void {
        // trace('_on_open');
    }

    function _on_progress(e : flash.events.ProgressEvent) : Void {
        // trace('_on_progress: ' + _ul.bytesAvailable);
        _try_write_data();
    }

    function _on_complete(e : flash.events.Event) : Void {
        // trace('_on_complete: ' + _ul.bytesAvailable);
        _data_complete = true;
        _try_write_data();
    }

    function _on_error(e : flash.events.IOErrorEvent) : Void {
        // trace("error occured: " + e);
    }

    function _on_security(e : flash.events.SecurityErrorEvent) : Void {
        // trace("security error: " + e);
    }


    // ADQueue callbacks

    function _on_over_min() : Void {
        // trace('_on_over_min');
        _data_min = true;
        if (_decoding && _sch == null) {
            _sch = _s.play(); //??
        }
    }

    function _on_over_max() : Void {
        //@ trace('_on_over_max');
        _need_samples = false;
    }

    function _on_under_max() : Void {
        //@ trace('_on_under_max');
        _need_samples = true;
        //_decode();
        haxe.Timer.delay(_decode, 0);
    }


    // VSoundDecoder callback

    function _on_decoded(pcm : Array<Vector<Float>>, index : Vector<Int>,
                         samples : Int) : Void
    {
        _aq.write(pcm, index, samples);
    }


    // Sound data callback

    function _data_cb(event : SampleDataEvent) : Void {
        var avail : Int = _aq._samples;
        var to_write = avail > 8192 ? 8192 : avail; // FIXME: unhardcode!

        if (to_write > 0) {
            _aq.read(event.data, to_write);
            _c1 += to_write;
            //@ trace('_data_cb: ' + [avail, _c1]);
        } else {
            // trace('_data_cb: UNDERRUN');
        }
    }
}
