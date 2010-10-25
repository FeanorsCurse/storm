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
import systemadministration.usermanagement.*
import systemadministration.modulmanagement.*
import systemadministration.recommender.*

/**
 * BootStrap for SystemAdministration
 *
 * @author Edzard Fisch, Oliver Norkus
 * (@see BootStrap)
 */

class SystemAdministration {
	/**
	 * BootStrap loading for the Dev env
	 * 
	 *
	 */
	def initDev = { servletContext ->
		//Gerrit: This is important for i18n!
		def appContext = ServletContextHolder.getServletContext().getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
		def messageSource = appContext.getBean("messageSource")
		messageSource.fallbackToSystemLocale = false
		
		//module installieren
		installUser()
		installModule()
		
		installRole()
		installRoleUser()
		enableSecurity()
		
		
		//Dummy records for Enum Language
		def language1 = new Language (countryCode:"de",name:"Deutsch",enabled:true, site:true, report:true).save()
		def language2 = new Language (countryCode:"en",name:"English",enabled:true, site:true, report:true).save()
		//FIXME: Gerrit (22.11.2010): nicht unterstützte Sprachen vorerst entfernt
		//		def language3 = new Language (countryCode:"ru",name:"Русский",enabled:true, site:true, report:true).save()
		//		def language4 = new Language (countryCode:"tc",name:"Türkçe",enabled:true, site:true, report:true).save()
		//		def language5 = new Language (countryCode:"bg",name:"Български",enabled:true, site:true, report:true).save()
		//		def language6 = new Language (countryCode:"da",name:"Danske",enabled:true, site:true, report:true).save()
		//		def language7 = new Language (countryCode:"ja",name:"日本語",enabled:true, site:true, report:true).save()
		//		def language8 = new Language (countryCode:"ja",name:"العربية",enabled:true, site:true, report:true).save()
		
		
		//dummy records for Templates
		new Template(name:"uniol", activated:"false").save()
		new Template(name:"default", activated:"true").save()
		
		new Welcome(text:'<h2>Dieses ist ein Newssystem</h2> <p>Es kann viel Text enthalten. Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.Es kann viel Text enthalten.</p> <p> <object style="width: 90%; height: 250px;" width="100" height="100" data="http://www.youtube.com/v/uIElt1GBSOQ" type="application/x-shockwave-flash"> <param name="data" value="http://www.youtube.com/v/uIElt1GBSOQ" /> <param name="src" value="http://www.youtube.com/v/uIElt1GBSOQ" /> </object> </p>').save()
		
	}
	
	
	
	/**
	 * BootStrap loading for the test env
	 * 
	 *
	 */
	def initTest = { servletContext ->
		//TODO Bitte eintragen
		initDev()
		
	}
	/**
	 * BootStrap loading for the Prod env
	 * 
	 *
	 */
	def initProd = { servletContext ->
		//TODO Bitte eintragen
		initDev()
		
	}
	
