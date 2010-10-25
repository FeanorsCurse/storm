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
 
package ReportEditorMain.ReportEditor

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;

import java.util.ArrayList;
import java.util.Iterator;

import ReportEditorMain.ReportEditor.ReportService
import ReportEditorMain.ReportEditor.Report

import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArticleService
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.Enum.ReportStatus
import ReportEditorMain.Enum.ArticleStatus

import systemadministration.usermanagement.User
import systemadministration.modulmanagement.Module
import systemadministration.modulmanagement.ModuleService
import systemadministration.log.LogService

import shemaEditor.shemaAdministration.TReport
import shemaEditor.shemaAdministration.TNode
import shemaEditor.indicatorAdministration.Indicator
import validator.ValidatorResult;

/**
 * Controller for creating, editing, updating and deleting Reports 
 * 
 * @author Irene, Gerrit
 *
 */
class ReportController {
	
	//include ArticleService, reportService
	def articleService
	def reportService
	def pdfExportService
	
	//Redirection to list
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * Shows a List of all reports
	 */
	def list = {
		//Read Filter-Fields
		def searchfor = params.searchfor
		if(searchfor == null){
			searchfor = ""
		}
		def author = params.author
		if(author=="1"||author==null){
			author=""
		}
		def scheme = params.scheme
		if(scheme=="1"||scheme==null){
			scheme=""
		}
		def status = params.status
		if(status=="1"||status==null){
			status=""
		}
		
		def indikator = params.indikator
		if(indikator=="1"||indikator==null){
			indikator=""
		}
		
		//Searchable Plugin
		ArrayList searchResults = null;
		def tilde=false
		if(searchfor!=""){
			searchResults = Report.searchEvery(searchfor, reload:true)
			
			if(!searchResults){
				try{
					searchResults = Report.searchEvery("*"+searchfor+"*", reload:true)
				}catch (Exception e){
				}
			}
			
			if(!searchResults){//2. Suche ein like, d.h. ~string~
				def queryItems = new String(searchfor)
				queryItems.split(" ").each{
					searchResults = Report.searchEvery(it+"~0.5", reload:true)
				}
				if(searchResults){
					tilde=true
					flash.message = "${message(code: 'ee.search.did.you.mean', default: 'Did you mean:')}"
				}
			}
		}
		
		def reportList = Report.list()
		ArrayList<Report> filteredReportList = new ArrayList<Report>();		
		for (Iterator iterator = reportList.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			//if(report.name.toLowerCase().matches(".*"+searchfor.toLowerCase()+".*")
			if((searchResults==null||searchResults.contains(report))
			&&(author=="" ||report.author.username==User.findByUsername(author).username)
			&&((status==""&&report.status.status!="Closed") ||report.status.status==status)
			&&(scheme=="" ||report.treport==TReport.findByName(scheme))){
				filteredReportList.add(report)
			}
		}
		if(filteredReportList.size()==0){
			flash.message="${message(code: 'report.list.search.no.results', default:'No results')}"
		}
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[tilde: tilde,searchResults:searchResults,reportInstanceList: filteredReportList, reportInstanceTotal: Report.count()]		
	}
	
	/**
	 * controller for searchable plugin
	 */
	def searchFilter = { 
		params.reportSearch = params.reportSearch.trim()
		if(!params.reportSearch.equals(" ")) params.reportSearch = params.reportSearch.trim()
		if(!params.reportSearch){
			render (view: "list", model:[reportInstanceList: Report.list(params)])
		}
		
		def tilde=false
		if(params.reportSearch && !params.reportSearch.isEmpty()){
			
			def searchResults = Report.searchEvery(params.reportSearch, reload:true)
			if(searchResults){
				flash.message = null
				render(view:"list", model:[reportInstanceList:searchResults])	
			}
			
			if(!searchResults){
				searchResults = Report.searchEvery("*"+params.reportSearch+"*", reload:true)
			}
			
			if(!searchResults){
				def queryItems = new String(params.reportSearch)
				queryItems.split(" ").each{
					searchResults = Report.searchEvery(it+"~0.5", reload:true)
				}
				if(searchResults){
					tilde=true
				}
				flash.message = "${message(code: 'report.search.results')}"
			}
			render(view:"list", model:[reportInstanceList: searchResults, tilde:tilde])	
		}
	}	
	
	/**
	 * Create a new Report
	 */
	def create = {
		def reportInstance = new Report()
		reportInstance.properties = params
		
		return [reportInstance: reportInstance]
	}
	
	/**
	 * Export Report to PDF
	 */
	def toPdf={
		def reportInstance = Report.get(params.id)
		
		def root = servletContext.getRealPath(grailsApplication.config.service.pdfExport.reportPdfRoot)
		
		
		def url =pdfExportService.export(reportInstance,root)
		
		redirect(uri: url)	
	}
	
