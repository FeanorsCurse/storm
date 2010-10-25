package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class TreeViewRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {			
		if(!attrs?.id){
			attrs.id = "tree" + RenderUtils.getUniqueId()
		}
		
		builder.div(id: attrs?.id, "")					
		builder.script(type: "text/javascript"){  		 
		    builder.yieldUnescaped "	var tree = new YAHOO.widget.TreeView(\"$attrs.id\");\n"
		    builder.yieldUnescaped "	var root = tree.getRoot();\n"		    	
		    
		    builder.yieldUnescaped "    function createNode(text, id, icon, pnode){\n"
            builder.yieldUnescaped "        var n = new YAHOO.widget.TextNode(text, pnode, false);\n"
            builder.yieldUnescaped "        n.labelStyle=icon;\n"
		    if(attrs?.onLabelClick){
		    	builder.yieldUnescaped "		n.additionalId = id;\n"
		    }
            builder.yieldUnescaped "        return n;\n"
            builder.yieldUnescaped "    }\n\n"
	
		    if(attrs?.onLabelClick){
			    builder.yieldUnescaped "	tree.subscribe(\"labelClick\", function(node) {\n"
			    builder.yieldUnescaped "		var id = node.additionalId;"
			    builder.yieldUnescaped "		${attrs.onLabelClick}"
			    builder.yieldUnescaped "	});\n"
		    }
		    
		    if(attrs?.showRoot == "false") {
                createTree(attrs.xml.children(), "root", builder)
            } else {
                createTree(attrs.xml, "root", builder)
            }
				
			builder.yieldUnescaped "	tree.draw();\n"
		}
	}
	
	private void createTree(nodes, parent, builder){
		nodes.each {
			//leaf
			if(it.children().size() == 0){
				builder.yieldUnescaped "    createNode(\"" + it.@name + "\", \"" + it?.@id + "\", \"" + it.@icon + "\", $parent);\n"
			}
			//knot
			else {
				def nodeName = it.@name
				if(it.@name == ""){
					nodeName = "unknown"
				}
				
				def newParent = "t" + RenderUtils.getUniqueId()
				
				builder.yieldUnescaped "    " + newParent + " = createNode(\"" + nodeName + "\", \"" + it?.@id + "\",\"" + it?.@icon + "\", $parent);\n"	
				
				createTree(it.children(), newParent, builder)
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
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/treeView.css")
				css.name = "${resourcePath}/css/treeView.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/treeview/assets/skins/sam/treeview.css")
			css.name = "${yuiResourcePath}/treeview/assets/skins/sam/treeview.css"
		}
		
		resources.add(css)
		
		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
			

		// Yahoo event min
		Resource yahooEventMin = new Resource()
		yahooEventMin.name = "${yuiResourcePath}/event/event-min.js"
		
		def yahooEventMinBuilder = yahooEventMin.getBuilder()
		yahooEventMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/event/event-min.js", "")
		
		resources.add(yahooEventMin)
		
		
		// Yahoo min
		Resource yahooMin = new Resource()
		yahooMin.name = "${yuiResourcePath}/yahoo/yahoo-min.js"
		
		def yahooMinBuilder = yahooMin.getBuilder()
		yahooMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo/yahoo-min.js", "")
		
		resources.add(yahooMin)
		
		
		// Yahoo tree view min
		Resource yahooTreeViewMin = new Resource()
		yahooTreeViewMin.name = "${yuiResourcePath}/treeview/treeview-min.js"
		
		def yahooTreeViewMinBuilder = yahooTreeViewMin.getBuilder()
		yahooTreeViewMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/treeview/treeview-min.js", "")
		
		resources.add(yahooTreeViewMin)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {
		builder.yieldUnescaped "<!-- TreeView -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/treeView.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/treeview/assets/skins/sam/treeview.css")
		}
			
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/event/event-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo/yahoo-min.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/treeview/treeview-min.js", "")
	}


}