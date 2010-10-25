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
 
package schemaEditor.indicatorAdministration

import java.text.SimpleDateFormat;

import ReportEditorMain.ArticleEditor.ArtInd;
import ReportEditorMain.ReportEditor.Report;
import shemaEditor.indicatorAdministration.Indicator;
import shemaEditor.shemaAdministration.TNodeIndi;
import shemaEditor.shemaAdministration.TNode;

class IndicatorService {
	
	static transactional = true
	
	def TNodeIndicatorService
	def reportService
	def articleService
	
	def boolean isIndicatorObligatory(TNode tnode, Indicator indicator){
		
		if(!indicator || !tnode)
			return null	
		def ti = TNodeIndi.find("from TNodeIndi as t where t.tnode="+tnode.id+" and t.indicator="+indicator.id)
		if(ti && ti.i_usage == 0){//Obligatory
			return true	
		}
		return false
		
	}
	
	def boolean isIndicatorOptional(TNode tnode, Indicator indicator){
		
		if(!indicator || !tnode)
			return null
		def ti = TNodeIndi.find("from TNodeIndi as t where t.tnode="+tnode.id+" and t.indicator="+indicator.id)
		if(ti && ti.i_usage == 1){//Optional
			return true	
		}
		return false
		
	}
	
	def List yearSelection(Indicator indicator){
		def notApprovedList = Report.list() 
		def reportList = Report.list()
		def labels = new ArrayList<Integer>()
		
		reportList.each{
			if(!reportService.isReportApproveable(it)){	
				notApprovedList.remove(it)
			}
		}
		reportList = notApprovedList
		def indicatorInstance = indicator
		def indicatorValueList = new ArrayList<Long>()
		
		def articleIndicators
		def articles
		
		reportList.each{
			articles = reportService.listArticles(it)
			
			articles.each{art->
				articleIndicators = articleService.listIndicators(art)	
				if(articleIndicators.contains(indicatorInstance)){
					
					def artIndList = ArtInd.findAllByArticle(art)
					
					artIndList.each{
						if(it.indicator==indicatorInstance){
							
							SimpleDateFormat y = new SimpleDateFormat("yyyy")
							y.format((java.util.Date) articleService.getReport(art).getReleasedDate())
							
							labels.add(new Integer(y.format((java.util.Date) articleService.getReport(art).getReleasedDate())).intValue())
						}
					}	
				}
			}
		}
		return labels
	}
	
	
	
	
	def int isIndicatorValueBigger(Indicator indicatorInstance){
		
		def notApprovedList = Report.list() 
		def reportList = Report.list()
		
		reportList.each{
			if(!reportService.isReportApproveable(it)){	
				notApprovedList.remove(it)
			}
		}
		reportList = notApprovedList
		
		def indicatorValueList = new ArrayList<Long>()
		
		def articleIndicators
		def articles
		
		reportList.each{
			articles = reportService.listArticles(it)
			
			articles.each{art->
				articleIndicators = articleService.listIndicators(art)
				
				if(articleIndicators.contains(indicatorInstance)){
					
					def artIndList = ArtInd.findAllByArticle(art)
					
					artIndList.each{
						if(it.indicator==indicatorInstance){
							indicatorValueList.add(it.value)					
						}
					}	
				}	
			}
		}
		
		if(indicatorValueList.size()>1){
			
			def lastIndicator = indicatorValueList.get(indicatorValueList.size()-1)
			def prevLastIndicator = indicatorValueList.get(indicatorValueList.size()-2)
			
			if((lastIndicator-prevLastIndicator)>0){
				return 1
			}else if(lastIndicator-prevLastIndicator==0){
				return 3
			}else{
				return 2
			}
			
		}
		
		return 0
	}

	/*
	 * return true, if selected indicator is used by an article,
	 * otherwise returns false
	 * 
	 */
	def boolean isIndicatorUsedInArticle(Indicator indicatorInstance){
			
			boolean inUse = false
			ArtInd.list().each{
				if(indicatorInstance.id==it.indicator.id){
					inUse= true
				}
			}
			return inUse
		}
}
