/**
 * Copyright (c) 2009/2010, Very Large Business Applications, University Oldenburg. Specifically:
 * Daniel Süpke <suepke@gmx.de>
 * Andreas Solsbach <solsbach@wi-ol.de>
 * Benjamin Wagner vom Berg <wagnervomberg@wi-ol.de>
 * Prof. Dr. Jorge Marx Gomez <jorge.marx.gomez@uni-oldenburg.de>
 * Christian Wenke <cw81@cw81.de>
 * Desislava Dechkova <desislavamd@yahoo.com>
 * Edzard Fisch <edzard.fisch@googlemail.com>
 * Frank Gerken <frank.gerken@uni-oldenburg.de>
 * Gerrit Meerpohl <gerrit.meerpohl@uni-oldenburg.de>
 * Irene Fröhlich <froehlich.irene@web.de>
 * Kerem-Kazim Sezer <Kerem.Sezer@gmx.de>
 * Olaf Roeder <o.roeder@gmx.net>
 * Oliver Norkus <oliver.norkus@googlemail.com>
 * Rachid Lacheheb <rachid.lacheheb@mail.uni-oldenburg.de>
 * Sebastian van Vliet <sebastian.van.vliet@mail.uni-oldenburg.de>
 * Swetlana Lipnitskaya <swetlana.lipnitskaya@uni-oldenburg.de>
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
 
/**
 * Integration Tests for ReportController
 * 
 * @author: Irene, Gerrit
 * @since: 10.06.2010
 */
package ReportEditor


import systemadministration.usermanagement.User;
import ReportEditorMain.ReportEditor.Report;
import shemaEditor.shemaAdministration.TReport;
import ReportEditorMain.ReportEditor.ReportService
import ReportEditorMain.ArticleEditor.ArticleService
import ReportEditorMain.ArticleEditor.Article;
import ReportEditorMain.Enum.ReportStatus;
import ReportEditorMain.Enum.ArticleStatus;

import grails.test.*

/**
 * Integration Tests for the ReportEditor Controller-Actions
 * 
 * @author: Irene, Gerrit
 */
class ReportEditorEEIntegrationTests extends GrailsUnitTestCase {
	def reportService
	def articleService
	
	protected void setUp() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testIndexReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.index()
		assertEquals "/report/list", controller.response.redirectedUrl
	}
	
	void testListReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		def model = controller.list()
		assertEquals 3, model.reportInstanceList.size()
	}
	
	void testCreateReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		def model = controller.create()
		assertNotNull model.reportInstance
	}
	
	void testSaveReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.reportService = reportService
		
		//Number of reports before creating a new one
		def numRepBefore = Report.list().size()
		
		//Create params for new report
		controller.session.user=User.findByUsername("admin")
		controller.params.name="ReportXYZ"
		controller.params.treportid=1
		
		//create report
		def model = controller.save()
		
		assertEquals "Neuer Report erstellt", numRepBefore+1, Report.list().size()
		assertEquals "Namen des neuen Reports prüfen", "ReportXYZ", Report.findByName("ReportXYZ").name
		assertEquals "Prüfen, ob root Artikel angelegt wurde","Root",Report.findByName("ReportXYZ").rootArticle.name    	
		assertEquals "Prüfen, Schema-Root Node importiert wurde","GRI A",Article.findByParentArticle(Report.findByName("ReportXYZ").rootArticle).name
	}
	
	void testCancelReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.cancel()
		assertEquals "/report/list", controller.response.redirectedUrl
	}
	
	void testApproveReportPositive(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.reportService = reportService
		
		//create new report
		def rootArticle = new Article(name:"RootABC").save()
		def reportInstance = new Report(name:"ReportABC", status: ReportStatus.findByStatus("New"), rootArticle:rootArticle, treport:TReport.get(1)).save(flush:true);
		def newArticle = new Article(name:"newArticle", parentArticle:rootArticle, status:ArticleStatus.findByStatus("Approved")).save()
		
		//set params for approve-action
		controller.params.id= reportInstance.id
		
		//approve report
		def model = controller.approve()
		
		//check results for no articles
		assertNotNull "new report created", model.reportInstance
		assertEquals "is report approved", "Approved", model.reportInstance.status.status
	}
	
	void testApproveReportNegative(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.reportService = reportService
		
		//create new report
		def rootArticle = new Article(name:"RootABC").save()
		def reportInstance = new Report(name:"ReportABC", status: ReportStatus.findByStatus("New"), rootArticle:rootArticle, treport:TReport.get(1)).save(flush:true);
		def newArticle = new Article(name:"newArticle", parentArticle:rootArticle, status:ArticleStatus.findByStatus("New")).save()
		
		//set params for approve-action
		controller.params.id= reportInstance.id
		
		//approve report
		def model = controller.approve()
		
		//check results for no articles
		assertNotNull "new report created", reportInstance
		assertEquals "is report approved", ReportStatus.findByStatus("New"), reportInstance.status
	}
	
	void testShowReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.show()
		assertEquals "/report/list", controller.response.redirectedUrl
	}
	
	void testDisplayReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.display()
		assertEquals "/report/list", controller.response.redirectedUrl
	}
	
	void testCopyReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		controller.articleService = articleService
		
		//Number of reports before copy a new one
		def numRepBefore = Report.list().size()
		
		//first report from bootstrap
		def reportInstance = Report.findById(1)
		
		//set params for copy-action
		controller.params.id= reportInstance.id
		controller.session.user=User.findByUsername("admin")
		
		//copy report
		def model = controller.copy()
		
		assertEquals "One more report in report-list", numRepBefore+1, Report.list().size()
		assertEquals "User of copied report must be session.user", controller.session.user, model.newReport.author
		assertEquals "Reports have the same root Article-Names", reportInstance.rootArticle.name, model.newReport.rootArticle.name
		assertEquals "Reports have the status 'New'", "New", model.newReport.status.status
	}
	
	void testUpdateReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		
		//set params for copy-action
		controller.params.id= 1
		controller.session.user=User.findByUsername("admin")
		controller.params.name ="NewReportName"
		
		//update report
		def model = controller.update()
		
		assertEquals "Updated Reports must have the status Development", "Development", model.reportInstance.status.status
		assertEquals "User of updated report must be session.user", controller.session.user, model.reportInstance.author
		assertEquals "Name must be updated", controller.params.name, model.reportInstance.name
	}    
	
	void testCloseReport(){
		def controller = new ReportEditorMain.ReportEditor.ReportController()
		
		//set params for delete-action
		controller.params.id= 1
		controller.session.user=User.findByUsername("admin")
		
		//update report
		def model = controller.close()
		
		assertNotNull "Report must still exist", model.reportInstance
		assertEquals "Deletes Reports must have the status Closed", "Closed", model.reportInstance.status.status
		assertEquals "User of deleted report must be session.user", controller.session.user, model.reportInstance.author
	}  
}
