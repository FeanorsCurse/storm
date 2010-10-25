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

import systemadministration.modulmanagement.Module;
import systemadministration.usermanagement.*;
import systemadministration.usermanagement.User;
import systemadministration.usermanagement.Rule;
import systemadministration.usermanagement.Role;

class UserService {

    boolean transactional = true

	static ArrayList<User> getAllUsersWhoHaveOneOrMoreFriends(){
    	ArrayList<User> users
    	users = null
    	println "in getAllUsersWhoHaveOneOrMoreFriends"
    	
    //	users = User.findAllWithFriends()
    // 	
   
    	ArrayList<User> usersWithFriends;
    	
    	User.each { user ->
    				println user
    				
    				user.friends.each { friend ->
    					println friend
    					usersWithFriends.add(user)
    				}
    	}
    	
    	
    	
    	return usersWithFriends
  
    	
    }
    
    
    static boolean checkAccess(){
    	return checkAccessObjectmethod()
    }
    static boolean checkAccessObjectmethod(){
    	UserService us = new UserService();
    	return us.checking();
    }
    
    boolean checking(){
    	return checkAccess(session.user.username, params.controller, params.action)
    }
    
//replaced by     checkAccess(User user, String modul_name, String action_name)
    //but maybe it is still used so it access the new one
    static boolean checkAccess(String user_name, String modul_name, String action_name){

    	User useruser = User.findByUsername(user_name)
    	return checkAccess(useruser, modul_name, action_name)
    	
    }


static boolean checkAccess(User user, String modul_name, String action_name){
	java.util.List result
	//get user requesting
	
	
	String hsql="select rule.actionname, rule.module,user.username"+
	" from User as user join user.roles as role join role.rules as rule  " +
	"  where user.id = ? and rule.module.name = ? and (rule.actionname = ? or rule.actionname = ?) " 

	result = User.executeQuery(hsql, [user.id, modul_name, action_name, '*'])
	
	if (result.size()>0){
		//println("CheckAccess Zugriff: JA:  modul_name: "+modul_name+ " action_name: "+action_name+ " user_name: "+user.username+" resultset: "+result)
		return true
	}
	else
	{
		//println("CheckAccess Zugriff: Nein:  modul_name: "+modul_name+ " action_name: "+action_name+ " user_name: "+user.username +" resultset: "+result)
		return false
	}
}
}