	def destroy = {
	}
	
	
	def installModule() {
		ModuleService.initialiseModule("user")
		ModuleService.initialiseModule("role")
		ModuleService.initialiseModule("module")
		ModuleService.initialiseModule("help")
		ModuleService.initialiseModule("helpArticle")
		ModuleService.initialiseModule("infocart")
		ModuleService.initialiseModule("infocartItem")
		ModuleService.initialiseModule("rating")
		ModuleService.initialiseModule("tagcloud")
		ModuleService.initialiseModule("usercomment")
		ModuleService.initialiseModule("taggable")
		ModuleService.initialiseModule("rateable")
		ModuleService.initialiseModule("article")
		ModuleService.initialiseModule("report")
		ModuleService.initialiseModule("category")
		ModuleService.initialiseModule("indicator")
		ModuleService.initialiseModule("TNode")
		ModuleService.initialiseModule("TReport")
		ModuleService.initialiseModule("accessLog")
		ModuleService.initialiseModule("changeLog")
		ModuleService.initialiseModule("securityLog")
		ModuleService.initialiseModule("config")
		ModuleService.initialiseModule("language")
		ModuleService.initialiseModule("plugin")
		ModuleService.initialiseModule("template")
		ModuleService.initialiseModule("rule")
		ModuleService.initialiseModule("media")
		ModuleService.initialiseModule("backend")
		ModuleService.initialiseModule("module")
		ModuleService.initialiseModule("home")
		ModuleService.initialiseModule("articleIndicator")
		ModuleService.initialiseModule("accessArticleTyp")
		ModuleService.initialiseModule("articleToArticleCollaborativeFiltering")
		ModuleService.initialiseModule("ArticleViewedByInterestgroup")
		ModuleService.initialiseModule("ArticleViewedByFriends")
		ModuleService.initialiseModule("welcome")
		ModuleService.initialiseModule("feed")
		ModuleService.initialiseModule("bapi")
		ModuleService.initialiseModule("databaseConnection")
		ModuleService.initialiseModule("SAPConnection")
		ModuleService.initialiseModule("SQLStatement")
		ModuleService.initialiseModule("bapi")
		ModuleService.initialiseModule("newsletter")
		ModuleService.initialiseModule("question")
		ModuleService.initialiseModule("rssFeed")
	}
	
	
	def installUser() {
		//dummy records for Users
		//chefr.
		User burns = new User(username:"burns",password:"123456",email:"john@doe.com",lastname:"Burns",firstname:"Charles Montgomery 'Monty'",sex:"Herr",validationEMailCode:"1").save()
		//redakteur
		User user = new User(username:"user",password:"123456",email:"john@doe.com",lastname:"Simpsons",firstname:"user",sex:"Herr",validationEMailCode:"1").save()
		//admin
		User admin = new User(username:"admin",password:"123456",email:"john@doe.com",lastname:"Simpsons",firstname:"admin",sex:"Herr",validationEMailCode:"1").save()
		// user
		User barney = new User(username:"barney",password:"123456",email:"john@doe.com",lastname:"Gumble",firstname:"Barney",sex:"Herr",validationEMailCode:"1").save()
		//User anonym = new User(username:"Anonym",password:"123456",email:"keine@da.com",lastname:"Anonmy",firstname:"john",sex:"Herr").save()
		User anonym = new User(username:"Anonym",password:"123456",email:"keine@da.com",lastname:"Anonmy",firstname:"john",sex:"Herr",validationEMailCode:"1").save()
		
		//dummy records for InterestGroup
		def ig1 = new InterestGroup(name:"Mitarbeiter",description:"Mitarbeiter").save()
		def ig2 = new InterestGroup(name:"Studenten",description:"studenten").save()
		def ig3 = new InterestGroup(name:"Externe",description:"externe").save()
		
		
		//wird von den anderen bootstrap sachen benutzt
		User user1 = new User(username:"User1",password:"123456",email:"john@doe.com",lastname:"Burns",firstname:"Charles Montgomery 'Monty'",sex:"Herr",validationEMailCode:"1", interestGroup:ig1).save()
		User user2 = new User(username:"User2",password:"123456",email:"john@doe.com",lastname:"Burns",firstname:"Charles Montgomery 'Monty'",sex:"Herr",validationEMailCode:"1", interestGroup:ig1).save()
		
		//fuer die Beta
		User olaf = new User(username:"olaf",password:"123456",email:"olaf@olaf-roeder.de",lastname:"Roeder",firstname:"Olaf",sex:"Herr",validationEMailCode:"1", interestGroup:ig1).save()
		User edzard = new User(username:"edzard",password:"123456",email:"edzard@doe.com",lastname:"Fisch",firstname:"Edzard",sex:"Herr",validationEMailCode:"1", interestGroup:ig1).save()
		User daniel = new User(username:"daniel",password:"123456",email:"suepke@wi-ol.de",lastname:"Süpke",firstname:"Daniel",sex:"Herr",validationEMailCode:"1", interestGroup:ig1).save()
		//fuer Testzwecke (von Olaf erstellt)
		User uli = new User(username:"uli",password:"123456",email:"uli@olaf-roeder.de",lastname:"Roeder",firstname:"Uli",sex:"Herr",validationEMailCode:"1").save()
		User martin = new User(username:"martin",password:"123456",email:"martin@olaf-roeder.de",lastname:"Roeder",firstname:"Martin",sex:"Herr",validationEMailCode:"1").save()
		
		//some friends
		olaf.addFriend(user2)
		olaf.addFriend(user1)
		olaf.save()
		
		//dummy records for Roles
		Role roleRedak = new Role(name:"Redakteur", description:"Redakteur in einem Fachbereich", dateCreated: new Date(), lastUpdated: new Date()).save()
		Role roleAdmin = new Role(name:"Admin", description:"Adminrole").save()
		Role roleUser = new Role(name:"User", description:"Internet User").save()
		Role roleAnonym = new Role(name:"Anonym", description:"Rolle für nicht angemeldete Benutzer").save()
		
		
		// user5 = User.findByUsername("oliver")
		//user5.addToRoles(Role.findByName("Redakteur Umwelt"))
		
		burns.save()
		user.save()
		admin.save()
		barney.save()
		anonym.save()
		user1.save()
		user2.save()
		
		
		roleRedak.save()
		roleAdmin.save()
		roleUser.save()
		roleAnonym.save()
		
	}	
	
