package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource


/*
 * @author Andreas Schmitt
 */
class TabViewRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var tabView = new YAHOO.widget.TabView(\"${attrs.id}\");\n"
		
			if(attrs?.event){
				builder.yieldUnescaped "	function handleClick(e) {"   
				builder.yieldUnescaped "        ${attrs?.event};" 
				builder.yieldUnescaped "    } "
				builder.yieldUnescaped "    tabView.addListener('activeTabChange', handleClick);"
			}
		}
			
		builder."div"("class": "yui-skin-sam"){
			"div"("class": "yui-navset yui-navset-top", "id": attrs?.id){				
				builder.yieldUnescaped "${body.call()}"
			}	
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
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/fonts/fonts-min.css")
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/tabview/assets/tabview-core.css")
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/tabView.css")
				css.name = "${yuiResourcePath}/fonts/fonts-min.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/tabview/assets/skins/sam/tabview.css")
			css.name = "${yuiResourcePath}/tabview/assets/skins/sam/tabview.css"
		}
		
		resources.add(css)
		
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
		

		// Yahoo element min
		Resource yahooElementMin = new Resource()
		yahooElementMin.name = "${yuiResourcePath}/element/element-min.js"
		
		def yahooElementMinBuilder = yahooElementMin.getBuilder()
		yahooElementMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/element/element-min.js", "")
		
		resources.add(yahooElementMin)

		// Yahoo connection min
		Resource connectionMin = new Resource()
		connectionMin.name = "${yuiResourcePath}/connection/connection-min.js"
		
		def connectionMinBuilder = connectionMin.getBuilder()
		connectionMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/connection/connection-min.js", "")
		
		resources.add(connectionMin)
		
		// Yahoo tab view min
		Resource yahooTabViewMin = new Resource()
		yahooTabViewMin.name = "${yuiResourcePath}/tabview/tabview-min.js"
		
		def yahooTabViewMinBuilder = yahooTabViewMin.getBuilder()
		yahooTabViewMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/tabview/tabview-min.js", "")
		
		resources.add(yahooTabViewMin)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- TabView -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/fonts/fonts-min.css")
				builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/tabview/assets/tabview-core.css")
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/tabView.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/tabview/assets/skins/sam/tabview.css")
		}
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/element/element-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/connection/connection-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/tabview/tabview-min.js", "")		
	}

}