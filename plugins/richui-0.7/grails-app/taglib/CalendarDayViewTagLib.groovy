import de.andreasschmitt.richui.taglib.renderer.*

/*
*
* @author Andreas Schmitt
*/
class CalendarDayViewTagLib {
	
	static namespace = "richui"
		
	Renderer calendarDayViewRenderer
	def messageSource	
		
	def calendarDayView = {attrs ->
		
		if(!attrs?.action){
			attrs.action = actionName
		}
			
		if(!attrs?.controller){
			attrs.controller = controllerName
		}
			
		if(!attrs?.dayController){
			attrs.dayController = controllerName
		}
			
		if(!attrs?.weekController){
			attrs.weekController = controllerName
		}
			
		if(attrs?.createLink && attrs?.createLink == "true"){
			attrs.itemUrl = "${createLink(controller: attrs.controller, action: attrs.action, id: 'itemId')}"
		}
			
		try {
			attrs.time = messageSource.getMessage("default.time", null, request?.locale)
			attrs.weekDays = [:]
			attrs.weekDays.monday = messageSource.getMessage("default.monday", null, request?.locale)
			attrs.weekDays.tuesday = messageSource.getMessage("default.tuesday", null, request?.locale)
			attrs.weekDays.wednesday = messageSource.getMessage("default.wednesday", null, request?.locale)
			attrs.weekDays.thursday = messageSource.getMessage("default.thursday", null, request?.locale)
			attrs.weekDays.friday = messageSource.getMessage("default.friday", null, request?.locale)
			attrs.weekDays.saturday = messageSource.getMessage("default.saturday", null, request?.locale)
			attrs.weekDays.sunday = messageSource.getMessage("default.sunday", null, request?.locale)	
		}
		catch(Exception e){
			log.error("Error retrieving messages", e)
		}
			
		//Render output
		try {
			out << calendarDayViewRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
