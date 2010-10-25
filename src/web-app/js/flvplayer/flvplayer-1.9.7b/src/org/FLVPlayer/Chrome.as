/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer { 

	import flash.display.Loader;
	import flash.display.MovieClip;
	import flash.events.*;
	import flash.geom.ColorTransform;
	import flash.net.URLRequest;

	
	/**
	* Class Chrome
	* This class loads a skin file for the FLV Playback Component containing the chrome (=background behind the buttons).
	* 
	*/
	
	public class Chrome extends MovieClip {
		
		/**
		* Request
		*/
		private var request:URLRequest;
		
		
		/**
		* Loader
		*/
		private var loader:Loader 					
		
		
		/**
		* skin color
		*/
		private var skinc:uint;						
		
		/**
		* reference to to skin file movieclip
		*/		
		public var completeSkin_mc:MovieClip;
		
		/**
		* indicates that everything is loaded
		*/		
		public var loadedComplete:Boolean;			
		
		
		
		/**
		  * Constructor
		  * loads the external swf skinfile
		  * and adds event listener
		  *
		  * @param url    skinfile (.swf)
		  * @param col	  color of the chrome
		  *
		  */
		
		
		public function Chrome (url:String, col:uint){
			
			// init objects
			request  = new URLRequest(url);
			loader = new Loader();
			this.loadedComplete = false;
			this.skinc = col;
			
			// eventListeners
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loadComplete);
			loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, fileNotFound);
			
			// start loading
			loader.load(request);
			
			// add to stage
			this.addChild(loader);
		}
		

		/**
		  * loadComplete
		  * chrome is complete
		  *
		  * The function hides all elements in the skin (buttons, ...) except the chrome.
		  *
		  * @param event   event from loader class 
		  *
		  */ 
		private function loadComplete(event:Event):void {
			
		    // trace("Chrome complete");			
			this.loadedComplete = true;
									
			// reference to content movieclip
			this.completeSkin_mc = loader.content as MovieClip;	

			// hide complete Skin
			this.completeSkin_mc.visible = false;
			
			// hide all elements in the skin (buttons, ...)
			hideSkinElement(this.completeSkin_mc.captionToggle_mc);
			hideSkinElement(this.completeSkin_mc.video_mc);
			hideSkinElement(this.completeSkin_mc.playpause_mc);			
			hideSkinElement(this.completeSkin_mc.stop_mc);
			hideSkinElement(this.completeSkin_mc.seekBarProgress_mc);			
			hideSkinElement(this.completeSkin_mc.fullScreenToggle_mc);
			hideSkinElement(this.completeSkin_mc.volumeBar_mc);	
			hideSkinElement(this.completeSkin_mc.seekBarHandle_mc);	
			hideSkinElement(this.completeSkin_mc.volumeBarHandle_mc);	
			hideSkinElement(this.completeSkin_mc.seekBar_mc);
			hideSkinElement(this.completeSkin_mc.seekBarHit_mc);				
			hideSkinElement(this.completeSkin_mc.bufferingBarFill_mc);							
			hideSkinElement(this.completeSkin_mc.bufferingBar_mc);			
			hideSkinElement(this.completeSkin_mc.back_mc);
			hideSkinElement(this.completeSkin_mc.forward_mc);
			hideSkinElement(this.completeSkin_mc.volumeMute_mc);
			hideSkinElement(this.completeSkin_mc.captionToggle_mc);
			hideSkinElement(this.completeSkin_mc.forwardBackFrame_mc);
			
			
		}

		
		 /**
		  * hideSkinElement
		  *
		  * Tries to hide the element
		  *
		  * @param MovieClip	MovieClip to hide
		  *
		  */
		  
		  private function hideSkinElement(skinElement:MovieClip):void {
			  
			// hide movieclip
			try {
				skinElement.visible = false;				
			}
						
			catch(e:Error) {
			};
			  
		  }
		
		
		/**
		  * placeChrome
		  *
		  * The function places chrome under the preview picture and sets the size of the chrome
		  *
		  * @param imageWidth 	width of the preview 
		  *	@param imageHeight	height of the preview 
		  *
		  *
		  */
		
		
		public function placeChrome(imageWidth:Number, imageHeight: Number):void {					
			
			// set x of the chrome to 0
			this.completeSkin_mc.border_mc.x = 0;			
			this.completeSkin_mc.outline_mc.x = this.completeSkin_mc.border_mc.x;				
			this.completeSkin_mc.shadow_mc.x = this.completeSkin_mc.border_mc.x;
			
			// set chrome to the width of the preview pic
			this.completeSkin_mc.border_mc.width = imageWidth;			
			this.completeSkin_mc.outline_mc.width = this.completeSkin_mc.border_mc.width;				
			this.completeSkin_mc.shadow_mc.width = this.completeSkin_mc.border_mc.width;				
	
			// below the y of the preview pic
			this.completeSkin_mc.border_mc.y = imageHeight;			
			this.completeSkin_mc.outline_mc.y = this.completeSkin_mc.border_mc.y;				
			this.completeSkin_mc.shadow_mc.y = this.completeSkin_mc.border_mc.y;			
		
			// change color
			this.changeChromeColor();
		
			// show chrome
			this.completeSkin_mc.visible = true;
			
		}
		

		/**
		  * changeChromeColor
		  *	
		  * This function changes the chrome color (on preview screen)
		  *	The color value has been set by the Constructor of this class.
          *
		  */
		public function changeChromeColor():void {	
		
			// color Change
			var colorInfo:ColorTransform = this.completeSkin_mc.border_mc.transform.colorTransform;
						
			// Set the color of the ColorTransform object
    		colorInfo.color = skinc;
			
			// apply the change to the display object
			this.completeSkin_mc.border_mc.transform.colorTransform = colorInfo;
		
		}
		
		
		/**
		  * Skin file with chrome not found.
		  *	Dispatch event.
		  *
		  */		
		private function fileNotFound(e:Event):void {
			trace ("error: file not found");
			dispatchEvent(new Event("fileNotFound"));
		}
		
	}
}