<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />

<title><g:message code="articleindicator.edit.title" default="Edit indicator" /></title>

</head>
<body>
<div class="body">

<div class="nav"><span class="menuButton"> <g:link class="list" controller="report" action="list">
	<g:message code="articleIndicator.edit.reports.list" default="List Reports" />
</g:link> </span>&nbsp; <span class="menuButton"> <g:link class="list" controller="article" action="list">
	<g:message code="articleIndicator.edit.article.list" default="List articles" />
</g:link> </span></div>

<h1><g:message code="articleindicator.edit.title" default="Edit indicator" /></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if> <g:hasErrors bean="${artIndInstance}">
	<div class="errors"><g:renderErrors bean="${artIndInstance}" as="list" /></div>
</g:hasErrors> <g:form name="form" method="post">
	<g:hiddenField name="id" value="${artIndInstance?.id}" />
	<g:hiddenField name="version" value="${artIndInstance?.version}" />
	<div class="dialog">
	<table>
		<tr class="prop">
			<td valign="top" class="name"><label for="name"><g:message code="articleindicator.edit.name" default="Name" /></label></td>
			<td valign="top" class="value ${hasErrors(bean: artIndInstance, field: 'name', 'errors')}"><g:textField name="name" value="${artIndInstance?.indicator.name}" /></td>
		</tr>
		<tr class="prop">
			<td valign="top" class="value"><label for="value"><g:message code="articleindicator.edit.value" default="Value" /></label></td>
			<td valign="top" class="value ${hasErrors(bean: artIndInstance, field: 'value', 'errors')}"><g:textField name="value" value="${artIndInstance?.value}" /><input type="button" onclick="document.form.value.value=${artIndInstance?.value}" name="assume" value="${message(code: 'articleindicator.edit.button.reset', default: 'Reset')}" /></td>
		</tr>
		<g:if test="${sapvalue!=null}">
			<tr class="prop">
				<td valign="top" class="value"><label for="value"><g:message code="articleindicator.edit.sap.suggestion" default="SAP Suggestion" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: artIndInstance, field: 'value', 'errors')}"><g:textField name="sapSuggestion" value="${sapvalue}" /><input type="button" onclick="document.form.value.value=document.form.sapSuggestion.value" name="assume" value="${message(code: 'articleindicator.edit.button.assume', default: 'Assume')}" /></td>
			</tr>
		</g:if>
		<g:if test="${databaseValue!=null}">
			<tr class="prop">
				<td valign="top" class="value"><label for="value"><g:message code="articleindicator.edit.database.suggestion" default="Database Suggestion" /></label></td>
				<td valign="top" class="value ${hasErrors(bean: artIndInstance, field: 'value', 'errors')}"><g:textField name="databaseSuggestion" value="${databaseValue}" /><input type="button" onclick="document.form.value.value=document.form.databaseSuggestion.value" name="assume" value="${message(code: 'articleindicator.edit.button.assume', default: 'Assume')}" /></td>
			</tr>
		</g:if>
		<tr class="prop">
			<td valign="top" class="name"><label for="unit"><g:message code="articleindicator.edit.unit" default="Unit" /></label></td>
			<td valign="top" class="value ${hasErrors(bean: artIndInstance, field: 'unit', 'errors')}"><g:textField name="unit" value="${artIndInstance?.indicator.unit}" /></td>
		</tr>
		</tbody>
	</table>
	<div class="buttons"><span class="button"> <g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /> </span> <span class="button"> <span class="button"> <g:actionSubmit class="cancel" action="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" /> </span> <span class="button"> <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /> </span></div>
</g:form></div>
</div>
</body>
</html>
