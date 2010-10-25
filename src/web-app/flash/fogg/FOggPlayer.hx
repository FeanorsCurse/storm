////////////////////////////////////////////////////////////////////////////////
//
//  FOggPlayer - A simple Flash Ogg Vorbis player written in Haxe.
//
//  Copyright (C) 2009  Bill Farmer
//
//  This program is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License along
//  with this program; if not, write to the Free Software Foundation, Inc.,
//  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
//
//  Bill Farmer  william j farmer [at] tiscali [dot] co [dot] uk.
//
//  Uses the development FOgg Ogg Vorbis HaXe library from xiph.org.
//
////////////////////////////////////////////////////////////////////////////////

import flash.Lib;
import flash.Error;
import flash.Vector;
import flash.text.Font;
import flash.geom.Matrix;
import flash.utils.Timer;
import flash.media.Sound;
import flash.events.Event;
import flash.display.Shape;
import flash.net.URLLoader;
import flash.net.URLStream;
import flash.display.Sprite;
import flash.net.URLRequest;
import flash.text.TextField;
import flash.errors.IOError;
import flash.text.TextFormat;
import flash.display.Graphics;
import flash.display.MovieClip;
import flash.events.MouseEvent;
import flash.events.TimerEvent;
import flash.media.SoundChannel;
import flash.system.Capabilities;
import flash.events.IOErrorEvent;
import flash.display.GradientType;
import flash.display.SpreadMethod;
import flash.media.SoundTransform;
import flash.events.ProgressEvent;
import flash.display.SimpleButton;
import flash.events.SampleDataEvent;
import flash.media.SoundLoaderContext;
import flash.events.SecurityErrorEvent;
// import flash.filters.DropShadowFilter;
import flash.display.InterpolationMethod;
// import flash.filters.BitMapFilterQuality;

// Declare the font as a class

class Silkscreen extends flash.text.Font {}

// FOggPlayer class

class FOggPlayer
{
    // Static colour strings and values

    static var COLOURS : Array<String> =
	["red", "green", "blue",
	 "cyan", "magenta", "yellow"];

    static var VALUES : Array<Array<Int>> =
	[[0xff4040, 0xc04040], [0x40ff40, 0xc0ffc0],
	 [0x4040ff, 0x4040c0], [0x40ffff, 0x40c0c0],
	 [0xff40ff, 0xc040c0], [0xffff40, 0xc0c040]];

    // Class variables

    var position  : Float;
    var playTimer : Timer;
    var titleTimer: Timer;
    var volume    : Float;
    var colour    : String;
    var url       : String;
    var title     : String;
    var sound     : OVSound;
    var titleText : TitleText;
    var colours   : Array<Int>;
    var request   : URLRequest;
    var channel   : SoundChannel;

    // FIXME: find a better way to initialize those static bits?

    static function init_statics() : Void {
        org.xiph.fogg.Buffer._s_init();
        org.xiph.fvorbis.FuncFloor._s_init();
        org.xiph.fvorbis.FuncMapping._s_init();
        org.xiph.fvorbis.FuncTime._s_init();
        org.xiph.fvorbis.FuncResidue._s_init();
    }

    // Kick things off

    public static function main()
    {
	init_statics();

	var foggplayer = new FOggPlayer();
    }

    // Create a new FOggPlayer

