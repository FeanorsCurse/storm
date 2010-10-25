import de.andreasschmitt.richui.taglib.renderer.*
/*
*
* @author Andreas Schmitt
*/
class SliderTagLib {
	
	static namespace = "richui"
		
	Renderer sliderRenderer
		
	def slider = {attrs ->
		//Render output
		try {
			out << sliderRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
