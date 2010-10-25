package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class RatingRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String id = "r" + RenderUtils.getUniqueId()
		int imageWidth = 30
		int currentRating = attrs?.rating * imageWidth
		int units = attrs?.units * imageWidth
		String currentRatingId = "r" + RenderUtils.getUniqueId()
				
		String updateId = id
		if(attrs?.updateId) {
			updateId = attrs.updateId
		}
		
		if(!attrs?.inputId){
			attrs.inputId = "r" + RenderUtils.getUniqueId()
		}
		if(!attrs?.inputName){
			attrs.inputName = "rating"
		}
		
		builder.div(id: "${id}"){
			div("class": "ratingblock"){
				div(id: "unit_long$id"){
					if(attrs?.dynamic){
						ul(id: "unit_ul$id", "class": "unit-rating", style: "width: ${units}px"){
							li(id: "${currentRatingId}", "class": "current-rating", style:  "width: ${currentRating}px;", "Currently $attrs.rating")
							for(i in 1..attrs.units){
								li(){
									if(attrs?.noAjax){
										a("class": "r${i}-unit rater", onclick: "window.document.getElementById('${attrs.inputId}').value = ${i}; window.document.getElementById('${currentRatingId}').style.width = '${i * imageWidth}px'; window.document.getElementById('${currentRatingId}').innerHTML = 'Currently ${i}';", "${i}")
									}
									else {
										String link = attrs.link.replace(":class:", "r${i}-unit rater").replace(":title:", "$i").replace("update", "${updateId}").replace("number", "$i").replace("%3Arating%3A", "${i}")
										builder.yieldUnescaped "$link"	
									}
								}
							}
						}
					}
					else {
						ul(id: "unit_ul$id", "class": "unit-rating", style: "width: ${units}px"){
							li("class": "current-rating", style:  "width: ${currentRating}px;", "Currently $attrs.rating")
						}
					}
					
					if(attrs.showCurrent){
						p("class": "static"){
							strong(attrs?.rating)
						}
					}
				}
			}
			
			if(attrs?.noAjax){
				input(id: attrs.inputId, name: attrs.inputName, type: "hidden")
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// Prototype
		Resource prototype = new Resource()
		prototype.name = "${resourcePath}/js/prototype/prototype.js"
		
		def prototypeBuilder = prototype.getBuilder()
		prototypeBuilder.script(type: "text/javascript", src: "${resourcePath}/js/prototype/prototype.js", "")
		
		resources.add(prototype)

		
		// Behavior
		Resource behavior = new Resource()
		behavior.name = "${resourcePath}/js/rating/behavior.js"
		
		def behaviorBuilder = behavior.getBuilder()
		behaviorBuilder.script(type: "text/javascript", src: "${resourcePath}/js/rating/behavior.js", "")
		
		resources.add(behavior)
		

		// Rating
		Resource rating = new Resource()
		rating.name = "${resourcePath}/js/rating/rating.js"
		
		def ratingBuilder = rating.getBuilder()
		ratingBuilder.script(type: "text/javascript", src: "${resourcePath}/js/rating/rating.js", "")
		
		resources.add(rating)

		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", href: "${resourcePath}/css//rating.css", "")
				css.name = "${resourcePath}/css//rating.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", href: "${resourcePath}/css//rating.css", "")	
			css.name = "${resourcePath}/css//rating.css"
		}
		
		resources.add(css)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Rating -->"
		builder.script(type: "text/javascript", src: "$resourcePath/js/prototype/prototype.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/rating/behavior.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/rating/rating.js", "")
		//builder.link(rel: "stylesheet", href: "$resourcePath/css/rating.css", "")
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", href: "$resourcePath/css//rating.css", "")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", href: "$resourcePath/css//rating.css", "")	
		}
	}
}