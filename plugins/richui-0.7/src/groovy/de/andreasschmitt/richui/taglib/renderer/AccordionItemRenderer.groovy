package de.andreasschmitt.richui.taglib.renderer;

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class AccordionItemRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
			
		builder.dt("class": "a-m-t", "id": "${attrs?.id}", "${attrs?.caption}")
		builder.dd("class": "a-m-d"){
			builder."div"("class": "bd"){
				builder.yieldUnescaped "${body.call()}"	
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			

	}

}