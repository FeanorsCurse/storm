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
 * This class Manages the different Languages for the Homepage and the Reports
 * 
 * @author: Gerrit
 */
class LanguageController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */                         
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * List Languages
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[languageInstanceList: Language.list(params), languageInstanceTotal: Language.count()]
	}
	
	/**
	 * create new Language
	 */
	def create = {
		def languageInstance = new Language()
		languageInstance.properties = params
		return [languageInstance: languageInstance]
	}
	
	/**
	 * Save new Language to Database
	 */
	def save = {
		def languageInstance = new Language(params)
		if (languageInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'language.label', default: 'Language'), languageInstance.id])}"
			redirect(action: "show", id: languageInstance.id)
		}
		else {
			render(view: "create", model: [languageInstance: languageInstance])
		}
	}
	
	/**
	 * Show values of new Language
	 */
	def show = {
		def languageInstance = Language.get(params.id)
		if (!languageInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
			redirect(action: "list")
		}
		else {
			[languageInstance: languageInstance]
		}
	}
	
	/**
	 * Edit Language
	 */
	def edit = {
		def languageInstance = Language.get(params.id)
		if (!languageInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [languageInstance: languageInstance]
		}
	}
	
	/**
	 * Update Language
	 */
	def update = {
		def languageInstance = Language.get(params.id)
		if (languageInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (languageInstance.version > version) {
					
					languageInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'language.label', default: 'Language')] as Object[], "Another user has updated this Language while you were editing")
					render(view: "edit", model: [languageInstance: languageInstance])
					return
				}
			}
			languageInstance.properties = params
			if (!languageInstance.hasErrors() && languageInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'language.label', default: 'Language'), languageInstance.id])}"
				redirect(action: "show", id: languageInstance.id)
			}
			else {
				render(view: "edit", model: [languageInstance: languageInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Delete Language
	 */
	def delete = {
		def languageInstance = Language.get(params.id)
		if (languageInstance) {
			try {
				languageInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'language.label', default: 'Language'), params.id])}"
			redirect(action: "list")
		}
	}
}
