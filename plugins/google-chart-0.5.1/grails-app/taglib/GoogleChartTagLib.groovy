/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * @author James Williams
 */
class GoogleChartTagLib {
	static apiString = "http://chart.apis.google.com/chart?"

	def baseChart(attrs, type) { 
		def baseString = ''
		baseString += '<img src='
		baseString += '\"'+apiString
		if (attrs.size != null)
			baseString += 'chs='+attrs.size[0]+'x'+attrs.size[1]
		else baseString += 'chs=400x200'
		
		def minValue = attrs.minValue ?: 0
        def maxValue = attrs.maxValue ?: null

		if (!type.equals('qr') && attrs.dataType == null || attrs.dataType == 'simple') {
			baseString += '&chd=s:' + chartDataEncoding(attrs.data, minValue, maxValue)
		} else if (attrs.dataType == 'text') {
            if (attrs.scale != null) {
            		//Independent min/max for each dataset
            		//only valid for text encoding
            		baseString += '&chds=' + attrs.scale.collect{it}.join(',')
            }
            if (type == 'gmeter') {
            	baseString += '&chd=t:' + chartDataEncoding(attrs.data, 0, 100, textChartDataValues)
            	
            } else {
            	if (attrs.data[0] instanceof ArrayList) {
            		baseString += '&chd=t:'+ attrs.data.collect{it.collect{it}.join(',')}.join('|')
            	}
            	else baseString += '&chd=t:' + attrs.data.collect {it}.join(',')
            }
		} else if (attrs.dataType == 'extended') {
			baseString += '&chd=e:' + chartDataEncoding(attrs.data, minValue, maxValue, extendedChartDataValues)
		}
		
		if (attrs.axes != null) {
			baseString += '&chxt=' + attrs.axes
		}
		
		// takes a map in the form
		// [0:[Jan,Feb,Mar], 1:[0,100]...]
		if (attrs.axesLabels != null) {
			baseString += '&chxl='
			baseString += processAxesLabels(attrs.axesLabels)
		}
		
		// takes a map in the form
		// [0:[Jan,Feb,Mar], 1:[0,100]...]
		if (attrs.axesPositions != null) {
			baseString += '&chxp='
			baseString += processAxesPositions(attrs.axesPositions)
		}
		
		// takes a map in the form
		// [0:[Jan,Feb,Mar], 1:[0,100]...]
		if (attrs.axesRanges != null) {
			baseString += '&chxr='
			baseString += processAxesPositions(attrs.axesRanges)
		}
		
		// takes a map in the form
		// [0:[Jan,Feb,Mar], 1:[0,100]...]
		if (attrs.axesStyles != null) {
			baseString += '&chxs='
			baseString += processAxesPositions(attrs.axesStyles)
		}
		
		if (attrs.colors != null) {
			def colors = ''
			if (attrs.colors instanceof ArrayList) 
			    colors = attrs.colors.collect{it}.join(',') 
            else colors = attrs.colors
            baseString += '&chco='+colors
		}
		
		if (attrs.fill != null) {
			baseString += '&chf='
			if (attrs.fill instanceof ArrayList)
				baseString += attrs.fill[0] + '|' + attrs.fill[1]
			else baseString += attrs.fill
		}
		
		// takes an ArrayList of the titles
		// Valid for all types except pie charts
		if (attrs.legend != null) {
			baseString += '&chdl='
			def legendLabels = ''
			for(label in attrs.legend) {
				legendLabels += label + '|'
			}
			baseString += legendLabels.substring(0,legendLabels.length()-1)
		}
		// chart title and attributes
		if (attrs.title != null)
			baseString += '&chtt='+attrs.title.encodeAsHTML()
		// takes an array of length 2 [color, font size]
		if (attrs.titleAttrs != null) {
			baseString += "&chts=${attrs.titleAttrs[0]},${attrs.titleAttrs[1]}"
		}
		return baseString
	}

	def processAxesLabels(params) {
		def labelString = ''
		for (axis in params.keySet()) {
			labelString += "${axis}:|"
			for (value in params[axis]) {
				labelString += "${value}|"
			}
		}
		return labelString.substring(0,labelString.length()-1)
	}
	
	def processAxesPositions(params) {
		def posString = ''
		
		for (axis in params.keySet()) {
			posString += "${axis},"
			posString += params[axis].collect{it}.join(',')
			posString += '|'
		}
		return posString.substring(0,posString.length()-1)
	}

	// Attributes:
	// size	- ArrayList
	// type - simple, text, or extended
	def pieChart = { attrs, body ->
		out << baseChart(attrs, 'pie')
		
		if (attrs.labels != null) {
			out << '&chl='+attrs.labels.collect{it}.join('|')
		}
		
		if (attrs.type != '3d')
			out << '&cht=p'
		else out << '&cht=p3'
		
		out << '\" />'
	}
	
	def lineChart = { attrs, body ->
		out << baseChart(attrs,'line')
		
		//chart line styles
		// assumes an ArrayList of ArrayLists indicating the 
		// style for each line the outer collect joins the 
		// results of the inner collect with | to separate each
		// style whereas the inner one puts commas between the 
		// individual elements of the style		
		if (attrs.lineStyles != null) {
			out << '&chls='
	
			out << attrs.lineStyles.collect{
					//iterate over each element in the
					it.collect{it}.join(',')
					}.join('|')
		}
		
		//grid styles
		if (attrs.gridLines != null) {
			out << '&chg='
			out << attrs.gridLines
		}
		out << parseShapeRangeFill(attrs)		
		
		if (attrs.type == 'xy' || attrs.type == null)
			out << '&cht=lxy'
		else if (attrs.type == 'lc')
			out << '&cht=lc'
		else if (attrs.type == 'ls')
			out << '&cht=ls'
		//else out << '&cht=lxy'
		
		out << '\" />'
	}
	
