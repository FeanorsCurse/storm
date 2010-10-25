/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer { 

	import fl.transitions.*;
	import fl.transitions.easing.*;
	import fl.video.*;
	
	import flash.display.*;
	import flash.events.*;
	import flash.system.Security;
	import flash.external.ExternalInterface;
	import flash.text.TextField;
	import flash.utils.Timer;
	
	import org.osflash.thunderbolt.Logger;

	
	/**
	* This class is the main application of the FLV Flash Fullscreen Video Player. 
	*	
	*
	* @version 1.9.7
	* 
	*/
	
	public class FLVPlayer extends MovieClip {
		

		/**
		* Parameter object
		*/		
		private var myParam:Param;
		
		
		/**
		* playback engine (with the flv playback component in it)
		*/
		private var myPlaybackEngine:PlaybackEngine;
		
		
		/**
		* preloader
		*/	
		private var myPreloader:Preloader;	
		
		
		/**
		* preview
		*/
		private var myPreview:Preview;	
		
		
		/**
		* button overlay
		*/
		private var myButtonOverlay:ButtonOverlay;
		
		
		/**
		* chrome / skin
		*/
		private var myChrome:Chrome;
	
		
		/**
		* end screen
		*/
		private var myEnding:Ending;			
	
		
		/**
		* timer for checking the status
		*/
		private var myTimer:Timer;
		
		/**
		* text field for error messages 
		*/
		private var errorMsg:TextField;


		/**
		* state of the application 
		*/
		private var applicationState:String;


		/**
		* application state "init phase" 
		*/
		private static var STATE_INIT:String = "state_init";
		
		/**
		* application state "preview phase" 
		*/
		private static var STATE_PREVIEW:String = "state_preview";
		
		/**
		* application state "playing a video" 
		*/		
		private static var STATE_VIDEO:String = "state_video";
		
		/**
		* application state "ending phase" 
		*/			
		private static var STATE_ENDING:String = "state_ending";
		
		/**
		* application version
		*/					
		private static var APPLICATION_VERSION:String = "1.9.7";		


		/**
		*
		* The constructor of the FLVPlayer class sets the application state to STATE_INIT.
		*
		*/
			
		public function FLVPlayer():void {

			// set state for application: init
			applicationState = STATE_INIT;
			
			// init context menu
			var myContextMenu:CustomContextMenu = new CustomContextMenu(this);
		
		}


		/**
		* Get the parameters from flashvars. Flashvars are values that are defined in
		* the HTML page where the FLVPlayer is embedded. 
		*
		* @param DisplayObject 	A DisplayObject where the parameters could be found. 
		* If you call this function from the main timeline of Flash, use "root" as Displayobject.
		*
		*/
			
		public function getFlashVars(myDisplayObject:DisplayObject):void {

			// new Param
			myParam = new Param();
			
			// call function that gets the flashvars
			myParam.setByFlashVars(myDisplayObject);
		
		}


		/**
		* Set the param object as parameters for the FLV Player.
		*
		*	@param Param 	Param Object
		*
		*/
			
		public function setParam(p:Param):void {

			// set Param
			myParam = p;
		
		}


			
		/**
		* This function starts the player.
		*
		* @param p Param (optional)		Parameter Object for the player
		*
		*/
				
		public function start(p:Param = null):void {
		
			// handle optional parameter
			if (p != null) {
				myParam = p;
			}	
		
			// stop logging?
			if (myParam.debug == false) {
				// stop logging
				Logger.hide = true;
			}

			// set state for application: preview screen
			applicationState = STATE_PREVIEW;

			// place text field for error messages
			placeErrorMsgTextField();

			// new Preloader (check if it exists already)
			if (!Boolean(this.getChildByName('myPreloader'))) {
				myPreloader = new Preloader(myParam.preloader);
			}			
			myPreloader.visible = true;
			this.addChild(myPreloader);
			myPreloader.x = 10;
			myPreloader.y = 10;
			myPreloader.name = "myPreloader";						

			// event listener for fileNotFound
			myPreloader.addEventListener("fileNotFound", preloaderErrorHandling);
			
			// new preview
			if (myParam.preview != null) { 
				// preview available
				myPreview = new Preview(myParam.preview, myParam.autoScale, myParam.videoWidth, myParam.videoHeight);
			}
			else {
				// default preview 
				initDefaultPreview();
			}

			myPreview.addEventListener("fileNotFound", previewErrorHandling);


			// new button overlay
			myButtonOverlay = new ButtonOverlay(myParam.buttonOverlay);
			myButtonOverlay.addEventListener("fileNotFound", buttonOverlayErrorHandling);

			// new chrome
			myChrome = new Chrome(myParam.skin, myParam.skinColor);
			myChrome.addEventListener("fileNotFound", chromeErrorHandling);

			// check status (new Timer)
			myTimer = new Timer(200, 0);
			myTimer.addEventListener("timer", checkStatus);
			myTimer.start();		
			
			// trace to Firebug		
			debugParamObject();			
		}
		
		
		/**
		* Check if all the assets are loaded.
		*
		* @param p Param (optional)		Parameter Object for the player
		*
		*/		
		private function checkStatus(evt:TimerEvent):void {

			if ((myPreview.loadedComplete == true) && (myChrome.loadedComplete == true) && (myButtonOverlay.loadedComplete == true)) {

					myTimer.stop();

					// autoplay -> jump directly to "start video"
					if (myParam.autoPlay == true) {
						startVideo(new Event(""));
					}
					
					// show screen
					else {

						// add button overlay
						this.addChild(myButtonOverlay);

						// hide preloader, show buttonOverlay
						myPreloader.visible = false;

						// dispatch FLVPlayerResizeEvent (for Flex)
						//var myFLVPlayerResizeEvent:FLVPlayerResizeEvent = new FLVPlayerResizeEvent(FLVPlayerResizeEvent.RESIZE, false, false);
						//myFLVPlayerResizeEvent.width = myPreview.width;
						//myFLVPlayerResizeEvent.height = myPreview.height;
						//dispatchEvent(myFLVPlayerResizeEvent);

						// fade in preview
						this.addChild(myPreview);
						myPreview.alpha = 0;
						var previewTween:Tween = new Tween(myPreview,"alpha",Regular.easeOut,myPreview.alpha,100,35,true);

						// center playbutton and set onRelease
						myButtonOverlay.content.myPlaySymbol.centerButton(myPreview.width,myPreview.height);
						myButtonOverlay.content.myPlaySymbol.addEventListener (MouseEvent.MOUSE_UP, startVideo);

						// place chrome
						this.addChild(myChrome);
						myChrome.placeChrome(myPreview.width,myPreview.height);

						// swap depths
						this.swapChildren(myPreview, myPreloader);
						this.swapChildren(errorMsg, myPreview);
					
					}
			}
		}
		



		/**
		* Initiate the video player engine
		*
		*/

		private function startVideo(e:Event):void {

			// remove movieclips (only if autoplay is false, otherwise there aren't any movieclips)
			if (myParam.autoPlay == false) {
				
				// remove all items
				removePreviewMovieClips();
			}
			 
			//show Preloader
			myPreloader.visible = true;
			
			//new playback engine
			myPlaybackEngine = new PlaybackEngine(myParam);

			// event listeners
			myPlaybackEngine.addEventListener ("fileNotFound", playbackEngineErrorHandling);
			myPlaybackEngine.addEventListener ("complete", playbackEngineComplete);
			myPlaybackEngine.addEventListener ("videoNotFound", videoFileErrorHandling);
			myPlaybackEngine.addEventListener ("preRollNotFound", preRollErrorHandling);
			myPlaybackEngine.addEventListener("ending", playbackEngineInitEnding);

			// add to stage
			this.addChild(myPlaybackEngine);
		}



		/**
		* Playback Engine is completely loaded
		*
		*/

		private function playbackEngineComplete(evt:Event):void {

				// add listener for keyboard
				//var st:Stage = this..stage;
				stage.addEventListener(KeyboardEvent.KEY_UP, onKey);
				stage.focus = this.parent;

				// hide preloader
				this.removeChild(this.myPreloader);
				
				// set state for application: video
				applicationState = STATE_VIDEO;	
									
		}


		/*
		* Init end screen
		*
		*/

		private function playbackEngineInitEnding(evt:Event):void {

				// show preloader
				myPreloader.visible = true;
				this.addChild(myPreloader);
				
				this.myEnding = new Ending(this.myParam.ending);
				this.myEnding.addEventListener(Event.COMPLETE, onEndingComplete);
				this.myEnding.addEventListener("fileNotFound", endingErrorHandling);
				
				// set state for application: ending
				applicationState = STATE_ENDING;		

		}


		/*
		* End screen completely loaded
		*
		*/

		private function onEndingComplete(evt:Event):void {
				
			// add ending and remove preloader	
			this.addChild(myEnding);
			this.removeChild(myPreloader);
			
			// remove event listeners
			this.myEnding.removeEventListener(Event.COMPLETE, onEndingComplete);
			this.myEnding.removeEventListener("fileNotFound", endingErrorHandling);
			
			// leave fullscreen for ending
			if (stage.displayState == StageDisplayState.FULL_SCREEN) {
				stage.displayState = StageDisplayState.NORMAL;
			}
			
			// set up ending panel
			this.myEnding.content.myContainer.width = this.myPlaybackEngine.player.myFLVPlayback.width;
			this.myEnding.content.myContainer.height = this.myPlaybackEngine.player.myFLVPlayback.height;
			
			// add event listeners 			
			this.myPlaybackEngine.addEventListener(Event.CHANGE, onStateChangeDuringEnding);	
			this.myEnding.content.addEventListener("replay", onReplay);
			Logger.info("Ending has been loaded");
			
		}
		
	
	
		/*
		* 
		*
		*/

		private function onReplay(evt:Event):void {
			this.myEnding.content.removeEventListener("replay", onReplay);
			this.myPlaybackEngine.player.myFLVPlayback.play();	
			applicationState = STATE_VIDEO;
			Logger.info("Replay");
		}
		
		/*
		* 
		*
		*/

		private function onStateChangeDuringEnding(evt:Event):void {
			this.myPlaybackEngine.removeEventListener(Event.CHANGE, onStateChangeDuringEnding);
			try {
			this.removeChild(myEnding);
			}
			catch (e:Error) {};
			Logger.info("Ending has been removed");
			
		}		


		/*
		* Init default preview
		*
		*/

		private function initDefaultPreview():void {
			myPreview = new Preview(myParam.getDefaultPreview(), myParam.autoScale, myParam.videoWidth, myParam.videoHeight);	
		}



		/*
		* Init default chrome
		*
		*/

		private function initDefaultChrome():void {
			myParam.skin = myParam.defaultSkin;
			myChrome = new Chrome(myParam.skin, myParam.skinColor);
			myChrome.addEventListener("fileNotFound", chromeErrorHandling);

		}




		/**
		*	
		* place text field for error messages
		*	
		*/

		private function placeErrorMsgTextField():void {
			errorMsg = new TextField();
			addChild (errorMsg);
			errorMsg.width = 200;
			errorMsg.height = 60;
		}	
	
	

		/**
		* Update player with a new parameter object. This is useful if you instantiate the FLV Player in your
		* own ActionScript project. You can use this function to change all the parameters (including the video file). 
		*
		* @param flv player param object
		*/

		public function updatePlayer(p:Param):void {

			// clean error message display
			errorMsg.text = "";

			// check current state
			if (applicationState == STATE_PREVIEW) {
				
				// stop timer (in case the player is loading some preview assets at the moment)
				myTimer.stop();
				
				// remove movieclips
				removePreviewMovieClips();
				
				
			}
			
			// check current state
			if ( applicationState == STATE_VIDEO ) {
				
				// stop video / close netstream
				myPlaybackEngine.player.myFLVPlayback.stop();
				myPlaybackEngine.player.vp.netStream.close();
				
				try {
				// remove video from stage
				removeChild (myPlaybackEngine);
				} 
				catch (e:Error) { }
	
				
			}
			
			// check current state
			if ( applicationState == STATE_ENDING ) {
				
				try {
				// remove video from stage
				removeChild (myPlaybackEngine);
				
				// remove ending				
				removeChild(myEnding);
				}
				catch (e:Error) {}
			}

		// start/init player with new param object			
		start(p);


		}	


		/**
		 * Add external interface for JavaScript Communication
		 * 
		 */
		public function addExternalInterface():void {
			if (Security.sandboxType != Security.LOCAL_WITH_FILE) {
				ExternalInterface.addCallback("updatePlayer", updateParamFromExternalInterface);
			}
		}  
		
		
		/**
		 * Update parameters from external interface
		 * 
		 */
		private function updateParamFromExternalInterface(javaScriptObj:Object):void {
			var p:Param = new Param(); 
			p.setByJavaScriptObject(javaScriptObj);
			updatePlayer(p);
			Logger.info("External Interface: Updated player with JavaScript parameters");
		}		
		


		/**
		*	
		* Remove all movieclips that are displayed in preview screen state
		*	
		* @param event
		*/

		private function removePreviewMovieClips():void {
			try {
				removeChild(myPreview);
				removeChild(myChrome);
				removeChild(myButtonOverlay);
			}
			catch (e:Error) {
				
			}
			
		}
	

		
		/**
		  * Keyboard
		  *
		  */		
		private function onKey(e:KeyboardEvent):void {
			
			var keyEvt:KeyboardControl = new KeyboardControl(this.myPlaybackEngine);
			keyEvt.evaluate(e.keyCode);

		}		
	

		/**
		*	
		* preview error handling
		*	
		* @param event
		*/

		private function previewErrorHandling(evt:Event):void {
			Logger.error ("Preview file could not be found, loading default preview");
			initDefaultPreview();
		}


		/**
		*	
		* chrome error handling
		*	
		* @param event
		*	
		*/

		private function chromeErrorHandling(evt:Event):void {

			if (myParam.skin != (myParam.defaultSkin) ) {
				Logger.error ("Custom skin file could not be found ... Loading default skin");
				initDefaultChrome();
			}
			else {
				Logger.error ("Default skin file could not be found!");
			}

		}



		/**
		*	
		* playback engine error handling
		*	
		* @param event
		*/

		private function playbackEngineErrorHandling(evt:Event):void {

			Logger.error ("Error: Playback Engine (playbackengine.swf) could not be found.");
			myPreloader.visible = false;

		}



		/**
		*	
		* video file not found error handling
		*	
		* @param event
		*/

		private function videoFileErrorHandling(evt:Event):void {

			Logger.error ("Error: Video file could not be found.");
			errorMsg.text = "Error: Video file could not be found.";
			myPreloader.visible = false;

		}


		/**
		*	
		* video file not found error handling
		*	
		* @param event
		*/

		private function preRollErrorHandling(evt:Event):void {

			Logger.error ("Error: Preroll file could not be found.");
			myPreloader.visible = false;

		}


		/**
		*	
		* button overlay file not found error handling
		*
		* @param event
		*/

		private function buttonOverlayErrorHandling(evt:Event):void {

			Logger.error ("Error: button overlay file could not be found.");
			myPreloader.visible = false;

		}


		/**
		* preloader file not found error handling
		*
		* @param event
		*/

		private function preloaderErrorHandling(evt:Event):void {

			Logger.error ("Error: preloader file could not be found.");

		}
		

		/**
		* ending file not found error handling
		*
		* @param event
		*/

		private function endingErrorHandling(evt:Event):void {

			Logger.error ("Error: ending file could not be found.");

		}		



		/**
		*	
		* debug: trace variables with ThunderBolt to Firebug
		*	
		*/

		private function debugParamObject():void {
				Logger.info("### FLV Flash Fullscreen Video Player ### version is: " + APPLICATION_VERSION);
				Logger.info("*** Parameter Object: *** autoplay is: " + myParam.autoPlay );
				Logger.info("*** Parameter Object: *** autoscale is: " + myParam.autoScale );
				Logger.info("*** Parameter Object: *** buttonOverlay is: " + myParam.buttonOverlay);
				Logger.info("*** Parameter Object: *** captions is: " + myParam.captions );
				Logger.info("*** Parameter Object: *** content path is: " + myParam.contentPath );
				Logger.info("*** Parameter Object: *** debug is: " + myParam.debug );
				Logger.info("*** Parameter Object: *** default skin is: " + myParam.defaultSkin);
				Logger.info("*** Parameter Object: *** ending is: " + myParam.ending);				
				Logger.info("*** Parameter Object: *** islive is: " + myParam.isLive);
				Logger.info("*** Parameter Object: *** loop is: " + myParam.loop );
				Logger.info("*** Parameter Object: *** loop is: " + myParam.loop );
				Logger.info("*** Parameter Object: *** player path is: " + myParam.playerPath );
				Logger.info("*** Parameter Object: *** preloader is: " + myParam.preloader);
				Logger.info("*** Parameter Object: *** preroll is: " + myParam.preRoll );
				Logger.info("*** Parameter Object: *** preview is: " + myParam.preview );
				Logger.info("*** Parameter Object: *** skin is: " + myParam.skin);
				Logger.info("*** Parameter Object: *** skin scale maximum is: " + myParam.skinScaleMaximum);
				Logger.info("*** Parameter Object: *** skincolor is: " + myParam.skinColor);
				Logger.info("*** Parameter Object: *** smoothing is: " + myParam.smoothing);
				Logger.info("*** Parameter Object: *** video is: " + myParam.video);
				Logger.info("*** Parameter Object: *** videoheight is: " + myParam.videoHeight );
				Logger.info("*** Parameter Object: *** videowidth is: " + myParam.videoWidth );
				Logger.info("*** Parameter Object: *** volume is: " + myParam.volume );				
		}

		


	}
}