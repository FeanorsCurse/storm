<!--
  This component lists all Helparticles with the Status "approved" in the Sidebar menu


  TODO: List only Help for the specified language

  @author: Desislava
  @changed: Rachid 10.08.2010
-->

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="interactiveFeatures.Help.*"%>
<%@ page import="systemadministration.modulmanagement.Language"%>


<!-- Sidebar Menu -->
<%
//Desislava: set default language german (de)
if(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']==null){
	session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']= new Locale("de")
}
%>

<ul class="helpnavsectlist">
<%
//Desislava: set default language german (de)
if(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']==null){
	session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']= new Locale("de")
}
%>
	<g:each var="helpsect" in="${HelpSection.findAll()}" >
		<li>
                    <g:link controller="help" action="section" id="${helpsect?.id}">${helpsect?.name}</g:link>
                    <ul class="helpnavartlist">
                      <g:if test="${helpSection?.id == helpsect?.id || helpArticle?.section?.id == helpsect?.id}">
                    	<g:each var="helpart" in="${helpsect?.articles}" >
                          <li>
                            <g:link controller="help" action="article" id="${helpart?.id}">${helpart?.title}</g:link>
                        </li>
                        </g:each>
                      </g:if>
                    </ul>
                </li>
	</g:each>
</ul>