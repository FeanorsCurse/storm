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
 * This tag library provides tags to show the uploaded media files.
 * @author Olaf Roeder
 */

package storm

class ShowMediaTagLib {

    /**
     *This method generates html code to show the associated media file,
     *usage: <g:displayFile fileName="${mediaInstance.fileName}" alt="${mediaInstance.alt}" /> where mediaInstance is an instance of the media class.
	 *Supported file formats are:
	 *<ul>
	 *<li>wmv</li>
	 *<li>mpg</li>
	 *<li>avi</li>
	 *<li>rm</li>
	 *<li>mov</li>
	 *<li>ogg</li>
	 *<li>mp3</li>
	 *<li>swf</li>
	 *<li>gif</li>
	 *<li>jpg</li>
	 *<li>png</li>
	 *</ul>
     *@see Media.groovy
     *@param fileName="${mediaInstance.fileName}"
     *@param alt="${mediaInstance.alt}" where mediaInstance is an instance of the media class
     *@return String html code to show the associated media file
     */
    def displayFile = {attrs, body->
        def media = attrs["media"]
        def alt = media.alt
        def fileName = media.fileName
        def title = media.name
        def userDir = media.userDirectory

        if(fileName){
            def extension = fileName.split("\\.")[-1]

            switch(extension.toUpperCase()){
                case ["JPG", "PNG", "GIF", "JPEG"]://Note: if you add a case here, add it throughout the whole project!
                def picture = """
				<div class="media">
				<a href="${createLinkTo(dir:'Mediathek/'+userDir+'/',
										file:''+fileName)}"
				target="_blank">
				<img src="${createLinkTo(dir:'Mediathek/'+userDir+'/',
										file:''+fileName)}"
				alt="${alt}"
				title="${alt}" />
				</a>
				</div>
				"""

                if (media.owner.username==session.user.username || media.isPublicMedia) out << picture
                break

                case ["FLV"]:
                def pathToPlayer = "${resource(dir:'js/flvplayer/flvplayer-1.9.7b/flvplayer/',file:'')}"
                def pathToPlayerApp = "${resource(dir:'js/flvplayer/flvplayer-1.9.7b/flvplayer/',file:'swfobject.js')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
                def video = """
<script type="text/javascript" src=\""""+pathToPlayerApp+"""\"></script>
<script type="text/javascript">
var playervars = false;
swfobject.embedSWF(\""""+pathToPlayer+"""flvplayer.swf?contentpath="""+pathToFile+"""&preview=&video="""+fileName+"""&captions=&preroll=&autoplay=&loop=&islive=&volume=&smoothing=true&autoscale=true&videowidth=&videoheight=&skincolor=&skinscalemaximum=&skin=&preloader=&buttonoverlay=&ending=&debug=", "videoCanvas", "640", "530", "9.0.28", "expressInstall.swf", playervars, { scale: "noscale", allowfullscreen: "true",salign: "tl", bgcolor: "#FFFFFF", base: "."},  { align: "left"});
</script>
<div class="media">
<div id="videoCanvas">
<p>This <a href="http://www.video-flash.de/flv-flash-fullscreen-video-player/">Flash Video Player</a> requires the Adobe Flash Player.</p>
<p><a href="http://www.adobe.com/go/getflashplayer" target="_blank">
<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
</div>
</div>
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << video
                break

                case ["MP3"]:
                def pathToPlayer = "${resource(dir:'js/audio-player/assets/',file:'')}"
                def pathToPlayerApp = "${resource(dir:'js/audio-player/assets/',file:'audio-player.js')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
                def audio = """
<script language="JavaScript" src=\""""+pathToPlayerApp+"""\"></script>
<object type="application/x-shockwave-flash" data=\""""+pathToPlayer+"""player.swf" id="audioplayer" height="24" width="290">
<param name="movie" value=\""""+pathToPlayer+"""player.swf">
<param name="FlashVars" value="playerID=audioplayer&soundFile="""+pathToFileName+"""\">
<param name="quality" value="high">
<param name="menu" value="false">
<param name="wmode" value="transparent">
</object>
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << audio
                break

                case ["OGG"]:
                def pathToPlayer = "${resource(dir:'flash/fogg/',file:'')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
                def audio = """
<object
type="application/x-shockwave-flash"
data=\""""+pathToPlayer+"""FOggPlayer.swf?url="""+pathToFileName+"""&title="""+title+"""\"
width="350"
height="21">
<param
name="movie"
value=\""""+pathToPlayer+"""FOggPlayer.swf?url="""+pathToFileName+"""&title="""+title+"""\" />
</object>
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << audio
                break
				
				case["RM"]:
				def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
				out << "<p>"+"<object classid=\"clsid:cfcdaa03-8be4-11cf-b84b-0020afbbccfa\" width=\"300\" height=\"100\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0\"> <param name=\"src\" value=\""+pathToFileName+"\" /><embed type=\"audio/x-pn-realaudio-plugin\" width=\"300\" height=\"100\" src=\""+pathToFileName+"\"></embed> </object> </p>"
				break
				
				case["AVI"]:
				def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
				out << "<p> <object classid=\"clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b\" width=\"300\" height=\"300\" codebase=\"http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0\"> <param name=\"src\" value=\""+pathToFileName+"\" /><embed type=\"video/quicktime\" width=\"300\" height=\"300\" src=\""+pathToFileName+"\"></embed> </object> </p>"
				break

                default:
				def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"
                out << "<p>"+"<object width=\"300\" height=\"300\" data=\""+pathToFileName+"\" > <param name=\"src\" value=\""+pathToFileName+"\" />"+"</object>"+"</p>"
                break
            }
        }else{
            out << "<!-- Sorry, your file format is not supported. -->"
        }
    }//end displayFile

