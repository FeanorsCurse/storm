
<%@ page import="systemadministration.usermanagement.User" %>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
   

<div id="lastViewedArticles">
	<g:if test="${lastViewedArticles != null}">
<g:render template="/welcome/lastViewedArticles" lastViewedArticles="${lastViewedArticles}" />

	</g:if>
</div>
    </body>
</html>
