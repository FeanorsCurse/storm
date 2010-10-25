package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class CarouselRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String id = "c" + RenderUtils.getUniqueId()
		
		String direction = "horizontal"
		if(attrs?.direction){
			direction = attrs.direction
		}
		
		builder."div"(id: "${id}", "class": "${direction}_carousel ${attrs?.carouselClass}", style: "${attrs?.carouselStyle}"){
			"div"("class": "previous_button", ""){
			}

			"div"("class": "container ${attrs?.itemsClass}", style: "${attrs?.itemsStyle}"){
				ul(){
					builder.yieldUnescaped "${body.call()}"
				}	
			}
			
			"div"("class": "next_button", ""){
			}
		}
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	    carousel = new UI.Carousel('${id}', {direction: '${direction}'});\n"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()		
		if(attrs?.skin){
			if(attrs.skin == "classic"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/carousel/classic.css")
				css.name = "${resourcePath}/css/carousel/classic.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/carousel/prototype-ui.css")
			css.name = "${resourcePath}/css/carousel/prototype-ui.css"
		}
		
		resources.add(css)
		
		
		// Prototype
		Resource prototype = new Resource()
		prototype.name = "${resourcePath}/js/carousel/prototype.js"
		
		def prototypeBuilder = prototype.getBuilder()
		prototypeBuilder.script(type: "text/javascript", src: "${resourcePath}/js/carousel/prototype.js", "")
		
		resources.add(prototype)
		
		
		// Prototype effects
		Resource prototypeEffects = new Resource()
		prototypeEffects.name = "${resourcePath}/js/carousel/effects.js"
		
		def prototypeEffectsBuilder = prototypeEffects.getBuilder()
		prototypeEffectsBuilder.script(type: "text/javascript", src: "${resourcePath}/js/carousel/effects.js", "")
		
		resources.add(prototypeEffects)
		

		// Carousel packed
		Resource carouselPacked = new Resource()
		carouselPacked.name = "${resourcePath}/js/carousel/carousel.packed.js"
		
		def carouselPackedBuilder = carouselPacked.getBuilder()
		carouselPackedBuilder.script(type: "text/javascript", src: "${resourcePath}/js/carousel/carousel.packed.js", "")
		
		resources.add(carouselPacked)		
		

		// Prototype ui packed
		Resource prototypeUiPacked = new Resource()
		prototypeUiPacked.name = "${resourcePath}/js/carousel/prototype-ui.packed.js"
		
		def prototypeUiPackedBuilder = prototypeUiPacked.getBuilder()
		prototypeUiPackedBuilder.script(type: "text/javascript", src: "${resourcePath}/js/carousel/prototype-ui.packed.js", "")
		
		resources.add(prototypeUiPacked)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Carousel -->"		
		if(attrs?.skin){
			if(attrs.skin == "classic"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/carousel/classic.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/carousel/prototype-ui.css")
		}
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/prototype.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/effects.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/carousel.packed.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/carousel/prototype-ui.packed.js", "")
	}
}