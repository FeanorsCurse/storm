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
import systemadministration.modulmanagement.Module
import ReportEditorMain.ArticleEditor.Article;
import systemadministration.usermanagement.User
import java.text.DateFormat


class ArticleToArticleCollaborativeFilteringController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

                             
    def startService = {
    	ArticleToArticleCollaborativeFilteringService.updateArticleToArticleCollaborativeFiltering();
    	redirect(action: "list")
    }		
    	                     
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [articleToArticleCollaborativeFilteringInstanceList: ArticleToArticleCollaborativeFiltering.list(params), articleToArticleCollaborativeFilteringInstanceTotal: ArticleToArticleCollaborativeFiltering.count()]
    }

    def create = {
        def articleToArticleCollaborativeFilteringInstance = new ArticleToArticleCollaborativeFiltering()
        articleToArticleCollaborativeFilteringInstance.properties = params
        return [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance]
    }

    def save = {
        def articleToArticleCollaborativeFilteringInstance = new ArticleToArticleCollaborativeFiltering(params)
        if (articleToArticleCollaborativeFilteringInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), articleToArticleCollaborativeFilteringInstance.id])}"
            redirect(action: "show", id: articleToArticleCollaborativeFilteringInstance.id)
        }
        else {
            render(view: "create", model: [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance])
        }
    }

    def show = {
        def articleToArticleCollaborativeFilteringInstance = ArticleToArticleCollaborativeFiltering.get(params.id)
        if (!articleToArticleCollaborativeFilteringInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
            redirect(action: "list")
        }
        else {
            [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance]
        }
    }

    def edit = {
        def articleToArticleCollaborativeFilteringInstance = ArticleToArticleCollaborativeFiltering.get(params.id)
        if (!articleToArticleCollaborativeFilteringInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance]
        }
    }

    def update = {
        def articleToArticleCollaborativeFilteringInstance = ArticleToArticleCollaborativeFiltering.get(params.id)
        if (articleToArticleCollaborativeFilteringInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleToArticleCollaborativeFilteringInstance.version > version) {
                    
                    articleToArticleCollaborativeFilteringInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering')] as Object[], "Another user has updated this ArticleToArticleCollaborativeFiltering while you were editing")
                    render(view: "edit", model: [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance])
                    return
                }
            }
            articleToArticleCollaborativeFilteringInstance.properties = params
            if (!articleToArticleCollaborativeFilteringInstance.hasErrors() && articleToArticleCollaborativeFilteringInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), articleToArticleCollaborativeFilteringInstance.id])}"
                redirect(action: "show", id: articleToArticleCollaborativeFilteringInstance.id)
            }
            else {
                render(view: "edit", model: [articleToArticleCollaborativeFilteringInstance: articleToArticleCollaborativeFilteringInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def articleToArticleCollaborativeFilteringInstance = ArticleToArticleCollaborativeFiltering.get(params.id)
        if (articleToArticleCollaborativeFilteringInstance) {
            try {
                articleToArticleCollaborativeFilteringInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering'), params.id])}"
            redirect(action: "list")
        }
    }
}
