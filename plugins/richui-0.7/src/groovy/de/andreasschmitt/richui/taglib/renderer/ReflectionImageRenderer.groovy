package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class ReflectionImageRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String resultId = "a" + RenderUtils.getUniqueId()
		
		String clazz = "reflect rheight${attrs?.reflectionHeight} ropacity${attrs?.reflectionOpacity}"
		if(attrs?."class"){
			clazz += " ${attrs.'class'}"
		}
		
		attrs.put("class", clazz)
		attrs.remove("reflectionHeight")
		attrs.remove("reflectionOpacity")
		
		builder.img(attrs)
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
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

		
		// Reflection 
		Resource reflection = new Resource()
		reflection.name = "${resourcePath}/js/reflection/reflection.js"
		
		def reflectionBuilder = reflection.getBuilder()
		reflectionBuilder.script(type: "text/javascript", src: "${resourcePath}/js/reflection/reflection.js", "")
		
		resources.add(reflection)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Reflection Image -->"
		
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
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/reflection/reflection.js", "")
	}

}