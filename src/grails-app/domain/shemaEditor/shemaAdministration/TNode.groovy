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
 
package shemaEditor.shemaAdministration
import java.util.List;


import systemadministration.usermanagement.*
import shemaEditor.indicatorAdministration.*

class TNode {
	
	//TODO Logik nach Beta aus der Domainclass nehmen. 
	def TNodeIndicatorService
	
	String name
	String description
	long parent_id
	
	User creator
	
	Date creationDate
	
	//ID of last modifier (User)
	User lastModifiedBy
	Date lastModified
	
	List children
	
	//true if this tnode is the title node 
	boolean title
	
	
	//to render name instead of ID
	String toString(){
		return name
	}
	
	static constraints = {
		name(size:3..100, blank:false)
		description(blank:false)
	}
	
	
	static hasMany =[children:TNode, indicators:shemaEditor.indicatorAdministration.Indicator]
	
	
	
	
	/**Returns all children of this tnode. 
	 * 
	 * @return List list
	 */
	def List listChildren (){
		
		
		return getChildren()
	}
	
	/**Returns all children of this tnode. 
	 * 
	 * @return List list
	 */
	def List listIndicators (){
		
		return TNodeIndicatorService.getIndicators(this)
	}
	
	
	/**Returns a copy of this TNode
	 * 
	 * @return TNode a copy of this TNode
	 */
	def TNode copy(){	
		
		TNode copy = new TNode(name:this.name, description:this.description, creator:this.creator, creationDate:new Date(), lastModifiedBy:this.lastModifiedBy, lastModified:new Date())	
		if(copy.save(flush:true)){
			//TODO IUsage ist immer 0. Es sollte eine Liste mit allen iu mitgegeben werden. 
			TNodeIndicatorService.addAllIndicators(copy, listIndicators(), 0)
		}
		
		return copy
	}
	
	
	def addToIndicators(Indicator indicator, int i_usage){
		
		TNodeIndicatorService.addIndicator(this, indicator, i_usage)
	}
	
	def removeIndicator(Indicator indicator){	
		
		
		TNodeIndicatorService.removeIndicator(this, indicator)
		
	}
	
	def addAllToIndicators(List indicatorList, int i_usage){
		
		TNodeIndicatorService.addAllIndicators(this,  indicatorList, i_usage)
	}
	
	
	
}


