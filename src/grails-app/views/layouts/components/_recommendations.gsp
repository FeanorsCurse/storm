<!--
  This component lists the profile menu if the user is logged in
  @author: Frank
  @changed 28.08.2010: Gerrit
-->

<%@ page import="systemadministration.recommender.WelcomeService"%>			

<div class="title"><g:message code="sidebar.last.viewed.articles.title" default="Last viewed Articles" /></div>

<ul>
	<g:each var="article" in="${WelcomeService.getLastViewedArticles(session.user,5,0) }">
		<li><g:link controller="article" action="display" id="${article.id}">${article.name}</g:link></li>
	</g:each>
</ul>