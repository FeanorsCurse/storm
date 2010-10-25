package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class RichTextEditorRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		//Default HTML attributes
		Map htmlAttributes = [name: "${attrs?.name}", id: "${attrs?.id}", style: "width: ${attrs?.width}px; height: ${attrs?.height}px;"]

		//Add additional attributes
		attrs.each { key, value ->
			if(key.startsWith("html:")){
				htmlAttributes[key.replace("html:", "")] = value
			}
		}
		
		builder.textarea(htmlAttributes, "${attrs.value}")
		
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	tinyMCE.execCommand('mceAddControl', true, '${attrs?.id}');\n"
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// Tiny mce  
		Resource tinyMce = new Resource()
		tinyMce.name = "${resourcePath}/js/tinymce/tiny_mce.js"
		
		def tinyMceBuilder = tinyMce.getBuilder()
		tinyMceBuilder.script(type: "text/javascript", src: "${resourcePath}/js/tinymce/tiny_mce.js", "")
		
		resources.add(tinyMce)

		
		// Tiny mce  
		Resource tinyMceInit = new Resource()
		tinyMceInit.name = "tinymceinit"
		
		def tinyMceInitBuilder = tinyMceInit.getBuilder()		
		tinyMceInitBuilder.script(type: "text/javascript"){
			tinyMceInitBuilder.yieldUnescaped "tinyMCE.init({\n"
			tinyMceInitBuilder.yieldUnescaped "	mode : 'none',\n"
			tinyMceInitBuilder.yieldUnescaped " extended_valid_elements : 'script[type|src]',\n" //added by Olaf
			

			if(!attrs?.type ||attrs?.type == "medium") {
				tinyMceInitBuilder.yieldUnescaped " extended_valid_elements : 'script[type|src]',\n" //added by Olaf
				tinyMceInitBuilder.yieldUnescaped "	theme : 'advanced',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons1 : 'bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,bullist,numlist,undo,redo,link,unlink',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons2 : '',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons3 : '',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				tinyMceInitBuilder.yieldUnescaped "	extended_valid_elements : 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]'\n"	
			}
			else if(attrs.type == "simple"){
				tinyMceInitBuilder.yieldUnescaped " extended_valid_elements : 'script[type|src]',\n" //added by Olaf
				tinyMceInitBuilder.yieldUnescaped "	theme : 'simple',\n"
				tinyMceInitBuilder.yieldUnescaped "	editor_selector : 'mceSimple'\n"
			}
			else if(attrs?.type == "advanced") {
				tinyMceInitBuilder.yieldUnescaped " extended_valid_elements : 'script[type|src]',\n" //added by Olaf
				tinyMceInitBuilder.yieldUnescaped "	theme : 'advanced',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons1 : 'bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect,|,bullist,numlist,undo,redo,link,unlink',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons2 : '',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons3 : '',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				tinyMceInitBuilder.yieldUnescaped "	extended_valid_elements : 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]'\n"	
			}
			else if(attrs?.type == "full") {
				tinyMceInitBuilder.yieldUnescaped " valid_elements : 'script[src]',\n" //added by Olaf
				tinyMceInitBuilder.yieldUnescaped "	theme : 'advanced',\n"
				tinyMceInitBuilder.yieldUnescaped "	plugins : 'safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template',\n"

				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons1 : 'save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons2 : 'cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons3 : 'tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_buttons4 : 'insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				tinyMceInitBuilder.yieldUnescaped "	theme_advanced_resizing : true\n"
			}
			else {
				tinyMceInitBuilder.yieldUnescaped "	theme : 'advanced',\n"
			}

			tinyMceInitBuilder.yieldUnescaped "});\n"
		}
		
		resources.add(tinyMceInit)
		
		
		// CSS  
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()
		cssBuilder.script(type: "text/javascript", src: "${resourcePath}/js/tinymce/tiny_mce.js", "")
		
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
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- RichTextEditor -->"
		
		builder.script(type: "text/javascript", src: "$resourcePath/js/tinymce/tiny_mce.js", "")
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "tinyMCE.init({\n"
			builder.yieldUnescaped "	mode : 'none',\n"
			

			if(!attrs?.type ||attrs?.type == "medium") {
				builder.yieldUnescaped "	theme : 'advanced',\n"
				builder.yieldUnescaped "	theme_advanced_buttons1 : 'bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,bullist,numlist,undo,redo,link,unlink',\n"
				builder.yieldUnescaped "	theme_advanced_buttons2 : '',\n"
				builder.yieldUnescaped "	theme_advanced_buttons3 : '',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				builder.yieldUnescaped "	extended_valid_elements : 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]'\n"	
			}
			else if(attrs.type == "simple"){
				builder.yieldUnescaped "	theme : 'simple',\n"
				builder.yieldUnescaped "	editor_selector : 'mceSimple'\n"
			}
			else if(attrs?.type == "advanced") {
				builder.yieldUnescaped "	theme : 'advanced',\n"
				builder.yieldUnescaped "	theme_advanced_buttons1 : 'bold,italic,underline,separator,strikethrough,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect,|,bullist,numlist,undo,redo,link,unlink',\n"
				builder.yieldUnescaped "	theme_advanced_buttons2 : '',\n"
				builder.yieldUnescaped "	theme_advanced_buttons3 : '',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				builder.yieldUnescaped "	extended_valid_elements : 'a[name|href|target|title|onclick],img[class|src|border=0|alt|title|hspace|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|noshade],font[face|size|color|style],span[class|align|style]'\n"	
			}
			else if(attrs?.type == "full") {
				builder.yieldUnescaped "	theme : 'advanced',\n"
				builder.yieldUnescaped "	plugins : 'safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template',\n"

				builder.yieldUnescaped "	theme_advanced_buttons1 : 'save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect',\n"
				builder.yieldUnescaped "	theme_advanced_buttons2 : 'cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor',\n"
				builder.yieldUnescaped "	theme_advanced_buttons3 : 'tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen',\n"
				builder.yieldUnescaped "	theme_advanced_buttons4 : 'insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_location : 'top',\n"
				builder.yieldUnescaped "	theme_advanced_toolbar_align : 'left',\n"
				builder.yieldUnescaped "	theme_advanced_resizing : true\n"
			}
			else {
				builder.yieldUnescaped "	theme : 'advanced',\n"
			}

			builder.yieldUnescaped "});\n"
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
	}
}