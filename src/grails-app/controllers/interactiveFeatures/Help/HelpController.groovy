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
 
package interactiveFeatures.Help

import java.util.Iterator;

import interactiveFeatures.Help.HelpArticle
import interactiveFeatures.Help.HelpSection

class HelpController {

        def index = {
                redirect(action: "overview", params: params)
        }

        def overview = {
                params.max = Math.min(params.max ? params.int('max') : 10, 100)
                [helpSectionList: HelpSection.list(params), helpSectionTotal: HelpSection.count()]
        }



        def section = {
                def helpSectionInstance = HelpSection.get(params.id)
                if (!helpSectionInstance) {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                        redirect(action: "overview")
                }
                else {
                        [helpSection: helpSectionInstance]
                }
        }

        def article = {
                def helpArticleInstance = HelpArticle.get(params.id)
                if (!helpArticleInstance) {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                        redirect(action: "overview")
                }
                else {
                        [helpArticle: helpArticleInstance]
                }
        }

        /**
         * show list of Helpsection
         */

        def listsections = {
                params.max = Math.min(params.max ? params.int('max') : 10, 100)
                [helpSectionInstanceList: HelpSection.list(params), helpSectionInstanceTotal: HelpSection.count()]
        }


        /**
         * Creat one Helpsection
         */

        def createsection = {
                def helpSectionInstance = new HelpSection()
                helpSectionInstance.properties = params
                return [helpSectionInstance: helpSectionInstance]
        }


        /**
         * Save one Helpsection  after creating
         */
        def savesection = {
                def helpSectionInstance = new HelpSection(params)
                if (helpSectionInstance.save(flush: true)) {
                        flash.message = "${message(code: 'default.created.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), helpSectionInstance.id])}"
                        redirect(action: "section", id: helpSectionInstance.id)
                }
                else {
                        render(view: "createsection", model: [helpSectionInstance: helpSectionInstance])
                }
        }

        /**
         * Edit  one Helpsection
         */

        def editsection = {
                def helpSectionInstance = HelpSection.get(params.id)
                if (!helpSectionInstance) {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                        redirect(action: "listsections")
                }
                else {
                        return [helpSectionInstance: helpSectionInstance]
                }
        }

        /**
         * Update one Helpsection
         */

