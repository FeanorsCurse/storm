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
 
package systemadministration.recommender

class AccessArticleTypController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [accessArticleTypInstanceList: AccessArticleTyp.list(params), accessArticleTypInstanceTotal: AccessArticleTyp.count()]
    }

    def create = {
        def accessArticleTypInstance = new AccessArticleTyp()
        accessArticleTypInstance.properties = params
        return [accessArticleTypInstance: accessArticleTypInstance]
    }

    def save = {
        def accessArticleTypInstance = new AccessArticleTyp(params)
        if (accessArticleTypInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), accessArticleTypInstance.id])}"
            redirect(action: "show", id: accessArticleTypInstance.id)
        }
        else {
            render(view: "create", model: [accessArticleTypInstance: accessArticleTypInstance])
        }
    }

    def show = {
        def accessArticleTypInstance = AccessArticleTyp.get(params.id)
        if (!accessArticleTypInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
            redirect(action: "list")
        }
        else {
            [accessArticleTypInstance: accessArticleTypInstance]
        }
    }

    def edit = {
        def accessArticleTypInstance = AccessArticleTyp.get(params.id)
        if (!accessArticleTypInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [accessArticleTypInstance: accessArticleTypInstance]
        }
    }

    def update = {
        def accessArticleTypInstance = AccessArticleTyp.get(params.id)
        if (accessArticleTypInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (accessArticleTypInstance.version > version) {
                    
                    accessArticleTypInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp')] as Object[], "Another user has updated this AccessArticleTyp while you were editing")
                    render(view: "edit", model: [accessArticleTypInstance: accessArticleTypInstance])
                    return
                }
            }
            accessArticleTypInstance.properties = params
            if (!accessArticleTypInstance.hasErrors() && accessArticleTypInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), accessArticleTypInstance.id])}"
                redirect(action: "show", id: accessArticleTypInstance.id)
            }
            else {
                render(view: "edit", model: [accessArticleTypInstance: accessArticleTypInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def accessArticleTypInstance = AccessArticleTyp.get(params.id)
        if (accessArticleTypInstance) {
            try {
                accessArticleTypInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp'), params.id])}"
            redirect(action: "list")
        }
    }
	
	def updateStats = {
    	render ("Artikelzugriffstat erneuert?: "+ ArticleAccessReportingService.updateAccessArticleType())
	    }
    def showMostFrequentlyArticle = {
    	
    	//def results = Book.list(max:10, offset:100, sort:"title", order:"desc")
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort="countAll"
        params.order="desc"
        [accessArticleTypInstanceList: AccessArticleTyp.list(params), accessArticleTypInstanceTotal: AccessArticleTyp.count()]
   
    }
    
    def showMostFrequentlyArticle2 = {
    	
    	String hsql = "select accessArticleTyp.article,sum(accessArticleTyp.countAll) from AccessArticleTyp as accessArticleTyp" +
							//"  where accessLog.dateCreated > ?" +
							"group by accessArticleTyp.article"
    	//def results = Book.list(max:10, offset:100, sort:"title", order:"desc")
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.sort="countAll"
        params.order="desc"
        	
    	java.util.List result = AccessLog.executeQuery(hsql)
      //  [accessArticleTypInstanceList: AccessArticleTyp.list(params), accessArticleTypInstanceTotal: AccessArticleTyp.count()]
        [accessArticleTypInstanceList: result, accessArticleTypInstanceTotal: AccessArticleTyp.count()]
          
    }
    
}
