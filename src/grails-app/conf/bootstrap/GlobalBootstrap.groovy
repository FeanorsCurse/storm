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
import java.util.Date;

import systemadministration.usermanagement.User;

import rss.Feed;
import ReportEditorMain.Enum.ReportStatus;

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import ReportEditorMain.ReportEditor.Report
import ReportEditorMain.Enum.ReportStatus
import ReportEditorMain.Enum.ArticleStatus
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.MediaCenter.Media

import systemadministration.externalsystems.*;

import systemadministration.usermanagement.InterestGroup
import systemadministration.usermanagement.User
import systemadministration.usermanagement.Role
import systemadministration.usermanagement.Rule
import systemadministration.modulmanagement.Module
import systemadministration.modulmanagement.Question
import systemadministration.modulmanagement.Language
import systemadministration.modulmanagement.Config
import systemadministration.modulmanagement.Template
import systemadministration.modulmanagement.Newsletter
import systemadministration.log.AccessLog
import systemadministration.log.ChangeLog
import systemadministration.log.SecurityLog
import systemadministration.modulmanagement.ModuleService
import systemadministration.modulmanagement.RssFeed
import systemadministration.recommender.ArticleAccessReportingService
import systemadministration.recommender.ArticleToArticleCollaborativeFilteringService
import systemadministration.recommender.ArticleViewedByFriendsService;
import systemadministration.recommender.ArticleViewedByInterestgroupService;

import shemaEditor.shemaAdministration.*
import shemaEditor.indicatorAdministration.*
import shemaEditor.chart.Chart;
import systemadministration.externalsystems.*;

import interactiveFeatures.Infocart.*
import interactiveFeatures.Tagcloud.*
import interactiveFeatures.Usercomments.*
import interactiveFeatures.Help.*

import org.grails.rateable.*

/**
 * Global Bootstrap data for all category groups
 *
 * @author all
 * (@see BootStrap)
 */

