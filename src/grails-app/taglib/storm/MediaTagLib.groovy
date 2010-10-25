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

import ReportEditorMain.MediaCenter.*
import ReportEditorMain.ReportEditor.*;
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArticleService

/**
 * Tag-Lib for the media library.
 * <ul>
 * <li>reportArticleView: searches all articles for given reports</li>
 * <li>articleView: </li>
 * </ul>
 * @author Olaf Roeder
 */
class MediaTagLib{

    def articleService

    /**
     * This method searches all articles for given reports and builds a tree view which is displayed in the Media's list view.
     * @param report The report the articles are gotten from.
     */
    def reportArticleView = {attrs, body->
        def report = attrs["report"]
        def articleInstanceList
        ViewID view
        String id = "viewID"
		String mediaAdded =""

        def mediaInstanceList = Media.list()
        ArrayList<String> mediaViewList = new ArrayList<String>()

        for (Iterator iterator = mediaInstanceList.iterator();iterator.hasNext();){
            Media mediaInstance = (Media) iterator.next()
            if (mediaInstance.views != null && mediaInstance.views.size() > 0){
                mediaViewList.add(mediaInstance)
            }
        }

        out << "<li id=\""+id+report.getRootArticle().id+"\">"+"<a href=\"\" onclick=\"javascript:alterDisplay('"+id+report.getRootArticle().id+"div');return false;\">"+report.name+"</a>"//+" "+id+report.getRootArticle().id
        out << "<div id=\""+id+report.getRootArticle().id+"div\">"

        for (Iterator iterator = mediaViewList.iterator();iterator.hasNext();){
            Media mediaInstance = (Media)iterator.next()
            if (mediaInstance.views.view.contains(id+report.getRootArticle().id)){
				if (mediaInstance.articles.id.contains(report.id) && !report.status.toString().equals("Revision")){
							mediaAdded ="<img src=\"../images/schemaEditor/notundo.png\" title=\"\" alt=\"mediaAdded\" />"
						} else {
							mediaAdded =""
						}
                out << "<ul><li style=\"list-style-type:none\">&#160;&#160;-&#160;"+mediaInstance.name+" <a href=\"removeFromView?id="+mediaInstance.id+"&amp;view="+id+report.getRootArticle().id+"\" ><img src=\"../images/ReportEditor/delete.png\" title=\"\" alt=\"remove from view\" /></a>"+mediaAdded+"</li></ul>"
            }
        }

        if (ViewID.findByView(""+id+report.getRootArticle().id) == null){
            view = new ViewID(view:""+id+report.getRootArticle().id).save()
        } else {
            view = ViewID.findByView(""+id+report.getRootArticle().id)
        }

        articleInstanceList = articleService.buildTree(report)

        if (articleInstanceList.size()==0){
            out << "</div></li>"
        } else {
            out << "<ul>"
            for (Iterator it2 = articleInstanceList.iterator(); it2.hasNext();){
                Article article = (Article) it2.next()
                out << "<li id=\""+id+article.id+"\">" + article.number +" "+ "<a href=\"\" onclick=\"javascript:alterDisplay('article"+id+article.id+"div');return false;\">"+article.name+"</a></li>"// +" "+ id+article.id +"</li>"
                out << "<span id=\"article"+id+article.id+"div\">"
                for (Iterator iterator = mediaViewList.iterator();iterator.hasNext();){
                    Media mediaInstance = (Media)iterator.next()
                    if (mediaInstance.views.view.contains(id+article.id)){
						if (mediaInstance.articles.id.contains(article.id) && !article.status.toString().equals("Revision")){
							mediaAdded ="<img src=\"../images/schemaEditor/notundo.png\" title=\"\" alt=\"mediaAdded\" />"
						} else {
							mediaAdded =""
						}
                        out << "<li style=\"list-style-type:none\">&#160;&#160;-&#160;"+mediaInstance.name+" <a href=\"removeFromView?id="+mediaInstance.id+"&amp;view="+id+article.id+"\" ><img src=\"../images/ReportEditor/delete.png\" title=\"\" alt=\"remove from view\" /></a>"+mediaAdded+"</li>"
                    }
                }
                out << "</span>" //end articleViewIDxdiv
                if (ViewID.findByView(""+id+article.id) == null){
                    view = new ViewID(view:""+id+article.id).save()
                } else {
                    view = ViewID.findByView(""+id+article.id)
                }
            }
            out << "</ul></div></li>" //end report viewIDxdiv
        }
    }

    /**
     * This method creates a list of media files that are assigned to the article.
     * @param article The article to search for media files.
     */
    def articleView = {attrs, body->
        def article = attrs["article"]
        ViewID view
        String id = "viewID"
		String mediaAdded =""

        def mediaInstanceList = Media.list()
        ArrayList<String> mediaViewList = new ArrayList<String>()
        for (Iterator iterator = mediaInstanceList.iterator();iterator.hasNext();){
            Media mediaInstance = (Media) iterator.next()
            if (mediaInstance.views != null && mediaInstance.views.size() > 0){
                mediaViewList.add(mediaInstance)
            }
        }

        out << "<ul>"

        for (Iterator iterator = mediaViewList.iterator();iterator.hasNext();){
            Media mediaInstance = (Media)iterator.next()
            if (mediaInstance.views.view.contains(id+article.id)){
				if (mediaInstance.articles.id.contains(article.id) && !article.status.toString().equals("Revision")){
					mediaAdded ="<img src=\"../../images/schemaEditor/notundo.png\" title=\"\" alt=\"mediaAdded\" />"
				} else {
					mediaAdded =""
				}
                out << "<li>"+mediaInstance.name+" <a href=\"../removeFromView?id="+mediaInstance.id+"&amp;view="+id+article.id+"\" ><img src=\"../../images/ReportEditor/delete.png\" title=\"\" alt=\"remove from view\" /></a>"
				out << mediaAdded
                out <<"</li>"
            }
        }
        out << "</ul>"
    }

}
