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
 
package bootstrap

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import ReportEditorMain.MediaCenter.Media
import systemadministration.usermanagement.User
import ReportEditorMain.ArticleEditor.Article



/**
 * BootStrap for EingabeEditor
 *
 * @author Irene, Olaf, Gerrit
 * (@see BootStrap)
 */

class EingabeEditor {
	
	/**
	 * BootStrap loading for the Dev env
	 * 
	 */
	def initDev = { servletContext ->
		//example records for media
		new Media(name:"3D Cube",
		fileName:"cube.jpg",
		description:"a 3D cube",
		alt:"3D_cube",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/cube.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"VLBA Logo",
		fileName:"VLBA_waagerecht.png",
		description:"Logo der Abteilung VLBA",
		alt:"VLBA Logo",
		fileType:"png",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/VLBA_waagerecht.png",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Solarhaus",
		fileName:"1279687488_0ab726ed17.jpg",
		description:"Ein Haus mit Solarzellen",
		alt:"solarhaus",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/1279687488_0ab726ed17.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Nachhaltigkeit 01",
		fileName:"2539377692_4b0b3d46a9.jpg",
		description:"grüne Ökowende",
		alt:"nh01",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/2539377692_4b0b3d46a9.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Haus",
		fileName:"2607242131_97ccc8271c.jpg",
		description:"gelbes Haus",
		alt:"haus",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/2607242131_97ccc8271c.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Haus",
		fileName:"2608067082_fe947ebf03.jpg",
		description:"gelbes Haus, Frontansicht",
		alt:"hausfront",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/2608067082_fe947ebf03.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Nachhaltigkeit Kongress",
		fileName:"3037025513_dc74d525ba.jpg",
		description:"Kongress zur Nachhaltigkeit",
		alt:"nhkongress",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/3037025513_dc74d525ba.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Nachhaltigkeit Bücher",
		fileName:"3083509910_6995df4449.jpg",
		description:"Bücher zur Nachhaltigkeit",
		alt:"nhbücher",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/3083509910_6995df4449.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Nachhaltigkeit Brainstorming",
		fileName:"4114130003_f6797bcdab.jpg",
		description:"Brainstorming im Sitzkreis zur Nachhaltigkeit",
		alt:"nhbrainstorming",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/4114130003_f6797bcdab.jpg",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Flash Test",
		fileName:"flashtest.flv",
		description:"Flash Test-Film",
		alt:"flash test",
		fileType:"flv",
		mediaType:"video",
		url:"/Storm/Mediathek/admin/flashtest.flv",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Flash Icon",
		fileName:"flashicon.png",
		description:"Ein Logo von Flash",
		alt:"flash icon",
		fileType:"png",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/flashicon.png",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Flash Icon",
		fileName:"flashicon.png",
		description:"Ein Logo von Flash",
		alt:"flash icon",
		fileType:"png",
		mediaType:"image",
		url:"/Storm/Mediathek/admin/flashicon.png",
		userDirectory:"admin",
		owner: User.findByUsername("admin"),
		date:new Date()).save()
		
		new Media(name:"Gehirn",
		fileName:"A_MALE_BRAIN.GIF",
		description:"gif Beispiel",
		alt:"gif",
		fileType:"gif",
		mediaType:"image",
		url:"/Storm/Mediathek/uli/A_MALE_BRAIN.GIF",
		userDirectory:"uli",
		owner: User.findByUsername("uli"),
		date:new Date()).save()
		
		new Media(name:"16 Farben",
		fileName:"16FARBEN.JPG",
		description:"jpg Beispiel",
		alt:"jpg",
		fileType:"jpg",
		mediaType:"image",
		url:"/Storm/Mediathek/uli/16FARBEN.JPG",
		userDirectory:"uli",
		owner: User.findByUsername("uli"),
		date:new Date()).save()
		
		new Media(name:"Zug",
		fileName:"ZUG.tif",
		description:"tif Beispiel",
		alt:"tif",
		fileType:"tif",
		mediaType:"image",
		url:"/Storm/Mediathek/uli/ZUG.tif",
		userDirectory:"uli",
		owner: User.findByUsername("uli"),
		date:new Date()).save()
		
		new Media(name:"Flashtest",
		fileName:"flashtest.flv",
		description:"flv Beispiel",
		alt:"flv",
		fileType:"flv",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/flashtest.flv",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"Strichmännchen Kampf",
		fileName:"Xxiao05.swf",
		description:"swf Beispiel",
		alt:"swf",
		fileType:"swf",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/Xxiao05.swf",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"The Blues Brothers - Theme From Rawhide",
		fileName:"The Blues Brothers - Theme From Rawhide.mp3",
		description:"mp3 Beispiel",
		alt:"mp3",
		fileType:"mp3",
		mediaType:"sound",
		url:"/Storm/Mediathek/martin/The Blues Brothers - Theme From Rawhide.mp3",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"Hennes Bender - Bielefeld",
		fileName:"Hennes Bender - 02 - Bielefeld.ogg",
		description:"ogg Beispiel",
		alt:"ogg",
		fileType:"ogg",
		mediaType:"sound",
		url:"/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"Mmmmh Lecker ...",
		fileName:"Mmmmh Lecker!.mov",
		description:"mov Beispiel",
		alt:"mov",
		fileType:"mov",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/Mmmmh Lecker!.mov",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"maus",
		fileName:"maus.rm",
		description:"rm Beispiel",
		alt:"rm",
		fileType:"rm",
		mediaType:"sound",
		url:"/Storm/Mediathek/martin/maus.rm",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"The Dancer",
		fileName:"The Dancer.avi",
		description:"avi Beispiel",
		alt:"avi",
		fileType:"avi",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/The Dancer.avi",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"Olympic",
		fileName:"Olympic.mpg",
		description:"mpg Beispiel",
		alt:"mpg",
		fileType:"mpg",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/Olympic.mpg",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		new Media(name:"carling",
		fileName:"carling_.wmv",
		description:"wmv Beispiel",
		alt:"wmv",
		fileType:"wmv",
		mediaType:"video",
		url:"/Storm/Mediathek/martin/carling_.wmv",
		userDirectory:"martin",
		owner: User.findByUsername("martin"),
		date:new Date()).save()
		
		
	}
	/**
	 * BootStrap loading for the test env
	 * 
	 *
	 */
	def initTest = { servletContext ->
		//TODO Bitte eintragen
		//initDev()
		
	}
	/**
	 * BootStrap loading for the prod env
	 * 
	 *
	 */
	def initProd = { servletContext ->
		//TODO Bitte eintragen
		initDev()
		
	}
	def destroy = {
	}
} 