	def installRole()  {
		Role roleRedak = Role.findByName("Redakteur")
		Role roleAdmin = Role.findByName("Admin")
		Role roleUser = Role.findByName("User")
		Role roleAnonym = Role.findByName("Anonym")
		Module module		
		
		/*		module = Module.findAllWhere(name:"like '%'")
		 //admin
		 //	println ("module: " + module?.class.toString())
		 Rule.list().each { rule -> 
		 //	println ("module: " + rule?.class.toString()+ " rule: " + rule.name)
		 roleAdmin.addToRules(rule)
		 }*/
		roleAdmin.save()
		
		//roleChef
		
		
		//function
		//	println ("function: " + module?.class.toString())
		module = Module.findByName("function")
		module?.rules.each { rule -> 
			//	println ("function: " + rule?.class.toString()+ " rule: " + rule.name)
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		
		//user
		module = Module.findByName("user")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				
				break;
				case 'Action logout':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'Action login':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'Action createUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'forgotPassword':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'showUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'editPwdUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'index':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'list':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'editUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'updateUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'lastArticles':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'myRecommendation':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'validateMail':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'myFollowers':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'authenticate':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'save':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'addFriend':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'auth':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'changePwdUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'forgotPassword_SetandSendMail':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'deleteUser':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
					
				case 'show':
					roleRedak.addToRules(rule).save()
					roleUser.addToRules(rule).save()
					break;
			}
		}
		
		
		//Ab hier die Zuordnungen von den Gruppen
		
		
		//role
		module=Module.findByName("role")
		module?.rules.each { rule -> 
			/*    	roleRedak.addToRules(rule).save()
			 roleUser.addToRules(rule).save()
			 roleAnonym.addToRules(rule).save()*/
		}
		
		//module
		module = Module.findByName("module")
		module?.rules.each { rule -> 
			/*    	roleRedak.addToRules(rule).save()
			 roleUser.addToRules(rule).save()
			 roleAnonym.addToRules(rule).save()*/
		}
		
		
		
		//Infocart
		module = Module.findByName("Infocart")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				
				break;
				
