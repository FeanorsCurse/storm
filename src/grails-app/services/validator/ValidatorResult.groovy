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

import ReportEditorMain.ReportEditor.Report;

import shemaEditor.indicatorAdministration.Indicator;
import shemaEditor.shemaAdministration.TReport;
import shemaEditor.shemaAdministration.TReportController;

class ValidatorResult {
	
	
	ArrayList<Indicator> reportIndicators
	ArrayList<Indicator> requiredIndicators
	
	ArrayList<Indicator> missingIndicators= new ArrayList<Indicator>()
	
	ArrayList<Indicator> optionalIndicators = new ArrayList<Indicator>()
	
	ArrayList<Indicator> missingOptionalIndicators = new ArrayList<Indicator>()
	
	
	int numberOfRequiredIndicators
	int numberOfMissingIndicators = 0
	boolean reportValid = false
	
	Report report
	
	TReport tReport
	
	public ValidatorResult(ArrayList<Indicator> reportIndicators, ArrayList<Indicator> requiredIndicators, Report report, TReport tReport){
		
		this.reportIndicators = reportIndicators
		
		this.requiredIndicators = requiredIndicators	
		println("###################requiredIndicators Indicators:"+requiredIndicators)
		println("###################Report Indicators:"+reportIndicators)
		
		this.tReport = tReport
		this.report = report
		
	}
	
	
	
	/**
	 * Returns a list of all report indicators
	 * @return List 
	 */
	public ArrayList<Indicator> getReportIndicators(){
		return this.reportIndicators
	}
	
	
	/**
	 * Returns a list of all required (schema) indicators with usage=0 (obligatory) indicators
	 * @return List 
	 */
	public ArrayList<Indicator> getRequiredIndicators(){
		return this.requiredIndicators
	}
	
	/**
	 * Returns a list of indicators that are obligatory but does not appear in the report-indicator-list
	 * @return List 
	 */
	public ArrayList<Indicator> getMissingIndicators(){
		return this.missingIndicators
	}
	
	/**
	 * Returns a list of all optional schema-indicators
	 * @return List 
	 */
	public ArrayList<Indicator> getOptionalIndicators(){
		return this.optionalIndicators
	}
	
	/**
	 * Returns a list of all optional schema-indicators that does not appear in the report-indicator-list 
	 * @return List 
	 */
	public ArrayList<Indicator> getMissingOptionalIndicators(){
		return this.missingOptionalIndicators
	}
	
	
	public int getNumberOfRequiredIndicators(){
		return this.numberOfRequiredIndicators
	}
	
	public int getNumberOfMissingIndicators(){
		return this.numberOfMissingIndicators
	}
	
	/**
	 * Returns the report to be validated
	 * @return
	 */
	public Report getReport(){
		return this.report
	}
	
	/**
	 * Returns the TReport (schema)
	 * @return TReport the schema
	 */
	public TReport getTReport(){
		return this.tReport
	}
	
	/**
	 * Returns true if the report was successfully validated, false otherwise
	 * @return
	 */
	public boolean isReportValid(){
		return this.reportValid
	}
	
	
	
	public String toHtml(){
		
		String out ="<p>Required Indicators</p>"
		
		requiredIndicators.each{
			out+=it.name+"<br>"
		}
		
		out +="<p>Report-Indicators</p>"
		
		reportIndicators.each{
			out+=it.name+"<br>"
		}
		
		if(isReportValid())
			out+="<p>Report is valid!</p>"
		else
			out+="<p>Report is not valid due to following missing Indicators:</p>"
		
		
		missingIndicators.each{
			out+=it.name+"<br>"
		}
		
		if(!isReportValid())
			out+="<p>Missing: "+getNumberOfMissingIndicators()+" of "+getNumberOfRequiredIndicators()+"</p>"
		
		
		
		return out
	}
	
}
