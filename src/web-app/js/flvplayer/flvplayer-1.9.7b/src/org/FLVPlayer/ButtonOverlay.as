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
	import org.FLVPlayer.*;
	
	/**
	  * The ButtonOverlay class loads an external swf file that contains a play symbol.
	  */
	
	public class ButtonOverlay extends MovieClip {


		/**
		* url request
		*/		
		private var request:URLRequest;

		
		/**
		* loader
		*/
		private var loader:Loader 



		/**
		* Indicates whether the ButtonOverlay ist loaded (==true) or not (==false)
		*/	
		public var loadedComplete:Boolean;


		
		/**
		* Pointer to the loaded ButtonOverlay MovieClip
		*/
		public var content:MovieClip;


	
		

		/**
		  * Constructor
		  * Loads the image
		  *	*
		  * @param url       URL of the ButtonOverlay swf file
		  *	
		  */				
		public function ButtonOverlay (url:String){
			
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
		  * Set loadedComplete to true and save the pointer to the loaded movieclip  
		  * @param event    event from eventlistener
		  */			
		private function loadComplete(event:Event):void {
			
		    //trace("ButtonOverlay complete");	
			this.loadedComplete = true;
			this.content = loader.content as MovieClip;

		}
		
		
		
		/**
		  * Button overlay swf file not found.
		  *	Dispatch an event.
		  *
		  */		
		private function fileNotFound(e:Event):void {
			trace ("error: file not found");
			dispatchEvent(new Event("fileNotFound"));
		}
		
		
	}
}