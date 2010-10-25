package de.andreasschmitt.richui.taglib.renderer

import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/

interface Renderer {
	
	String renderResources(Map attrs, String contextPath) throws RenderException
	String renderTag(Map attrs) throws RenderException
	String renderTag(Map attrs, Closure body) throws RenderException
	List<Resource> getResources(Map attrs, String contextPath) throws RenderException

}