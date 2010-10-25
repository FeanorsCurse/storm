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
 
/** This User Controller includes all methods and the whole user functionality.
 **  @author  Oliver Norkus
 */

package systemadministration.usermanagement

import systemadministration.log.LogService;
import ReportEditorMain.ArticleEditor.*
import systemadministration.recommender.WelcomeService
import systemadministration.recommender.ArticleAccessReportingService
import systemadministration.usermanagement.User
import systemadministration.usermanagement.Role
import systemadministration.recommender.ArticleToArticleCollaborativeFilteringService
import interactiveFeatures.Usercomments.Usercomment


class UserController {
	
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	
	def authenticate = {
		User user = User.findByUsernameAndPassword(params.username,params.password.encodeAsSHA())
		//check if user has been deleted via softdelete
		if((user)&&(user.isDeleted==false)){

			//check if email has been validated
			if(user.getValidationEMailCode()=="1")
			{
				session.user = user
		    	//set the current Controller, Action and all Params
		    	session.setAttribute("previousController", params.controller)
		    	session.setAttribute("previousAction", params.action)
		    	session.setAttribute("previousParams", params)
				//TODO Logging
				LogService.writeSecurityLog session.user.toString(), 2, 'IP'
				flash.info = message(code:"user.helloUser", args:[user.username])
				redirect(controller:"welcome", action:"welcomePage")
			}
			else {
				
					//TODO Logging
					LogService.writeSecurityLog params.username.toString(), 4, 'IP'
					flash.message = message(code:"user.validateFirst", args:[user.username, user.email])
					redirect(controller:"welcome", action:"welcomePage")
				
			}
		}else{
			if(params.username != null){
				//TODO Logging
				LogService.writeSecurityLog params.username.toString(), 3, 'IP'
			}
			flash.message =message(code:"user.wrongPass")
			redirect(controller:"welcome", action:"welcomePage")
		}
	}
	
	
	

	
	
	def debug(){
		println "DEBUG: ${actionUri} called."
		println "DEBUG: ${params}"
		
	}
	
	
	
	def logout = {
		//TODO Logging
		LogService.writeSecurityLog session.user.username.toString(), 3, 'IP'
		flash.info =  message(code:"user.byeUser", args:[session.user.username])
		session.user = null
		redirect(controller:"welcome", action:"welcomePage")
	}
	
	def login = {
	}
	
	
	def index = {
		redirect(action: "list", params: params)
	}
	
