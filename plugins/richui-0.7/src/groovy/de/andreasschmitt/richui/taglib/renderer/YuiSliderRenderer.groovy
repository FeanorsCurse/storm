package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class YuiSliderRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {		 
		String valueId = "s" + RenderUtils.getUniqueId()
		String inputId = "i" + RenderUtils.getUniqueId()
		String inputName = "silder"
		String value = "0"
		
		if(attrs?.valueId){
			valueId = attrs.valueId
		}
		
		if(attrs?.inputId){
			inputId = attrs.inputId
		}
		
		if(attrs?.inputName){
			inputName = attrs.inputName
		}
		
		if(attrs?.value){
			value = attrs.value
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "var slider = new Slider();\n"
			
			if(attrs?.scaleFactor){
				builder.yieldUnescaped "slider.setScaleFactor(${attrs.scaleFactor});\n"
			}
			
			if(attrs?.tickSize){
				builder.yieldUnescaped "slider.setTickSize(${attrs.tickSize});\n"				
			}
			
			builder.yieldUnescaped "slider.setValueId(\"${valueId}\");\n"				
			builder.yieldUnescaped "slider.setInputId(\"${inputId}\");\n"
			
			builder.yieldUnescaped "slider.init();\n"	
		}
		
		builder."div"(id: "slider-bg-horizontal", tabindex: "-1", title: "Slider"){
			"div"(id: "slider-thumb"){
				 img(src: "http://developer.yahoo.com/yui/examples/slider/assets/thumb-n.gif")
			}
		}
		
		builder.input(autocomplete: "off", id: inputId, name: inputName, type: "text", value: value, "size": "4", maxlength: "4")
		builder.span(id: valueId, "")
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// CSS
		Resource css = new Resource()
		css.name = "slider.css"
		
		def cssBuilder = css.getBuilder()
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/slider.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/slider.css")
		}
		
		resources.add(css)
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "yui:yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)

		// Yahoo animation min
		Resource yahooAnimationMin = new Resource()
		yahooAnimationMin.name = "yui:animation-min.js"
		
		def yahooAnimationMinBuilder = yahooAnimationMin.getBuilder()
		yahooAnimationMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/animation/animation-min.js", "")
		
		resources.add(yahooAnimationMin)
		
		// Yahoo drag drop min
		Resource yahooDragDropMin = new Resource()
		yahooDragDropMin.name = "yui:dragdrop-min.js"
		
		def yahooDragDropMinBuilder = yahooDragDropMin.getBuilder()
		yahooDragDropMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/dragdrop/dragdrop-min.js", "")
		
		resources.add(yahooDragDropMin)

		// Yahoo slider min
		Resource yahooSliderMin = new Resource()
		yahooSliderMin.name = "yui:slider-min.js"
		
		def yahooSliderMinBuilder = yahooSliderMin.getBuilder()
		yahooSliderMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/slider/slider-min.js", "")
		
		resources.add(yahooSliderMin)		
		
		// Slider js
		Resource sliderJavaScript = new Resource()
		sliderJavaScript.name = "yui:slider.js"
		
		def sliderJavaScriptBuilder = sliderJavaScript.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${resourcePath}/js/slider/slider.js", "")
		
		resources.add(sliderJavaScript)		
				
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Slider -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/slider.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/slider.css")
		}
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/dragdrop/dragdrop-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/slider/slider-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/slider/slider.js", "")
	}
}