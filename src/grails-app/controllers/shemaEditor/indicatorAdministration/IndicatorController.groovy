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
 
package shemaEditor.indicatorAdministration
import java.util.List;

import shemaEditor.chart.Chart;

import shemaEditor.shemaAdministration.TNode;
import shemaEditor.shemaAdministration.TReport;
import systemadministration.usermanagement.User;

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;

import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import ReportEditorMain.ArticleEditor.*;
import shemaEditor.indicatorAdministration.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder

import ReportEditorMain.ReportEditor.ReportService;
import ReportEditorMain.ReportEditor.Report;

class IndicatorController {
	
	def articleService	
	def reportService	
	def chartService
	def TReportService
	def indicatorService
	
	static allowedMethods = [save: "POST", update: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * shows a list of all Indicators.
	 */
	def list = {
		//IndicatorList as return value
		ArrayList<Indicator> indicatorList = new ArrayList<Indicator>();
		
		indicatorList=Indicator.list(params)
		
		//Return filtered list
		[indicatorInstanceList: indicatorList, indicatorInstanceTotal: indicatorList.count()]
		
	}
	
	/*
	 * deletes all selected indicators from indicator-administration
	 */
	def deleteSelectedInd = {
		def selectedInd = request.getParameterValues('indicatorcheck').collect{it.toLong()
		}
		
		selectedInd.each{
			def indicatorInstance = Indicator.get(it)
			
			if(indicatorInstance){
				indicatorInstance.defaultChart=null
			}
			if (!indicatorService.isIndicatorUsedInArticle(indicatorInstance) && indicatorInstance) {
				
				indicatorInstance.delete()
				flash.info = "${message(code: 'indicatorlist.deleted.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
			}else if(indicatorService.isIndicatorUsedInArticle(indicatorInstance) && indicatorInstance){
				flash.message = "${message(code: 'indicatorlist.not.deleted.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
			}
		}
		
		redirect(action:"list")
	}
	
	
	//TODO
	//URLs sollen dynamisch ermittelt werden wo es sinnvoll ist und andernfalls über die config beschreibbar sein
	/**
	 * Returns the xml of a specific Indicator.
	 * query: http://localhost:8080/Storm/indicator/xml/1
	 */
	def xml ={
		
		def AppName ="Storm"
		def Source ="http://www.storm.uni-oldenburg.de/"
		
		if(!params.id){//Paramter format is null
			render "Missing param: ID"
		}else{
			
			def IndicatorInstance = Indicator.get(params.id)
			
			if(!IndicatorInstance){
				render ("Wrong ID")
				return
			}
			
			def builder = new StreamingMarkupBuilder()
			def Indicator = builder.bind {
				mkp.xmlDeclaration()
				mkp.declareNamespace(storm:'http://www.storm.uni-oldenburg.de/TNode',xsi:'http://www.w3.org/2001/XMLSchema-instance', schemaLocation:'http://www.storm.uni-oldenburg.de/TNode tnode.xsd') 
				indicator(id:IndicatorInstance.id, unit:IndicatorInstance.unit){
					source Source
					shortName IndicatorInstance.shortName
					name IndicatorInstance.name
					description IndicatorInstance.description
					compilation IndicatorInstance.compilation
					reference IndicatorInstance.reference
					relevance IndicatorInstance.relevance
					documentation IndicatorInstance.documentation
					definition IndicatorInstance.definition
					category{
						name IndicatorInstance.category.name
					}
					
				}
			}
			
			render XmlUtil.serialize(Indicator)
		}
		
	}
	
	
	
	
	def chart ={
		
		def notApprovedList = Report.list() 
		def reportList = Report.list()
		
		reportList.each{
			if(!reportService.isReportApproveable(it)){	
				notApprovedList.remove(it)
			}
		}
		reportList = notApprovedList
		
		
		def indicatorInstance = Indicator.get(params.id)
		
		if(indicatorInstance.defaultChart ==null){//TODO Message -> Auslagern
			render("<p>Für diesen Indikator steht kein Diagramm zur Verfügung.</p>")//TODO Message auslagern
			
		}else{
			
			def indicatorValueList = new ArrayList<Long>()
			
			def articleIndicators
			def articles
			
			reportList.each{
				articles = reportService.listArticles(it)
				
				articles.each{art->
					articleIndicators = articleService.listIndicators(art)
					
					if(articleIndicators.contains(indicatorInstance)){
						
						def artIndList = ArtInd.findAllByArticle(art)
						
						artIndList.each{
							if(it.indicator==indicatorInstance){
								indicatorValueList.add(it.value)								
								
							}
						}	
					}	
				}
			}
			def years=indicatorService.yearSelection(indicatorInstance)
			//Manipulate valuelist (show only selected years)	
			
			if(params.fromYear || params.toYear){
				
				int fromYear, toYear
				
				if(!params.fromYear || params.fromYear.equalsIgnoreCase("0")){
					fromYear=years.get(0)
				}else{
					fromYear =new Integer(params.fromYear).intValue()
				}
				
				if(!params.toYear || params.toYear.equalsIgnoreCase("0")){
					toYear=years.get(years.size()-1)
				}else{
					toYear = new Integer(params.toYear).intValue()
				}
				
				int fromIndex, toIndex
				
				for(int i=0;i<years.size();i++){
					if(years.get(i)==fromYear)
					fromIndex = i
					if(years.get(i)==toYear)
					toIndex = i
				}
				
				if(fromIndex!=toIndex){
					years = years.subList(fromIndex, toIndex+1)
					indicatorValueList = indicatorValueList.subList(fromIndex, toIndex+1)
				}
			}
			
			if(indicatorValueList.size()>1){
				def src = chartService.getChart(indicatorInstance.defaultChart, indicatorValueList, years, " ",indicatorInstance.unit)
				render(view: "chart", model:[indicatorInstance:indicatorInstance,chartEnabled: true, src:src, years:years])
				
			}else{
				
				render("<p>Das Diagramm kann nicht angezeigt werden, da dieser Indikator keine Vergleichswerte besitzt.</p>")//TODO Message auslagern
				
			}
		}
	}	
	
	//TODO prüfen ob indicator von Author angelegt werden soll. Wenn ja, model erweitern
	def create = {
		def indicatorInstance = new Indicator()
		indicatorInstance.properties = params
		
		if(params.author){
			return [indicatorInstance: indicatorInstance, author:true]
		}else{
			return [indicatorInstance: indicatorInstance]
		}
	}
	
	def importe = {
		return [indicatorInstanceList: Indicator.list(params), indicatorInstanceTotal: Indicator.count()]
	}
	
	
	
	/**
	 * Lists all Indicators available for the selected TNode, except those which are still part of the current Schema
	 */
	def listGlobalIndicators = {
		def results
		
		println("boboobo"+params)
		
		if(!params.cId){
			results = Indicator.list()
		}else{
			
			if(new Long(params.cId) == 0){
				results = Indicator.list()
			}else{
				results = Indicator.findAllByCategory(Category.get(new Long(params.cId)))				
			}
			
		}
		
		
		def TNodeInstance = TNode.get(params.snode)
		
		
		
		results.removeAll(TReportService.listSchemaIndicators(TReport.get(params.report)))
		
		
		render(view: "listGlobalIndicators", model:[globalList:results, snode:params.snode, report:params.report])
	}	
	
	
	
	
	/**
	 * Saves the given Indicator
	 */
	def save = {
		
		params.creationDate=new java.util.Date()
		params.lastModifiedDate=new java.util.Date()
		
		
		def indicatorInstance = new Indicator(params)
		
		if(!Category.get(indicatorInstance.category.id)){
			flash.message = "${message(code: 'indicator.create.nocategory')}"	
			render(view: "create", model: [indicatorInstance: indicatorInstance])
		}else{
			
			
			indicatorInstance.creator=session.user
			indicatorInstance.lastModifiedBy=session.user
			
			if(params.indicator == 'true'){
				indicatorInstance.indicator == true
				
				if(params.chart>0)
				indicatorInstance.defaultChart = Chart.get(params.chart)
				
			}else{
				indicatorInstance.indicator == false
				
			}
			
			if (indicatorInstance.save(flush: true)) {
				flash.info = "${message(code: 'default.created.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
				redirect(action: "show", id: indicatorInstance.id)
			}
			else {
				flash.message = "${message(code: 'indicator.not.created.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"	
				render(view: "create", model: [indicatorInstance: indicatorInstance])
			}
		}
		
	}
	
	
	
	/**
	 * Shows the Indicator identified by params.id
	 */
	def show = {
		
		def indicatorInstance = Indicator.get(params.id)
		def categoryInstance = indicatorInstance.category
		
		categoryInstance.indicators.remove(indicatorInstance)
		
		if (!indicatorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'indicator.label', default: 'Indicator'), params.id])}"
			redirect(action: "list")
		}
		else {		
			
			model:[indicatorInstance: indicatorInstance,categoryInstance:categoryInstance]
			
		}
	}
	
	
	//TODO delete
	def display= {
		def indicatorInstance = Indicator.get(params.id)
		if (!indicatorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'indicator.label', default: 'Indicator'), params.id])}"
			redirect(action: "list")
		}
		else {
			[indicatorInstance: indicatorInstance]
		}
	}
	
	/**
	 * Shows the details (short version) of an indicator which will be identified by params.indicatorId
	 */
	def showIndiDetails = {
		
		def indicatorInstance = Indicator.get(params['indicatorId'])
		if (!indicatorInstance) {
			render("")//TODO 
		}
		else {
			render(view:"showdetails", model:[report:params['report'], id:params.id, indicatorInstance:indicatorInstance])
		}
	}
	
	
	
	
	/**
	 * Updates an options box with a new result of indicators. 
	 * Query for all indicators which name starts with params.value
	 */
	def searchIndicator = {
		println(params)
		
		def indicators = Indicator.findAllByNameLike("${params.value}%")
		
		
		def TNodeInstance = TNode.get(params.id)
		
		indicators.removeAll(TNodeInstance.indicators)
		
		
		render(view: "listGlobalIndicators", model:[globalList:indicators, snode:params.id])
	}
	
	
	//TODO
	/**
	 * Import an Indicator (xml)
	 */
	def xmlImport ={
		
		if(!params.url){
			render "You have to spicify an indicator_id"
		}else{
			
			InputStream is = null; 
			
			
			URL url = new URL(params.url)
			url.
			is = url.openStream()
			String out = new Scanner( is ).useDelimiter( "\\Z" ).next()
			render out
			
		}
	}
	
	/**
	 * searchFilter for indicator-administration
	 * Searches for a given key (params.indicator) and shows the result
	 * First search is a exact search, second a wildcardsearch, third a relative (fuzzy) search
	 * 
	 */
	def searchableService
	def searchFilter = {
		def searchResults
		def tilde=false
		def indicatorFilter = params.indicator
		def category = params.category
		def creator = params.creator
		
		def CategoryInstance, UserInstance
		
		if(creator>'0'){
			UserInstance=User.findByUsername(creator)
		}
		if(category>'0'){
			CategoryInstance=Category.findByName(category)
		}
		indicatorFilter = indicatorFilter.trim()
		if(!indicatorFilter && category=='0' && creator=='0'){
			render (view: "list", model:[indicatorInstanceList: Indicator.list()])
		}else if(indicatorFilter.isEmpty() && (creator>'0' || category>'0')){
			
			def indicatorCategory, indicatorCreator
			
			if(CategoryInstance){
				indicatorCategory = Indicator.findAllByCategory(CategoryInstance)
			}
			if(UserInstance){
				indicatorCreator=Indicator.findAllByCreator(UserInstance)
			}
			
			if(CategoryInstance && UserInstance){
				indicatorCreator.retainAll(indicatorCategory)
				searchResults=indicatorCreator
			}else if(CategoryInstance && !UserInstance){
				searchResults=indicatorCategory
			}else if(!CategoryInstance && UserInstance){
				searchResults=indicatorCreator
			}
			if(searchResults){
				flash.message= null
			}
			
			render(view:"list", model:[indicatorInstanceList: searchResults])	
		}else if(!indicatorFilter.isEmpty()){
			searchResults = Indicator.searchEvery(indicatorFilter, reload:true)
			
			if(searchResults){
				render(view:"list", model:[indicatorInstanceList: searchResults])
			}
			
			if(!searchResults){
				searchResults = Indicator.searchEvery("*"+indicatorFilter+"*", reload:true)
			}
			
			if(!searchResults){
				def queryItems = new String(indicatorFilter)
				queryItems.split(" ").each{
					searchResults = Indicator.searchEvery(it+"~0.5", reload:true)
				}
				if(searchResults){
					flash.info = "${message(code: 'schema.search.results')}"
					tilde=true
				}
			}
			
			render(view:"list", model:[indicatorInstanceList: searchResults, tilde:tilde])	
		}
		
		
	}
	
	
	/**
	 * autoComplete for indicatorlist in indicator-administration
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
	 * The export action for the ExportPlugin
	 */
	def exportService
	def exportAllIndicators = {
		if(!params.max) 
		params.max = 10
		
		if(params?.format && params.format != "html"){
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=indicators.${params.format}")
			List fields = ["name", "shortName", "creationDate", "unit", "category", "lastModifiedDate", "creator", "lastModifiedBy"]
			Map labels = ["name": "Name", "shortName": "Shortname", "creationDate": "Creationdate", "value": "Value", "unit": "Unit", "category": "Category", "lastModifiedDate": "Lastmodifieddate", "creator": "Creator", "lastModifiedBy": "LastModifiedBy"]
			
			exportService.export(params.format, response.outputStream, Indicator.list(params), fields, labels, [:], [:])
		}
		
		[ TReportInstanceList: Indicator.list( params ) ] 
		
	}
	
	def edit = {
		
		
		def indicatorInstance = Indicator.get(params.id)
		if (!indicatorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'indicator.label', default: 'Indicator'), params.id])}"
			redirect(action: "list")
		}
		else {
			render (view: "edit", model: [indicatorInstance: indicatorInstance])
		}
	}
	
	/**
	 * Shows a RTE to edit one of the indicators definitions e.g. compilation
	 */
	def editDefinitions = {
		def indicatorInstance = Indicator.get(params.id)
		def section = params.section
		if (!indicatorInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'indicator.label', default: 'Indicator'), params.id])}"
			redirect(action: "list")
		}
		else {
			render (view: "editDefinitions", model: [indicatorInstance: indicatorInstance, section: section])
		}
	}
	
	def update = {
		
		def indicatorInstance = Indicator.get(params.id)
		
		indicatorInstance.lastModifiedBy=session.user
		if (indicatorInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (indicatorInstance.version > version) {		
					indicatorInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'indicator.label', default: 'Indicator')] as Object[], "Another user has updated this Indicator while you were editing")
					redirect (action: "show", id:params.id)
					
					return
				}
			}
			
			if(params.section){
				def section = params.section
				if(section=='1'){
					indicatorInstance.relevance=params.definitions1
					//indicatorDetails = indicatorInstance.relevance
				}
				if(section=='2'){
					indicatorInstance.compilation=params.definitions2
					//	indicatorDetails = indicatorInstance.compilation
				}
				if(section=='3'){
					indicatorInstance.documentation=params.definitions3
					//	indicatorDetails = indicatorInstance.documentation
				}
				if(section=='4'){
					indicatorInstance.reference=params.definitions4
					//	indicatorDetails = indicatorInstance.reference
				}
				if(section=='5'){
					indicatorInstance.definition=params.definitions5
					//	indicatorDetails = indicatorInstance.definition
				}
			}else{
				
				
				indicatorInstance.name=params.name
				
				
				if(params.defaultChart>0 && indicatorInstance.isIndicator()){
					indicatorInstance.defaultChart = Chart.get(params.defaultChart)
				}
				params.category = Category.get(params.category)
				params.defaultChart =  Chart.get(params.defaultChart)
				indicatorInstance.properties = params
				
				
			}
			if (!indicatorInstance.hasErrors() && indicatorInstance.save(flush: true)) {
				flash.info = "${message(code: 'indicator.update.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
				redirect (action: "show", id:params.id)
			}
			else {
				flash.message = "${message(code: 'indicator.not.update.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
				redirect (action: "show", id:params.id)
			}
		}
		else {
			flash.message = "${message(code: 'indicator.not.found.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def indicatorInstance = Indicator.get(params.id)
		
		
		if (!indicatorService.isIndicatorUsedInArticle(indicatorInstance)&& indicatorInstance) {
			
			indicatorInstance.defaultChart=null
			
			indicatorInstance.delete()
			flash.info = "${message(code: 'indicator.deleted.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
			redirect(action: "list")
			
		}else {
			
			println("###################################################11111")
			flash.message = "${message(code: 'indicator.not.deleted.message', args: [message(code: 'indicator.label', default: 'Indicator'), indicatorInstance.name])}"
			redirect(action: "show", id:indicatorInstance.id)
		}
	}
	
}
