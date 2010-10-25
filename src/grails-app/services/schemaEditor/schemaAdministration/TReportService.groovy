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
 
package schemaEditor.schemaAdministration

import java.util.ArrayList;
import java.util.List;

import ReportEditorMain.ReportEditor.Report;

import shemaEditor.indicatorAdministration.Indicator;
import shemaEditor.shemaAdministration.TNode;
import shemaEditor.shemaAdministration.TReport;

class TReportService {
	
	static transactional = true
	def indicatorService
	
	
	/**
	 * Returns a List of all Nodes which are children of node
	 * @param node the rootNode
	 * @param NodeList an empty list
	 * @return List of all children
	 */
	def List getAllNodes(TNode root, List NodeList){
		
		root.children.each{
			NodeList.add(it)
			getAllNodes(it, NodeList)
			
		}	
		
		return NodeList
	}
	
	/**
	 * Returns a List of all Nodes which are children of node
	 * @param node the rootNode
	 * @param NodeList an empty list
	 * @return List of all children
	 */
	def List getAllNodesExceptOf(TNode root,TNode selectedNode, List NodeList){
		
		root.children.each{
			if(it.id==selectedNode.id){
				
			}else{
				println(it)
				NodeList.add(it)
			}
			getAllNodes(it, NodeList)
			
		}	
		
		return NodeList
	}
	
	def List listSchemaIndicators(TReport treport){
		
		def IndicatorList = new ArrayList<Indicator>()
		def NodeList = new ArrayList<TNode>()
		
		def TNodeList = getAllNodes(treport.getRoot(),NodeList)
		TNodeList.each{
			IndicatorList.addAll(it.listIndicators())
		}
		return IndicatorList
	}
	
	def List listObligatorySchemaIndicators(TReport treport){
		
		def IndicatorList = new ArrayList<Indicator>()
		def NodeList = new ArrayList<TNode>()
		
		def TNodeList = getAllNodes(treport.getRoot(),NodeList)
		
		TNodeList.each{tnode->	
			tnode.listIndicators().each{indicator->		
				if(indicatorService.isIndicatorObligatory(tnode,indicator)){
					IndicatorList.add(indicator)
				}			
			}	
			
		}		
		return IndicatorList
	}
	
	
	def List listOptionalSchemaIndicators(TReport treport){
		
		def IndicatorList = new ArrayList<Indicator>()
		def NodeList = new ArrayList<TNode>()
		
		def TNodeList = getAllNodes(treport.getRoot(),NodeList)
		
		TNodeList.each{tnode->	
			tnode.listIndicators().each{indicator->		
				if(indicatorService.isIndicatorOptional(tnode,indicator)){
					IndicatorList.add(indicator)
				}			
			}	
			
		}		
		return IndicatorList
	}
	
	/*
	 * returns true if a specific schema is used in report,
	 * returns false otherwise
	 * 	
	 */
	def boolean isSchemaUsedInReport(TReport treport){
		//lists all reports
		boolean inUse = false
		
		Report.list().each{
			if(treport.id==it.treport.id){
				inUse= true
			}
		}
		return inUse
	}
	
}
