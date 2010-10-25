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

import groovyjarjarantlr.collections.List;
import systemadministration.usermanagement.Rule

/** This Domain Class describes  a Module. Each system in Storm is a Module even a detachable function
* Modules which can not be disabled  like infokorb or administration must have the detachable value -1.
* -1 sets the module to not detachable. It is not possible to change -1 via the UI
*  0 to off and 1 to on.
@author Edzard Fisch, Oliver Norkus
*/

class Module {
    String name
    String description
    Date dateCreated
    Date lastUpdated
    //0 aus
    //1 an
    // -1 nicht abschaltbar
    Integer detachable
    
    static constraints = {
    	detachable(inList:[-1, 0, 1],nullable:false)
    }
    
    static hasMany = [rules:Rule , configs:Config]

    String toString(){
        return name
    }

    static Module getModule(String name)
    {
        Module module = findByName(name)
        return module
    }
//TODO evt. auslagern in service oder so
    Boolean isActive()
    {
        if((this.detachable==1) ||(this.detachable==-1))
        true
        else
        false

    }
  //TODO evt. auslagern in service oder so
    static Module registerModule(String name, String description, Integer detachable)
    {
       Module module
    	module = Module.getModule(name)
        if (!module)
         {
             module = new Module (name:name, description:description,detachable:detachable)
            
             module.save()
             return module

         }
         else
         {
             return module
         }
    }
  //TODO evt. auslagern in service oder so
    static Rule registerRule(Module module,String name, String description, String actionname)
    {
        Rule rule= new Rule (name:name,description:description,actionname:actionname)
        module.addToRules(rule)
  	 	rule.save()
    	module.save()
    	return rule
    }

      //TODO evt. auslagern in service oder so
    static void registerConfig(Module module,String name, String value, String description)
    {
    	Config config = new Config (name:name,value:value,description:description)
    	module.addToConfigs(config)
    	config.save()
    	module.save()
    }
    /**
     * This Methode return one config value 
     *
     * @param config is a String must be equal to a config
     * @return String, the value of the config or null if it doesn't exists
     */
    public String getConfig(String config)
    {
        java.util.List list =this.executeQuery("select conf.value from Module as module join module.configs as conf where module.name = ? and conf.name = ? ", [this.name,config])
        if(list.isEmpty())
        	return null
        return list.get(0)
    }
    
    public boolean setConfig(String configName, String value)
    {
    	Config config
    	java.util.List list =this.executeQuery("select conf from Module as module join module.configs as conf where module.name = ? and conf.name = ? ", [this.name,configName])
        if(list.isEmpty())
        	return false
         config = list.get(0)
         config.setValue(value)
         return config.save()
    }


}
