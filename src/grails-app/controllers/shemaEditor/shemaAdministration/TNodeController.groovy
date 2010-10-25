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

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import java.util.ArrayList;
import java.util.Collections;

import shemaEditor.indicatorAdministration.*

class TNodeController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def indicatorService
	def TNodeIndicatorService
	def exportService
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[TNodeInstanceList: TNode.list(params), TNodeInstanceTotal: TNode.count()]
	}
	
	def deleteConfirm={
		
		
		
		render(view:"delete", model:[report:params['report'], snode:params['id']])
	}
	
	
	
	//TODO delete
	/*Returns a copy of this TNode
	 */
	
	def TNode getClone(TNode tnode){
		TNode node = new TNode()
		node.name = tnode.name
		node.description = tnode.description
		node.creater = tnode.creator
		node.creationDate = tnode.creationDate
		node.parent_id = tnode.parent_id
		node
	}
	
	/**
	 * Export function for the ExportPLugin
	 * Open a "save-dialogue" to save an exported domainclass to a file like *.pdf, *.csv,...
	 * Exports all Indicators of the identified by selected node
	 */
	def exportIndicatorsFromNode = {
		
		if(!params.max) 
		params.max = 10
		def nodeInstance = TNode.get(params.node)
		if(params?.format && params.format != "html"){
			response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
			response.setHeader("Content-disposition", "attachment; filename=indicatorsFromNode.${params.format}")
			List fields = ["name", "shortName", "description", "creationDate", "unit", "category", "lastModifiedDate", "creator", "lastModifiedBy"]
			Map labels = ["name": "Name", "shortName": "Shortname", "description": "Description",, "creationDate": "Creationdate", "value": "Value", "unit": "Unit", "category": "Category", "lastModifiedDate": "Lastmodifieddate", "creator": "Creator", "lastModifiedBy": "LastModifiedBy"]
			
			exportService.export(params.format, response.outputStream, TNodeIndicatorService.getIndicators(nodeInstance), fields, labels, [:], [:])
		}
		
		[ TNodeInstanceList: TNode.list( params ) ] 
		
	}
	
	
	//TODO delete
	def create = {
		
		def TNodeInstance = new TNode()	
		TNodeInstance.setParent_id(new Long(params.snode))
		
		return [TNodeInstance: TNodeInstance, report:params.report]
	}
	
	
	def save = {
		
		
		def TNodeInstance = new TNode(params)
		//Sets date
		TNodeInstance.lastModified = new Date()
		TNodeInstance.creationDate = new Date()
		TNodeInstance.creator=session.user
		TNodeInstance.lastModifiedBy=session.user
		
		if(TNodeInstance.name.size() <3){
			flash.message = "${message(code: 'tnode.invalid.length')}"	
			redirect(controller:"TReport", action: "show", params:[id:params.report])
			return
		}
		
		if(TNodeInstance.description.isEmpty()){
			//report.create.description
			
			flash.message = "${message(code: 'default.blank.message', args:[message(code: 'report.create.description')])}"	
			redirect(controller:"TReport", action: "show", params:[id:params.report])
			return
		}
		
		//The parent node
		def parentNode = TNode.get(params.snode)
		
		parentNode.children.add(TNodeInstance)
		TNodeInstance.parent_id = parentNode.id
		
		if (TNodeInstance.save(flush: true)) {
			//store both in tnode_tnode (n:m relationship)
			redirect(controller:"TReport", action: "show", params:[id:params.report])
			
		}
		else {
			render(view: "create", model: [TNodeInstance: TNodeInstance])
		}
		
	}
	
	/**
	 * shows all indicators of a tnode, identified by params.snode (the id)
	 */
	def listNodeIndicator = {
		
		def TNodeInstance = TNode.get(new Long(params['snode']))
		def globalList = Indicator.list()
		
		def localList = TNodeInstance.listIndicators()
		
		globalList.removeAll(localList)
		
		render(view: "listNodeIndicator", model:[nodeIndicators:TNodeInstance.listIndicators(), globalList:globalList, snode:params['snode'], report:params.report, i_usage:params.i_usage])
	}
	
	/**
	 * Lists all Indicators of the TNode identified by id
	 */
	def listLocalIndicators = {
		def TNodeInstance = TNode.get(params.id)
		
		ArrayList<Boolean> isObligatoryList = new ArrayList<Boolean>()
		TNodeInstance.listIndicators().each{indicator->
			def obli = indicatorService.isIndicatorObligatory(TNodeInstance, indicator)		
			if(obli){
				isObligatoryList.add(true)
			}else{
				isObligatoryList.add(false)
			}
		}
		
		render(view: "listLocalIndicators", model:[nodeIndicators:TNodeInstance.listIndicators(), snode:params.id, isObligatoryList:isObligatoryList])
	}	
	
	//TODO macht  nicht was sie soll.
	/**
	 * Removes all indicaotrs from a TNode. The TNode will be identified by params.snode, indicaotrs to delete specified by params.nodeIndicatorList
	 */
	def deleteIndicator = {
		def TNodeInstance = TNode.get(new Long(params.snode))
		
		if(!params.nodeIndicatorList){
			
			flash.message = "${message(code: 'default.indicator.delete.fail.noselection')}"				
			
		}
		
		def nodeIndicatorList = request.getParameterValues('nodeIndicatorList').collect{it.toLong()
		}
		nodeIndicatorList.each{
			def IndicatorInstance =Indicator.get(it)
			TNodeIndicatorService.removeIndicator(TNodeInstance, IndicatorInstance)
		}
		
		flash.info = "${message(code: 'node.indicator.deletedmany', args: [TNodeInstance.name])}"
		
		redirect(action: "listNodeIndicator", params:[snode:params.snode, report:params.report])
	}
	
	//TODO
	//URLs sollen dynamisch ermittelt werden wo es sinnvoll ist und andernfalls über die config beschreibbar sein
	/**
	 * Returns the xml of an TNode, specified by id.
	 * query: http://localhost:8080/Storm/TNode/xml/1
	 */
	def xml ={
		
		def AppName ="Storm"
		def Source ="http://www.storm.uni-oldenburg.de/"
		
		if(!params.id){//Paramter format is null
			render ("Missing param: ID")
		}else{
			
			def TNodeInstance = TNode.get(params.id)
			if(!TNodeInstance){
				render ("Wrong ID")
				return
			}
			
			def builder = new StreamingMarkupBuilder()
			def TNode = builder.bind {
				mkp.xmlDeclaration()
				mkp.declareNamespace(storm:'http://www.storm.uni-oldenburg.de/TNode',xsi:'http://www.w3.org/2001/XMLSchema-instance', schemaLocation:'http://www.storm.uni-oldenburg.de/TNode tnode.xsd') 
				tnode(id:TNodeInstance.id){
					name TNodeInstance.name
					description TNodeInstance.description
					source Source
					children(count:TNodeInstance.children.size()){
						TNodeInstance.children.each{tn->
							tnode(id:tn.id, query:"http://"+request.serverName+":"+request.serverPort+"/"+AppName+"/TNode/xml/"+tn.id){
								name tn.name
							}
						}
					}
					indicators(count:TNodeInstance.listIndicators().size()){
						TNodeInstance.listIndicators().each{i->
							indicator(id:i.id, query:"http://"+request.serverName+":"+request.serverPort+"/"+AppName+"/indicator/xml/"+i.id){
								name i.name
							}
						}
					}
				}
			}
			
			render XmlUtil.serialize(TNode)
		}
		
	}
	
	
	//TODO falls die ebene die title-ebene ist, darf kein indikator hinzugefügtwerden können
	/**
	 * Adds each indcicator (identified by params.globalIndicatorList) to a given TNode (identified by params.snode)
	 */
	def addIndicator = {
		
		
		
		def TNodeInstance = TNode.get(new Long(params['snode']))
		
		def globalIndicatorList = request.getParameterValues('globalIndicatorList').collect{it.toLong()
		}
		
		globalIndicatorList.each{			
			def IndicatorInstance = Indicator.get(it)
			
			if(TNodeInstance.listIndicators().contains(IndicatorInstance)){
				flash.message = "${message(code: 'default.indicator.already.added', args: [IndicatorInstance.name, TNodeInstance.name])}"						
			}else{
				
				TNodeInstance.addToIndicators(IndicatorInstance, new Integer(params.i_usage).intValue())
				
				if(TNodeInstance.save(flush:true)){
					flash.info = "${message(code: 'default.indicator.addedmany', args: [TNodeInstance.name])}"
				}
			}
			
			
		}
		
		redirect(action: "listNodeIndicator", params:[snode:params.snode,report:params.report, i_usage:params.i_usage])
		
	}
	
	def updateIndicatorUsage = {
		
		
		
		def TNodeInstance = TNode.get(new Long(params['snode']))
		
		int i_usage = new Integer(params['i_usage']).intValue()
		
		def localIndicatorList = request.getParameterValues('localIndicatorList').collect{it.toLong()
		}
		
		params.localIndicatorList.each{
			
			
			def IndicatorInstance = Indicator.get(new Long(it))
			
			def c = TNodeIndi.createCriteria()
			def result = c {
				eq("indicator", IndicatorInstance)
				and { eq("tnode", TNodeInstance)
				}
			}
			
			result.each{ti->
				if(i_usage ==0|| i_usage==1){
					ti.i_usage = i_usage
					ti.save()
				}else if(i_usage==4){
					
				}else{
					ti.delete()			
				}
			}	
		}
		redirect(action: "listNodeIndicator", params:[snode:params.snode, report:params.report])
	}
	
	def show = {
		def TNodeInstance = TNode.get(params.id)
		if (!TNodeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TNode.label', default: 'TNode'), params.id])}"
			redirect(action: "list")
		}
		else {
			[TNodeInstance: TNodeInstance, report:params.report, indicatorList:TNodeInstance.listIndicators()]
		}
	}
	
	def edit = {
		def TNodeInstance = TNode.get(params.id)
		if (!TNodeInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TNode.label', default: 'TNode'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [TNodeInstance: TNodeInstance, "report":params['report']]
		}
	}
	
	def update = {
		
		def TNodeInstance = TNode.get(params.id)
		TNodeInstance.lastModified = new Date()
		TNodeInstance.lastModifiedBy=session.user
		if (TNodeInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (TNodeInstance.version > version) {
					
					TNodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'TNode.label', default: 'TNode')] as Object[], "Another user has updated this TNode while you were editing")
					render(view: "edit", model: [TNodeInstance: TNodeInstance, report:params['report']])
					return
				}
			}
			TNodeInstance.properties = params
			
			if (!TNodeInstance.hasErrors() && TNodeInstance.save(flush: true)) {
				flash.info = "${message(code: 'default.updated.message', args: [message(code: 'TNode.label', default: 'TNode'), TNodeInstance.name])}"
				redirect(action: "show", controller: "TReport", params:[id: params['report']])
			}
			else {
				render(view: "edit", model: [TNodeInstance: TNodeInstance, report:params['report']])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'TNode.label', default: 'TNode'), TNodeInstance.name])}"
			redirect(action: "show", controller: "TReport", params:[id:params['report']])
		}
	}
	
	//TODO delete
	public void deleteNodeRelations(TNode node){
		//First delete relations
		
		node.children.each{ it.delete() }
		
		
	}
	
	
	
	/*
	 * removes all Children of a parentnode
	 */
	def deleteAllChildren(TNode tnode, List allnodes){
		
		tnode.children.each{
			deleteAllChildren(it, allnodes)
			
		}
		allnodes.add(tnode)	
		
	}
	
	
	
	//TODO ohne nachfolger
	//TODO kann nur gelöscht werden, wenn die tnode keine titlenode ist
	def delete = {
			
		def TNodeInstance = TNode.get(params.snode)	
		def ParentNode = TNode.get(TNodeInstance.parent_id)
		
		def tnodeList = new ArrayList<TNode>()
		deleteAllChildren(TNodeInstance,tnodeList)
		
		tnodeList.each{		
			def p = TNode.get(it.parent_id)
			p.children.remove(it)
			TNodeIndicatorService.removeAllIndicators(it)
			it.delete()
		}	
		ParentNode.children.remove(TNodeInstance)
		
		if (TNodeInstance) {
			
			TNodeInstance.delete()
			flash.info = "${message(code: 'node.deleted.message', args: [message(code: 'TNode.label', default: 'TNode'), TNodeInstance.name])}"
			redirect(action: "show", controller:"TReport", params:[id:params['report']])
		}else {
			flash.message = "${message(code: 'node.not.found.message', args: [message(code: 'TNode.label', default: 'TNode'), TNodeInstance.name])}"
			redirect(action: "show",controller:"TReport", params:[id:params['report']])
		}
	}
}
