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
import ReportEditorMain.ArticleEditor.Article
import ReportEditorMain.ReportEditor.Report
import interactiveFeatures.Infocart.Infocart
import interactiveFeatures.Usercomments.Usercomment
/** This Domain Class describes  a user. A user can be member of zero or more roles
 **  @author Edzard Fisch, Oliver Norkus
 */

class User {

    String lastname
    String firstname
    String username
    String password
    Date dateCreated
    Date lastUpdated
    String address
    String postcode
    String city 
    String country //should be drop down box
    Date birthday
    InterestGroup interestGroup
    String email
    String academicTitle //should be drop down box
    String sex //should be drop down box
	String validationEMailCode = 0
	Boolean isDeleted = false


    static constraints = {
        username(nullable:false,size:1..15,blank:false,unique:true)
        password(nullable:false,blank:false,password:true)
        email(nullable:false,email:true,blank:false)
        lastname(nullable:false)
        firstname(nullable:false)
        academicTitle(inList:["B.Sc", "PHD", "DR"],nullable:true)
        sex(inList:["Fräulein", "Frau", "Herr"],nullable:true)
        birthday(max:new Date(),nullable:true)
        address(nullable:true)
        postcode(nullable:true)
        city(nullable:true)
        country(inList:["Germany", "UK", "USA"],nullable:true)
        interestGroup(nullable:true)
		validationEMailCode(nullable:false)
		isDeleted(nullable:false)

    }

    //Gerrit: important for mapping
    static mappedBy = [articles:'author']
    
    def beforeInsert = {
    	password = password.encodeAsSHA()
    }
   
    // static belongsTo = [InterestGroup]
    static belongsTo = [Role, InterestGroup]
    static hasMany = [roles:Role ,articles:Article, reports:Report, infocarts:Infocart,friends:User,usercomments:Usercomment]

    String toString(){
        return username
    }

    /**
     *Creates a directory to store the user's media files.
     *@author Olaf Roeder
     *@paramas String webRootDir: root directory to store files, String username
     */
    void createUserDirectory(String webRootDir, String username){
        File userDir = new File(webRootDir+"/Mediathek/"+username)
        if (!userDir.exists()){
            println "Info: User Media Ordner angelegt für User "+username+": "+userDir.mkdirs()
        } else {
            println "Info: User Media Ordner existiert bereits für User "+username+"."
        }

        File userSubDir = new File(webRootDir+"/Mediathek/"+username+"/imgthumbs/")
        if (!userSubDir.exists()){
            println "Info: User Media Thumbnail-Ordner angelegt für User "+username+": "+userSubDir.mkdirs()
        } else {
            println "Info: User Media Thumbnail-Ordner existiert bereits für User "+username+"."
        }
    }
    
    public boolean isMemberOfRole(String role)
    {
        java.util.List list =Role.executeQuery("select user.username from Role as role join role.users as user where role.name = ? and user.username = ? ", [role,this.username])
        if(list.isEmpty())
        	return false
        return true
    }
    
	public boolean addFriend(User friend)  
	{
	    	

	    	//no friendslist
	    	if (friend==null)
    		{
    			return false
    		}
	    	this.addToFriends(friend)
	    	if(this.save(flush:true))
	    		return true
    		else
    			return false
	    	
	    }
    

}
