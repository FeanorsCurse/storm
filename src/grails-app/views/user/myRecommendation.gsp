
<%@ page import="systemadministration.usermanagement.User" %>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
   

<div id="articleRecommendationList">
	<g:if test="${articleRecommendationList != null}">
	<g:render template="/welcome/recommendationArticles" articleRecommendationList="${articleRecommendationList}" />

	</g:if>	</div>
	
	<div id="topTenArticle">
<g:if test="${topTenArticleInstanceList != null}">
	<g:render template="/welcome/topArticles" topTenArticleInstanceList="${topTenArticleInstanceList}" />
</g:if>
</div>

    </body>
</html>
