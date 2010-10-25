import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class LightBoxTagLib {

	static namespace = "richui"
		
	Renderer lightBoxRenderer
			
	def lightBox = { attrs, body ->
		//Render output
		try {
			out << lightBoxRenderer.renderTag(attrs, body)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
