package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class AccordionRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		builder."dl"("class": "accordion-menu ${attrs?.class}", style: "${attrs?.style}"){
			builder.yieldUnescaped "${body.call()}"	
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/accordion.css")
				css.name = "${resourcePath}/css/accordion.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/accordion.css")
			css.name = "${resourcePath}/css/accordion.css"
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
		

		// Yahoo animation min
		Resource yahooAnimationMin = new Resource()
		yahooAnimationMin.name = "${yuiResourcePath}/animation/animation-min.js"
		
		def yahooAnimationMinBuilder = yahooAnimationMin.getBuilder()
		yahooAnimationMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/animation/animation-min.js", "")
		
		resources.add(yahooAnimationMin)		
		

		// Accordion menu v2
		Resource accordionMenu = new Resource()
		accordionMenu.name = "${resourcePath}/js/accordion/accordion-menu-v2.js"
		
		def accordionMenuBuilder = accordionMenu.getBuilder()
		accordionMenuBuilder.script(type: "text/javascript", src: "${resourcePath}/js/accordion/accordion-menu-v2.js", "")
		
		resources.add(accordionMenu)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Accordion -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/accordion.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/accordion.css")
		}
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/accordion/accordion-menu-v2.js", "")
	}

}