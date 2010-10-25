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


import systemadministration.modulmanagement.Module
import systemadministration.usermanagement.*
import java.text.DateFormat
import ReportEditorMain.ArticleEditor.Article;
import java.util.ArrayList;
import java.text.DateFormat
import ReportEditorMain.ArticleEditor.Article;

class ArticleViewedByFriendsService {

    	
	static updateArticleViewedByFriends(){

		Module module = Module.getModule("articleToArticleCollaborativeFiltering")
		
		Long datevalue
		datevalue = Long.parseLong(module?.getConfig('lastRun'))
		Date currentTimestamp = new Date()
		DateFormat df = DateFormat.getDateInstance()
		Date lastRun = new Date(datevalue)
		module?.setConfig('lastRun', currentTimestamp.getTime().toString())	
		
  		java.util.List list = ArticleViewedByFriends.executeQuery("select accessLog.article, accessLog.user from AccessLog as accessLog")
  		//user = list[xx][1]
    	//article = list[xx][0]
  		User user
    	Article article
    	InterestGroup group
    	ArticleViewedByFriends goal
    	int indexOfFirstItemInList = 0;
    	for (int xx = indexOfFirstItemInList;  xx < list.size(); xx++ ){
    		
    		user =  list[xx][1]
    		article = list[xx][0]
    	
    		if("Anonym" == user.username)
    			continue;
    		goal = ArticleViewedByFriends.findByFriendAndArticle(user, article)
			
    		if(goal!=null){
    			goal.counter = goal.counter + 1
			}
			else{
				goal = new ArticleViewedByFriends(friend:user, article:article, counter:1)
			}
    		goal.save()

    	}
		
	}
	static ArrayList<Article> getArticleViewedByFriendsForUser(User user){
		
    	ArrayList<Article> goal_article = new ArrayList()
    	ArrayList<Integer> goal_int = new ArrayList()            
 
    	User userInstance = User.findById(user.id)
		userInstance.friends.each { friend ->


			ArrayList<ArticleViewedByFriends> list
			list = ArticleViewedByFriends.findAllByFriend(friend)
		
			// list.article
			// list.counter
			list.each { listelement ->

				Boolean exist = false;
				
				if(goal_article == null){
					goal_article.add(0, listelement.article) 
					goal_int.add(0, listelement.counter)
				}
				else{	
					for(int ii = 0 ; ii < goal_article.size(); ii++){
						if(goal_article.get(ii) == listelement.article){

							goal_int.set(ii, (goal_int.get(ii) + listelement.counter))
							exist = true
						}
					}
					if(!exist){
						int ll = goal_article.size() // + 1
						goal_article.add(ll, listelement.article)
						goal_int.add(ll , listelement.counter)
						exist = true;
					}
				}
			}
    	}

    	ArrayList<Article> sorted_list  = sortList(goal_article, goal_int)

    	return sorted_list 
	}
	
static ArrayList<Article> sortList(ArrayList<Article> goal_article, ArrayList<Integer> goal_int){

		if( goal_article == null || goal_int == null)
			return null
  	
    	ArrayList<Article> goal_sorted = new ArrayList()
    	ArrayList<Integer> save_int_list = new ArrayList()
    	
		
    	int save_value, save_pos;
    	while( ! goal_int.isEmpty() ){
    		
    		save_value = goal_int.get(0)
    		save_pos = 0
    		
    		for(int ii = 1 ;  ii < goal_int.size(); ii++ ){
    			if(save_value < goal_int.get(ii)){
    				save_value = goal_int.get(ii)
    				save_pos = ii
    			}	
    		}	
    		goal_int.remove(save_pos)
    		save_int_list.add(save_value)
    		goal_sorted.add(goal_article.get(save_pos))
    		goal_article.remove(save_pos) 
    		   		
    	}	
    	
    	if (goal_sorted.isEmpty())
    		return null
    	return goal_sorted
	}
	
	
	boolean transactional = true

    def serviceMethod() {

    }
}
