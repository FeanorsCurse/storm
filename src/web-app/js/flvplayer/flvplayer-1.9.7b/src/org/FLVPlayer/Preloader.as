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
	  * This class loads an external preloader (SWF file).
	  * @version 1.9.7
	  */
	
	public class Preloader extends MovieClip {
		
		/**
		 * 
		 */
		private var request:URLRequest;
		
		/**
		 *
		 */
		private var loader:Loader;

			
		/**
		  * The constructor 
		  * loads the external SWF file that should be used as preloader.
		  *
		  * @param url Preloader file (.swf) in the "preloaders" folder of the FLV Player. 
		  *
		  */
		public function Preloader(url:String){
			
			// init objects
			request  = new URLRequest(url);
			loader = new Loader();
			
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
    	*/
		private function loadComplete(event:Event):void {
		    //trace("Preloader Complete");
		}
		
		
		
		/**
		  * Preloader swf file not found.
		  *	Dispatch an event.
		  *
		  */		
		private function fileNotFound(e:Event):void {
			trace ("error: file not found");
			dispatchEvent(new Event("fileNotFound"));
		}
		
		
	}
}