	/**
	 * Export Report to XML
	 */
	def xml={
		if(!params.id){
			render ("Missing param: ID")
		}else{
			def reportInstance = Report.get(params.id)
			
			if(!reportInstance){
				render ("Wrong ID")
				return
			}
			
			def articleTreeList = articleService.buildTree(reportInstance)
			//def ReportInstance = articleService.getReport(ArticleInstance)
			
			def builder = new StreamingMarkupBuilder()
			def Report = builder.bind {
				data{
					title reportInstance.name
					articleList(size:articleTreeList.size()){		
						articleTreeList.each{	
							def art = it
							
							article(author_firstname:art.author.firstname,author_lastname:art.author.lastname){
								name art.number+" "+art.name
								content art.content.toString().replaceAll("\\<.*?>","").decodeHTML()
							}
						}
					}
				}
			}		
			render XmlUtil.serialize(Report)
		}
	}
	
	/**
	 * Saves a new report after creating
	 */
	def save = {
		//create dummy-root article for every report
		def root = new Article(name:"Root").save()
		
		//create reportInstance from params
		params.rootArticle=root
		params.author=session.user
		params.status=ReportStatus.findByStatus("New")
		
		//create report instance from params
		def reportInstance = new Report(params)
		if(params.treportid){
			reportInstance.treport = TReport.findById(params.treportid)
			
			reportInstance = reportInstance.merge()
			if (reportInstance.save(flush: true)) {
				//Import Scheme
				def tReport = reportInstance.treport
				def rootNode = tReport.root
				reportService.importNode(rootNode, root, session.user)
				
				params.author=null;
				params.status=null;
				
				flash.message = "${message(code: 'default.created.message', args: [message(code: 'report.label', default: 'Report'), reportInstance.name])}"
				redirect(action: "list")
			}
			else {
				render(view: "create", model: [reportInstance: reportInstance])
			}
		}else{
			flash.message = "${message(code: 'report.create.scheme.missing', default:'Please select a scheme')}"
			render(view: "create", model: [reportInstance: reportInstance])
		}
	}
	
	/**
	 * Action exit. Redirect to list
	 */
	def cancel={
		def reportInstance = Report.get(params.id)
		
		redirect(action: "list")
	}
	
	/*
	 * Validates a report against a given schema
	 */
	def validatorService
	def validateReport={
		def ReportInstance
		if(params.articleId){
			ReportInstance = articleService.getReport(Article.get(params.articleId))
		}else{
			ReportInstance = Report.get(params.id)
		}
		
		def TReportInstance = ReportInstance.treport
		
		ValidatorResult vr = validatorService.singleValidate(ReportInstance, TReportInstance)
		if(params.articleId){
			render(view:"../TReport/validateArticle", model:[validatorResult:vr])
		}else{
			render(view:"../TReport/validateReport", model:[validatorResult:vr])	
		}
	}
	
	
	/**
	 * sets status of an reports to "approved"
	 */
	def approve={
		def reportInstance = Report.get(params.id)
		
		if (reportInstance&&(reportInstance.status.status=='Development'||reportInstance.status.status=='New')) {		
			if (reportService.isReportApproveable(reportInstance)){
				reportInstance.status = ReportStatus.findByStatus("Approved");
				reportInstance.releasedDate = new Date()
				reportInstance.save(flush:true)
				
				flash.message = "${message(code: 'report.edit.approved.message', args: [params.name])}"
				redirect(action: "list")
			}else {
				flash.message = "${message(code: 'report.edit.not.approved.message', default:'Report could not be approved',args: [ params.name])}"
				redirect(action: "list")
			}
		} else {
			flash.message = "${message(code: 'report.edit.not.found.message', args: [params.name])}"
			redirect(action: "list")
		}
		[reportInstance:reportInstance]
	}
	
