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
 * Integration Tests for ArticleController
 * 
 * @author: Irene, Gerrit
 * @since: 10.06.2010
 */
package ArticleEditor

import java.util.ArrayList
import java.util.Iterator

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
 * Integration Tests for the ArticleEditor Controller-Actions
 * 
 * @author: Irene, Gerrit
 */
class ArticleEditorEEIntegrationTests extends GrailsUnitTestCase {
	def reportService
	def articleService   
	
	protected void setUp() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	void testIndexArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.index()
		assertEquals "/article/list", controller.response.redirectedUrl
	}
	
	void testListArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//set report to first report from bootstrap
		controller.params.reportid=1
		
		def model = controller.list()
		
		assertEquals "Filtered Article List for Report 1", 6, model.articleInstanceList.size()
	} 
	
	void testCreateArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//set report to first report from bootstrap
		controller.session.report=Report.get(1)
		
		def model = controller.create()
		assertNotNull "Article Instance must exist", model.articleInstance
	}   
	
	void testSaveArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//Number of articles before creating a new one article
		def numArtBefore = Article.list().size()
		
		//Create params for new article
		controller.session.user=User.findByUsername("admin")
		controller.params.name="ArticleXYZ"
		
		//create article
		def model = controller.save()
		
		assertEquals "Neuer Article erstellt", numArtBefore+1, Article.list().size()
		assertEquals "Namen des neuen artiles prüfen", "ArticleXYZ", Article.findByName("ArticleXYZ").name
	}
	
	void testEditArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//set Article ID to 1
		controller.params.id=1
		controller.session.report=Report.get(1)
		
		// Edit article
		def model = controller.edit()
		
		assertNotNull "Article can be edited", model.articleInstance
		assertNotNull "There is a Tree of Articles", model.articleInstanceList
	}
	
	void testCopyArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//Number of articles before copy a new one
		def numArtBefore = Article.list().size()
		
		//set params for copy-action
		controller.params.id= 1
		controller.session.user=User.findByUsername("admin")
		
		//copy article
		def model = controller.copy()
		
		assertEquals "One more article in article-list", 26, Article.list().size()
		assertEquals "User of copied article must be session.user", controller.session.user, model.newArticle.author
		assertEquals "Articles have the status 'New'", "New", model.newArticle.status.status
	}   
	
	void testShowArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.show()
		assertEquals "Article not found", "/article/list", controller.response.redirectedUrl
	}
	
	void testDisplayArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.display()
		assertEquals "Article not found", "/article/list", controller.response.redirectedUrl
	}
	
	
	void testApproveArticlePositive(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//create new article
		def newArticle = new Article(name:"newArticle", status:ArticleStatus.findByStatus("New")).save()
		
		//set params for approve-action
		controller.params.id=newArticle.id
		
		//approve article
		def model = controller.approve()
		
		//check results for no articles
		assertNotNull "new article created", model.articleInstance
		assertEquals "is article approved", ArticleStatus.findByStatus("Approved"), model.articleInstance.status
	}
	
	void testUpdateArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		controller.articleService = articleService
		
		//set params for update-action
		controller.params.id= 5
		controller.session.user=User.findByUsername("admin")
		controller.params.name ="NewArticleName"
		
		//update report
		def model = controller.update()
		
		//find revision
		def articleRevision = Article.findByRevArticle(model.articleInstance)
		
		assertEquals "Updated articles must have the status Development", "Development", model.articleInstance.status.status
		assertEquals "User of updated article must be session.user", "admin", model.articleInstance.author.username
		assertEquals "Name must be updated", "NewArticleName", model.articleInstance.name
		assertEquals "Revision must exist, name must be old name", "Nachhaltige Entwicklung und Universitäten",  articleRevision.name
		assertEquals "Revision must have status Revision", "Revision", articleRevision.status.status
	}    
	
	void testCloseArticle(){
		def controller = new ReportEditorMain.ArticleEditor.ArticleController()
		
		//set params for delete-action
		controller.params.id= 1
		controller.session.user=User.findByUsername("admin")
		
		//update report
		def model = controller.close()
		
		assertNotNull "Article must still exist", model.articleInstance
		assertEquals "Deleted article must have the status Closed", "Closed", model.articleInstance.status.status
		assertEquals "User of deleted article must be session.user", controller.session.user, model.articleInstance.author
	}     
}
