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

import java.net.URL;
import java.text.DateFormat 
import java.text.SimpleDateFormat 

/**
 *The purpose of this tag library is to give access to certain common tags used in the STORM application.
 * <ul>
 * <li>feed: imports a feed with title, link, date, image and content</li>
 * <li>niceDate: displays a nice date, dependent on current locale</li>
 * <li>replaceHTML: removes html-comments from strings</li>
 * </ul>
 *@author Olaf Roeder, Gerrit, Edzard
 */
class GeneralTagLib {
	
	/**
	 * Imports a feed with titel, link, date, image and content
	 * usage in *.gsp: <g:feed url="http://example.com/rss" max="3"/>
	 *
	 * @param url: Url for the feed
	 * @param max: Maximum number of feeds shown
	 *
	 * @author: Gerrit
	 */
	def feed ={ attrs, body ->
		try{
			def feed = attrs["feed"]
			
			//Maximum number of shown entries
			def maxNumItems = 3
			if(feed.maxNumItems>0){
				maxNumItems = feed.maxNumItems
			}
			
			//Read Host from URL
			def url2 = new URL(feed.url).host
			out << "<h3>${message(code: 'feed.news.from', default: 'News from ')} "+url2+"</h3>"
			
			//Show in new window
			def window =""
			if(feed.showInNewWindow){
				window = " target='blank' "
			}
			
			
			//iterate through feed-entries
			int i = 0
			def entries=new XmlSlurper().parse(feed.url)
			for(entry in entries.channel.item){
				if(i<maxNumItems){
					Date date = null;
					
					out << "<p>"
					if(feed.showTitle)out << "<p><a "+window+" href='"+entry.link.text()+"'>"+entry.title.text()+"</a><br>"
					if(feed.showDate) out << entry.pubDate.text()
					if(feed.showAuthor) out << " "+entry.author.text()
					out << "</p>"
					out << "<table><tr>"
					if(entry.enclosure.@url!=""){
						if(feed.showDescription) out << "<td><a "+window+" href='"+entry.link.text()+"'><img src='"+entry.enclosure.@url+"'/></a></td>"
					}
					if(feed.maxDescriptionLength>0 && entry.description.text().toString().length()>feed.maxDescriptionLength){
						if(feed.maxDescriptionLength) out << "<td>"+entry.description.text().toString().substring(0, feed.maxDescriptionLength)+"...</td>"
					}else{
						if(feed.showDescription) out << "<td>"+entry.description.text()+"</td>"
					}
					out << "</tr></table>"
					i++
				}
			}
		}catch(Exception e){
			
		}
	}
	
	
	/**
	 *Displays a nice date, dependent on current locale,
	 *usage: <g:niceDate date="${classInstance.date}" /> where classInstance is a class containing a date variable.
	 *Currently only distinguishes between english and german.
	 *@param date="${classInstance.date}" where classInstance is a class containing a date variable
	 *@return String a formatted date string
	 */
	def niceDate = { attrs, body->
		def date = attrs["date"]
		def lang = ""+session["org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE"]
		//lang = lang.toLowerCase()
		def day = date.getDate()
		def month = date.getMonth()+1
		def year = date.getYear()+1900
		
		switch(lang.toLowerCase()){
			case ["de"]:// germany: day month year
			out << ""+day+"."+month+"."+year
			break
			
			case ["en"]: // united states: month day year
			out << ""+month+"/"+day+"/"+year
			break
			
			default:
			out << year+"-"+month+"-"+day
			break
		}
		
	}
	
	def replaceHTML = { attrs ->
		String text = attrs['text']
		if (attrs['lenght']!=null) {
			int lenght = Integer.parseInt(attrs['lenght'])
			if (lenght >text.toString().length()) {
				lenght = text.toString().length()
			}
			out << text.toString().substring(0, lenght).replaceAll(/<!--.*?-->/, '').replaceAll(/<.*?>/, '')
			
		}
		else {
			out << text.toString().replaceAll(/<!--.*?-->/, '').replaceAll(/<.*?>/, '')
		}
	}
}

