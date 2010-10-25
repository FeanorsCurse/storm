<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.ReportEditor.Report"%>
<%@ page import="ReportEditorMain.Enum.ReportStatus"%>
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<%@ page import="systemadministration.usermanagement.User"%>
<%@ page import="systemadministration.modulmanagement.Language"%>

<html>
<head>
<g:render template="/layouts/components/richtexteditor" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="report.create" default="Create new Report" /></title>
</head>
<body>
<div class="nav"><span class="menuButton"> <g:link class="link" action="list">
	<g:message code="report.create.link.report.list" default="Reports" />
</g:link> </span></div>

<div class="body">
<h1><g:message code="report.create" default="Create new Report" /></h1>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if> 
<g:hasErrors bean="${reportInstance}">
	<div class="errors"><g:renderErrors bean="${reportInstance}" as="list" /></div>
</g:hasErrors> 
<g:form action="save" method="post">
	<div class="dialogs">
	<table class="list">
		<tbody>
			<tr class="prop">
				<td valign="top" class="name"><label for="name"> <g:message code="report.create.name" default="Name" /> </label></td>
				<td valign="top" class="value ${hasErrors(bean: reportInstance, field: 'name', 'errors')}"><g:textField name="name" maxlength="100" value="${reportInstance?.name}" /></td>
			</tr>
	</table>
	<div>
		<textarea name="description" height="500" width="100%"/>${reportInstance?.description}</textarea>
	</div>
	<table class="list">
		<tr class="prop">
			<td valign="top" class="name"><label for="scheme"> <g:message code="report.create.scheme" default="Scheme" /> </label></td>
			<td valign="top" class="value ${hasErrors(bean: reportInstance, field: 'treport', 'errors')}">
			<table class="list">
				<tr>
					<th></th>
					<th><g:message code="report.create.scheme.name" default="Name" /></th>
					<th><g:message code="report.create.scheme.description" default="Description" /></th>
				</tr>
				<g:each in="${TReport.list()}" status="i" var="treport">
					<g:if test="${i%2==0 }">
						<tr class="even">
					</g:if>
					<g:else>
						<tr class="odd">
					</g:else>
						<g:if test="${params.treportid==String.valueOf(treport.id)}">
							<td><g:radio name="treportid" value="${treport.id}" checked="true" /></td>
						</g:if>
						<g:else>
							<td><g:radio name="treportid" value="${treport.id}" /></td>
						</g:else>
						<td>${treport.name}</td>
						<td>${treport.description}</td>
					</tr>
				</g:each>
			</table>
			</td>
		</tr>
		<tr class="prop">
			<td valign="top" class="name"><label for="language"> <g:message code="report.create.language" default="Language" /> </label></td>
			<td valign="top" class="value ${hasErrors(bean: reportInstance, field: 'language', 'errors')}"><g:select name="language.id" from="${Language.findAllByEnabledAndReport(true,true)}" optionKey="id" value="${reportInstance?.language?.id}" /></td>
		</tr>
		</tbody>
	</table>
	</div>
	<div class="buttons"><span class="button"> <g:submitButton name="create" class="save" value="${message(code: 'report.create.button.create', default: 'Create')}" /> </span> <span class="button"> <g:actionSubmit class="cancel" action="list" value="${message(code: 'report.create.button.cancel', default: 'Cancel')}" onclick="return confirm('${message(code: 'default.button.cancel.confirm.message', default: 'Are you sure?')}');" /> </span></div>
</g:form></div>
</body>
</html>
