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
 
/* SapService
 * 
 * @author Irene, Gerrit
 * @since 11.06.2010
 */
package ReportEditorMain.ArticleEditor

import java.text.DecimalFormat;
import java.util.ArrayList

import SAPBAPI.SAPConnector;
import java.util.Iterator;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

import ReportEditorMain.ArticleEditor.Article
import SAPBAPI.SAPConnector;
import shemaEditor.indicatorAdministration.Indicator
import shemaEditor.shemaAdministration.TNode
import systemadministration.externalsystems.*;

/**
 * Service Class for the SAP-Controller
 * 
 * @author: Irene, Gerrit
 */
class SapService {
	
	//define as transactional
	boolean transactional = true
	
	/**
	 * Compute a BAPI
	 * 
	 * @params bapi: Bapi to be computed
	 * @return: result of the BAPI
	 */
	public String computeBapi(Bapi bapi){
		if(bapi!=null){
			SAPConnector sap = new SAPConnector()
			SAPConnection sapConnection = bapi.connection
			if(sap.openConnectionToSAP(sapConnection.client, sapConnection.login, sapConnection.password, sapConnection.locale,sapConnection.url, sapConnection.system)){
				
				String bapiName = bapi.bapi;
				
				ArrayList importParameter = new ArrayList();
				importParameter=bapi.importParameter.split(";")
				
				String exportStructure=bapi.exportStructure;
				
				String exportValue=bapi.exportValue;
				
				String result = getBapiResult(sap, bapiName, importParameter, exportStructure, exportValue);
				
				sap.closeConnectionToSAP()
				
				return result;
			}
		}
		return null
	}
	
	/**
	 * This function calculates the result of a specified Bapi
	 * 
	 * @param sap Connection Object to SAP from SAPController
	 * @param bapi Name of the BAPI to be calculated
	 * @param importParameter List of Parameters for the BAPI with name and value
	 * @param exportStructure Name of the Structure to be calculated
	 * @param exportValue name of the value field within the exportStructure to be calculated
	 * @return
	 */
	public String getBapiResult(SAPConnector sap, String bapi, ArrayList importParameter, String exportStructure, String exportValue){
		
		JCO.Function function = null;
		try {
			function = sap.createFunction(bapi);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		if (function == null) {
			System.err.println(bapi + " not found in SAP");
			System.exit(1);
		}
		
		for (Iterator iterator = importParameter.iterator(); iterator.hasNext();) {
			String stringValue = (String) iterator.next();
			String stringField = (String) iterator.next();
			function.getImportParameterList().setValue(stringValue, stringField);
		}
		
		sap.execute(function);		
		
		Double doubleValue=function.getExportParameterList().getDouble(exportStructure);
		String value = String.valueOf(doubleValue).replace('.', ',');
		return value;
	}
}
