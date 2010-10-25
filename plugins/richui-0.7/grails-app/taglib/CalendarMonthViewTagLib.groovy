import de.andreasschmitt.richui.taglib.renderer.*
import java.text.SimpleDateFormat

/*
*
* @author Andreas Schmitt
*/
class CalendarMonthViewTagLib {
	
	static namespace = "richui"
		
	Renderer calendarMonthViewRenderer
	def messageSource	
	
	def calendarMonthView = {attrs ->
	
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
		
		if(attrs?.weekAction){
			attrs.weekUrl = "${createLink(controller: attrs.weekController, action: attrs.weekAction)}"
		}
		if(attrs?.dayAction){
			attrs.dayUrl = "${createLink(controller: attrs.dayController, action: attrs.dayAction)}"
		}
		if(attrs?.createLink && attrs?.createLink == "true"){
			String action = attrs.action
			
			// Domain specific controller mapping
			if(attrs.controller instanceof Map){
				Map itemUrls = [:]
				
				attrs.controller.each { key, value ->
					String controller = value //attrs.controller.get(domain.toString())
					
					// Domain specific action mapping
					if(attrs.action instanceof Map && attrs.action.containsKey(key)){
						action = attrs.action[key]
					}

					itemUrls.put(key, "${createLink(controller: controller, action: action, id: 'itemId')}")
				}
				attrs.itemUrls = itemUrls
			}
			else {
				attrs.itemUrl = "${createLink(controller: attrs.controller, action: attrs.action, id: 'itemId')}"
			}
		}
		
		try {
			attrs.week = messageSource.getMessage("default.week", null, request?.locale)
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
			out << calendarMonthViewRenderer.renderTag(attrs)
		}
		catch(RenderException e){
			log.error(e)
		}
	}
}
