
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="shemaEditor.chart.Chart"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:set var="entityName"
	value="${message(code: 'indicator.label', default: 'Indicator')}" />
<title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="body"><g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> <g:hasErrors bean="${indicatorInstance}">
	<div class="errors"><g:renderErrors bean="${indicatorInstance}"
		as="list" /></div>
</g:hasErrors> <g:form method="post">
	<g:hiddenField name="id" value="${indicatorInstance?.id}" />
	<g:hiddenField name="version" value="${indicatorInstance?.version}" />
	<div class="dialog">
	<table class="list">
		<tbody>

			<tr>
				<td valign="top" align="left" class="name" width="35%"><label
					for="name"><g:message code="indicator.name.label"
					default="Name" /></label></td>
				<td valign="top" align="left"
					class="value ${hasErrors(bean: indicatorInstance, field: 'name', 'errors')}"
					width="65%"><g:textField name="name"
					value="${indicatorInstance?.name}" /></td>
			</tr>

			<tr>
				<td valign="top" align="left" class="name"><label
					for="description"><g:message
					code="indicator.description.label" default="Description" /></label></td>
				<td valign="top" align="left"
					class="value ${hasErrors(bean: indicatorInstance, field: 'description', 'errors')}">
				<g:textArea name="description"
					value="${indicatorInstance?.description}" rows="4" /></td>
			</tr>

			<tr>
				<td valign="top" align="left" class="name"><label for="unit"><g:message
					code="indicator.unit.label" default="Unit" /></label></td>
				<td valign="top" align="left"
					class="value ${hasErrors(bean: indicatorInstance, field: 'unit', 'errors')}">
				<g:textField name="unit" value="${indicatorInstance?.unit}" /></td>
			</tr>

			<tr>
				<td valign="top" align="left" class="name"><label
					for="category"><g:message code="indicator.category.label"
					default="Category" /></label></td>
				<td valign="top" align="left"
					class="value ${hasErrors(bean: indicatorInstance, field: 'category', 'errors')}">
				<g:select name="category"
					from="${shemaEditor.indicatorAdministration.Category.list()}"
					optionKey="id" value="${indicatorInstance?.category?.id}" /></td>
			</tr>


			<g:if test="${indicatorInstance.isIndicator()}">
				<tr>


					<td valign="top" class="name"><label for="chart"> <g:message
						code="indicator.chart.label" default="Chart" /> </label></td>

					<td valign="top"
						class="value ${hasErrors(bean: indicatorInstance, field: 'chart', 'errors')}">

					<g:select name="defaultChart" noSelection="${['0':message(code:'indicator.create.chart')]}"
						from="${Chart.list()}" optionKey="id"
						value="${indicatorInstance?.defaultChart?.id}">
					</g:select></td>


				</tr>
			</g:if>

		</tbody>
	</table>
	</div>
	<p>
	<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
	</div>
	</p>
</g:form></div>
</body>
</html>
