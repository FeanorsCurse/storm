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
import systemadministration.log.AccessLog
import ReportEditorMain.ArticleEditor.Article
import java.text.DateFormat
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Service, which creates and updates statictics about count of access
 *
 * @author Edzard Fisch, Oliver Norkus
 * 
 */
class ArticleAccessReportingService {
	
	
	static ArrayList<Article> getTopTenViewedArticle(int max, int offset){
		
    	String hsql = "select accessArticleTyp.article from AccessArticleTyp as accessArticleTyp " +
			" order by accessArticleTyp.countAll desc "		
		java.util.ArrayList result = AccessArticleTyp.executeQuery(hsql,[max:max, offset:offset])
		return result
		//TODO: nur die ersten 10 wiedergeben
	}
	
	/**
	 * updates the count statistics 
	 * 
	 *
	 */
	static boolean updateAccessArticleType() {
		AccessArticleTyp accessArticleTyp
		Article article
		String value = ''
		Long datevalue
		Module module
		String hsql
		Date currentTimestamp = new Date()
		ArrayList<Long> countPara = new ArrayList<Long>(6)
		//hole last run
		
		module = Module.getModule("accessArticleTyp")
		//hole die Para in tagen  
		countPara[0] = Long.parseLong(module?.getConfig('countPara0'))
		countPara[1] = Long.parseLong(module?.getConfig('countPara1'))
		countPara[2] = Long.parseLong(module?.getConfig('countPara2'))	
		countPara[3] = Long.parseLong(module?.getConfig('countPara3'))	
		countPara[4] = Long.parseLong(module?.getConfig('countPara4'))
		//für countAlle Zeitpunkt der letzten Berechnung fuer delta
			countPara[5] = Long.parseLong(module?.getConfig('lastRun'))
		
		for (int yy=0;yy<countPara.size();yy++)
		{
			//Wenn -1 nichts machen
			if(countPara[yy]!=-1) {
				//Wenn 5 dann handelt es sich um delte
				Date countParaDate
				if (yy==5){
					countParaDate = new Date(countPara[yy])
					hsql="select accessLog.article,accessLog.typeAccess, count(accessLog.article) as accessCount from AccessLog as accessLog" +
							"  where accessLog.dateCreated > ?" +
							"group by accessLog.article,accessLog.typeAccess "		
				}
				else {
					Calendar countParaCalendar = Calendar.getInstance()
					countParaCalendar.add(Calendar.DAY_OF_MONTH, -(Integer)countPara[yy])
					countParaDate= countParaCalendar.getTime()
					hsql="select accessLog.article,accessLog.typeAccess, count(accessLog.article) as accessCount from AccessLog as accessLog" +
							"  where accessLog.dateCreated > ?" +
							"group by accessLog.article,accessLog.typeAccess "
					
				}
				java.util.List result = AccessLog.executeQuery(hsql, [countParaDate])
				
				//	println(list)
				for (int ii = 0; ii < result.size(); ii++)
				{
					
					article = result[ii][0]
				//	println("Artikel: " + article)
					accessArticleTyp=AccessArticleTyp.findByArticleAndTypeAccess(article,result[ii][1])
					
					if (accessArticleTyp==null) {
				//		println("nicht gefunden")
						accessArticleTyp = new AccessArticleTyp(typeAccess:result[ii][1],article:article,countAll:"0",countPara0:"0",countPara1:"0",countPara2:"0",countPara3:"0",countPara4:"0")
						accessArticleTyp.save()
					}
					
					switch (yy) {
						case '0':
							accessArticleTyp.setCountPara0((Integer)result[ii][2])
							accessArticleTyp.save()
							break;
						case '1':
							accessArticleTyp.setCountPara1((Integer)result[ii][2])
							accessArticleTyp.save()
							break;
						case '2':
							accessArticleTyp.setCountPara2((Integer)result[ii][2])
							accessArticleTyp.save()
							break;
						case '3':
							accessArticleTyp.setCountPara3((Integer)result[ii][2])
							accessArticleTyp.save()
							break;
						case '4':
							accessArticleTyp.setCountPara4((Integer)result[ii][2])
							accessArticleTyp.save()
							break;
						//noch probs mit
						case '5':
							accessArticleTyp.setCountAll(accessArticleTyp.getCountAll() + (Integer)result[ii][2]) 
						//accessArticleTyp.setCountAll((Integer)result[ii][2]) 
							accessArticleTyp.save()
							break;
					}
					
					
					//lastRun muss noch gespeichert werden
					module?.setConfig( 'lastRun', currentTimestamp.getTime().toString())
					accessArticleTyp=null
					article=null
				}
			}
		}
		return true
	}
	
}
