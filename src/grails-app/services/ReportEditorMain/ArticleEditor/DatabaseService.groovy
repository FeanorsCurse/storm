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

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList

import SAPBAPI.SAPConnector;
import java.util.Iterator;

import com.mysql.jdbc.Connection;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

import ReportEditorMain.ArticleEditor.Article
import SAPBAPI.SAPConnector;
import shemaEditor.indicatorAdministration.Indicator
import shemaEditor.shemaAdministration.TNode
import systemadministration.externalsystems.*;

/**
 * This Service create a Connection to a Database
 * 
 * @author Irene, Gerrit
 *
 */
class DatabaseService {
	
	//define as transactional
	boolean transactional = true
	
	/**
	 * Executes a SQL Statement
	 * 
	 * @param sqlStatement Statement which should be executet
	 * @return null, if no result could be computet. First Result, if there is a result
	 */
	public String computeSQL(SQLStatement sqlStatement){
		try {
			DatabaseConnection db = sqlStatement.connection
			Class.forName(db.driver)
			Connection connection = DriverManager.getConnection(db.url, db.user, db.password)
			Statement statement = connection.createStatement()
			ResultSet resultSet = statement.executeQuery(sqlStatement.sqlStatement)
			while(resultSet.next()){
				return resultSet.getString(1)
			}
			return null
		} catch (Exception e) {
			System.out.println(e);
		}
		return null
	}
}
