<!--
  This component lists all Helparticles with the Status "approved" in the Sidebar menu


  TODO: List only Help for the specified language

  @author: Desislava
  @changed: Rachid 10.08.2010
-->

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="interactiveFeatures.Infocart.*"%>
<%@ page import="systemadministration.modulmanagement.Language"%>


<!-- Sidebar Menu -->
<%
//Desislava: set default language german (de)
if(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']==null){
	session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']= new Locale("de")
}
%>

<ul class="publishedinfocartsnavlist">
<%
//Desislava: set default language german (de)
if(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']==null){
	session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']= new Locale("de")
}
%>
	<g:each var="infocart" in="${Infocart.findAllByPublished(true)}" >
		<li>
                    <g:link controller="infocart" action="display" id="${infocart?.id}">${infocart?.name}</g:link>
                </li>
	</g:each>
</ul>