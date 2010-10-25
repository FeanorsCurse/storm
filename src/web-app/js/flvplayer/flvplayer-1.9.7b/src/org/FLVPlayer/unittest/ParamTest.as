package org.FLVPlayer.unittest {
	
 	import asunit.framework.TestCase;
 	import org.FLVPlayer.*;	
 		
 	public class ParamTest extends TestCase {
  		private var instance:Param;
  
  		/**
  		 * Constructor
  		 */
  		public function ParamTest(testMethod:String) {
   			super(testMethod);
   		}
  		
  		/**
  		 * Set up instance to test with
  		 */
  		protected override function setUp():void {
   			instance = new Param();
   		}
  		
  		/**
  		 * Delete instance used for testing
  		 */
  		protected override function tearDown():void {
   			instance=null;
   		}
  		
  		
  		/**
  		 * instantiation
  		 */
  		public function testInstantiated():void {
   			assertTrue("Example instantiated", instance is Param);
   		}
   		
   		public function testButtonOverlay():void {
   			assertTrue("default", instance.buttonOverlay == "buttonoverlays/" + Param.DEFAULT_BUTTON_OVERLAY_FILENAME);
   			instance.playerPath = "flvplayer";
   			assertTrue("playerpath + default", instance.buttonOverlay == "flvplayer/buttonoverlays/" + Param.DEFAULT_BUTTON_OVERLAY_FILENAME);
			instance.buttonOverlay = "custom.swf";
   			assertTrue("playerpath + default", instance.buttonOverlay == "flvplayer/buttonoverlays/" + "custom.swf");
   		}
  	
  		public function testPlayerPath():void {
  			instance.playerPath = "http://www.video-flash.de/flvplayer";
  			assertTrue("playerpath is http://www.video-flash.de/flvplayer", instance.playerPath == "http://www.video-flash.de/flvplayer/");  					   			  					
   			instance.playerPath = "flvplayer";
  			assertTrue("playerpath is flvplayer", instance.playerPath == "flvplayer/");  
   			instance.playerPath = "";
  			assertTrue("playerpath is flvplayer", instance.playerPath == Param.DEFAULT_PLAYER_PATH); 
   			instance.playerPath = null;
  			assertTrue("playerpath is flvplayer", instance.playerPath == Param.DEFAULT_PLAYER_PATH);   			   								   			  					
   		}
   		
  		public function testContentPath():void {
  			instance.contentPath = "http://www.video-flash.de/flvplayer/content";
  			assertTrue("contentpath is http://www.video-flash.de/flvplayer/content", instance.contentPath == "http://www.video-flash.de/flvplayer/content/");  					   			  					
   			instance.contentPath = "flvplayer";
  			assertTrue("contentpath is flvplayer", instance.contentPath == "flvplayer/");  
   			instance.contentPath = "";
  			assertTrue("contentpath is flvplayer", instance.contentPath == Param.DEFAULT_CONTENT_PATH); 
   			instance.contentPath = null;
  			assertTrue("contentpath is flvplayer", instance.contentPath == Param.DEFAULT_CONTENT_PATH);   			   								   			  					
   		}   
  
   		
  		public function testCaptions():void {
  			assertTrue("default", instance.captions == null);
  			instance.captions = "test.xml";
  			assertTrue("test.xml", instance.captions == "test.xml");
  		} 
   
   
   		public function testEnding():void {
   			assertTrue("default", instance.ending == "endings/" + Param.DEFAULT_ENDING_FILENAME);
   			instance.playerPath = "flvplayer";
   			assertTrue("playerpath + default", instance.ending == "flvplayer/endings/" + Param.DEFAULT_ENDING_FILENAME);
			instance.ending = "custom.swf";
   			assertTrue("playerpath + default", instance.ending == "flvplayer/endings/" + "custom.swf");
   		}   
   
  		
   		public function testPreloader():void {
   			assertTrue("default", instance.preloader == "preloaders/" + Param.DEFAULT_PRELOADER_FILENAME);
   			instance.playerPath = "flvplayer";
   			assertTrue("playerpath + default", instance.preloader == "flvplayer/preloaders/" + Param.DEFAULT_PRELOADER_FILENAME);
			instance.preloader = "custom.swf";
   			assertTrue("playerpath + default", instance.preloader == "flvplayer/preloaders/" + "custom.swf");
   		}  
   		
   		public function testPreRoll():void {
   			assertTrue("default", instance.preRoll == null);
   			instance.contentPath = "content";
			instance.preRoll = "custom.swf";
   			assertTrue("contentpath + custom.swf", instance.preRoll == "content/" + "custom.swf");
   		}      		 		
  		
  		
  		public function testPreview():void {
  			assertTrue("default", instance.preview == Param.DEFAULT_PREVIEW_FILENAME);
  			
  			instance.playerPath = "flvplayer";  			
  			instance.contentPath = "content";
  			assertTrue("playerpath + default", instance.preview == "flvplayer/" + Param.DEFAULT_PREVIEW_FILENAME);  
  			
  			instance.contentPath = "";
  			instance.preview = "test.jpg";			
  			assertTrue("test.jpg", instance.preview == "test.jpg");  	
  			
  			instance.contentPath = "content";
  			assertTrue("test.jpg", instance.preview == "content/test.jpg");  	  								
  		} 
  		   		
  
  		public function testSkin():void {
  			assertTrue("default", instance.skin == "skins/" + Param.DEFAULT_SKIN_FILENAME);
  			instance.playerPath = "flvplayer";
  			assertTrue("default", instance.skin == "flvplayer/skins/" + Param.DEFAULT_SKIN_FILENAME);
  			
  			instance.playerPath = "";  			  			
  			instance.skin = "myskin.swf";
  			assertTrue("myskin.swf", instance.skin == "skins/myskin.swf");
  			 
  			instance.playerPath = "flvplayer";
   			assertTrue("myskin.swf with playerpath", instance.skin == "flvplayer/skins/myskin.swf");  			
  		} 
   
		  				
  		public function testVolume():void {
    			instance.volume = 0.5;
    			assertTrue("volume 0.5", instance.volume == 0.5);
    			instance.volume = 0.2;
    			assertTrue("volume 0.2", instance.volume == 0.2);
    			instance.volume = 0;
    			assertTrue("volume 0", instance.volume == 0);    			    				
  		}   
  			
  			
  		public function testVideo():void {
  			assertTrue ("default", instance.video == null);
  			instance.video = "video.flv";
  			assertTrue ("video.flv", instance.video == "video.flv");
  			instance.contentPath = "content";
  			assertTrue ("contentPath + video.flv", instance.video == "content/video.flv");  			
  		}			
  		  
  	}
}