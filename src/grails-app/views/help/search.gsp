<%--
@autor: Rachid, Frank
--%>

<%@ page import="home"%>
<%@ page import="interactiveFeatures.Help.HelpArticle"%>
<%@ page import="interactiveFeatures.Help.HelpSection"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title><g:message code="help.search.label" default="Help Search" /></title>
<resource:autoComplete skin="default/autocomplete" />
</head>
<body>
<h1><g:message code="help.search.label" default="Help Search" /></h1>

<g:form controller="help" action="search">
	<table>
		<tr>
			<td><richui:autoComplete name="searchfor" maxResultsDisplayed="10" minQueryLength="3" value="${params.searchfor}" style="width:200px;" action="${createLinkTo('dir': 'help/autocomplete')}" /></td>
			<td><g:actionSubmit action="search" value="${message(code: 'button.searchhelp.label', default: 'Search')}" /></td>
		</tr>
	</table>
</g:form>
<br>
<br>

<g:if test="${!helpArticleInstanceList}">
	<%--
        <g:if test="${!searchkey}">
                <g:message code="results.nokey.label" default="No key given" />
        </g:if>
        <g:else>
                <g:message code="results.notfound.label" default="No results found for key" /> ${searchkey}
        </g:else>
--%>
</g:if>
<%--
Search on Root-Element shall not be allowed
Frank Gerken
--%>
<g:elseif test="${searchkey == 'Root' || searchkey == 'root'}">
	<g:message code="results.notallowed.label" default="No Search allowed" />
</g:elseif>
<g:else>

	<table class="list">
		<g:set var="counter" value="${1}" />
		<g:each in="${helpArticleInstanceList}" status="i" var="helpArticleInstance">
			<tr>
				<g:if test="${helpArticleInstance.title!= 'Root'}">
					<td>${counter}</td>
					<g:set var="counter" value="${counter + 1}" />
					<td><g:link controller="help" action="article" id="${helpArticleInstance?.id}">${helpArticleInstance.title}</g:link></td>
				</g:if>
			</tr>
		</g:each>
	</table>
</g:else>

</body>
</html>