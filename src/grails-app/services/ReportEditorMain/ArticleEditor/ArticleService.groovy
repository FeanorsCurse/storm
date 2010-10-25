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
 * Service for the ArticleController
 * 
 * @author Irene, Gerrit
 * @since 11.06.2010
 */
package ReportEditorMain.ArticleEditor

import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.XmlUtil;

import java.util.ArrayList
import java.util.regex.Matcher 
import java.util.regex.Pattern

import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ArticleEditor.ArtInd
import ReportEditorMain.ReportEditor.Report
import shemaEditor.indicatorAdministration.Indicator
import shemaEditor.shemaAdministration.TNode
import ReportEditorMain.Enum.ArticleStatus
import systemadministration.usermanagement.User

/**
 * Service class for the Article-Controller
 * 
 * @author: Irene, Gerrit
 */
class ArticleService {
	
	//define as transactional
	boolean transactional = true
	
	/**
	 * Copies all Indicator-Associations for oldArticle to newArticle
	 *
	 * @param oldArticle: old Article
	 * @param newArticle: new Article
	 */
	public void copyIndicators(Article oldArticle, Article newArticle, User user){
		//find all Indicators for oldArticle
		for (ArtInd artInd : ArtInd.findAllByArticle(oldArticle)) {
			new ArtInd(article:newArticle,indicator:artInd.indicator,author:user,value:artInd.value).save()
		}
	}
	
	/**
	 * Finds the report for this articleInstance
	 * 
	 * @param articleInstance: article to find report for
	 * @return: Report where the param-article belongs to, null if no Report found
	 */
	public Report getReport(Article articleInstance){
		ArrayList list = getParent(new ArrayList(),articleInstance);
		for (Object object : list) {
			if(object instanceof Report){
				return (Report) object
			}
		}
		return null
	}
	
	/**
	 * Creates a list of all parent articles for the specified article
	 *
	 * @params 	list: ArticleList
	 * 			article: Starting Point for building the Parent Article-List
	 * @return: List of all Parent Articles and the Report
	 */
	def ArrayList getParent(ArrayList list, Article article){
		if(article.name.equals("Root")){
			Report report = Report.findByRootArticle(article)
			list.add(report)
		}
		else if(article.getParentArticle()!=null){
			if(article.getParentArticle().name.equals("Root")){
				Report report = Report.findByRootArticle(article.getParentArticle())
				list.add(report)
			}else{
				list.add(article.getParentArticle());
				getParent(list,article.getParentArticle());
			}
		}
		return list
	}
	
	/**
	 * Creates a List of all parent and parent-sibling articles for specified article
	 * 
	 * @param articleInstance: article to search for
	 * @return: List of all parent and parent-sibling articles for specified article
	 */
	def ArrayList getParentAndSiblingArticles(Article articleInstance){
		ArrayList list = getParent(new ArrayList(),articleInstance);
		ArrayList returnList = new ArrayList();
		for (Object object : list) {
			if(object instanceof Article){
				Article article = (Article) object
				returnList.add(article)
				returnList.addAll(getSiblingArticles(article))
			}
		}
		return returnList
	}
	
	/**
	 * Creates a list of first children (one layer)
	 * 
	 * @param article: article to find children for
	 * @return: list of children
	 */
	def ArrayList<Article> getChildrenArticles( Article parentArticle){
		return Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"])
	}
	
	/**
	 * Creates a list of all siblings for the specified article
	 * 
	 * @param article: article to find siblings for
	 * @return: list of all siblings
	 */
	def ArrayList<Article> getSiblingArticles( Article article){
		return Article.findAllByParentArticle(article.parentArticle, [sort:"sequence", order:"asc"])
	}
	
