/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer { 

	import flash.events.Event;

	
	/**
	* Class FLVPlayerResizeEvent
	* Event after resizing the preview picture or the player 
	* 
	*/
	
	public class FLVPlayerResizeEvent extends Event {
		
		/**
		* width
		*/
		public var width:Number;
		
		
		/**
		* height
		*/
		public var height:Number 					
		
	
		public static var RESIZE:String = "FLVPlayerResize";
			
		
		/**
		  * Constructor
		  *
		  *
		  */
		
		
		public function FLVPlayerResizeEvent(pType:String, pBubbles:Boolean, pCancelable:Boolean):void {
				super(pType, pBubbles, pCancelable);
		}
		


	}
}