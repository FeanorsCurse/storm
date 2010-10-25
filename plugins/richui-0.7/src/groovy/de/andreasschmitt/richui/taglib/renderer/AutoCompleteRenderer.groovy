package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class AutoCompleteRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String resultId = "a" + RenderUtils.getUniqueId()
		
		//Default attribute initialization
		if(!attrs?.id){
			attrs.id = attrs.name
		}
		
		if(!attrs?.yuiVariableName){
			attrs.yuiVariableName = "autoComplete"
		}
			
		if(!attrs."class"){
			attrs."class" = ""	
		}
			
		if(!attrs.style){
			attrs.style = ""	
		}
		
		if(!attrs?.shadow){
			attrs.shadow = false			
		} 
		else {
			attrs.shadow = attrs.shadow == "true" ? true : false
		}
		
		if(!attrs?.minQueryLength){
			attrs.minQueryLength = 0
		}
		
		if(!attrs?.queryDelay){
			attrs.queryDelay = 0
		}
			
		if(!attrs?.value){
			attrs.value = ""
		}
		
		if(!attrs?.title){
            attrs.title = ""
        }
		
		//Internal or legacy attributes
		Map internalAttributes = [id: true, controller: true, action: true, name: true, value: true, "class": true, style: true, title: true, yuiVariableName: true, onItemSelect: true]
		
		//YUI Data source configuration attributes
		Map dataSourceAttributes = [scriptQueryAppend: null, scriptQueryParam: null, 
		                            responseType: "YAHOO.util.XHRDataSource.TYPE_XML", 
		                            responseSchema: "{\n resultNode : \"result\", \n fields : [\n { key: \"name\" }, \n { key: \"id\" }\n]\n};\n"]
		
		//YUI configuration attributes
		Map configAttributes = [queryDelay: "0", prehighlightClassName: "yui-ac-prehighlight", useShadow: "false", 
			                              minQueryLength: "0", delimChar: null, typeAhead: "false", 
			                              forceSelection: "false", maxResultsDisplayed: "10"]		
		
		//Default HTML attributes
		Map htmlAttributes = ["class": "${attrs?.'class'}", style: "${attrs?.style}", 
		                      type: "text", id: "${attrs?.id}", name: "${attrs?.name}", 
		                      value: "${attrs?.value}", title: "${attrs?.title}"]
			
		//Add additional attributes
		attrs.each { key, value ->
			if(key.startsWith("dataSource:")){
				dataSourceAttributes[key.replace("dataSource:", "")] = value
			}
			else if(key.startsWith("html:")){
				htmlAttributes[key.replace("html:", "")] = value
			}
			else {
				configAttributes[key] = value
			}
		}
		
		//Attribute transformer mapping for certain attributes
		AttributeTransformer attributeTransformer = new AttributeTransformer()
		attributeTransformer.registerTransformer("delimChar", AttributeTransformer.stringTransformer)
		attributeTransformer.registerTransformer("scriptQueryAppend", AttributeTransformer.stringTransformer)
		attributeTransformer.registerTransformer("scriptQueryParam", AttributeTransformer.stringTransformer)
		attributeTransformer.registerTransformer("prehighlightClassName", AttributeTransformer.stringTransformer)
		
		builder."div"(""){
			//Input element with HTML attributes
			input(htmlAttributes)
			"div"("class": "searchcontainer yui-skin-sam", id: resultId, ""){}
				
			script(type: "text/javascript"){				
				builder.yieldUnescaped "	var autoCompleteDataSource = new YAHOO.util.XHRDataSource(\"${attrs?.action}\");\n" 
				
				//DataSource attributes
				dataSourceAttributes.each { key, value ->
					if(!internalAttributes.containsKey(key) && value) {
						builder.yieldUnescaped "	autoCompleteDataSource.${key} = ${attributeTransformer.transform(key, value)};\n"
					}
				}
				
				builder.yieldUnescaped "	${attrs.yuiVariableName} = new YAHOO.widget.AutoComplete('${attrs?.id}','${resultId}', autoCompleteDataSource);\n"
				
				//Config attributes
				configAttributes.each { key, value ->
					if(!internalAttributes.containsKey(key) && value != null) {
						builder.yieldUnescaped "	${attrs.yuiVariableName}.${key} = ${attributeTransformer.transform(key, value)};\n"
					}
				}
				
				//OnItemSelect JavaScript handler
				if(attrs?.onItemSelect){
					builder.yieldUnescaped "	var itemSelectHandler = function(sType, args) {\n"
					builder.yieldUnescaped "		var autoCompleteInstance = args[0];\n"
					builder.yieldUnescaped "		var selectedItem = args[1];\n"
					builder.yieldUnescaped "		var data = args[2];\n"
					builder.yieldUnescaped "		var id = data[1];\n"
					builder.yieldUnescaped "		${attrs?.onItemSelect}"
					builder.yieldUnescaped "	};\n"
					builder.yieldUnescaped "	${attrs.yuiVariableName}.itemSelectEvent.subscribe(itemSelectHandler);\n"
				}
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		//Switch between local and remote JavaScript and CSS files 
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/autocomplete.css")
				css.name = "${resourcePath}/css/autocomplete.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/autocomplete/assets/skins/sam/autocomplete.css")
			css.name = "${yuiResourcePath}/autocomplete/assets/skins/sam/autocomplete.css"
		}
		
		resources.add(css)
		
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)

		
		// Yahoo connection min
		Resource yahooConnectionMin = new Resource()
		yahooConnectionMin.name = "${yuiResourcePath}/connection/connection-min.js"
		
		def yahooConnectionMinBuilder = yahooConnectionMin.getBuilder()
		yahooConnectionMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/connection/connection-min.js", "")
		
		resources.add(yahooConnectionMin)
		
		
		// Yahoo data source min
		Resource yahooDataSourceMin = new Resource()
		yahooDataSourceMin.name = "${yuiResourcePath}/datasource/datasource-min.js"
		
		def yahooDataSourceMinBuilder = yahooDataSourceMin.getBuilder()
		yahooDataSourceMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/datasource/datasource-min.js", "")
		
		resources.add(yahooDataSourceMin)


		// Yahoo animation min
		Resource yahooAnimationMin = new Resource()
		yahooAnimationMin.name = "${yuiResourcePath}/animation/animation-min.js"
		
		def yahooAnimationMinBuilder = yahooAnimationMin.getBuilder()
		yahooAnimationMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/animation/animation-min.js", "")
		
		resources.add(yahooAnimationMin)
		
		
		// Yahoo auto complete min
		Resource yahooAutoCompleteMin = new Resource()
		yahooAutoCompleteMin.name = "${yuiResourcePath}/autocomplete/autocomplete-min.js"
		
		def yahooAutoCompleteMinBuilder = yahooAutoCompleteMin.getBuilder()
		yahooAutoCompleteMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/autocomplete/autocomplete-min.js", "")
		
		resources.add(yahooAutoCompleteMin)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- AutoComplete -->"
		
		//Switch between local and remote JavaScript and CSS files 
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/autocomplete.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/autocomplete/assets/skins/sam/autocomplete.css")
		}

		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/datasource/datasource-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/autocomplete/autocomplete-min.js", "")
	}
}