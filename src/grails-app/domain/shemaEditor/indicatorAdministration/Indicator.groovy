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
 
package shemaEditor.indicatorAdministration

import ReportEditorMain.ArticleEditor.Article;
import shemaEditor.chart.Chart;
import shemaEditor.shemaAdministration.TNode;
import systemadministration.externalsystems.Bapi;
import systemadministration.externalsystems.SQLStatement;
import systemadministration.usermanagement.User;


class Indicator {
	static searchable = true //to searching in indicator-administration
    String name
    
    String shortName//e.g. EN1
    String description//a short description/summary of all information this indicator should provide
    String compilation//how to compilate indicator-values
    String reference//a reference for this indicator (e.g. link, text, see...)
    String relevance//if the indicator is relevant or not. 
    String documentation//how to document this indicator
    String definition//definitions of words included in the indicator-definition-attributes which should be define 
    
    // true, if this is an indicator, false otherwise
    boolean indicator
    //true if this is an indicator which was created by an Author, false otherwise
    boolean fromAuthor
    Date creationDate
    //Value is null during shema prozess.
    long value
    //e.g. kg, g,  EUR
    String unit
    Category category
    Date lastModifiedDate
    User creator
    User lastModifiedBy
    Bapi bapi
    SQLStatement sqlStatement
    Chart defaultChart
    List indicatorGroups

    
    

    //to render indicator.name instead of indicator.id
    String toString(){
        return name
    }
   
    static hasMany=[article:Article, nodes:TNode]
      
    /**
     * The owning side of the relationship, in this case TNode and Article, takes responsibility for persisting the relationship and is the only side that can cascade saves across.
     */
    static belongsTo = [Article, TNode]
    
    static constraints = {//TODO more constraints
    	name(unique:true, size:3..100, blank:false)
    	defaultChart(nullable:true)
    	description(blank:false,size:0..100000)
    	shortName(blank:false,size:0..10)
    	unit(size:0..10)
    	sqlStatement(nullable:true)
    	bapi(nullable:true)
    	compilation(nullable:true,size:0..1000000)
    	reference(nullable:true,size:0..1000000)
    	relevance(nullable:true,size:0..1000000)
    	documentation(nullable:true,size:0..1000000)
    	definition(nullable:true,size:0..1000000)
    	lastModifiedBy(nullable:true)
    	creator(nullable:true)
    	
    }
}