	def list = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[userInstanceList: User.list(params), userInstanceTotal: User.count()]
	}
	
	def create = {
		def userInstance = new User()
		userInstance.properties = params
		return [userInstance: userInstance]
	}
	
	def save = {
		User userInstance = new User(params)
		if (userInstance.save(flush: true)) {
			//send email with validationString
			
			if (userInstance.getValidationEMailCode()=="0")
			{
				//create a random number
				java.util.Random random = new Random()
				
				userInstance.setValidationEMailCode((random.nextInt().toString()))
				
				sendMail {
					to userInstance.getEmail()
					subject message(code:"user.mail.validate.subject")
					body message(code:"user.mail.validate.body", args:[userInstance.getLastname(), userInstance.id,userInstance.getValidationEMailCode(),userInstance.getUsername()])
				}
			}
			//TODD vielleicht dynamisch machen was passiert wenn es keine Grupep User gibt
			userInstance.addToRoles(Role.findByName("User")).save()
			flash.info = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
				
			redirect(controller:"welcome", action:"welcomePage")
		}
		else {
			render(view: "create", model: [userInstance: userInstance])
		}
	}
	
	def createUser = {
		def userInstance = new User()
		userInstance.properties = params
		return [userInstance: userInstance]
	}
	
	def forgotPassword = {
			
			
	}
	
	def forgotPassword_SetandSendMail = {
		def username = params.username
		def mail = params.email
		
		def userInstance = User.findByUsernameAndEmail(username, mail)
		
		if(!userInstance){
			flash.message = message(code:"user.notfound")
			redirect(action: "forgotPassword")
		}
		else
		{
			//TODO newPasswort as random String
			def newPassword = random.nextInt().toString()
			userInstance.setPassword newPassword.encodeAsSHA()
			sendMail {
				to userInstance.getEmail()
				subject  message(code:"user.mail.newpass")
				body message(code:"user.mail.body.newpass", args:[userInstance.getLastname(),newPassword])

			}
			flash.message = "${message(code: 'user.mail.pass')}"
			redirect(action: "forgotPassword")
		}
			
	}
	
	def changePwd = {
		def newPwd1 = params.newpassword1
		def newPwd2 = params.newpassword2
		
		def userInstance = User.get(params.id)
		
		if(!userInstance){
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else{
			if(newPwd1==newPwd2){
				userInstance.setPassword newPwd1.encodeAsSHA()
				flash.message = message(code:"user.passChanged")
				redirect(action: "list")
			}
			else {
				flash.message = "${message(code: 'user.error.passwordsMissmatch')}"
				render(view: "editPwdUser", model: [userInstance: userInstance])
			}
		}
	}
	def changePwdUser = {
		def oldPwd = params.oldpassword
		
		def newPwd1 = params.newpassword1
		def newPwd2 = params.newpassword2
		
		def userInstance = User.get(session.user.id)
		
		if(!userInstance){
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		} 		
		else{
			if(oldPwd.encodeAsSHA() != userInstance.getPassword() ) {
				flash.message = message(code:"user.passnotcorrect")
				render(view: "editPwdUser", model: [userInstance: userInstance])
			}
			else{
				if(newPwd1==newPwd2){
					userInstance.setPassword newPwd1.encodeAsSHA()
					flash.message = message(code:"user.passChanged")
					render(view: "editUser", model: [userInstance: userInstance])
				}
				else {
					flash.message = "${message(code: 'user.error.passwordsMissmatch')}"
					render(view: "editPwdUser", model: [userInstance: userInstance])
				}
			}
		}
	}
	
	def show = {
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			[userInstance: userInstance]
		}
	}
	
	def showUser = {
			def userInstance = User.get(params.id)
			if (!userInstance) {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
				redirect(action: "welcomePage")
			}
			else {
				[userInstance: userInstance]
			}
		}
	def edit = {
		
			def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
				redirect(action: "list")
		}
		else {
			render(view: "edit", model: [userInstance: userInstance])
		}
	}
	
	def editPwd = {
		def userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [userInstance: userInstance]
		}
	}
	
	def editPwdUser = {
		User userInstance = session.user
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
		else {

			render(view: "editPwdUser", model: [userInstance: userInstance])
		}
	}
	
	def update = {
		def userInstance = User.get(params.id)
		if (userInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (userInstance.version > version) {
					
					userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
					render(view: "edit", model: [userInstance: userInstance])
					return
				}
			}
			userInstance.properties = params
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
				flash.info = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
				redirect(action: "show", id: userInstance.id)
			}
			else {
				render(view: "edit", model: [userInstance: userInstance])
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
	}
	
	
	def editUser = {
		def userInstance = User.get(session.user.id)
		if (!userInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			
			redirect(action: "editUser", id: session.user.id)
		}
		if (session.user.id!=userInstance.id) {
			flash.message = "${message(code: 'user.error.wrongID', args: [message(code: 'user.label', default: 'User' ), params.id,session.user.id])}"
			
			redirect(action: "editUser", id: session.user.id)
		}
		else {
			return [userInstance: userInstance]
		}
	}
	
	def updateUser = {
		
		def userInstance =User.get(session.user.id)
		if (userInstance) {
			if (params.version) {
				def version = params.version.toLong()
				if (userInstance.version > version) {
					
					userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
					redirect(action: "editUser", id: userInstance.id)
					return
				}
			}
			userInstance.properties = params
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
				flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
				redirect(action: "showUser", id: userInstance.id)
			}
			else {
				redirect(action: "editUser", id: userInstance.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "welcomePage")
		}
	}
	
	
	def delete = {
		def userInstance = User.get(params.id)
		
		boolean deleteMyself = false
		
		if (userInstance?.id==session.user?.id )
		{
			deleteMyself=true
		}
		
		if (userInstance) {
			
			if(deleteMyself)
			{
				session.user = null
			}
			userInstance.isDeleted=true
			
			if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
						flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
						
				if(deleteMyself)
				{
					redirect(controller:"welcome", action:"welcomePage")
				}
				else
				{
					redirect(action:"list")
				}
			}
			else {
				flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
                redirect(action: "show", id: params.id)
			}
		}
		else {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
			redirect(action: "list")
		}
	}
	
	
	def deleteUser = {
			User userInstance = User.get(session.user.id)
			
			
			
			boolean deleteMyself = false
			

			
			if (userInstance) {
				

				session.user = null

				userInstance.isDeleted=true
				
				if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
							flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
							

						redirect(controller:"welcome", action:"welcomePage")

				}
				else {
					flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
					redirect(controller:"welcome", action:"welcomePage")
				}
			}
			else {
				flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
				redirect(controller:"welcome", action:"welcomePage")
			}
		}
	
	
	
	def lastArticles = {
		java.util.ArrayList lastArticles
		lastArticles = null
		
		lastArticles = WelcomeService.getLastViewedArticles(session.user,10,0)
		
		[lastViewedArticles: lastArticles]
	}
	
	
	def myRecommendation = {
		java.util.ArrayList topTenArticleInstanceList, articleRecommendationList
		topTenArticleInstanceList = null
		articleRecommendationList = null
		int topArticlesStart = 0
		int lastViewedArticlesStart = 0
		User user = session.user
		
		
		session.setAttribute("topArticlesStart", topArticlesStart)
		session.setAttribute("lastViewedArticlesStart", lastViewedArticlesStart)
		
		//top ten article
		topTenArticleInstanceList = ArticleAccessReportingService.getTopTenViewedArticle(3,topArticlesStart)
		//only do if user loged in
		//	if(session.user!=User.findByUsername("Anonym").toString())
		if(session.user.toString()!="Anonym")
		{
			//last view
			Article lastArticle = WelcomeService.getLastViewArticle(user)
			if(lastArticle!=null){
				
				
				articleRecommendationList = ArticleToArticleCollaborativeFilteringService.getRecommendationsForArticleAndUser(lastArticle, session.user)
			}
		}
		[articleRecommendationList: articleRecommendationList, topTenArticleInstanceList: topTenArticleInstanceList]
	}
	
	
	def deleteRole = {
		//brauchen wir auch eine Tbl User_Role wie SE für Indi_Node?
		
	}
	
	
	
	
	def addIndicator = {
		//brauchen wir auch eine Tbl User_Role wie SE für Indi_Node?
	}
	
	
	def validateMail = {
		
		User userInstance = User.get(params.id)
		if (!userInstance) {
			flash.message =message(code:"user.notfound")
			redirect(controller:"welcome", action:"welcomePage")
		}
		else {
			if(params.validationEMailCode==userInstance.getValidationEMailCode()) {
				userInstance.setValidationEMailCode("1")
				
				userInstance.save()
				flash.info = message(code:"user.validateTrue", args:[userInstance.getUsername()])
					
					redirect(controller:"welcome", action:"welcomePage")
			}
			else {
			flash.message =message(code:"user.validateFalse")
			redirect(controller:"welcome", action:"welcomePage")
			}
		}
		
		
	}
	
	def myFollowers ={
			User userInstance = session.user
			java.util.ArrayList allFriends
			String hsqlFriends="select  friend from User as user join user.friends as friend" +
			"  where user.id = ?"

			allFriends = User.executeQuery(hsqlFriends, [userInstance.id])

			
			java.util.ArrayList allFriendsContent = new ArrayList(allFriends.size())
			
			for (int ii =0; ii<allFriends.size();ii++)
			{
				
				allFriendsContent[ii] = new ArrayList(3)
				allFriendsContent[ii][0] = allFriends[ii]
              allFriendsContent[ii][1]= Article.findAllByAuthor(allFriends[ii],[max:3, 
				                                                   offset:0, 
				                                                   sort:"dateCreated",
				                                                   order:"desc"])

               allFriendsContent[ii][2]= Usercomment.findAllByCommentator(allFriends[ii],[max:3, 
				                                                   offset:0, 
				                                                   sort:"dateCreated",
				                                                   order:"desc"])
				
			}
			
			
			[ allFriends: allFriends, allFriendsContent: allFriendsContent]
			
	}
	
	def addFriend = {
			
			def userInstance = User.get(params.id)
			if (!userInstance) {
				flash.message = message(code:"user.notfound", args:[user.username])
				redirect(controller:"welcome", action:"welcomePage")
			}
			else {
				User friend = User.get(params.friendID)
				if(!friend)
				{
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
						redirect(controller:"welcome", action:"welcomePage")
				}
				
					if (userInstance.addFriend(friend)) {
						flash.message = "${message(code: 'user.addFriend.message', args: [message(code: 'user.label', default: 'User'), friend.username.toString()])}"
						redirect(controller: "user", action: "myFollowers")
					}
					else {
						flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
							redirect(controller:"welcome", action:"welcomePage")
					}
			}
	    	
	    }
		
	def deleteFriend = {
			
			def userInstance = User.get(params.id)
			if (!userInstance) {
				flash.message = message(code:"user.notfound", args:[user.username])
				redirect(controller:"welcome", action:"welcomePage")
			}
			else {
				User friend = User.get(params.friendID)
				if(!friend)
				{
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
						redirect(controller:"welcome", action:"welcomePage")
				}

				
				
				if (userInstance.removeFromFriends(friend)) {
					flash.message = "${message(code: 'user.deleteFriend.message', args: [message(code: 'user.label', default: 'User'), friend.username.toString()])}"
					redirect(controller: "user", action: "myFollowers")
				}
				else {
					flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
						redirect(controller:"welcome", action:"welcomePage")
				}
			}
	    	
	    }
		
	
}
