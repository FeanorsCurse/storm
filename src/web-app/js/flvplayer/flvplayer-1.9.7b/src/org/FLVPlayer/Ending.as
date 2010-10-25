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
	import flash.geom.ColorTransform;

	
	/**
	* Class Ending
	* This class loads an ending file
	* 
	*/
	
	public class Ending extends MovieClip {
		
		/**
		* Request
		*/
		private var request:URLRequest;
		
		
		/**
		* Loader
		*/
		private var loader:Loader 					
		
		
		/**
		* indicates that everything is loaded
		*/		
		public var loadedComplete:Boolean;	
		
		/**
		* Pointer to the loaded Ending MovieClip
		*/
		public var content:MovieClip;				
		
		
		
		/**
		  * Constructor
		  * loads the external swf skinfile
		  * and adds event listener
		  *
		  * @param url    ending (.swf)
		  *
		  */
		
		
		public function Ending (url:String){
			
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
		  * loadComplete
		  * ending is complete
		  *
		  * 
		  *
		  * @param event   event from loader class 
		  *
		  */ 
		private function loadComplete(event:Event):void {
					
			this.loadedComplete = true;								
			this.content = loader.content as MovieClip;
			dispatchEvent(new Event(Event.COMPLETE));		
			
		}		
		
		
		/**
		  * Skin file with chrome not found.
		  *	Dispatch event.
		  *
		  */		
		private function fileNotFound(e:Event):void {
			dispatchEvent(new Event("fileNotFound"));
		}
		
	}
}