class GlobalBootstrap {
	
	
	
	
	/**
	 * BootStrap loading for the Dev env
	 */
	def initDev = { servletContext ->
		
		//Sample Feeds
		RssFeed feed0 = new RssFeed(name:"Feed0", url:"http://www.uni-oldenburg.de/aktuell/vk/rss.php", active:false, showTitle:true, showDescription:true, showDate:true, showAuthor:true, maxNumItems:5, maxDescriptionLength:0, showInNewWindow:true).save()
		RssFeed feed1 = new RssFeed(name:"Feed1", url:"http://www.umweltdialog.de/umweltdialog/rubrikverteiler/rss_index.php", active:true, showTitle:true, showDescription:true, showDate:true, showAuthor:true, maxNumItems:3, maxDescriptionLength:0, showInNewWindow:true).save()
		RssFeed feed2 = new RssFeed(name:"Feed2", url:"http://www.spiegel.de/schlagzeilen/tops/index.rss", active:false, showTitle:true, showDescription:true, showDate:true, showAuthor:true, maxNumItems:5, maxDescriptionLength:0, showInNewWindow:false).save()
		
		//Get Users
		User user1 = User.findByUsername("User1") 
		User user2 = User.findByUsername("User2") 
		User anonym = User.findByUsername("Anonym") 
		User olaf = User.findByUsername("olaf")
		User edzard = User.findByUsername("edzard")
		User daniel = User.findByUsername("daniel")
		User admin = User.findByUsername("admin")
		
		//Sample Newsletter
		Newsletter news1 = new Newsletter(provider:"Newsletter111", description:"Text Text Text Text Text",action:"http://www.newsletterboy.de/apply/22220",email:"recipient[email]",name:"recipient[lastname]", firstname:"recipient[firstname]",active:true,dateCreated:new Date(), lastUpdated:new Date(), author:user1 ).save()
		Newsletter news2 = new Newsletter(provider:"Newsletter222", description:"Text Text Text Text Text",action:"http://www.newsletterboy.de/apply/22220",email:"recipient[email]",name:"recipient[lastname]", firstname:"recipient[firstname]",active:true,dateCreated:new Date(), lastUpdated:new Date(), author:user1 ).save()
		Newsletter news3 = new Newsletter(provider:"Newsletter333", description:"Text Text Text Text Text",action:"http://www.newsletterboy.de/apply/22220",email:"recipient[email]",name:"recipient[lastname]", firstname:"recipient[firstname]",active:false,dateCreated:new Date(), lastUpdated:new Date(), author:user1 ).save()
		
		//Dummy SAP Connection
		//SAPConnection sapConnection1 = new SAPConnection(name:"SAPConnection1",description:"...",author:user1,client:"927", login:"WIPG-01",password:"123456", locale:"DE",url:"m22z.hcc.uni-magdeburg.de",system:"22").save()
		SAPConnection sapConnection2 = new SAPConnection(name:"SAPConnection2",description:"...",author:user1,client:"927", login:"WIPG-01",password:"123456", locale:"DE",url:"m22z.hcc.uni-magdeburg.de",system:"22").save()
		
		//Dummy BAPI
		Bapi bapi1 = new Bapi(name:"Bapi2", description:"...",author:user1,connection:sapConnection2, bapi:"BAPI_MATERIAL_AVAILABILITY",importParameter:"1000;PLANT;1400-300;MATERIAL;ST;UNIT",exportStructure:"AV_QTY_PLT",exportValue:"AV_QTY_PLT").save()
		
		//Dummy Database Connection
		DatabaseConnection db1 = new DatabaseConnection(name:"DB1", description:"...",author:user1,url:"jdbc:mysql://devdb.uni-oldenburg.de:3306/nachhaltigkeit",driver:"com.mysql.jdbc.Driver",user:"nachhaltigkeit",password:"dschland").save()
		
		//Dummy SQL
		String sqlStatement = "select count(distinct `bezeichnung`) from `nachhaltigkeit`.`modul_deskriptor` where `bezeichnung` like '%umwelt%' or `bezeichnung` like '%nachhaltig%';"
		SQLStatement sql1 = new SQLStatement(name:"sql1",description:"...",author:user1,connection:db1,sqlStatement:sqlStatement).save()
		
		//Dummy records for Enum Language
		Language language1 = Language.findByCountryCode("de")  
		Language language2 = Language.findByCountryCode("en")
		
		//Sample Questions
		Question question1= new Question(title:"Woher stammt die Abkürzung O.k. und was bedeutet sie?", question:"Frage 1 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question2= new Question(title:"Sterben Spinnen im Staubsauger?", question:"Frage 2 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question3= new Question(title:"Was war zuerst da? Huhn oder Ei?", question:"Frage 3 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question4= new Question(title:"Warum trinken im Flugzeug so viele Passagiere Tomatensaft?", question:"Frage 4 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question5= new Question(title:"Wie viele Bäume sind ein Wald?", question:"Frage 5 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question6= new Question(title:"Warum verspürt man ein unangenehmes Gefühl, wenn man auf Alufolie beißt?", question:"Frage 6 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question7= new Question(title:"Stimmt es, dass man von nassem und kaltem Wetter eine Erkältung bekommen kann?", question:"Frage 7 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question8= new Question(title:"Verwenden Bäcker tatsächlich einen aus Menschenhaaren gewonnen Stoff, um Brötchen das typische Aroma zu verleihen?", question:"Frage 8 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question9= new Question(title:"Soll man wirklich das Gift aus der Wunde saugen, wenn jemand von einer Schlange gebissen wurde?", question:"Frage 9 Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1, answer:"Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 Antwort 9 ",editor:user2,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question10= new Question(title:"Wird in manchen Schwimmbädern das Wasser rot, wenn man hinein pinkelt?", question:"Frage 10 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1, answer:"Antwort 10  Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 Antwort 10 ",editor:user2,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question11= new Question(title:"Macht das Vorheizen von Backöfen wirklich Sinn?", question:"Frage 11 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1, answer:"Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 Antwort 11 ",editor:user2,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		Question question12= new Question(title:"Bekommt man am Toten Meer keinen Sonnenbrand?", question:"Frage 12 Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage Frage", author:user1, answer:"Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 Antwort 12 ",editor:user2,active:true,dateCreated:new Date(), lastUpdated:new Date()).save()
		
		//Dummy RssFeeds
		def rssfeed1 = new Feed(title:"Testfeed 1", description:"Beschreibung zu feed 1", copyright:"Storm", content:"Inhalt zum testfeed 1", link:"http://www.feeds.de.test/1", author:user1, lastModifiedBy:user1, creationDate:new Date(), lastModified:new Date()).save()
		def rssfeed2 = new Feed(title:"Testfeed 2", description:"Beschreibung zu feed 2", copyright:"Storm", content:"Inhalt zum testfeed 2", link:"http://www.feeds.de.test/2", author:user1, lastModifiedBy:user1, creationDate:new Date(), lastModified:new Date()).save()
		def rssfeed3 = new Feed(title:"Testfeed 3", description:"Beschreibung zu feed 3", copyright:"Storm", content:"Inhalt zum testfeed 3", link:"http://www.feeds.de.test/3", author:user1, lastModifiedBy:user1, creationDate:new Date(), lastModified:new Date()).save()
		
		//Dummy Rating
		def rating2 = new Rating(stars: "1", raterId:user1.id, raterClass: "systemadministration.modulmanagement.Question").save()
		def link2 = new RatingLink(rating: rating2, ratingRef: question1.id, type: "question").save()
		
		//Dummy records for category
		def cat1 = new Category(name:"Wasser").save()
		def cat2 = new Category(name:"Bildung").save()
		def cat3 = new Category(name:"Energie").save()
		def cat4 = new Category(name:"Sozial").save()
		def cat5 = new Category(name:"Menschenrechte").save()
		def cat6 = new Category(name:"Ökologische Leistungsindikatoren").save()
		def cat7 = new Category(name:"Umwelt Leistungsindikatoren").save()
		def cat8 = new Category(name:"Ökonomische Indikatoren").save()
		
		//dummy records for charts
		def chart1 = new Chart(name:"Balkendiagramm", description:"Ein Balkendiagramm").save()
		def chart2 = new Chart(name:"Liniendiagramm", description:"Ein Liniendiagramm").save()
		def chart3 = new Chart(name:"Tortendiagramm", description:"Ein Tortendiagramm").save()
		def chart4 = new Chart(name:"3DTortendiagramm", description:"Ein 3DTortendiagramm").save()
		
		//dummy records for indicator
		def indi1 = new Indicator(name:"Energieverbrauch", shortName:"TN1", description:"Energieverbrauch Beschreibung", 
		compilation:"This Indicator\n" +
		" describes the reporting organization contribution to the conservation of the global resource base\n" +
		" and efforts to reduce the	material intensity and increase the efficiency of the economy. These\n" +
		" are expressed goals of the OECD Council and various national sustainability strategies. For internal\n" +
		" managers and others interested in the financial state of the organization, material consumption\n" +
		" relates directly to overall costs of operation. Tracking this	consumption internally, either by product or product category,\n" +
		" facilitates the monitoring of material efficiency and cost of material flows.", 
		reference:"International Accounting Standard (IAS) 20 über die Bilanzierung und Darstellung von Zuwendungen der\n" +
		" öffentlichen Hand.",
		relevance:"This Indicator describes the reporting organization contribution to the conservation of the\n" +
		" global resource base and efforts to reduce the material intensity and increase the efficiency of the economy.\n" +
		" These are expressed goals of the OECD Council and various national sustainability strategies. For internal\n" +
		" managers and others interested in the financial state of the organization, material consumption relates directly to overall\n" +
		" costs of operation. Tracking this consumption internally, either by product or product category, facilitates\n" +
		" the monitoring of material efficiency and cost of material flows.",
		documentation:"This Indicator describes the reporting organization contribution to\n" +
		" the conservation of the global resource base and efforts to reduce the material intensity and increase\n" +
		" the efficiency of the economy. These are expressed goals of the OECD Council and various national	sustainability strategies.\n" +
		" For internal managers and others interested in the financial state of the organization, material consumption\n" +
		" relates directly to overall costs of operation. Tracking this	consumption internally, either by product or product category,\n" +
		" facilitates the monitoring of material efficiency and cost of material flows.",
		definition:"This Indicator describes the reporting\n" +
		" organization contribution to the conservation of the global resource base and efforts to reduce the material intensity\n" +
		" and increase the efficiency of the economy. These	are expressed goals of the OECD Council and various national\n" +
		" sustainability strategies. For internal managers and others interested in the financial state of the organization, material consumption\n" +
		" relates directly to overall costs of operation. Tracking this	consumption internally, either by product or product category,\n" +
		" facilitates the monitoring of material efficiency and cost of material flows.	<p>This Indicator describes the reporting organization\n" +
		" contribution to the conservation of the global resource base and efforts to reduce the material intensity and increase the efficiency\n" +
		" of the economy. These are expressed goals of the OECD Council and	various national sustainability strategies. For internal managers and\n" +
		" others interested in the financial state of the organization, material consumption relates directly to overall costs of operation. Tracking\n" +
		" this consumption internally, either by product or product category, facilitates the monitoring of material efficiency and cost of material flows.</p>", 
		creationDate: new Date(), value:0, unit:"kw", category:cat2, defaultChart:chart3, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi10 = new Indicator(name:"Wasserverbrauch dieses Jahr", defaultChart:chart4, fromAuthor:false, shortName:"TN1",description:"Wasserverbrauch Beschreibung", creationDate: new Date(), value:0, unit:"Liter", category:cat1, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi3 = new Indicator(name:"Beschäftigte", fromAuthor:false, defaultChart:chart2,  shortName:"TN1",description:"Beschäftigte Beschreibung", creationDate: new Date(), value:0, unit:"Abolut", category:cat4, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi4 = new Indicator(name:"Anzahl Urlaubstage", fromAuthor:false, defaultChart:chart4,  shortName:"TN1",description:"Urlaubstage Beschreibung", creationDate: new Date(), value:0, unit:"Tag", category:cat4, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi5 = new Indicator(name:"Papierverbrauch", fromAuthor:false, defaultChart:chart2,  shortName:"TN1",description:"Wasserverbrauch Beschreibung", creationDate: new Date(), value:0, unit:"tone", category:cat1, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi6 = new Indicator(name:"Wasserverbrauch im Vergleich zum Vorjahr", defaultChart:chart1, fromAuthor:false,  shortName:"TN1",description:"Wasserverbrauch Beschreibung", creationDate: new Date(), value:0, unit:"Liter", category:cat1, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		def indi7 = new Indicator(name:"Vorwort des Geschäftsführers", fromAuthor:false, shortName:"GS",description:"Vorwort des Geschäftsführers", creationDate: new Date(), indicator:false, value:0, unit:"Liter", category:cat1, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, bapi:bapi1, sqlStatement:sql1).save()
		def indi2 = new Indicator(name:"Gleichbehandlung", shortName:"HR4", defaultChart:chart3, description:"Ergriffene Maßnahmen", 
		
		compilation:"1. Ermitteln Sie Vorfälle, in denen Personen aufgrund\n" +
		"ihrer ethnischen Herkunft, ihrer Hautfarbe, ihres Geschlechts,\n" +
		"ihrer Religion, ihrer politischen Ansichten,\n" +
		"ihrer nationalen oder sozialen Herkunft nach der\n" +
		"Definition der ILOC diskriminiert wurden sowie andere\n" +
		"relevante Formen von Diskriminierung, die interne\n" +
		"bzw. externe Stakeholder an den verschiedenen\n" +
		"Geschäftsstandorten im Berichtszeitraum betreffen.\n <br>"+
		"Der Begriff „Vorfälle” bezieht sich auf Klagen, bei der\n" +
		"Organisation oder bei zuständigen Behörden im\n" +
		"Rahmen eines förmlichen Verfahrens eingereichte\n" +
		"Beschwerden oder Fälle von Ungleichbehandlung,\n" +
		"die die Organisation durch bestehende Verfahren wie\n" +
		"z. B. Audits zum Managementsystem oder formelle\n" +
		"Überwachungsprogramme festgestellt hat.<br>\n" +
		"2. Geben Sie die Gesamtzahl der Diskriminierungsvorfälle\n" +
		"während des Berichtszeitraums an.<br>\n" +
		"3. Geben Sie gemäß der folgenden Typologie den\n" +
		"Status der Vorfälle sowie die Maßnahmen.",
		reference:"Erklärung über die Beseitigung aller Formen von\n" +
		"Intoleranz und Diskriminierung aufgrund der Religion\n" +
		"oder Überzeugung, UN-Generalversammlung, Beschluss 36/55\n" +
		"vom 26. November 1981.",
		relevance:"Menschenrechte gehen über die Rechte von Arbeitnehmern\n" +
		"an ihrem Arbeitsplatz hinaus. Gleichbehandlung ist eine\n" +
		"Schlüsselanforderung internationaler Übereinkommen sowie\n" +
		"nationaler Sozialgesetzgebung und sozialer Richtlinien.\n" +
		"Das Thema Gleichbehandlung wird auch in den Kernarbeitsnormen\n" +
		"100 und 111 der ILOC behandelt. Um sicherzustellen,\n" +
		"dass der Gleichbehandlungsgrundsatz in der gesamten\n" +
		"berichtenden Organisation eingehalten wird, ist ein wirksames\n" +
		"Überwachungssystem notwendig. Stakeholder werden\n" +
		"sich rückversichern wollen, dass solche Grundsätze und\n" +
		"Überwachungssysteme der Organisation wirksam sind.",
		
		
		documentation:"Informationen können u.a. in der Rechtsabteilung oder der\n" +
		"Abteilung der berichtenden Organisation, die sich mit der\n" +
		"Befolgung von Gesetzen beschäftigt, vorhanden sein.",
		
		
		definition:"Darunter versteht man die Ungleichbehandlung einer\n" +
		"Person und die damit verbundenen Folgen, indem man\n" +
		"eine Person unverhältnismäßig belastet oder ihr Vorteile\n" +
		"verweigert, anstatt Sie aufgrund ihrer persönlichen Verdienste\n" +
		"gerecht zu behandeln. Diskriminierung kann auch\n" +
		"Belästigung einschließen. Der Begriff der Belästigung wird\n" +
		"definiert als eine Reihe von Bemerkungen oder Handlungen, die von\n" +
		"der Person, an die sie gerichtet sind, nicht\n" +
		"gewünscht sind oder bei denen vernünftigerweise davon\n" +
		"ausgegangen werden kann, dass sie nicht gewünscht sind.",
		creationDate: new Date(), value:0, unit:"", category: cat5, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		
		
		
		
		def indi11 = new Indicator( name:"Energie", shortName:"EN3", defaultChart:chart1, description:"Direkter Energieverbrauch aufgeschlüsselt nach Primärenergieträgern",
		
		compilation: "Ermitteln Sie die von der berichtenden Organisation\n" +
		"für den Eigenverbrauch eingekauften Primärenergiequellen.",
		reference:"Die Greenhouse Gas Protocol (GHG) Initiative – ein\n" +
		"Abrechnungs- und Berichtsstandard für Unternehmen\n" +
		"(überarbeitete Ausgabe 2004) des World\n" +
		"Resources Institute (WRI) und des World Business\n" +
		"Council for Sustainable Development (WBCSD).",
		relevance:"Durch die Berechnung des Energieverbrauchs der berichtenden\n" +
		"Organisation kann seine Fähigkeit zur effizienten\n" +
		"Nutzung von Energie offen gelegt werden. Der Energieverbrauch\n" +
		"wirkt sich unmittelbar auf die Betriebskosten\n" +
		"und die Abhängigkeit der Organisation von Schwankungen\n" +
		"in der Energieversorgung und den Energiepreisen\n" +
		"aus. Der „ökologische Fußabdruck“ einer Organisation wird\n" +
		"teilweise durch ihre Wahl der Energiequellen geformt.\n" +
		"Änderungen in der Zusammensetzung dieser Energiequellen\n" +
		"können ein Indikator für die Bemühungen der Organisation\n" +
		"sein, ihre Umweltauswirkungen zu minimieren.<br> Informationen\n" +
		"über den Verbrauch von Primärenergiequellen\n" +
		"helfen bei der Einschätzung, wie die Organisation\n" +
		"von künftigen umweltrechtlichen Vorschriften, wie z. B.\n" +
		"dem Kyoto-Protokoll, betroffen sein könnte. Der Verbrauch\n" +
		"fossiler Brennstoffe ist eine Hauptquelle von Treibhausgasemissionen\n" +
		"und der Energieverbrauch ist unmittelbar\n" +
		"mit dem Treibhausgasausstoß der Organisation verknüpft. <p>Das Ersetzen fossiler\n" +
		"Brennstoffe durch erneuerbare Energieträger\n" +
		"ist für die Bekämpfung des Klimawandels und\n" +
		"anderer Auswirkungen auf die Umwelt, verursacht durch\n" +
		"die Entnahme und Verarbeitung von Energie, wesentlich.\n" +
		"Die aktive Unterstützung erneuerbarer und effizienter\n" +
		"Energietechnologien verringert auch die aktuelle und\n" +
		"zukünftige Abhängigkeit der berichtenden Organisation\n" +
		"von nicht erneuerbaren Energiequellen und von möglichen\n" +
		"Schwankungen der Energiepreise und -versorgung.<p> Dieser Indikator\n" +
		"misst den Verbrauch der berichtenden\n" +
		"Organisation von direkten Primärenergieträgern. Er deckt\n" +
		"Bereich 1 des WRI/WBCSD Greenhouse Gas Protokolls ab.\n" +
		"Indikator EN4 misst den Verbrauch von Primärenergieträgern,\n" +
		"die das berichtende Organisation mit Sekundärenergie,\n" +
		"wie Elektrizität, Wärme und Kühlung versorgen.",
		documentation:"Informationen können Rechnungen, den gemessenen\n" +
		"oder errechneten Ergebnissen in Heizkostenabrechnungen, Schätzungen,\n" +
		"Standardwerten usw. entnommen\n" +
		"werden. Werte in Joule können direkt aus den Rechnungen\n" +
		"oder Lieferscheinen entnommen oder daraus umgerechnet\n" +
		"werden. Informationen über die Primärenergieträger,\n" +
		"die verwendet werden, um Sekundärenergie zu produzieren,\n" +
		"können bei den Zulieferern eingeholt werden.",
		definition:"Dabei handelt es sich um Rohstoffe, die innerhalb kurzer\n" +
		"Zeit durch ökologische Kreisläufe wieder aufgefüllt werden\n" +
		"können (im Gegensatz zu Rohstoffen, wie Mineralstoffe,\n" +
		"Metalle, Öl, Gas, Kohle, die sich nicht in kurzen Zeiträumen\n" +
		"erneuern).",
		creationDate: new Date(), value:0, unit:"kw", category: cat6, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		
		
		
		
		
		
		
		def indi12 = new Indicator( name:"Wasser", defaultChart:chart3, shortName:"EN8", description:"Gesamtwasserentnahme aufgeteilt nach Quellen.",
		compilation:"1. Insgesamt entnommen wurde. Maßgeblich ist das\n" +
		"Wasser, das entweder direkt von der berichtenden\n" +
		"Organisation oder durch zwischengeschaltete\n" +
		"Einrichtungen, wie z. B. Wasserversorgern entnommen\n" +
		"wurde. Dies beinhaltet die Entnahme von Kühlwasser.<br>\n" +
		"2. Berichten Sie die insgesamt entnommene Menge Wasser in Kubikmetern pro\n" +
		"Jahr (m3/Jahr) aufgeschlüsselt nach den folgenden Quellen:<br>\n" +
		"- Oberflächenwasser einschließlich Wasser aus Feuchtgebieten, Flüssen,\n" +
		"Seen und Meeren<br>\n" +
		"- Grundwasser<br>\n" +
		"- Regenwasser, das von der berichtenden Organisation direkt gesammelt\n" +
		"und gelagert wurde <br> \n" +
		"- Abwasser einer anderen Organisation und\n" +
		"Wasser der kommunalen Wasserversorgung oderanderer Wasserversorger.",
		reference:"Erklärung über die Beseitigung aller Formen von\n" +
		"Intoleranz und Diskriminierung aufgrund der Religion\n" +
		"oder Überzeugung, UN-Generalversammlung, Beschluss 36/55\n" +
		"vom 26. November 1981.",
		
		relevance:"Die Angabe der insgesamt entnommenen Wassermenge\n" +
		"aufgeschlüsselt nach Quellen trägt zum Verständnis\n" +
		"der Größenordnung der mit dem Wasserverbrauch der\n" +
		"berichtenden Organisation verbundenen möglichen Auswirkungen\n" +
		"und Risiken bei. Die insgesamt entnommene\n" +
		"Wassermenge ist ein Indikator für die relative Größe und\n" +
		"Bedeutung der berichtenden Organisation als Wasserverbraucher\n" +
		"und kann als Grundlage für andere Berechnungen\n" +
		"in Bezug auf die Effizienz und den Verbrauch\n" +
		"dienen. <br> Die systematische Anstrengung, den effizienten Wasserverbrauch\n" +
		"der berichtenden Organisation zu überwachen\n" +
		"und zu verbessern, ist unmittelbar mit den Wasserkosten\n" +
		"verknüpft. Der Gesamtwasserverbrauch kann auch als\n" +
		"Indikator für das Risiko dienen, dem die Organisation\n" +
		"durch eine Unterbrechung der Wasserzufuhr oder durch\n" +
		"höhere Wasserpreise ausgesetzt ist. Sauberes Frischwasser\n" +
		"wird immer knapper und kann die Produktionsprozesse\n" +
		"beeinflussen, die von großen Wassermengen abhängig\n" +
		"sind. In Regionen, in denen die Wasserquellen stark\n" +
		"begrenzt sind, kann das Wasserverbrauchsverhalten der\n" +
		"Organisation auch die Beziehungen zu anderen Stakeholdern\n" +
		"beeinflussen.",
		documentation:"Informationen über den Wasserverbrauch der Organisation\n" +
		"können von den Wasserzählern abgelesen oder den\n" +
		"Wasserrechnungen, den aus anderen verfügbaren Wasserdaten\n" +
		"abgeleiteten Berechnungen oder, falls weder\n" +
		"Wasserzähler noch -rechnungen noch Referenzwerte\n" +
		"existieren, den Schätzungen der Organisation selbst\n" +
		"entnommen werden.",
		definition:"Dieser Begriff steht für das gesamte Wasser, dass in die\n" +
		"Berichtsgrenzen der berichtenden Organisation aus allen\n" +
		"möglichen Quellen für jede Art von Nutzung während\n" +
		"des Berichtszeitraums entnommen wurde, einschließlich\n" +
		"Oberflächenwasser, Grundwasser, Regenwasser und\n" +
		"kommunaler Wasserversorgung.",
		creationDate: new Date(), value:0, unit:"Liter", category: cat6, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		
		
		def indi13 = new Indicator(name:"Emissionen, Abwasser und Abfall", fromAuthor:false,  shortName:"EN16",description:"Gesamte direkte und indirekte Treibhausgasemissionen insgesamt nach",
		
		compilation:"1- Es stehen verschiedene Methoden zu Verfügung, um Treibhausgasemissionen pro Quelle\n" +
		"zu berechnen. Geben Sie den verwendeten Standard\n" +
		"und die für die Daten relevante Methodik unter\n" +
		"Bezugnahme auf die folgenden Aspekte an:<br>\n" +
		"- Direkte Messung(z. B. kontinuierliche Onlineanalysen)<br>\n" +
		"- Berechnung basierend auf standortspezifischen Daten (z. B. für die Untersuchung der Brennstoffzusammensetzung)<br> \n" +
		"- Berechnung auf der Grundlage von Werten und <br>\n" +
		"Schätzungen. Falls Schätzwerte zugrunde gelegt werden, weil keine vorgegebenen Werte zur\n" +
		"Verfügung stehen, geben Sie an, von welchen Grundannahmen ausgegangen wird.<br>\n" +
		"Weitere Details zur Datensammlung für diesen Indikator\n" +
		"finden Sie im WRI /WBCSD Greenhouse Gas-Protokoll und\n" +
		"in dem IPCC-Dokument, das unter den Quellen aufgeführt wird <br>.\n" +
		"Ermitteln Sie den direkten Ausstoß von Treibhausgas aus allen Quellen,\n" +
		"die sich im Eigentum der berichtenden Organisation befinden oder von\n" +
		"diesem kontrolliert werden, einschließlich.<br>\n" +
		"- Erzeugung von Elektrizität, Wärme oder Dampf, wie unter EN3 berichtet<br>\n" +
		"- andere Verbrennungsprozesse, wie das Abfackeln <br>\n" +
		"- physikalische oder chemische Verarbeitung <br>\n" +
		" Transport von Material, Produkten und Abfällen<br>\n" +
		"Belüftung und<br>\n" +
		"flüchtige Emissionen.<br>\n" +
		"Emissionen aus Verbrennungsprozessen und -quellen\n" +
		"werden der direkten Primärenergie aus nicht\n" +
		"erneuerbaren und erneuerbaren Energiequellen, wie\n" +
		"unter EN3 berichtet, entsprechen. Bitte beachten\n" +
		"Sie, dass die direkten CO2-Emissionen aus der Verbrennung\n" +
		"von Biomasse im Rahmen des Treibhausgasprotokolls\n" +
		"(überarbeitete Fassung) hier nicht zu\n" +
		" berücksichtigen, sondern getrennt auszuweisen sind.<br>\n" +
		"2-Ermitteln Sie die indirekten Treibhausgasemissionen,\n" +
		"die aus der Erzeugung eingekaufter Elektrizität, eingekaufter\n" +
		"Wärme oder eingekauften Dampfs resultieren. Dies entspricht dem unter EN4 berichteten\n" +
		"Energieverbrauch.Sonstige indirekte Emissionen, wie z. B. durch Dienstreisen\n" +
		"verursachte Emissionen, werden hier nicht erfasst, da diese unter EN17 berichtet werden. <br>\n" +
		"3-Berichten Sie die Treibhausgasemissionen als Summe\n" +
		"der direkten und indirekten Emissionen, wie unter 2 und 3 \n" +
		"ermittelt, in Tonnen CO2-Äquivalent.\n",
		reference: "-Die Greenhouse Gas Protocol (GHG) Initiative – ein \n" +
		"Abrechnungs- und Berichtsstandard für Unternehmen \n" +
		"(überarbeitete Ausgabe 2004) des World\n" +
		"Resources Institute (WRI) und des World Business\n" +
		"Council for Sustainable Development (WBCSD).<br>\n" +
		"-Kyoto-Protokoll, 1997. <br>\n" +
		"-Intergovernmental Panel on Climate Change (IPCC),<br>\n" +
		"-Climate Change 2001, Working Group I: The Scientific Basis.<br>\n",
		relevance:"Treibhausgasemissionen sind die Hauptursache für den\n" +
		"Klimawandel und unterliegen der Klimarahmenkonvention\n" +
		"der Vereinten Nationen (UNFCC) und dem danach verabschiedeten\n" +
		"Kyoto-Protokoll. Zu deren Umsetzung wurden verschiedene nationale\n" +
		"und internationale Bestimmungen und Anreizsysteme (wie z. B. Emissionszertifikate)\n" +
		"beschlossen. Sie zielen darauf ab, das Volumen der Treibhausgasemissionen\n" +
		"zu kontrollieren und die Verringerung des Ausstoßes zu belohnen<br>\n." +
		"Dieser Indikator kann in Kombination mit EN17 genutzt werden, um auf\n" +
		"internationaler oder nationaler Ebene Ziele für Rechtsvorschriften oder Handelssysteme zu\n" +
		"beschreiben. Die kombinierte Betrachtung von direkten und indirekten Emissionen liefert\n" +
		"Einsichten in mögliche Kostenauswirkungen von Besteuerungs- und Handelssystemen\n" +
		"für die für die berichtende Organisation.\n",
		documentation: "Emissionen, die aus direktem oder indirektem Energieverbrauch\n" +
		"resultieren, können mit Hilfe der unter EN3 und\n" +
		"EN4 berichteten Daten berechnet werden.<br>\n" ,
		definition: "Direkte Emissionen:<br>\n" +
		"Emissionen aus Quellen, die im Besitz der berichtenden\n" +
		"Organisation sind oder von dieser kontrolliert werden.\n" +
		"Direkte Emissionen im Zusammenhang mit einer Verbrennung\n" +
		"würden z. B. entstehen, wenn die berichtende\n" +
		"Organisation innerhalb ihrer Berichtsgrenzen Energie aus\n" +
		"Brennstoffen erzeugt.<br>\n" +
		"Indirekte Emissionen:<br>\n" +
		"Emissionen, die aus Aktivitäten der berichtenden Organisation\n" +
		"resultieren, aber die aus Quellen erzeugt werden, die\n" +
		"zum Eigentum einer anderen Organisation gehören oder\n" +
		"von einer anderen Organisation kontrolliert werden. Im Zusammenhang\n" +
		" mit diesem Indikator bezieht sich der Begriff\n" +
		"der indirekten Emissionen auf Treibhausgasemissionen,\n" +
		"die durch die Erzeugung von Elektrizität, Wärme oder\n" +
		"Dampf verursacht wurden, die zum Eigenverbrauch in die\n" +
		"Berichtsgrenzen der berichtenden Organisation geliefert wurden.\n" +
		"Kohlendioxid-Ä quivalent:<br>\n" +
		"CO2 (Kohlendioxid)-Äquivalent ist die Maßeinheit, die\n" +
		"verwendet wird, um die Emissionen verschiedener Treibhausgase\n" +
		"auf der Grundlage ihrer Klimawirksamkeit (GWP\n" +
		"= Global Warming Potential) zu vergleichen. Das CO2-Äquivalent\n" +
		"für ein Gas wird abgeleitet, indem man die Tonnen.\n" +
		"des Gases mit dem damit verbundenen GWP multipliziert.\n",
		creationDate: new Date(), value:0, unit:"Tone", category:cat7, defaultChart:chart4, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, bapi:bapi1, sqlStatement:sql1).save()
		
		
		def indi14 = new Indicator(name:"Transport", fromAuthor:false, defaultChart:chart1,  shortName:"EN29",
		description:"Wesentliche Umweltauswirkungen\n" +
		"verursacht durch den Transport von\n" +
		"Produkten und anderen Gütern und\n" +
		"Materialien, die für die Geschäftstätigkeit\n" +
		"der Organisation verwendet werden, sowie\n" +
		"durch den Transport von Mitarbeitern.\n" ,
		
		compilation: "1- Ermitteln Sie die wesentlichen Umweltauswirkungen\n" +
		" der verschiedenen von der Organisation eingesetzten\n" +
		" Transportarten, einschließlich:<br>\n" +
		" - Energieverbrauch (z. B. Öl, Kerosin, Benzin, Strom)<br>\n" +
		"- Emissionen (z. B. Treibhausgasemissionen,<br>\n" +
		"  Ozon abbauende Stoffe, NOX , SOX und andere Luftemissionen)\n" +
		"- Abwässer (z. B. verschiedene Arten von Chemikalien)<br>\n" +
		"Abfall (z. B. verschiedene Arten von Verpackungsmaterial)<br>\n" +
		"- Lärm und\n" +
		"Freigesetzte Materialien (z. B. Verschütten von Chemikalien, Ölen und Treibstoffen).<br>\n" +
		"2-Berichten sie die wesentlichen Umweltauswirkungen\n" +
		" des Transports von Gütern und von Mitarbeitern\n" +
		"der Organisation. Sofern keine quantitativen Daten im Bericht angegeben werden,\n" +
		"geben Sie bitte den\n" +
		"Grund dafür an.<br>\n" +
		"3- Nennen Sie die Kriterien und die Methodik, die\n" +
		" verwendet wurde, um zu ermitteln, welche Umweltauswirkungen\n" +
		" wesentlich sind.<br>\n" +
		"4- Berichten Sie, wie die Umweltauswirkungen\n" +
		"des Transports von Produkten, Mitarbeitern der\n" +
		"Organisation und sonstigen Gütern und Materialien vermindert werden\n" ,
		reference:"Empfehlungen der Vereinten Nationen zur\n" +
		"Beförderung gefährlicher Güter.\n",
		relevance: "Die Umweltauswirkungen der Transportsysteme reichen\n" +
		"von der globalen Erwärmung über Smog bis hin zu\n" +
		"Lärmbelästigungen vor Ort. Für einige Organisationen,\n" +
		"insbesondere solche mit umfangreichen Zuliefer- und\n" +
		"Vertriebsnetzwerken, können diese Umweltauswirkungen\n" +
		"den wesentlichsten Teil ihres „ökologischen Fußabdrucks”\n" +
		"ausmachen. Die Bewertung der Auswirkungen des\n" +
		"Transports von Produkten, Gütern und Material sowie des\n" +
		"Verkehrs von Mitarbeitern der Organisation ist Teil eines\n" +
		"umfassenden Ansatzes für die Planung von Strategien des\n" +
		"Umweltmanagements.\n" ,
		documentation: "Informationen können Rechnungen von Logistikdienstleistern\n" +
		"und Lieferanten, Aufzeichnungen der Logistikabteilung,\n" +
		"Aufzeichnungen über die Verwendung und\n" +
		"Wartung der Fahrzeuge und Überwachungen/Messungen\n" +
		"entnommen werden, die z. B. von der Umweltabteilung\n" +
		"durchgeführt werden.\n" ,
		definition:
		"Transport<br>\n" +
		"Die Handlung, bei der Ressourcen und Güter unter\n" +
		"Verwendung verschiedener Transportmittel einschließlich\n" +
		"des Personentransports (z. B. Dienstweg der Mitarbeiter\n" +
		"und Geschäftsreisen) von einem Ort zu einem anderen gebracht\n" +
		"werden (zwischen Lieferanten, Produktionsanlagen,\n" +
		"Lagern und dem Kunden).\n" +
		"Logistische Zwecke<br>\n" +
		"Der Fluss und die Lagerung von Waren und Dienstleistungen\n" +
		"zwischen dem Ursprungsort und dem Verbrauchsort\n" +
		"in beiden Richtungen.\n" +
		"Transport von Mitarbeitern der Organisation<br>\n" +
		"Transportmittel, die von Mitarbeitern der Organisation\n" +
		"auf ihrem Weg zur Arbeit oder für Dienstreisen verwendet\n" +
		"werden, einschließlich Flugzeug, Zug, Bus und anderen\n" +
		"Formen motorisierten und nichtmotorisierten Reisens.\n",
		creationDate: new Date(), value:0, unit:"Tone", category:cat7, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		
		def indi15 = new Indicator(name:"Marktpräsenz", fromAuthor:false, defaultChart:chart2,  shortName:"EC7",
		description: "Verfahren für die Einstellung von\n" +
		"lokalem Personal und Anteil von lokalem\n" +
		"Personal an den Posten für leitende\n" +
		"Angestellte an wesentlichen Geschäftsstandorten.\n",
		compilation:"1- Geben Sie an, ob die Organisation eine organisationsweite\n" +
		"Firmenpolitik oder einheitliche Verfahren\n" +
		"verfolgt, um Ortsansässige bei der Einstellung an\n" +
		"wesentlichen Geschäftsstandorten zu bevorzugen.<br>\n" +
		"2- Falls ja, geben Sie den Anteil des lokalen Personals\n" +
		"an den leitenden Angestellten an wesentlichen Geschäftsstandorten\n" +
		"an. Verwenden Sie Daten für Vollzeitbeschäftigte,\n" +
		"um diesen Prozentsatz zu ermitteln.<br>\n" +
		"3- Geben Sie an, wie der Begriff „leitende Angestellte”\n" +
		"definiert wurde.\n",
		reference:"",
		relevance:  "Die Auswahl von Personal und leitenden Anstellten basiert\n" +
		"auf zahlreichen Überlegungen. Sicherzustellen, dass unter\n" +
		"den leitenden Angestellten auch lokales Personal vertreten\n" +
		"ist, kann der Standortgemeinde zugute kommen und der\n" +
		"Organisation dabei helfen, die lokalen Bedürfnisse besser\n" +
		"zu verstehen. Vielfalt im Managementteam und die Einbeziehung\n" +
		"von Mitarbeitern, die vor Ort leben, kann das\n" +
		"Humankapital, den wirtschaftlichen Nutzen für die örtliche\n" +
		"Gemeinde und die Fähigkeit der Organisation, die lokalen\n" +
		"Bedürfnisse zu verstehen, verbessern.\n",
		documentation:"Die Personalabteilung sollte über die Informationen\n" +
		"verfügen, die für diesen Indikator benötigt werden.\n",
		definition: "Lokales Personal bezieht sich auf Personen, die am Ort\n" +
		"geboren wurden, wo sich die Betriebsstätte befindet,\n" +
		"oder dort uneingeschränktes Wohnrecht haben (z. B.\n" +
		"einge¬bürgerte Mitbürger oder Inhaber einer unbeschränkten\n" +
		"Aufenthaltsgenehmigung). Berichtende Organisationen\n" +
		"können wählen, wie sie den Begriff „Lokales\n" +
		"Personal” definieren, da in einigen Fällen auch Personal,\n" +
		"das aus derselben Region und – im Fall kleinerer Länder\n" +
		"– sogar demselben Land kommt, vertretbarerweise als\n" +
		"lokales Personal angesehen werden kann. Die Definition\n" +
		"sollte jedoch klar offengelegt werden.\n" ,
		creationDate: new Date(), value:0, unit:"", category:cat8, lastModifiedDate:new Date(), creator:user1, lastModifiedBy:user1, indicator:true, bapi:bapi1, sqlStatement:sql1).save()
		
		
		
		//dummy records for tnode
		def rootNode = new TNode(name:"GRI A", description:"Beschreibung", title:true, parent_id:0, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def rootNode2 = new TNode(name:"GRI B", description:"Beschreibung", title:true, parent_id:0, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def rootNode3 = new TNode(name:"GRI C", description:"Beschreibung", title:true, parent_id:0, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		
		def gri_a_node1 = new TNode(name:"Kapitel 1", description:"Ökonomie", parent_id:rootNode.id, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node2 = new TNode(name:"Kapitel 1.1", description:"Mehr über Ökonomie", parent_id:4, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node3 = new TNode(name:"Kapitel 2", description:"Ökologie", parent_id:rootNode.id, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node4 = new TNode(name:"Kapitel 3", description:"Soziales", parent_id:rootNode.id, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node5 = new TNode(name:"Kapitel 3.1", description:"Mehr über Soziales", parent_id:7, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node6 = new TNode(name:"Kapitel 4", description:"Schlußwort", parent_id:rootNode.id, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		def gri_a_node7 = new TNode(name:"Kapitel 5", description:"Schlußwort - Revision", parent_id:rootNode.id, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date())
		
		//dummy records for tnode_tnode
		rootNode.addToChildren(gri_a_node1)
		rootNode.addToChildren(gri_a_node3)
		rootNode.addToChildren(gri_a_node4)
		rootNode.addToChildren(gri_a_node6)
		rootNode.addToChildren(gri_a_node7)
		
		gri_a_node1.addToChildren(gri_a_node2)
		gri_a_node4.addToChildren(gri_a_node5)
		
		gri_a_node1.save()
		gri_a_node2.save()
		gri_a_node3.save()
		gri_a_node4.save()
		gri_a_node5.save()
		gri_a_node6.save()
		gri_a_node7.save()
		
		
		
		if(gri_a_node1.save(flush:true)){
			def ti1 = new TNodeIndi(tnode: gri_a_node1, indicator: indi1, i_usage:0).save()
			def ti2 = new TNodeIndi(tnode: gri_a_node1, indicator: indi2, i_usage:0).save()
			
		}
		if(gri_a_node2.save(flush:true)){
			def ti3 = new TNodeIndi(tnode: gri_a_node2, indicator: indi3, i_usage:1).save()
			def ti4 = new TNodeIndi(tnode: gri_a_node2, indicator: indi4, i_usage:0).save()
		}
		if(gri_a_node4.save(flush:true)){
			def ti5 = new TNodeIndi(tnode: gri_a_node4, indicator: indi5, i_usage:1).save()
			def ti6 = new TNodeIndi(tnode: gri_a_node4, indicator: indi6, i_usage:0).save()
		}
		
		gri_a_node1.save()
		gri_a_node2.save()
		gri_a_node4.save()
		
		rootNode.save()
		rootNode2.save()
		rootNode3.save()
		
		//dummy records for schema A
		def rootNode4 = new TNode(name:"Nachhaltigkeitsbericht 2010", description:"Beschreibung", title:true, parent_id:0, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		
		def a_node1 = new TNode(name:"Nachhaltige Entwicklung und Universitäten", description:"Ökonomie", parent_id:11, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node2 = new TNode(name:"Die gegenwärtige globale Situation", description:"Mehr über Ökonomie", parent_id:11, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node3 = new TNode(name:"Die Rolle der Universitäten in der nachhaltigen Entwicklung", description:"Ökologie", parent_id:13, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node4 = new TNode(name:"Die Universität Oldenburg zwischen Tradition und Innovation", description:"Soziales", parent_id:13, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node5 = new TNode(name:"Organisation der Universität", description:"Mehr über Soziales", parent_id:11, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node6 = new TNode(name:"Motto und Werte", description:"Schlußwort", parent_id:16, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def a_node7 = new TNode(name:"GRI Richtlinien", description:"Schlußwort", parent_id:11, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		
		rootNode4.addToChildren(a_node1)
		rootNode4.addToChildren(a_node2)
		rootNode4.addToChildren(a_node5)
		rootNode4.addToChildren(a_node7)
		
		a_node2.addToChildren(a_node3)
		a_node2.addToChildren(a_node4)
		a_node5.addToChildren(a_node6)
		
		a_node1.save()
		a_node2.save()
		a_node3.save()
		a_node4.save()
		a_node5.save()
		a_node6.save()
		a_node7.save()
		
		if(a_node1.save(flush:true)){
			def ti7 = new TNodeIndi(tnode: a_node1, indicator: indi1, i_usage:0).save()
		}
		if(a_node2.save(flush:true)){
			def ti8 = new TNodeIndi(tnode: a_node2, indicator: indi2, i_usage:1).save()
			def ti9 = new TNodeIndi(tnode: a_node2, indicator: indi5, i_usage:0).save()
		}
		if(a_node3.save(flush:true)){
			def ti10 = new TNodeIndi(tnode: a_node3, indicator: indi3, i_usage:0).save()
		}
		if(a_node4.save(flush:true)){
			def ti11 = new TNodeIndi(tnode: a_node3, indicator: indi6, i_usage:1).save()
			
		}
		
		a_node1.save()
		a_node2.save()
		a_node3.save()
		a_node4.save()
		
		rootNode4.save()
		
		//dummy records for schema B
		def rootNode5 = new TNode(name:"Nachhaltigkeitsbericht 2009", description:"Beschreibung", title:true, parent_id:0, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		
		def b_node1 = new TNode(name:"Nachhaltige Entwicklung und Universitäten", description:"Ökonomie", parent_id:19, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node2 = new TNode(name:"Die gegenwärtige globale Situation", description:"Mehr über Ökonomie", parent_id:20, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node3 = new TNode(name:"Die Rolle der Universitäten in der nachhaltigen Entwicklung", description:"Ökologie", parent_id:21, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node4 = new TNode(name:"Die Universität Oldenburg zwischen Tradition und Innovation", description:"Soziales", parent_id:22, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node5 = new TNode(name:"Organisation der Universität", description:"Mehr über Soziales", parent_id:19, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node6 = new TNode(name:"Motto und Werte", description:"Schlußwort", parent_id:19, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		def b_node7 = new TNode(name:"GRI Richtlinien", description:"Schlußwort", parent_id:19, creator:user1, creationDate:new Date(),lastModifiedBy:user1, lastModified:new Date()).save()
		
		rootNode5.addToChildren(b_node1)
		rootNode5.addToChildren(b_node5)
		rootNode5.addToChildren(b_node6)
		rootNode5.addToChildren(b_node7)
		
		b_node1.addToChildren(b_node2)
		b_node2.addToChildren(b_node3)
		b_node3.addToChildren(b_node4)
		
		b_node1.save()
		b_node2.save()
		b_node3.save()
		
		
		if(b_node2.save(flush:true)){
			def ti12 = new TNodeIndi(tnode: b_node2, indicator: indi1, i_usage:0).save()
			def ti13 = new TNodeIndi(tnode: b_node2, indicator: indi2, i_usage:0).save()
		}
		if(b_node3.save(flush:true)){
			def ti14 = new TNodeIndi(tnode: b_node3, indicator: indi4, i_usage:1).save()
			
		}
		if(b_node4.save(flush:true)){
			def ti15 = new TNodeIndi(tnode: b_node4, indicator: indi7, i_usage:0).save()
			
		}
		
		if(b_node5.save(flush:true)){
			def ti16 = new TNodeIndi(tnode: b_node5, indicator: indi5, i_usage:1).save()
			
		}
		
		b_node2.save()
		b_node3.save()
		b_node4.save()
		b_node5.save()
		
		rootNode5.save()
		
		//dummy records for treport
		def rep1 = new TReport(name:"Schema GRIA", root:rootNode, lastModifiedBy:user1, lastModified:new Date(), creationDate:new Date(), description:"GRI A report", creator:user1).save()
		def rep2 = new TReport(name:"Schema GRIB", root:rootNode2, lastModifiedBy:user1, lastModified:new Date(), creationDate:new Date(), description:"GRI B report", creator:user1).save()
		def rep3 = new TReport(name:"Schema GRIC", root:rootNode3, lastModifiedBy:user1, lastModified:new Date(), creationDate:new Date(), description:"GRI A report", creator:user1).save()
		def rep4 = new TReport(name:"Schema Validierung A" ,root:rootNode4, lastModifiedBy:user1, lastModified:new Date(), creationDate:new Date(), description:"GRI A Validation", creator:user1).save()
		def rep5 = new TReport(name:"Schema Validierung B" ,root:rootNode5, lastModifiedBy:user1, lastModified:new Date(), creationDate:new Date(), description:"GRI A Validation", creator:user1).save()
		//
		// Bootstrap for EE
		//
		
		//Dummy records for Enum Report Status
		def status1 = new ReportStatus(status:"Approved", imageDir:"images/ReportEditor", imageFile:"releasedStatus.png").save()
		def status2 = new ReportStatus(status:"Development", imageDir:"images/ReportEditor", imageFile:"editStatus.png").save()
		def status3 = new ReportStatus(status:"Closed", imageDir:"images/ReportEditor", imageFile:"closeStatus.png").save()
		def status4 = new ReportStatus(status:"New", imageDir:"images/ReportEditor", imageFile:"newStatus.png").save()
		
		//Dummy records for Enum Report Status
		def astatus1 = new ArticleStatus(status:"Approved", imageDir:"images/ReportEditor", imageFile:"releasedStatus.png").save()
		def astatus2 = new ArticleStatus(status:"Development", imageDir:"images/ReportEditor", imageFile:"editStatus.png").save()
		def astatus3 = new ArticleStatus(status:"Closed", imageDir:"images/ReportEditor", imageFile:"closeStatus.png").save()
		def astatus4 = new ArticleStatus(status:"New", imageDir:"images/ReportEditor", imageFile:"newStatus.png").save()
		def astatus5 = new ArticleStatus(status:"Revision", imageDir:"images/ReportEditor", imageFile:"revisionStatus.png").save()	
		
		
		//Root article
		def rootArticle = new Article(name:"Root",content:"Root", status:astatus1, author:user1).save()
		def rootArticleReport2 = new Article(name:"Root",content:"Root", status:astatus1, author:user1).save()
		def rootArticleReport3 = new Article(name:"Root",content:"Root", status:astatus1, author:user1).save()
		def rootArticleReport4 = new Article(name:"Root",content:"Root", status:astatus1, author:user1).save()
		def rootArticleReport5 = new Article(name:"Root",content:"Root", status:astatus1, author:user1).save()
		//Testdatensatz, bitte nicht entfernen/ändern (Olaf)
		def rootArticleTest = new Article(name:"Root",content:"Root", status:astatus2, author:admin).save()
		
		
		def article1 = new Article(name:"Nachhaltige Entwicklung und Universitäten",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", parentArticle:rootArticle,status:astatus1, author:user1).save()
		def article2 = new Article(name:"Die gegenwärtige globale Situation",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user1, parentArticle:article1).save()
		def article3 = new Article(name:"Die Rolle der Universitäten in der nachhaltigen Entwicklung",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user2, parentArticle:article1).save()
		def article4 = new Article(name:"Die Universität Oldenburg zwischen Tradition und Innovation",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user2, parentArticle:rootArticle).save()
		def article5 = new Article(name:"Organisation der Universität",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user2, parentArticle:article4).save()
		def article6 = new Article(name:"Motto und Werte",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user2, parentArticle:article4).save()
		def article7 = new Article(name:"GRI-Richtlinien",content:"Dies ist der Nachhaltigkeitsbericht aus dem Jahr 2006", status:astatus1, author:user2, parentArticle:rootArticle).save()
		//Testdatensatz, bitte nicht entfernen/ändern (Olaf)
		def articleTest = new Article(name:"Testdatensatz",content:"<p>/Storm/Mediathek/martin/carling_.wmv</p><p><object"
		+" style='width:350px;height:350px;' classid='clsid:6bf52a52-394a-11d3-b153-00c04f79faa6' width='350' height='350'"
		+"codebase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701'><param name='autostart'"
		+"value='false' /><param name='url' value='/Storm/Mediathek/martin/carling_.wmv' /><embed style='width: 350px; height: 350px;'"
		+"type='application/x-mplayer2' width='350' height='350' src='/Storm/Mediathek/martin/carling_.wmv'autostart='false'>&nbsp;</embed></object>"
		+"</p><p>/Storm/Mediathek/martin/Olympic.mpg</p><p>"
		+"<object style='width: 350px; height: 350px;' classid='clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b' width='350' height='350' codebase='http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0'>"
		+"<param name='autoplay' value='false' />"
		+"<param name='src' value='/Storm/Mediathek/martin/Olympic.mpg' /><embed style='width: 350px; height: 350px;' type='video/quicktime' width='350' height='350' src='/Storm/Mediathek/martin/Olympic.mpg' autoplay='false'>&nbsp;</embed>"
		+"</object></p><p>/Storm/Mediathek/martin/The Dancer.avi</p><p>"
		+"<object classid='clsid:6bf52a52-394a-11d3-b153-00c04f79faa6' width='300' height='300' codebase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701'>"
		+"<param name='url' value='/Storm/Mediathek/martin/The Dancer.avi' />"
		+"<param name='src' value='/Storm/Mediathek/martin/The Dancer.avi' /><embed type='application/x-mplayer2' width='300' height='300' src='/Storm/Mediathek/martin/The Dancer.avi' url='/Storm/Mediathek/martin/The Dancer.avi'>&nbsp;</embed>"
		+"</object></p><p>/Storm/Mediathek/martin/maus.rm</p><p>"
		+"<object style='width: 500px; height: 100px;' classid='clsid:cfcdaa03-8be4-11cf-b84b-0020afbbccfa' width='500' height='100' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0'>"
		+"<param name='autogotourl' value='false' /><param name='src' value='/Storm/Mediathek/martin/maus.rm' /><embed style='width: 500px; height: 100px;' type='audio/x-pn-realaudio-plugin' width='500' height='100' src='/Storm/Mediathek/martin/maus.rm' autogotourl='false'>&nbsp;</embed>"
		+"</object></p><p>/Storm/Mediathek/martin/Mmmmh Lecker!.mov</p><p>"
		+"<object style='width: 300px; height: 300px;' classid='clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b' width='300' height='300' codebase='http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0'>"
		+"<param name='autoplay' value='false' />"
		+"<param name='src' value='/Storm/Mediathek/martin/Mmmmh Lecker!.mov' /><embed style='width: 300px; height: 300px;' type='video/quicktime' width='300' height='300' src='/Storm/Mediathek/martin/Mmmmh Lecker!.mov' autoplay='false'></embed>"
		+"</object></p><p>/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg</p><p>"
		+"<object style='width: 350px; height: 21px;' width='350' height='21' data='http://localhost:8080/Storm/js/tinymce/jscripts/tiny_mce/plugins/media/img/FOggPlayer.swf?url=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg&amp;title=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg' type='application/x-shockwave-flash'>"
		+"<param name='data' value='http://localhost:8080/Storm/js/tinymce/jscripts/tiny_mce/plugins/media/img/FOggPlayer.swf?url=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg&amp;title=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg' />"
		+"<param name='src' value='http://localhost:8080/Storm/js/tinymce/jscripts/tiny_mce/plugins/media/img/FOggPlayer.swf?url=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg&amp;title=/Storm/Mediathek/martin/Hennes Bender - 02 - Bielefeld.ogg' />"
		+"</object></p><p>&nbsp;</p><p>/Storm/Mediathek/martin/The Blues Brothers - Theme From Rawhide.mp3</p><p>"
		+"<object style='width: 100px; height: 100px;' classid='clsid:02bf25d5-8c17-4b23-bc80-d3488abddc6b' width='100' height='100' codebase='http://www.apple.com/qtactivex/qtplugin.cab#version=6,0,2,0'>"
		+"<param name='autoplay' value='false' />"
		+"<param name='src' value='/Storm/Mediathek/martin/The Blues Brothers - Theme From Rawhide.mp3' /><embed style='width: 100px; height: 100px;' type='video/quicktime' width='100' height='100' src='/Storm/Mediathek/martin/The Blues Brothers - Theme From Rawhide.mp3' autoplay='false'></embed>"
		+"</object></p><p>/Storm/Mediathek/martin/Xxiao05.swf</p><p><br /><object width='200' height='200' data='/Storm/Mediathek/martin/Xxiao05.swf' type='application/x-shockwave-flash'>"
		+"<param name='data' value='/Storm/Mediathek/martin/Xxiao05.swf' /><param name='src' value='/Storm/Mediathek/martin/Xxiao05.swf' />"
		+"</object></p><p>&nbsp;</p><p>/Storm/Mediathek/uli/A_MALE_BRAIN.GIF</p><p>&nbsp;</p>"
		+"<p><img title='Gehirn' src='/Storm/Mediathek/uli/A_MALE_BRAIN.GIF' alt='Gehirn' width='640' height='480' /></p>"
		+"<p>/Storm/Mediathek/uli/16FARBEN.JPG</p>"
		+"<p><img title='16 Farben' src='/Storm/Mediathek/uli/16FARBEN.JPG' alt='16 farben' width='1058' height='742' /></p>"
		+"<p>&nbsp; &nbsp; /Storm/Mediathek/uli/ZUG.tif</p>"
		+"<p><img title='Zug' src='/Storm/Mediathek/uli/ZUG.tif' alt='Zug' /></p>"
		+"<p>&nbsp;</p>", parentArticle:rootArticleTest,status:astatus2, author:admin).save()
		
		new Report(name:"Nachhaltigkeitsbericht 2006", description:"Uni Oldenburg", language:language1, treport:rep1, rootArticle:rootArticle, status:status1, author:user1, releasedDate:new Date(new GregorianCalendar(2006,Calendar.JANUARY,1).getTimeInMillis())).save()
		new Report(name:"Nachhaltigkeitsbericht 2007", description:"Uni Oldenburg", language:language2, treport:rep2, rootArticle:rootArticleReport3, status:status1, author:user2, releasedDate:new Date(new GregorianCalendar(2007,Calendar.MARCH,13).getTimeInMillis())).save()
		new Report(name:"Nachhaltigkeitsbericht 2008", description:"Nachhaltigkeitsbericht 2008 Universität Graz", language:language1, treport:rep2, rootArticle:rootArticleReport2, status:status1, author:user2, releasedDate:new Date(new GregorianCalendar(2008,Calendar.SEPTEMBER,21).getTimeInMillis())).save()
		new Report(name:"Nachhaltigkeitsbericht 2009", description:"Uni Oldenburg", language:language2, treport:rep3, rootArticle:rootArticleReport4, status:status4, author:user1, releasedDate:new Date(new GregorianCalendar(2009,Calendar.OCTOBER,30).getTimeInMillis())).save()
		//Testdatensatz, bitte nicht entfernen/ändern (Olaf)
		new Report(name:"Testdatensatz", description:"Testdatensatz", language:language2, treport:rep2, rootArticle:rootArticleTest, status:status2, author:admin).save()
		
		def article12 = new Article(name:"Nachhaltiges Leitbild ",
		content:"<p><span style'color: #333399;'>Die Karl-Franzens-Universit&auml;t Graz ist die zweit&auml;lteste \n"+
		"Universit&auml;t &Ouml;sterreichs und eine der gr&ouml;&szlig;ten des Landes. Zahlreiche herausragende \n"+
		"WissenschafterInnen, unter ihnen sechs Nobelpreistr&auml;ger, haben hier gelehrt und geforscht. Mit rund \n"+
		"22.000 Studierenden und mehr als 3.500 MitarbeiterInnen tr&auml;gt die Universit&auml;t Graz entscheidend zum\n"+
		"pulsierenden Leben der steirischen Landeshauptstadt bei. Die geografische Lage beg&uuml;nstigt einen regen \n"+
		"wissenschaftlichen und kulturellen Austausch mit dem s&uuml;d&ouml;stlichen Europa. Jahrzehntelange, intensive \n"+
		"Kooperationen mit Partnerinstitutionen aus der Region haben zu gro&szlig;er Expertise sowie zur Einrichtung des \n"+
		"gesamtuniversit&auml;ren Schwerpunkts &bdquo;S&uuml;dosteuropa&ldquo; gef&uuml;hrt &ndash; einmalig\n"+
		"im deutschsprachigen Raum.</span></p>", 
		status:astatus1, author:user2, parentArticle:rootArticleReport2).save()
		def article13 = new Article(name:"Katholisch-Theologische Fakultät",
		content:"<p>Die Katholisch-Theologische Fakult&auml;t ist seit jeher einem Studium generale verpflichtet. Gleichzeitig\n"+
		"werden durch Spezialisierungen in Lehre und Forschung sowie durch Wissenstransfer in den gesellschaftlichen Bereich \n"+
		"nachhaltige Akzente gesetzt. Ein erheblicher Teil der T&auml;tigkeiten einer Fakult&auml;t ist eo ipso auf Nachhaltigkeit\n"+
		"angelegt, auch wenn diese selten ausdr&uuml;cklich unter dieser Perspektive gesehen werden; dabei zeichnen sich nachhaltige \n"+
		"Prozesse durch eine gelungene Verbindung von Tradition und Innovation aus. Unter diesem Aspekt k&ouml;nnen exemplarisch folgende \n"+
		"Aktivit&auml;ten der Fakult&auml;t genannt werden:</p> <p>&bull; Die Verbindung von Tradition (Studium generale) mit Innovation\n"+
		"zeigt sich besonders markant in der kontinuierlichen Arbeit an den Studienpl&auml;nen: Hier kommt geradezu paradigmatisch eine auf \n"+
		"Nachhaltigkeit ausgerichtete Kompetenz zum Tragen, die in der Lage ist, die durch kirchliche wie staatliche Vorgaben gegebenen \n"+
		"Anforderungen mit den zuk&uuml;nftig erforderlichen beruflichen Belangen von Studierenden in Balance zu halten.</p> <p>&bull; \n"+
		"Besonders nachhaltige Entwicklungen zeigen sich in der Vernetzung des Fakult&auml;tsschwerpunktes &bdquo;Theologische Frauen- und \n"+
		" Geschlechterforschung&ldquo; mit fakult&auml;ts&uuml;bergreifenden sowie auf Internationalit&auml;t ausgerichteten Perspektiven \n"+
		"und Netzwerken (z. B. Gender Studies, Elisabeth-G&ouml;ssmann-Preis f&uuml;r JungforscherInnen).</p> <p>&bull; Innovative Impulse \n"+
		"sowie langj&auml;hrige und weitreichende Vernetzungen in die Bereiche Kunst, Film und Medien kennzeichnen auch den Fakult&auml;tsschwerpunkt\n"+
		"&bdquo;Theologie &ndash; Kultur &ndash; &Auml;sthetik&ldquo; (z. B. Entwicklung eines automatischen Filmanalyse- Programms im Rahmen eines \n"+
		"FWF-Projektes mit der TU Graz und dem Joanneum Research).</p> <p>&bull; Zu den seit Jahrzehnten von der internationalen kirchlichen \n"+
		"&Ouml;ffentlichkeit, wie z.B. der Konferenz Europ&auml;ischer Kirchen (KEK) deutlich wahrgenommenen Aktivit&auml;ten geh&ouml;rt der \n"+
		"Einsatz der Fakult&auml;t f&uuml;r eine Intensivierung des &ouml;kumenischen Dialogs; zu den H&ouml;hepunkten z&auml;hlte die Verleihung \n"+
		"des Ehrendoktorats an den &Ouml;kumenischen Patriarchen von Konstantinopel, Bartholomaios I.</p> <p>&bull; In einem sehr intensiven \n"+
		"Diskussionsprozess widmet sich die Fakult&auml;t gegenw&auml;rtig dem Thema Nachhaltigkeit im Spannungsfeld von Tradition und Innovation \n"+
		"bei den &Uuml;berlegungen einer optimalen Verbindung der o. g. traditionellen und mit gro&szlig;er Au&szlig;enwirkung betriebenen \n"+
		"Fakult&auml;tsschwerpunkte mit den sich konstituierenden Forschungsschwerpunkten.</p> <p>&bull; Angesichts globaler Entwicklungen ist \n"+
		"f&uuml;r das unl&auml;ngst eingef&uuml;hrte und auf gro&szlig;e Resonanz sto&szlig;ende Masterstudium &bdquo;Religionswissenschaft&ldquo; \n"+
		"nachhaltige Wirkung zu erwarten.</p> <p>&bull; Die im Rahmen des S&uuml;dosteuropa- Schwerpunktes entwickelte und seit knapp zehn Jahren \n"+
		"j&auml;hrlich durchgef&uuml;hrte Summer University Europa S&uuml;d/ Ost (EuroS/O) steht f&uuml;r nachhaltigen Wissenstransfer und entwickelte \n"+
		"ein breit gef&auml;chertes Netzwerk in die s&uuml;dosteurop&auml;ischen Nachbarl&auml;nder. Nachhaltiges Denken und Handeln war mehrfach auch \n"+
		"inhaltliches Thema.</p> <p>&bull; F&ouml;rderung von Studierenden und NachwuchswissenschafterInnen (Mentoring, StudienassistentInnen), \n"+
		"Herausgabe der Buchreihe &bdquo;Theologie im kulturellen Dialog&ldquo;, Durchf&uuml;hrung der Veranstaltungsreihe &bdquo;Religion am \n"+
		"Donnerstag&ldquo;, Werkstattgespr&auml;che f&uuml;r MaturantInnen.</p>", 
		status:astatus1, author:user2, parentArticle:article12).save()
		def article14 = new Article(name:"Rechtswissenschaftliche Fakultät",
		content:"<p>An der Rechtswissenschaftlichen Fakult&auml;t bietet das Diplomstudium eine allgemein wissenschaftlich-juristische Ausbildung, \n"+
		"auf der nachfolgende berufliche Ausbildungswege aufbauen k&ouml;nnen. Auf die Einrichtung von Studienzweigen wird verzichtet, doch besteht im \n"+
		"dritten Studienabschnitt die M&ouml;glichkeit, zwischen verschiedenen F&auml;chergruppen, die die Hauptrichtungen der rechtsberuflichen Schwerpunkte \n"+
		"repr&auml;sentieren, zu w&auml;hlen und so Akzente zu setzen, ohne dass der universelle Charakter des Studiums verloren geht. Hervorgehoben k&ouml;nnen \n"+
		"in Bezug auf Nachhaltigkeit die Lehrveranstaltungen zum Themenbereich Umweltrecht werden, die im Studienplan der Rechtswissenschaftlichen Fakult&auml;t \n"+
		"verankert sind. Der nachhaltigen Entwicklung der Fakult&auml;t muss auch der Dekan seine Aufmerksamkeit widmen. Sie stellt eine Herausforderung und Aufgabe \n"+
		"dar, der zentrale Bedeutung beizumessen ist.</p> <p>Dabei erweist sich die Dokumentation der diesbez&uuml;glichen Entwicklung als besonders wichtiges \n"+
		"Instrument zur Bewusstseinsbildung und Fortschrittsevaluierung. Im gegebenen Rahmen k&ouml;nnen nur einige wenige einschl&auml;gige Punkte angef&uuml;hrt \n"+
		"werden. Zun&auml;chst ist die Entwicklung der Fakult&auml;t auf dem Gebiet des Gender-Mainstreamings und der Frauenf&ouml;rderung hervorzuheben. Schon\n"+
		" bisher war dieser Bereich ein Schwerpunkt in der Forschung und Lehre an der Fakult&auml;t, was insbesondere in der Beteiligung der Fakult&auml;t und ihrer \n"+
		"Mitglieder an der Entwicklung und Installierung des Masterstudiums <strong>&bdquo;Interdisziplin&auml;re Geschlechterstudien&ldquo;</strong>, der Einrichtung \n"+
		"eines eigenen Arbeitskreises im Rahmen des Sechsten Fakult&auml;tstages, dem Aufbau eines weltumspannenden Gender-Netzwerkes mit Mitgliedern in Argentinien, \n"+
		"Brasilien, Italien und Spanien oder dem im Mai 2010 stattfindenden interdisziplin&auml;ren Symposium &bdquo;Die Juristen und die Frauen&ldquo; Ausdruck gefunden \n"+
		"hat. Das Bekenntnis der Fakult&auml;t zur aktiven Frauenf&ouml;rderung ist auch in der Entwicklung ihrer Personalstruktur zu verfolgen.</p> <p>Abgesehen vom \n"+
		"&uuml;berdurchschnittlich hohen Anteil von Professorinnen und dem wachsenden Anteil von Frauen unter den Habilitierten wurden alle bisher an der Fakult&auml;t\n"+
		" eingerichteten Qualifikationsstellen an Frauen vergeben. Zu unterstreichen ist ferner das besonders ausgepr&auml;gte und bereits angeklungene Bestreben der \n"+
		"Fakult&auml;t und ihrer Mitglieder, sowohl auf nationaler wie auch auf internationaler Ebene Kooperationen einzugehen, Partnerschaften aufzubauen und sich in \n"+
		"unterschiedlichsten Gremien und Initiativen einzubringen. Nur beispielhaft sei hier unter vielen die Bem&uuml;hung der Fakult&auml;tsleitung um eine verst&auml;rkte \n"+
		"Zusammenarbeit auf Ebene der DekanInnen und in der Europ&auml;ischen Vereinigung der Rechtsfakult&auml;ten (ELFA) um ein koordiniertes und konzertiertes Vorgehen \n"+
		"bei der Entwicklung von dem Bologna-Programm entsprechenden juristischen Studienangeboten genannt. Schlie&szlig;lich obliegt es dem Unterzeichnenden zu bekr&auml;ftigen, \n"+
		"dass die Rechtswissenschaftliche Fakult&auml;t den Herausforderungen einer nachhaltigen Entwicklung auch weiterhin mit gr&ouml;&szlig;tem Engagement begegnen und ihre \n"+
		"diesbez&uuml;glichen Anstrengungen insbesondere in ihren Schwerpunkten (S&uuml;dosteuropa, Mediation, Gender und Recht, Kunst- und Kulturrecht, Demokratie und \n"+
		"Menschenrechte, Europ&auml;isches Recht und europ&auml;ische Politik) kontinuierlich fortsetzen wird.</p>", 
		status:astatus1, author:user2, parentArticle:article12).save()
		def article15 = new Article(name:"Sozial- und Wirtschaftswissenschaftliche Fakultät",
		content:"<p>Die Sozial- und Wirtschaftswissenschaftliche Fakult&auml;t bietet international ausgerichtete betriebs- und volkswirtschaftliche sowie soziologische Studien. \n"+
		"<strong>Schwerpunkte im Nachhaltigkeitsbereich liegen in der Lehre wie auch im Studienangebot (u.a. Bachelor- und Masterstudium &bdquo;Umweltsystemwissenschaften&ldquo;). \n"+
		"</strong></p> <p>Die Sozial- und Wirtschaftswissenschaftliche Fakult&auml;t f&uuml;hlt sich dem Konzept der Nachhaltigkeit in besonders hohem Ma&szlig;e verpflichtet. Von \n"+
		"den drei S&auml;ulen der Nachhaltigkeit, der &ouml;kologischen, der &ouml;konomischen und der sozialen Nachhaltigkeit, sind ja gleich zwei im Namen der Fakult&auml;t enthalten: \n"+
		"die soziale und die &ouml;konomische Seite der Nachhaltigkeit. Dementsprechend sind Themen der Nachhaltigkeit seit vielen Jahren fest in Lehre und Forschung der Fakult&auml;t \n"+
		"verankert. In der Forschung sind z.B. der Schwerpunkt &bdquo;Umwelt&ouml;konomik&ldquo; am Institut f&uuml;r Volkswirtschaftslehre zu erw&auml;hnen und nat&uuml;rlich die \n"+
		"pionierhaften Projekte von em. Univ.-Prof. Dr. Heinz Strebel am Institut f&uuml;r Innovations- und Umweltmanagement. Auch das Wegener Zentrum der Umwelt-, Regional- und \n"+
		"Bildungswissenschaftlichen Fakult&auml;t wird von der Sozial- und Wirtschaftswissenschaftlichen Fakult&auml;t personell und materiell unterst&uuml;tzt.</p> <p>In der Lehre \n"+
		"ist Nachhaltigkeit ein Thema, das sich in zahlreichen Lehrveranstaltungen der Fakult&auml;t wiederfindet. Dazu kommt die enge Kooperation mit der Fakult&auml;t f&uuml;r Umwelt-, \n"+
		"Regional- und Bildungswissenschaften im Studium der Umweltsystemwissenschaften. Allein der Fachschwerpunkt &bdquo;Betriebswirtschaftslehre&ldquo; des Studiums der \n"+
		"Umweltsystemwissenschaften (USW-BWL) hat an unserer Universit&auml;t mit fast 700 Studierenden die Dimension einer kleinen Fakult&auml;t erreicht. Das Interesse an dieser \n"+
		"Studienrichtung ist ungebrochen und hat sich in den letzten vier Jahren vervierfacht. Dazu kommen noch einmal fast 140 H&ouml;rerInnen im Fachschwerpunkt &bdquo;Volkswirtschaftslehre&ldquo; \n"+
		"(USW-VWL), dessen Studierendenzahlen sich in den letzten vier Jahren mehr als verdoppelt haben. Der Nachhaltigkeitsbericht der Universit&auml;t Graz soll zeigen, dass wir Nachhaltigkeit \n"+
		"nicht nur erforschen und lehren, sondern als Organisation auch leben.</p>", 
		status:astatus1, author:user2, parentArticle:article12).save()
		def article16 = new Article(name:"Geisteswissenschaftliche Fakultät",
		content:"<p>Die Geisteswissenschaftliche Fakult&auml;t stellt die gr&ouml;&szlig;te Fakult&auml;t der Universit&auml;t dar. Diese spannt den Bogen der Lehre von klassischen \n"+
		"F&auml;chern wie Latein und Griechisch bis hin zu neuen Medien wie der Informationsmodellierung in den Geisteswissenschaften. Den Begriff der Nachhaltigkeit im universit&auml;ren  \n"+
		"Bereich zu verwenden, scheint mir bei dem derzeitigen Universit&auml;tskonzept nicht unproblematisch, vor allem deshalb, weil dem Begriff Nachhaltigkeit doch ein gewisser Zeitfaktor \n"+
		"innewohnt. Betrachtet man nun die universit&auml;re Szene, so ist die derzeit l&auml;ngste &uuml;berschaubare zeitliche Linie die Dauer eines Universit&auml;tsrates von f&uuml;nf Jahren,  \n"+
		"wogegen ein Rektorat auf vier Jahre konzipiert ist und die sogenannten Globalbudgets sich &uuml;ber drei Jahre erstrecken.</p> <p>Die unter dem Rektorat befindlichen Funktion&auml;rsebenen \n"+
		"sind auf zwei Jahre angelegt. Rechnet man dazu die raschen Ver&auml;nderungen der universit&auml;tsrechtlichen Szene, so fragt man sich, wie Nachhaltigkeit aussehen soll und welchen Sinn \n"+
		"nachhaltige Ma&szlig;nahmen machen, wenn sich doch in einem relativ kurzen Zeitraum die Basis universit&auml;ren Daseins radikal ver&auml;ndern kann &ndash; wie die letzten Jahre auch gezeigt \n"+
		"haben. Nichtsdestoweniger muss es das Ziel fakult&auml;rer T&auml;tigkeit sein, in die Zukunft zu denken, zu planen und zu wirken, ohne unbedingt den Begriff der Nachhaltigkeit zu strapazieren. \n"+
		"Solche in die Zukunft gerichteten Aktivit&auml;ten sind meist darauf ausgerichtet, gegenw&auml;rtige Probleme zu sanieren oder &uuml;berhaupt neue Wege einzuschlagen, ohne dass man dabei wei&szlig;,  \n"+
		"ob die zuk&uuml;nftigen Ver&auml;nderungen (z.B. im Dienstrecht) nicht doch alles ad absurdum f&uuml;hren. Ma&szlig;nahmen dieser Art gibt es an der Geisteswissenschaftlichen Fakult&auml;t einige: \n"+
		"</p> <p style='padding-left: 30px;'>&bull; Das Bem&uuml;hen, m&ouml;glichst viele Habilitationen in Gang zu bringen, um den wissenschaftlichen Fortbestand der Fakult&auml;t zu wahren. Eine spezielle \n"+
		" Zielrichtung bilden dabei jene AssistentInnen, die in Lehre, Forschung und Verwaltung massiv eingebunden sind und dadurch das Ziel der Habilitation aus den Augen verloren haben.</p>  \n"+
		"<p style='padding-left: 30px;'>&bull; Eine zweite Linie bilden die jungen Studierenden, die bei entsprechender Qualifikation durch Doktorandenstipendien auf den wissenschaftlichen Weg gebracht  \n"+
		"werden, um eine weitere Nachwuchsebene f&uuml;r die Fakult&auml;t zu bilden. Dazu geh&ouml;rt auch das von einigen Fakult&auml;tsmitgliedern geschaffene Doktorandenkolleg. Beide Ma&szlig;nahmen  \n"+
		"zeigen in hervorragender Weise das interdisziplin&auml;re Leistungsspektrum der Geisteswissenschaftlichen Fakult&auml;t auf.</p> <p style='padding-left: 30px;'>&bull; Im Rahmen der Ma&szlig;nahmen  \n"+
		"unter Punkt 1 und 2 sind auch jene Aktivit&auml;ten zu sehen, die der wissenschaftlichen Karriere von Frauen dienen: Habilitationsf&ouml;rderungen und besondere Unterst&uuml;tzung bei Forschungsvorhaben. \n"+
		"</p> <p style='padding-left: 30px;'>&bull; Im Bereich der Sprachausbildung wurde die Studieneingangsphase kostensparend durch Auslagerungen an Treffpunkt Sprachen neu gestaltet. Die Einsparungen kommen  \n"+
		"f&uuml;r die bessere Ausbildung der h&ouml;heren Semester zum Tragen.</p> <p style='padding-left: 30px;'>&bull; Die Realisierung des Bologna-Modells an der Fakult&auml;t.</p> <p style='padding-left: 30px;'>&bull;  \n"+
		"Zur Bewusstseinsst&auml;rkung im Hinblick auf die Leistungsf&auml;higkeit der Geisteswissenschaftlichen Fakult&auml;t selbst wurde im Herbst 2008 ein Fakult&auml;tstag veranstaltet, der prim&auml;r an die  \n"+
		"Au&szlig;enwelt gerichtet war, aber erstaunlicherweise auch nach innen gewirkt hat.</p> <p style='padding-left: 30px;'>&bull; Die Nachbesetzung der &uuml;ber Jahre hinweg vakanten Professuren, teilweise auch  \n"+
		"unter neuer Zielrichtung, sowie die Schaffung neuer Professuren sind ebenfalls unter dem Gesichtspunkt der St&auml;rkung f&uuml;r die Zukunft anzuf&uuml;hren. Auch die positive Rolle des Rektorates sei hier erw&auml;hnt.</p>", 
		status:astatus1, author:user2, parentArticle:article12).save()
		def article17 = new Article(name:"Naturwissenschaftliche Fakultät",content:"<p>Eine breite naturwissenschaftliche Grundausbildung, erg&auml;nzt um innovative Spezialisierungen, bildet das Profil der Naturwissenschaftlichen Fakult&auml;t. Mit dem Projekt NAWI-Graz wurde ein Meilenstein in der Geschichte der Universit&auml;t gesetzt. Basierend auf traditionell bew&auml;hrter Zusammenarbeit haben die Universit&auml;t Graz und die Technische Universit&auml;t Graz 2004 ihre Kooperation in Lehre und Forschung weiter verdichtet und bieten nun gemeinsam Studien in den folgenden Bereichen an: Chemie, Technische und Molekulare Biowissenschaften, Mathematik, Physik und Geowissenschaften. Als einer der wichtigsten Ans&auml;tze f&uuml;r Nachhaltigkeit im Bereich der Forschung und Lehre an der Naturwissenschaftlichen Fakult&auml;t ist das Kooperationsprojekt NAWI Graz anzuf&uuml;hren.</p> <p>Die Universit&auml;t Graz und die Technische Universit&auml;t Graz setzen Impulse f&uuml;r ForscherInnen der Zukunft auf dem Weg an die internationale Spitze. Die Ausbildung von exzellenten NachwuchswissenschafterInnen und h&ouml;chst qualifizierten F&uuml;hrungskr&auml;ften f&uuml;r Wissenschaft und Wirtschaft nimmt die Graz Advanced School of Science (GASS) wahr. Ziel ist es, ein Grazer Center of Excellence der Naturwissenschaften f&uuml;r mehr als 60 Studierende zu etablieren, das Forschung und Lehre ideal verbindet. Die GASS beinhaltet Doktoratsschulen der Fachbereiche Physik, Chemie, Mathematik, Erdwissenschaften und Molekulare und Technische Biowissenschaften. Neben Kooperationen mit der Technischen Universit&auml;t bestehen auch intensive Kontakte und gemeinsame Projekte mit der Medizinischen Universit&auml;t, etwa im Bereich der Neuropsychologie. Herausragende Projekte in den Biowissenschaften (der Spezialforschungsbereich <strong>&bdquo;Lipotox&ldquo;</strong>), der Mathematik (Spezialforschungsbereich &bdquo;Mathematische Optimierung und Anwendungen in der Biomedizin&ldquo;), den Nanowissenschaften (das nationale Forschungsnetzwerk &bdquo;Nanowissenschaften auf Oberfl&auml;chen &ldquo; ) und drei Networks of Excellence an der Naturwissenschaftlichen Fakult&auml;t (PsychologInnen erarbeiten neue E-Learning-Konzepte, PhysikerInnen erforschen M&ouml;glichkeiten der optischen Daten&uuml;bertragung im Nano-Bereich und ChemikerInnen untersuchen Polysaccharide als nachwachsende Rohstoffe) geben einen &Uuml;berblick &uuml;ber die zukunftstr&auml;chtigen Forschungsinitiativen der Naturwissenschaftlichen Fakult&auml;t.</p> <p>Auch die sozialen Aspekte der Nachhaltigkeit wurden in Form von Gender-Mainstreaming und Frauenf&ouml;rderung in den letzten Jahren verst&auml;rkt in die nachhaltige Entwicklung einbezogen. Es gelang zwei Mal, sich im Frauenf&ouml;rder-Anreizsystem der Universit&auml;t Graz unter die ersten drei Fakult&auml;ten in Bezug auf Nachwuchsf&ouml;rderung und Erh&ouml;hung des Professorinnenanteils zu platzieren. Zur Erh&ouml;hung der Attraktivit&auml;t von Studienrichtungen, in denen der Frauenanteil gering ist, sind f&ouml;rderliche Ma&szlig;nahmen in Planung.</p>", status:astatus1, author:user2, parentArticle:article12).save()
		def article18 = new Article(name:"Umwelt-, Regional- und Bildungswissenschaftliche Fakultät",content:"<p>Menschen wollen etwas bewegen und von ihrem Dasein Spuren hinterlassen. Doch sind nachfolgende Generationen an unseren Spuren interessiert? Der Kahlschlag von B&auml;umen, verseuchtes Wasser oder strahlender Atomm&uuml;ll werden kein dankbares Interesse erwecken. Kulturelle Leistungen, Musik, Literatur, Kunstwerke eher schon. Technologische Erfindungen? Es kommt darauf an. Wir m&uuml;ssen beurteilen und unterscheiden, wenn wir die nachhaltigen Wirkungen unseres Handelns bewerten. Das Absch&auml;tzen von Folgen wird bedeutsam. Daf&uuml;r brauchen wir Empathie und Achtsamkeit:</p> <p>&bull; Einf&uuml;hlen in Situationen, die wir nicht eindeutig vorhersehen k&ouml;nnen, die wir nur aus unserer Gegenwart in eine k&uuml;nftige projizieren,</p> <p>&bull; Achtsamkeit, die uns darauf aufmerksam macht, dass unser Blick in die Zukunft sehr begrenzt ist.</p> <p>Zweifel wird eine wichtige Kategorie. Zweifel ist eine wissenschaftliche Tugend. Wir zweifeln Aussagen und Ergebnisse an, wir &uuml;berpr&uuml;fen Resultate und Quellen. Wir werden dabei achtsam, ob Aussagen begr&uuml;ndet und tragf&auml;hig sind. Die institutionalisierte Kritik, das st&auml;ndige methodisch abgesicherte Beurteilen zeigt Wissenschaft als Instrument, das Aussagen immer relativiert. Wissenschaft vermittelt nachhaltige Unsicherheit, sie institutionalisiert den Zweifel, sie ist der Widerspruch zur Welt der schnellen Entscheidungen. Heute stellt die Gesellschaft Fragen, die die Wissenschaft beantworten soll. Die Probleme, die zu l&ouml;sen, und die Themen, die zu behandeln sind, verlangen meist interdisziplin&auml;re Strategien. Die Kooperation von Wissenschaftsdisziplinen, die unterschiedliche Methoden, Theorien und Begriffsbestimmungen verwenden, setzt Lern- und Kommunikationsprozesse voraus.</p> <p>Doch auch ein Selbstverst&auml;ndnis, das auf Skepsis beruht. Skepsis nicht im alles verneinenden Sinn, sondern als begleitende Vorsicht, die wissenschaftliche Aussagen als vorl&auml;ufige Urteile und Entscheidungen anerkennt, um offen zu bleiben f&uuml;r neue Erkenntnisse. Dann wird auch die Gefahr der unerw&uuml;nschten Spuren reduziert, weil das, was gebaut, errichtet, institutionalisiert oder implementiert werden soll, achtsamer Entscheidung vorbehalten bleibt. Der nachhaltige Zweifel wird zur wissenschaftlichen Haltung, nicht um Gewissheiten zu scheuen, sondern um sie st&auml;ndig neu in Frage zu stellen.</p>", status:astatus1, author:user2, parentArticle:article12).save()
		
		
		def article19 = new Article(name:"Studium und Lehre",content:"<p>Der Medieneinsatz an der Universit&auml;t Graz soll als integrierter Bestandteil der Pr&auml;senzlehre die Gestaltung von qualitativ hochwertigen Bildungsangeboten unterst&uuml;tzen. Medien werden &auml;u&szlig;erst vielf&auml;ltig eingesetzt; die einzelnen L&ouml;sungen weisen ein &uuml;beraus hohes Qualit&auml;tsniveau auf. Ein Gro&szlig;teil der Entwicklung geht in den Regelbetrieb der Universit&auml;t Graz &uuml;ber. Die letzten sechs Jahre haben gezeigt, dass der Medieneinsatz in der Lehre langsam, aber kontinuierlich zunimmt und eine Verankerung etablierter Leitprojekte in der Organisation stattfindet.</p>", status:astatus1, author:user2, parentArticle:rootArticleReport2).save()
		def article30 = new Article(name:"Nachhaltigkeit in Studium und Lehre",content:"<p>An der Universit&auml;t Graz sind das Konzept Nachhaltigkeit und die daraus abzuleitenden Themenbereiche in einem breiten Spektrum von Lehrveranstaltungen integriert. Systemisches und vernetztes Denken ist Bestandteil einer forschungsgeleiteten Lehre und stellt eine Kompetenz dar, die alle Studierenden f&uuml;r ihr Studium und ihr sp&auml;teres Berufsleben so fr&uuml;h wie m&ouml;glich erlernen und anwenden sollen. Hier setzt u.a. das strategische Projekt &bdquo;Basismodul&ldquo; an: Im Bereich der universit&auml;ren Lehre wird es immer wichtiger, einen bedeutenden Beitrag zu nachhaltiger Entwicklung zu leisten, da diese einen wesentlichen Einfluss auf die Bildung zuk&uuml;nftiger Entscheidungstr&auml;gerInnen hat. Lehre innerhalb der ordentlichen Studien und der universit&auml;ren Weiterbildung muss dabei unter den Prinzipien der Nachhaltigkeit insbesondere auf ein gesellschaftsbezogenes, probleml&ouml;sungsorientiertes Lernen der Studierenden ausgerichtet sein. In diesem Zusammenhang ist es zentrale Aufgabe jeder Universit&auml;t, Studierenden nicht nur ein fundiertes Fachwissen, sondern auch ein grunds&auml;tzliches Verst&auml;ndnis f&uuml;r nachhaltige Entwicklung, basierend auf ethischen Werten und einer integrativen Sicht von Gesellschaft, Wirtschaft und Umwelt, zu vermitteln. Die Umsetzung der Nachhaltigkeitsorientierung in der universit&auml;ren Lehre wird an der Universit&auml;t Graz einerseits durch spezielle Studien- und andererseits durch spezielle Lehrangebote gesichert.</p> <p><strong>Das Basismodul</strong></p> <p><strong> </strong>Die Universit&auml;t Graz ist sich der Verantwortung gegen&uuml;ber ihren Studierenden bewusst und wei&szlig; um den f&uuml;r viele nicht einfach zu bew&auml;ltigenden &Uuml;bergang von der Schule zur Universit&auml;t. Aus diesem Grund fand das Basismodul als eines von 16 strategischen Projekten Ber&uuml;cksichtigung. Das Projekt gr&uuml;ndet sich auf der Erfahrung, dass nur ein Teil der Studierenden bei Studienbeginn eine ausreichend ausgepr&auml;gte Vorstellung davon hat, welches Studium f&uuml;r sie geeignet w&auml;re. Dies hat Studienwechsel und Studienabbr&uuml;che zur Folge. Das Basismodul wird in allen Bachelorstudien (mit Ausnahme der Bachelorstudien der NAWI Graz) implementiert. Das gesamte Basismodul weist ein Ausma&szlig; von etwa 30 ECTS-Anrechnungspunkten auf, welches bei Absolvierung aller Anteile zu einem Zertifikat f&uuml;hrt. Es umfasst einf&uuml;hrende Lehrveranstaltungen, die die fachliche, fakult&auml;tsweite und universit&auml;tsweite Orientierung am Beginn des Studiums an der Universit&auml;t erleichtern sollen und besteht aus folgenden Teilen:</p> <p>&bull; universit&auml;tsweites Basismodul</p> <p>&bull; fakult&auml;tsweites Basismodul</p> <p>&bull; fachspezifisches Basismodul des gew&auml;hlten Studiums Gemeinsam mit dem Projekt &bdquo;Umstellung der Studienarchitektur nach dem Bologna-Modell&ldquo; bedeutet die Realisierung des Basismoduls eine wesentliche Neuerung im Bereich Lehre im Hinblick auf eine universit&auml;re und institutionelle Profilierung, die auch eine Modernisierung der Curricula erm&ouml;glicht. Neben der Vermittlung von spezifisch methodischem und theoretischem Grundlagenwissen werden sowohl Elemente des klassischen Studium generale mit fach&uuml;bergreifenden Einf&uuml;hrungen als auch spezielle Fach&uuml;berblicke geboten, die die Orientierung der Studierenden erleichtern.</p> <p><strong>Lehrveranstaltungen und Studienangebote &ndash; Nachhaltigkeitsfokus</strong></p> <p>Zahlreiche Lehrveranstaltungen an der Universit&auml;t Graz fokussieren direkt den Themenbereich nachhaltige Entwicklung bzw. dessen Teilbereiche. Obwohl sich eine Kategorisierung bzw. Abgrenzung &bdquo;einschl&auml;giger&ldquo; Lehrveranstaltungen als schwierig erweist und stark von den angelegten Kriterien abh&auml;ngt, soll hier ein Schlaglicht auf das reichhaltige Lehrangebot geworfen werden. Im Studienjahr 2007/08 wurden u.a. folgende Lehrveranstaltungen angeboten:</p> <p>&bull; Nachhaltige Raumentwicklung und Raumplanung &bull; Eigenst&auml;ndige, nachhaltige und systemische Raumentwicklung</p> <p>&bull; Nachhaltige Raumplanung im Transportwesen</p> <p>&bull; Umwelt und nachhaltige Entwicklung</p> <p>&bull; Praktikum Tourismus und Nachhaltigkeit</p>", status:astatus1, author:user2, parentArticle:article19).save()
		def article31 = new Article(name:"Akademie für Neue Medien und Wissenstransfer",content:"<p>In den letzten Jahren haben zahlreiche Angeh&ouml;rige der Universit&auml;t Graz technologiegest&uuml;tzte und andere Lehr- und Lernmethoden erforscht, entwickelt und angewendet. Jedoch gab es keine zentrale Anlaufstelle, die alle Dimensionen einer Lernkultur mit Neuen Medien in strategischen und inhaltlichen &Uuml;berlegungen b&uuml;ndelt und den gemeinsamen, universit&auml;tsweiten Diskussions- und Entwicklungsprozess unterst&uuml;tzt. Eine eineinhalbj&auml;hrige Planungsphase f&uuml;hrte 2006 zur Gr&uuml;ndung der Akademie f&uuml;r Neue Medien und Wissenstransfer. Die r&auml;umliche Zusammenf&uuml;hrung bewirkt neben der Nutzung von Synergien und der ressourcenschonenden Durchf&uuml;hrung von Lehr- und Forschungsprojekten auch in hohem Ma&szlig;e die F&ouml;rderung einer zielgerichteten Diskussion.</p> <p>Im Mittelpunkt steht die Einbindung aller AkteurInnen in einen gemeinsamen Gestaltungsprozess. An der Universit&auml;t Graz agiert die Akademie f&uuml;r Neue Medien und Wissenstransfer als Kommunikationsdrehscheibe f&uuml;r alle Beteiligten, leitet Handlungsperspektiven f&uuml;r mediengest&uuml;tzte Lehre ab und bem&uuml;ht sich, Ankn&uuml;pfungspunkte zwischen Strategieentwicklungsprozessen der Universit&auml;t (top-down), Entwicklungsleistungen durch Lehrende und Studierende (bottom-up) sowie Serviceleistungen der Support-Einrichtungen zu initiieren.</p> <p><strong>Unsere Mission</strong></p> <p>Wir sind EntwicklungspartnerInnen, wenn es darum geht,</p> <p>&bull; Bildungsanliegen mit innovativen und qualitativ hochwertigen L&ouml;sungen zu begegnen,</p> <p>&bull; die Entwicklung von Bildungsstrategien innerhalb von Organisationen zu unterst&uuml;tzen und in der Unternehmenskultur nachhaltig zu verankern,</p> <p>&bull; die Vernetzung und den respektvollen Umgang des Menschen mit Umwelt und Unternehmen zu intensivieren.</p>", status:astatus1, author:user2, parentArticle:article19).save()
		
		def article20 = new Article(name:"Forschung ",content:" <p style='background-color: #ccffff;'>Das FMS ber&auml;t WissenschafterInnen und NachwuchswissenschafterInnen bei nationalen und internationalen Forschungsf&ouml;rderprogrammen. Angeboten werden eine Reihe an Informationsveranstaltungen sowie die gezielte Unterst&uuml;tzung bei der Antragstellung und bei der Administration an der Universit&auml;t w&auml;hrend der Laufzeit des Projekts. Seit 2008 bietet die Abteilung aktive Unterst&uuml;tzung bei der Verfassung von Antr&auml;gen im geistes-, sozial- und kulturwissenschaftlichen Bereich an.</p>", status:astatus1, author:user2, parentArticle:rootArticleReport2).save()
		def article21 = new Article(name:"Podcasting",content:"<p>Nachhaltige Entwicklung kann nur durch die Einbindung der relevanten Anspruchsgruppen geschehen und vorangetrieben werden. Doch was, wenn eine der zahlenm&auml;&szlig;ig gr&ouml;&szlig;ten Gruppen, n&auml;mlich jene der Studierenden, gar nichts von den Aktivit&auml;ten an der eigenen Universit&auml;t wei&szlig;? Dass dies tats&auml;chlich der Fall ist, zeigen Umfragen, die in einigen Lehrveranstaltungen mit einem Nachhaltigkeitsschwerpunkt (u.a. <em>&bdquo;Management of Sustainable Development&ldquo;</em> mit dem Schwerpunkt &bdquo;Umweltmanagement&ldquo; sowie &bdquo;Betriebswirtschaftliche Umwelt&ouml;konomie&ldquo;) in den letzten zwei Jahren immer wieder durchgef&uuml;hrt wurden. Zwar gibt es an der Universit&auml;t Graz viele Beispiele f&uuml;r Lehrveranstaltungen und Projekte, in denen die Themen Nachhaltigkeit und nachhaltige Entwicklung eine gro&szlig;e Rolle spielen, doch werden wichtige Anspruchsgruppen nicht oder nur vereinzelt erreicht. In den letzten Jahren wurde klar, dass es sich tats&auml;chlich um eine neue Generation von Studierenden handelt, die sogenannte &bdquo;Net-Generation&ldquo;.1 Soziale Netzwerke spielen sich auf Plattformen wie Facebook und StudiVZ ab und bieten entertainment und socialization zugleich.</p> <p>Diese Entwicklungen wirken sich positiv auf die Vernetzung innerhalb der Universit&auml;t bzw. innerhalb der unterschiedlichen Stakeholdergruppen der Universit&auml;t aus. Hier kann vonseiten der Lehrenden Podcasting dazu eingesetzt werden, Lehrveranstaltungsergebnisse aufzubereiten. Zudem k&ouml;nnen die Ergebnisse auch kursexterne Interessierte erreichen. Diesen Ansatz, der das Medium Podcast als ein dezentrales, internetbasiertes Medienkonzept3 nutzt, um Projektergebnisse in einer einfachen und leicht handhabbaren Form aufzubereiten, verfolgt das Institut f&uuml;r Systemwissenschaften, Innovations- und Nachhaltigkeitsforschung nun seit einigen Semestern. Dabei geht es weniger darum ein professionelles Sendeprogramm mit ausgebildeten SprecherInnen in Form von w&ouml;chentlich neu erscheinenden Episoden zu produzieren, als ein modernes Medium bedarfsgerecht und f&uuml;r den Einsatzzweck richtig dimensioniert zu nutzen. So entstand im Wintersemester 2008/09 erstmals eine Podcast-Reihe zum Thema &bdquo;Nachhaltige Entwicklung an der Universit&auml;t Graz&ldquo;. Die Studierendengruppen besch&auml;ftigten sich &uuml;ber ein Semester hinweg intensiv mit den folgenden Themen:</p> <p>&bull; Nachhaltige Entwicklung im Bereich des Karriereprogramms der Universit&auml;t Graz</p> <p>&bull; Nachhaltige Entwicklungsm&ouml;glichkeiten im Bereich Beleuchtungssysteme der Universit&auml;t Graz</p> <p>&bull; Einsatzm&ouml;glichkeiten alternativer Energieformen an der Universit&auml;t Graz</p> <p>&bull; Marketing f&uuml;r nachhaltige Entwicklung an der Universit&auml;t Graz</p> <p>&bull; Nachhaltig Studieren an der Universit&auml;t Graz</p> <p>&bull; Nachhaltige Entwicklung im IT-Bereich der Universit&auml;t Graz</p>", status:astatus1, author:user2, parentArticle:article20).save()
		def article22 = new Article(name:"Universität und Umweltschutz",content:"<p>Nachhaltigkeitsberichte als Mittel der Unternehmenskommunikation sind ein noch relativ junges Ph&auml;nomen. Wohl lassen sich, wenn man weiter zur&uuml;ckblickt, bereits &uuml;ber einen l&auml;ngeren Zeitraum diverse Non-financial Reports in Gestalt von Umwelt-, Sozial- oder &auml;hnlichen Berichten beobachten. Dabei wurde indessen nicht jene integrative Sicht von &ouml;konomischen, &ouml;kologischen und sozialen Aspekten an den Tag gelegt, die f&uuml;r Nachhaltigkeitsberichte moderner Pr&auml;gung kennzeichnend ist. So wird allgemein der sogenannte &bdquo;Triple-P-Report&ldquo; (People, Planet and Profits) von Shell aus dem Jahr 1999 als einer der ersten Berichte dieser Art angesehen.</p> <p style='padding-left: 30px;'><strong>Betrachtet man die Entwicklung auf globaler Ebene, so zeigt sich, dass die Anzahl der Unternehmen, die einen Sozial-, Umwelt- und/oder Nachhaltigkeitsbericht publizieren, seit den 1990-iger Jahren stetig anstieg. W&auml;hrend 1993 weltweit weniger als 100 Unternehmen eigene Non-financial Reports ver&ouml;ffentlichten, waren es 2007 bereits mehr als 2.500.1 Vor allem bei den als Global Players bekannten gr&ouml;&szlig;ten Unternehmen haben sich Nachhaltigkeitsberichte mittlerweile in vielen F&auml;llen einen festen Platz im Rahmen der Unternehmensberichterstattung erobert. </strong></p> <p>So zeigt eine Studie von KPMG aus dem Jahr 2008, dass 79% der weltweit gr&ouml;&szlig;ten 250 Unternehmen (Global Fortune 250) in einem eigenst&auml;ndigen Bericht &uuml;ber ihre Sozial- und Umweltperformance informieren; verglichen mit dem Jahr 2005 ein Anstieg um 27 Prozentpunkte. Freilich, angesichts von weltweit ca. 60.000 Multinationals ist man aber selbst auf dieser Ebene noch weit von einer etablierten Praxis der Berichterstattung entfernt. In geographischer Hinsicht nehmen Unternehmen aus Japan und aus Gro&szlig;britannien eine Vorreiterrolle ein. In beiden L&auml;ndern liegt die Quote der Berichterstattung durch die jeweils 100 gr&ouml;&szlig;ten Unternehmen mittlerweile bei &uuml;ber 80%.</p> <p>In &Ouml;sterreich hat das Instrument Nachhaltigkeitsbericht bis dato noch keinen &auml;hnlich hohen Stellenwert erlangt. So wurden auf der Plattform Corporate Register f&uuml;r das Jahr 2006 gerade einmal 30 Non-financial Reports, &uuml;berwiegend in Gestalt von Nachhaltigkeitsberichten, von &ouml;sterreichischen Unternehmen registriert. Dessen ungeachtet, wird aber auch hierzulande das Ph&auml;nomen Nachhaltigkeitsberichterstattung in zunehmendem</p>", status:astatus1, author:user2, parentArticle:rootArticleReport2).save()
		def article23 = new Article(name:"Menschen ",content:"<p><strong>Familienfreundliche Universit&auml;t</strong></p> <p>Die Universit&auml;t Graz sieht die Schaffung von Rahmenbedingungen f&uuml;r die Vereinbarkeit von famili&auml;ren Aufgaben und Beruf bzw. Studium als ihre Verpflichtung an. Konflikte zwischen beruflichen und famili&auml;ren Aufgaben gehen oftmals entweder zu Lasten der beruflichen Leistungsf&auml;higkeit oder zu Lasten der Lebensqualit&auml;t der MitarbeiterInnen. Gezielte Ma&szlig;nahmen, die MitarbeiterInnen mit Betreuungspflichten die Bew&auml;ltigung des Alltags erleichtern und es ihnen erm&ouml;glichen, Beruf und Familie besser zu vereinbaren, leisten daher einen Beitrag zur &ouml;konomischen Nachhaltigkeit des Unternehmens sowie zur nachhaltigen Entwicklung der Gesellschaft. Das Bem&uuml;hen der Universit&auml;t Graz um familienfreundliche Arbeitsbedingungen wurde 2007 durch die Auszeichnung als &bdquo;Frauen- und familienfreundlichster Betrieb&ldquo; in der Steiermark und auf Bundesebene (in der Kategorie &bdquo;&Ouml;ffentlich-rechtliche Unternehmen&ldquo;) gew&uuml;rdigt.</p> <p><strong>Bedarfsgerechte Kinderbetreuung </strong></p> <p>Die Universit&auml;t Graz strebt ein qualit&auml;tsvolles, vielf&auml;ltiges und bedarfgerechtes Kinderbetreuungsangebot an und hat ihre Leistungen auf diesem Gebiet seit Jahren kontinuierlich ausgebaut und verbessert.Die Kinderbetreuungsbeauftragte ber&auml;t das Rektorat und die Universit&auml;tsangeh&ouml;rigen bei Fragen zum Thema Kinderbetreuung sowie bei der Bedarfserhebung und der Schaffung von neuen Kinderbetreuungsangeboten. Mit der Interuniversit&auml;ren Kinderbetreuungsanlaufstelle unikid steht Studierenden und Bediensteten eine Service- und Beratungsstelle zur Verf&uuml;gung. Diese wurde 2007 als Abteilung in der Struktur der Universit&auml;t Graz verankert. Unikid organisiert Betreuungsangebote und unterst&uuml;tzt die Universit&auml;tsangeh&ouml;rigen dabei, f&uuml;r ihre Kinder die jeweils bestm&ouml;gliche, dem individuellen Bedarf entsprechende Betreuungsm&ouml;glichkeit zu finden. Dazu z&auml;hlt unter anderem die Organisation einer Sommerkinderbetreuung f&uuml;r Kindergarten- und Schulkinder &uuml;ber die Sommerferien. Im Juli 2008 gab es erstmalig in Kooperation mit der JuniorUni Graz auch ein Angebot f&uuml;r Jugendliche im Alter von zehn bis vierzehn Jahren. Unikid organisiert ein Kursprogramm und Kinderevents, welche Eltern und Kindern auch die M&ouml;glichkeit zur Vernetzung geben. Unikid bietet seit 2001 einen BabysitterInnen- Pool und seit 2002 die Universit&auml;ts- Wiki-Kinderkrippe, eine ganzj&auml;hrige, personalintensive Betreuung, an. Die 2007 begonnene Kooperation mit einer Einrichtung f&uuml;r flexible, stundenweise Kinderbetreuung erm&ouml;glicht eine verg&uuml;nstigte Inanspruchnahme dieser Betreuungsform von Kindern im Alter von null bis zehn Jahren.</p>", status:astatus1, author:user2, parentArticle:rootArticleReport2).save()
		
		def article24 = new Article(name:"Article XXX",content:"<p>English Text English Text English Text English Text </p>", status:astatus1, author:user2, parentArticle:rootArticleReport3).save()
		def article241 = new Article(name:"Article XXXAAA",content:"<p>English Text English Text English Text English Text </p>", status:astatus1, author:user2, parentArticle:article24).save()
		def article242 = new Article(name:"Article XXXBBB",content:"<p>English Text English Text English Text English Text </p>", status:astatus1, author:user2, parentArticle:article24).save()
		def article25 = new Article(name:"Article YYY",content:"<p>English Text English Text English Text English Text </p>", status:astatus1, author:user2, parentArticle:rootArticleReport3).save()
		def article26 = new Article(name:"Article ZZZ",content:"<p>English Text English Text English Text English Text </p>", status:astatus1, author:user2, parentArticle:rootArticleReport3).save()
		
		
		//add indicators
		new ArtInd(article:article1,indicator:indi1, author:user1, value:101).save(flush:true)
		new ArtInd(article:article1,indicator:indi2, author:user1, value:99).save(flush:true)
		new ArtInd(article:article1,indicator:indi3, author:user1, value:101).save(flush:true)
		new ArtInd(article:article2,indicator:indi4, author:user1, value:48).save(flush:true)
		new ArtInd(article:article2,indicator:indi5, author:user1, value:77).save(flush:true)
		new ArtInd(article:article2,indicator:indi6, author:user1, value:10).save(flush:true)
		new ArtInd(article:article3,indicator:indi7, author:user1, value:25).save(flush:true)
		new ArtInd(article:article3,indicator:indi11, author:user2, value:10).save(flush:true)
		
		new ArtInd(article:article12,indicator:indi11, author:user2, value:5470).save(flush:true)
		new ArtInd(article:article12,indicator:indi12, author:user2, value:59).save(flush:true)
		new ArtInd(article:article12,indicator:indi10, author:user2, value:33).save(flush:true)
		new ArtInd(article:article13,indicator:indi6, author:user2, value:45).save(flush:true)
		new ArtInd(article:article15,indicator:indi3, author:user2, value:600).save(flush:true)
		new ArtInd(article:article14,indicator:indi7, author:user2, value:20).save(flush:true)
		
		
		//dummy record for infocart
		def cart1 = new Infocart(name:"Infokorb Mitarbeiter",user:user2,visible:false,published:true).save()
		cart1.addToItems(new InfocartItem(article:Article.get(15)))
		cart1.addToItems(new InfocartItem(article:Article.get(16)))
		cart1.addToItems(new InfocartItem(article:Article.get(17)))
		cart1.addToItems(new InfocartItem(article:Article.get(18)))
		cart1.addToItems(new InfocartItem(article:Article.get(19)))
		cart1.addToItems(new InfocartItem(article:Article.get(20)))
		cart1.addToItems(new InfocartItem(article:Article.get(21)))
		cart1.save()
		def cart2 = new Infocart(name:"Infokorb Studenten",user:user1,visible:false,published:true).save()
		cart2.addToItems(new InfocartItem(article:Article.get(22)))
		cart2.addToItems(new InfocartItem(article:Article.get(23)))
		cart2.addToItems(new InfocartItem(article:Article.get(24)))
		cart2.addToItems(new InfocartItem(article:Article.get(25)))
		cart2.addToItems(new InfocartItem(article:Article.get(26)))
		cart2.save()
		def cart3 = new Infocart(name:"Infocart 3",user:user1,visible:true).save()
		cart3.addToItems(new InfocartItem(article:article4))
		cart3.addToItems(new InfocartItem(article:article5))
		cart3.addToItems(new InfocartItem(article:article6))
		cart3.addToItems(new InfocartItem(article:article7))
		cart3.save()
		def cart4 = new Infocart(name:"Infocart 4",user:user2,visible:true).save()
		
		Random rand = new Random()
		for (i in Article.findAll()) {
			//Ratings
			//def rating = new Rating(stars: "1", raterId: user1.id, raterClass: "systemadministration.usermanagement.User")
			//assert rating.save()
			//def link = new RatingLink(rating: rating, ratingRef: i.id, type: "article")
			//assert link.save()
			for(int j=1;j<=12;j++){
				if(rand.nextInt(3)==0){
					i.rate(User.get(j), rand.nextInt(5)+1)
				}
			}
			//Kommentare
			def uc1 = new Usercomment(commentator: edzard, title: "Super!", content: "<p>Gut geschrieben, weiter so!</p>")
			def uc2 = new Usercomment(commentator: olaf, title: "Spannende Sache", content: "<p>Sehr interessant, wusste gar nicht, dass die so viel machen.</p>", reference: uc1)
			def uc3 = new Usercomment(commentator: daniel, title: "Gravatar", content: "<p>Guckt mal, ich hab sogar einen Avatar! ;-)</p>")
			def uc4 = new Usercomment(commentator: admin, title: "Re: Gravatar", content: "<p>Juhuu, endlich!</p>", reference: uc3)
			i.addToUsercomments(uc1)
			i.addToUsercomments(uc2)
			i.addToUsercomments(uc3)
			i.addToUsercomments(uc4)
		}
		
		//Tags
		article12.addTags(["Nobelpreisträger", "geografische Lage", "Südosteuropa", "international"])
		article13.addTags(["nachhaltiges Leitbild", "katholisch", "Religionswissenschaft", "Fakultät", "Geschlechterforschung"])
		article14.addTags(["nachhaltiges Leitbild", "Rechtswissenschaft", "Fakultät", "überdurchschnittlicher Frauenanteil", "interdisziplinär", "Geschlechterforschung"])
		article15.addTags(["nachhaltiges Leitbild", "Sozialwissenschaft", "Wirtschaftswissenschaft", "Fakultät", "BWL", "VWL", "international"])
		article16.addTags(["nachhaltiges Leitbild", "Geisteswissenschaft", "nachhaltige Maßnahmen", "Habilitationen", "Doktorandenstipendien"])
		article20.addTags(["FMS", "Forschung", "international", "Forschungsförderung", "Geisteswissenschaft", "Sozialwissenschaft", "Kulturwissenschaft"])
		
		//dummy records for HelpSection
		def Helpsection1 = new HelpSection(name:"Anmelden und Abmelden", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		def Helpsection2 = new HelpSection(name:"Bearbeitung der Benutzereinstellungen", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		def Helpsection3 = new HelpSection(name:"Anlegung eines Artikels", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		def Helpsection4 = new HelpSection(name:"Anlegung eines Indikators", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		def Helpsection5 = new HelpSection(name:"Erstellung Infokorb", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		def Helpsection6 = new HelpSection(name:"Bewertung", description:"<p>Hier steht ein wenig einleitender und beschreibender Text zu dieser Hilfekategorie. Der Benutzer kann sich so besser zurechtfinden, wenn der Text passend gestaltet ist. Er ist jederzeit frei editierbar.</p>").save()
		
		
		//dummy records for HelpArticle
		def Helparticle1 = new HelpArticle(title:"Anmelden",section: Helpsection1,
		helpcontent:"<p>Die persönliche Begrüßung auf der Homepage wird ausgeschaltet und\n" +
		"die Zugriffsberechtigung auf Ihre Kundendaten aufgehoben, sobald Sie sich bei\n" +
		"dem von Ihnen benutzten öffentlichen Rechner abmelden.<br>\n" +
		"folgende Seite zeigt Ihnen dann die leere Anmeldeseite mit den Eingabefeldern\n" +
		"für E-Mail-Adresse und Passworteingabe.<br>\n" +
		"Von dieser Seite aus können Sie entweder auf den Reiter Home oder auf\n" +
		"einen der Links zu den Produktlinien in der oberen Navigationsleiste klicken\n" +
		". Benutzen Sie nicht den Back- oder Zurück-Button Ihres Browsers. Sie sehen,\n" +
		"dass die Homepage Sie nun nicht mehr namentlich begrüßt, da nun die Cookies\n" +
		"von Amazon.de entfernt wurden. Schließen Sie dann bitte noch den Browser,\n" +
		"damit nicht durch den Back- oder Zurück-Button doch noch auf Ihre persönlichen\n" +
		"Daten zugegriffen werden kann.\n").save()
		def Helparticle2 = new HelpArticle(title:"Abmelden",section: Helpsection1,
		helpcontent:"Um die Informationen über Ihr Konto einsehen zu können,\n" +
		"müssen Sie sich bei Amazon.de anmelden. Dazu müssen Sie nur Ihre E-Mail\n" +
		"-Adresse und Ihr Passwort eingeben. Sie können sich auch jederzeit über\n" +
		"unsere Startseite anmelden. Klicken Sie dazu unter der oberen Navigationsleiste\n" +
		"in diesem Text Neukunde? Bitte hier starten auf  Bitte hier starten.<br>\n" +
		"Wenn Sie angemeldet sind, werden Sie bei weiteren Besuchen auf unserer\n" +
		"Startseite persönlich begrüßt. Durch Ihre Anmeldung werden auch Ihre \n" +
		"persönlichen Empfehlungen aktiviert. Wenn Sie keine Empfehlungen \n" +
		"bekommen möchten, oder den Computer mit anderen teilen, die keinen Zugriff \n" +
		"auf die Daten in Ihrem Konto haben sollen, können Sie sich ganz einfach wieder\n" +
		"abmelden.\n").save()
		
		//Records for RE
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article1,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article2,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article3,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article4,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article5,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article6,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article7,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:anonym,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)
		
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article1,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article2,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article3,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article4,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article5,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article6,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article7,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:user1,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)		
		
		
		new AccessLog(user:user2,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:user2,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)
		new AccessLog(user:user2,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:user2,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:user2,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		new AccessLog(user:admin,module:ModuleService.initialiseModule("article"),article:article30,typeAccess:1).save(flush:true)
		
		
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article13,typeAccess:1).save(flush:true)	
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article12,typeAccess:1).save(flush:true)	
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article1,typeAccess:1).save(flush:true)
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article2,typeAccess:1).save(flush:true)	
		new AccessLog(user:olaf,module:ModuleService.initialiseModule("article"),article:article3,typeAccess:1).save(flush:true)	
		
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article16,typeAccess:1).save(flush:true)
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article13,typeAccess:1).save(flush:true)	
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article12,typeAccess:1).save(flush:true)	
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article1,typeAccess:1).save(flush:true)
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article2,typeAccess:1).save(flush:true)	
		new AccessLog(user:edzard,module:ModuleService.initialiseModule("article"),article:article3,typeAccess:1).save(flush:true)
		
		ArticleAccessReportingService.updateAccessArticleType()
		ArticleToArticleCollaborativeFilteringService.updateArticleToArticleCollaborativeFiltering();
		ArticleViewedByFriendsService.updateArticleViewedByFriends()
		ArticleViewedByInterestgroupService.updateArticleViewedByInterestgroup()
	}
	
	/**
	 * BootStrap loading for the test env
	 * 
	 *
	 */
	def initTest = { servletContext ->
		User user1 = User.findByUsername("User1") 
		User user2 = User.findByUsername("User2") 
		User anonym = User.findByUsername("Anonym") 
		User olaf = User.findByUsername("olaf")
		User edzard = User.findByUsername("edzard")
		User daniel = User.findByUsername("daniel")
		User admin = User.findByUsername("admin")
		
		//Dummy SAP Connection
		//SAPConnection sapConnection1 = new SAPConnection(name:"SAPConnection1",description:"...",author:user1,client:"927", login:"WIPG-01",password:"123456", locale:"DE",url:"m22z.hcc.uni-magdeburg.de",system:"22").save()
		SAPConnection sapConnection2 = new SAPConnection(name:"SAPConnection2",description:"...",author:user1,client:"927", login:"WIPG-01",password:"123456", locale:"DE",url:"m22z.hcc.uni-magdeburg.de",system:"22").save()
		
		//Dummy BAPI
		//Bapi bapi1 = new Bapi(name:"Bapi1", description:"...",author:user1,connection:sapConnection1, bapi:"BAPI_EXCHANGERATE_GETDETAIL",importParameter:"20071203;DATE;1012;RATE_TYPE;EUR;FROM_CURR;USD;TO_CURRNCY",exportStructure:"EXCH_RATE",exportValue:"EXCH_RATE").save()
		Bapi bapi1 = new Bapi(name:"Bapi2", description:"...",author:user1,connection:sapConnection2, bapi:"BAPI_MATERIAL_AVAILABILITY",importParameter:"1000;PLANT;1400-300;MATERIAL;ST;UNIT",exportStructure:"AV_QTY_PLT",exportValue:"AV_QTY_PLT").save()
		
		//Dummy Database Connection
		DatabaseConnection db1 = new DatabaseConnection(name:"DB1", description:"...",author:user1,url:"jdbc:mysql://devdb.uni-oldenburg.de:3306/nachhaltigkeit",driver:"com.mysql.jdbc.Driver",user:"nachhaltigkeit",password:"dschland").save()
		
		//Dummy SQL
		String sqlStatement = "select count(distinct `bezeichnung`) from `nachhaltigkeit`.`modul_deskriptor` where `bezeichnung` like '%umwelt%' or `bezeichnung` like '%nachhaltig%';"
		SQLStatement sql1 = new SQLStatement(name:"sql1",description:"...",author:user1,connection:db1,sqlStatement:sqlStatement).save()
		
		//Dummy records for Enum Language
		Language language1 = Language.findByCountryCode("de")  
		Language language2 = Language.findByCountryCode("en")
		
		//dummy records for charts
		def chart1 = new Chart(name:"Balkendiagramm", description:"Ein Balkendiagramm").save()
		def chart2 = new Chart(name:"Liniendiagramm", description:"Ein Liniendiagramm").save()
		def chart3 = new Chart(name:"Tortendiagramm", description:"Ein Tortendiagramm").save()
		def chart4 = new Chart(name:"3DTortendiagramm", description:"Ein 3DTortendiagramm").save()
		
		//Dummy records for Enum Report Status
		def status1 = new ReportStatus(status:"Approved", imageDir:"images/ReportEditor", imageFile:"releasedStatus.png").save()
		def status2 = new ReportStatus(status:"Development", imageDir:"images/ReportEditor", imageFile:"editStatus.png").save()
		def status3 = new ReportStatus(status:"Closed", imageDir:"images/ReportEditor", imageFile:"closeStatus.png").save()
		def status4 = new ReportStatus(status:"New", imageDir:"images/ReportEditor", imageFile:"newStatus.png").save()
		
		//Dummy records for Enum Report Status
		def astatus1 = new ArticleStatus(status:"Approved", imageDir:"images/ReportEditor", imageFile:"releasedStatus.png").save()
		def astatus2 = new ArticleStatus(status:"Development", imageDir:"images/ReportEditor", imageFile:"editStatus.png").save()
		def astatus3 = new ArticleStatus(status:"Closed", imageDir:"images/ReportEditor", imageFile:"closeStatus.png").save()
		def astatus4 = new ArticleStatus(status:"New", imageDir:"images/ReportEditor", imageFile:"newStatus.png").save()
		def astatus5 = new ArticleStatus(status:"Revision", imageDir:"images/ReportEditor", imageFile:"revisionStatus.png").save()
		
		//dummy records for HelpSection
		def Helpsection1 = new HelpSection(name:"Anmelden und Abmelden").save()
		def Helpsection2 = new HelpSection(name:"Bearbeitung der Benutzereinstellungen").save()
		def Helpsection3 = new HelpSection(name:"Anlegung eines Artikels").save()
		def Helpsection4 = new HelpSection(name:"Anlegung eines Indikators").save()
		def Helpsection5 = new HelpSection(name:"Erstellung Infokorb").save()
		def Helpsection6 = new HelpSection(name:"Bewertung").save()
	}
	/**
	 * BootStrap loading for the Prod env
	 * 
	 */
	def initProd = { servletContext ->
		initDev()
	}
	
	def destroy = {
	}
} 
