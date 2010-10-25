<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.ReportEditor.Report"%>
<html>
<head>
<g:render template="/layouts/components/richtexteditor" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="article.create" default="Create Article" /></title>
</head>
<body>
<div class="nav">
    <span class="menuButton">
      <g:link class="list" controller="report" action="list">
        <g:message code="article.list.reports.list" default="List Reports"/>
      </g:link>
    </span>&nbsp;
	<span class="menuButton">
		<g:link class="list" controller="article" action="list"><g:message code="article.create.list" default="List Articles" /></g:link>
	</span>
</div>
<div class="body">

<h1><g:message code="article.create.label" default="Create Article" /></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if> <g:hasErrors bean="${articleInstance}">
	<div class="errors"><g:renderErrors bean="${articleInstance}" as="list" /></div>
</g:hasErrors> <g:form action="save" method="post">
	<div class="dialog">
	<table class="list">
		<tr class="prop">
			<td valign="top" class="name"><label for="name"><g:message code="article.create.name" default="Name" /></label></td>
			<td valign="top" class="value ${hasErrors(bean: articleInstance, field: 'name', 'errors')}"><g:textField name="name" value="${articleInstance?.name}" /></td>
		</tr>
	</table>
	<textarea name="content" height="500" width="100%"/>${articleInstance?.content}</textarea>
	<table class="list">
			<tr class="prop">
				<td valign="top" class="name"><label for="parentArticle"><g:message code="article.create.parentarticle" default="ParentArticle" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: articleInstance, field: 'parentArticle', 'errors')}"><g:select name="parentArticle.id" from="${articleInstanceList}" optionKey="id" value="${articleInstance?.parentArticle?.id}" /></td>
			</tr>

			<tr class="odd">
				<td valign="top" class="name"><label for="sequence"><g:message code="article.create.sequence" default="Sequence" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: articleInstance, field: 'sequence', 'errors')}"><g:textField name="sequence" value="${articleInstance?.sequence}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="tags"><g:message code="article.create.tags" default="Tags" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: articleInstance, field: 'tags', 'errors')}"><g:textField name="tags" value="${articleInstance?.tags}" /></td>
			</tr>
			</tbody>
	</table>
	</div>

	<div class="buttons">
		<span class="button"> 
			<g:submitButton name="create" class="save" value="${message(code: 'article.create.button.create', default: 'Create')}" />
		</span>
		<span class="button">
			<g:actionSubmit class="cancel" action="list" value="${message(code: 'article.create,button.cancel', default: 'Cancel')}" />
		</span>
</g:form>
</div>
</div>
</body>
</html>
