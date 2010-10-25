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
import java.util.Date
import imagetools.ImageTool
import systemadministration.usermanagement.User
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ReportEditor.Report
import ReportEditorMain.MediaCenter.ViewID

/**
 * This controller holds all actions used in the <i>Media Library</i>
 * <ul>
 * <li>index: same as list</li>
 * <li>thumb: lists all media files, without special functions</li>
 * <li>list: lists all media files</li>
 * <li>listThumbs: controls wether images or other media files should be listed for the Rich Text Editor's media plugin</li>
 * <li>searchFilter: organizes search results</li>
 * <li>searchAJAX: autocomplete for search</li>
 * <li>create: save a local media to the server</li>
 * <li>save: save a local media to the server</li>
 * <li>download: save an external media to the server</li>
 * <li>show: shows a single media</li>
 * <li>update: edit a media</li>
 * <li>edit: edit a media</li>
 * <li>delete: delete a media</li>
 * <li>actionChecked: deletes a chosen list of media</li>
 * <li>updateArticles: updates the list of referenced articles</li>
 * <li>addToView: adds a media to a view</li>
 * <li>removeFromView: removes a media from a view</li>
 * </ul>
 *@author Olaf Roeder
 **/
class MediaController {

    static allowedMethods = [save: "POST", update: "POST", delete: ["POST", "GET"]]
    def mediaService

    /**
     * Standard index action.
     */
    def index = {
        redirect(action: "list", params: params)
    }

    /**
     * Lists all media files, without special functions.
     */
    def thumb = {
        [mediaInstanceList: Media.list(params)]
    }

    /**
     * Standard list action, but with filter and search functionality added.
     */
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def reportList = Report.list() //reportList is needed for Report Structure View
        def articleList = Article.list() //articleList is needed for Report Structure View
        mediaService.checkAndCreateViewIDs() //ViewIDs are needed in the list.gsp to add media to views
        def viewIDList = ViewID.list()

