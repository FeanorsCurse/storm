
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:set var="entityName"
	value="${message(code: 'scheme.show.title', default: 'TReport')}" />
<title><g:message code="scheme.create.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav"><span class="menuButton"><g:link
	class="list" action="list">
	<g:message code="scheme.list.label" args="[entityName]" />
</g:link></span></div>
<div class="body">
<h1><g:message code="scheme.create.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> <g:hasErrors bean="${TReportInstance}">
	<div class="errors"><g:renderErrors bean="${TReportInstance}"
		as="list" /></div>
</g:hasErrors> <g:form action="save" method="post">
	<div class="dialogs">
	<table class="list">
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="reportname"><g:message
					code="scheme.name.label" default="Name" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TReportInstance, field: 'name', 'errors')}">
				<g:textField name="reportname" value="${TReportInstance?.name}"
					size="27" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="root"><g:message
					code="scheme.root.label" default="Title" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TReportInstance, field: 'root', 'errors')}">
				<g:textField name="name" optionKey="name" WIDTH="400"
					STYLE="width: 174px" /></td>
			</tr>



			<tr class="prop">
				<td valign="top" class="name"><label for="description"><g:message
					code="scheme.description.label" default="Description" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TReportInstance, field: 'description', 'errors')}">
				<g:textArea name="description"
					value="${TReportInstance?.description}" STYLE="width: 350px"
					rows="6" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="creator"><g:message
					code="scheme.creator.label" default="Creator" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TReportInstance, field: 'creator', 'errors')}">
					${session.user}
				</td>
			</tr>

		</tbody>
	</table>
	</div>
	<div class="buttons"><span class="button"><g:submitButton
		name="create" class="save"
		value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
	</div>
</g:form></div>
</body>
</html>
