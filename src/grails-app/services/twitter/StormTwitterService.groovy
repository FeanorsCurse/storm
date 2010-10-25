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
 
package twitter

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import winterwell.jtwitter.OAuthSignpostClient;
import winterwell.jtwitter.Twitter;

/**
 * 
 * @author Sebastian van vliet mailto:sebastian.van.vliet@mail.uni-oldenburg.de
 *
 */
class StormTwitterService {
	
	
	static scope = "singleton"
	
	
	//  Consumer key
	static String CONSUMER_KEY = "3YGTXySJNQSv9CxdFy6tA"
	// Consumer secret
	static String CONSUMER_SECRET ="F22uMnca3DmgBlcYikbH4YPz7Q1I7TCh9mKLxPTow"
	// Request token URL
	static String REQUEST_TOKEN_URL ="https://twitter.com/oauth/request_token"
	// Access token URL
	static String ACCESS_TOKEN_URL ="https://twitter.com/oauth/access_token"
	// Authorize URL
	static String AUTHORIZE_URL ="https://twitter.com/oauth/authorize"
	
	OAuthSignpostClient client
	
	String ACCESS_TOKEN
	String ACCESS_TOKEN_SECRET
	
	String UNAME ="pgstorm"
	
	Twitter jtwit
	
	/**
	 * 
	 * @return true if login was successfull, false otherwise
	 */
	def String authenticateApp(){
		
		
		this.client = new OAuthSignpostClient(CONSUMER_KEY, CONSUMER_SECRET, "oob")
		this.jtwit = new Twitter(this.UNAME, client)
		
		
		
		return client.authorizeUrl()
	}
	
	
	/**
	 * 
	 * @return true if logout was successful, false otherwise
	 */
	def boolean logout(){
		
	}
	
	def sendAuthorisationCode(String pin){
		
		client.setAuthorizationCode(pin);
		String[] accessTokenAndSecret = client.getAccessToken();
		
		this.ACCESS_TOKEN = accessTokenAndSecret[0]
		this.ACCESS_TOKEN_SECRET = accessTokenAndSecret[1]
		
	}
	
	def boolean isUserLogedIn(){
		if(this.ACCESS_TOKEN && this.ACCESS_TOKEN_SECRET){
			return true
		}
		return false
	}
	
	
	
	/**
	 * tweets a message to all followers
	 * 
	 * @param username the Username
	 * @param message the message
	 * 
	 */
	def sendMessage(String m){  
		
		this.client = new OAuthSignpostClient(this.CONSUMER_KEY, this.CONSUMER_SECRET, this.ACCESS_TOKEN, this.ACCESS_TOKEN_SECRET)
		
		this.jtwit = new Twitter(this.UNAME, client)
		this.jtwit.setStatus(m)
	}
	
	/**Returns the 20 most recent statuses posted in the last 24 hours from the authenticating user. 
	 * 
	 * @return List The List of statuses
	 */
	def List getStatuses(){
		this.client = new OAuthSignpostClient(this.CONSUMER_KEY, this.CONSUMER_SECRET, this.ACCESS_TOKEN, this.ACCESS_TOKEN_SECRET)
		this.jtwit = new Twitter(this.UNAME, client)
		this.jtwit.getUserTimeline()	
	}
	
}
