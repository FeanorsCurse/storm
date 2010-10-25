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
 * Service for the ReportController
 * 
 * @author Irene, Gerrit
 * @since 11.06.2010
 */
package ReportEditorMain.ReportEditor

import java.util.ArrayList

import shemaEditor.indicatorAdministration.Indicator
import shemaEditor.shemaAdministration.TNode
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.Enum.ArticleStatus
import systemadministration.usermanagement.User

/**
 * Services for the Report-Controller
 * 
 * @author: Irene, Gerrit
 */
class ReportService {
	
	//define as transactional
	boolean transactional = true
	
	//dummy Service Method
	def serviceMethod() {
		
	}
	/**
	 * This function checks, whether all articles to a specific report are approved, in order to approve the whole report
	 * 
	 * @params reportInstance: the report to be checked
	 * @return true, if all articles for this report have the status "approved"
	 */
	def boolean isReportApproveable(Report reportInstance){
		//check status for each article belonging to this report
		for (Article article : listArticlesNotRevision(reportInstance)) {
			if(article.status.status!="Approved"){
				//return false if only one article has not the status "approved"
				return false;
			}
		}
		//return true if all articles have the status "approved"
		return true;
	}
	
	/**
	 * This Function lists all Articles that have not the Status "Revision"
	 * 
	 * @params reportInstance: the report to be checked
	 * @return list of Articles
	 */
	def ArrayList<Article> listArticlesNotRevision(Report reportInstance){
		ArrayList<Article> articleList =  listArticles(reportInstance);
		ArrayList<Article> articleListNotRevision =  new ArrayList<Article>();
		
		for (Article article : articleList) {
			if(article.status.status!="Revision"&&article.status.status!="Closed"){
				articleListNotRevision.add(article)
			}
		}
		
		return articleListNotRevision
	}
	
	/**
	 * This Service lists all articles belonging to a specific report
	 * 
	 * @params reportInstance: the report to find the articles for
	 * @return list of all articles belonging to this report
	 */
	def ArrayList<Article> listArticles(Report reportInstance){
		//Root article for this report
		Article rootArticle = reportInstance.rootArticle
		
		//create empty List for articles
		ArrayList<Article> articleList = new ArrayList<Article>()
		
		//Get the Children
		return listArticlesIntern(rootArticle, articleList )
	}
	
	/**
	 * internal function for listArticles() with recursive invokation
	 */
	def ArrayList<Article> listArticlesIntern(Article parentArticle, ArrayList<Article> articleList ){
		//get child-article-list
		ArrayList<Article> list = Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"])
		
		for (Article article : list) {
			if(article!=parentArticle){
				//add articles to list
				articleList.add(article);
				
				//recursive method invocation
				listArticlesIntern(article, articleList)
			}
		}
		
		//return the list after recursive method invocation
		return articleList
	}
	
	/**
	 * This function imports all nodes under the specific tnode under the specific article
	 */
	def importNode(TNode node, Article parent, User user){
		def newParent = importNodeIntern(node, parent, user)
		
		for (Iterator iterator = node.children.iterator(); iterator.hasNext();) {
			TNode childNode = (TNode) iterator.next();
			importNode(childNode, newParent, user)
		}
	}
	
	/**
	 * imports the TNode into the Report as new Article. Called from importNode
	 * 
	 * @param node: imported node
	 * @param parent: parent article
	 * @return new parent article
	 */
	private Article importNodeIntern(TNode node, Article parent, user){
		//create Article Status
		def status = ArticleStatus.findByStatus("New");
		
		//create new article from tnode
		def article = new Article(name:node.name,content:"", status:status, author:user, parentArticle:parent, tnode:node).save()
		
		//add indicators for this article
		System.out.println("Import Indicators: ");
		for (Indicator indicator : node.listIndicators()) {
			System.out.println("Indicator: "+indicator);
			new ArtInd(article:article,indicator:indicator, author:user).save()
		}
		return article
	}
}
