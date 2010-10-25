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
 
import ReportEditorMain.ArticleEditor.Article
import interactiveFeatures.Help.*
import ReportEditorMain.ReportEditor.Report
import interactiveFeatures.Usercomments.Usercomment
import java.util.Date
class HomeController {
        //2010-06-22 17:42:27,570 [main] ERROR plugins.DefaultGrailsPlugin  - Cannot generate controller logic for scaffolded class true. It is not a domain class!
        //def scaffold = true
        def index = { //render view:"/index"
        redirect(controller:"welcome",action:"welcomePage")

        }
def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
         render (view: "infosearch", model:[helpArticleInstanceList: HelpArticle.list(params), helpArticleInstanceTotal: HelpArticle.count()])
    }

        def help = {


        }

// @Rachid: Search for Helparticle

        def infosearch = {
        render (view: "infosearch", model:[helpSectionInstanceList: HelpSection.list(params)])
}
         // Action for Impressum
       def impressum = {

        }

        // Action for newsletter
        def newsletter = {

        }

        //Action for AGB
        def agb = {

        }


        // Action for Contact

        def contact = {

        }


        // Action for privacy
        def privacy = {

        }

        // Action for Sitemap
    def sitemap = {

        }


// @Rachid: Show Helparticle
def helparticleshow = {
    def helpSectionInstance = HelpSection.get(params.id)
        if (!helpSectionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpSection.label', default: 'HelpSection'), params.id])}"
            redirect(action: "list")
        }
        else {
            [helpSectionInstance: helpSectionInstance]
        }

        }



 //@rachid
 def searchhelparticleshow= {

    def helpArticleInstance = HelpArticle.get(params.id)
        if (!helpArticleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'helpArticle.label', default: 'HelpArticle'), params.id])}"
            params.max = Math.min(params.max ? params.int('max') : 10, 100)
            [helpArticleInstanceList: HelpArticle.list(params), helpArticleInstanceTotal: HelpArticle.count()]

        }
        else {
            [helpArticleInstance: helpArticleInstance]
        }
        }



    //searchable plugin for Help
        def infoserchDetails = {
                def _searchkey =params.searchfor
                def _count=0
                params.searchfor = params.searchfor.trim()
                def tilde=false



        //if(params.suchType == article){
                if(params.searchfor && !params.searchfor.isEmpty()){
                println "Debug: 2nd search"
                        def searchResults = HelpArticle.searchEvery(params.searchfor, reload:true)
                        println "Debug: "+searchResults

                        if(searchResults){
                                flash.message = null
                                render(view: "infoserchDetails", model:[helpArticleInstanceList: searchResults, helpSectionInstanceList: HelpSection.list(params), searchkey:_searchkey, count:_count])
                        }

                        if(!searchResults){
                                searchResults = HelpArticle.searchEvery("*"+params.searchfor+"*", reload:true)
                        }

                        if(!searchResults){
                            def queryItems = new String(params.searchfor)
                                queryItems.split(" ").each{
                                        searchResults = HelpArticle.searchEvery(it+"~0.5", reload:true)
                                        println("tilde "+searchResults)

                                }
                                if(searchResults){
                                        tilde=true
                                }
                                flash.message = "${message(code: 'searchfor.search.results')}"
                        }

                        render(view: "infoserchDetails", model:[helpArticleInstanceList: searchResults, helpSectionInstanceList: HelpSection.list(params), searchkey:_searchkey, count:_count])
                }

        //}

        }

 /**
     * Action for the Grails Searchable Plugin. Three kinds of searches are performed, first an exact search, if no results are returned a wildcards searched follows, if again no results are returned a like search will be done.
 */


//@Rchid: searchable plugin for Article, Report and Kommentar
        def searchArticle = {

            // The requested name of Article
                def _searchkey = params.searchtext
                def _count=0
                def _searchType=params.searchType
                if (params.searchfor){
                params.searchfor = params.searchtext.trim()
                }
                def tilde=false


                if(params.searchtext && !params.searchtext.isEmpty()){
                println "Debug: 2nd search"

                       def searchResultsArticle = Article.searchEvery(params.searchtext, reload:true)
                       def searchResultsReport = Report.searchEvery(params.searchtext, reload:true)
                      def searchResultsComent = Usercomment.searchEvery(params.searchtext, reload:true)
                      def _result=null

                        println "Debug: "+searchResultsArticle


                       // Search article with using searchable plugin
                      if(searchResultsArticle){
                                flash.message = null
                                render(view: "searchArticle", model:[articleInstanceList: searchResultsArticle, searchkey:_searchkey, searchTyp:_searchType, count:_count])
                        }

                        if(!searchResultsArticle){
                                searchResultsArticle = Article.searchEvery("*"+params.searchtext+"*", reload:true)
                        }

                        if(!searchResultsArticle){
                            def queryItems = new String(params.searchtext)
                                queryItems.split(" ").each{
                                        searchResultsArticle =Article.searchEvery(it+"~0.5", reload:true)
                                        println("tilde "+searchResultsArticle)

                                }
                                if(searchResultsArticle){
                                        tilde=true
                                }
                                flash.message = "${message(code: 'searchtext.search.results')}"
                        }



            // Search Report with using searchable plugin

            if(searchResultsReport){
                                flash.message = null
                                render(view: "searchArticle", model:[reportInstanceList: searchResultsReport, searchkey:_searchkey, searchTyp:_searchType, count:_count])
                        }

                        if(!searchResultsReport){
                        searchResultsReport = Report.searchEvery("*"+params.searchtext+"*", reload:true)
                        }

                        if(!searchResultsReport){
                           def queryItems = new String(params.searchtext)
                        queryItems.split(" ").each{
                                searchResultsReport =Report.searchEvery(it+"~0.5", reload:true)
                                        println("tilde "+searchResultsReport)

                                }
                                if(searchResultsReport){
                                        tilde=true
                                }
                                flash.message = "${message(code: 'searchtext.search.results')}"
                }




         // Search Coment with using searchable plugin

                if(searchResultsComent){
                                flash.message = null
                                render(view: "searchArticle", model:[commentInstanceList: searchResultsComent, searchkey:_searchkey, searchTyp:_searchType, count:_count])
                        }

                        if(!searchResultsComent){
                        searchResultsComent = Usercomment.searchEvery("*"+params.searchtext+"*", reload:true)
                        }

                        if(!searchResultsComent){
                           def queryItems = new String(params.searchtext)
                        queryItems.split(" ").each{
                                 searchResultsComent = Usercomment.searchEvery(it+"~0.5", reload:true)
                                        println("tilde "+searchResultsComent)

                                }
                                if(searchResultsComent){
                                        tilde=true
                                }
                                flash.message = "${message(code: 'searchtext.search.results')}"
                }

                      render(view: "searchArticle", model:[articleInstanceList: searchResultsArticle, commentInstanceList:searchResultsComent, reportInstanceList: searchResultsReport, searchkey:_searchkey, searchTyp:_searchType, count:_count])
                }

        }


// @Rachid: autocomplete

// Auctocomplete for Article
    def autocomplete = {

    // Auctocomplete for Article: Name
    def resultList = Article.findAllBynameLike("${params.query}%")

       render(contentType: "text/xml") {
                results() {
                                resultList.each { word ->
                                result(){
                                                name(word.name)
                                        }
                                }
                        }
                }

        }



def helpautocomplete = {

                def resultList = HelpArticle.findAllBytitleLike("${params.query}%")
                render(contentType: "text/xml") {
                        results() {
                                resultList.each { word ->
                                        result(){
                                                name(word.name)
                                        }
                                }
                        }
                }

        }


}
