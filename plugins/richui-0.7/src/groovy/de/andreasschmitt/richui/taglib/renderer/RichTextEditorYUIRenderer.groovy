package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class RichTextEditorYUIRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		builder."div"("class": "yui-skin-sam"){
			textarea(name: "${attrs?.name}", id: "${attrs?.id}", "${attrs.value}")
		}

		builder.script(type: "text/javascript"){
			if(attrs?.type == "advanced"){
				builder.yieldUnescaped "	var editor = new YAHOO.widget.Editor('${attrs?.id}', {\n"
			}
			else {
				builder.yieldUnescaped "	var editor = new YAHOO.widget.SimpleEditor('${attrs?.id}', {\n"
			}
			builder.yieldUnescaped "	    height: '${attrs?.height}px',\n"
			builder.yieldUnescaped "	    width: '${attrs?.width}px',\n"
			builder.yieldUnescaped "	    handleSubmit: true\n"
			builder.yieldUnescaped "	});\n"
			builder.yieldUnescaped "	editor.render();\n"
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
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/assets/skins/sam/skin.css")
				css.name = "${yuiResourcePath}/assets/skins/sam/skin.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/assets/skins/sam/skin.css")	
			css.name = "${yuiResourcePath}/assets/skins/sam/skin.css"
		}
		
		resources.add(css)
		
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
		

		// Element beta min
		Resource yahooElementBetaMin = new Resource()
		yahooElementBetaMin.name = "${yuiResourcePath}/element/element-beta-min.js"
		
		def yahooElementBetaMinBuilder = yahooElementBetaMin.getBuilder()
		yahooElementBetaMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/element/element-beta-min.js", "")
		
		resources.add(yahooElementBetaMin)
		

		// Animation min
		Resource yahooAnimationMin = new Resource()
		yahooAnimationMin.name = "${yuiResourcePath}/animation/animation-min.js"
		
		def yahooAnimationMinBuilder = yahooAnimationMin.getBuilder()
		yahooAnimationMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/animation/animation-min.js", "")
		
		resources.add(yahooAnimationMin)
		
		
		// Container core min
		Resource yahooContainerCoreMin = new Resource()
		yahooContainerCoreMin.name = "${yuiResourcePath}/container/container_core-min.js"
		
		def yahooContainerCoreMinBuilder = yahooContainerCoreMin.getBuilder()
		yahooContainerCoreMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/container/container_core-min.js", "")
		
		resources.add(yahooContainerCoreMin)
		

		// Menu min
		Resource yahooMenuMin = new Resource()
		yahooMenuMin.name = "${yuiResourcePath}/menu/menu-min.js"
		
		def yahooMenuMinBuilder = yahooMenuMin.getBuilder()
		yahooMenuMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/menu/menu-min.js", "")
		
		resources.add(yahooMenuMinBuilder)
		
		
		// Button min
		Resource yahooButtonMin = new Resource()
		yahooButtonMin.name = "${yuiResourcePath}/button/button-min.js"
		
		def yahooButtonMinBuilder = yahooButtonMin.getBuilder()
		yahooButtonMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/button/button-min.js", "")
		
		resources.add(yahooButtonMin)
		
		
		// Editor
		Resource editor = new Resource()
		
		def editorBuilder = editor.getBuilder()
		if(attrs?.type == "advanced"){
			editorBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/editor/editor-beta-min.js", "")	
			editor.name = "${yuiResourcePath}/editor/editor-beta-min.js"
		}
		else {
			editorBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/editor/simpleeditor-beta-min.js", "")
			editor.name = "${yuiResourcePath}/editor/simpleeditor-beta-min.js"
		}
		
		resources.add(editor)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- RichTextEditor -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
	
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/assets/skins/sam/skin.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/assets/skins/sam/skin.css")	
		}
		
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/element/element-beta-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/animation/animation-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/container/container_core-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/menu/menu-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/button/button-min.js", "")
		if(attrs?.type == "advanced"){
			builder.script(type: "text/javascript", src: "$yuiResourcePath/editor/editor-beta-min.js", "")	
		}
		else {
			builder.script(type: "text/javascript", src: "$yuiResourcePath/editor/simpleeditor-beta-min.js", "")
		}
	}

}