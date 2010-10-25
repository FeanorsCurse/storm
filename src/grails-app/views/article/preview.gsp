
<%@ page import="ReportEditorMain.ArticleEditor.Article" %>
<%@ page import="storm.ReportTagLib" %>
<%@ page import="systemadministration.recommender.WelcomeService" %>
<%@ page defaultCodec="none" %>

<html>
  <head>
	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	   <meta name="layout" content="main" />
	  <title><g:message code="article.preview.label" default="Preview"/></title>
	</head>
<body>
  <div class="body">
    <div id="articlename"><h1><g:message code="article.preview.label" default="Preview: "/> ${articleInstance.number} ${articleInstance.name}</h1></div>
    <div id="articlecontent">${articleInstanceContent}</div>
    <div id="articleindicators">
    	<h2><g:message code="article.preview.indicator" default="Indicators" /></h2>
        <table>
        <tr>
        	<th><g:message code="article.preview.name" default="Name" /></th>
        	<th><g:message code="article.preview.value" default="Value" /></th>
        	<th><g:message code="article.preview.unit" default="Unit" /></th>
        </tr>
            <g:each in="${artIndList}" status="i" var="artInd">
            
                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                    <td><g:link controller="indicator" action="show" id="${artInd?.indicator?.id}">${artInd?.indicator?.name}</g:link></td>
                    <td>${artInd?.value}</td>
                    <td>${artInd?.indicator?.unit}</td>
                </tr>
            </g:each>
     </table>
     <br>
	<g:link action="edit" id="${articleInstance.id}">
		<g:message code="article.preview.button.edit" default="Edit" />
	</g:link>
	</div>
  </div>
</body>
</html>
