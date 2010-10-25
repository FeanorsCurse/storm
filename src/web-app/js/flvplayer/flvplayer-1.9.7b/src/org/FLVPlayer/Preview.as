/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer { 

	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.display.MovieClip;
	import flash.events.*;

	
	/**
	  * This class loads a preview (jpg, png, gif).
	  * 
	  */
	
	public class Preview extends MovieClip {
	
		/**
		* URLRequest
		* 
		*/
		private var request:URLRequest;
		
		/**
		* Loader
		* 
		*/
		private var loader:Loader; 
	
		/**
		* If true, the preview is loaded 
		* 
		*/
		public var loadedComplete:Boolean;
		
		
		/**
		* autoScale; if set to true, the preview is displayed with its native size;
		* if set to false, the preview is scaled to noAutoScaleWidth and noAutoScaleHeight
		* 
		* @default 
		*/
		public var autoScale:Boolean;
		
		
		/**
		* Width in Pixel
		* 
		*
		* @see #autoScale 
		*/
		public var noAutoScaleWidth:Number;
		
		
		
		/**
		* Height in Pixel
		* 
		*
		* @see #autoScale 
		*/
		public var noAutoScaleHeight:Number;

		
		
		
		
		
		/**
		* Constructor
		*
		* @param url 		URL of the preview file
		* @param autoSc 	autoScale the size of the preview (true/false)
		* @param w 			Width (noAutoScaleWidth)
		* @param h 			Height  (noAutoScaleHeight)
		*/
		public function Preview (url:String, autoSc:Boolean, w:Number, h:Number){
			
			// save parameters
			autoScale = autoSc;
			noAutoScaleWidth = w;
			noAutoScaleHeight = h;
					
			// init objects
			request  = new URLRequest(url);
			loader = new Loader();
			this.loadedComplete = false;
			
			// eventListeners
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loadComplete);
			loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, fileNotFound);
			
			// start loading
			loader.load(request);
			
			// add to stage
			this.addChild(loader);
		}
		

		/**
		* This function is called, after the preview file is loaded
		*
     	* @param event event from eventlistener
     	*	
    	*/		
		private function loadComplete(event:Event):void {
		    //trace("Preview complete");	
			this.loadedComplete = true;
			setWidthAndHeight();
		}
		
		
		
		/**
		  * Set Width and Height
		  * autoscale == true --> don't do anything
		  * autoscale == false --> set width and height
		  *	
		  */		
		private function setWidthAndHeight():void {
			
		    if (autoScale == false) {
				this.width = noAutoScaleWidth;
				this.height = noAutoScaleHeight;
			}

		}
		
		
		/**
		  * Preview file not found.
		  *	Dispatch event.
		  *
		  */		
		private function fileNotFound(e:Event):void {
			trace ("error: file not found");
			dispatchEvent(new Event("fileNotFound"));
		}
		
		
	}
}