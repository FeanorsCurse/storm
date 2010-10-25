
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="shemaEditor.chart.Chart"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:set var="entityName"
	value="${message(code: 'indicator.label', default: 'Indicator')}" />
<title><g:message code="default.create.label"
	args="[entityName]" /></title>
<g:javascript library="prototype" />


<script type="text/javascript">

function hideElem(){
	document.getElementById('chart1').style.visibility = 'hidden'; 
	document.getElementById('chart2').style.visibility = 'hidden'; 
}

function showElem(){

	document.getElementById('chart1').style.visibility = 'visible'; 
	document.getElementById('chart2').style.visibility = 'visible'; 
}
</script>
</head>
<body>
<p>
<div class="nav" align="right"><span class="menuButton"><g:link
	class="list" action="list">
	<g:message code="default.list.label" args="[entityName]" />
</g:link></span></div>
</p>
<div class="body">
<h1><g:message code="default.create.label" args="[entityName]" /></h1>

<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if> 

<g:hasErrors bean="${indicatorInstance}">
	<div class="errors"><g:renderErrors bean="${indicatorInstance}"
		as="list" /></div>
</g:hasErrors> <g:form action="save" method="post">
	<div class="dialogs">
	<table class="list">
		<tbody>


			<tr class="prop">
				<td valign="top" class="name"><label for="name"><g:message
					code="indicator.isindicator.label"
					default="Indicator or Content-Guideline" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'isindicator', 'errors')}">

				<g:radio name="indicator" value="true" checked="true"
					onclick="showElem();" /> <g:message code="indicator.tooltip.label" default="Indicator" /><br>

				<g:radio name="indicator" value="false" onclick="hideElem();" />
				<g:message code="guideline.tooltip.label" default="Content-Guideline" /></td>
			</tr>


			<tr class="prop">
				<td valign="top" class="name"><label for="name"><g:message
					code="indicator.name.label" default="Name" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'name', 'errors')}">
				<g:textField name="name" value="${indicatorInstance?.name}"
					STYLE="width: 200px" size="10" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="shortName"><g:message
					code="indicator.shortName.label" default=Shortname /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'shortName', 'errors')}">
				<g:textField name="shortName"
					value="${indicatorInstance?.shortName}" STYLE="width: 200px"
					size="10" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="category"><g:message
					code="indicator.category.label" default="Category" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'category', 'errors')}">

				<g:select name="category.id" noSelection="${['0':message(code:'category.list.label')]}"
					from="${shemaEditor.indicatorAdministration.Category.list()}"
					optionKey="id" value="${indicatorInstance?.category?.id}">
				</g:select></td>
			</tr>


			<tr class="prop">
				<td valign="top" class="name"><label for="description"><g:message
					code="indicator.description.label" default="Description" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'description', 'errors')}">
				<g:textArea name="description"
					value="${indicatorInstance?.description}" cols="40" rows="5" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="unit"><g:message
					code="indicator.unit.label" default="Unit" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'unit', 'errors')}">
				<g:textField name="unit" value="${indicatorInstance?.unit}" /></td>
			</tr>


			<tr class="prop">


				<td valign="top" class="name"><label for="chart">
				<div id="chart1"><g:message code="indicator.chart.label"
					default="Chart" />
				</label>
				</div>
				</td>




				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'chart', 'errors')}">

				<div id="chart2"><g:select name="chart"
					noSelection="${['0':message(code:'indicator.create.chart')]}" from="${Chart.list()}"
					optionKey="id" value="${indicatorInstance?.defaultChart?.id}">
				</g:select></div>

				</td>


			</tr>


			<tr class="prop">
				<td valign="top" class="name"><label for="creator"><g:message
					code="indicator.creator.label" default="Creator" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: indicatorInstance, field: 'creator', 'errors')}">
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
