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
 

package ReportEditorMain.MediaCenter

import systemadministration.usermanagement.User
import org.springframework.beans.factory.InitializingBean
import java.util.ArrayList
import ReportEditorMain.MediaCenter.*
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ReportEditor.Report;

/**
 * Service for the Media package.
 * @author Olaf Roeder
 */
class MediaService implements InitializingBean{
    def grailsApplication
    def setting
    //Rather than implementing a constructor for your service class, it is usually more useful to implement the Spring InitializingBean interface so that injected dependencies are available.
    void afterPropertiesSet()
    {
        this.setting = grailsApplication.config.setting
    }

    def articleService

    /**
     * This deletes a media file from the server's file system without redirecting to any view.
     */
    def boolean deleteWithoutRedirect (Media mediaInstance, File file, File thumb, User userInstance){
        if (userInstance.id==mediaInstance.owner.id && mediaInstance.articles.size()==0){
            if (mediaInstance) {
                try {
                    mediaInstance.delete(flush: true)
                    //flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    //flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                }
            } else {
                //flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                println "Media WARNING: media not found: "+mediaInstance
            }

            //delete files from filesystem
            if (file.exists()){
                if (file.delete()){
                    println "Media INFO: file "+file+" was deleted from filesystem"
                } else {
                    println "Media WARNING: file "+file+" was not deleted from filesystem"
                }
            }
            if (thumb.exists()){
                if (thumb.delete()){
                    println "Media INFO: file "+thumb+" was deleted from filesystem"
                } else {
                    println "Media WARNING: file "+thumb+" was not deleted from filesystem"
                }
            }
            return true
        } else {
            return false
        }
    }

    /**
     * This method searches the article content for file names and compares them to all media file names. If a match is found, that article is added to the media's article list.
     */
    def updateArticles(Article article, ArrayList mediaInstanceList){
        String str = article.content
        if (str==null){
            str = "";
        }
        String delims = "[\"<>/']+"; // use + to treat consecutive delims as one;
        // omit to treat consecutive delims separately
        String[] tokens = str.split(delims);

        ArrayList<String> files = new ArrayList<String>();
        boolean check=true;

        for(int i=0;i<tokens.length;i++){
            if (tokens[i].contains(".") && (!files.contains(tokens[i]))){
                check = ((files.add(tokens[i]))&&check);
            }
        }

        if (!check){
            println("Media WARNING: One or more tokens could not be added!");
        }

        for(Media media:mediaInstanceList){
            //check if one used file matches the media, if so, add it to the media's article list (add)
            if(files.contains(media.fileName)&&(!media.articles.contains(article))){
                media.addToArticles(article)
                if (!media.hasErrors() && media.save(flush: true)) {
                    println "Media INFO: "+media.fileName+" wurde gespeichert"
                }
                //make a note that the media is already added to an article
                for(ViewID view:media.views){
                    for(Article artikel:media.articles){
                        if(view.articleId==artikel.id){
                            view.added=true
                        }
                    }
                }
            }

            //check if all articles in the media's article list are still valid (remove)
            if(media.articles.contains(article)&&(!files.contains(media.fileName))){
                while(media.articles.contains(article)){
                    media.removeFromArticles(article)
                }
                if (!media.hasErrors() && media.save(flush: true)) {
                    println "Media INFO: "+media.fileName+" wurde gespeichert"
                }
            }
        }
    }

    /**
     * Organizes the report article view structure for the media list view.
     */
    def checkAndCreateViewIDs(){
        def reportList = Report.list()
        for (Iterator iterator = reportList.iterator(); iterator.hasNext();) {
            Report report = (Report) iterator.next();
            def articleInstanceList
            ViewID view
            String id = "viewID"

            if (ViewID.findByView(""+id+report.getRootArticle().id) == null){
                view = new ViewID(view:""+id+report.getRootArticle().id, reportId:report.id).save()
            } else {
                view = ViewID.findByView(""+id+report.getRootArticle().id)
            }

            articleInstanceList = articleService.buildTree(report)
            if (articleInstanceList.size()==0){
            } else {
                for (Iterator it2 = articleInstanceList.iterator(); it2.hasNext();){
                    Article article = (Article) it2.next()
                    if (ViewID.findByView(""+id+article.id) == null){
                        view = new ViewID(view:""+id+article.id, articleId:article.id).save()
                    } else {
                        view = ViewID.findByView(""+id+article.id)
                    }
                }
            }
        }
    }
}
