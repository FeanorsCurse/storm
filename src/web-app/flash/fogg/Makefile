HAXE = haxe
SWFMILL = swfmill
HX_FILES = 				\
	org/xiph/fogg/Buffer.hx		\
	org/xiph/fogg/SyncState.hx	\
	org/xiph/fogg/Packet.hx		\
	org/xiph/fogg/StreamState.hx	\
	org/xiph/fogg/Page.hx		\
	org/xiph/foggy/Demuxer.hx	\
	org/xiph/system/Bytes.hx	\
	org/xiph/system/AudioSink.hx	\
	org/xiph/system/VSound.hx	\
	org/xiph/fvorbis/Block.hx	\
	org/xiph/fvorbis/CodeBook.hx	\
	org/xiph/fvorbis/Comment.hx	\
	org/xiph/fvorbis/Drft.hx	\
	org/xiph/fvorbis/DspState.hx	\
	org/xiph/fvorbis/EncodeAuxNearestMatch.hx	\
	org/xiph/fvorbis/EncodeAuxThreshMatch.hx	\
	org/xiph/fvorbis/Floor0.hx	\
	org/xiph/fvorbis/Floor1.hx	\
	org/xiph/fvorbis/FuncFloor.hx	\
	org/xiph/fvorbis/FuncMapping.hx	\
	org/xiph/fvorbis/FuncResidue.hx	\
	org/xiph/fvorbis/FuncTime.hx	\
	org/xiph/fvorbis/Info.hx	\
	org/xiph/fvorbis/InfoMode.hx	\
	org/xiph/fvorbis/Lookup.hx	\
	org/xiph/fvorbis/Lpc.hx		\
	org/xiph/fvorbis/Lsp.hx		\
	org/xiph/fvorbis/Mapping0.hx	\
	org/xiph/fvorbis/Mdct.hx	\
	org/xiph/fvorbis/PsyInfo.hx	\
	org/xiph/fvorbis/Residue0.hx	\
	org/xiph/fvorbis/Residue1.hx	\
	org/xiph/fvorbis/Residue2.hx	\
	org/xiph/fvorbis/StaticCodeBook.hx	\
	org/xiph/fvorbis/Time0.hx	\
	ArrayTools.hx		\
	Button.hx		\
	ButtonState.hx		\
	FOggPlayer.hx		\
	OpenEvent.hx		\
	OVSound.hx		\
	PauseButton.hx		\
	PlayButton.hx		\
	State.hx		\
	StopButton.hx		\
	System.hx		\
	TitleText.hx		\
	ValueChangeEvent.hx	\
	VectorTools.hx		\
	VolumeControl.hx

FOggPlayer.swf: FOggPlayer.hxml FOggPlayer.hx $(HX_FILES) Resource.swf

clean:
	rm -f FOggPlayer.swf

%.swf:	%.hx
	$(HAXE) -cp . $*.hxml

%.swf:	%.xml
	$(SWFMILL) simple $< $@
