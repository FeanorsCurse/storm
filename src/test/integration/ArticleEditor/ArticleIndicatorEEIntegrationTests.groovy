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
 * Integration Tests for ArticleIndicatorController
 * 
 * @author: Irene, Gerrit
 * @since: 10.06.2010
 */
package ArticleEditor

import java.util.ArrayList
import java.util.Iterator

import systemadministration.usermanagement.User;
import ReportEditorMain.ReportEditor.Report;
import ReportEditorMain.ArticleEditor.ArticleIndicatorController
import shemaEditor.shemaAdministration.TReport;
import ReportEditorMain.ReportEditor.ReportService
import ReportEditorMain.ArticleEditor.ArticleService
import ReportEditorMain.ArticleEditor.ArticleIndicatorService
import ReportEditorMain.ArticleEditor.Article;
import ReportEditorMain.ArticleEditor.ArtInd;
import ReportEditorMain.ArticleEditor.SapService
import ReportEditorMain.Enum.ReportStatus;
import ReportEditorMain.Enum.ArticleStatus;

import grails.test.*

/**
 * Integration Tests for the ArticleIndicator Controller-Actions
 * 
 * @author: Irene, Gerrit
 */
class ArticleIndicatorEEIntegrationTests extends GrailsUnitTestCase {
	
	// Define Services
	def reportService
	def articleService   
	def articleIndicatorService
	def sapService
	
	protected void setUp() {
		super.setUp()
	}
	
	protected void tearDown() {
		super.tearDown()
	}
	
	
	/**
	 * test redirection to list
	 */
	void testIndexArticleIndicator(){
		def controller = new ArticleIndicatorController()
		controller.index()
		assertEquals "/articleIndicator/list", controller.response.redirectedUrl
	}
	
	/**
	 * test size of indicator list for one report
	 */
	void testListArticleIndicatorReport(){
		def controller = new ArticleIndicatorController()
		controller.articleService = articleService
		
		controller.params.reportid=1
		
		def model = controller.list()
		
		assertEquals "Size of Report 1", 9, model.articleIndicatorInstanceList.size()
	} 
	
	/**
	 * test size of indicator list for one article
	 */
	void testListArticleIndicatorArticle(){
		def controller = new ArticleIndicatorController()
		controller.articleService = articleService
		
		controller.params.articleid=6
		
		def model = controller.list()
		
		assertEquals "Size of Article 6", 3, model.articleIndicatorInstanceList.size()
	} 
	
	/**
	 * test size of allocatable indicators for one articl
	 */
	void testAllocateArticleIndicator(){
		def controller= new ArticleIndicatorController()
		controller.articleIndicatorService = articleIndicatorService
		
		controller.params.id=6
		
		def model = controller.allocate()
		
		assertNotNull "Article must exist", model.articleInstance
		assertEquals "Number of Indocators",7, model.indicatorInstanceList.size()
	}
	
	/**
	 * test allocation of a indicator to an article
	 */
	void testUpdateAllocationArticleIndicator(){
		def controller= new ArticleIndicatorController()
		controller.articleIndicatorService = articleIndicatorService
		
		//get old indicator size
		int oldSize = ArtInd.findAllByArticle(Article.get(6)).size()
		
		//create params
		String[] array = new String[3]
		array[0]="1"     
		array[1]="2"  
		array[2]="3"  
		controller.params.checkbox=array
		controller.params.id=6
		controller.session.user=User.findByUsername("admin")
		
		def model = controller.updateAllocation()
		
		//get new indicator size
		int newSize = ArtInd.findAllByArticle(model.articleInstance).size()
		assertEquals "Number of Indocators",oldSize+3, newSize
	}
	
	/**
	 * test if an articleIndicator exists
	 * test if sap connection exists
	 */
	void testEditArticleIndicator(){
		def controller= new ArticleIndicatorController()
		controller.articleIndicatorService = articleIndicatorService	
		controller.sapService = sapService	
		
		//create params
		controller.params.id=6
		
		def model = controller.edit()
		
		assertNotNull "Article must exist", model.artIndInstance
		assertNotNull "Sap Value must exist", model.sapvalue
	}
	
	/**
	 * test if value of an indicator can be set
	 */
	void testUpdateArticleIndicator(){
		def controller= new ArticleIndicatorController()
		controller.articleIndicatorService = articleIndicatorService	
		
		//create params
		controller.params.id=6
		controller.params.value="123"
		controller.session.user=User.findByUsername("admin")
		controller.session.article=Article.get(6)
		
		def model = controller.update()
		
		assertNotNull "ArticleIndicator must exist", model.artIndInstance
		assertEquals "Value must have changed", 123, model.artIndInstance.value
	}
	
	
	/**
	 * test redirection to edit
	 */
	void testCancelArticleIndicator(){
		def controller = new ArticleIndicatorController()
		
		controller.session.article=Article.get(6)
		
		controller.cancel()
		
		assertEquals "/article/edit/6", controller.response.redirectedUrl
	}
	
}
