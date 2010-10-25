package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
public class LightBoxRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}

	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {		
		if(!attrs?.rel){
			if(attrs?.group){
				attrs.rel = "lightbox[${attrs.group}]"
			}
			else {
				attrs.rel = "lightbox"	
			}
		}
		
		builder."a"(attrs){
			builder.yieldUnescaped "${body.call()}"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// Prototype
		Resource prototype = new Resource()
		prototype.name = "${resourcePath}/js/lightbox/prototype.js"
		
		def prototypeBuilder = prototype.getBuilder()
		prototypeBuilder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/prototype.js", "")
		
		resources.add(prototype)
		
		// Scriptaculous
		Resource scriptaculous = new Resource()
		scriptaculous.name = "${resourcePath}/js/lightbox/scriptaculous.js?load=effects,builder"
		
		def scriptaculousBuilder = scriptaculous.getBuilder()
		scriptaculousBuilder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/scriptaculous.js?load=effects,builder", "")
		
		resources.add(scriptaculous)
		
		// Lightbox configuration
		Resource lightBoxConfig = new Resource()
		lightBoxConfig.name = "lightbox.config"
		
		Map configAttributes = [overlayOpacity: "0.8", animate: "true", resizeSpeed: "7",
			                        borderSize: "10", labelImage: "Image", labelOf: "of"]
			
		// Allow attribute overriding
		attrs.each { key, value ->
			if(configAttributes.containsKey(key)){
				configAttributes[key] = value
			}
		}
		
		def lightBoxConfigBuilder = scriptaculous.getBuilder()
		lightBoxConfigBuilder.script(type: "text/javascript"){
			lightBoxConfigBuilder.yieldUnescaped " LightboxOptions = Object.extend({\n"
			lightBoxConfigBuilder.yieldUnescaped " fileLoadingImage:        '${resourcePath}/js/lightbox/images/loading.gif',\n"     
			lightBoxConfigBuilder.yieldUnescaped " fileBottomNavCloseImage: '${resourcePath}/js/lightbox/images/closelabel.gif',\n"
			lightBoxConfigBuilder.yieldUnescaped " overlayOpacity: ${configAttributes.overlayOpacity},\n"
			lightBoxConfigBuilder.yieldUnescaped " animate: ${configAttributes.animate},\n"
			lightBoxConfigBuilder.yieldUnescaped " resizeSpeed: ${configAttributes.resizeSpeed},\n"
			lightBoxConfigBuilder.yieldUnescaped " borderSize: ${configAttributes.borderSize},\n"
			lightBoxConfigBuilder.yieldUnescaped " labelImage: '${configAttributes.labelImage}',\n"
			lightBoxConfigBuilder.yieldUnescaped " labelOf: '${configAttributes.labelOf}'\n"
			lightBoxConfigBuilder.yieldUnescaped " }, window.LightboxOptions || {});\n"
		}
		
		resources.add(lightBoxConfig)		
		
		// Lightbox JavaScript
		Resource lightBoxJavaScript = new Resource()
		lightBoxJavaScript.name = "${resourcePath}/js/lightbox/lightbox.js"
		
		def lightBoxJavaScriptBuilder = lightBoxJavaScript.getBuilder()
		lightBoxJavaScriptBuilder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/lightbox.js", "")
		
		resources.add(lightBoxJavaScript)		
		
		// Lightbox CSS
		Resource lightBoxCss = new Resource()
		lightBoxCss.name = "${resourcePath}/css/lightbox.css"
		
		def lightBoxCssBuilder = lightBoxCss.getBuilder()
		lightBoxCssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/lightbox.css")
		
		resources.add(lightBoxCss)
		
		return resources
	}

	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- LightBox -->"
			

		builder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/prototype.js", "")
		builder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/scriptaculous.js?load=effects,builder", "")
		
		Map configAttributes = [overlayOpacity: "0.8", animate: "true", resizeSpeed: "7",
		                        borderSize: "10", labelImage: "Image", labelOf: "of"]
		
		// Allow attribute overriding
		attrs.each { key, value ->
			if(configAttributes.containsKey(key)){
				configAttributes[key] = value
			}
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped " LightboxOptions = Object.extend({\n"
			builder.yieldUnescaped " fileLoadingImage:        '${resourcePath}/js/lightbox/images/loading.gif',\n"     
			builder.yieldUnescaped " fileBottomNavCloseImage: '${resourcePath}/js/lightbox/images/closelabel.gif',\n"
			builder.yieldUnescaped " overlayOpacity: ${configAttributes.overlayOpacity},\n"
			builder.yieldUnescaped " animate: ${configAttributes.animate},\n"
			builder.yieldUnescaped " resizeSpeed: ${configAttributes.resizeSpeed},\n"
			builder.yieldUnescaped " borderSize: ${configAttributes.borderSize},\n"
			builder.yieldUnescaped " labelImage: '${configAttributes.labelImage}',\n"
			builder.yieldUnescaped " labelOf: '${configAttributes.labelOf}'\n"
			builder.yieldUnescaped " }, window.LightboxOptions || {});\n"
		}
		
		builder.script(type: "text/javascript", src: "${resourcePath}/js/lightbox/lightbox.js", "")
		builder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/lightbox.css")
	}
	
}
