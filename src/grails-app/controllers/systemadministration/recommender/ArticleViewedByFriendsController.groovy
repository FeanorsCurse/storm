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

import systemadministration.usermanagement.User
import ReportEditorMain.ArticleEditor.Article
import systemadministration.usermanagement.User;
import systemadministration.modulmanagement.Module;
import systemadministration.usermanagement.InterestGroup;
import systemadministration.recommender.AccessArticleTyp
import ReportEditorMain.ArticleEditor.Article;
import java.util.ArrayList;

class ArticleViewedByFriendsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

                     
                             
    def startService = {
    	ArticleViewedByFriendsService.updateArticleViewedByFriends()
    	redirect(action: "list")
    }
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [articleViewedByFriendsInstanceList: ArticleViewedByFriends.list(params), articleViewedByFriendsInstanceTotal: ArticleViewedByFriends.count()]
    }

    def create = {
        def articleViewedByFriendsInstance = new ArticleViewedByFriends()
        articleViewedByFriendsInstance.properties = params
        return [articleViewedByFriendsInstance: articleViewedByFriendsInstance]
    }

    def save = {
        def articleViewedByFriendsInstance = new ArticleViewedByFriends(params)
        if (articleViewedByFriendsInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), articleViewedByFriendsInstance.id])}"
            redirect(action: "show", id: articleViewedByFriendsInstance.id)
        }
        else {
            render(view: "create", model: [articleViewedByFriendsInstance: articleViewedByFriendsInstance])
        }
    }

    def show = {
        def articleViewedByFriendsInstance = ArticleViewedByFriends.get(params.id)
        if (!articleViewedByFriendsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
            redirect(action: "list")
        }
        else {
            [articleViewedByFriendsInstance: articleViewedByFriendsInstance]
        }
    }

    def edit = {
        def articleViewedByFriendsInstance = ArticleViewedByFriends.get(params.id)
        if (!articleViewedByFriendsInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [articleViewedByFriendsInstance: articleViewedByFriendsInstance]
        }
    }

    def update = {
        def articleViewedByFriendsInstance = ArticleViewedByFriends.get(params.id)
        if (articleViewedByFriendsInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleViewedByFriendsInstance.version > version) {
                    
                    articleViewedByFriendsInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends')] as Object[], "Another user has updated this ArticleViewedByFriends while you were editing")
                    render(view: "edit", model: [articleViewedByFriendsInstance: articleViewedByFriendsInstance])
                    return
                }
            }
            articleViewedByFriendsInstance.properties = params
            if (!articleViewedByFriendsInstance.hasErrors() && articleViewedByFriendsInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), articleViewedByFriendsInstance.id])}"
                redirect(action: "show", id: articleViewedByFriendsInstance.id)
            }
            else {
                render(view: "edit", model: [articleViewedByFriendsInstance: articleViewedByFriendsInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def articleViewedByFriendsInstance = ArticleViewedByFriends.get(params.id)
        if (articleViewedByFriendsInstance) {
            try {
                articleViewedByFriendsInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends'), params.id])}"
            redirect(action: "list")
        }
    }
}
