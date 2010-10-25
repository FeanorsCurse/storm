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
 
package rss

import java.util.Date;

import systemadministration.usermanagement.User;

class FeedController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[feedInstanceList: Feed.list(params), feedInstanceTotal: Feed.count()]
	}
	
	def create = {
		def feedInstance = new Feed()
		feedInstance.properties = params
		return [feedInstance: feedInstance]
	}
	
	def save = {
		def feedInstance = new Feed(params)
		feedInstance.lastModifiedBy=session.user 
		feedInstance.author=session.user 
		feedInstance.creationDate=new Date()
		feedInstance.lastModified=new Date()
	
		if (feedInstance.save(flush: true)) {
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'feed.label', default: 'Feed'), feedInstance.id])}"
			redirect(action: "show", id: feedInstance.id)
		}
		else {
			render(view: "create", model: [feedInstance: feedInstance])
		}
	}
	
	def show = {
		def feedInstance = Feed.get(params.id)
		if (!feedInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
			redirect(action: "list")
		}
		else {
			[feedInstance: feedInstance]
		}
	}
	
	def edit = {
		def feedInstance = Feed.get(params.id)
		if (!feedInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [feedInstance: feedInstance]
		}
	}
	
	def update = {
		def feedInstance = Feed.get(params.id)
		if (feedInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (feedInstance.version > version) {
					
					feedInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'feed.label', default: 'Feed')] as Object[], "Another user has updated this Feed while you were editing")
					render(view: "edit", model: [feedInstance: feedInstance])
					return
				}
			}
			feedInstance.properties = params
			if (!feedInstance.hasErrors() && feedInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'feed.label', default: 'Feed'), feedInstance.id])}"
				redirect(action: "show", id: feedInstance.id)
			}
			else {
				render(view: "edit", model: [feedInstance: feedInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
			redirect(action: "list")
		}
	}
	
	def delete = {
		def feedInstance = Feed.get(params.id)
		if (feedInstance) {
			try {
				feedInstance.delete(flush: true)
				flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
				redirect(action: "list")
			}
			catch (org.springframework.dao.DataIntegrityViolationException e) {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
				redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'feed.label', default: 'Feed'), params.id])}"
			redirect(action: "list")
		}
	}
	
	//TODO in config outsourcen
	def feed = {
		render(feedType:"rss", feedVersion:"2.0") {
			title = "Storm feed"
			link  = "http://localhost:8080/Storm/feed/feed"
			description =  "Storm Rss Feeds"
			
			Feed.list().each() { 
				rss -> entry(rss.title) { 
					link = rss.link 
					rss.content
				}
			}
		}
	}
}
