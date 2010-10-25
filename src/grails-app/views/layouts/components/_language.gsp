<!--
This layout lists the Languages dynamically
@author: Frank
@changed 28.08.2010: Gerrit
 -->
<%@ page import="systemadministration.modulmanagement.Language"%>
<form action="" name="language" id="language" method="get"><select name="lang">
	<g:each in="${Language.findAllByEnabledAndSite(true,true)}" var="language">
		<option value="${language.countryCode}" class="${language.name}" <g:if test="${language.countryCode==session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'].toString()}"> selected="selected"</g:if>>${language.name}</option>
	</g:each>
</select>
	<%
		//pass parameters
		for(Map.Entry e : params.entrySet()){
			String key = e.getKey() 
			String value = e.getValue()
			if(key!="controller"&&key!="action"&&key!="lang"){
				out << "<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\">"
			}
		}
	%> 
<input type="submit" value="<g:message code="select.list" default="Select" />"></input></form>
