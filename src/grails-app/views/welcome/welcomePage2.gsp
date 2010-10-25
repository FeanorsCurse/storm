
<%@ page import="systemadministration.recommender.Welcome" %>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>  
    </head>
    <body>
    <h2>Herzlich Willkommen bei STORM</h2>	
		<p>Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum.</p>
		<p><a href="">Mehr erfahren...</a></p> 
	
<table>
<tr>
<td style="width:50%;vertical-align:top;padding-right:20px;">

		<div style="cursor:pointer;"><h2>Last answered Questions</h2></div>
	<div id="div13">
		<g:render template="answeredQuestions" />
    </div>

	 		<g:if test="${topTenArticleInstanceList != null}">
		<div style="cursor:pointer;"><h2>Die beliebtesten Artikel</h2></div>
		<div id="div12">
			<g:render template="topArticles" topTenArticleInstanceList="${topTenArticleInstanceList}" />	
		</div>
	</g:if>

</td>
<td style="width:50%;vertical-align:top">

    <div style="background-color:#EFEFEF">
        <h2>Tags</h2>	
		<g:render template="/layouts/components2/tagcloud" />
	</div>

	<h2>Mehr Text</h2>
	<p>Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum. </p>
    

</td>
</tr>
</table>


	<div style="cursor:pointer;"><h2>RSS Feeds</h2></div>
	<div id="div14">
		<g:render template="/layouts/components/rssFeed" />
	</div>
    </body>
</html>