        if (params.mediaType==null || params.mediaType=='all' || params.mediaType==''){
            [mediaInstanceList: Media.list(params), mediaInstanceTotal: Media.count(), reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList]
        } else if(params.mediaType=='img'){
            def imgList = Media.findAllByMediaType('image')
            def result = Media.createCriteria().list(max: 10, offset:params?.offset) {
                like("mediaType","image")
            }
            [mediaInstanceList: result, mediaInstanceTotal: result.totalCount as Integer, reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList]
        } else if(params.mediaType=='vid'){
            def imgList = Media.findAllByMediaType('video')
            def result = Media.createCriteria().list(max: 10, offset:params?.offset) {
                like("mediaType","video")
            }
            [mediaInstanceList: result, mediaInstanceTotal: result.totalCount as Integer, reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList]
        } else if(params.mediaType=='snd'){
            def imgList = Media.findAllByMediaType('sound')
            def result = Media.createCriteria().list(max: 10, offset:params?.offset) {
                like("mediaType","sound")
            }
            [mediaInstanceList: result, mediaInstanceTotal: result.totalCount as Integer, reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList]
        } else if(params.mediaType=='other'){
            def imgList = Media.findAllByMediaType('other')
            def result = Media.createCriteria().list(max: 10, offset:params?.offset) {
                like("mediaType","other")
            }
            [mediaInstanceList: result, mediaInstanceTotal: result.totalCount as Integer, reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList]
        }
    }

    /**
     * Lists thumbnails of media during the Rich Text Editor media insertion dialogue.
     */
    def listThumbs = {
        if(params.type=='image'){
            def imgList = Media.findAllByMediaType('image')
            render(template: "/media/templates/showMediaThumbs", model: [mediaInstanceList:imgList, mediaInstanceTotal: imgList.count()])
        } else if (params.type=='media'){
            def mediaList = Media.findAllByMediaTypeInList(['sound', 'video', 'other'])
            render(template: "/media/templates/showMediaThumbs", model: [mediaInstanceList:mediaList, mediaInstanceTotal: mediaList.count()])
        }
    }

    /**
     * Action for the Grails Searchable Plugin. Three kinds of searches are performed, first an exact search, if no results are returned a wildcards searched follows, if again no results are returned a like search will be done.
     */
    def searchFilter = {
		def reportList = Report.list() //reportList is needed for Report Structure View
        def articleList = Article.list() //articleList is needed for Report Structure View
        def viewIDList = ViewID.list()
		
        if(!params.mediaSearch.equals("") && !params.mediaSearch==null) params.mediaSearch = params.mediaSearch.trim()
        if(!params.mediaSearch){
            render (view: "list", model:[mediaInstanceList: Media.list(params), mediaInstanceTotal:Media.count(), reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList])
        }
        def tilde=false
        if(params.mediaSearch && !params.mediaSearch.isEmpty()){
            def searchResults = Media.searchEvery(params.mediaSearch, reload:true)

            if(searchResults){
                flash.info = "${message(code: 'mediaSearch.search.results', default: 'Search Results')}"
                def resultsTotal = searchResults
                render(view:"list", model:[mediaInstanceList: searchResults, mediaInstanceTotal:resultsTotal.size(), reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList])
            }

            if(!searchResults){
                searchResults = Media.searchEvery("*"+params.mediaSearch+"*", reload:true)
            }

            if(!searchResults){//2. search as like, means ~string~
                def queryItems = new String(params.mediaSearch)
                queryItems.split(" ").each{
                    searchResults = Media.searchEvery(it+"~0.5", reload:true)
                }
                if(searchResults){
                    tilde=true
                }
                flash.info = "${message(code: 'mediaSearch.search.results', default: 'Search Results')}"
            }

            def resultsTotal = searchResults
            render(view:"list", model:[mediaInstanceList: searchResults, tilde:tilde, mediaInstanceTotal:resultsTotal.size(), reportInstanceList:reportList, viewIDInstanceList:viewIDList, articleInstanceList:articleList])
        }
    }

    /**
     * Action to generate search autocompletion.
     */
    def searchAJAX = {
        def mediaList = Media.findAllByNameLike("${params.query}%")

        //Create XML response
        render(contentType: "text/xml") {
            results() {
                mediaList.each { media ->
                    result(){
                        name(media.name)
                        //optional id which will be available in onItemSelect
                        id(media.id)
                    }
                }
            }
        }
    }

    /**
     * Standard create action.
     */
    def create = {
        def mediaInstance = new Media()
        mediaInstance.properties = params
        return [mediaInstance: mediaInstance]
    }

    /**
     * Save action. Saves a file from the user's local file system to the Grails server's file system (it uploads the file). If the file is an image type, a thumbnail will be generated.
     */
    def save = {
        def mediaInstance = new Media(params)
        def webRootDir = servletContext.getRealPath("/") //path to filesystem root
        def userInstance = session.user
        def uploadDir = new File(webRootDir, "/Mediathek/"+userInstance.username) //create directory ... serverroot/Mediathek/username
        def thumbDir = new File(webRootDir, "/Mediathek/"+userInstance.username+"/imgthumbs/") //create directory ... serverroot/Mediathek/username/imgthumbs
        def cleanFileName //file name without whitespace and special characters
        userInstance.createUserDirectory(webRootDir.toString(), userInstance.username)

        //handle uploaded file
        Date s = new Date()
        def uploadedFile = request.getFile('uploadfile')
        s = new Date()

        if(!uploadedFile.empty){
            cleanFileName = uploadedFile.originalFilename.replaceAll("[^-._a-zA-Z0-9]+","_")
            uploadedFile.transferTo( new File( uploadDir, cleanFileName))
            mediaInstance.date = s
            mediaInstance.fileName = cleanFileName
            def extension = uploadedFile.originalFilename.split("\\.")[-1]
            mediaInstance.fileType = extension.toLowerCase()
            mediaInstance.userDirectory = userInstance.username
            mediaInstance.url = "${resource(dir: '/Mediathek/'+userInstance.username,file:''+cleanFileName)}"
            mediaInstance.owner = session.user
            if(mediaInstance.alt==''||mediaInstance.alt==null) mediaInstance.alt = uploadedFile.originalFilename
            if(mediaInstance.name==''||mediaInstance.name==null) mediaInstance.name = uploadedFile.originalFilename
            if(mediaInstance.description==''||mediaInstance.description==null) mediaInstance.description = uploadedFile.originalFilename

            //set mediaType
            if(mediaInstance.fileType=="jpg"
                ||mediaInstance.fileType=="png"
                ||mediaInstance.fileType=="gif"
                ||mediaInstance.fileType=="jpeg"
                ||mediaInstance.fileType=="bmp"
                ||mediaInstance.fileType=="tif"){
                mediaInstance.mediaType="image"
            } else if(mediaInstance.fileType=="mp3"
                ||mediaInstance.fileType=="ogg"){
                mediaInstance.mediaType="sound"
            } else if(mediaInstance.fileType=="flv"
                ||mediaInstance.fileType=="wmv"
                ||mediaInstance.fileType=="avi"
                ||mediaInstance.fileType=="mpg"
                ||mediaInstance.fileType=="mpeg"
                ||mediaInstance.fileType=="mov"
				||mediaInstance.fileType=="rm"
				||mediaInstance.fileType=="swf"){
                mediaInstance.mediaType="video"
            } else mediaInstance.mediaType="other"

            //make a thumbnail
            if(mediaInstance.fileType=="jpg"
                ||mediaInstance.fileType=="png"
                ||mediaInstance.fileType=="gif"
                ||mediaInstance.fileType=="jpeg"){
                def imageTool = new ImageTool()
                imageTool.load(""+uploadDir.toString()+"/"+mediaInstance.fileName)
                imageTool.thumbnailSpecial(50,50,1,2)
                imageTool.writeResult(""+thumbDir.toString()+"/"+"thumb"+mediaInstance.fileName, "JPEG")
            }
        } else { //uploadfile is empty
			flash.message = "${message(code: 'media.upload.error2', args: [message(code: 'media.label', default: 'File'), mediaInstance.name])}"
			redirect(action: "create", params: params)
		}

        if (mediaInstance.save(flush: true)) {
            flash.info = "${message(code: 'default.created.message', args: [message(code: 'media.label', default: 'File'), mediaInstance.name])}"
            redirect(action: "show", id: mediaInstance.id)
        }
        else {
            render(view: "create", model: [mediaInstance: mediaInstance])
        }
    }

    /**
     * Save action. Saves a file from a given URL to the Grails server's file system (it downloads the file). If the file is an image type, a thumbnail will be generated.
     */
    def download = { //downloads a file from an external source
        def mediaInstance = new Media(params)
        def webRootDir = servletContext.getRealPath("/")
        def userInstance = session.user
        def uploadDir = new File(webRootDir, "/Mediathek/"+userInstance.username)
        def thumbDir = new File(webRootDir, "/Mediathek/"+userInstance.username+"/imgthumbs/")
        userInstance.createUserDirectory(webRootDir.toString(), userInstance.username)
        Date s = new Date()

        //handle external files
        if(!(params.externalUrl == null) && !params.externalUrl.equals("")){
            def externalUrlName = params.externalUrl.tokenize("/")[-1]
            def file = new FileOutputStream(uploadDir.toString()+"/"+params.externalUrl.tokenize("/")[-1].replaceAll("[^-._a-zA-Z0-9]+","_"))

            def out = new BufferedOutputStream(file)
            out << new URL(params.externalUrl).openStream()
            out.close()
            def externalFile = new File(uploadDir.toString()+"/"+params.externalUrl.tokenize("/")[-1].replaceAll("[^-._a-zA-Z0-9]+","_"))

            if(externalFile.exists()){

                mediaInstance.fileName = externalFile.name
                def extension = externalFile.name.split("\\.")[-1]
                mediaInstance.fileType = extension.toLowerCase()
                mediaInstance.userDirectory = userInstance.username
                mediaInstance.url = "${resource(dir: '/Mediathek/'+userInstance.username,file:''+externalFile.name)}"
                mediaInstance.owner = session.user
                mediaInstance.date = s
                mediaInstance.name = params.name
                mediaInstance.alt = params.alt
                mediaInstance.description = params.description

                if(mediaInstance.alt==''||mediaInstance.alt==null) mediaInstance.alt = externalFile.name
                if(mediaInstance.name==''||mediaInstance.name==null) mediaInstance.name = externalFile.name
                if(mediaInstance.description==''||mediaInstance.description==null) mediaInstance.description = externalFile.name
            }else{
                flash.message = "${message(code: 'media.download.error', args: [message(code: 'media.label', default: 'File'), mediaInstance.name])}"
            }

            //set mediaType
            if(mediaInstance.fileType=="jpg"
                ||mediaInstance.fileType=="png"
                ||mediaInstance.fileType=="gif"
                ||mediaInstance.fileType=="jpeg"
                ||mediaInstance.fileType=="bmp"
                ||mediaInstance.fileType=="tif"){
                mediaInstance.mediaType="image"
            } else if(mediaInstance.fileType=="mp3"
                ||mediaInstance.fileType=="ogg"){
                mediaInstance.mediaType="sound"
            } else if(mediaInstance.fileType=="flv"
                ||mediaInstance.fileType=="wmv"
                ||mediaInstance.fileType=="avi"
                ||mediaInstance.fileType=="mpg"
                ||mediaInstance.fileType=="mpeg"
                ||mediaInstance.fileType=="mov"
				||mediaInstance.fileType=="rm"
				||mediaInstance.fileType=="swf"){
                mediaInstance.mediaType="video"
            } else mediaInstance.mediaType="other"

            //make a thumbnail
            if(mediaInstance.fileType=="jpg"
                ||mediaInstance.fileType=="png"
                ||mediaInstance.fileType=="gif"
                ||mediaInstance.fileType=="jpeg"){
                def imageTool = new ImageTool()
                imageTool.load(""+uploadDir.toString()+"/"+mediaInstance.fileName)
                imageTool.thumbnailSpecial(50,50,1,2)
                imageTool.writeResult(""+thumbDir.toString()+"/"+"thumb"+mediaInstance.fileName, "JPEG")
            }
        } else { //no URL
			flash.message = "${message(code: 'media.upload.error2', args: [message(code: 'media.label', default: 'File'), mediaInstance.name])}"
			redirect(action: "create", params: params)
		}

        if (mediaInstance.save(flush: true)) {
            flash.info = "${message(code: 'default.created.message', args: [message(code: 'media.label', default: 'File'), mediaInstance.name])}"
            redirect(action: "show", id: mediaInstance.id)
        }
        else {
            render(view: "create", model: [mediaInstance: mediaInstance])
        }
    }

    /**
     * Standard show action.
     */
    def show = {
        def mediaInstance = Media.get(params.id)
        if (!mediaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            [mediaInstance: mediaInstance]
        }
    }

    /**
     * Standard edit action.
     */
    def edit = {
        def mediaInstance = Media.get(params.id)
        if (!mediaInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [mediaInstance: mediaInstance]
        }
    }

    /**
     * Standard update action.
     */
    def update = {
        def mediaInstance = Media.get(params.id)
        def owner = mediaInstance.owner.username
        def sessionUser = session.user


        if (mediaInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (mediaInstance.version > version) {

                    mediaInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'media.label', default: 'Media')] as Object[], "Another user has updated this Media while you were editing")
                    render(view: "edit", model: [mediaInstance: mediaInstance])
                    return
                }
            }

            mediaInstance.properties = params
            if (!mediaInstance.hasErrors() && mediaInstance.save(flush: true)) {
                flash.info = "${message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])}"
                redirect(action: "show", id: mediaInstance.id)
            }
            else {
                render(view: "edit", model: [mediaInstance: mediaInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
    }

    /**
     * Standard delete action. Files are deleted from the server's filesystem, too.
     */
    def delete = {
        def mediaInstance = Media.get(params.id)
        def webRootDir = servletContext.getRealPath("/")
        def userInstance = session.user
        def uploadDir = new File(webRootDir, "/Mediathek/"+userInstance.username)
        def thumbDir = new File(webRootDir, "/Mediathek/"+userInstance.username+"/imgthumbs/")
        def file = new File(uploadDir.toString()+"/"+mediaInstance.fileName)
        def thumb = new File(thumbDir.toString()+"/"+"thumb"+mediaInstance.fileName)
		
		//check if file is used
		if (mediaInstance.articles.size()!=0){
			flash.message = "${message(code: 'media.editing.error.3', args: [message(code: 'media.label', default: 'Media'), params.id], default: 'Media is used and can not be deleted.')}"
            return redirect(action: "list")
		}
		
		//check if user is owner
        if (userInstance.id==mediaInstance.owner.id){
            if (mediaInstance) {
                try {
                    mediaInstance.delete(flush: true)
                    flash.info = "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                    redirect(action: "list")
                }
                catch (org.springframework.dao.DataIntegrityViolationException e) {
                    flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                    redirect(action: "show", id: params.id)
                }
            }
            else {
                flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])}"
                redirect(action: "list")
            }


            //delete files from filesystem
            if (file.delete()){
                println "Media INFO: file "+file+" was deleted from filesystem"
            } else {
                println "Media WARNING: file "+file+" was not deleted from filesystem"
            }
            if (thumb.delete()){
                println "Media INFO: file "+thumb+" was deleted from filesystem"
            } else {
                println "Media WARNING: file "+thumb+" was not deleted from filesystem"
            }

        } else {
            flash.message = "${message(code: 'media.editing.error.2', args: [message(code: 'media.label', default: 'Media'), params.id])}"
            redirect(action: "list")
        }
    }

    /**
     * Multiple delete action, all media files that are checked will be deleted.
     */
    def actionChecked = {
        def webRootDir = servletContext.getRealPath("/")
        def userInstance = session.user
        def uploadDir = new File(webRootDir, "/Mediathek/"+userInstance.username)
        def thumbDir = new File(webRootDir, "/Mediathek/"+userInstance.username+"/imgthumbs/")
		boolean arr = true //params.checkedBoxes is an array

        //delete checked articles
        if(params.checkedBoxes){
            if (params.checkedBoxes.toString().contains("[")){
				arr = true
			} else {
				arr = false
			}
			String[] checkboxes = params.checkedBoxes
            flash.message = ""
            flash.info = ""
            if(arr){
				for (int i = 0; i < checkboxes.length; i++) {
					def mediaInstance = Media.findById(checkboxes[i])
					def file = new File(uploadDir.toString()+"/"+mediaInstance.fileName)
					def thumb = new File(thumbDir.toString()+"/"+"thumb"+mediaInstance.fileName)

					if(mediaService.deleteWithoutRedirect(mediaInstance, file, thumb, userInstance)){
						flash.info += "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])} "
					} else {
						flash.message += "${message(code: 'media.editing.error.4', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])} "
					}
				}
			} else {
				def mediaInstance = Media.findById(params.checkedBoxes)
				def file = new File(uploadDir.toString()+"/"+mediaInstance.fileName)
				def thumb = new File(thumbDir.toString()+"/"+"thumb"+mediaInstance.fileName)
				if(mediaService.deleteWithoutRedirect(mediaInstance, file, thumb, userInstance)){
						flash.info += "${message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])} "
					} else {
						flash.message += "${message(code: 'media.editing.error.4', args: [message(code: 'media.label', default: 'Media'), mediaInstance.id])} "
					}
			}
        }
        redirect(action: "list")
    }

    /**
     * Updates the list of referenced articles.
     */
    def updateArticles = {
        def mediaInstanceList = Media.findAll();
        mediaService.updateArticles(Article.findById(params.articleid), mediaInstanceList)
        flash.info = "${message(code: 'media.articles.updated', args: [message(code: 'media.label', default: 'Media'), params.id])}"
        redirect(action: "list")
    }

    /**
     * Adds the specified media to the specified view.
     */
    def addToView = {
        def mediaInstance = Media.findById(params.id)
        ViewID view = ViewID.findByView(params.view)
        if (view == null){
            view = new ViewID(view:params.view).save()
        }
        mediaInstance.addToViews(view)
        redirect(action: "list")
    }

    /**
     * Removes the specified media from the specified view.
     */
    def removeFromView = {
        def mediaInstance = Media.findById(params.id)
        ViewID view = ViewID.findByView(params.view)
        mediaInstance.removeFromViews(view)
        redirect(action: "list")
    }
}
