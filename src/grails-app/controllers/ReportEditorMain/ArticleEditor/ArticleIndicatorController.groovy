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
 * Controller for indicator administration within articles
 * 
 * @author: Irene, Gerrit
 */
package ReportEditorMain.ArticleEditor

import java.util.ArrayList
import java.util.Iterator

import ReportEditorMain.ReportEditor.Report
import ReportEditorMain.ReportEditor.ReportService

import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.ArticleEditor.ArticleService
import ReportEditorMain.ArticleEditor.SapService
import ReportEditorMain.ArticleEditor.DatabaseService
import systemadministration.externalsystems.*;

import ReportEditorMain.ArticleEditor.ArticleIndicatorService

import shemaEditor.indicatorAdministration.Indicator

import systemadministration.usermanagement.User


/**
 * Controller for creating, editing, updating and deleting Indicators for a specific Article
 *
 * @author Irene, Gerrit
 *
 */
class ArticleIndicatorController {
	
	//include Services
	def reportService
	def articleService
	def articleIndicatorService
	def sapService
	def databaseService
	
	//define allowed methods
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
	
	/**
	 * redirect to list
	 */
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * shows a list of indicators for one report
	 * 
	 * @param articleIndicatorInstanceList: filtered List of all articles belonging to a specific report
	 */
	def list = {
		//Create empty list
		ArrayList<ArtInd> articleIndicatorList = new ArrayList<ArtInd>()
		
		def articleInstance = Article.get(params.id)
		
		//Read Filter-Fields
		def searchfor = params.searchfor
		if(searchfor == null){
			searchfor = ""
		}
		def articleFilter = params.article
		if(articleFilter=="1"||articleFilter==null){
			articleFilter=""
		}
		
		//load report from session or params
		if(params.reportid != null){
			Report reportInstance = Report.findById(params.reportid)
			session.report = Report.findById(params.reportid)
			session.article = null
		}
		//load article from session or params
		if(params.articleid != null){
			session.article = Article.findById(params.articleid)
		}
		
		//if report view, find all indicators for report, otherwise find all reports for article
		if(session.article==null && session.report!=null){
			for (Article article : articleService.buildTree(session.report)) {
				articleIndicatorList.addAll(ArtInd.findAllByArticle(article))
			}
			
		}
		if(session.article!=null){
			articleIndicatorList.addAll(ArtInd.findAllByArticle(session.article))
		}
		
		def filteredArticleIndicatorList = new ArrayList<ArtInd>();
		def articleFilterNames = new ArrayList<Article>();
		for (ArtInd articleIndicator : articleIndicatorList) {
			if(articleIndicator.indicator.name.toLowerCase().matches(".*"+searchfor.toLowerCase()+".*")
			&&(articleFilter==""||articleIndicator.article==Article.get(articleFilter))){
				filteredArticleIndicatorList.add(articleIndicator);
			}
			if(!articleFilterNames.contains(articleIndicator.article)){
				articleFilterNames.add(articleIndicator.article)
			}
		}
		if(filteredArticleIndicatorList.size()==0){
			flash.message="${message(code: 'article.list.search.no.results', default:'No results')}"
		}	
		[articleFilterNames:articleFilterNames,articleIndicatorInstanceList:filteredArticleIndicatorList]
	}
	
	/**
	 * gets for a specified article an indicator list for allocation view
	 */
	def allocate = {
		//Read Filter-Fields
		def searchfor = params.searchfor
		if(searchfor == null){
			searchfor = ""
		}
		
		//load article from session
		if(params.id != null){
			session.article = Article.findById(params.id)
		}
		def articleInstance = session.article
		
		//Filter Indicator-List
		def indicatorList = Indicator.list()
		def filteredIndicatorList = new ArrayList<Indicator>();
		for (Indicator indicator : indicatorList) {
			if(indicator.name.toLowerCase().matches(".*"+searchfor.toLowerCase()+".*")){
				filteredIndicatorList.add(indicator);
			}
		}
		[indicatorInstanceList:filteredIndicatorList,articleInstance:articleInstance]
	}		
	
	/**
	 * allocates one or more indicators which have been created before to a specified article
	 */
	def updateAllocation = {
		//Read selected Indicator from the formular parameters
		String[] indicators = null;
		if(params.checkbox instanceof String){
			indicators = new String[1]
			indicators[0] = params.checkbox
		}else{
			indicators = params.checkbox
		}
		
		Article articleInstance = Article.findById(params.id)
		User authorInstance = session.user
		
		for (int i = 0; i < indicators?.length; i++) {
			Indicator indicatorInstance = Indicator.findById(indicators[i])
			ArtInd articleIndicator = new ArtInd(article:articleInstance,indicator:indicatorInstance,author:authorInstance)
			articleIndicator.save(flush:true)
		}
		
		//Redirect to the Article-Edit view
		redirect(controller:"article", action: "edit", id:articleInstance.id)
		[articleInstance:articleInstance]
	}
	
	
	/**
	 * find the indicator for the view to be updated
	 */
	def edit = {
		def artIndInstance = ArtInd.get(params.id)
		
		//compute BAPI
		def sapvalue = sapService.computeBapi(artIndInstance.indicator.bapi)
		
		//compute SQl-Statement
		def databaseValue = databaseService.computeSQL(artIndInstance.indicator.sqlStatement)
		[artIndInstance:artIndInstance, sapvalue:sapvalue,databaseValue:databaseValue]
	}
	
