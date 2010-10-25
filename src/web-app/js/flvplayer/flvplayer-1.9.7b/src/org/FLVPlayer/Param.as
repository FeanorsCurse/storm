/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer {
	
	import flash.display.DisplayObject;	
	
	/**
	 * 
	 * The Param class handles all the parameters for the FLV Player.
	 * You can either set them manually by ActionScript or you can get them from "outside" (HTML) by reading the flashvars.
	 * 
	 * The most important properties are playerPath and contentPath.
	 *   
	 *	
	 *	@version 1.9.7
	 */
	
	public class Param {
		
		private var _playerPath:String;	
		
		private var _contentPath:String;
		
		private var _autoPlay:Boolean;
		
		private var _autoScale:Boolean;	
				
		private var _buttonOverlay:String;
			
		private var _captions:String;
		
		private var _debug:Boolean;

		private var _defaultSkin:String;

		private var _ending:String;  
	
		private var _isLive:Boolean;

		private var _loop:Boolean;
	
		private var _preloader:String;
			
		private var _preRoll:String;			

		private var _preview:String;		
		
		private var _skin:String;
		
		private var _skinColor:uint;
	
		private var _skinScaleMaximum:Number;

		private var _smoothing:Boolean;	

		private var _video:String;
				
		private var _videoWidth:Number;		
				
		private var _videoHeight:Number;		
			
		private var _volume:Number;	

	
		/**
		* 
		* Constants
		* 
		*
		*/
		public static const DEFAULT_AUTO_PLAY:Boolean = false;
		public static const DEFAULT_AUTO_SCALE:Boolean = true;	
		public static const DEFAULT_BUTTON_OVERLAY_FILENAME:String = "defaultbuttonoverlay.swf";
		public static const DEFAULT_CONTENT_PATH:String = "";  
		public static const DEFAULT_ENDING_FILENAME:String = "defaultending.swf"; 		
		public static const DEFAULT_LOOP:Boolean = false;	
		public static const DEFAULT_PLAYER_PATH:String = "";		
		public static const DEFAULT_PRELOADER_FILENAME:String = "defaultpreloader.swf";
		public static const DEFAULT_PREVIEW_FILENAME:String = "defaultpreview.jpg";
		public static const DEFAULT_SKIN_COLOR:uint = 0x555555;
		public static const DEFAULT_SKIN_FILENAME:String = "defaultskin.swf";
		public static const DEFAULT_SKIN_SCALE_MAXIMUM:Number = 1;
		public static const DEFAULT_SMOOTHING:Boolean = true;
		public static const DEFAULT_DEBUG:Boolean = false;
		public static const DEFAULT_ISLIVE:Boolean = false;	
		public static const DEFAULT_VOLUME:Number = 1;	
	
		
					
			/**
			* Constructor
			*
			* If you create a new Param object and want to set the param by actionscript,
			* you have to use the two optional parameters. 
			* 			 
			* @param pp playerpath (optional)
			* @param cp contentpath (optional)
			*/
			public function Param() {
				playerPath = null;	
				contentPath = null;		
				autoPlay = DEFAULT_AUTO_PLAY;
				autoScale = DEFAULT_AUTO_SCALE;				
				buttonOverlay = null;
				captions = null;
				debug = DEFAULT_DEBUG;
				ending = DEFAULT_ENDING_FILENAME;
				isLive = DEFAULT_ISLIVE;		
				loop = DEFAULT_LOOP;
				preloader = null;
				preRoll = null;
				preview = null;
				skin = null;
				skinColor = DEFAULT_SKIN_COLOR;
				skinScaleMaximum = DEFAULT_SKIN_SCALE_MAXIMUM;
				smoothing = DEFAULT_SMOOTHING;
				video = null;
				videoHeight = NaN;
				videoWidth = NaN;
				volume = DEFAULT_VOLUME;										
			}

			
			

		/**
		* Get the parameters from flashvars. Flashvars are values that are defined in
		* the HTML page where the FLVPlayer is embedded. 
		*
		* @param DisplayObject 	A DisplayObject where the parameters could be found. 
		* If you call this function from the main timeline of Flash, use "root" as Displayobject.
		*
		*/
			public function setByFlashVars(base:DisplayObject):void {

				playerPath = base.loaderInfo.parameters.playerpath;	
				contentPath = base.loaderInfo.parameters.contentpath;
							
				autoPlay = changeParamToBoolean(base.loaderInfo.parameters.autoplay, DEFAULT_AUTO_PLAY);
				autoScale = changeParamToBoolean(base.loaderInfo.parameters.autoscale, DEFAULT_AUTO_SCALE);
				buttonOverlay = base.loaderInfo.parameters.buttonoverlay;
				captions = base.loaderInfo.parameters.captions;
				debug = changeParamToBoolean(base.loaderInfo.parameters.debug, DEFAULT_DEBUG);
				ending = base.loaderInfo.parameters.ending;   
				isLive = changeParamToBoolean(base.loaderInfo.parameters.islive, DEFAULT_ISLIVE);	
				loop = changeParamToBoolean(base.loaderInfo.parameters.loop, DEFAULT_LOOP);
				preloader = base.loaderInfo.parameters.preloader;
				preRoll = base.loaderInfo.parameters.preroll;
				preview = base.loaderInfo.parameters.preview;
				skin = base.loaderInfo.parameters.skin;
				skinColor = changeParamTo_uint(base.loaderInfo.parameters.skincolor, DEFAULT_SKIN_COLOR);
				skinScaleMaximum = changeParamToNumber(base.loaderInfo.parameters.skinscalemaximum);
				smoothing = changeParamToBoolean(base.loaderInfo.parameters.smoothing, DEFAULT_SMOOTHING);
				video = base.loaderInfo.parameters.video;
				videoHeight = changeParamToNumber(base.loaderInfo.parameters.videoheight);
				videoWidth = changeParamToNumber(base.loaderInfo.parameters.videowidth);
				volume = changeParamToNumber(base.loaderInfo.parameters.volume);
			}
			
			
		/**
		* Get the parameters from flashvars. Flashvars are values that are defined in
		* the HTML page where the FLVPlayer is embedded. 
		*
		* @param DisplayObject 	A DisplayObject where the parameters could be found. 
		* If you call this function from the main timeline of Flash, use "root" as Displayobject.
		*
		*/
			public function setByJavaScriptObject(javaScriptObj:Object):void {
				playerPath = javaScriptObj.playerpath;	
				contentPath = javaScriptObj.contentpath;
							
				autoPlay = changeParamToBoolean(javaScriptObj.autoplay, DEFAULT_AUTO_PLAY);
				autoScale = changeParamToBoolean(javaScriptObj.autoscale, DEFAULT_AUTO_SCALE);
				buttonOverlay = javaScriptObj.buttonoverlay;
				captions = javaScriptObj.captions;
				debug = changeParamToBoolean(javaScriptObj.debug, DEFAULT_DEBUG);
				ending = javaScriptObj.ending;   
				isLive = changeParamToBoolean(javaScriptObj.islive, DEFAULT_ISLIVE);	
				loop = changeParamToBoolean(javaScriptObj.loop, DEFAULT_LOOP);
				preloader = javaScriptObj.preloader;
				preRoll = javaScriptObj.preroll;
				preview = javaScriptObj.preview;
				skin = javaScriptObj.skin;
				skinColor = changeParamTo_uint(javaScriptObj.skincolor, DEFAULT_SKIN_COLOR);
				skinScaleMaximum = changeParamToNumber(javaScriptObj.skinscalemaximum);
				smoothing = changeParamToBoolean(javaScriptObj.smoothing, DEFAULT_SMOOTHING);
				video = javaScriptObj.video;
				videoHeight = changeParamToNumber(javaScriptObj.videoheight);
				videoWidth = changeParamToNumber(javaScriptObj.videowidth);
				volume = changeParamToNumber(javaScriptObj.volume);				
			}			
			



			/**
			 * 
			 */		
			public function set playerPath( arg:String ) : void { 
				_playerPath = arg;								
			}
			
			/**
			* This is an important property. It contains the URL or path to all the files that belong to the the FLV Player.  
			* Note: Don't place a Slash at the end, it is automatically added! 
			* Example: <code>http://www.video-flash.de/flvplayer</code> 
			*
			* @example http://www.video-flash.de/flvplayer
			*
			* @default ""
			*
			*/
			public function get playerPath():String {
				var result:String;
				if ((_playerPath != null) && (_playerPath != "")) {
					result = _playerPath + "/"; 
				}
				else {
					result = DEFAULT_PLAYER_PATH;
				}				 
				return result; 
			}




			/**
			* 
			*/
			public function set contentPath( arg:String ) : void { 
					_contentPath = arg;
				}
			
			/**
			* This is an important property. It contains the URL or path to all the files that belong to the content (video, preview, preroll and captions files).  
			* Note: Don't place a Slash at the end, it is automatically added! 
			* Example: <code>http://www.video-flash.de/videos</code> 
			*
			* @example content/videos
			*
			* @default ""
			*
			* @see #video
			* @see #preview
			* @see #captions
			* @see #preroll
			*/
			public function get contentPath():String {
				var result:String;

					if ((_contentPath != null) && (_contentPath != "")) {
						result = _contentPath + "/"; 
					}
					else {
						result = DEFAULT_CONTENT_PATH;
					}							 
				return result; 
			}




			/**
			*
			*/
			public function set video( arg:String ) : void { 
				_video = arg;
			}
	
	
			/**
			* The file name of the video file that should be displayed. Possible are all formats that can be played with the Flash Player
			* (.flv, .f4v, .m4v, .mov, etc.). The file has to be in the folder defined in "contentPath". If "contentPath" is "", you can also 
			* use absolute URLs (for instance http://www.video-flash.de/videos/video.flv). 
			 * 
			* @example /videos/myvideo.flv
			* @example http://www.video-flash.de/myvideo.flv
			*
			* @default null
			* @see #contentPath 
			*/	
			public function get video() : String { 
				var result:String;
				if ((_video != null) && (_video != "")) {
					result = contentPath + _video; 
				}
				else {
					result = null;
				}				
				
				return result; 
			}



			/**
			 * 
			 */		
			public function set preview( arg:String ) : void { 
				_preview = arg;
			}
			
			
			/**
			 * 
			 */			
			public function get preview() : String { 
				var result:String;
				if ((_preview != null) && (_preview != "")) {
					result = contentPath + _preview; 
				}
				else {
					result = playerPath + DEFAULT_PREVIEW_FILENAME;
				}				
				return result; 
			}


			/**
			 * 
			 */		
			public function getDefaultPreview() : String { 
				return playerPath + DEFAULT_PREVIEW_FILENAME;
			}			
                     



			/**
			 * 
			 */		
			public function set ending( arg:String ) : void { 
				_ending = arg;
			}
	
	
			/**
			 * 
			 */			
			public function get ending() : String { 
				var result:String;
				if ((_ending != null) && (_ending != "")) {
					result = playerPath + "endings/" + _ending; 
				}
				else {
					result = playerPath + "endings/" + DEFAULT_ENDING_FILENAME;
				}				
				return result; 
			}



			/**
			 * 
			 */		
			public function getDefaultEnding() : String { 
				return contentPath + DEFAULT_ENDING_FILENAME;
			}

			

			/**
			 * 
			 */		
			public function set skin( arg:String ) : void { 
				_skin = arg;
			}

			public function get skin() : String {
				var result:String; 
				if ((_skin != null) && (_skin != "")) {
					result = playerPath + "skins/" + _skin; 					
				}
				else {
					result = defaultSkin;
				}				
				return result; 
			}
			
			
			/**
			 * 
			 */		
			public function get defaultSkin() : String { 
				return playerPath + "skins/" + DEFAULT_SKIN_FILENAME; 
			}			


			/**
			 * 
			 */				
			public function set skinColor( arg:uint ) : void { 
				_skinColor = arg; 
			}
			
			
			/**
			 * Color of the skin file. Example: <code>0xFF0000</code>. Note: Works only, if the skins is enabled for changing its color.
			 * 
			 * @example 0xFF0000
			 * @default DEFAULT_SKIN_COLOR 
			 */					
			public function get skinColor() : uint { 
				return _skinColor; 
			}

		
			
			/**
			 * 
			 */		
			public function set skinScaleMaximum( arg:Number ) : void { 
				_skinScaleMaximum = arg;
			}
			
			/**
			 * This parameters is a scale factor for the skin in the fullscreen mode. If you choose for instance "2", the skin will be larger.
			 * If you have performance problems, set it to "4.5" (=default from Flash).
			 * @default: 1
			 */		
			public function get skinScaleMaximum() : Number { 
				
				var result:Number;
				if (isNaN(_skinScaleMaximum) == false) { 
					result = _skinScaleMaximum;
				}
				else {
					result = DEFAULT_SKIN_SCALE_MAXIMUM;
				}								
				return result; 
			}		
			


			
			/**
			 * 
			 */		
			public function set loop( arg:Boolean ) : void { 
				_loop = arg; 
			}
			
			/**
			 * 
			 */		
			public function get loop():Boolean { 
				return _loop; 
			}
			
			
			
			
			/**
			 * 
			 */		
			public function set debug( arg:Boolean ) : void { 
				_debug = arg; 
			}
			
			
			/**
			 * 
			 */		
			public function get debug():Boolean { 
				return _debug; 
			}
			
			
			/**
			 * 
			 */		
			public function set isLive( arg:Boolean ) : void { 
				_isLive = arg; 

			}
			
			/**
			 * If you want to show a live stream from a streaming server, you have to set this property to true.
			 * @default false
			 */	
			public function get isLive():Boolean { 
				return _isLive; 
			}
			
			
			/**
			 * 
			 */	
			public function set smoothing( arg:Boolean ) : void { 
				_smoothing = arg; 

			}
			
			/**
			 * This properties allows you to turn smoothing on or off. Smoothing improves the quality, especially in full screen mode and for scaled videos.  
			 * @default true
			 */
			public function get smoothing():Boolean { 
				return _smoothing; 
			}


			
			/**
			 * 
			 */
			public function set autoPlay( arg:Boolean ) : void { 
				_autoPlay = arg; 

			}
			
			/**
			* If set to true, the preview screen will be skipped. The video will start immediatelly and no preview will be displayed. 
			*
			* @default false
			*
			*/
			public function get autoPlay():Boolean { 
				return _autoPlay; 
			}


			/**
			*
			*/
			public function set autoScale( arg:Boolean ) : void { 
				_autoScale = arg; 
			}
			

			/**
			* If set to true, the preview as well as the video are scaled automatically to their native dimensions.
			* If no preview is available, the default preview and its size are taken.
			* 
			* If set to false, the preview as well as the video are scaled manually to the properties "videoWidth" and "videoHeight".
			* 
			* @default true
			*
			* @see #videoWidth
			* @see #videoHeight
			*/
			public function get autoScale():Boolean { 
				return _autoScale; 
			}


			/**
			 * 
			 */
			public function set preRoll( arg:String ) : void { 
					_preRoll = arg;
			}
			
			public function get preRoll() : String {
				var result:String;
				if ((_preRoll != null) && (_preRoll != "")) {
					result = contentPath + _preRoll; 
				}
				else {
					result = null;
				}				 
				return result; 
			}

	
					
			
			/**
			 *
			 */
			public function set captions( arg:String ) : void { 
					_captions = arg;
			}
			
			
			/**
			* This property contains the filename of a captions file (XML).
			* The file has to be in the "contentPath" folder. 	
			*	
			* @default null
			* @see #contentPath 
			*/
			public function get captions() : String {
				var result:String;
				if ((_captions != null) && (_captions != "")) {
					result = contentPath + _captions; 
				}
				else {
					result = null;
				}				 
				return result; 
			}
	
	
	
			/**
			 *
			 */
			public function set videoWidth( arg:Number ) : void { 
				_videoWidth = arg; 
			}
			
					
			/**
			* 
			* Width of the video in pixel. Video and preview screen are scaled to this width, but only if autoScale is set to false.
			* 
			* @default 240
			* @see #autoScale
			* @see #videoHeight
			*/				
			public function get videoWidth() : Number { 
				return _videoWidth; 
			}	
						
	
	
	
			public function set videoHeight( arg:Number ) : void { 
				_videoHeight = arg; 
			}
			
			/**
			* 
			* Height of the video in pixel. Video and preview screen are scaled to this height, but only if autoScale is set to false.
			* 
			* @default 240
			* @see #autoScale
			* @see #videoWidth
			*/						
			public function get videoHeight() : Number { 
				return _videoHeight; 
			}			
			
			
			
			
			/**
			 *
			 */
			public function set volume( arg:Number ) : void { 
				_volume = arg;					
			}
			
		
			/**
			* Initial volume of the FLV Player. 1 is maximum, 0 is minimum (=mute).
			* @default 1
			*/			
			public function get volume() : Number {
				var result:Number;
				if ( (_volume >= 0) && (_volume <= 1) ) {
					result = _volume; 
				} else {
					result = DEFAULT_VOLUME;
				}					 
				return result; 
			}			
	
	
			/**
			 *
			 */
			public function set preloader( arg:String ) : void { 
				_preloader = arg;
			}

			/**
			* This property contains the filename of the preloader file (SWF).
			* The file has to be in the "preloaders" folder in the FLV Player directory (=playerpath). 	
			* Default is the file "defaultpreloader.swf". 
			*	
			* @default playerpath + "preloaders/" + DEFAULT_PRELOADER_FILENAME
			*/
			
			public function get preloader() : String {
				var result:String;
				if ((_preloader != null) && (_preloader != "")) {
					result = playerPath + "preloaders/" + _preloader; 
				}
				else {
					result = playerPath + "preloaders/" + DEFAULT_PRELOADER_FILENAME;
				}				 
				return result; 
			}
			
			

			/**
			 *
			 */	
			public function set buttonOverlay( arg:String ) : void { 
					_buttonOverlay = arg;
			}
			
	
			/**
			* 
			* This property contains the filename of the buttonoverlay file (SWF).
			* The file has to be in the "buttonoverlays" folder in the FLV Player directory (=playerpath). 
			* Default is the file "defaultbuttonoverlay.swf".
			* 
			* @default playerpath + "buttonverlays/" + DEFAULT_BUTTON_OVERLAY_FILENAME
			*
			*/		
	
			public function get buttonOverlay() : String {
				var result:String;
				if ((_buttonOverlay != null) && (_buttonOverlay != "")) {
					result = playerPath + "buttonoverlays/" + _buttonOverlay; 
				}
				else {
					result = playerPath + "buttonoverlays/" + DEFAULT_BUTTON_OVERLAY_FILENAME;
				}				 
				return result; 
			}
	
	
	
			// :::::::::::::::::
			// :::::::::::::::::
	
	
			/**
			* This function converts the incoming String to a Number
			* @param  arg  String
			* @return value (or NaN, if not defined) Number
			*/	
	
			private function changeParamToNumber(arg:String):Number {
				var myNum:Number;
				if ((arg != null) && (arg != "")) {
					myNum = Number(arg); 
				}
				else {
					myNum = NaN;
				}		
				
				return myNum;
				
			}
			
			
			/**
			* This function converts the incoming String to Boolean
			* @param  arg  String
			* @return true/false
			*/	
			private function changeParamToBoolean(arg:String, defaultValue:Boolean):Boolean {
				var myBool:Boolean;
				if ((arg == "true") ||(arg == "false")) {
					
					if (arg == "true") {
						myBool = true;
					}
					
					if (arg == "false") {
						myBool = false;
					}		
				}
				else {
					myBool = defaultValue;
				}		
				
				return myBool;

			}
			
			
			/**
			* This function converts the incoming String to uint
			* @param  arg  String
			* @return value or defaultValue (if incoming string is not a valid uint)
			*/	
	
			private function changeParamTo_uint(arg:String, defaultValue:uint):uint {
				var my_uint:uint;
				
				if ((arg != null) && (arg != "")) {
						// save uint; an invalid string results in zero
						my_uint = uint(arg);
							
						// check if zero (=invalid), except for the case, that zero was the user input
						if ( (my_uint == 0) && (arg != "0x000000") ) {
								my_uint = defaultValue;
						}
							
				}
				else {
					my_uint = defaultValue;
				}		
								
				return my_uint;
			}
	
	} // class



} // package
