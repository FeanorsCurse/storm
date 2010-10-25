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
 
package systemadministration.usermanagement

import systemadministration.usermanagement.User
import systemadministration.modulmanagement.Module;
import systemadministration.modulmanagement.UserService

class UserTagLib {
	
	def userService;
	
	def displayUsername = {attrs ->
	
		User sessionuser = session.user
		
		User follower = attrs["user"]
	
		
		
		if((sessionuser==null)|| (follower==null))
		{
			println("No Paranull")
			flash.message = "${message(code: 'default.not.found.message')}"
			redirect(controller:"welcome", action:"welcomePage")
		}
		if (sessionuser.id==follower?.id)
		{
			out << '<a href="/Storm/user/showUser?id='+follower.getId()+'">'+follower.getUsername().toString()+' </a>'
			return	
		}
		
		
		String hsql2 = "select user.id, friend.id "+
    	" from User as user join user.friends as friend " +
    	"  where user.id = ? and friend.id = ?  "
	 //java.util.List lastArticle = AccessArticleTyp.executeQuery(hsql2)
	 java.util.List result = User.executeQuery(hsql2, [sessionuser.id,follower.id] )
	 //TODO null abfangen, also user hat noch keine article angesehen

		
		if(result[0]==null)
		{
		out << '<a href="/Storm/user/showUser?id='+follower.getId()+'">'+follower.getUsername().toString()+' </a>' 	+ '<a href="/Storm/user/addFriend?id='+sessionuser.getId()+'&friendID='+follower.getId().toString()+'"><img src="/Storm/images/oxygen/list-add-user.png" alt="Add Friend"> </a>'
		}
		else
		{
		out << '<a href="/Storm/user/showUser?id='+follower.getId()+'">'+follower.getUsername().toString()+' </a>' 	+ '<a href="/Storm/user/deleteFriend?id='+sessionuser.getId()+'&friendID='+follower.getId().toString()+'"><img src="/Storm/images/oxygen/list-delete-user.png" alt="Add Friend"> </a>'
		
		}
		
		
		
		
			
	}
	
	def checkAccess = { attrs, body ->
		def user_name = session.user.username
		
		def modul_name = attrs["modulname"]
		def action_name = attrs["actionname"]
		
		if(userService.checkAccess(user_name, modul_name, action_name)){
			out << body()
		}
		
	}
	
	def isActive={attrs, body ->	
		def modul_name = attrs["modulname"]
		Module m = Module.findByName(modul_name)	
		if(m.detachable==1){//Module is on
			out << body()	
		}
		//Module is off
	}
}
