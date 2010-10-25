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
import systemadministration.usermanagement.User;
import systemadministration.modulmanagement.Module;
import systemadministration.usermanagement.InterestGroup;
import ReportEditorMain.ArticleEditor.Article;

class ArticleViewedByInterestgroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    
    def startService = {
    
    		ArticleViewedByInterestgroupService.updateArticleViewedByInterestgroup()
    		redirect(action: "list")
    }
    	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [articleViewedByInterestgroupInstanceList: ArticleViewedByInterestgroup.list(params), articleViewedByInterestgroupInstanceTotal: ArticleViewedByInterestgroup.count()]
    }

    def create = {
        def articleViewedByInterestgroupInstance = new ArticleViewedByInterestgroup()
        articleViewedByInterestgroupInstance.properties = params
        return [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance]
    }

    def save = {
        def articleViewedByInterestgroupInstance = new ArticleViewedByInterestgroup(params)
        if (articleViewedByInterestgroupInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), articleViewedByInterestgroupInstance.id])}"
            redirect(action: "show", id: articleViewedByInterestgroupInstance.id)
        }
        else {
            render(view: "create", model: [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance])
        }
    }

    def show = {
        def articleViewedByInterestgroupInstance = ArticleViewedByInterestgroup.get(params.id)
        if (!articleViewedByInterestgroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance]
        }
    }

    def edit = {
        def articleViewedByInterestgroupInstance = ArticleViewedByInterestgroup.get(params.id)
        if (!articleViewedByInterestgroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance]
        }
    }

    def update = {
        def articleViewedByInterestgroupInstance = ArticleViewedByInterestgroup.get(params.id)
        if (articleViewedByInterestgroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleViewedByInterestgroupInstance.version > version) {
                    
                    articleViewedByInterestgroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup')] as Object[], "Another user has updated this ArticleViewedByInterestgroup while you were editing")
                    render(view: "edit", model: [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance])
                    return
                }
            }
            articleViewedByInterestgroupInstance.properties = params
            if (!articleViewedByInterestgroupInstance.hasErrors() && articleViewedByInterestgroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), articleViewedByInterestgroupInstance.id])}"
                redirect(action: "show", id: articleViewedByInterestgroupInstance.id)
            }
            else {
                render(view: "edit", model: [articleViewedByInterestgroupInstance: articleViewedByInterestgroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def articleViewedByInterestgroupInstance = ArticleViewedByInterestgroup.get(params.id)
        if (articleViewedByInterestgroupInstance) {
            try {
                articleViewedByInterestgroupInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup'), params.id])}"
            redirect(action: "list")
        }
    }
}
