<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="article.show" default="Article details" /></title>

<g:render template="/layouts/components/richtexteditor" />
    

</head>
<body>
<div class="nav">
      <span class="menuButton">
        <g:link class="list" controller="report" action="list">
          <g:message code="article.list.reports.list" default="List Reports"/>
        </g:link>
      </span>&nbsp;
<span class="menuButton"><g:link class="list" action="list">
	<g:message code="article.show.list" default="Article list" />
</g:link></span></div>
<div class="body">
<h1><g:message code="article.show" default="Article details" /></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<div class="dialog">
<table class="list">
	<tr>
		<th>Attribute</th>
		<th>Value</th>
	</tr>
	<tbody>
		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.id" default="Id" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "id")}</td>

		</tr>

		<tr class="odd">
			<td valign="top" class="name"><g:message code="article.show.name" default="Name" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "name")}</td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.content" default="Content" /></td>

			<td valign="top" class="value">${articleInstance?.content}</td>

		</tr>

		<tr class="odd">
			<td valign="top" class="name"><g:message code="article.show.status" default="Status" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "status")}</td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.author" default="Autor" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "author")}</td>

		</tr>

		<tr class="odd">
			<td valign="top" class="name"><g:message code="article.show.parentarticle" default="ParentArticle" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "parentArticle")}</td>

		</tr>
		
		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.report" default="Report" /></td>

			<td valign="top" class="value">${articleReport}</td>

		</tr>

		<tr class="odd">
			<td valign="top" class="name"><g:message code="article.show.sequence" default="Sequence" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "sequence")}</td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.tags" default="Tags" /></td>

			<td valign="top" class="value">${fieldValue(bean: articleInstance, field: "tags")}</td>

		</tr>



		<tr class="odd">
			<td valign="top" class="name"><g:message code="article.show.dateCreated" default="Date created" /></td>

			<td valign="top" class="value"><g:formatDate date="${reportInstance?.DateCreated}" /></td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message code="article.show.LastUpdated" default="Last updated" /></td>

			<td valign="top" class="value"><g:formatDate date="${reportInstance?.lastUpdated}" /></td>

		</tr>

	</tbody>
</table>
</div>

<div class="buttons"><g:form>
	<g:hiddenField name="id" value="${articleInstance?.id}" />
	<span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'article.show.button.edit', default: 'Edit')}" /></span>
	<span class="button"><g:actionSubmit class="cancel" action="list" value="${message(code: 'article.show.button.cancel', default: 'Abbrechen')}" /></span>
</g:form></div>
</div>
</body>
</html>
