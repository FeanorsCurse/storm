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
 
package ReportEditorMain.ArticleEditor

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;

import java.io.File;
import java.net.URL;
import java.util.ArrayList
import java.util.Iterator

import org.codehaus.groovy.ast.expr.RegexExpression;
import org.springframework.web.util.HtmlUtils;

import ReportEditorMain.ReportEditor.Report
import ReportEditorMain.ArticleEditor.ArticleService
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.Enum.ReportStatus
import ReportEditorMain.Enum.ArticleStatus
import ReportEditorMain.MediaCenter.Media
import ReportEditorMain.MediaCenter.ViewID

import systemadministration.modulmanagement.Module
import systemadministration.usermanagement.User
import systemadministration.modulmanagement.ModuleService
import systemadministration.log.LogService
import systemadministration.recommender.ArticleToArticleCollaborativeFilteringService;

import shemaEditor.shemaAdministration.TReport
import shemaEditor.shemaAdministration.TNode
import shemaEditor.indicatorAdministration.Indicator

/**
 * Controller for creating, editing, updating and deleting Articles 
 * 
 * @author Irene, Gerrit
 *
 */
class ArticleController {
	
	//include ArticleService, reportService, mediaService
	def articleService
	def reportService
	def mediaService
	
	//pdfExport Service
	def pdfExportService
	
	//define allowed HTTP-Requests
	static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
	