    /**
     * This method returns a link with the absolute path of an image with the actual image as clickable link.
     * usage: <g:displayThumbnail fileName="${mediaInstance.fileName}" alt="${mediaInstance.alt}" /> where mediaInstance is an instance of the media class.
     * @param fileName="${mediaInstance.fileName}", alt="${mediaInstance.alt}" where mediaInstance is an instance of the media class
     * @return: clickable image link
     */
    def displayThumbnail = {attrs, body->
        def media = attrs["media"]
        def alt = media.alt
        def fileName = media.fileName
        def title = media.name
        def userDir = media.userDirectory
        def url = media.url

        if(fileName){
            def extension = fileName.split("\\.")[-1]

            switch(extension.toUpperCase()){
                case ["JPG", "PNG", "GIF", "JPEG"]:
                def picture = """
<a href="${createLinkTo(dir:'Mediathek/'+userDir+'/',
                        file:''+fileName)}">
<img src="${createLinkTo(dir:'Mediathek/'+userDir+'/imgthumbs/',
                        file:''+'thumb'+fileName)}"
alt="${alt}"
title="${alt}" />
</a>
"""

                if (media.owner.username==session.user.username || media.isPublicMedia) out << picture
                break

                case ["FLV"]:
                def pathToPlayer = "${resource(dir:'js/flvplayer/flvplayer-1.9.7b/flvplayer/',file:'')}"
                def pathToPlayerApp = "${resource(dir:'js/flvplayer/flvplayer-1.9.7b/flvplayer/',file:'swfobject.js')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"

                def video = """
<img src="${createLinkTo(dir:'Mediathek/admin/imgthumbs/', file:'thumbflashicon.png')}"
alt="flash icon"
title="Flash Icon}" />
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << video
                break

                case ["MP3"]:
                def pathToPlayer = "${resource(dir:'js/audio-player/assets/',file:'')}"
                def pathToPlayerApp = "${resource(dir:'js/audio-player/assets/',file:'audio-player.js')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"

                def audio = """
<img src="${createLinkTo(dir:'Mediathek/admin/imgthumbs/', file:'thumbflashicon.png')}"
alt="flash icon"
title="Flash Icon}" />
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << audio
                break

                case ["OGG"]:
                def pathToPlayer = "${resource(dir:'flash/fogg/',file:'')}"
                def pathToFile = "${resource(dir:'Mediathek/'+userDir+'/',file:'')}"
                def pathToFileName = "${resource(dir:'Mediathek/'+userDir+'/',file:''+fileName)}"

                def audio = """
<img src="${createLinkTo(dir:'Mediathek/admin/imgthumbs/', file:'thumbflashicon.png')}"
alt="flash icon"
title="Flash Icon" />
"""
                if (media.owner.username==session.user.username || media.isPublicMedia) out << audio
                break

                default:
                out << ""
                break
            }
        }else{
            out << "<!-- Sorry, your file format is not supported. -->"
        }
    }//end displayThumbnail

    /**
     * This method does nearly the same as displayThumbnail, the difference is, the controller is used to create the link, so the show
     * action is used. @see displayThumbnail
     * This method returns a link with the show action of an image with the actual image as clickable link,
     * usage: <g:displayThumbnailController fileName="${mediaInstance.fileName}" alt="${mediaInstance.alt}" id="${mediaInstance.id}"/> where mediaInstance is an instance of the media class.
     * @params fileName="${mediaInstance.fileName}", alt="${mediaInstance.alt}", id="${mediaInstance.id}" where mediaInstance is an instance of the media class
     * @return: clickable image link
     */
    def displayThumbnailController = {attrs, body->
        def media = attrs["media"]
        def fileName = media.fileName
        def alt = media.alt
        def userDir = media.userDirectory
        def id = attrs["id"]

        if(fileName){
            def extension = fileName.split("\\.")[-1]

            switch(extension.toUpperCase()){
                case ["JPG", "PNG", "GIF", "JPEG"]:
                def picture = """
					<a href="${createLinkTo(dir:'media/show/', file:''+id)}">
					<img src="${createLinkTo(dir:'Mediathek/'+userDir+'/imgthumbs/', file:''+'thumb'+fileName)}" alt="${alt}" title="${alt}" />
					</a>
				"""

                if (media.owner.username==session.user.username || media.isPublicMedia) out << picture
                break

                default:
                out << ""
                break
            }
        }else{
            out << "<!-- Sorry, your file format is not supported. -->"
        }
    }//end displayThumbnailController

}//end ShowMediaTagLib