    function new()
    {
        var clip : MovieClip = Lib.current;
	var g    : Graphics = clip.graphics;

	// Create the buttons and volume control

	var playButton    : PlayButton = new PlayButton();
	var pauseButton   : PauseButton = new PauseButton();
	var stopButton    : StopButton = new StopButton();
	var volumeControl : VolumeControl = new VolumeControl();

	// And the text field

	titleText = new TitleText();
	volume = 1;

	// Position the buttons etc

	pauseButton.x = playButton.width;
	stopButton.x = pauseButton.x + pauseButton.width;
	titleText.x = stopButton.x + stopButton.width + 4;
	titleText.y = 3;
	titleText.width = clip.width - ((playButton.width * 3) +
					volumeControl.width + 10);
	volumeControl.x = titleText.x + titleText.width + 3;
	volumeControl.y = 3;

	// Draw the frame background

	draw(g);

	// Add the buttons etc

	clip.addChild(playButton);
	clip.addChild(pauseButton);
	clip.addChild(stopButton);
	clip.addChild(titleText);
	clip.addChild(volumeControl);

	// If there's a url, enable the buttons, and add the event
	// listeners

	if (getParameters())
	{
	    playButton.enabled = true;
	    pauseButton.enabled = true;
	    stopButton.enabled = true;

	    // Change the titleText event listener to pause as replay
	    // currently doesn't work with the FOgg decoder

	    playButton.addEventListener(MouseEvent.CLICK, playSound);
	    stopButton.addEventListener(MouseEvent.CLICK, stopSound);
	    pauseButton.addEventListener(MouseEvent.CLICK, pauseSound);
	    titleText.addEventListener(MouseEvent.CLICK, pauseSound);
	    volumeControl.addEventListener(ValueChangeEvent.VALUE_CHANGE,
					   changeVolume);
	}
    }
	
    // Draw the frame background

    function draw(g : Graphics)
    {
	// Set up a nice gradient for the right hand end of the frame
	// to look concave

	var colors : Array<Int> = [0xA0A0A0, 0xF0F0F0];
	var alphas : Array<Int> = [1, 1];
	var ratios : Array<Int> = [0, 255];
	var matrix : Matrix = new Matrix();

	matrix.createGradientBox(285, 19, Math.PI/2, 0, 0);
	g.beginGradientFill(GradientType.LINEAR, 
			    colors,
			    alphas,
			    ratios, 
			    matrix, 
			    SpreadMethod.PAD, 
			    InterpolationMethod.LINEAR_RGB, 
			    0);

	// Draw the frame background

	g.drawRoundRect(titleText.x - 4, 0, 287, 21, 5, 5);
	g.endFill();

	// Change the colours for the text field background, and set
	// up another gradient

	colors = [0xffffff, 0xd0d0d0];
	matrix.createGradientBox(titleText.width - 2,
				 titleText.height - 2,
				 Math.PI/2, 0, 0);

	g.beginGradientFill(GradientType.LINEAR, 
			    colors,
			    alphas,
			    ratios, 
			    matrix, 
			    SpreadMethod.PAD, 
			    InterpolationMethod.LINEAR_RGB, 
			    0);

	// Draw the text field background

	g.drawRoundRect(titleText.x, titleText.y,
			titleText.width, titleText.height, 5, 5);
    }

    // Get the url and title

    function getParameters() : Bool
    {
	var clip : MovieClip = Lib.current;
	var pars : Dynamic<String> = clip.loaderInfo.parameters;

	// Check the url

	if (pars.url == null)
	{
	    title = "No URL";
	    titleText.text = title;
	    return false;
	}

	// If there's a url, see if there's a title

	url = pars.url;

	if (pars.title == null)
	{
	    title = "No Title: " + url;
	    titleText.text = title;
	}
			
	else
	{
	    title = pars.title;
	    titleText.text = title;
	}

	// See if there's a colour. Have to allow for those folks
	// across the pond who can't spell

	if (pars.color != null)
	{
	    colour = pars.color;
	    setColours();
	}

	if (pars.colour != null)
	{
	    colour = pars.colour;
	    setColours();
	}

	// Default colours

	if (colours == null)
	    colours = [0x40ffff, 0x40c0c0];

	return true;
    }

    // Set colours

