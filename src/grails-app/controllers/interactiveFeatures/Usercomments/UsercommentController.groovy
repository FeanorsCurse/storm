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
@author Frank Gerken
*/
package interactiveFeatures.Usercomments

import interactiveFeatures.Usercomments.*
import ReportEditorMain.ArticleEditor.*
import systemadministration.usermanagement.*


class UsercommentController {
    static allowedMethods = [save: "POST", update: "POST"]

    final Boolean THUMBUP = 1
    final Boolean THUMBDOWN = 0

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [usercommentInstanceList: Usercomment.list(params), usercommentInstanceTotal: Usercomment.count()]
    }

    def listarticlecomments = {
        def crit = Usercomment.createCriteria()
        // Find all usercomments für given article with empty reference (top level comments for this article)
        def usercommentInstanceList = crit {
            and {
                eq("article", Article.get(params.id))
                isNull("reference")
            }
            order("dateCreated", "desc")
        }
        [usercommentInstanceList: usercommentInstanceList]
    }

    def create = {
        def usercommentInstance = new Usercomment()
        usercommentInstance.properties = params
        usercommentInstance.article = Article.get(params.id)
        if(params.reference) {
            usercommentInstance.reference = Usercomment.get(params.reference)
        }
        return [usercommentInstance: usercommentInstance]
    }

    def reply = {
        def usercommentInstance = new Usercomment()
        // get referenced comment
        def ref = Usercomment.get(params.id)
        usercommentInstance.commentator = session.user
        usercommentInstance.reference = ref
        usercommentInstance.article = ref.article
        if (ref.title.startsWith("Re: ")) {
            usercommentInstance.title = ref.title
        }
        else {
            usercommentInstance.title = "Re: " + ref.title
        }
        return [usercommentInstance: usercommentInstance]
    }

    def save = {
        def article = Article.get(params.article)
        def comment = new Usercomment(title: params.title, content:params.commentcontent, commentator:session.user)
        if(params.reference) {
            comment.reference = Usercomment.get(params.reference)
        }
        // try to add usercomment to article
        if (!article.addToUsercomments(comment)) {
            flash.message = "${message(code: 'usercomment.notcreated.message', default: "Comment not created")}"
        }
        redirect(controller: "article", action: "display", id: article.id)
    }

    def cancel = {
        redirect(controller: "article", action: "display", id: params.id)
    }

    def show = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(action: "list")
        }
        else {
            def crit = Usercomment.createCriteria()
            // create list of all comments that are direct replies to given comment
            def replyList = crit {
                and {
                    eq("reference", Usercomment.get(params.id))
                }
                order("dateCreated", "desc")
            }
            [usercommentInstance: usercommentInstance, thumbsupcount: ThumbVote.countByCommentAndVote(usercommentInstance, "up"), thumbsdowncount: ThumbVote.countByCommentAndVote(usercommentInstance, "down"), replyList: replyList]
        }
    }

    def display = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(action: "list")
        }
        else {
            def crit = Usercomment.createCriteria()
            def replyList = crit {
                and {
                    eq("reference", Usercomment.get(params.id))
                }
                order("dateCreated", "desc")
            }
            [usercommentInstance: usercommentInstance, thumbsupcount: ThumbVote.countByCommentAndVote(usercommentInstance, THUMBUP), thumbsdowncount: ThumbVote.countByCommentAndVote(usercommentInstance, THUMBDOWN), replyList: replyList]
        }
    }

    def displaysingle = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(action: "list")
        }
        else {
            [usercommentInstance: usercommentInstance, thumbsupcount: ThumbVote.countByCommentAndVote(usercommentInstance, THUMBUP), thumbsdowncount: ThumbVote.countByCommentAndVote(usercommentInstance, THUMBDOWN)]
        }
    }

    def thumbsupvote = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
        }
        else {
            if (session.user.id != usercommentInstance.commentator.id && session.user.username != 'Anonym') {
                def vote = ThumbVote.findByUserAndComment(session.user, usercommentInstance)
                if (vote) {
                    vote.vote = THUMBUP
                    vote.save()
                }
                else {
                    vote = new ThumbVote(user:session.user, vote:THUMBUP)
                    usercommentInstance.addToThumbvotes(vote)
                }
            }
        }
        redirect(controller: "article", action: "display", id: usercommentInstance.article.id)
    }

    def thumbsdownvote = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
        }
        else {
            if (session.user.id != usercommentInstance.commentator.id && session.user.username != 'Anonym') {
                def vote = ThumbVote.findByUserAndComment(session.user, usercommentInstance)
                if (vote) {
                    vote.vote = THUMBDOWN
                    vote.save()
                }
                else {
                    vote = new ThumbVote(user:session.user, THUMBDOWN)
                    usercommentInstance.addToThumbvotes(vote)
                }
            }
        }
        redirect(controller: "article", action: "display", id: usercommentInstance.article.id)
    }

    def edit = {
        def usercommentInstance = Usercomment.get(params.id)
        if (!usercommentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(action: "list")
        }
        else {
            if(session.user?.id == usercommentInstance?.commentator?.id) {
                return [usercommentInstance: usercommentInstance]
            }
            else {
                flash.message = "${message(code: 'usercomment.nopermission.message', default: 'No permission!')}"
            }
        }
    }

    def update = {
        def usercommentInstance = Usercomment.get(params.id)
        def article = Article.get(params.article)
        if (usercommentInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (usercommentInstance.version > version) {

                    usercommentInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'usercomment.label', default: 'Usercomment')] as Object[], "Another user has updated this Usercomment while you were editing")
                    render(view: "edit", model: [usercommentInstance: usercommentInstance])
                    return
                }
            }
            //usercommentInstance.properties = params
            usercommentInstance.article = article
            usercommentInstance.reference = Usercomment.get(params.reference)
            usercommentInstance.title = params.title
            usercommentInstance.content = params.commentcontent
            if (!usercommentInstance.hasErrors() && usercommentInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), usercommentInstance.id])}"
                redirect(controller: "article", action: "display", id: usercommentInstance.article?.id)
            }
            else {
                render(view: "edit", model: [usercommentInstance: usercommentInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(controller: "article", action: "display", id: article?.id)
        }
    }

    def delete = {
        def usercommentInstance = Usercomment.get(params.id)
        def articleId = usercommentInstance?.article?.id
        if (usercommentInstance) {
            if (session?.user?.username == 'admin' || session?.user?.id == usercommentInstance?.commentator?.id) {
                try {
                    usercommentInstance.delete(flush: true)
                    flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
                    redirect(controller: "article", action: "display", id: articleId)
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
                    redirect(controller: "article", action: "display", id: articleId)
                }
            }
            else {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
                redirect(controller: "article", action: "display", id: articleId)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'usercomment.label', default: 'Usercomment'), params.id])}"
            redirect(controller: "article", action: "display", id: articleId)
        }
    }
}