	String parseShapeRangeFill(params) {
		def text = ''
		if (params.shapeRangeFill != null) {
			text += '&chm='
			text += params.shapeRangeFill.collect{
					//iterate over each element in the
					it.collect{it}.join(',')
					}.join('|')
		}
		return text
	}
	
	// You must specify the type for a bar chart, there
	// are just too many combinations
	def barChart = { attrs, body ->
		out << baseChart(attrs,'bar')
		
		//expects a List
		if (attrs.zeroLine != null) {
			out << '&chp='+attrs.zeroLine.collect{it}.join(',')
		}
		out << '&cht='+attrs.type
		
		out << '\" />'
	}
	
	def vennDiagram = { attrs, body ->
		out << baseChart(attrs,'venn')

		out << '&cht=v'
		
		out << '\" />'
	}
	
	def scatterPlot = { attrs, body ->
		out << baseChart(attrs,'scatter')
		out << '&cht=s'
		out << parseShapeRangeFill(attrs)
		//grid lines
		if (attrs.gridLines != null) {
			out << '&chg='
			out << attrs.gridLines
		}
		
		out << '\" />'
	}
	
	def map = { attrs, body ->
		out << baseChart(attrs,'map')
		out << '&cht=t'
		out << '&chtm='+attrs.mapArea
		// process map list
		out << '&chld='+attrs.countries.collect{it}.join("")
		
		out << '\" />'
	}
	
	def gmeter = { attrs, body ->
		out << baseChart(attrs,'gmeter')
		if (attrs.labels != null) {
			out << '&chl='+attrs.labels.collect{it}.join('|')
		}
		out << '&cht=gom'
		
		out << '\" />'
	}
	
	def radar = { attrs, body ->
		out << baseChart(attrs,'radar')
		
		if (attrs.lineStyles != null) {
			out << '&chls='
	
			out << attrs.lineStyles.collect{
					//iterate over each element in the
					it.collect{it}.join(',')
					}.join('|')
		}
		
		//grid styles
		if (attrs.gridLines != null) {
			out << '&chg='
			out << attrs.gridLines
		}
		out << parseShapeRangeFill(attrs)		
		
		out << '&cht=r'
		
		out << '\" />'
	}
	
	//QR codes
	// takes a string to encode - text
	// an encoding - encoding
	// and an error correction pair
	// size is also a valid attribute pair
	def qr = { attrs, body ->
	   out << baseChart(attrs,'qr') 
	   if (attrs.text != null) {
          out << '&chl='+attrs.labels.collect{it}.join('')
	   }
	   //default encoding is UTF-8 if none is set
	   if (attrs.encoding != null)
          out << '&choe='+attrs.encoding
       else out << '&choe=UTF-8'
       
       //error correction
       if (attrs.errorCorrection != null) {
          out << '&chld='+attrs.errorCorrection.collect{it}.join('|')
       }
       out << '&cht=qr'
       out << '\" />'
	}

	def simpleChartDataValues = { dataSet, minValue, maxValue ->
        return dataSet.collect { currentValue ->
            if (currentValue != null && currentValue >= minValue && currentValue <= maxValue) {
                def key = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
				int divBy = maxValue - minValue
		        if (divBy == null || divBy == 0 )
		          divBy = 1                
				return key.charAt(Math.round(61 * ((currentValue - minValue) / divBy)).intValue())
            }
            return "_"
        }.join("")
    }

    def textChartDataValues = { dataSet, minValue, maxValue ->
        return dataSet.collect { currentValue ->
            if (currentValue != null && currentValue >= minValue && currentValue <= maxValue) {
                def key = 0.0..100.0
				int divBy = maxValue - minValue
		        if (divBy == null || divBy == 0 )
		          divBy = 1 
                return key.get(Math.round(100 * ((currentValue - minValue) / divBy)).intValue())
            } else {
                return "-1"
            }
        }.join(",")
    }

    def extendedChartDataValues = { dataSet, minValue, maxValue ->
        def chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-."
        def keys = chars.collect { first -> chars.collect { second ->"$first$second"} }?.flatten()

        return dataSet.collect { currentValue ->
			int divBy = maxValue - minValue
		    if (divBy == null || divBy == 0 )
		        divBy = 1 
            if (currentValue != null && currentValue >= minValue && currentValue <= maxValue) {
                return keys.get(Math.round(4095 * ((currentValue - minValue) / divBy)).intValue())
            }
            return "_"
        }.join("")
    }

	def chartDataEncoding = { dataSet, minValue = 0, maxValue = 0, dataValueClosure = simpleChartDataValues ->
        maxValue = maxValue ?: dataSet?.flatten()?.max()
        if (dataSet[0] instanceof List) {
            return dataSet.collect{ chartDataEncoding(it, minValue, maxValue, dataValueClosure) }.join(",")
        } else {
            return dataValueClosure.call(dataSet, minValue, maxValue)
        }
    }
}
