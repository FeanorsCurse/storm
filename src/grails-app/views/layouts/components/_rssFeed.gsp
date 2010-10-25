<!-- 
This layout lists the RSS Feeds dynamically
@author: Gerrit
 -->

<%@ page import="systemadministration.modulmanagement.RssFeed"%>
<g:if test="${RssFeed.findAllByActive(true).size()>0}">
	
	<g:each in="${RssFeed.findAllByActive(true)}" var="feed">
		<g:feed feed="${feed}"></g:feed>
	</g:each>
</g:if>