package org.FLVPlayer.unittest {
 	import asunit.framework.TestSuite;
 
 	public class AllTests extends TestSuite {
  		public function AllTests() {
   	 		super();
   			addTest(new ParamTest("testInstantiated"));

			addTest(new ParamTest("testPlayerPath"));
			addTest(new ParamTest("testButtonOverlay"));
			addTest(new ParamTest("testContentPath"));
			addTest(new ParamTest("testCaptions"));				
			addTest(new ParamTest("testEnding"));				
			addTest(new ParamTest("testPreloader"));	
			addTest(new ParamTest("testPreRoll"));						
			addTest(new ParamTest("testSkin"));	
			addTest(new ParamTest("testPreview"));							
			addTest(new ParamTest("testVolume"));
			addTest(new ParamTest("testVideo"));
   	 	}
  	}
}