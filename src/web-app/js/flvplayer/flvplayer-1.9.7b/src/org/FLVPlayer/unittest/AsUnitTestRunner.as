package org.FLVPlayer.unittest {
	import asunit.textui.TestRunner;
	
	public class AsUnitTestRunner extends TestRunner {

		public function AsUnitTestRunner() {
			start(AllTests, null, TestRunner.SHOW_TRACE);
		}
	}
}
