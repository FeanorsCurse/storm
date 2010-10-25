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
 
package storm
import java.util.ArrayList;


import ReportEditorMain.ReportEditor.*;
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.Enum.ArticleStatus

import java.util.ArrayList;

import ReportEditorMain.MediaCenter.*
import ReportEditorMain.ReportEditor.*;
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArticleService

/**
 * Tag-Lib for Reports
 *
 * @author Irene, Gerrit
 */
class ReportTagLib {

        //import ArticleService
        def articleService

        /**
         * Tag for breadcrumb. Lists parent Articles and the Root-Report for a specific Article
         */
        def breadcrumb = {attrs, body->
                Article article = attrs["article"]
                ArrayList list = articleService.getParent(new ArrayList(),article);
                if(list.size()>0){
                        for (int i = list.size()-1; i >= 0; i--) {
                                if(list.get(i)instanceof Article){
                                        Article a = (Article) list.get(i)
                                        String name = a.name
                                        out << '<a href="../../article/display/'+a.id+'">'+name+'</a>'
                                }else{
                                        Report r = (Report) list.get(i)
                                        String name = r.name
                                        out << '<a href="../../report/display/'+r.id+'">'+name+'</a>'
                                }
                                if(i>0){
                                        out << " > "
                                }
                        }
                }
        }

        /**
         * This tag shows the sidebar for all reports.
         */
        def reportSidebar = { attrs, body->
                def report = attrs["report"]
                int status = attrs["status"]

                def parentArticle = report.rootArticle
                def root = report.rootArticle
                def reportId = String.valueOf(report.id)

                // show reports
                def active=""

                if(params.id==reportId){
                        active = "class=\"active\" "
                }
                def output = """
                <li>
                        <a """+active+"""href=\""""+createLink(action:'display', controller:'report', id:report.id)+"""\">
                                """+ report.name +"""
                        </a>
                </li>
                """
                out << output
                if(params.id==reportId|| params.controller=='article' ||(status==0&&params.controller!='report')){
                        if(((params.controller=='report' || params.controller=='article')&&params.action=='display')||status==0){
                                reportSidebarIntern(parentArticle, root)
                        }
                }
        }





        /**
         * Internal Function with recursive invocation. Called from reportSidebar.
         * @param parentArticle: Parent Article
         *
         * @return output for the gsp
         */
        private reportSidebarIntern(parentArticle, root){
                def children = Article.findAllByParentArticleAndStatusNotEqual(parentArticle,ArticleStatus.findByStatus("Revision"),  [sort:"sequence", order:"asc"])
                if(children.size>0){
                        boolean open=true;
                        int i=0
                        for (Iterator iterator = children.iterator(); iterator.hasNext();) {
                                Article article = (Article) iterator.next();
                                if(article.status.status=="Approved"){
                                        def articleId = String.valueOf(article.id)
                                        def selectedArticle=root
                                        if(params.controller=='article'&&params.action=='display'){
                                                selectedArticle = Article.findById(params.id)
                                        }
                                        //Show self, parents, parentsiblings, siblings and children
                                        if(selectedArticle){
                                                if(params.id==articleId||articleService.getParentAndSiblingArticles(selectedArticle)?.contains(article)||articleService.getSiblingArticles(selectedArticle)?.contains(article)||articleService.getChildrenArticles(selectedArticle)?.contains(article)){
                                                        //if first child open new list
                                                        if(open){
                                                                out << "<li><ul>"
                                                                open =false;
                                                        }

                                                        //Generate number
                                                        i++
                                                        if(parentArticle.number==""||parentArticle.number==null){
                                                                article.number= i //Increment last Number + 1
                                                        }else{
                                                                article.number= parentArticle.number+"."+i //Increment last Number + 1
                                                        }
                                                        def active=""
                                                        if(params.id==articleId){
                                                                active = "class=\"active\" "
                                                        }
                                                        def output = """
                                                                        <li>
                                                                                <a """+active+"""href=\""""+createLink(action:'display', controller:'article', id:article.id)+"""\">
                                                                                        """+article.number+" "+article.name +"""
                                                                                </a>
                                                                        </li>
                                                                        """
                                                        out << output
                                                        reportSidebarIntern(article, root)
                                                }
                                        }
                                }
                        }
                        //if last child close list
                        if(!open){
                                out << "</ul></li>"
                        }
                }


     }



     /**
         * Search Report
         *
         * @author: Rachid
         */



     def reportArticleSearch = {attrs, body->
        def report = attrs["report"]
        def articleInstanceList
        ViewID view
        String id = "viewID"

        def mediaInstanceList = Media.list()
        ArrayList<String> mediaViewList = new ArrayList<String>()

                out << "<li>"+report.name



                articleInstanceList = articleService.buildTree(report)
                if (articleInstanceList.size()==0){
                        out << "</li>"
                } else {
                out << "<ul>"
                for (Iterator it2 = articleInstanceList.iterator(); it2.hasNext();){
                        Article article = (Article) it2.next()
                        out << "<li>" + article.number +" "+ "<a href=\""+createLink(action:'display', controller:'article', id:article.id)+"\">"+article.name+"</a></li>"
                        
                }
                out << "</ul></div></li>" //end report viewIDxdiv
                }
}




     }

