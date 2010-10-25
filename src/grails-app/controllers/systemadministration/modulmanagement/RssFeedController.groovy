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
 * This Class manages the RSSFeeds, that can be imported into the STORM-Software
 * 
 * @author: Gerrit
 */
class RssFeedController {
	
	//define allowed HTTP-Request methods
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Redirection to list
	 */   
	def index = {
		redirect(action: "list", params: params)
	}
	
	/**
	 * List RSSFeeds
	 */
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[rssFeedInstanceList: RssFeed.list(params), rssFeedInstanceTotal: RssFeed.count()]
	}
	
	/**
	 * Create new RSSFeed
	 */
	def create = {
		def rssFeedInstance = new RssFeed()
		rssFeedInstance.properties = params
		return [rssFeedInstance: rssFeedInstance]
	}
	
	/**
	 * Save created RSSFeed
	 */
	def save = {
		def rssFeedInstance = new RssFeed(params)
		if (rssFeedInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), rssFeedInstance.id])}"
			redirect(action: "show", id: rssFeedInstance.id)
		}
		else {
			render(view: "create", model: [rssFeedInstance: rssFeedInstance])
		}
	}
	
	/**
	 * Show RSSFeed
	 */
	def show = {
		def rssFeedInstance = RssFeed.get(params.id)
		if (!rssFeedInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
			redirect(action: "list")
		}
		else {
			[rssFeedInstance: rssFeedInstance]
		}
	}
	
	/**
	 * Edit RSSFeed
	 */
	def edit = {
		def rssFeedInstance = RssFeed.get(params.id)
		if (!rssFeedInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [rssFeedInstance: rssFeedInstance]
		}
	}
	
	/**
	 * Update RSSFeed
	 */
	def update = {
		def rssFeedInstance = RssFeed.get(params.id)
		if (rssFeedInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (rssFeedInstance.version > version) {
					
					rssFeedInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'rssFeed.label', default: 'RssFeed')] as Object[], "Another user has updated this RssFeed while you were editing")
					render(view: "edit", model: [rssFeedInstance: rssFeedInstance])
					return
				}
			}
			rssFeedInstance.properties = params
			if (!rssFeedInstance.hasErrors() && rssFeedInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), rssFeedInstance.id])}"
				redirect(action: "show", id: rssFeedInstance.id)
			}
			else {
				render(view: "edit", model: [rssFeedInstance: rssFeedInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
			redirect(action: "list")
		}
	}
	
	/**
	 * Delete RSSFeed
	 */
	def delete = {
		def rssFeedInstance = RssFeed.get(params.id)
		if (rssFeedInstance) {
			try {
				rssFeedInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'rssFeed.label', default: 'RssFeed'), params.id])}"
			redirect(action: "list")
		}
	}
}
