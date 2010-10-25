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
 
package systemadministration.externalsystems

/**
 * Controller for creating, editing, deleting, listing, updating SAP-BAPIs for specific SAP Connections
 * 
 * @author: Gerrit
 * @see: SAPConnection
 */
class BapiController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * List BAPIs
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[bapiInstanceList: Bapi.list(params), bapiInstanceTotal: Bapi.count()]
	}
	
	/**
	 * create new BAPI
	 */
	def create = {
		def bapiInstance = new Bapi()
		bapiInstance.properties = params
		return [bapiInstance: bapiInstance]
	}
	
	/**
	 * Save new BAPI
	 */
	def save = {
		def bapiInstance = new Bapi(params)
		if (bapiInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'bapi.label', default: 'Bapi'), bapiInstance.id])}"
			redirect(action: "show", id: bapiInstance.id)
		}
		else {
			render(view: "create", model: [bapiInstance: bapiInstance])
		}
	}
	
	/**
	 * Show BAPI values
	 */
	def show = {
		def bapiInstance = Bapi.get(params.id)
		if (!bapiInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
			redirect(action: "list")
		}
		else {
			[bapiInstance: bapiInstance]
		}
	}
	
	/**
	 * Edit BAPI
	 */
	def edit = {
		def bapiInstance = Bapi.get(params.id)
		if (!bapiInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [bapiInstance: bapiInstance]
		}
	}
	
	/**
	 * Update BAPI after editing
	 */
	def update = {
		def bapiInstance = Bapi.get(params.id)
		if (bapiInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (bapiInstance.version > version) {
					
					bapiInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'bapi.label', default: 'Bapi')] as Object[], "Another user has updated this Bapi while you were editing")
					render(view: "edit", model: [bapiInstance: bapiInstance])
					return
				}
			}
			bapiInstance.properties = params
			if (!bapiInstance.hasErrors() && bapiInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'bapi.label', default: 'Bapi'), bapiInstance.id])}"
				redirect(action: "show", id: bapiInstance.id)
			}
			else {
				render(view: "edit", model: [bapiInstance: bapiInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Delete BAPI
	 */
	def delete = {
		def bapiInstance = Bapi.get(params.id)
		if (bapiInstance) {
			try {
				bapiInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'bapi.label', default: 'Bapi'), params.id])}"
			redirect(action: "list")
		}
	}
}
