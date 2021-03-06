package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class GoogleMapsRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String mapId = "map" + RenderUtils.getUniqueId()
		
		if(attrs?.mapId){
			mapId = attrs.mapId
		}
		
		String dirId = "d" + RenderUtils.getUniqueId()
					
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "//<![CDATA[\n"
			builder.yieldUnescaped "var $attrs.mapIntegrationVar;\n"
			
			if(!(attrs?.immediate && attrs.immediate == "true")){
				builder.yieldUnescaped "addEvent(window, \"load\", \n"
				builder.yieldUnescaped "function () {\n"	
			}	
			
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + " = new GoogleMapIntegration();\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setDirectionsId(\"${dirId}\");\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setDraggable($attrs.draggable);\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setLatitudeId(\"${attrs?.latId}\");\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setLongitudeId(\"${attrs?.lngId}\");\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setMapId(\"$mapId\");\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setZoomLevel(${attrs.zoomLevel});\n"
			builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setShowStartMarker(${attrs.showStartMarker});\n"
			if(attrs?.small && attrs.small == "true"){
				builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".setSmallMap(${attrs?.small});\n"
			}
			
			//Markers
			if(attrs?.markers){
				attrs.markers.each {
					try {
						builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".addMarker(new GLatLng($it.latitude, $it.longitude), $it.draggable, '$it.description');\n"
					}
					catch(exception) {
						println exception
					}
				}	
			}
				
			if("${attrs.lat}" == "" && "${attrs.lng}" == ""){
				if(attrs?.markers?.size() > 0){
					Map marker = attrs.markers[0]
					String latitude = marker.latitude
					String longitude = marker.longitude
					builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".load(new GLatLng(${latitude}, ${longitude}));\n"
				}
				else {
					builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".load();\n"	
				}
			}
			else {
				builder.yieldUnescaped "	" + attrs.mapIntegrationVar + ".load(new GLatLng($attrs.lat, $attrs.lng));\n"				
			}
			
			if(!(attrs?.immediate && attrs.immediate == "true")){
				builder.yieldUnescaped "}\n"
				builder.yieldUnescaped ", false);\n"
			}
			
			builder.yieldUnescaped "window.onunload = function () {\n"
			builder.yieldUnescaped "	GUnload();\n"
			builder.yieldUnescaped "}\n"
			builder.yieldUnescaped "//]]>\n"	
		}
			
		builder.div("class": attrs?.'class', style: attrs?.style){
		    if(attrs?.search && attrs.search == "true"){
				search(builder, attrs, attrs.mapIntegrationVar)
		    }
			
			//Map div
			div(id:"$mapId", style: "$attrs.mapStyle", "class": "$attrs.mapStyleClass", "")
			
			if(attrs?.route && attrs.route == "true"){
				calculateRoute(builder, attrs, attrs.mapIntegrationVar)
				drivingDirections(builder, attrs, dirId)
			}
			
			
		}
	}
	
	private void drivingDirections(builder, attrs, dirId){		
		builder."div"(id: "$dirId", style: "${attrs?.dirStyle}", "class": "${attrs?.dirClass}", "")
	}
	
	private void calculateRoute(builder, attrs, mapIntegrationVar){
		builder.p(){
			form(){
				label("for": "start", "${attrs.routeStart}:")
				input(id: "${mapIntegrationVar}start", type: "text", name: "start")
				label("for": "destination","${attrs.routeDestination}:")
				input(id: "${mapIntegrationVar}destination", type: "text", name: "destination")
				input(type: "button", name: "route", value: "${attrs.routeOk}", onclick: "javascript: ${mapIntegrationVar}.setDirections(document.getElementById('${mapIntegrationVar}start').value, document.getElementById('${mapIntegrationVar}destination').value, '${attrs.directionsLocale}');")
				input(type: "button", name: "route_clear", value: "${attrs.routeClear}", onclick: "javascript: ${mapIntegrationVar}.clearDirections(); javascript: document.getElementById('${mapIntegrationVar}start').value = ''; javascript: document.getElementById('${mapIntegrationVar}destination').value = '';")
			}
		}
	}
	
	private void search(builder, attrs, mapIntegrationVar){
		builder.p(){
			form(){
				label("for": "${mapIntegrationVar}search", "${attrs.searchSearch}:")
				input(id: "${mapIntegrationVar}search", type: "text", name: "search", style: "")
				input(type: "button", name: "searchbutton", onclick: "javascript: ${mapIntegrationVar}.showAddress(document.getElementById('${mapIntegrationVar}search').value);", value: "${attrs.searchOk}")
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		if(!attrs?.key){
			throw new RenderException("Attribute 'key' is required")
		}
		
		if(!attrs?.version){
			attrs.version = "2.x"
		}
		
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
		

		// Java script
		Resource javaScript = new Resource()
		javaScript.name = "http://maps.google.com/maps?file=api&amp;v=${attrs.version}&key=${attrs.key}"
		
		def javaScriptBuilder = javaScript.getBuilder()
		javaScriptBuilder.script(type: "text/javascript", src:"http://maps.google.com/maps?file=api&amp;v=${attrs.version}&key=${attrs.key}", "")
		
		resources.add(javaScript)
		
		
		// Gmap
		Resource gmap = new Resource()
		gmap.name = "${resourcePath}/js/googlemaps/gmap.js"
		
		def gmapBuilder = gmap.getBuilder()
		gmapBuilder.script(type: "text/javascript", src:"${resourcePath}/js/googlemaps/gmap.js", "")
		
		resources.add(gmap)


		// Util
		Resource util = new Resource()
		util.name = "${resourcePath}/js/util/util.js"
		
		def utilBuilder = util.getBuilder()
		utilBuilder.script(type: "text/javascript", src:"${resourcePath}/js/util/util.js", "")
		
		resources.add(util)

		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		if(!attrs?.key){
			throw new RenderException("Attribute 'key' is required")
		}
		
		if(!attrs?.version){
			attrs.version = "2.x"
		}
		
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
		
		builder.script(type: "text/javascript", src:"http://maps.google.com/maps?file=api&amp;v=${attrs.version}&key=${attrs.key}", "")
		builder.script(type: "text/javascript", src:"$resourcePath/js/googlemaps/gmap.js", "")
		builder.script(type: "text/javascript", src:"$resourcePath/js/util/util.js", "")
	}

}