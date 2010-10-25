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
 
package systemadministration.recommender;
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArticleService;
import ReportEditorMain.ReportEditor.Report
import systemadministration.usermanagement.User;
import storm.generalService;
import systemadministration.log.AccessLog



class WelcomeService{
	
	public static Article getLastViewArticle(User user){
		ArrayList<AccessLog> all
		if (AccessLog.findByUser(user) == null )	
			return null
		all = AccessLog.findByUser(user).list(sort:"dateCreated", order:"desc")
		if (all == null)
			return null
		return all[0].article
	}
	
	public static ArrayList<Article> getLastViewedArticles(User user,int max, int offset){
		 String hsql2 = "select aL.article from AccessLog as aL " +
	 		"where aL.user = :user group by aL.article order by aL.dateCreated desc"
	 //java.util.List lastArticle = AccessArticleTyp.executeQuery(hsql2)
	 java.util.List lastArticles = AccessArticleTyp.executeQuery(hsql2, [user:user, max:max, offset:offset] )
	 //TODO null abfangen, also user hat noch keine article angesehen
	 if (lastArticles==null)
		 return null
	 return lastArticles	
		
	}
	
	
	public static String getArticleInfos(Article a){

		
		String result
		String articlename
		articlename = a.name
		//TODO HTML encode
		articlename = generalService.encodeAsText(articlename)
		
		String articlecontent 
		articlecontent = a.content
		//TODO HTML encode
		articlecontent = generalService.encodeAsText(articlecontent)
			
		String articlereport
		ArticleService artService = new ArticleService();
		Report report
		report = artService?.getReport(a)
		//sometimes problems with Root aritcle because of that we have to check for null
		if (report.name==null)
		{
			articlereport ='null'
		}
		else
		{
			articlereport  = report.name;
		}
		
		//TODO HTML encode
		articlereport = generalService.encodeAsText(articlereport)
		
		
		//new HTML format
		articlename = "<b>" + articlename + "</b>"
		articlereport = "<FONT COLOR=\"#6b6b6b\" size=\"2\"> "+"von: " +"aus: " +articlereport + " </FONT>"
		
		int art_l = articlecontent.length()
		art_l = art_l / 3;
		if( art_l > 100)
			art_l = 100
			
		result = articlename + " " + articlecontent.substring(0, art_l) + " ... <br>&nbsp;&nbsp;&nbsp;&nbsp;" +  articlereport

		return result ;
	}
	
}