    function setColours()
    {
	var fg : Int;
	var bg : Int;

	// Does it start with '#'?

	if (colour.indexOf("#") == 0)
	{
	    // Are there two values?

	    var ac = colour.split(",");

	    if (ac.length != 2)
		return;

	    // Does the second one start with '#'?

	    if (ac[1].indexOf("#") != 0)
		return;

	    // Parse the two values

	    fg = Std.parseInt("0x" + ac[0].substr(1));
	    bg = Std.parseInt("0x" + ac[1].substr(1));

	    colours = [fg, bg];
	}

	// Match a colour

	else
	{
	    for (i in 0...COLOURS.length)
	    {
		if (colour == COLOURS[i])
		{
		    fg = VALUES[i][0];
		    bg = VALUES[i][1];
		    colours = [fg, bg];
		    break;
		}
	    }
	}
    }

    // Play button clicked, play the music

    function playSound(e)
    {
	// If there's a channel, stop it

	if (channel != null)
	    channel.stop();

	// If position is zero set the sound object to null to force a
	// replay

	if (position == 0)
	    sound = null;

	// If there's a url

	if (url != null)
	{
	    // If no sound object, create one

	    if (sound == null)
	    {
		request = new URLRequest(url);
		sound = new OVSound();

		// Add the event listeners for errors

		sound.addEventListener(IOErrorEvent.IO_ERROR, ioError);
		sound.addEventListener(SecurityErrorEvent.SECURITY_ERROR,
				       ioError);

		// Load the request

		sound.load(request);

		// Set the position to the start because this is the
		// first time

		position = 0;
	    }
			
	    // Play the music

	    if (sound != null)
	    {
		// Add the event listeners for load progress, load
		// complete and sound open

		sound.addEventListener(ProgressEvent.PROGRESS, loadProgress);
		sound.addEventListener(Event.COMPLETE, loadComplete);
		sound.addEventListener(Event.OPEN, soundOpen);

		// Play the music, this will return null the first
		// time around because not enough data available

		channel = sound.play(position);

		// Set the volume, if the channel isn't null

		if (channel != null)
		{
		    var transform = channel.soundTransform;
		    transform.volume = volume;
		    channel.soundTransform = transform;

		    // Add the event listener for sound complete

		    channel.addEventListener(Event.SOUND_COMPLETE,
					     soundComplete);
		}

		// Start a timer to show play progress, there's no play
		// progress event

		startPlayTimer();

		// Set the text to show what's happening

		titleText.text = "Play";
		restoreTitle();
	    }
	}
    }

    // Sound open event

    function soundOpen(e : OpenEvent)
    {
	channel = e.channel;

	// Set the volume, if the channel isn't null

	if (channel != null)
	{
	    var transform = channel.soundTransform;
	    transform.volume = volume;
	    channel.soundTransform = transform;

	    // Add the event listener for sound complete

	    channel.addEventListener(Event.SOUND_COMPLETE,
				     soundComplete);
	}
    }

    // IO error

    public function ioError(e : Event)
    {
	sound = null;
	channel = null;

	// Show the error

	titleText.text =  e.type;
	restoreTitle();
    }

    // Pause button clicked

    function pauseSound(e)
    {
	// If there's a channel, save the position and stop the
	// channel and the timer

	if (channel != null)
	{
	    position = channel.position;
	    channel.stop();

	    if (playTimer != null)
		playTimer.stop();

	    // Show what's happening

	    titleText.text = "Pause";
	    restoreTitle();
	}
    }
	
    // Stop button clicked

    function stopSound(e)
    {
	// If there's a channel, stop it and the timer, and set the
	// position back to the start

	if (channel != null)
	{
	    position = 0;
	    channel.stop();

	    if (playTimer != null)
		playTimer.stop();

	    // Draw progress bar

	    drawProgressBar(1, 0);

	    // Show what's happening

	    titleText.text = "Stop";
	    restoreTitle();
	}
    }

    // Text field clicked, reposition the music. This currently
    // doesn't work with the FOgg decoder, so do nothing

    function replaySound(e)
    {
    }
	
    // Sound complete event

