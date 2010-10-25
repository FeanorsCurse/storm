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
 
package systemadministration.modulmanagement

import systemadministration.usermanagement.*
//TODO println rausnehmen
class ModuleService {
	
	//TODO raus
	static String getModuleName(Object obj)
	{
		String name
		name = obj.class.toString()
		name = name.substring(name.indexOf (' ')+1, name.length())
		return name
	}
	
	static Module initialiseModule(String moduleName) {
		Module module
		module = Module.getModule(moduleName)
		//if module is null than installModule
		if(!module)
		{
			module = registerModule (moduleName)
			registerRules(module)
			module.save()
			registerConfigs(module)
			module.save()
		}
		return module
		
	}
	
	
	static Module registerModule(String moduleName) {
		ArrayList moduleSettings
		moduleSettings=ModuleSettingsService.getModuleSettings(moduleName)
		if(moduleSettings==null) {
		}
		else {
			Module.registerModule(moduleName,moduleSettings[0],moduleSettings[1])
		}
	}
	
	static void registerRules(Module module) {
		//name, description, action
		ArrayList rules
		rules=ModuleSettingsService.getRules(module)
		Rule rule
		if(rules==null)
		{
		}
		else {
			Role roleAdmin = Role.findByName("Admin")
			for (int i =0; i<rules.size;i++) {
				rule = module.registerRule(module,rules[i][0],rules[i][1],rules[i][2])
				
				module.save(flush:true)
				roleAdmin.addToRules(rule)
			}
			roleAdmin.save(flush:true)
		}
		
	}
	
	static void registerConfigs(Module module) {
		//name, value, description
		ArrayList configs
		configs=ModuleSettingsService.getConfigs(module)
		if(configs==null)
		{
		}
		else {
			for (int i =0; i<configs.size;i++) {
				module.registerConfig(module,configs[i][0],configs[i][1],configs[i][2])
			}
		}
		
	}
	
	
	
	
	
}
