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
 
package storm
import systemadministration.modulmanagement.*;
import systemadministration.modulmanagement.UserService;
import systemadministration.usermanagement.*;
import systemadministration.usermanagement.User;
import systemadministration.usermanagement.Rule;
import systemadministration.usermanagement.Role;
import systemadministration.log.LogService;

class CheckPrivilegesFilters {

    def filters = { 
    		check(controller:'*', action:'*'){
    			before = {
    				//if user doesn't exist delete session
    					if(User.get(session.user?.id)==null)
    					{
    						session.user = null
    					}
    					//if no sessin.user is available, set session.user on anonym	
    				if(!session.user)  {
    					session.user = User.findByUsername("Anonym")
    					//TODO IP nachtragen
    					//logeintrag wird geschrieben
    					LogService.writeSecurityLog session.user.toString(), 1,'IP'
    					session.setAttribute("previousController", params.controller)
    				}

    				//check permission - Begin
    				
    				//Declaration
    				User user
    				String modul_name
    				String action_name
    				def result = false
    				String previousController
    				String previousAction
    				String previousParams
    				
    				// get the AppContext for I18n messages
    				 def messageSource = grailsAttributes.getApplicationContext().getBean("messageSource")

    				
    				//remember your last Controller, Action and all Params
    				previousController = session.getAttribute("previousController")
    		    	previousAction = session.getAttribute("previousAction")
    		    	previousParams = session.getAttribute("previousParams")
    		    	//set the current Controller, Action and all Params
    		    	session.setAttribute("previousController", params.controller)
    		    	session.setAttribute("previousAction", params.action)
    		    	session.setAttribute("previousParams", params)

    				//Initialisation
    				//modul_name = params.controller
    		    	modul_name = controllerName
    				//TODO 
					action_name = actionName
					user = session.user
					Module userModule = Module.getModule("user")
					//pruefen ob security an ist
    				if(userModule?.getConfig('securityOn') == '1'){
    	//				println("Security is on: ")
/*						if(   controllerName == 'function'|| controllerName == 'media'){
							return true	
						}*/
						
						result = UserService.checkAccess(user, modul_name, action_name)
						
						//println "ModuleName: " + modul_name + " ActionName:" + action_name + " controllerName: " + controllerName + " username: "+user.username
						//permisson?
	    				if (result){
	    					//println "Berechtigung ja"
	    					return true
	    					
	    				}
						//no
	    				else
	    				{
	    					//println "Berechtigung nein"
						//TODO die flash.message wird nicht aus dem cache gelöscht
						//if Anonym say he should log in

						if (user.username== 'Anonym')
						{
							//flash.message = message(code:"user.security.noUserNoAccess", args:[modul_name,action_name,request.request.requestURL])
							flash.message = messageSource.getMessage("user.security.noUserNoAccess",null,null,  new Locale("de"))	+ " MODULE: "+modul_name + " ACTION: "+action_name + " USER: "+user.username
							
								
						}
						else
						{
							//flash.message = message(code:"user.security.NoAccess", args:[modul_name,action_name,request.request.requestURL])
							flash.message = messageSource.getMessage("user.security.NoAccess",null,null,  new Locale("de"))	+ " MODULE: "+modul_name + " ACTION: "+action_name + " USER: "+user.username
						}
						
					//	redirect(controller:previousController , action: previousAction, params:previousParams)

					//	render("Keine Erlaubnis"+ "params: "+params)
						redirect(controller:"welcome", action:"welcomePage")
						return false
	    				}

    				}
    				//security is not on, go on
    				else
    				{
    					return true
    				}
    				//check permission -End
    			}
    		}
    			
    		
    		  	
    }    			
}
