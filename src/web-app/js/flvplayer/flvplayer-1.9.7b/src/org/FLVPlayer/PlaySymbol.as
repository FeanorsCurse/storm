/*
FLV Flash Fullscreen Video Player 
Copyright (C) 2008, Florian Plag, www.video-flash.de

This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
*/

package org.FLVPlayer { 

	import flash.display.SimpleButton;

	/**
	  * Class PlaySymbol
	  * The PlaySymbol is displayed together with the preview image. It a button within the ButtonOverlay.
	  * The PlaySymbol loads and starts the video.
	  *	Note: The play button's instance number in the .fla file has to be "myPlaySymbol"
	  * @author "Florian Plag"
	  */
	
	public class PlaySymbol extends SimpleButton {


		/**
		* Constructor
		* Hides the PlaySymbol 
    	*/
		public function PlaySymbol(){
			this.visible = false;
		}



		/**
		  * This function centers the Button depending on the preview image.
		  * Then it shows the button (visible = true).
		  *
		  * @param w width of preview image
		  * @param h height of preview image
		  *
		  */
		
		public function centerButton(w:Number, h:Number):void {

			// center
			this.x = (w - this.width) / 2;
			this.y = (h - this.height) / 2;			
			
			// show Button
			this.visible = true;
		}
				
	}
}