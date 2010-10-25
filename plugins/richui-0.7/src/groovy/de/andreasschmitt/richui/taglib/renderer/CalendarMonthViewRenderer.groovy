package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat
import java.util.regex.*
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class CalendarMonthViewRenderer extends AbstractRenderer {

	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		if(!attrs?.month){
			attrs.month = new Date()
		}
		
		if(!attrs?.createLink){
			attrs?.createLink = "true"
		}
		
		Calendar calendarToday = new GregorianCalendar()
		
		Calendar calendar = new GregorianCalendar()
		calendar.setTime(attrs.month)
		calendar.set(GregorianCalendar.DAY_OF_MONTH, 1)
		
		int month = calendar.get(GregorianCalendar.MONTH) + 1
		int year = calendar.get(GregorianCalendar.YEAR)
		int maxDaysOfMonth = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)
		
		Map daysOfWeek = [0: GregorianCalendar.MONDAY, 1: GregorianCalendar.TUESDAY, 2: GregorianCalendar.WEDNESDAY,
		                  3: GregorianCalendar.THURSDAY, 4: GregorianCalendar.FRIDAY, 5: GregorianCalendar.SATURDAY,
		                  6: GregorianCalendar.SUNDAY]
		
		Map events = [:]

		//Date range pattern
		Pattern pattern = Pattern.compile("(.*)-(.*)")
		
		for(int i = 1; i <= maxDaysOfMonth; i++){
			attrs?.items?.each { item ->			

				String className = item.class.simpleName				
				List constraintDateFields = []

                // Either use class specific constraint date fields or constraint date fields for all objects 
                if(attrs?.constraintDateFields instanceof Map){
                	if(attrs?.constraintDateFields.containsKey(className)){
                    	constraintDateFields = attrs?.constraintDateFields[className]	
                	}
				}
                else {
                	constraintDateFields = attrs?.constraintDateFields
                }

				
				constraintDateFields?.each { constraintDateField ->

					//Date range
					Matcher matcher = pattern.matcher(constraintDateField)
					if(matcher.matches()){
						String constraintDateFieldStart = matcher.group(1)
						String constraintDateFieldEnd = matcher.group(2)
						
						Calendar calendarStart = new GregorianCalendar()
						calendarStart.setTime(item?."${constraintDateFieldStart}")
						
						Calendar calendarEnd = new GregorianCalendar()
						calendarEnd.setTime(item?."${constraintDateFieldEnd}")
						
						Calendar calendarItem = new GregorianCalendar(attrs.month.year + 1900, attrs.month.month, i)
						
						if(calendarItem.compareTo(calendarStart) >= 0 && calendarItem.compareTo(calendarEnd) <= 0){
							List eventItems = []
							if(events.containsKey(i)){
								eventItems = events[i]
							}
							
							if(!eventItems.contains(item)){
								eventItems.add(item)
								events[i] = eventItems
							}
						}
					}
					else if(item?."${constraintDateField}"?.month == attrs.month.month){
						if(ignoreYear(attrs?.ignoreYear, item.class.simpleName) || item?."${constraintDateField}".year == attrs.month.year){
							List eventItems = []
							if(events.containsKey(item."${constraintDateField}".date)){
								eventItems = events[item."${constraintDateField}".date]
							}
						
							if(!eventItems.contains(item)){
								eventItems.add(item)
								events[item."${constraintDateField}".date] = eventItems
							}
						}
					}
				}
			}
		}

		// TODO extract method
		def teaser = { String value, int length ->
			if(value.length() > length){
				return value.substring(0, length) + "..."
			}
			else {
				return value
			}
		}
		
		builder.table("class": "calendar"){
			thead {
				tr {
					if(attrs?.weekOfYear && attrs?.weekOfYear == "true"){
						th(attrs?.week)
					}
					th(attrs?.weekDays?.monday)
					th(attrs?.weekDays?.tuesday)
					th(attrs?.weekDays?.wednesday)
					th(attrs?.weekDays?.thursday)
					th(attrs?.weekDays?.friday)
					th(attrs?.weekDays?.saturday)
					th(attrs?.weekDays?.sunday)
				}
			}
			tbody {
				int day = 1
				int lastWeek = 0
				for(int i = 0; i < 6; i++){
					tr {
						//Show week of Year
						if(attrs?.weekOfYear && attrs?.weekOfYear == "true"){
							td("class": "weekOfYear") {
								if(day <= maxDaysOfMonth){
									int weekOfYear = calendar.get(GregorianCalendar.WEEK_OF_YEAR)
									
									// Sometimes there are 53  weeks
									if(weekOfYear >= 1 && lastWeek >= 52){
										weekOfYear = lastWeek + 1
									}
									if(weekOfYear > 53 || (calendar.get(GregorianCalendar.MONTH) == GregorianCalendar.JANUARY) && lastWeek > 50){
										weekOfYear = 1
									}
									
									if(attrs?.weekAction){
										a(href: "${attrs.weekUrl}?week=${weekOfYear}&year=${calendar.get(GregorianCalendar.YEAR)}"){
											h1(weekOfYear)	
										}
									}
									else {
										h1(weekOfYear)	
									}
									
									lastWeek = weekOfYear
								}
							}
						}
						
						for(int k = 0; k < 7; k++){
							int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK)
							int dayOfMonth = calendar.get(GregorianCalendar.DAY_OF_MONTH)
							
							if(dayOfWeek == daysOfWeek[k] && day <= maxDaysOfMonth){								
								boolean today = calendar.get(GregorianCalendar.DAY_OF_WEEK) == calendarToday.get(GregorianCalendar.DAY_OF_WEEK) && calendar.get(GregorianCalendar.DAY_OF_MONTH) == calendarToday.get(GregorianCalendar.DAY_OF_MONTH) && calendar.get(GregorianCalendar.YEAR) == calendarToday.get(GregorianCalendar.YEAR)
								
								td("class": "${today ? 'today ' : ''}day ${day % 2 ? 'evenDay' : 'oddDay'} ${events.containsKey(day) ? 'dayWithItems' : ''}"){
									if(attrs?.dayAction){
										a(href: "${attrs.dayUrl}?day=${day}&month=${month}&year=${year}"){
											h1(day)	
										}
									}
									else {
										h1(day)	
									}
									
									ul {
										if(events.containsKey(day)){
											events[day].each { eventItem ->										
												li {
													String title = getDisplayField(attrs?.displayField, eventItem)
													
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
														String itemUrl = getItemUrl(attrs?.itemUrls, attrs?.itemUrl, eventItem)
														
														a(href: "${itemUrl}", title)
														//a(href: attrs.itemUrl.replace("itemId", "${eventItem?.id}"), title)
													}
													else {
														span(title)
													}
												}
											}	
										}
									}
								}
								calendar.add(GregorianCalendar.DAY_OF_MONTH, 1)
								day += 1
							}
							else {
								td("class": "day outsideOfMonth")
							}
						}	
					}
				}
			}
		}
	}

	private boolean ignoreYear(Object ignoreYear, String className){
		if(ignoreYear instanceof String){
			return ignoreYear == "true"
		}
		else if(ignoreYear instanceof Map){
			if(ignoreYear.containsKey(className)){
				return ignoreYear[className] == "true"
			}
		}

		return false
	}
	
	private Object getDisplayField(Object displayField, Object item){
		String result = item.toString()
		String className = item.class.simpleName
		
		if(displayField instanceof String){
			try {
				result = item."${displayField}"	
			}
			catch(Exception e){
				//log.error("Unexpected error using displayField", e)
			}
		}
		else if(displayField instanceof Map && displayField.containsKey(className)){
			try {
				result = item."${displayField[className]}"	
			}
			catch(Exception e){
				//log.error("Unexpected error using displayField", e)
			}			
		}

		return result
	}
	
	private String getItemUrl(Object itemUrls, String itemUrl, Object item){
		String url = ""
		
		if(itemUrls instanceof Map && itemUrls.containsKey(item.class.simpleName)){
			url = itemUrls[item.class.simpleName]
		}
		else if(itemUrl){
			url = itemUrl
		}
		
		return url.replace("itemId", "${item?.id}")
	}
	
	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		// CSS 
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()
		if(!attrs.skin || attrs.skin == "default"){
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/calendarmonthview.css")	
			css.name = "${resourcePath}/css/calendarmonthview.css"
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
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/calendarmonthview.css")	
		}
		else {
			String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
			builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
		}
	}

}