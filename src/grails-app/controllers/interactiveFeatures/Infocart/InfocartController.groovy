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
 @author Frank Gerken, Christian Wenke
 */
package interactiveFeatures.Infocart
import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;
import interactiveFeatures.Infocart.InfocartItem
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.Article
import systemadministration.modulmanagement.Module


class InfocartController {
	
	static allowedMethods = [save: "POST", update: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def infocartList = Infocart.findAllByUser(session.user)
		[infocartInstanceList: infocartList, infocartInstanceTotal: infocartList.size()]
	}
	
	def listvisible = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def crit = Infocart.createCriteria()
		def infocartList = crit {
			and {
				eq("visible", true)
				ne("user", session.user)
			}
			order("dateCreated", "desc")
		}
		[infocartInstanceList: infocartList, infocartInstanceTotal: infocartList.size()]
	}
	
	def listpublished = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def crit = Infocart.createCriteria()
		def infocartList = crit {
			eq("published", true)
			order("dateCreated", "desc")
		}
		[infocartInstanceList: infocartList, infocartInstanceTotal: infocartList.size()]
	}
	
	def create = {
		session.infocart = new Infocart(user:session.user,visible:false,published:false).save()
		redirect(action: edit)
	}
	
	def save = {
		def infocartInstance
		if (params.id) {
			infocartInstance = Infocart.get(params.id)
		}
		else {
			if (!session.infocart) {
				session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
			}
			infocartInstance = Infocart.get(session.infocart?.id)
		}
		if (session.infocart.user != session.user) {
			session.infocart.user = session.user
		}
		infocartInstance.properties = params
		infocartInstance.user = session.user
		if (infocartInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'infocart.label', default: 'Infocart'), infocartInstance.id])}"
			redirect(action: "list")
		}
		else {
			render(view: "create", model: [infocartInstance: infocartInstance])
		}
	}
	
	def show = {
		def infocartInstance
		if (params.id) {
			infocartInstance = Infocart.get(params.id)
		}
		else {
			if (!session.infocart) {
				session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
			}
			infocartInstance = Infocart.get(session.infocart?.id)
		}
		if (!infocartInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
		else { 
			def crit = InfocartItem.createCriteria()
			def infocartItemList = crit {
				eq("infocart",infocartInstance)
				order("article", "asc")
			}
			return [infocartInstance: infocartInstance, infocartItemList: infocartItemList]
		} 
	}
	
	def display = {
		def infocartInstance
		if (params.id) {
			infocartInstance = Infocart.get(params.id)
		}
		else {
			if (!session.infocart) {
				session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
			}
			infocartInstance = Infocart.get(session.infocart?.id)
		}
		if (!infocartInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
		else {
			def crit = InfocartItem.createCriteria()
			def infocartItemList = crit {
				eq("infocart",infocartInstance)
				order("article", "asc")
			}
			return [infocartInstance: infocartInstance, infocartItemList: infocartItemList]
		}
	}
	
	def showAsOnePage = {
		def infocartInstance
		if (params.id) {
			infocartInstance = Infocart.get(params.id)
		}
		else {
			if (!session.infocart) {
				session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
			}
			infocartInstance = Infocart.get(session.infocart?.id)
		}
		if (!infocartInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
		else {
			def crit = InfocartItem.createCriteria()
			def infocartItemList = crit {
				eq("infocart",infocartInstance)
				order("article", "asc")
			}
			return [infocartInstance: infocartInstance, infocartItemList: infocartItemList]
		}
	}
	
	def edit = {
		def infocartInstance
		if (params.id) {
			infocartInstance = Infocart.get(params.id)
		}
		else {
			if (!session.infocart) {
				session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
			}
			infocartInstance = Infocart.get(session.infocart?.id)
		}
		if (!infocartInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
		else {
			def crit = InfocartItem.createCriteria()
			def infocartItemList = crit {
				eq("infocart",infocartInstance)
				order("article", "asc")
			}
			return [infocartInstance: infocartInstance, infocartItemList: infocartItemList]
		}
	}
	
	def updatename = {
		def infocartInstance = Infocart.get(params.id)
		if (infocartInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (infocartInstance.version > version) {
					infocartInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'infocart.label', default: 'Infocart')] as Object[], "Another user has updated this Infocart while you were editing")
					render(view: "edit", model: [infocartInstance: infocartInstance])
					return
				}
			}
			infocartInstance.properties = params
			if (!infocartInstance.hasErrors() && infocartInstance.save(flush: true)) {
				if (infocartInstance.user != session.user) {
					infocartInstance.user = session.user
				}
				//flash.message = "${message(code: 'default.updated.message', args: [message(code: 'infocart.label', default: 'Infocart'), infocartInstance.id])}"
				redirect(action: "edit", id: infocartInstance.id)
			}
			else {
				render(view: "edit", model: [infocartInstance: infocartInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def togglepublished = {
		def infocartInstance = Infocart.get(params.id)
		if (infocartInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (infocartInstance.version > version) {
					infocartInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'infocart.label', default: 'Infocart')] as Object[], "Another user has updated this Infocart while you were editing")
					render(view: "edit", model: [infocartInstance: infocartInstance])
					return
				}
			}
			if(infocartInstance.published) {
				infocartInstance.published = false
			} else {
				infocartInstance.published = true
			}
			if (!infocartInstance.hasErrors() && infocartInstance.save(flush: true)) {
				if (infocartInstance.user != session.user) {
					infocartInstance.user = session.user
				}
				//flash.message = "${message(code: 'default.updated.message', args: [message(code: 'infocart.label', default: 'Infocart'), infocartInstance.id])}"
				redirect(action: "edit", id: infocartInstance.id)
			}
			else {
				render(view: "edit", model: [infocartInstance: infocartInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def togglevisible = {
		def infocartInstance = Infocart.get(params.id)
		if (infocartInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (infocartInstance.version > version) {
					infocartInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'infocart.label', default: 'Infocart')] as Object[], "Another user has updated this Infocart while you were editing")
					render(view: "edit", model: [infocartInstance: infocartInstance])
					return
				}
			}
			if(infocartInstance.visible) {
				infocartInstance.visible = false
			} else {
				infocartInstance.visible = true
			}
			if (!infocartInstance.hasErrors() && infocartInstance.save(flush: true)) {
				if (infocartInstance.user != session.user) {
					infocartInstance.user = session.user
				}
				//flash.message = "${message(code: 'default.updated.message', args: [message(code: 'infocart.label', default: 'Infocart'), infocartInstance.id])}"
				redirect(action: "edit", id: infocartInstance.id)
			}
			else {
				render(view: "edit", model: [infocartInstance: infocartInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def infocartInstance = Infocart.get(params.id)
		if (infocartInstance) {
			try {
				infocartInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocart.label', default: 'Infocart'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def addArticle = {
		def infocartInstance
		def articleInstance=Article.get(params.id)
		if (!session.infocart) {
			session.infocart = new Infocart(name:message(code: 'infocart.infocartof.name', args: [session?.user?.username]),user:session.user,visible:false,published:false).save()
		}
		infocartInstance = Infocart.get(session.infocart?.id)
		
		//check for duplicate article entry
		boolean duplicate = false;
		for (Iterator iterator = infocartInstance.items.iterator(); iterator.hasNext();) {
			InfocartItem infocartItem = (InfocartItem) iterator.next();
			if(infocartItem.article == articleInstance){
				duplicate = true;
				break;
			}
		}
		if (!duplicate &&infocartInstance.addToItems(new InfocartItem(article:articleInstance))) {
			flash.message = "${message(code: 'infocart.articleAdded.message', default: 'Article added to infocart')}"
		}
		else {
			flash.message = "${message(code: 'infocart.articleNotAdded.message', default: 'Article could not be added to infocart')}"
		}
		redirect(controller: "article", action: "display", id: params.id)
	}
	
	def deleteItem = {
		def infocartItemInstance = InfocartItem.get(params.id)
		if (infocartItemInstance) {
			try {
				infocartItemInstance.delete(flush: true)
				// flash.message = "${message(code: 'infocart.articleremoved.message', default: 'Article removed from infocart')}"
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'infocart.articlenotremoved.message', default: 'Article could not be removed from infocart')}"
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'infocartItem.label', default: 'Infocart'), params.id])}"
		}
		redirect(action: "show")
	}
	
	def load = {
		def infocartItemInstance = InfocartItem.get(params.id)
		if (infocartItemInstance) {
			session.infocart = Infocart.get(params.id)
			flash.message = "${message(code: 'infocart.loaded.message')}"
		}
		else {
			flash.message = "${message(code: 'infocart.notloaded.message')}"
		}
		redirect(action: "show")
	}
	
	def pdfExportService
	/**Exports the selected Infocart to PDF
	 * 
	 */
	def  toPdf ={
		def InfocartInstance = Infocart.get(params.id)
		
		if(InfocartInstance.items.isEmpty()){
			flash.message = "${message(code: 'infocart.empty.label', args: [message(code: 'infocart.empty.label', default: 'Infocart')])}"
			redirect(action:"show", id:params.id)
		}else{
			
			def root = servletContext.getRealPath(grailsApplication.config.service.pdfExport.infocartPdfRoot)
			
			def url = pdfExportService.export(InfocartInstance, root, session.user.username)		
			
			//delete pdf after redirect
			redirect(uri: url)	
		}
		
	}
	def articleService
	def xml={
		if(!params.id){
			render ("Missing param: ID")
		}else{
			def InfocartInstance = Infocart.get(params.id)
			//def ReportInstance = articleService.getReport(ArticleInstance)
			//String contentOut  = ArticleInstance.content.toString().replaceAll("\\<.*?>","");
			
			def builder = new StreamingMarkupBuilder()
			def Infocart = builder.bind {
				data{
					user(uid:InfocartInstance.getUser().id, firstname:InfocartInstance.getUser().firstname, lastname:InfocartInstance.getUser().lastname)
					infocartname InfocartInstance.name
					itemlist{		
						InfocartInstance.items.each{
							def ArticleInstance = it.getArticle()
							def ReportInstance = articleService.getReport(ArticleInstance)
							infoitem{
								article(id:ArticleInstance.id, name:ArticleInstance.name, report:ReportInstance.name, author_firstname:ArticleInstance.author.firstname,author_lastname:ArticleInstance.author.lastname){
									content ArticleInstance.content.toString().replaceAll("\\<.*?>","").decodeHTML()
								}
							}
						}
					}
				}
			}		
			render XmlUtil.serialize(Infocart)
		}
	}
}
