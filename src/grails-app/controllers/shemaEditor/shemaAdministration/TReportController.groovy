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
 
package shemaEditor.shemaAdministration
import systemadministration.usermanagement.*
import java.util.ArrayList
import java.util.Date;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import shemaEditor.shemaAdministration.TNode
import shemaEditor.indicatorAdministration.Indicator
import validator.ValidatorResult;
import winterwell.jtwitter.Twitter;

import ReportEditorMain.ArticleEditor.ArtInd;
import ReportEditorMain.ArticleEditor.Article;
import ReportEditorMain.ArticleEditor.ArticleService;
import ReportEditorMain.ReportEditor.Report;

import grails.converters.XML;
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;


class TReportController {
	
	static allowedMethods = [save: "POST", update: "POST"]
	
	def TReportService
	def TNodeIndicatorService
	
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[TReportInstanceList: TReport.list(params), TReportInstanceTotal: TReport.count()]
	}
	
	/*
	 * deletes all selected schemes from schema-administration
	 */
	def deleteSelectedTReport = {
		def selectedTReport =request.getParameterValues('treportcheck').collect{it.toLong()
		}
		selectedTReport.each{
			def treportInstance = TReport.get(it)
			
			if (!TReportService.isSchemaUsedInReport(treportInstance)&& treportInstance) {
				
				treportInstance.delete()
				flash.info = "${message(code: 'scheme.delete.message', args: [message(code: 'TReport.label', default: 'TReport'), treportInstance.name])}"
				
			}else if(TReportService.isSchemaUsedInReport(treportInstance)&& treportInstance){	
				
				flash.message = "${message(code: 'scheme.not.deleted.message', args: [message(code: 'TReport.label', default: 'TReport'), treportInstance.name])}"
			}else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), treportInstance.name])}"
			}
		}
		redirect(action: "list")
	}
	
	/**
	 * //searchFilter for scheme-administration
	 * Searches for a given key (params.schema) and shows the result
	 * First search is a exact search, second a wildcardsearch, third a relative (fuzzy) search
	 * 
	 */
	def searchableService
	def searchFilter = {
		def searchResults
		def tilde=false
		def schemaFilter = params.schema
		def creator = params.creator
		
		def UserInstance
		
		if(creator>'0'){
			UserInstance=User.findByUsername(creator)
		}
		schemaFilter = schemaFilter.trim()
		if(!schemaFilter && creator=='0'){
			render(view:"list", model:[TReportInstanceList: TReport.list()])	
		}else if(schemaFilter.isEmpty() && creator>'0'){
			
			def schemaCreator
			
			if(UserInstance){
				schemaCreator=TReport.findAllByCreator(UserInstance)
				searchResults=schemaCreator
			}
			render(view:"list", model:[TReportInstanceList: searchResults])	
		}else if(!schemaFilter.isEmpty()){
			searchResults = TReport.searchEvery(schemaFilter, reload:true)
			
			if(searchResults){
				render(view:"list", model:[TReportInstanceList: searchResults])	
			}
			
			if(!searchResults){
				searchResults = TReport.searchEvery("*"+schemaFilter+"*", reload:true)
			}
			
			if(!searchResults){
				def queryItems = new String(schemaFilter)
				queryItems.split(" ").each{
					searchResults = TReport.searchEvery(it+"~0.5", reload:true)					
				}
				if(searchResults){
					flash.info = "${message(code: 'schema.search.results')}"
					tilde=true
				}
			}	
			render(view:"list", model:[TReportInstanceList: searchResults, tilde:tilde])	
		}
		
	}
	
	
	/**
	 * autoComplete for schema.
	 */
	def autocomplete = {
		
		def resultList = TReport.findAllByNameLike("${params.query}%")
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
	 * Export function for the ExportPLugin
	 * Open a "save-dialogue" to save an exported domainclass to a file like *.pdf, *.csv,...
	 * Exports all TReports
	 */
	def exportService
	def exportAllSchemes = { 
		if(!params.max) 
		params.max = 10
		
		if(params?.format && params.format != "html"){
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=allSchemes.${params.format}")
			List fields = ["name", "lastModifiedBy", "lastModified", "creationDate",  "creator"]
			Map labels = ["name": "Name",  "lastModifiedBy": "LastModifiedBy", "lastModified": "LastModified", "creationDate": "CreationDate","creator": "Creator"]
			
			exportService.export(params.format, response.outputStream, TReport.list(params), fields, labels, [:], [:])
		}
		
		[ TReportInstanceList: TReport.list( params ) ] 
		
	}
	
	def validatorService
	def testValidate={
		
		def TReportInstance = TReport.get(1)
		def ReportInstance = Report.get(1)
		
		
		ValidatorResult vr = validatorService.singleValidate(ReportInstance, TReportInstance)
		
		
		render(view:"testValidate", model:[validatorResult:vr])		
	}
	
	//TODO wirft oft  fehler (sehr unterschiedlich, meist timeouts,.
	def twitterService
	def twitIt={
		
		
		def twitter = new Twitter("bennymoos","lodder198")
		
		if(twitter==null){
			render("incorrect login")
		}
		
		
		
		
		render("login correct"+twitter.getStatus)
		
		
		
		//OAuthSignpostClient oauthClient = new OAuthSignpostClient("pgstorm", "vlba-storm", "oob");
		//App registrieren!!!
		
		
		
	}
	
	
	/**
	 * Export function for the ExportPLugin
	 * Open a "save-dialogue" to save an exported domainclass to a file like *.pdf, *.csv,...
	 * Exports all Indicators of the identified by params.report
	 */
	def exportIndicatorsFromSchema = {
		if(!params.max) 
		params.max = 10
		
		if(params?.format && params.format != "html"){
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=indicatorsFromSchema.${params.format}")
			List fields = ["name", "shortName", "description", "creationDate", "unit", "category", "lastModifiedDate", "creator", "lastModifiedBy"]
			Map labels = ["name": "Name", "shortName": "Shortname", "description": "Description",, "creationDate": "Creationdate", "value": "Value", "unit": "Unit", "category": "Category", "lastModifiedDate": "Lastmodifieddate", "creator": "Creator", "lastModifiedBy": "LastModifiedBy"]
			
			exportService.export(params.format, response.outputStream, getAllSchemaIndicators(TReport.get(params.report).root,new ArrayList<Indicator>()), fields, labels, [:], [:])
		}
		
		[ TReportInstanceList: TReport.list( params ) ] 
		
	}
	
	
	//TODO delete
	def create = {
		def TReportInstance = new TReport()
		TReportInstance.properties = params
		return [TReportInstance: TReportInstance]
	}
	
	def save = {
		
		//Params which are not part of the create.gsp
		def CreationDate = new Date()
		
		def LastModifiedDate = new Date()
		def lastModifiedBy = session.user 
		def creator = session.user
		
		def TReportInstance = new TReport(params)
		TReportInstance.name = params["reportname"]
		
		//Check if a Schema with name params.name already exists
		def treportWithSameName = TReport.findByName(params.reportname)
		
		if(treportWithSameName){
			render(view: "create", model: [TReportInstance: TReportInstance])
			
		}
		
		def TNodeInstance = new TNode(params)
		
		TNodeInstance.name=params["name"]
		
		TNodeInstance.creationDate=CreationDate
		TNodeInstance.lastModified=LastModifiedDate
		TNodeInstance.lastModifiedBy=lastModifiedBy
		TNodeInstance.creator=creator
		TNodeInstance.setTitle(true)
		
		if( TNodeInstance.save(flush: true)){
			
			TReportInstance.creationDate=CreationDate
			TReportInstance.lastModified=LastModifiedDate
			TReportInstance.lastModifiedBy=lastModifiedBy
			TReportInstance.creator=creator
			TReportInstance.root=TNodeInstance
			
			if (TReportInstance.save(flush: true)) {
				
				redirect(action: "show", params:[id: TReportInstance.id])
				
			}
		}
		else {
			render(view: "create", model: [TReportInstance: TReportInstance])
		}
	}
	
	
	/**
	 * Returns a list of all Indicators related to the given tnode (the rootNode)
	 * @param parent the first TNode (rootNode)
	 * @param schemaIndicatorList an empty list
	 * @return List a List of Indicators
	 */
	def List getAllSchemaIndicators(TNode parent, List schemaIndicatorList){
		
		parent.children.each{
			schemaIndicatorList.addAll(it.listIndicators())
			getAllSchemaIndicators(it, schemaIndicatorList)		
		}
		
		return schemaIndicatorList
	}
	
	def show = {
		def TReportInstance = TReport.get(params.id)
		
		//collects indicators
		def indicatorList = getAllSchemaIndicators(TReportInstance.root, new ArrayList<Indicator>())
		
		//collects  nodestructure
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		TNode tnode = TNode.get(TReportInstance.root.id) //The rootNode
		
		
		//basicly html not xml
		toXml(tnode, xml, params.id)
		
		
		if (!TReportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), params.id])}"
			redirect(action: "list")
		}
		else {
			[TReportInstance: TReportInstance, "domTreeAsHtml":writer.toString(), indicatorList:indicatorList,importchildid:params.importchildid]
		}
	}
	
	def edit = {
		def TReportInstance = TReport.get(params.id)
		if (!TReportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), params.id])}"
			redirect(action: "show", params:params)
		}
		else {
			render(view:"edit", model:[TReportInstance:TReportInstance])
		}
	}
	
	/**
	 * Moves a tnode to another level of the tnode-structure. The tnode to move will be identified by params.selectedNode, the target node will be identified by params.targetNode
	 */
	def move = {
		
			
			
		if(!params['targetNode']){
			def srcNode =TNode.get(params.id)
			def allTnodes =TReportService.getAllNodesExceptOf(TReport.get(params.report).root,srcNode, new ArrayList<TNode>())	
	
			allTnodes.remove(srcNode)
			def rootNode =TReport.get(params.report).root
			allTnodes.add(rootNode)
			render(view:"moveNode", model:[report:params['report'], id:params.id, allNodes: allTnodes])
			
		}else{
			def selectedNode = TNode.get(params.selectedNode)
			
			def targetNode = TNode.get(params.targetNode)
			
			if(selectedNode.id ==targetNode.id){
				
			}
			
			def parentNode = TNode.get(selectedNode.parent_id)
			
			// remove selectedNode
			parentNode.children.remove(selectedNode)
			targetNode.children.add(selectedNode)
			selectedNode.parent_id = targetNode.id
			
			redirect(action: "show", report: params['report'], id:params.id)
		}
	}
	
	//	
	
	
	def listNodes = {
		
		def TReportIstance = TReport.get(params['rootId'])
		TNode rootNode = TReportIstance.root
		
		
		return [nodeList:TReportService.getAllNodes(rootNode, new ArrayList<TNode>())]
	}
	
	def importSchema = {
		
		if(!params.tnodeListSelection || !params.tnodeListSelection){
			
			render(view:"importSchema", model:[report:params['report'], id:params.id])
			//else import
		}else{
			def SelectedNode = TNode.get(params.selectedNode)	
			def TNodeInstance = TNode.get(params.tnodeListSelection)
			
			if(!SelectedNode ||!TNodeInstance){
				flash.message = "${message(code: 'choose.node.to.move')}"
				redirect(action: "show", id: params.report)
				return
			}
			
			def TNodeCopy = copyAll(TNodeInstance, TNodeInstance.copy())
			
			
			TNodeCopy.setParent_id(SelectedNode.id)
			
			SelectedNode.addToChildren(TNodeCopy)
			
			
			if(TNodeInstance.save(flush:true))
			redirect(action: "show", id: params.report, params:[importchildid:TNodeCopy.id])
		}
	}
	
	def undo ={
		println(params)
		def tnodeInstance = TNode.get(params.long('importchildid'))
		
		def parentNode = TNode.get(tnodeInstance.parent_id)
		
		println("TNode: "+tnodeInstance.id+" | Parent: "+parentNode.id)
		
		parentNode.children.remove(tnodeInstance)
		
		
		redirect(action: "show", id: params.report)
	}
	
	
	//TODO
	//URLs sollen dynamisch ermittelt werden wo es sinnvoll ist und andernfalls über die config beschreibbar sein
	/**
	 * Returns the xml of a specific TReport.
	 * query: http://localhost:8080/Storm/TReport/xml/1
	 */
	def xml ={
		
		
		def AppName ="Storm"
		def Source ="http://www.storm.uni-oldenburg.de/"
		
		if(!params.id)
		render ("Missing param: ID")
		
		if(params.id && !params.c){//Paramter format is null
			def TReportInstance = TReport.get(params.id)
			
			if(!TReportInstance){
				render ("Wrong ID")
				return
			}
			
			def builder = new StreamingMarkupBuilder()
			def TReport = builder.bind {
				mkp.xmlDeclaration()
				mkp.declareNamespace(storm:'http://www.storm.uni-oldenburg.de/TReport',xsi:'http://www.w3.org/2001/XMLSchema-instance', schemaLocation:'http://www.storm.uni-oldenburg.de/TReport treport.xsd') 
				treport{
					id TReportInstance.id
					source Source
					name TReportInstance.name
					description TReportInstance.description
					root(id:TReportInstance.root.id){
						name TReportInstance.root.name
						description TReportInstance.root.description
						children(count:TReportInstance.root.children.size()){
							TReportInstance.root.children.each{tn->
								tnode(query:"http://"+request.serverName+":"+request.serverPort+"/"+AppName+"/TNode/xml/"+tn.id){
									name tn.name
									
								}
							}
						}		
					}	
				}
			}
			
			render XmlUtil.serialize(TReport)
			
		}else if(params.id && params.c){
			
			def rootNode = TNode.get(params.id)
			
			def TReportInstance = TReport.get(params.id)
			
			if(!TReportInstance)
			render ("Wrong ID")
			
			def builder = new StreamingMarkupBuilder()
			def TReport = builder.bind {
				mkp.xmlDeclaration()
				mkp.declareNamespace(storm:'http://www.storm.uni-oldenburg.de/TReport',xsi:'http://www.w3.org/2001/XMLSchema-instance', schemaLocation:'http://www.storm.uni-oldenburg.de/TReport treport.xsd') 
				schema{
					treport(id:TReportInstance.id, source:Source, name:TReportInstance.name){
						description TReportInstance.description
					}
					node(id:TReportInstance.root.id, name:TReportInstance.root.name){
						description TReportInstance.root.description
						def clos ={n ->
							node n
							n.children.each{c->		
								
								this.clos(c)
								node c
							}
							
						}
						clos(TReportInstance.root)				
					}	
				}
			}
			
			
			render XmlUtil.serialize(TReport)
			
		}else{
			render "User Param f to specify the output format and param id to specify the TReport"
		}
		
		
	}
	
	
	
	/**
	 * Returns the full xml with all tnodes and indicators (max-detailed) of a specific TReport.
	 * query: http://localhost:8080/Storm/TReport/xml/1
	 */
	def fullReportAsXML(TNode tnode){
		children{
			name tnode.name
			children{
				tnode.children.each{  fullReportAsXML(xml, it) }
			}
		}
	}
	
	
	
	
	/**
	 * Generates an xml from a given tnode-structure	
	 * @param node the first node (root)
	 * @param xml (a MarkupBuilder)
	 * @param report the report which contains the node 
	 */
	public void toXml(TNode node, def xml,	def report) {
		def remoteLink ="${remoteFunction(controller:'TNode', action:'show',update:'detailsBox',params:[id:node.id, report:report])}"
		
		
		if(node.isTitle()){
			xml.p{
				a(href:"#", onClick:remoteLink+';return false'){k ' '+node.name }
			}		
			xml.ul(id:"treemenu", 'class':"treeview"){					
				node.children.each {
					toXml(it, xml, report)
				}
				
				
			}
		}else if(!node.getChildren().isEmpty()){
			
			xml.li{
				a(href:"#", onClick:remoteLink+';return false'){k node.name }
				xml.ul{	
					
					
					
					node.children.each {
						toXml(it, xml, report)
					}
				}
			}
		}else{
			xml.li{		
				
				a(href:"#", onClick:remoteLink+';return false'){k node.name }
				
				
				node.children.each {
					toXml(it, xml, report)
				}
			}
			
		}
		
	}
	
	
	
	
	//TODO delete
	def TNode copyAll(TNode tnode, TNode tnodeCopy){
		tnode.children.each{
			def copyNode = it.copy()	
			copyNode.parent_id =tnodeCopy.id
			tnodeCopy.addToChildren(copyNode)		
			copyAll(it, copyNode)
		}	
		return tnodeCopy
	}
	
	
	//TODO delete
	def editSchema ={
		
		
		def TReportInstance = TReport.get(params.report)//Current schema
		
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		
		TNode tnode = TNode.get(TReportInstance.root.id) //The rootNode
		
		
		//basicly html not xml
		toXml(tnode, xml, params.report)
		
		if (!TReportInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), params.id])}"
			redirect(action: "show")
		}
		else {
			return [TReportInstance: TReportInstance, "domTreeAsHtml":writer.toString()]
		}
	}
	
	
	
	
	def update = {
		def TReportInstance = TReport.get(params.id)
		TReportInstance.lastModified=new Date()
		TReportInstance.lastModifiedBy=session.user
		if (TReportInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (TReportInstance.version > version) {
					
					TReportInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'TReport.label', default: 'TReport')] as Object[], "Another user has updated this TReport while you were editing")
					render(view: "edit", model: [TReportInstance: TReportInstance])
					return
				}
			}
			TReportInstance.properties = params
			if (!TReportInstance.hasErrors() && TReportInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'TReport.label', default: 'TReport'), TReportInstance.id])}"
				redirect(action: "show", id: TReportInstance.id)
			}
			else {
				render(view: "edit", model: [TReportInstance: TReportInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), params.id])}"
			redirect(action: "list")
		}
	}
	/*
	 * deletes selected treport if schema is not used by a report
	 */
	def delete = {
		def TReportInstance = TReport.get(params.id)
		
		
		if (!TReportService.isSchemaUsedInReport(TReportInstance)&& TReportInstance) {
			
			TReportInstance.delete(flush: true)
			flash.info = "${message(code: 'scheme.delete.message', args: [message(code: 'TReport.label', default: 'TReport'), TReportInstance.name])}"
			redirect(action: "list")
			
		}else if(TReportService.isSchemaUsedInReport(TReportInstance)&& TReportInstance){	
			
			flash.message = "${message(code: 'scheme.not.deleted.message', args: [message(code: 'TReport.label', default: 'TReport'), TReportInstance.name])}"
			redirect(action: "show", id: params.id)
		}else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TReport.label', default: 'TReport'), TReportInstance.name])}"
			redirect(action: "list")
		}
		
	}
	
}

