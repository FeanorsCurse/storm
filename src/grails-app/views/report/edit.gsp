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
	<g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
	<title>
		<g:message code="report.edit.label" args="[entityName]" />
	</title>
</head>

<body>
	<div class="nav">
		<span class="menuButton">
			<g:link 
				class="list" action="list"><g:message code="report.edit.report.list" args="[entityName]"/> | 
			</g:link>
		</span>
		<span class="menuButton">
			<g:link 
				class="create" action="create"><g:message code="report.edit.report.create" args="[entityName]" />
			</g:link>
		</span>
	</div>
	<div class="body">
		<h1>
			<g:message code="report.edit.report" args="[entityName]" />
		</h1>
	<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
	</g:if> 
	<g:hasErrors bean="${reportInstance}">
		<div 
			class="errors"><g:renderErrors bean="${reportInstance}" as="list" />
		</div>
	</g:hasErrors> <g:form method="post">
		<g:hiddenField name="id" value="${reportInstance?.id}" />
		<g:hiddenField name="version" value="${reportInstance?.version}" />
		<div 
			class="dialog">
			<table class="list">
				<tr class="prop">
					<td valign="top" class="name"><label for="name"><g:message
						code="report.edit.name" default="Name" /></label></td>
					<td valign="top"
						class="value ${hasErrors(bean: reportInstance, field: 'name', 'errors')}">
					<g:textField name="name" maxlength="100"
						value="${reportInstance?.name}" /></td>
				</tr>
			</table>
			<textarea name="description" height="500" width="100%"/>${reportInstance?.description}</textarea>
			<table class="list">	
				<tr class="prop">
					<td 
						valign="top" class="name">
						<label 
							for="language"><g:message code="report.edit.language" default="Language" />
						</label>
					</td>
					<td 
						valign="top" class="value ${hasErrors(bean: reportInstance, field: 'language', 'errors')}">
						<g:select name="language.id" from="${Language.list()}" optionKey="id" value="${reportInstance?.language?.id}" />
					</td>
				</tr>
			</table>
		</div>
		<div 
			class="buttons">
			<g:if test="${reportInstance.status.status=='Approved'||reportInstance.status.status=='Closed'}">
				<g:checkAccess modulname="report" actionname="open">
					<g:actionSubmit class="open" action="open" value="${message(code: 'default.button.open.label', default: 'Öffnen')}" />
				</g:checkAccess>
			</g:if>
			
			<g:if test="${reportInstance.status.status=='Development'||reportInstance.status.status=='New'}">
				<g:checkAccess modulname="report" actionname="update">
					<g:actionSubmit class="update" action="update" id="${reportInstance.id }" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</g:checkAccess>
				
				<g:checkAccess modulname="report" actionname="approve">
					<g:actionSubmit class="approve" action="approve" id="${reportInstance.id }" value="${message(code: 'default.button.publish.label', default: 'Publish')}" />
				</g:checkAccess>
				
				<g:checkAccess modulname="report" actionname="close">
					<g:actionSubmit class="close" action="close" id="${reportInstance.id }" value="${message(code: 'default.button.close.label', default: 'Close')}" />
				</g:checkAccess>
				<g:checkAccess modulname="report" actionname="copy">
					<g:actionSubmit class="copy" action="copy" id="${reportInstance.id }" value="${message(code: 'default.button.copy.label', default: 'Copy')}" />
				</g:checkAccess>
			</g:if>
			<g:actionSubmit class="cancel" action="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
		</div>
	</g:form>
</div>
</body>
</html>
