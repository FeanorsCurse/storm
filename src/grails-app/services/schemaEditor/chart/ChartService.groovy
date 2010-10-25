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
 
package schemaEditor.chart


import chart.GoogleChartBuilder;

import java.util.ArrayList;

import shemaEditor.chart.Chart;

class ChartService {
	
	
	static scope = "request"
	int fac =1
	def preColors = ['00FF00', '0000FF', 'FFFF00', '00FFFF', 'FF00FF', '99000', 'FF6600', '663399', '000000', 'FF0000']
	//def colorNames = ['schwarz', 'rot', 'grün', 'blau', 'gelb', 'türkis', 'rosa', 'dunkelrot', 'orange', 'lila' ]
	
	
	/**
	 * returns a List of 's' colors
	 * @param s The count of colors
	 * @return List of colors
	 */
	def List getColorSet(int s){
		ArrayList<String> colors = new ArrayList<String>()
		for(int i=0; i<s; i++){
			colors.add(preColors[i])
		}
		return colors
	}
	
	/**
	 * Returns the url (as String) to the specified google chart
	 * @param id 
	 * @return A String (the url)
	 */
	def String getChart(Chart chart, List dataSet, List labels, String title, String unit){
		//Scales the values to a multiple of 100
		
		dataSet = scaleDataSet(dataSet)
		
		//Select colors
		def colors = getColorSet(dataSet.size())
		
		//Generate result
		def result
		if(chart.id==1){
			result = getBarChart(dataSet, labels, colors, title,  unit)
		}else if(chart.id==2){
			result = getLineChart(dataSet, labels, colors, title,   unit)
		}else if(chart.id==3){
			result = getPieChart(dataSet, labels, colors, title)
		}else if(chart.id==4){
			result = get3DPieChart(dataSet, labels, colors, title)
		}
		return result
	}
	
	/** Scales a List of Values to a range of 0..100
	 * 
	 * @param dataSet 
	 * @return List dataSet
	 */
	def List scaleDataSet(List dataSet){
		this.fac =1
		println("original dataSet: "+dataSet)
		
		int maxval = (int)Collections.max( dataSet )
		
		if(maxval<100)
			return dataSet
		
		int maxval_copy = maxval
	
	
		while(maxval_copy>0){
			maxval_copy -= (maxval_copy%10)
			maxval_copy /=10
			fac *=10
		}	
		fac /=100
		for(int i =0 ;i<dataSet.size();i++){
			int newval =(int)dataSet.get(i)/fac	
			//Manipulate data if val=0 for a better graphical presentation
			if(newval==0)
				newval+=1
			dataSet.set(i, newval)
		}
		
		return dataSet
	}
	
	/*
	 * Creates a pie chart 
	 */
	def String getPieChart(List values, List labelsIn, List colorsIn, String title){
		
		def chart = new GoogleChartBuilder()
		def result = chart.pieChart{
			size(w:300, h:200)		
			data(encoding:'extended'){
				dataSet(values)
						
			}
			colors{
				
				colorsIn.each{
					color(it)
				}				
			}
			lineStyle(line1:[1,6,3])
			legend{ 
				
				labelsIn.each{
					label(it)
				}			
			}			
			backgrounds{
				background{ solid(color:'FFFFFF') }
				
			}
			
		}
		return result
	}
	
	/*
	 * Creates a line chart 
	 */
	def String getLineChart(List values, List labelsIn, List colorsIn, String headline, String unit){
		def chart = new GoogleChartBuilder()

		def result = chart.lineChart{
		 
		  size(w:430, h:250)
		  data(encoding:'text'){
			  dataSet(values)
		  }
		  colors{
			 color('00FF00')
		  }
		  grid(x:20, y:22, dash:1, space:1)
		  axis(left:[0,10,20,30,40,50,60,70,80,90,100], bottom:labelsIn)
		  backgrounds{
		    	  background{ 
		    		  solid(color:'FFFFFF') 
		    	  }   
		  }
  
		}
		return result
	}
	/*
	 * Creates a bar chart 
	 * The first paramter indicates the directionallity of the bars either horizontal or vertiacal
	 * The seocnd paramter indicates weather the bars are grouped or stacked.
	 */
	def String getBarChart(List values, List labelsIn, List colorsIn, String headline, String unit){
				
		
		def chart = new GoogleChartBuilder()
		def result = chart.barChart(['vertical', 'stacked']){
		
			barSize(witdth:25, space:2)
			size(w:430, h:200)		
			data(encoding:'text'){
				dataSet(values)
						
			}
			colors{	
				color('00FF00')
				color('0000FF')
			}
			legend{ 	
				labelsIn.each{
					label(it)
				}			
			}	
			axis(left:[0,10,20,30,40,50,60,70,80,90,100], bottom:labelsIn)

		}
	
		return replace(result.toString(), "chco","&chdl",",","|")	
	}
	/**
	 * Manipulates a substring of a string
	 * 
	 * @param String str Source string
	 * @param String startString left side of the substring
	 * @param String endString right side of the substring
	 * @param String regex chars to replace
	 * @param String replacement replacement
	 * @return String the manipulated String str
	 */
	def String replace(String str, String startString, String endString, String regex, String replacement){
		
		int startS = str.indexOf(startString)
		int endS = str.indexOf(endString)	
		
		String substring = str.substring(startS, endS)
		
		def mSubstring = substring.replaceAll(regex,replacement)
		
		str = str.replaceAll(substring, mSubstring)
		
		return str
	}
	
	/*
	 * Creates a 3D pie chart 
	 */
	def String get3DPieChart(List values, List labelsIn, List colorsIn, String titleIn){
		def chart = new GoogleChartBuilder()
		def result = chart.pie3DChart{
			size(w:350, h:200)		
			//title(color:'000000', size:16){row(titleIn)}
			data(encoding:'extended'){
				dataSet(values)
						
			}
			colors{
				
				colorsIn.each{
					color(it)
				}				
			}
			legend{ 	
				labelsIn.each{
					label(it)
				}			
			}	

			backgrounds{
				background{ solid(color:'FFFFFF') }
				
			}
		}
		return result
	}
	
}