	/**
	 * set the value for the specified indicator
	 */
	def update = {
		def artIndInstance = ArtInd.get(params.id)
		
		if (artIndInstance) {
			artIndInstance.properties = params
			artIndInstance.author=session.user
			
			if (!artIndInstance.hasErrors()  && artIndInstance.save(flush:true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'articleindicator.indicator', default: 'Indicator'), artIndInstance.id])}"
				redirect(controller: "article", action: "edit", id:session.article.id)
			}
			else {
				render(view: "edit", model: [artIndInstance: artIndInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleindicator.indicator', default: 'Indicator'), params.id])}"
			redirect(controller: "article", action: "edit", id:session.article.id)
		}
		[artIndInstance:artIndInstance]
	}	
	
	/**
	 * delete an indicator
	 */
	def delete = {
		def artInt = ArtInd.get(params.id)
		if (artInt) {
			try {
				artInt.delete()
				
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'articleindicator.indicator', default: 'Indicator'), params.id])}"
				redirect(controller: "article", action: "edit", id:session.article.id)
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'articleindicator.indicator', default: 'Indicator'), params.id])}"
				redirect(controller: "article", action: "edit", id:session.article.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleindicator.indicator', default: 'Indicator'), params.id])}"
			redirect(controller: "article", action: "edit", id:session.article.id)
		}
		[artInt:artInt]
	}
	
	/**
	 * cancel action
	 */
	def cancel = {
		redirect(controller: "article", action: "edit", id:session.article.id)
	}
	
	/**
	 * Redirection to create private Indicators
	 */
	def createPrivateIndicator = {
		redirect(controller: "indicator", action: "create", params:["private":true])
	}
	
	
	/**
	 * controller for searchable plugin
	 */
	def searchFilter = { 
		params.artIndSearch = params.artIndSearch.trim()
		if(!params.artIndSearch.equals(" ")) params.artIndSearch = params.artIndSearch.trim()
		if(!params.artIndSearch){
			render (view: "list", model:[artIndInstanceList: ArtInd.list(params)])
		}
		
		def tilde=false
		if(params.artIndSearch && !params.artIndSearch.isEmpty()){
			
			def searchResults = ArtInd.searchEvery(params.artIndSearch, reload:true)
			if(searchResults){
				flash.message = null
				render(view:"list", model:[artIndInstanceList:searchResults])	
			}
			
			if(!searchResults){
				searchResults = ArtInd.searchEvery("*"+params.artIndSearch+"*", reload:true)
			}
			
			if(!searchResults){
				def queryItems = new String(params.artIndSearch)
				queryItems.split("").each{
					searchResults = ArtInd.searchEvery(it+"~0.5", reload:true)
				}
				if(searchResults){
					tilde=true
				}
				flash.message = "${message(code: 'article.search.results', default:'Search results')}"
			}
			render(view:"list", model:[artIndInstanceList: searchResults, tilde:tilde])	
		}
	}	
	
	/**
	 * autoComplete for articleIndicator.
	 */
	def autocomplete = {
		
		def resultList = Indicator.findAllByNameLike("${params.query}%")
		render(contentType: "text/xml") {
			results() {
				resultList.each { word ->
					result(){
						name(word.name)
					}
				}
			}
		}
	}	
	
	
	/**
	 * Search Article Indicators
	 */
	def searchArticleIndicator = {
		params.articleIndicator = params.articleIndicator.trim()
		
		if(!params.articleIndicator) {
			render (view: "searcharticleIndicator", model:[articleIndicatorInstanceList: ArtInd.list(params)])
		}
		
		def tilde=false
		if(params.articleIndicator && !params.articleIndicator.isEmpty()) {
			def searchResults = ArticleIndicator.searchEvery(params.articleIndicator, reload:true)
			
			if(searchResults) {
				flash.message = null
				render(view:"searcharticleIndicator", model:[articleIndicatorInstanceList: searchResults])
			}
			
			if(!searchResults) {
				searchResults = ArticleIndicator.searchEvery("*"+params.articleIndicator+"*", reload:true)
			}
			
			if(!searchResults) {
				def queryItems = new String(params.articleIndicator)
				queryItems.split(" ").each{
					searchResults = ArticleIndicator.searchEvery(it+"~0.5", reload:true)
				}
				
				if(searchResults) {
					tilde=true
					flash.message = "${message(code: 'article.search.results', default:'Search results')}"
				}
				
				render(view:"searcharticleIndicator", model:[articleIndicatorInstanceList: searchResults, tilde:tilde])
			}
		}
	}
}