				case 'list':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'listvisible':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'listpublished':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'create':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'save':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'show':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'display':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'showAsOnePage':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'edit':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'updatename':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'togglepublished':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'togglevisible':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'load':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'delete':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				
				case 'addArticle':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'deleteItem':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'toPdf':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'xml':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
			}
		}
		
		//rateable
		module = Module.findByName("rateable")
		module?.rules.each { rule ->
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		//taggable
		module = Module.findByName("taggable")
		module?.rules.each { rule ->
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		//welcome
		module = Module.findByName("welcome")
		module?.rules.each { rule ->
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		//tagcloud
		module = Module.findByName("tagcloud")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		//usercomment
		module = Module.findByName("usercomment")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				roleRedak.addToRules(rule).save()
				break;
				case 'listarticlecomments':
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'display':
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'displaysingle':
				roleUser.addToRules(rule).save()
				break;
				case 'create':
				roleUser.addToRules(rule).save()
				break;
				case 'edit':
				roleUser.addToRules(rule).save()
				break;
				case 'update':
				roleUser.addToRules(rule).save()
				break;
				case 'reply':
				roleUser.addToRules(rule).save()
				break;
				case 'save':
				roleUser.addToRules(rule).save()
				break;
				case 'delete':
				roleRedak.addToRules(rule).save()
				break;
				
			}
		}
		
		//article
		module = Module.findByName("article")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				roleRedak.addToRules(rule).save()
				break;
				case 'display':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'addTag':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'addTags':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
				case 'findByTag':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'searcharticle':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'starsrated':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'xml':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'pdf':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
			}
		}
		
		//articleIndicator
		module = Module.findByName("articleIndicator")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
		}
		
		//report
		module = Module.findByName("report")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				roleRedak.addToRules(rule).save()
				break;
				case 'display':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'sidebar':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'pdf':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'xml':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
			}
		}
		
		//category
		module = Module.findByName("category")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			/*    	roleUser.addToRules(rule).save()
			 roleAnonym.addToRules(rule).save()*/
		}
		
		//indicator
		module = Module.findByName("indicator")
		module?.rules.each { rule -> 
			switch(rule?.name){
				case 'all':
				roleRedak.addToRules(rule).save()
				break;
				case 'showIndicator':
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;				
				case 'showChart':
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;				
			}
		}
		
		
		
		//tNode
		module = Module.findByName("tNode")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		//tReport
		module = Module.findByName("tReport")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		
		
		
		//question
		module = Module.findByName("question")
		module?.rules.each { rule -> 
			switch(rule?.name){
				case 'all':
				break;
				case 'allAnswered':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'allNew':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'allTop':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'display':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'show2':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				case 'userSave':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				break;
			}
		}
		
		
		//backend
		module = Module.findByName("backend")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			/*    	roleUser.addToRules(rule).save()
			 roleAnonym.addToRules(rule).save()*/
		}
		
		//Help
		module = Module.findByName("help")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				
				break;
				
				case 'overview':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'section':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'listsections':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'createsection':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'savesection':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'editsection':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'updatesection':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'deletesection':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'article':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
				
				case 'listarticles':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'createarticle':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'savearticle':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'editarticle':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'updatearticle':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'deletearticle':
				roleRedak.addToRules(rule).save()
				break;
				
				case 'search':
				roleRedak.addToRules(rule).save()
				roleUser.addToRules(rule).save()
				roleAnonym.addToRules(rule).save()
				break;
			}
		}
		
		//media
		module = Module.findByName("media")
		module?.rules.each { rule ->
			switch(rule?.name){
				case 'all':
				break;
				
				case'Media_thumb':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_list':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_index':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_listThumbs':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_searchFilter':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_searchAJAX':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_create':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_save':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_download':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_show':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_update':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_edit':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_delete':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_actionChecked':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_updateArticles':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_addToView':
				roleRedak.addToRules(rule).save()
				break;
				case'Media_removeFromView':
				roleRedak.addToRules(rule).save()
				break;
			}
		}
		
		
		//home
		module = Module.findByName("home")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		
		//feed
		module = Module.findByName("feed")
		module?.rules.each { rule -> 
			roleRedak.addToRules(rule).save()
			roleUser.addToRules(rule).save()
			roleAnonym.addToRules(rule).save()
		}
		
		
		/*        def role4
		 role4 = Role.findByName("Chefredakteur Umwelt")
		 role4.addToRules(Rule.findByNameAndActionname("UserManagement alles", "*"))
		 role4.save()
		 role1 = Role.findByName("Redakteur Umwelt") 
		 role1.addToRules(Rule.findByName("User anzeigennn"))
		 role1.addToRules(Rule.findByName("User bearbeitennn"))*/
		
	}	
	
	def installRoleUser()  {
		Role roleRedak = Role.findByName("Redakteur")
		Role roleAdmin = Role.findByName("Admin")
		Role roleUser = Role.findByName("User")
		Role roleAnonym = Role.findByName("Anonym")
		
		//chefr.
		User burns = User.findByUsername("burns")
		//redakteur
		User user = User.findByUsername("user")
		User admin = User.findByUsername("admin")
		User barney = User.findByUsername("barney")
		User anonym = User.findByUsername("Anonym")
		User user1 = User.findByUsername("User1")
		User user2 = User.findByUsername("User2")
		User daniel = User.findByUsername("daniel")		
		
		
		
		
		User olaf = User.findByUsername("olaf")
		User edzard = User.findByUsername("edzard")
		
		burns.addToRoles(roleAnonym).save()
		burns.addToRoles(roleUser).save()
		
		user.addToRoles(roleRedak).save()
		user.addToRoles(roleAnonym).save()
		user.addToRoles(roleUser).save()
		
		user1.addToRoles(roleRedak).save()
		user1.addToRoles(roleAnonym).save()
		user1.addToRoles(roleUser).save()
		
		user2.addToRoles(roleUser).save()
		user2.addToRoles(roleAnonym).save()
		
		daniel.addToRoles(roleUser).save()
		daniel.addToRoles(roleAnonym).save()
		
		
		
		admin.addToRoles(roleAdmin).save()
		admin.addToRoles(roleRedak).save()
		admin.addToRoles(roleAnonym).save()
		admin.addToRoles(roleUser).save()
		
		olaf.addToRoles(roleUser).save()
		olaf.addToRoles(roleAnonym).save()
		olaf.addToRoles(roleUser).save()
		
		edzard.addToRoles(roleAdmin).save()
		edzard.addToRoles(roleAnonym).save()
		edzard.addToRoles(roleUser).save()
		
		barney.addToRoles(roleUser).save()
		barney.addToRoles(roleAnonym).save()
		
		anonym.addToRoles(roleAnonym).save()
		
		//fuer Testzwecke (von Olaf)
		User uli = User.findByUsername("uli")
		User martin = User.findByUsername("martin")
		uli.addToRoles(roleAdmin).save()
		uli.addToRoles(roleRedak).save()
		uli.addToRoles(roleAnonym).save()
		uli.addToRoles(roleUser).save()
		martin.addToRoles(roleUser).save()
		martin.addToRoles(roleAnonym).save()
		
		roleRedak.save()
		roleAdmin.save()
		roleUser.save()
		roleAnonym.save()
	}    
	
	def enableSecurity() {
		Module userModule = Module.findByName("user")
		userModule?.description = "an"
		
	}
	
}
