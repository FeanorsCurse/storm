import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class FlowTagLib {
	
	static namespace = "richui"
		
	Renderer flowRenderer
		
	def flow = {attrs, body ->				
		//Render output
		try {
			out << flowRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
