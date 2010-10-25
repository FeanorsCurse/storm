package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.util.regex.*
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
public class CalendarDayViewRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {

		// TODO extract method
		def teaser = { String value, int length ->
			if(value.length() > length){
				return value.substring(0, length) + "..."
			}
			else {
				return value
			}
		}

		// TODO extract method
		def determineMinutes = { int minutes ->
			if(minutes == 0 || minutes < 15){
				return 0
			}
			if(minutes >= 15 && minutes < 30){
				return 15
			}
			if(minutes >= 30 && minutes < 45){
				return 30
			}
			if(minutes >= 45){
				return 45
			}
		}
		
		Date date = new Date()
		if(attrs?.date){
			date = attrs.date
		}
		
		String dateFormat = "dd.MM.yyyy"
		if(attrs?.format){
			dateFormat = attrs.format
		}
		
		//12/24 hour system
		
		int startHour = 6
		if(attrs?.startHour){
			startHour = new Integer(attrs.startHour)
		}
		
		if(startHour < 0){
			startHour = 0
		}
		
		int endHour = 22
		if(attrs?.endHour){
			endHour = new Integer(attrs.endHour)
		}
		
		if(endHour > 23){
			endHour = 23
		}
		
		DateFormat fmt = new SimpleDateFormat(dateFormat)
		String formattedDate = fmt.format(date)

		Map daysOfWeek = [2: attrs.weekDays.monday, 3: attrs.weekDays.tuesday, 4: attrs.weekDays.wednesday,
		                  5: attrs.weekDays.thursday, 6: attrs.weekDays.friday, 7: attrs.weekDays.saturday,
		                  1: attrs.weekDays.sunday]
		
		Map addedEvents = [:]
		
		//Date range pattern
		Pattern pattern = Pattern.compile("(.*)-(.*)")
		
		// TODO extract method
		def getEvents = { int hour, int minute ->
			List events = []
			
			attrs?.items.each { item ->
			
				attrs?.constraintDateFields?.each { constraintDateField ->
				
					//Date range
					Matcher matcher = pattern.matcher(constraintDateField)
					if(matcher.matches()){
						String constraintDateFieldStart = matcher.group(1)
						String constraintDateFieldEnd = matcher.group(2)
						
						Calendar calendarStart = new GregorianCalendar()
						calendarStart.setTime(item?."${constraintDateFieldStart}")
						
						Calendar calendarEnd = new GregorianCalendar()
						calendarEnd.setTime(item?."${constraintDateFieldEnd}")
						
						Calendar calendarItem = new GregorianCalendar(attrs.date.year + 1900, attrs.date.month, attrs.date.date, hour, minute)
						
						if(calendarItem.compareTo(calendarStart) >= 0 && calendarItem.compareTo(calendarEnd) < 0){
							events.add(item)
						}
						

					}
					else if(item?."${constraintDateField}".month == attrs.date.month && item?."${constraintDateField}".year == attrs.date.year && item?."${constraintDateField}".hours == hour && determineMinutes(item."${constraintDateField}".minutes) == minute){
						events.add(item)
					}
				}
			}
			
			return events
		}
		
		builder.table("class": "calendar") {
			thead {
				tr {
					th("${attrs?.time}")
					
					Calendar calendarDay = new GregorianCalendar(attrs.date.year + 1900, attrs.date.month, attrs.date.date)
					
					th("${daysOfWeek[calendarDay.get(GregorianCalendar.DAY_OF_WEEK)]} ${formattedDate}")
				}
			}
			
			tbody {
				for(int hour = startHour; hour <= endHour; hour++){
					tr("class": "${hour % 2 == 0 ? 'evenHour' : 'oddHour'}"){
						//Display time
						if(attrs?.timeFormat=="12"){
				            td("class": "time", "${(hour > 12) ? hour - 12 : hour}:00") // 12 hour time
						}
				        else {
				            td("class": "time", "${hour}:00")
						}
						
						List events = getEvents(hour, 0)
						
						if(events.size() > 0){
							td("class": "minute minuteWithItems"){
								events.each { eventItem ->
								
									if(!addedEvents.containsKey(eventItem.id)){
										String title = eventItem.toString()
										
										if(attrs?.displayField){
											try {
												title = eventItem."${attrs.displayField}"	
											}
											catch(Exception e){
												//log.error("Unexpected error using displayField", e)
											}
										}
												
										//Teaser
										if(attrs?.teaser && attrs?.teaser == "true"){
											int teaserLength = 30
											if(attrs?.teaserLength){
												try {
													teaserLength = new Integer(attrs.teaserLength)
												}
												catch(Exception e){
													//log.error("Unexpected error creating teaser", e)
												}
											}
													
											title = teaser(title, teaserLength)
										}
												
										if(attrs?.createLink == "true"){
											a(href: attrs.itemUrl.replace("itemId", "${eventItem?.id}"), title)
										}
										else {
											span(title)
										}
										
										addedEvents[eventItem.id] = true
									}
								}
							}
						}
						else {
							td("class": "minute")
						}
					}
					
					for(int i = 1; i <= 3; i++){
						tr("class": "${hour % 2 == 0 ? 'evenHour' : 'oddHour'}"){
							List events = getEvents(hour, 15 * i)
							
							if(events.size() > 0){
								td()
								td("class": "minuteWithItems"){
									events.each { eventItem ->
										
									if(!addedEvents.containsKey(eventItem.id)){
										String title = eventItem.toString()
										
										if(attrs?.displayField){
											try {
												title = eventItem."${attrs.displayField}"	
											}
											catch(Exception e){
												//log.error("Unexpected error using displayField", e)
											}
										}
												
										//Teaser
										if(attrs?.teaser && attrs?.teaser == "true"){
											int teaserLength = 30
											if(attrs?.teaserLength){
												try {
													teaserLength = new Integer(attrs.teaserLength)
												}
												catch(Exception e){
													//log.error("Unexpected error creating teaser", e)
												}
											}
													
											title = teaser(title, teaserLength)
										}
												
										if(attrs?.createLink == "true"){
											a(href: attrs.itemUrl.replace("itemId", "${eventItem?.id}"), title)
										}
										else {
											span(title)
										}
										
										addedEvents[eventItem.id] = true
									}
									}
								}
							}
							else {
								td()
								td("class": "minute")
							}
						}
					}
				}
			}
		}
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()		
		if(!attrs.skin || attrs.skin == "default"){
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/calendardayview.css")	
			css.name = "${resourcePath}/css/calendardayview.css"
		}
		else {
			String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
		}
		
		resources.add(css)
		
		return resources
	}
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- Calendar Month View -->"
		
		if(!attrs.skin || attrs.skin == "default"){
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/calendardayview.css")	
		}
		else {
			String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
			builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
		}
	}	
	
}
