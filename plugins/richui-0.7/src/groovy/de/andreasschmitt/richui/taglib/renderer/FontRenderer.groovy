package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class FontRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {		
		String src = "${attrs?.src}?text=${attrs?.text.encodeAsURL()}&fontName=${attrs?.fontName.encodeAsURL()}&style=${attrs?.fontStyle.encodeAsURL()}&size=${attrs?.size.encodeAsURL()}&color=${attrs?.color.encodeAsURL()}"
		
		builder.img(src: "${src}")
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		
	}	
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		
	}

}