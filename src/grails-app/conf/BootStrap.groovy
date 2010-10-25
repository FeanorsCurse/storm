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
 
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import bootstrap.*
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
* Grails BootStrap
* Based on the current running env the right conf will be loaded
*
* @author Edzard Fisch, Oliver Norkus
*/

class BootStrap {
	
	/**
	* started by init
	*
	*/
	def init = { servletContext ->
		if(!envMapping[GrailsUtil.environment]){
			return
		}
		
		envMapping[GrailsUtil.environment]()
	}
	
	def destroy = {
	}
	
	/**
	* contains the loading for the test env
	*
	*/
	
	def initTest = {
		//SA als erstes wegen module, configs, I18N, security
		new SystemAdministration().initTest()
		// Bootstrap vom SE
		new SchemaEditor().initTest()
		// Bootstrap vom EE
		new EingabeEditor().initTest()
		// Bootstrap vom IF
		new InteractiveFeatures().initTest()
		// DIeses soll in Zukunft raus
		new GlobalBootstrap().initTest()
	}
	
	/**
	* contains the loading for the test env
	*
	*/
	
	def initDev = {
			//SA als erstes wegen module, configs, I18N, security
			new SystemAdministration().initDev()
			// Bootstrap vom SE
			new SchemaEditor().initDev()
			// Bootstrap vom EE
			new EingabeEditor().initDev()
			// Bootstrap vom IF
			new InteractiveFeatures().initDev()
			// DIeses soll in Zukunft raus
			new GlobalBootstrap().initDev()
	}
	
	/**
	* contains the loading for the test env
	*
	*/
	
	def initProd = {
			//SA als erstes wegen module, configs, I18N, security
			new SystemAdministration().initProd()
			// Bootstrap vom SE
			new SchemaEditor().initProd()
			// Bootstrap vom EE
			new EingabeEditor().initProd()
			// Bootstrap vom IF
			new InteractiveFeatures().initProd()
			// DIeses soll in Zukunft raus
			new GlobalBootstrap().initProd()
	}
	
	def envMapping = [(GrailsApplication.ENV_TEST):initTest,
	(GrailsApplication.ENV_DEVELOPMENT):initDev,
	(GrailsApplication.ENV_PRODUCTION):initProd]

} 
