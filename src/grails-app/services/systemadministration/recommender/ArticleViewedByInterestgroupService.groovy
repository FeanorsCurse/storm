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

import systemadministration.usermanagement.*
import systemadministration.modulmanagement.*
import ReportEditorMain.ArticleEditor.Article
class ArticleViewedByInterestgroupService {

    boolean transactional = true

    static updateArticleViewedByInterestgroup(){
    	
    	java.util.List list = ArticleViewedByInterestgroup.executeQuery("select accessLog.article, accessLog.user from AccessLog as accessLog" )
    	//user = list[xx][1]
    	//article = list[xx][0]
    	User user
    	Article article
    	InterestGroup group
    	ArticleViewedByInterestgroup goal
    	int indexOfFirstItemInList = 0;
    	for (int xx = indexOfFirstItemInList;  xx < list.size(); xx++ ){
    		
    		user =  list[xx][1]
    		article = list[xx][0]
    		group = user.interestGroup
    		
    		goal = ArticleViewedByInterestgroup.findByGroupAndArticle(group, article)
			
    		if(goal!=null){
    			goal.counter = goal.counter + 1
			}
			else{
				goal = new ArticleViewedByInterestgroup(article:article, group:group, counter:1)
			}
    		goal.save()
    		
    	}
    	
    	Module module = Module.getModule("articleViewedByInterestgroup")
    	Date currentTimestamp = new Date()
    	module?.setConfig('lastRun', currentTimestamp.getTime().toString())	
    	
    }
    
    
    static ArrayList<Article> getPopularArticlesForGroup(User user){
    	InterestGroup group 
    	

    	User userInstance = User.findByUsername(user.username)
    	group = userInstance.interestGroup
    	//ist stets null, auch wenn IG gesetzt
    

    	
    	//    	TODO: nur die ersten 10 wiedergeben
      	if (group == null){
      		return null
      	}
    	
    	String hsql = "select AVBIG.article from ArticleViewedByInterestgroup as AVBIG " +
    			" where AVBIG.group = ? " +
			    " order by AVBIG.counter desc "		
   
    	java.util.ArrayList result = ArticleViewedByInterestgroup.executeQuery(hsql,group)
    
    	if (result.isEmpty())
    		return null
    	return result

    	
    }
    
    def serviceMethod() {

    }
}
