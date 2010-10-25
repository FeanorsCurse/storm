import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class AutoCompleteTagLib {
	
	static namespace = "richui"
	
	Renderer autoCompleteRenderer
	
	def autoComplete = {attrs ->
	
		if(!attrs?.name){
			throw new RenderException("Attribute name is required.")
		}
		
		if(!attrs?.action){
			attrs.action = actionName
		}
		
		if(!attrs?.controller){
			attrs.controller = controllerName
		}
		
		if(!attrs.action.contains("/")){
			attrs.action = "${createLinkTo('dir': attrs.controller + '/' + attrs.action)}"	
		}
			
		if(!attrs?.id){
			attrs.id = attrs.name
		}
			
		if(!attrs."class"){
			attrs."class" = ""	
		}
			
		if(!attrs.style){
			attrs.style = ""	
		}
		
		if(!attrs?.shadow){
			attrs.shadow = false			
		} 
		else {
			attrs.shadow = attrs.shadow == "true" ? true : false
		}
		
		if(!attrs?.minQueryLength){
			attrs.minQueryLength = 0
		}
		
		if(!attrs?.queryDelay){
			attrs.queryDelay = 0
		}
			
		if(!attrs?.value){
			attrs.value = ""
		}
		
		if(!attrs?.title){
            attrs.title = ""
        }
			
		//Render output
		try {
			out << autoCompleteRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}