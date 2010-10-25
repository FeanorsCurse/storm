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
import systemadministration.usermanagement.User
import java.text.DateFormat
import ReportEditorMain.ArticleEditor.Article;
import java.util.ArrayList;


class ArticleToArticleCollaborativeFilteringService {

	static ArrayList<Article> getRecommendationsForArticleAndUser(Article base, User user){

		//get all Recommendations for this Article indepentend of user
		ArrayList<Article> alAllArticle = new ArrayList<Article>();
		alAllArticle = getRecommendationsForArticle(base)

		//get all Article seen by this user
		ArrayList<Article> alUserArticle = new ArrayList<Article>();
		alUserArticle = getArticleByUser(user)

		if(alUserArticle==null){
			return alAllArticle
		}
		else{
			//Substract alUserArticle from alAllArticle
			ArrayList<Article> res
			res = ArticlesetMinusArticleset(alAllArticle, alUserArticle)
			return res
		}
		
	//E	
	}
	
	// return aa - bb
	static ArrayList<Article> ArticlesetMinusArticleset(ArrayList<Article> aa, ArrayList<Article> bb){
		for (int ii= 0 ; ii < bb.size(); ii++)
			if(aa.contains(bb.get(ii)))
				aa.remove(bb.get(ii))
		
		return aa
	}
	
	static ArrayList<Article> getArticleByUser(User user){
		ArrayList<Article> alUserArticle = new ArrayList<Article>();
		
		if ( user.username == "Anonym")
			return null;
		
		java.util.List list = ArticleToArticleCollaborativeFiltering.executeQuery("" +
				" select aL.article from AccessLog as aL " +
				" where aL.user = ? ", user )
		
		for ( int ii = 0 ; ii < list.size(); ii++)
			alUserArticle.add list[ii]		                     
		
		return alUserArticle	                      			
	}
	
	
	static ArrayList<Article> getRecommendationsForArticle(Article base){
		
		ArrayList<Article> alArticle = new ArrayList<Article>();
		
		//TODO Optimieren
		//TODO Top Item-To-Item filtern -> order by counter, max:XX ...
		java.util.List list1 = ArticleToArticleCollaborativeFiltering.executeQuery("" +
				" select a2a.articleA from ArticleToArticleCollaborativeFiltering as a2a" +
				" where a2a.articleB = ? ", base )
		
		java.util.List list2 = ArticleToArticleCollaborativeFiltering.executeQuery("" +
				" select a2a.articleB from ArticleToArticleCollaborativeFiltering as a2a" +
				" where a2a.articleA = ? ", base )
		
		for( int ii=0; ii<list1.size(); ii++)
			alArticle.add list1[ii]
		for( int jj=0; jj<list2.size(); jj++)
			alArticle.add list2[jj]
			                   
		return alArticle; 
		
	}
	
	
	static boolean updateArticleToArticleCollaborativeFiltering()
	{
	
		ArticleToArticleCollaborativeFiltering article2article
		
		
		Long datevalue
		User customerC
		Article articleA, aa
		Article articleB, bb
		Module module = Module.getModule("articleToArticleCollaborativeFiltering")
		
	
		datevalue = Long.parseLong(module?.getConfig('lastRun'))
		Date currentTimestamp = new Date()
		DateFormat df = DateFormat.getDateInstance()
		Date lastRun = new Date(datevalue)
		module?.setConfig('lastRun', currentTimestamp.getTime().toString())	
		//Get the set of article-logs which were generated since the last run of this update

		// TODO: Löschen des bisherigen Contents
		//ArticleToArticleCollaborativeFiltering.executeQuery("delete from  ArticleToArticleCollaborativeFiltering")
		
		java.util.List list = ArticleToArticleCollaborativeFiltering.executeQuery("select accessLog.article, accessLog.user from AccessLog as accessLog" )
		
		
				
		int indexOfFirstItemInList = 0;
		
		//for each article A in list
		for (int kk = indexOfFirstItemInList;  kk < list.size(); kk++ ){
			
			customerC = list[kk][1]
			articleA = list[kk][0]
			                    
			//for each customer C who purchased A
			for(int jj=indexOfFirstItemInList; jj<list.size() ;  jj++){
			
				if(jj==kk || list[jj][0]==null)
					continue
				
				articleB = list[jj][0]
		
				
			//for each article B purchased by C
				if(customerC == list[jj][1]){
									
					if (articleA.id == articleB.id)
						continue;
					
					//article with lower Id becomes aa
					if(articleA.id < articleB.id){
						aa = articleA
						bb = articleB
					}
					else{
						bb = articleA
						aa = articleB
					}
							
				//record that a customer purchased A and B
					
					//get object of combination of aa and bb
					article2article = ArticleToArticleCollaborativeFiltering.findByArticleAAndArticleB(aa, bb)
					
					//check article2article-combination is exist
					if(article2article!=null){
						article2article.counter = article2article.counter + 1
					}
					else{
						article2article = new ArticleToArticleCollaborativeFiltering(articleA:aa, articleB:bb, counter:1)
					}
					article2article.save()		
				}	
			}
			//for articleA work done 
			list[kk][0] = null
			
		}
    	
	}
}