    public function soundComplete(e)
    {
	// Set the position back to the start

	position = 0;

	// Stop the timer

	if (playTimer != null)
	    playTimer.stop();

	// Draw the progress bar

	drawProgressBar(1, 0);

	// Show what's happening

	var mins = Math.floor(sound.length / 60000);
	var secs = Math.round((sound.length / 10) / 100) - (mins * 60);

	titleText.text = "Complete: " + mins + " mins " + secs + " secs";
	restoreTitle();
    }

    // Load progress event

    public function loadProgress(e)
    {
	// Calculate the progress bar length

	var l = e.bytesLoaded / e.bytesTotal;

	// Draw the progress bar

	drawProgressBar(l, 0);
    }

    // Load complete event

    public function loadComplete(e)
    {
	// Draw the progress bar

	drawProgressBar(1, 0);

	// Show what's happening

	titleText.text = "Load complete: " + sound.bytesTotal + " bytes";
	restoreTitle();
    }

    // Start a play timer to show play progress. This currently
    // doesn't work with the FOgg decoder, so do nothing

    function startPlayTimer()
    {
    }

    // Play timer event, show play progress. This currently doesn't
    // work with the FOgg decoder, so do nothing

    function playProgress(e)
    {
    }

    // Draw progress bar

    function drawProgressBar(l : Float, p : Float)
    {
	var clip : MovieClip = Lib.current;
	var g : Graphics = clip.graphics;

	var lx = l * titleText.width;
	var px = p * titleText.width;

	// Draw the load progress bar, but not right away because of
	// the rounded corners

	if (lx > 3)
	{
	    // Create a nice gradient

	    var colors : Array<Int> = colours;
	    var alphas : Array<Int> = [1, 1];
	    var ratios : Array<Int> = [0, 255];
	    var matrix : Matrix = new Matrix();

	    matrix.createGradientBox(lx - 2,
				     titleText.height - 2,
				     Math.PI/2, 0, 0);
	    g.beginGradientFill(GradientType.LINEAR, 
				colors,
				alphas,
				ratios, 
				matrix, 
				SpreadMethod.PAD, 
				InterpolationMethod.LINEAR_RGB, 
				0);

	    // Draw the text field background

	    g.drawRoundRect(titleText.x, titleText.y, lx,
			    titleText.height, 5, 5);
	}

	// Draw the play progress bar, but not right away because of
	// the rounded corners

	if (px > 3)
	{
	    var colors : Array<Int> = [0xffffff, 0xd0d0d0];
	    var alphas : Array<Int> = [1, 1];
	    var ratios : Array<Int> = [0, 255];
	    var matrix : Matrix = new Matrix();

	    matrix.createGradientBox(px - 2,
				     titleText.height - 2,
				     Math.PI/2, 0, 0);
	    g.beginGradientFill(GradientType.LINEAR, 
				colors,
				alphas,
				ratios, 
				matrix, 
				SpreadMethod.PAD, 
				InterpolationMethod.LINEAR_RGB, 
				0);

	    // Draw a nice shaded progress bar

	    g.drawRoundRect(titleText.x, titleText.y, px,
			    titleText.height, 5, 5);
	}
    }

    // Volume change event

    function changeVolume(e : ValueChangeEvent)
    {
	// Set the new volume

	volume = e.value;

	// If there's a channel, set the new volume on the channel

	if (channel != null)
	{
	    var transform = channel.soundTransform;
	    transform.volume = volume;
	    channel.soundTransform = transform;
	}

	// Show what's happening

	titleText.text = "Volume: " + Math.round(volume * 100) + "%";
	restoreTitle();
    }

    // Restore the title after a delay

    function restoreTitle()
    {
	// If there's a timer, stop it

	if (titleTimer != null)
	    titleTimer.stop();

	// Create a new timer, 2 secs

	titleTimer = new Timer(2000, 1);
	titleTimer.addEventListener(TimerEvent.TIMER, setTitle);
	titleTimer.start();
    }

    // Set the title after a delay

    function setTitle(e)
    {
	titleText.text = title;
    }
}
