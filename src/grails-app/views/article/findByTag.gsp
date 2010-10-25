
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.Enum.ArticleStatus"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
<rateable:resources/>
<title><g:message code="article.list.label" default="Article" /></title>
</head>
<body>

<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>

<div class="body">
<h1><g:message code="article.findByTag.label" default="Articles with tag" /> "${tag}"</h1>

<table width="100%">
	<thead>
		<tr>
                  <th align="left">${message(code: 'article.name.label', default: 'Name')}</th>
                  <th align="left">${message(code: 'default.comments.label', default: 'Comments')}</th>
                  <th align="left">${message(code: 'default.rating.label', default: 'Rating')}</th>
		</tr>


	</thead>
	<tbody>
		<tr style="display: none;">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<g:each in="${articleInstanceList}" status="i" var="articleInstance">
                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td align="left"><g:link action="display" id="${articleInstance.id}">${articleInstance?.number} ${articleInstance?.name}</g:link></td>
			<td align="center"><g:link action="display" id="${articleInstance.id}">${articleInstance?.usercomments?.size()}</g:link></td>
                        <td align="left"><g:include controller="article" action="starsrated" id="${articleInstance?.id}"/></td>
                    </tr>
		</g:each>
	</tbody>
</table>
</div>
<div class="paginateButtons" style="margin-top: 20px;"><g:paginate
	total="${articleInstanceTotal}" /></div>
</body>
</html>
