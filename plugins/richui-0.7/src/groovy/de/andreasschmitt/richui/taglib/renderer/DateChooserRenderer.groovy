package de.andreasschmitt.richui.taglib.renderer

import groovy.xml.MarkupBuilder
import java.text.SimpleDateFormat
import java.text.DateFormat
import de.andreasschmitt.richui.taglib.Resource

/*
*
* @author Andreas Schmitt
*/
class DateChooserRenderer extends AbstractRenderer {
	
	protected void renderTagContent(Map attrs, MarkupBuilder builder) throws RenderException {
		renderTagContent(attrs, null, builder)
	}
	
	protected void renderTagContent(Map attrs, Closure body, MarkupBuilder builder) throws RenderException {
		String id = "c" + RenderUtils.getUniqueId()
		String inputId = "i" + RenderUtils.getUniqueId()
		
		if(!attrs.id){
			attrs.id = attrs.name
		}
		
		if(attrs?.inputId){
			inputId = attrs.inputId
		}
		
		if(!attrs?.'class'){
			attrs.'class' = ""
		}
		
		if(!attrs?.style){
			attrs.style = ""
		}
		
		if(!attrs.timezone) {
            attrs.timezone = TimeZone.getDefault()
		}
		
		String formattedValue = ""
		String day = ""
		String month = ""
		String year = ""
		String hour = "00"
		String minute = "00"
		
		if(attrs?.value) {
			try {
				DateFormat fmt = new SimpleDateFormat(attrs.format)
				fmt.setTimeZone(attrs.timezone)
				formattedValue = fmt.format(attrs.value)
				
				Calendar cal = new GregorianCalendar(attrs.timezone)
				cal.setTime(attrs.value)
				day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH))
				month = Integer.toString(cal.get(Calendar.MONTH)+1)
				year = Integer.toString(cal.get(Calendar.YEAR))
				
				hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY))
				if(hour == "0"){
					hour = "00"
				}
				
				minute = Integer.toString(cal.get(Calendar.MINUTE))
				if(minute == "0"){
					minute = "00"
				}
			}
			catch(Exception e){
				log.error("Error formatting date", e)
			}
		}		
		
		//Default HTML attributes
		Map htmlAttributes = ["class": "${attrs?.'class'}", style: "${attrs?.style}", type:"text", name: "${inputId}", id: "${inputId}", value: "${formattedValue}"]

		//Add additional attributes
		attrs.each { key, value ->
			if(key.startsWith("html:")){
				htmlAttributes[key.replace("html:", "")] = value
			}
		}		
		
		builder.input(htmlAttributes)
		builder.div("id": id, "class": "datechooser yui-skin-sam", "")
			
		builder.script(type: "text/javascript"){
			builder.yieldUnescaped "	var dateChooser = new DateChooser();\n"
			builder.yieldUnescaped "	dateChooser.setDisplayContainer(\"$id\");\n"
			builder.yieldUnescaped "	dateChooser.setInputId(\"${inputId}\");\n"
			builder.yieldUnescaped "	dateChooser.setStructId(\"${attrs?.id}\");\n"
			builder.yieldUnescaped "	dateChooser.setFormat(\"${attrs?.format}\");\n"
			if(attrs?.locale){
				builder.yieldUnescaped "	dateChooser.setLocale(\"${attrs?.locale}\");\n"
			}

			// Add callbackHandler for focus, blur and change
			if(attrs?.onFocus){
				builder.yieldUnescaped "  dateChooser.setFocusCallback(\"${attrs?.onFocus}\");\n"	
			}
			if(attrs?.onBlur){
				builder.yieldUnescaped "  dateChooser.setBlurCallback(\"${attrs?.onBlur}\");\n"	
			}
			if(attrs?.onChange){
				builder.yieldUnescaped "  dateChooser.setChangeCallback(\"${attrs?.onChange}\");\n"	
			}

			if(attrs?.navigator){
				builder.yieldUnescaped "	dateChooser.setNavigator(${attrs?.navigator});\n"
			}
			
			if(attrs?.firstDayOfWeek){
				Map days = [su: 0, mo: 1, tu: 2, we: 3, th: 4, fr: 5, sa: 6]
				
				if(days.containsKey(attrs.firstDayOfWeek.toLowerCase())){
					String dayOfWeek = days[attrs.firstDayOfWeek.toLowerCase()]
					builder.yieldUnescaped "	dateChooser.setFirstDayOfWeek(\"${dayOfWeek}\");\n"	
				}
			}
			builder.yieldUnescaped "	dateChooser.init();\n"
		}
		
		builder.input(type: "hidden", name: "${attrs?.name}", id: "${attrs?.id}", value: "struct")
		
		if(attrs?.time){
			builder.input("class": "${attrs?.hourClass}", style: "${attrs?.hourStyle}", type: "text", name: "${attrs?.name}_hour", id: "${attrs?.id}_hour", value: hour)
			builder.yieldUnescaped ":"
			builder.input("class": "${attrs?.minuteClass}", style: "${attrs?.minuteStyle}", type: "text", name: "${attrs?.name}_minute", id: "${attrs?.id}_minute", value: minute)	
		
		}
		
		builder.input(type: "hidden", name: "${attrs?.name}_day", id: "${attrs?.id}_day", value: day)
		builder.input(type: "hidden", name: "${attrs?.name}_month", id: "${attrs?.id}_month", value: month)
		builder.input(type: "hidden", name: "${attrs?.name}_year", id: "${attrs?.id}_year", value: year)
	}

	protected List<Resource> getComponentResources(Map attrs, String resourcePath) throws RenderException {
		List<Resource> resources = []
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		// CSS
		Resource css = new Resource()
		
		def cssBuilder = css.getBuilder()		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/datechooser.css")
				css.name = "${resourcePath}/css/datechooser.css"
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
				css.name = "${applicationResourcePath}/css/${attrs.skin}.css"
			}
		}
		else {
			cssBuilder.link(rel: "stylesheet", type: "text/css", href: "${resourcePath}/css/datechooser.css")	
			css.name = "${resourcePath}/css/datechooser.css"
		}
		
		resources.add(css)
		
		
		// Calendar css
		Resource calendarCss = new Resource()
		calendarCss.name = "${yuiResourcePath}/calendar/assets/calendar.css"
		
		def calendarCssBuilder = calendarCss.getBuilder()
		calendarCssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/calendar/assets/calendar.css", "")
		
		resources.add(calendarCss)
		

		// Calendar skin css
		Resource calendarSkinCss = new Resource()
		calendarSkinCss.name = "${yuiResourcePath}/calendar/assets/skins/sam/calendar.css"
		
		def calendarSkinCssBuilder = calendarSkinCss.getBuilder()
		calendarSkinCssBuilder.link(rel: "stylesheet", type: "text/css", href: "${yuiResourcePath}/calendar/assets/skins/sam/calendar.css")
		
		resources.add(calendarSkinCss)

		
		// Yahoo dom event
		Resource yahooDomEvent = new Resource()
		yahooDomEvent.name = "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js"
		
		def yahooDomEventBuilder = yahooDomEvent.getBuilder()
		yahooDomEventBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/yahoo-dom-event/yahoo-dom-event.js", "")
		
		resources.add(yahooDomEvent)
		
		
		// Date chooser
		Resource dateChooser = new Resource()
		dateChooser.name = "${resourcePath}/js/datechooser/datechooser.js"
		
		def dateChooserBuilder = dateChooser.getBuilder()
		dateChooserBuilder.script(type: "text/javascript", src: "${resourcePath}/js/datechooser/datechooser.js", "")
		
		resources.add(dateChooser)


		// Yahoo calendar min
		Resource yahooCalendarMin = new Resource()
		yahooCalendarMin.name = "${yuiResourcePath}/calendar/calendar-min.js"
		
		def yahooCalendarMinBuilder = yahooCalendarMin.getBuilder()
		yahooCalendarMinBuilder.script(type: "text/javascript", src: "${yuiResourcePath}/calendar/calendar-min.js", "")
		
		resources.add(yahooCalendarMin)

		return resources
	}	
	
	protected void renderResourcesContent(Map attrs, MarkupBuilder builder, String resourcePath) throws RenderException {			
		builder.yieldUnescaped "<!-- DateChooser -->"
		
		String yuiResourcePath = YuiUtils.getResourcePath(resourcePath, attrs?.remote != null)
		
		if(attrs?.skin){
			if(attrs.skin == "default"){
				builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/datechooser.css")
			}
			else {
				String applicationResourcePath = RenderUtils.getApplicationResourcePath(resourcePath)
				builder.link(rel: "stylesheet", type: "text/css", href: "${applicationResourcePath}/css/${attrs.skin}.css")
			}
		}
		else {
			builder.link(rel: "stylesheet", type: "text/css", href: "$resourcePath/css/datechooser.css")	
		}
		
		builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/calendar/assets/calendar.css")
		builder.link(rel: "stylesheet", type: "text/css", href: "$yuiResourcePath/calendar/assets/skins/sam/calendar.css")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/yahoo-dom-event/yahoo-dom-event.js", "")
		builder.script(type: "text/javascript", src: "$resourcePath/js/datechooser/datechooser.js", "")
		builder.script(type: "text/javascript", src: "$yuiResourcePath/calendar/calendar-min.js", "")
	}
}