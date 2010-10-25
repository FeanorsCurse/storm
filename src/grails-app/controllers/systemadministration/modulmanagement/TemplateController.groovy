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
 
package systemadministration.modulmanagement

/**
 * This class manages the Templates for the STORM-Website
 * 
 * @author: Gerrit
 */
class TemplateController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */  
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * Get first Template
	 */
	def value = {
		Template.get(0);
	}
	
	/**
	 * List Templates 
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[templateInstanceList: Template.list(params), templateInstanceTotal: Template.count()]
	}
	
	/**
	 * Create new Template
	 */
	def create = {
		def templateInstance = new Template()
		templateInstance.properties = params
		return [templateInstance: templateInstance]
	}
	
	/**
	 * Save created Template
	 */
	def save = {
		def templateInstance = new Template(params)
		if (templateInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'template.label', default: 'Template'), templateInstance.id])}"
			redirect(action: "show", id: templateInstance.id)
		}
		else {
			render(view: "create", model: [templateInstance: templateInstance])
		}
	}
	
	/**
	 * Show Template
	 */
	def show = {
		def templateInstance = Template.get(params.id)
		if (!templateInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
			redirect(action: "list")
		}
		else {
			[templateInstance: templateInstance]
		}
	}
	
	/**
	 * Edit Template
	 */
	def edit = {
		def templateInstance = Template.get(params.id)
		if (!templateInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [templateInstance: templateInstance]
		}
	}
	
	/**
	 * Update Template
	 */
	def update = {
		def templateInstance = Template.get(params.id)
		if (templateInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (templateInstance.version > version) {
					
					templateInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'template.label', default: 'Template')] as Object[], "Another user has updated this Template while you were editing")
					render(view: "edit", model: [templateInstance: templateInstance])
					return
				}
			}
			templateInstance.properties = params
			if (!templateInstance.hasErrors() && templateInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'template.label', default: 'Template'), templateInstance.id])}"
				redirect(action: "show", id: templateInstance.id)
			}
			else {
				render(view: "edit", model: [templateInstance: templateInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Delete Template
	 */
	def delete = {
		def templateInstance = Template.get(params.id)
		if (templateInstance) {
			try {
				templateInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'template.label', default: 'Template'), params.id])}"
			redirect(action: "list")
		}
	}
}
