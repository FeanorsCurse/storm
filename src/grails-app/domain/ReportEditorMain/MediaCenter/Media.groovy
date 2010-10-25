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
import java.util.ArrayList
import ReportEditorMain.ArticleEditor.Article

/**
 *The class MediaCenter matches the "Mediathek" (media library) from the STORM concept paper.
 *All meta-data about the stored media-files is listed in this class.
 *Local files can be uploaded, supportet formats are common picture formats, mp3, ogg and flv.
 *Uploaded files are not stored in the database, but in the filesystem.
 *All media files have to be present in the media library in order to be used in articles.
 *@author Gerrit Meerpohl, Olaf Roeder
 */

class Media {

    //make this class searchable with the searchable plugin
    static searchable = [only: ['name','description']]

    String name //Titel
    String fileName //original name of the file
    String description //Beschreibung
    String alt //HTML Alt Text
    String url
    String fileType //file extension, e.g. jpg, gif, mp3
    String mediaType //file type, e.g. image, sound, video
    Date date
    //the following attributes are for external sources
    String externalUrl

    //variables for subdirectorys and ownership
    String userDirectory
    //owner
    User owner //the person who uploaded the file
    //allow others to see files?
    boolean isPublicMedia = true

    //special view in list.gsp that allows to assign files similar to a dbms view
    static hasMany = [articles:Article, views:ViewID] //List of articles the media files is used in

    static constraints = {
        //set the order
        name(unique:true, maxSize:200)
        fileName(nullable:false, blank:false, unique:true)
        description(nullable:true, blank:true, maxSize:1500)
        alt(nullable:true, blank:true)
        date(blank:true)
        userDirectory(nullable:false, blank:true)
        fileType(nullable:false, blank:false)
        mediaType(nullable:true, blank:true)
        url(nullable:false, blank:false)
        externalUrl(nullable:true, blank:true)
        owner(nullable:false, blank:false)
        isPublicMedia(nullable:false)
        articles(nullable:true)
        views(nullable:true)
    }
}