	/**
	 * sets status of an report to "development" if the report was closed before
	 */
	def open={
		def reportInstance = Report.get(params.id)
		if (reportInstance&&(reportInstance.status.status=='Approved'||reportInstance.status.status=='Closed')) {
			reportInstance.status = ReportStatus.findByStatus("Development");
			reportInstance.save(flush:true)
			
			flash.message = "${message(code: 'report.list.button.development', default:'Development',args: [ params.name])}"
			render(view: "edit", model: [reportInstance:reportInstance])
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			render(view: "edit", model: [reportInstance:reportInstance])
		}
		[reportInstance:reportInstance]
	}
	
	
	/**
	 * Show values of a Report
	 */
	def show = {
		def reportInstance = Report.get(params.id)
		if (!reportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action: "list")
		}
		else {
			[reportInstance: reportInstance]
		}
	}
	
	/**
	 * Show HTML-Representation of a Report for the frontend
	 */
	def display = {
		def reportInstance = Report.get(params.id)
		if (!reportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action: "list")
		}
		else {
			ArrayList<ArtInd> artIndList = new ArrayList<ArtInd>()
			
			for (Article article : articleService.buildTree(reportInstance)) {
				artIndList.addAll(ArtInd.findAllByArticle(article))
			}
			[reportInstance: reportInstance, artIndList:artIndList]
		}
	}
	
	/**
	 * sets status of an article to "closed"
	 */
	def edit = {
		def reportInstance = Report.get(params.id)
		if (!reportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action: "list")
		}
		else {
			return [reportInstance: reportInstance]
		}
	}
	
	/**
	 * Copies a Report 
	 */
	def copy = {
		def reportInstance = Report.get(params.id)
		if (!reportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action: "list")
		}
		else {			
			//copy root article
			def article = reportInstance.rootArticle
			def newRootArticle = new Article(name:article.name,content:article.content, status:article.status, author:session.user, parentArticle:article.parentArticle).save(flush:true)
			
			//copy children and indicators
			articleService.copyChildren (reportInstance.rootArticle, newRootArticle, session.user)
			
			//Copy Report
			def newReport = new Report(rootArticle:newRootArticle,author:session.user,language:reportInstance.language,status:ReportStatus.findByStatus("New"),name:"Copy of "+reportInstance.name,description:reportInstance.description);
			newReport.treport = TReport.findById(reportInstance.treport.id)
			newReport = newReport.merge()
			newReport.save(flush:true)
			
			flash.message = "${message(code: 'report.list.actions.copy', default:'Report copied')}"
			
			redirect(action: "list")
			[newReport:newReport]
		}
	}
	
	/**
	 * Update a Report
	 */
	def update = {
		def reportInstance = Report.get(params.id)
		if (reportInstance&&(reportInstance.status.status=='Development'||reportInstance.status.status=='New')) {
			if (params.version) {
				def version = params.version.toLong()
				if (reportInstance.version > version) {
					
					reportInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'report.label', default: 'Report')] as Object[], "Another user has updated this Report while you were editing")
					render(view: "edit", model: [reportInstance: reportInstance])
					return
				}
			}
			reportInstance.properties = params
			reportInstance.author = session.user
			reportInstance.status=ReportStatus.findByStatus("Development")
			
			if (!reportInstance.hasErrors() && reportInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'report.label', default: 'Report'), reportInstance.name])}"
				redirect(action: "list")
			}
			else {
				render(view: "edit", model: [reportInstance: reportInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action: "list")
		}
		[reportInstance:reportInstance]
	}
	
	/**
	 * sets status of a report to "closed"
	 */
	def close = {
		def reportInstance = Report.get(params.id)
		if (reportInstance&&(reportInstance.status.status=='Development'||reportInstance.status.status=='New')) {
			try {
				reportInstance.status = ReportStatus.findByStatus("Closed");
				reportInstance.author = session.user
				reportInstance.save(flush:true)
				
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
				redirect(action:"list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
				render(view: "edit", model: [reportInstance:reportInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.name])}"
			redirect(action:"list")
		}
		[reportInstance:reportInstance]
	}	
	
	/**
	 * Executes actions for one or more reports
	 */
	def actionSelect = {
		flash.message=""
		def action = params.actions
		
		if(action=="0"){
			//no action selected
			flash.message = "${message(code: 'report.list.action.not.selected', default:'No action selected')}"
		}
		if(action=="1"){
			//copy checked articles
			if(params.checkbox){
				String[] checkboxes = params.checkbox
				for (int i = 0; i < checkboxes.length; i++) {
					params.id = checkboxes[i]
					copy()
					flash.message += "${message(code: 'report.list.actions.copy2', default:'Selected reports copied')}<br/>"
				}
			}
		}
		if(action=="2"){
			//delete checked articles
			if(params.checkbox){
				String[] checkboxes = params.checkbox
				for (int i = 0; i < checkboxes.length; i++) {
					params.id = checkboxes[i]
					close()
					flash.message += "${message(code: 'report.list.actions.delete2', default:'Selected reports deleted')}<br/>"
					
				}
			}
		}
		if(action=="3"){
			//open checked Articles
			if(params.checkbox){
				String[] checkboxes = params.checkbox
				for (int i = 0; i < checkboxes.length; i++) {
					params.id = checkboxes[i]
					open()
					flash.message += "${message(code: 'report.list.actions.development2', default:'Selected reports opened')}<br/>"
					
				}
			}
		}
		redirect(action: "list")
	}
	
	/**
	 * AutoComplete for report.
	 */
	def autocomplete = {
		
		def resultList = Report.findAllByNameLike("${params.query}%")
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
}