        def updatesection = {
                def helpSectionInstance = HelpSection.get(params.id)
                if (helpSectionInstance) {
                        if (params.version) {
                                def version = params.version.toLong()
                                if (helpSectionInstance.version > version) {

                                        helpSectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'helpSection.label', default: 'HelpSection')] as Object[], "Another user has updated this HelpSection while you were editing")
                                        render(view: "editsection", model: [helpSectionInstance: helpSectionInstance])
                                        return
                                }
                        }
                        helpSectionInstance.properties = params
                        if (!helpSectionInstance.hasErrors() && helpSectionInstance.save(flush: true)) {
                                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), helpSectionInstance.id])}"
                                redirect(action: "section", id: helpSectionInstance.id)
                        }
                        else {
                                render(view: "editsection", model: [helpSectionInstance: helpSectionInstance])
                        }
                }
                else {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                        redirect(action: "listsections")
                }
        }


        /**
         * Delet one Helpsection  from listsections
         */

        def deletesection = {
                def helpSectionInstance = HelpSection.get(params.id)
                if (helpSectionInstance) {
                        try {
                                helpSectionInstance.delete(flush: true)
                                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                        }
                        catch (org.springframework.dao.DataIntegrityViolationException e) {
                                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                        }
                }
                else {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
                }
                redirect(action: "listsections")
        }


        /**
         * show List of Helparticles
         */

        def listarticles = {
                params.max = Math.min(params.max ? params.int('max') : 10, 100)
                [helpArticleInstanceList: HelpArticle.list(params), helpArticleInstanceTotal: HelpArticle.count()]
        }


        /**
         * Creat one Helparticle
         */

        def createarticle = {
                def helpArticleInstance = new HelpArticle()
                helpArticleInstance.properties = params
                return [helpArticleInstance: helpArticleInstance]
        }



        /**
         * Save one Helpsection
         */

        def savearticle = {
                def helpArticleInstance = new HelpArticle(params)
                if (helpArticleInstance.save(flush: true)) {
                        flash.message = "${message(code: 'default.created.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), helpArticleInstance.id])}"
                        redirect(action: "article", id: helpArticleInstance.id)
                }
                else {
                        render(view: "createarticle", model: [helpArticleInstance: helpArticleInstance])
                }
        }


        /**
         * Edit one Helpsection
         */

        def editarticle = {
                def helpArticleInstance = HelpArticle.get(params.id)
                if (!helpArticleInstance) {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                        redirect(action: "listarticles")
                }
                else {
                        return [helpArticleInstance: helpArticleInstance]
                }
        }


        /**
         * updatearticle ist using to upadate one Helparticle.
         */

        def updatearticle = {
                def helpArticleInstance = HelpArticle.get(params.id)
                if (helpArticleInstance) {
                        if (params.version) {
                                def version = params.version.toLong()
                                if (helpArticleInstance.version > version) {

                                        helpArticleInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'helpArticle.label', default: 'HelpArticle')] as Object[], "Another user has updated this HelpArticle while you were editing")
                                        render(view: "editarticle", model: [helpArticleInstance: helpArticleInstance])
                                        return
                                }
                        }
                        helpArticleInstance.properties = params
                        if (!helpArticleInstance.hasErrors() && helpArticleInstance.save(flush: true)) {
                                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), helpArticleInstance.id])}"
                                redirect(action: "article", id: helpArticleInstance.id)
                        }
                        else {
                                render(view: "editarticle", model: [helpArticleInstance: helpArticleInstance])
                        }
                }
                else {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                        redirect(action: "listarticles")
                }
        }


        /**
         * deletearticle ist using to delet Helparticle from List.
         */

        def deletearticle = {
                def helpArticleInstance = HelpArticle.get(params.id)
                if (helpArticleInstance) {
                        try {
                                helpArticleInstance.delete(flush: true)
                                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                        }
                        catch (org.springframework.dao.DataIntegrityViolationException e) {
                                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                        }
                }
                else {
                        flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
                }
                redirect(action: "listarticles")
        }

        // @Rachid: Search for Helparticle using searchable Plugin
        def search = {
                def _count=0
                //The requested Helparticle
                def searchfor = params.searchfor?.trim()
                def tilde=false
                ArrayList searchResults = new ArrayList();

                if(searchfor && !searchfor.isEmpty()){

                        searchResults = HelpArticle.searchEvery(searchfor, reload:true)

                        if(!searchResults){
                                searchResults = HelpArticle.searchEvery("*"+searchfor+"*", reload:true)
                        }

                        if(!searchResults){
                                def queryItems = new String(searchfor)
                                queryItems.split(" ").each{
                                        searchResults = HelpArticle.searchEvery(it+"~0.5", reload:true)
                                }
                                if(searchResults){
                                        tilde=true
                                }
                                flash.message = "${message(code: 'searchfor.search.results')}"
                        }
                }
                for (Iterator iterator = searchResults.iterator(); iterator.hasNext();) {
                        HelpArticle article = (HelpArticle) iterator.next();
                        System.out.println(article.title);
                }

                [helpArticleInstanceList: searchResults, helpSectionInstanceList: HelpSection.list(params), count:_count]
        }

        /**
         * autoComplete for Helparticle.
         */
        def autocomplete = {

                def resultList = HelpArticle.findAllByTitleLike("${params.query}%")
                render(contentType: "text/xml") {
                        results() {
                                resultList.each { word ->
                                        result(){
                                                title(word.name)
                                        }
                                }
                        }
                }

        }

}
