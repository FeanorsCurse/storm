import flash.Error;
import flash.media.Sound;
import flash.events.Event;
import flash.net.URLRequest;
import flash.media.SoundChannel;
import flash.events.IOErrorEvent;
import flash.media.SoundTransform;
import flash.events.ProgressEvent;
import flash.events.SampleDataEvent;
import flash.media.SoundLoaderContext;
import flash.events.SecurityErrorEvent;

import org.xiph.system.VSound;

// OVSound class to extend VSound to do some event handling and
// extraction of progress information

class OVSound extends VSound
{
    // Create a new OVSound

    public function new(?stream : URLRequest,
                        ?context : SoundLoaderContext)
    {
	super(stream, context);
    }

    override public function play(?startTime : Float, ?loops : Int,
				  ?sndTransform : SoundTransform) : SoundChannel
    {
	// No request

        if (_req == null)
            throw new Error("Not Loading");

	// Paused, restart

        if (_decoding)
	{
	    if (_data_min)
		_sch = _s.play();

            return _sch;
	}

	// First time, or play it again, Sam

        _decoding = true;
        _need_samples = true;

        _s = new Sound();
        _sch = null;
        _s.addEventListener(SampleDataEvent.SAMPLE_DATA, _data_cb);

        haxe.Timer.delay(_decode, 0);

        if (_data_min)
            _sch = _s.play();
 
        return _sch;
    }

    // Override _on_progress function to get bytesLoaded and
    // bytesTotal not available from URLStream, and to dispatch event
    // to player

    override function _on_progress(e : ProgressEvent) : Void
    {
	super._on_progress(e);

	bytesLoaded = e.bytesLoaded;
	bytesTotal = e.bytesTotal;

	if (hasEventListener(ProgressEvent.PROGRESS))
	    dispatchEvent(e);
    }

    // Override _on_complete function to dispatch event to player

    override function _on_complete(e : Event) : Void
    {
	super._on_complete(e);

	if (hasEventListener(Event.COMPLETE))
	    dispatchEvent(e);
    }

    // Override _on_error function to dispatch event to player

    override function _on_error(e : IOErrorEvent) : Void
    {
	super._on_error(e);

        if (hasEventListener(IOErrorEvent.IO_ERROR))
	    dispatchEvent(e);
    }

    // Override _on_security function to dispatch event to player

    override function _on_security(e : SecurityErrorEvent) : Void
    {
	super._on_security(e);

        if (hasEventListener(SecurityErrorEvent.SECURITY_ERROR))
	    dispatchEvent(e);
    }

    // Override function _on_over_min to dispatch an event

    override function _on_over_min()
    {
	super._on_over_min();

	// Set an event listener to get the complete event

	if (_sch != null)
	    _sch.addEventListener(Event.SOUND_COMPLETE, _on_sound_complete);

	// Dispatch the open event

	if (hasEventListener(Event.OPEN))
	    dispatchEvent(new OpenEvent(_sch));
    }

    // Sound complete function to get the length

    function _on_sound_complete(e : Event)
    {
	length = _sch.position;
    }
}
