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
 
package validator

import java.util.ArrayList;
import java.util.List;

import schemaEditor.indicatorAdministration.IndicatorService;
import schemaEditor.schemaAdministration.TNodeService;
import shemaEditor.indicatorAdministration.Indicator;
import shemaEditor.shemaAdministration.*;
import ReportEditorMain.ArticleEditor.ArtInd;
import ReportEditorMain.ArticleEditor.Article;
import ReportEditorMain.ArticleEditor.ArticleService;
import ReportEditorMain.ReportEditor.Report;

class ValidatorService {
	
	
	def TReportService
	def indicatorService
	def articleService
	def TNodeService
	
	
	
	/**
	 * Validates a Report against a given schema.
	 * @param report The Report
	 * @param schema The schema
	 * @return ValidatorResult The validation-result. 
	 */
	public ValidatorResult singleValidate(Report report, TReport schema){
		
		ValidatorResult vr
		
		vr = new ValidatorResult(normalizeList(articleService.listReportIndicators(report)), normalizeList(TReportService.listObligatorySchemaIndicators(schema)), report, schema)
		vr.setNumberOfRequiredIndicators(vr.getRequiredIndicators().size())
		
		
		calculateMissingIndicators(vr)
		
		println("###################Missing Indicators:"+vr.getMissingIndicators())
		
		calculateOptionalIndicators(vr)
		
		calculateMissingOptionalIndicators(vr)
		
		return vr
	}
	
	
	
	public void calculateMissingIndicators(ValidatorResult vr){
		boolean check = false
		vr.getRequiredIndicators().each{required->
			vr.getReportIndicators().each{report->
				if(report.id==required.id){
					check = true
					println("Gefunden: "+report.id+" gesucht: "+required.id)
				}
			}
			if(!check){
				vr.getMissingIndicators().add(required)
			}
			
			check = false
		}
		
		if(!vr.getMissingIndicators().isEmpty()){
			vr.setReportValid(false)
			vr.setNumberOfMissingIndicators(vr.getMissingIndicators().size())
		}
		else{
			vr.setReportValid(true)
		}
	}
	
	public void calculateOptionalIndicators(ValidatorResult vr){
		
		vr.setOptionalIndicators(TReportService.listOptionalSchemaIndicators(vr.getTReport()))
		
	}
	
	public void calculateMissingOptionalIndicators(ValidatorResult vr){
		boolean check = false
		vr.getOptionalIndicators().each{required->
			vr.getReportIndicators().each{report->
				if(report.id==required.id){
					check = true
					println("Gefunden: "+report.id+" gesucht: "+required.id)
				}
			}
			if(!check){
				vr.getMissingOptionalIndicators().add(required)
			}
			
			check = false
		}
		
		
	}
	
	
	public ArrayList<ValidatorResult> multiValidate(Report report, TReport schema){
		
		def TReportInstanceList = TReport.list()
		
		ArrayList<ValidatorResult> vrList = new ArrayList<ValidatorResult>()
		
		ArrayList<Indicator> reportIndicatorList =normalizeList(articleService.listArticleIndicators(report))
		
		ValidatorResult singleVr =new ValidatorResult(reportIndicatorList, normalizeList(TReportService.listSchemaIndicators(schema)))
		vrList.add(singleVr)
		
		TReportInstanceList.each{
			
			ArrayList<Indicator> schemaIndicators = normalizeList(TReportService.listSchemaIndicators(it))
			
			if(it!=schema && schemaIndicators && vrList.size()<4){
				ValidatorResult vr =new ValidatorResult(reportIndicatorList, schemaIndicators)
				if(vr.getMissingIndicators().size()< (schemaIndicators.size()/4))//25% variance
					vrList.add(vr)
			}
		}
		
		return vrList
	}
	
	
	/**
	 * Normalize a given list, removes all duplicates and sort it
	 * @param listInstance the List to normalize
	 * @return listInstance, a normalized List
	 */
	def ArrayList<?> normalizeList(ArrayList<?> listInstance){	
		
		def help = new ArrayList<?>()
		
		help.addAll(listInstance)
		listInstance.clear()	
		help.each{
			if(!listInstance.contains(it))
				listInstance.add(it)
		}	
		
		listInstance.sort{ it.id }
		
		return listInstance
	}
	
	
}
