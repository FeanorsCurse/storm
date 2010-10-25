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
public class CalendarWeekViewRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		
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
				
		Map daysOfWeek = [0: attrs.weekDays.monday, 1: attrs.weekDays.tuesday, 2: attrs.weekDays.wednesday,
		                  3: attrs.weekDays.thursday, 4: attrs.weekDays.friday, 5: attrs.weekDays.saturday,
		                  6: attrs.weekDays.sunday]
		
		Map addedEvents = [:]
		
		//Date range pattern
		Pattern pattern = Pattern.compile("(.*)-(.*)")
		
		// TODO extract to method
		def getEvents = { Calendar day, int hour, int minute ->
			List events = []
			
			attrs?.items.each { item ->
			
				attrs?.constraintDateFields?.each { constraintDateField ->
				
					Calendar calendarItem = day
					calendarItem.set(GregorianCalendar.HOUR_OF_DAY, hour)
					calendarItem.set(GregorianCalendar.MINUTE, minute)
					Date itemDate = calendarItem.getTime()
					
					//Date range
					Matcher matcher = pattern.matcher(constraintDateField)
					if(matcher.matches()){
						String constraintDateFieldStart = matcher.group(1)
						String constraintDateFieldEnd = matcher.group(2)
						
						Calendar calendarStart = new GregorianCalendar()
						try {
							calendarStart.setTime(item?."${constraintDateFieldStart}")	
						}
						catch(Exception e){
							// TODO log exception
							//e.printStackTrace()
						}
						
						Calendar calendarEnd = new GregorianCalendar()
						try {
							calendarEnd.setTime(item?."${constraintDateFieldEnd}")	
						}
						catch(Exception e){
							// TODO log exception
							//e.printStackTrace()
						}
						
						if(calendarItem.compareTo(calendarStart) >= 0 && calendarItem.compareTo(calendarEnd) < 0){
							events.add(item)
						}
					}
					else {
						// Don't check year e.g. for birthdays
						if(attrs?.noYearCheck && attrs.noYearCheck == "true"){
							try {								
								if(item?."${constraintDateField}".month == itemDate.month && item?."${constraintDateField}".date == itemDate.date && item?."${constraintDateField}".hours == hour && determineMinutes(item."${constraintDateField}".minutes) == minute){
									events.add(item)
								}
							}
							catch(Exception e){
								// TODO log exception
								//e.printStackTrace()
							}
						}
						// Normal check including year
						else {
							try {
								if(item?."${constraintDateField}".month == itemDate.month && item?."${constraintDateField}".year == itemDate.year && item?."${constraintDateField}".date == itemDate.date && item?."${constraintDateField}".hours == hour && determineMinutes(item."${constraintDateField}".minutes) == minute){
									events.add(item)
								}
							}
							catch(Exception e){
								// TODO log exception
								//e.printStackTrace()
							}
						}
					}
				}
			}
			
			return events
		}
		
		builder.table("class": "calendar") {
			thead {
				tr {
					th {
						span("${attrs?.time}")
					}
		
					Calendar calendar = new GregorianCalendar()
					calendar.setTime(date)
					
					for(int i = 0; i < 7; i++){
						DateFormat fmt = new SimpleDateFormat(dateFormat)
						String formattedDate = fmt.format(calendar.getTime())
						
						th {
							if(attrs?.dayAction){
								a(href: "${attrs.dayUrl}?day=${calendar.get(GregorianCalendar.DAY_OF_MONTH)}&month=${calendar.get(GregorianCalendar.MONTH) + 1}&year=${calendar.get(GregorianCalendar.YEAR)}"){
									span("${daysOfWeek[i]} ${formattedDate}")
								}	
							}
							else {
								span("${daysOfWeek[i]} ${formattedDate}")
							}
						}
						
						// Add one day
						calendar.add(GregorianCalendar.DAY_OF_YEAR, 1)
					}
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

						Calendar calendar = new GregorianCalendar()
						calendar.setTime(date)
						
						for(int n = 0; n < 7; n++){
							List events = getEvents(calendar, hour, 0)
							
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
							
							// Add one day
							calendar.add(GregorianCalendar.DAY_OF_YEAR, 1)
						}
						
						for(int i = 1; i <= 3; i++){
							tr("class": "${hour % 2 == 0 ? 'evenHour' : 'oddHour'}"){
								
								td()
					
								Calendar cal = new GregorianCalendar()
								cal.setTime(date)
								
								for(int n = 0; n < 7; n++){	
									
									List events = getEvents(cal, hour, 15 * i)
									
									if(events.size() > 0){
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
										td("class": "minute")
									}
									
									// Add one day
									cal.add(GregorianCalendar.DAY_OF_YEAR, 1)
								}								

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
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/calendarweekview.css")	
			css.name = "${resourcePath}/css/calendarweekview.css"
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
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/calendarweekview.css")	
		}
		else {
			String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
			builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
		}
	}
	
	private String teaser(String value, int length){
		if(value.length() > length){
			return value.substring(0, length) + "..."
		}
		else {
			return value
		}
	}

	private int determineMinutes(int minutes){
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
	
}
