package org.FLVPlayer
{
	import flash.ui.Keyboard;
	import org.osflash.thunderbolt.Logger;
	
	/**
	 * 
	 * The KeyboardControl class is responsible for keyboard inputs.
	 * The FLV Player can be controlled with various keys (e.g. play/pause, seeking, etc.).
	 *  
	 *	
	 *	@version 1.9.7
	 */	
	
	public class KeyboardControl {
		
		/**
		* reference to the playback engine of the flv player
		*/
		private var myPlaybackEngine:PlaybackEngine;
		
					
		/**
		 *
		 */			
		public function KeyboardControl(pbEngine:PlaybackEngine) {
			this.myPlaybackEngine = pbEngine;
		}
		
		
		/**
		* This function evaluates the key pressed on the keyboard.
		* Depending on the key, the corresponding action will be started.
		* At the moment, the following are used: space bar (play/pause), right and left arrow (seek)
		* and up/down arrow (currently no action).
		* 
		* @param keyCode Value of the key that has been pressed.  
		*/
		public function evaluate(keyCode:uint):void {
			
			if (keyCode == Keyboard.SPACE) {			
				onSpaceBar();		
			}
			
			if (keyCode == Keyboard.LEFT) {
				onLeftRight(Keyboard.LEFT);				
			}	
			
			if (keyCode == Keyboard.RIGHT) {
				onLeftRight(Keyboard.RIGHT);			
			}
			
			if (keyCode == Keyboard.UP) {
				Logger.info ("Up arrow pressed - currently no action");		
			}
			
			if (keyCode == Keyboard.DOWN) {
				Logger.info ("Down arrow pressed - currently no action");		
			}
		}


	/**
	* This function is called when the spacebar is pressed. 
	* It plays or pauses the video.
	*/
	private function onSpaceBar():void {

				// if state is playing
				if (this.myPlaybackEngine.player.myFLVPlayback.playing == true) {
							this.myPlaybackEngine.player.myFLVPlayback.pause();
				} 
				// else if state is paused
				else if (this.myPlaybackEngine.player.myFLVPlayback.paused == true) {
							this.myPlaybackEngine.player.myFLVPlayback.play();
				}
				
				else if (this.myPlaybackEngine.player.myFLVPlayback.stopped == true) {
							this.myPlaybackEngine.player.myFLVPlayback.play();
				}
				
				Logger.info ("Spacebar pressed");
				
	}
	
	
	/**
	* This function is called when the spacebar is pressed. 
	* It plays or pauses the video.
	*/
	private function onLeftRight(key:uint):void {
		
		var currentTime:Number = myPlaybackEngine.player.myFLVPlayback.playheadTime;
		var seekInterval:Number = 5;
		
		// change direction for backward
		if (key == Keyboard.LEFT) {
			seekInterval *= -1;
		}
				
		// new time must > 0		
		var newPlayheadTime:Number = currentTime+seekInterval;
		if (newPlayheadTime < 0) newPlayheadTime = 0;

		myPlaybackEngine.player.myFLVPlayback.seek(newPlayheadTime);
		Logger.info ("Left/right arrow pressed");
	}	




  }
}