	/**
	 * Parses the specified string and replace ${123} with the value of the Indicator with the id of the ArtInd table
	 * 
	 * @params 	s: String to parse
	 * @return: parsed String
	 */
	public String parseForIndicator(String s, Article article){
		if(s!=null&&s!=""){
			Pattern p = Pattern.compile('\\$\\{([0-9]+)}');
			Matcher m = p.matcher(s);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String indicatorId = m.group(1);
				if(indicatorId.matches("[0-9]+")){
					Indicator ind = Indicator.get(indicatorId)
					Long value = ArtInd.findByIndicatorAndArticle(ind, article)?.value
					System.out.print("Wert: "+value)	
					m.appendReplacement(sb, Matcher.quoteReplacement(value.toString()));	
				}
			}
			m.appendTail(sb);
			return sb.toString();
		}else{
			return ""
		}
	}
	
	/**
	 * Lists all Indicators belonging to a report
	 * @param report Report, for which the Indicators should be list
	 * @author Sebastian van Vliet
	 * @return List of indicators
	 */
	def ArrayList<Indicator> listReportIndicators(Report report){
		
		//create List for indicators
		ArrayList<Indicator> indicatorList = new ArrayList<Indicator>();
		
		def ArticleList = listChildren(report.getRootArticle())
		
		ArticleList.each{
			def ArticleInstance = Article.get(it.id)	
			
			def ArtIndInstance = ArtInd.findAllByArticle(ArticleInstance)	
			
			ArtIndInstance.each{
				indicatorList.add(it.getIndicator())
			}		
		}	
		return indicatorList
	}
	
	
	/**
	 * Lists all Indicators belonging to an article
	 * 
	 * @param article Article, for which the Indicators should be searched
	 * @return List of Indicators
	 */
	def ArrayList<Indicator> listIndicators(Article articleInstance){
		return ArtInd.findAllByArticle(articleInstance).indicator
	}
	
	/**
	 * Lists all Children of this Article
	 * 
	 * @param parent: parent Article
	 * @return ArrayList list
	 */
	def ArrayList<Article> listChildren (Article parent){	
		return listChildrenIntern (new ArrayList<Article>(), parent)
	}
	
	/**
	 * Lists all Children of this Article
	 * 
	 * @param childList: list of all articles 
	 * @param parentArticle: parent article for recursive invocation		
	 * @return: List of all child-articles
	 */
	private ArrayList<Article> listChildrenIntern (ArrayList<Article> childList, Article parentArticle){
		
		def children = Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"]) 
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			if(article!=parentArticle){
				childList.add(article)
				listChildrenIntern (childList, article)
			}
		}
		
		return childList
	}
	
	
	/**
	 * Copies all children for the specified parentArticle. Internal function called by copyChildren
	 * 
	 * @param parentArticle: parent article
	 * @param newArticle: new parent article
	 * @param user: user
	 */
	def copyChildren (Article parentArticle, Article newParentArticle, User user){
		
		def children = Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"]) 
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			if(article!=parentArticle&&article.status.status!="Closed"){
				def newArticle = new Article(name:"Copy of "+article.name,content:article.content, status:ArticleStatus.findByStatus("New"), author:user, parentArticle:newParentArticle).save(flush:true)
				copyIndicators(article, newArticle, user)
				copyChildren (article, newArticle, user)
			}
		}
	}
	
	/**
	 * Sets status of all children to closed
	 * 
	 * @param parentArticle: parent article
	 * @param user: user
	 */
	def closeChildren (Article parentArticle, User user){
		
		def children = Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"]) 
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			if(article!=parentArticle){
				article.status = ArticleStatus.findByStatus("Closed");
				article.author = user
				article.save(flush:true)
				
				closeChildren (article, user)
			}
		}
	}
	
	/**
	 * Sets status of all children to development
	 * 
	 * @param article: article
	 * @param user: user
	 */
	def openParents (Article article, User user){
		
		Article parentArticle = article.parentArticle
		
		if(parentArticle.name!="Root"){
			parentArticle.status = ArticleStatus.findByStatus("Development");
			parentArticle.author = user
			parentArticle.save(flush:true)
			openParents(parentArticle,user)
			
		}
	}
	
	/**
	 * Checks if children of the article are already approved
	 * 
	 * @param article: article
	 * @param user: user
	 */
	boolean childrenApproved (Article parentArticle){
		
		def children = Article.findAllByParentArticle(parentArticle, [sort:"sequence", order:"asc"]) 
		
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			if(article.status.status=="Approved"){
				childrenApproved (article)
			}else{
				return false				
			}
		}
		return true;
	}
	
	/**
	 * Deletes all child articles
	 * 
	 * @param list: article list
	 * @param parent: parent article
	 */
	public deleteChildNodes(ArrayList<Article> list, Article parent){
		list.remove(parent)
		for (Iterator iterator = parent.listChildren.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			deleteChildNodes(list)
		}
	}
	
	/**
	 * List all Articles for specified report with numbers
	 * 
	 * @param report: report
	 * @return: list of all articles
	 */
	def ArrayList<Article>  buildTree(Report report){
		//Root article for this report
		def rootArticle = report.rootArticle
		
		//Define TreeList
		ArrayList<Article> treeList = new ArrayList<Article> ()
		
		//Get the Children
		return buildTreeIntern(rootArticle, treeList )
	}
	
	/**
	 * List all Articles for specified report with numbers. Internal function called by buildTree()
	 * 
	 * @param parentArticle: parentArticle
	 * @param treeList: return lit
	 * @return: list of all articles
	 */
	private ArrayList<Article>  buildTreeIntern(Article parentArticle, ArrayList<Article> treeList ){
		//find next Tree Elements
		def children = Article.findAllByParentArticleAndStatusNotEqual(parentArticle,ArticleStatus.findByStatus("Revision"), [sort:"sequence", order:"asc"]) 
		//Add Children
		int i=0;
		int sequence = 1
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article article = (Article) iterator.next();
			
			if(article.status!=ArticleStatus.findByStatus("Revision")&&article.status!=ArticleStatus.findByStatus("Closed")){
				//generate Number
				i++;
				if(parentArticle.number==""||parentArticle.number==null){
					article.number= i //Increment last Number + 1
				}else{
					article.number= parentArticle.number+"."+i //Increment last Number + 1
				}
				
				//increment sequence
				article.sequence=sequence
				sequence++
			}
			
			//add article to treeList
			if(article!=parentArticle){
				treeList.add(article);
				//recursive method invocation
				buildTreeIntern(article, treeList )
			}
			
		}		
		//at the end return the TreeList
		return treeList
	}
	
	/**
	 * Sets all status of the childs to "closed"
	 * 
	 * @params parentArticle: Article
	 */
	def setChildStatusDeleted(Article parentArticle){
		//find next Tree Elements
		def children = Article.findAllByParentArticle(parentArticle) 
		for (Iterator iterator = children.iterator(); iterator.hasNext();) {
			Article childArticle = (Article) iterator.next();
			childArticle.status = ArticleStatus.findByStatus("Closed")
			setChildStatusDeleted(childArticle)
		}
	}
	
	/**
	 * Checks if the submitted article is locked by another user. In this case a message is flashed an the user gets redirected to article list. Otherwise the user gets the lock for this article
	 * 
	 * @param articleInstance Article which is checked if locked
	 * @return true, if Article is not locked, false if article is locked
	 */
	public boolean articleNotLocked(Article articleInstance, User user){
		//check if article is locked
		def currentTimestamp = System.currentTimeMillis() / 1000
		
		if(articleInstance.lockTimestamp+6*60*60>currentTimestamp && user.id!=articleInstance.lockUser.id){
			//lock active, user cannot modify Article
			
			return false
		}else{
			//lock not active, current user can modify article
			articleInstance.lockTimestamp = currentTimestamp
			articleInstance.lockUser = user
			
			return true
		}
	}
	
	/**
	 * frees a lock of an article
	 * 
	 * @params: Article to be freed
	 */
	public void freeArticleLock(Article articleInstance){
		articleInstance.lockUser=null
		articleInstance.lockTimestamp=0
	}
}
