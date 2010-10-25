package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/**
 * @author Andreas Schmitt
 *
 */
public class RunwayFlowRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {		
		//Seems to be a bug in protoflow
		String id = "flow" + RenderUtils.getUniqueId()
		
		if(attrs?.id){
			id = attrs.id
		}
		
		builder."div"(id: id, style: "height: 512px;"){
			builder.yieldUnescaped "${body.call()}"
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var records = [\n"    
			if(attrs?.images){
				int i = 0
				attrs.images.each { image ->
					if(i > 0){
						builder.yieldUnescaped ",\n"
					}
					builder.yieldUnescaped "	{	image: '${image?.url}',\n"
					builder.yieldUnescaped "		title: '${image?.title}',\n"
					builder.yieldUnescaped "		subtitle: '${image?.subtitle}'\n"
					builder.yieldUnescaped "	}\n"
					
					i += 1
				}
			}
			
			builder.yieldUnescaped "	];\n"
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "    initRunwayFlow('${id}');\n"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// Runway api 
		Resource runwayApi = new Resource()
		runwayApi.name = "http://api.simile-widgets.org/runway/1.0/runway-api.js"
		
		def runwayApiBuilder = runwayApi.getBuilder()
		runwayApiBuilder.script(type: "text/javascript", src: "http://api.simile-widgets.org/runway/1.0/runway-api.js", "")
		
		resources.add(runwayApi)
		
		
		// Java script 
		Resource javaScript = new Resource()
		javaScript.name = "runway:configuration"
		
		def javaScriptBuilder = javaScript.getBuilder()		
		javaScriptBuilder.script(type: "text/javascript"){
			javaScriptBuilder.yieldUnescaped "    var widget;\n"
			javaScriptBuilder.yieldUnescaped "    function initRunwayFlow(divId) {\n"
			javaScriptBuilder.yieldUnescaped "		widget = Runway.createOrShowInstaller(\n"
			javaScriptBuilder.yieldUnescaped "	    	document.getElementById(divId),\n"
			javaScriptBuilder.yieldUnescaped "	    	{\n"
			if(attrs?.slideSize){
				javaScriptBuilder.yieldUnescaped "	    		slideSize: 512,\n"	
			}
			javaScriptBuilder.yieldUnescaped "	        	// backgroundColorTop: '#fff',\n"
			javaScriptBuilder.yieldUnescaped "	               								\n"
			javaScriptBuilder.yieldUnescaped "	       		// event handlers\n"
			javaScriptBuilder.yieldUnescaped "	        	onReady: function() {\n"
			javaScriptBuilder.yieldUnescaped "	        		widget.setRecords(records);\n"
			javaScriptBuilder.yieldUnescaped "	            	widget.select(7);\n"
			if(attrs?.theme){
				javaScriptBuilder.yieldUnescaped "	            	widget.setThemeName('${attrs.theme}');\n"
			}
			javaScriptBuilder.yieldUnescaped "	        	},\n"
			javaScriptBuilder.yieldUnescaped "	               								\n"
			javaScriptBuilder.yieldUnescaped "	        	onSelect: function(index, id) {\n"
			javaScriptBuilder.yieldUnescaped "	        		var record = records[index];\n"
			javaScriptBuilder.yieldUnescaped "	            	document.getElementById('selected-slide').innerHTML = record.title;\n"
			javaScriptBuilder.yieldUnescaped "	        	}\n"
			javaScriptBuilder.yieldUnescaped "	 		}\n"
			javaScriptBuilder.yieldUnescaped "	   );\n"
			javaScriptBuilder.yieldUnescaped "	}\n"
		}
		
		resources.add(javaScript)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Flow -->"
		
		builder.script(type: "text/javascript", src: "http://api.simile-widgets.org/runway/1.0/runway-api.js", "")
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "    var widget;\n"
			builder.yieldUnescaped "    function initRunwayFlow(divId) {\n"
			builder.yieldUnescaped "		widget = Runway.createOrShowInstaller(\n"
			builder.yieldUnescaped "	    	document.getElementById(divId),\n"
			builder.yieldUnescaped "	    	{\n"
			if(attrs?.slideSize){
				builder.yieldUnescaped "	    		slideSize: 512,\n"	
			}
			builder.yieldUnescaped "	        	// backgroundColorTop: '#fff',\n"
			builder.yieldUnescaped "	               								\n"
			builder.yieldUnescaped "	       		// event handlers\n"
			builder.yieldUnescaped "	        	onReady: function() {\n"
			builder.yieldUnescaped "	        		widget.setRecords(records);\n"
			builder.yieldUnescaped "	            	widget.select(7);\n"
			if(attrs?.theme){
				builder.yieldUnescaped "	            	widget.setThemeName('${attrs.theme}');\n"
			}
			builder.yieldUnescaped "	        	},\n"
			builder.yieldUnescaped "	               								\n"
			builder.yieldUnescaped "	        	onSelect: function(index, id) {\n"
			builder.yieldUnescaped "	        		var record = records[index];\n"
			builder.yieldUnescaped "	            	document.getElementById('selected-slide').innerHTML = record.title;\n"
			builder.yieldUnescaped "	        	}\n"
			builder.yieldUnescaped "	 		}\n"
			builder.yieldUnescaped "	   );\n"
			builder.yieldUnescaped "	}\n"
		}
	}

}
