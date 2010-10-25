package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class TimelineRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String id = "t" + RenderUtils.getUniqueId()
			
		builder.div("class": attrs?.'class', style: attrs?.style, "id": id, ""){}
					
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var tl;\n"
			builder.yieldUnescaped "	function initTimeline() {\n"
			builder.yieldUnescaped "		var eventSource = new Timeline.DefaultEventSource();\n"
			builder.yieldUnescaped "		var bandInfos = [\n"
			builder.yieldUnescaped "		Timeline.createBandInfo({\n"
			builder.yieldUnescaped "			eventSource:    eventSource,\n"
				
			builder.yieldUnescaped "			date:           '$attrs.startDate',\n"
			
			if(attrs?.eventBandWidth){
				builder.yieldUnescaped "			width:          '${attrs.eventBandWidth}%', \n"
			}
			else {
				builder.yieldUnescaped "			width:          '70%', \n"	
			}
			
			Map intervalUnits = [day: "Timeline.DateTime.DAY", week: "Timeline.DateTime.WEEK", month: "Timeline.DateTime.MONTH",
			                     quarter: "Timeline.DateTime.QUARTER", year: "Timeline.DateTime.YEAR"]
			
			String eventIntervalUnit = ""
			if(attrs?.eventIntervalUnit && intervalUnits.containsKey(attrs.eventIntervalUnit.toLowerCase())){
				eventIntervalUnit = intervalUnits[attrs.eventIntervalUnit.toLowerCase()]
			}
			else {
				eventIntervalUnit = intervalUnits["month"]
			}
			
			builder.yieldUnescaped "			intervalUnit:   ${eventIntervalUnit}, \n"
			if(attrs?.eventIntervalPixels){
				builder.yieldUnescaped "			intervalPixels: ${attrs.eventIntervalPixels}\n"
			}
			else {
				builder.yieldUnescaped "			intervalPixels: 100\n"	
			}
			builder.yieldUnescaped "		}),\n"

			builder.yieldUnescaped "		Timeline.createBandInfo({\n"
			builder.yieldUnescaped "		    showEventText:  false,\n"
			builder.yieldUnescaped "			trackHeight:    0.5,\n"
			builder.yieldUnescaped "			trackGap:       0.2,\n"
			builder.yieldUnescaped "			eventSource:    eventSource,\n"
				
			builder.yieldUnescaped "			date:           '$attrs.startDate',\n"
			
			if(attrs?.legendBandWidth){
				builder.yieldUnescaped "			width:          '${attrs.legendBandWidth}%',\n"
			}
			else {
				builder.yieldUnescaped "			width:          '30%',\n" 	
			}
			
			String legendIntervalUnit = ""
			if(attrs?.legendIntervalUnit && intervalUnits.containsKey(attrs.legendIntervalUnit.toLowerCase())){
				legendIntervalUnit = intervalUnits[attrs.legendIntervalUnit.toLowerCase()]
			}
			else {
				legendIntervalUnit = intervalUnits["year"]
			}
			
			builder.yieldUnescaped "			intervalUnit:   ${legendIntervalUnit}, \n"
			
			if(attrs?.legendIntervalPixels){
				builder.yieldUnescaped "			intervalPixels: ${attrs.legendIntervalPixels}\n"
			}
			else {
				builder.yieldUnescaped "			intervalPixels: 200\n"	
			}
			
			builder.yieldUnescaped "		})\n"
			builder.yieldUnescaped "		];\n"

			builder.yieldUnescaped "		bandInfos[1].syncWith = 0;\n"
			builder.yieldUnescaped "		bandInfos[1].highlight = true;\n"
			
			//Bar highlighting
			if(attrs?.eventBandSpanHighlightDecorators || attrs?.eventBandPointHighlightDecorators || attrs?.legendBandSpanHighlightDecorators || attrs?.legendBandPointHighlightDecorators){
				builder.yieldUnescaped "	var theme = Timeline.ClassicTheme.create();\n"
				builder.yieldUnescaped "	theme.event.label.width = 250;\n"
				builder.yieldUnescaped "	theme.event.bubble.width = 250;\n"
				builder.yieldUnescaped "	theme.event.bubble.height = 200;\n"
				
				List decorators = [[spanHighlightDecorators: attrs?.eventBandSpanHighlightDecorators, pointHighlightDecorators: attrs?.eventBandPointHighlightDecorators], [spanHighlightDecorators: attrs?.legendBandSpanHighlightDecorators, pointHighlightDecorators: attrs?.legendBandPointHighlightDecorators]]
				
				decorators.eachWithIndex { value, index ->
					builder.yieldUnescaped "	bandInfos[${index}].decorators = [\n"
					if(value?.spanHighlightDecorators){
						value.spanHighlightDecorators.eachWithIndex { decorator, k ->
							builder.yieldUnescaped "		new Timeline.SpanHighlightDecorator({\n"
							
							String startDate = ""
							try {
								startDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(decorator?.startDate) + " GMT"
							}
							catch(Exception e){
								startDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(new Date()) + " GMT"
							}
							builder.yieldUnescaped "	        startDate:  '${startDate}',\n"
							
							String endDate = ""
							try {
								endDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(decorator?.endDate) + " GMT"
							}
							catch(Exception e){
								endDate = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(new Date()) + " GMT"
							}
							builder.yieldUnescaped "	        endDate:    '${endDate}',\n"
							
							builder.yieldUnescaped "	        color:      '${decorator?.color}',\n"
							builder.yieldUnescaped "	        opacity:    '${decorator?.opacity}',\n"
							builder.yieldUnescaped "	        startLabel: '${decorator?.startLabel}',\n"
							builder.yieldUnescaped "	        endLabel:   '${decorator?.endLabel}',\n"
							builder.yieldUnescaped "	        theme:      theme\n"
							
							if(k + 1 < value.spanHighlightDecorators.size() || value?.pointHighlightDecorators){
								builder.yieldUnescaped "	}),\n"
							}
							else {
								builder.yieldUnescaped "	})\n"
							}
						}
					}
					if(value?.pointHighlightDecorators){
						value.pointHighlightDecorators.eachWithIndex { decorator, k ->
							builder.yieldUnescaped "		new Timeline.PointHighlightDecorator({\n"
							
							String date = ""
							try {
								date = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(decorator?.date) + " GMT"
							}
							catch(Exception e){
								date = new SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US).format(new Date()) + " GMT"
							}
							builder.yieldUnescaped "	        date:  '${date}',\n"
							builder.yieldUnescaped "	        color:      '${decorator?.color}',\n"
							builder.yieldUnescaped "	        opacity:    '${decorator?.opacity}',\n"
							builder.yieldUnescaped "	        theme:      theme\n"
							
							if(k + 1 < value.pointHighlightDecorators.size()){
								builder.yieldUnescaped "	}),\n"
							}
							else {
								builder.yieldUnescaped "	})\n"
							}
						}
					}
					builder.yieldUnescaped "	];\n"
				}
			}
			
			builder.yieldUnescaped "		tl = Timeline.create(document.getElementById('$id'), bandInfos);\n"
				
			if(attrs?.datasource) {
				builder.yieldUnescaped "		tl.loadXML('$attrs.datasource', function(xml, url) { eventSource.loadXML(xml, url); });\n"
			}
			builder.yieldUnescaped "}\n"

			builder.yieldUnescaped "var resizeTimerID = null;\n"
			builder.yieldUnescaped "function onResize() {\n"
			builder.yieldUnescaped "	if (resizeTimerID == null) {\n"
			builder.yieldUnescaped "		resizeTimerID = window.setTimeout(function() {\n"
			builder.yieldUnescaped "		resizeTimerID = null;\n"
			builder.yieldUnescaped "		tl.layout();\n"
			builder.yieldUnescaped "	}, 500);\n"
			builder.yieldUnescaped "}\n"
			builder.yieldUnescaped "}\n"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// Simile ajax api 
		Resource simileAjaxApi = new Resource()
		simileAjaxApi.name = "${resourcePath}/js/simile/simile-ajax-api.js"
		
		def simileAjaxApiBuilder = simileAjaxApi.getBuilder()
		simileAjaxApiBuilder.script(type: "text/javascript", src: "${resourcePath}/js/simile/simile-ajax-api.js", "")
		
		resources.add(simileAjaxApi)
		
		
		// Timeline api 
		Resource timelineApi = new Resource()
		timelineApi.name = "${resourcePath}/js/timeline/timeline-api.js"
		
		def timelineApiBuilder = timelineApi.getBuilder()
		timelineApiBuilder.script(type: "text/javascript", src: "${resourcePath}/js/timeline/timeline-api.js", "")
		
		resources.add(timelineApi)

		
		// CSS 
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()
		if(attrs?.skin){
			if(attrs.skin == "default"){
				
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			
		}
		
		resources.add(css)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {		
		builder.script(type: "text/javascript", src: "$resourcePath/js/simile/simile-ajax-api.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/timeline/timeline-api.js", "")
	
		if(attrs?.skin){
			if(attrs.skin == "default"){
				
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			
		}
	}

}