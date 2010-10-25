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
 
package systemadministration.recommender
import ReportEditorMain.ArticleEditor.*
import systemadministration.recommender.WelcomeService
import systemadministration.recommender.ArticleAccessReportingService

import systemadministration.recommender.ArticleViewedByInterestgroupService;
import systemadministration.usermanagement.User
import interactiveFeatures.Usercomments.Usercomment
import systemadministration.modulmanagement.Module

class WelcomeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def test = {
    	Article art = Article.findById(13);
    	println WelcomeService.getArticleInfos(art)
    }
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [welcomeInstanceList: Welcome.list(params), welcomeInstanceTotal: Welcome.count()]
    }

    def create = {
        def welcomeInstance = new Welcome()
        welcomeInstance.properties = params
        return [welcomeInstance: welcomeInstance]
    }

    def save = {
        def welcomeInstance = new Welcome(params)
        if (welcomeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'welcome.label', default: 'Welcome'), welcomeInstance.id])}"
            redirect(action: "show", id: welcomeInstance.id)
        }
        else {
            render(view: "create", model: [welcomeInstance: welcomeInstance])
        }
    }

    def show = {
        def welcomeInstance = Welcome.get(params.id)
        if (!welcomeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
            redirect(action: "list")
        }
        else {
            [welcomeInstance: welcomeInstance]
        }
    }

    def edit = {
        def welcomeInstance = Welcome.get(params.id)
        if (!welcomeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [welcomeInstance: welcomeInstance]
        }
    }

    def update = {
        def welcomeInstance = Welcome.get(params.id)
        if (welcomeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (welcomeInstance.version > version) {
                    
                    welcomeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'welcome.label', default: 'Welcome')] as Object[], "Another user has updated this Welcome while you were editing")
                    render(view: "edit", model: [welcomeInstance: welcomeInstance])
                    return
                }
            }
            welcomeInstance.properties = params
            if (!welcomeInstance.hasErrors() && welcomeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'welcome.label', default: 'Welcome'), welcomeInstance.id])}"
                redirect(action: "show", id: welcomeInstance.id)
            }
            else {
                render(view: "edit", model: [welcomeInstance: welcomeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def welcomeInstance = Welcome.get(params.id)
        if (welcomeInstance) {
            try {
                welcomeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'welcome.label', default: 'Welcome'), params.id])}"
            redirect(action: "list")
        }
    }
    def welcomePage = {
    	java.util.ArrayList result, result2, result3, result4, result5 , welcomeList, lastComments
    	result = null
    	result2 = null
    	result3 = null
    	result4 = null
    	result5 = null 
    	int topArticlesStart = 0
    	int lastViewedArticlesStart = 0
       	int interestgroupArticleStart = 0
    	User user = session.user
    	
    	//get last welcome
    	welcomeList = Welcome.findAll([max:3,offset:0,sort:"lastUpdated", order:"desc"])
    	
    	//last comments
    	lastComments = Usercomment.findAll([max:3,offset:0,sort:"lastUpdated", order:"desc"])
    	
        session.setAttribute("topArticlesStart", topArticlesStart)
    	session.setAttribute("lastViewedArticlesStart", lastViewedArticlesStart)
    	session.setAttribute("interestgroupArticle", interestgroupArticleStart)   	
    //	session.setAttribute("articleViewedByFriends", articleViewedByFriends)
  //	session.setAttribute("friendsArticle", friendsArticle)
    	
    	
		//top ten article
    	result = ArticleAccessReportingService.getTopTenViewedArticle(3,topArticlesStart)
		//only do if user logged in
    //	if(session.user!=User.findByUsername("Anonym").toString())
    	if(session.user.toString()!="Anonym")
		{
	    	//last view
	    	Article lastArticle = WelcomeService.getLastViewArticle(user)
			if(lastArticle!=null){

				result2 = ArticleToArticleCollaborativeFilteringService.getRecommendationsForArticleAndUser(lastArticle, session.user)

			}
	    	result3 = WelcomeService.getLastViewedArticles(session.user,3,topArticlesStart)

	    	result4 = ArticleViewedByInterestgroupService.getPopularArticlesForGroup(session.user)
	    		
	     	result5 = ArticleViewedByFriendsService.getArticleViewedByFriendsForUser(session.user)
		}
		[friendsArticle: result5, interestgroupArticle: result4, lastViewedArticles: result3, articleRecommendationList: result2, topTenArticleInstanceList: result, welcomeList: welcomeList, lastComments:lastComments]
		  
    }
    
    //Gerrit: nur zum test
    def beispiele2 ={
    	User user = session.user
    	java.util.List result
    	//get user requesting
    	
    	String modul_name="welcome"
    		String action_name = "welcomePage"
    	String hsql="select rule.actionname, rule.module,user.username"+
    	" from User as user join user.roles as role join role.rules as rule  " +
    	"  where user.id = ? and rule.module.name = ? and (rule.actionname = ? or rule.actionname = ?) " 

    	result = User.executeQuery(hsql, [user.id, modul_name, action_name, '*'])
    	
    	if (result.size()>0){
    		render("CheckAccess JA: result: "+result+ " modul_name: "+modul_name+ " action_name: "+action_name+ " user: "+modul_name)
    		return true
    	}
    	else
    	{
    		render("CheckAccess nein: result: "+result+ " modul_name: "+modul_name+ " action_name: "+action_name+ " user: "+modul_name)
    		return false
    	}
    }
    
    //Gerrit: nur ein Test
    //Oliver: kann der dann jetzt wieder raus`?
    def welcomePage2 = {
    	java.util.ArrayList result, result2, result3
    	result = null
    	result2 = null
    	result3 = null
    	int topArticlesStart = 0
    	int lastViewedArticlesStart = 0
    	User user = session.user
    	
    	
        session.setAttribute("topArticlesStart", topArticlesStart)
    	session.setAttribute("lastViewedArticlesStart", lastViewedArticlesStart)
    	
		//top ten article
    	result = ArticleAccessReportingService.getTopTenViewedArticle(3,topArticlesStart)
		//only do if user loged in
    //	if(session.user!=User.findByUsername("Anonym").toString())
    	if(session.user.toString()!="Anonym")
		{
	    	//last view
	    	Article lastArticle = WelcomeService.getLastViewArticle(user)
			if(lastArticle!=null){

				
				result2 = ArticleToArticleCollaborativeFilteringService.getRecommendationsForArticleAndUser(lastArticle, session.user)

			}
	    	result3 = WelcomeService.getLastViewedArticles(session.user,3,topArticlesStart)
		}
		[lastViewedArticles: result3, articleRecommendationList: result2, topTenArticleInstanceList: result]
		  
    }

    def topArticles = {
    	java.util.ArrayList result
    	int topArticlesStart
   
    	topArticlesStart=session.getAttribute("topArticlesStart")+1
    	
    	if(topArticlesStart>5)
    		session.setAttribute("topArticlesStart", 0)
    	else
    		session.setAttribute("topArticlesStart", topArticlesStart)
    	result = null
		//top ten article
    	result = ArticleAccessReportingService.getTopTenViewedArticle(3,topArticlesStart)
    	render(template:"topArticles", model:[topTenArticleInstanceList: result, topArticlesStart:topArticlesStart ])
		  
    }


    def lastViewedArticles = {
    	java.util.ArrayList result
    	result = null
    	int lastViewedArticlesStart
    	
    	lastViewedArticlesStart=session.getAttribute("lastViewedArticlesStart")+1
    	
    	if(lastViewedArticlesStart>5)
    		session.setAttribute("lastViewedArticlesStart", 0)
    	else
    		session.setAttribute("lastViewedArticlesStart", lastViewedArticlesStart)
    	
    	if(session.user!=User.findByUsername("Anonym"))
		{
	    	result = WelcomeService.getLastViewedArticles(session.user,3,lastViewedArticlesStart)
		}
    	render(template:"lastViewedArticles", model:[lastViewedArticles: result])
		  
    }
    
    
    def lastComments = {
    	java.util.ArrayList lastComments
    	

    	//last comments
    	lastComments = Usercomment.findAll([max:3,offset:0,sort:"lastUpdated", order:"desc"])
    	println ("lastComments von Update: " + lastComments)
    	
    	render(template:"lastComments", model:[lastComments: lastComments])
		  
    }


    def articleRecommendation = {
    	java.util.ArrayList result
    	result = null
    	if(session.user!=User.findByUsername("Anonym"))
		{
	    	//last view
	    	Article lastArticle = WelcomeService.getLastViewArticle(user)
			if(lastArticle!=null){

				result = ArticleToArticleCollaborativeFilteringService.getRecommendationsForArticleAndUser(lastArticle, session.user)
			}
		}
    	render(template:"recommendationArticles", model:[articleRecommendationList: result])
		  
    }

}