	//Redirection to list
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * Copies an Article
	 */
	def copy = {
		def articleInstance = Article.get(params.id)
		//if report is approved, article cannot be edited
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.locked.report.approved', args: [message(code: 'article.label', default:'Article cannot be edited because Report is already approved'), articleInstance.name])}"
		}else{
			if (!articleInstance) {
				flash.message += "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}<br/>"
				redirect(action: "list")
			}else {
				//set copied article status to "new"
				def newArticleStatus = ArticleStatus.findByStatus("New")
				def newArticle = new Article(name:"Copy of "+articleInstance.name,content:articleInstance.content, status:newArticleStatus, author:session.user, parentArticle:articleInstance.parentArticle, sequence:articleInstance.sequence, tnode:articleInstance.tnode).save(flush:true)
				
				//copy indicators
				articleService.copyIndicators(articleInstance, newArticle, session.user)
				
				//copie children
				articleService.copyChildren(articleInstance, newArticle, session.user)
				
				flash.message += "${message(code: 'default.copy.message', args: [message(code: 'article.label', default: 'Article'), "'"+articleInstance.number+" "+articleInstance.name+"'"])}<br/>"
				
				//update used articles in media files
				def mediaInstanceList = Media.findAll();
				mediaService.updateArticles(newArticle, mediaInstanceList)
				flash.info += "${message(code: 'media.articles.updated', args: [message(code: 'media.label', default: 'Media'), newArticle.id])}"
				[newArticle:newArticle]
			}
		}
	}
	
	/**
	 * Shows a List of all Articles belonging to one specific Report (params.report) and filtered by a searchword and other filter-properties
	 */
	def list = {
		//Load Report from Session or params
		if(params.reportid != null){
			session.report = Report.get(params.reportid)
		}
		
		def reportName = session.report.name
		
		//Build TreeList
		def articleTreeList = articleService.buildTree(session.report)
		
		//Read Filter-Fields
		def searchfor = params.searchfor
		if(searchfor == null){
			searchfor = ""
		}else{
			searchfor= searchfor.trim();
		}
		def author = params.author
		if(author=="1"||author==null){
			author=""
		}
		def status = params.status
		if(status=="1"||status==null){
			status=""
		}
		
		//Searchable Plugin
		ArrayList<Article> searchResults = null;
		def tilde=false
		if(searchfor!=""){
			searchResults = Article.searchEvery(searchfor, reload:true)
			
			if(!searchResults){
				try{
					searchResults = Article.searchEvery("*"+searchfor+"*", reload:true)
				}catch (Exception e){
				}
			}
			
			if(!searchResults){//2. Suche ein like, d.h. ~string~
				def queryItems = new String(searchfor)
				queryItems.split(" ").each{
					searchResults = Article.searchEvery(it+"~0.5", reload:true)
				}
				if(searchResults){
					tilde=true
					flash.message = "${message(code: 'ee.search.did.you.mean', default: 'Did you mean:')}"
				}
			}
		}
		
		//Filter Articles
		def filteredArticleTreeList=new ArrayList();
		for (Iterator iterator = articleTreeList.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			//if(article.name.toLowerCase().matches(".*"+searchfor.toLowerCase()+".*")
			if((searchResults==null||searchResults.contains(article))
			&&(author=="" ||article.author.username==User.findByUsername(author).username)
			&&((status==""&&article.status.status!="Closed") ||article.status.status==status)){
				filteredArticleTreeList.add(article)
			}
		}	
		if(filteredArticleTreeList.size()==0){
			flash.message="${message(code: 'article.list.search.no.results', default:'No results')}"
		}	
		[reportName:reportName, searchResults:searchResults, articleInstanceList: filteredArticleTreeList, articleInstanceTotal: filteredArticleTreeList.count(),tilde: tilde] 
	}
	
	/**
	 * controller for searchable plugin
	 */
	def searchFilter = {
		try{
			params.articleSearch = params.articleSearch.trim()
			
			if(!params.articleSearch){
				render (view: "list", model:[articleInstanceList: Article.list(params)])
			}
			def tilde=false
			if(params.articleSearch && !params.articleSearch.isEmpty()){
				
				def searchResults = Article.searchEvery(params.articleSearch, reload:true)
				
				if(searchResults){
					flash.message = null
					render(view:"list", model:[articleInstanceList: searchResults])	
				}
				
				if(!searchResults){
					searchResults = Article.searchEvery("*"+params.articleSearch+"*", reload:true)
				}
				
				if(!searchResults){
					def queryItems = new String(params.articleSearch)
					queryItems.split(" ").each{
						searchResults = Article.searchEvery(it+"~0.5", reload:true)	
					}
					if(searchResults){
						tilde=true
					}
					flash.message = "${message(code: 'article.search.results', default:'Search results')}"
				}
				
				render(view:"list", model:[articleInstanceList: searchResults, tilde:tilde])	
			}
		}catch(Exception e){
			render(view:"list")	
		}
		
	}
	
	/**
	 * Create a new Article belonging to one specific Report
	 */
	def create = {	
		if(Report.get(session.report.id).status.status=="Approved"){
			flash.message = "${message(code: 'article.list.not.created', default: 'Article cannot be created because report is already released')}"
			redirect(action: "list")
		}else{
			def articleInstance = new Article()
			articleInstance.properties = params
			articleInstance.tags=params.tags?.split(",");
			
			def articleList = articleService.buildTree(session.report)
			articleList.add(0,session.report.rootArticle)
			
			return [articleInstance: articleInstance, articleInstanceList:articleList]
		}
	}
	
	/**
	 * Save one Article belonging to one specific Report after creating
	 */
	def save = {
		//Check if belonging Report has Status "Approved"
		int articleId = params.parentArticle.id
		Article parent = Article.get(params.parentArticle.id);
		
		if(articleService.getReport(parent).status.status=="Approved"){
			flash.message = "${message(code: 'article.list.not.created', default: 'Article cannot be created because report is already released')}"
			redirect(action: "list")
		}else{
			//set author to current user	
			params.author=session.user
			
			//set status to "new"
			params.status=ArticleStatus.findByStatus("New")
			
			//create new instance from params
			def articleInstance = new Article(params)
			
			if (articleInstance.save(flush: true)) {
				articleInstance.tags=params.tags?.split(",");
				
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
				
				//update used articles in media files
				def mediaInstanceList = Media.findAll();
				mediaService.updateArticles(articleInstance, mediaInstanceList)
				redirect(action: "list", id: articleInstance.id)
			}
			else {
				render(view: "create", model: [articleInstance: articleInstance])
			}
		}
	}
	
	/**
	 * Show all Informations to one Article
	 */
	def show = {
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
			redirect(action: "list")
		}
		else {
			//Find Report for this Article
			def articleReport = articleService.getReport(articleInstance)?.name
			[articleInstance: articleInstance, articleReport:articleReport]
		}
	}
	
	/**
	 * Shows rated stars for a specific article
	 */
	def starsrated = {
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
			redirect(action: "list")
		}
		else {
			//Find Report for this Article
			def articleReport = articleService.getReport(articleInstance)?.name
			[articleInstance: articleInstance, articleReport:articleReport]
		}
	}
	
	/**
	 * Show Contents of one Article belonging to one specific Report. Articles are only shown, if the article and the belonging report are approved
	 */
	def display = {
		def articleInstance = Article.get(params.id)
		System.out.print("Artikel"+articleInstance.name)
		if (!articleInstance) {
			flash.message = "${message(code: 'default.not.found.message')}"
			redirect(controller:"welcome", action: "welcomePage")
		}
		else if(articleInstance.name=="Root"||articleInstance.status.status!="Approved"||articleService.getReport(articleInstance).status.status!="Approved"){
			redirect(controller:"welcome", action: "welcomePage")
		}	
		else {
			//write accesslogfile
			LogService.writeAccessLog(session.user,ModuleService.initialiseModule(controllerName) , articleInstance, 1 )
			
			//Import indicators
			ArrayList<ArtInd> artIndList = new ArrayList<ArtInd>();
			for (ArtInd artInd : ArtInd.findAllByArticle(articleInstance)) {
				artIndList.add(artInd)
			}
			
			def parsedIndicators=articleService.parseForIndicator(articleInstance.content, articleInstance)
			
			ArrayList<Article> articleRecommendationList = new ArrayList<Article>();
			articleRecommendationList = ArticleToArticleCollaborativeFilteringService.getRecommendationsForArticleAndUser(articleInstance, session.user)
			[articleRecommendationList: articleRecommendationList, articleInstance: articleInstance, artIndList: artIndList,articleInstanceContent:parsedIndicators]	 
		}
	}
	
	/**
	 * Edit one Article belonging to one specific Report
	 */
	def edit = {
		//load article from session or params
		if(params.id != null){
			session.article = Article.get(params.id)
		}
		def articleInstance = session.article
		
		//if report is approved, article cannot be edited
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.locked.report.approved', default:'Article cannot be edited because Report is already approved')}"
			redirect(action: "list")
		}
		
		//check if article is locked
		if(articleService.articleNotLocked(articleInstance, session.user)){
			
			if (!articleInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
				redirect(action: "list")
			}
			else {
				def articleList = articleService.buildTree(session.report)
				
				//add root-node
				def report = session.report
				def rootArticle = report.rootArticle
				articleList.add(0, rootArticle)
				
				//delete this node
				articleList.remove(articleInstance)
				
				//delete child nodes
				for (Iterator iterator = articleService.listChildren(articleInstance).iterator(); iterator.hasNext();) {
					Article article = (Article) iterator.next();
					articleList.remove(article)
				}
				
				//find indicators for this article
				def indicatorList = ArtInd.findAllByArticle(articleInstance)
				
				//find Revisions for this article
				def articleInstanceRevisionList = Article.findAllByRevArticle(articleInstance,, [max:5, order:"desc", sort:"dateCreated",])
				
				return [articleInstance: articleInstance, articleInstanceList:articleList,articleInstanceRevisionList:articleInstanceRevisionList,indicatorList:indicatorList]
			}
		}else{
			flash.message = "${message(code: 'article.locked.message', args: ["\""+articleInstance.name+"\"", "\""+articleInstance.lockUser+"\""])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Update one Article belonging to one specific Report
	 */
	def update = {
		def articleInstance = Article.get(params.id)
		
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.list.not.updated', default: 'Article cannot be updated because report is already released')}"
			redirect(action: "list")
		}else{
			if (articleInstance) {
				if (params.version) {
					def version = params.version.toLong()
					if (articleInstance.version > version) {
						articleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'article.label', default: 'Article')] as Object[], "Another user has updated this Article while you were editing")
						render(view: "edit", model: [articleInstance: articleInstance])
						return
					}
				}	
				
				//create revision of this article
				def revisionArticle = new Article();
				revisionArticle.name=articleInstance.name
				revisionArticle.content=articleInstance.content
				revisionArticle.status=ArticleStatus.findByStatus("Revision")
				revisionArticle.author=articleInstance.author
				revisionArticle.sequence=articleInstance.sequence
				revisionArticle.revArticle=articleInstance	
				revisionArticle.save();
				
				//update used articles in media files
				def mediaInstanceList = Media.findAll();
				mediaService.updateArticles(revisionArticle, mediaInstanceList)
				//flash.info = "${message(code: 'media.revision.updated', args: [message(code: 'media.label', default: 'Media'), revisionArticle.id])}"
				
				//update articleInstance
				articleInstance.properties = params
				articleInstance.status=ArticleStatus.findByStatus("Development")
				articleInstance.author = session.user
				articleInstance.tags=params.tags?.split(",");
				
				if (!articleInstance.hasErrors() && articleInstance.save(flush:true)) {
					articleService.freeArticleLock(articleInstance)
					
					//update used articles in media files
					mediaService.updateArticles(articleInstance, mediaInstanceList)
					//flash.info += " ${message(code: 'media.articles.updated', args: [message(code: 'media.label', default: 'Media'), articleInstance.id])}"
					
					flash.info = " ${message(code: 'default.updated.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
					
					//redirect(action: "edit")
					redirect(action: "edit", id: articleInstance.id)
				}
				else {
					redirect(action: "edit", id: articleInstance.id)
				}
				[articleInstance: articleInstance]
			}
			else {
				articleService.freeArticleLock(articleInstance)
				
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
				redirect(action: "list")
			}
		}
	}
	
	/**
	 * Action exit, cancel action, redirect to list
	 */
	def cancel={
		def articleInstance = Article.get(params.id)
		
		articleService.freeArticleLock(articleInstance)
		redirect(action: "list")
	}
	
	/**
	 * sets status of an article to "approved" if the article was publish before
	 */
	def approve={
		def articleInstance = Article.get(params.id)
		
		//if report is approved, article cannot be edited
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.locked.report.approved', args: [message(code: 'article.label', default:'Article cannot be edited because Report is already approved'), articleInstance.name])}"
		}else if (articleInstance&&(articleInstance.status.status=='Development'||articleInstance.status.status=='New')) {
			//check if Children are approved
			//			if(articleService.childrenApproved(articleInstance)){
			articleInstance.status = ArticleStatus.findByStatus("Approved");
			articleInstance.save(flush:true)
			
			
			flash.message = "${message(code: 'article.approved.message', default: 'Article approved')}"
			redirect(action: "list")
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}"
			redirect(action: "list")
		}
		[articleInstance:articleInstance]
	}
	
	/**
	 * shows a preview of the edited article
	 */
	def preview={
		def articleInstance = Article.get(params.id)
		if (articleInstance) {
			articleInstance.properties = params
			articleInstance.save(flush:true)
			
			//update used articles in media files
			def mediaInstanceList = Media.findAll();
			mediaService.updateArticles(articleInstance, mediaInstanceList)
			flash.info = "${message(code: 'media.articles.updated', args: [message(code: 'media.label', default: 'Media'), params.id])}"
			
			//Import indicators
			ArrayList<ArtInd> artIndList = new ArrayList<ArtInd>();
			for (ArtInd artInd : ArtInd.findAllByArticle(articleInstance)) {
				artIndList.add(artInd)
			}
			
			def parsedIndicators=articleService.parseForIndicator(articleInstance.content, articleInstance)
			
			[articleInstance: articleInstance, artIndList: artIndList,articleInstanceContent:parsedIndicators]	 
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * sets status of an article to "development" if the article was closed before
	 */
	def open={
		def articleInstance = Article.get(params.id)
		
		//if report is approved, article cannot be edited
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.locked.report.approved', args: [message(code: 'article.label', default:'Article cannot be edited because Report is already approved'), articleInstance.name])}"
		}else if (articleInstance) {
			articleInstance.status = ArticleStatus.findByStatus("Development");
			articleInstance.author = session.user
			articleInstance.save(flush:true)
			
			articleService.openParents(articleInstance, session.user)
			
			flash.message += "${message(code: 'article.list.button.development', default:'Status set to Development')}<br/>"
		}
		else {
			flash.message += "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}<br/>"
		}
		[articleInstance:articleInstance]
	}
	
	/**
	 * sets status of an article to "closed"
	 */
	def close = {
		def articleInstance = Article.get(params.id)
		
		//if report is approved, article cannot be edited
		if(articleService.getReport(articleInstance).status.status=="Approved"){
			flash.message = "${message(code: 'article.locked.report.approved', args: [message(code: 'article.label', default:'Article cannot be edited because Report is already approved'), articleInstance.name])}"
		}else if (articleInstance) {
			try {
				articleInstance.status = ArticleStatus.findByStatus("Closed");
				articleInstance.author = session.user
				articleInstance.save(flush:true)
				
				articleService.closeChildren(articleInstance, session.user)
				
				flash.message += "${message(code: 'default.deleted.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}<br/>"
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message += "${message(code: 'default.not.deleted.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}<br/>"
			}
		}
		else {
			flash.message += "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), articleInstance.name])}<br/>"
		}
		[articleInstance:articleInstance]
	}
	
	/**
	 * This method is called, when a user loads a revision from the edit-article function
	 */
	def revision = {
		//find revision
		def articleRevision = Article.get(params.radio)	
		def articleInstance = articleRevision.revArticle
		
		//load articleRevision
		articleInstance.name= articleRevision.name
		articleInstance.content= articleRevision.content
		articleInstance.status= articleRevision.status
		articleInstance.author= articleRevision.author
		articleInstance.sequence= articleRevision.sequence
		
		flash.message = "${message(code: 'article.revision.loaded', default:'Revision loaded', args:[articleRevision.dateCreated])}"
		
		redirect(action: "edit", id: articleInstance.id)
	}
	
	
	/**
	 * Generates and shows a Preview of an Article as PDF
	 * @author Sebastian van Vliet
	 */
	def pdfPreview={	
		
		if(!params.id)
		render("Missing param: article_ID")
		
		String rootPath =servletContext.getRealPath("/nachhaltigkeitsberichte/preview/")
		
		//export
		pdfExportService.export(Article.get(params.id),rootPath)		
		
		//redirect to preview
		redirect(action:pdf, params:[id:params.id, prev:1])
	}
	
	/**
	 * Shows an article as PDF 
	 * @author Sebastian van Vliet
	 */
	def pdf={
		
		def rootPath
		
		def ArticleInstance = Article.get(params.id)
		
		//if(params.prev=="1"){
		//	rootPath="/nachhaltigkeitsberichte/preview/"+ReportInstance.name.toLowerCase()+"/"+ArticleInstance.name.toLowerCase()+"/"+ArticleInstance.name+"_"+ArticleInstance.id+".pdf"
		//}else{
		//	rootPath="/nachhaltigkeitsberichte/released/"+ReportInstance.name.toLowerCase()+"/"+ArticleInstance.name.toLowerCase()+"/"+ArticleInstance.name+"_"+ArticleInstance.id+".pdf"		
		//}		
		
		
		def root = servletContext.getRealPath(grailsApplication.config.service.pdfExport.articlePdfRoot)
		
		def url = pdfExportService.export(ArticleInstance, root)		
		
		redirect(uri: url)	
	}
	
	/**
	 * Returns a xml of the identified  Article
	 * Note: The xml is limited to the most important fields to generate a pdf
	 * @author Sebastian van Vliet
	 */
	def xml={
		
		if(!params.id){
			render ("Missing param: ID")
		}else{
			def ArticleInstance = Article.get(params.id)
			
			if(!ArticleInstance){
				render ("Wrong ID")
				return
			}
			
			def ReportInstance = articleService.getReport(ArticleInstance)
			String contentOut  = ArticleInstance.content.toString().replaceAll("\\<.*?>","")
			
			def builder = new StreamingMarkupBuilder()
			def Article = builder.bind {
				data{
					report ReportInstance.name
					name ArticleInstance.name
					content contentOut.decodeHTML()
					author (firstname:ArticleInstance.getAuthor().firstname, lastname:ArticleInstance.getAuthor().lastname)
				}
			}		
			render XmlUtil.serialize(Article)
		}
	}
	
	/**
	 * Returns a html of the identified  Article
	 * Note: Only for testing (xm/html)
	 * @author Sebastian van Vliet
	 */
	def html={
		
		if(!params.id){
			render ("Missing param: ID")
		}else{
			def ArticleInstance = Article.get(params.id)
			def ReportInstance = articleService.getReport(ArticleInstance)
			
			def writer = new StringWriter()  
			def builder = new groovy.xml.MarkupBuilder(writer) 
			builder.html(){ 
				head(){ 
					title(ReportInstance.name+" - "+ArticleInstance.name){
					} 
				} 
				body(){
					p(ArticleInstance.getAuthor().firstname+" "+ArticleInstance.getAuthor().lastname)
					
					h1{
						p(ArticleInstance.name)
					}
					HtmlUtils.htmlUnescape(ArticleInstance.content.toString())
					
				} 
			} 
			
			render writer.toString() 
			
		}
	}
	
	/**
	 * Executes actions for one or more indicators
	 */
	def actionSelect = {
		flash.message =""
		def action = params.actions
		if(action=="0"){
			//no action selected
			flash.message += "${message(code: 'article.list.action.not.selected', default:'No action selected')}"
		}else{
			
			//read checkboxes
			String[] articles = null;
			if(params.checkbox instanceof String){
				articles = new String[1]
				articles[0] = params.checkbox
			}else{
				articles = params.checkbox
			}
			
			if(articles==null){
				//no articles selected
				flash.message += "${message(code: 'article.list.articles.not.selected', default:'No Articles selected')}"	
			}else{	
				
				if(action=="1"){
					//copy checked articles
					for (int i = 0; i < articles.length; i++) {
						params.id = articles[i]
						copy()
					}
				}
				if(action=="2"){
					//delete checked articles
					for (int i = 0; i < articles.length; i++) {
						params.id = articles[i]
						close()
					}
				}
				if(action=="3"){
					//open checked Articles
					for (int i = 0; i < articles.length; i++) {
						params.id = articles[i]
						open()
					}
				}
			}
		}
		
		redirect(action: "list")
	}
	
	
	
	/**
	 * Add Tag for an article
	 * @author="Christian Wenke"
	 */
	def addTag = {
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])}"
			redirect(action: "display", id: articleInstance.id)
		}
		else {
			articleInstance.addTag(params.tag)
			redirect(action: "display", id: articleInstance.id)
		}
	}
	
	/**
	 * Add multiple Tags for an article
	 * @author="Christian Wenke"
	 */
	def addTags = {
		def articleInstance = Article.get(params.id)
		if (!articleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'article.label', default: 'Article'), params.id])}"
			redirect(action: "display", id: articleInstance.id)
		}
		else {
			articleInstance.addTags(params.tags.split(","))
			redirect(action: "display", id: articleInstance.id)
		}
	}
	
	/**
	 * Find articles by tag
	 * @author="Christian Wenke"
	 */
	def findByTag = {
		def tag = URLDecoder.decode(params.tag)
		def articleList = Article.findAllByTag(tag)
		
		[tag: tag, articleInstanceList: articleList, articleInstanceTotal: articleList.count()]
	}
	
	
	/**
	 * autoComplete for article.
	 */
	def autocomplete = {
		
		def resultList = Article.findAllByNameLike("${params.query}%")
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
	 *  Search Article
	 */
	def searchArticle = {
		params.article = params.article.trim()
		
		if(!params.article) {
			render (view: "searcharticle", model:[articleInstanceList: Article.list(params)])
		}
		def tilde=false
		
		if(params.article && !params.article.isEmpty()) {
			def searchResults = Article.searchEvery(params.article, reload:true)
			
			if(searchResults) {
				flash.message = null
				render(view:"searcharticle", model:[articleInstanceList: searchResults])
			}
			
			if(!searchResults) {
				searchResults = Article.searchEvery("*"+params.article+"*", reload:true)
			}
			
			if(!searchResults) {
				
				def queryItems = new String(params.article)
				queryItems.split(" ").each{
					searchResults = Article.searchEvery(it+"~0.5", reload:true)
				}
				
				if(searchResults) {
					tilde=true
				}
				flash.message = "${message(code: 'article.search.results', default:'Search results')}"
			}
			
			render(view:"searcharticle", model:[articleInstanceList: searchResults, tilde:tilde])
		}
	}
	
	/**
	 * removes the specified media from the specified view
	 * @author: Olaf
	 */
	def removeFromView = {
		def mediaInstance = Media.findById(params.id)
		ViewID view = ViewID.findByView(params.view)
		mediaInstance.removeFromViews(view)
		redirect(action: "list")
	}
}
