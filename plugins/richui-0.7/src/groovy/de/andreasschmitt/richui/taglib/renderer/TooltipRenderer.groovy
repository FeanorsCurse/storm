package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class TooltipRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped " var myTooltip = new YAHOO.widget.Tooltip(\"myTooltip\", { context:\"$attrs.id\" } );"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
		
		
		// Yahoo container min
		Resource yahooContainerMin = new Resource()
		yahooContainerMin.name = "${yuiResourcePath}/container/container-min.js"
		
		def yahooContainerMinBuilder = yahooContainerMin.getBuilder()
		yahooContainerMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/container/container-min.js", "")
		
		resources.add(yahooContainerMin)
		
		
		// Yahoo animation min
		Resource yahooAnimationMin = new Resource()
		yahooAnimationMin.name = "${yuiResourcePath}/animation/animation-min.js"
		
		def yahooAnimationMinBuilder = yahooAnimationMin.getBuilder()
		yahooAnimationMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/animation/animation-min.js", "")
		
		resources.add(yahooAnimationMin)

		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = yahooAnimationMin.getBuilder()
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", href: "${yuiResourcePath}/container/assets/container.css", "")
				css.name = "${yuiResourcePath}/container/assets/container.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", href: "${yuiResourcePath}/container/assets/container.css", "")	
			css.name = "${yuiResourcePath}/container/assets/container.css"
		}
		
		resources.add(css)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- Tooltip -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/container/container-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/animation/animation-min.js", "")		
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", href: "$yuiResourcePath/container/assets/container.css", "")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", href: "$yuiResourcePath/container/assets/container.css", "")	
		}
	}
}