
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<title><g:message code="report.validate.name" args="[entityName]" /></title>
</head>
<body>
<p><g:message code="validated.result.for.report" />
<g:link action="show" controller="report" id="${validatorResult.getReport().id}">
${validatorResult.getReport().getName() }
</g:link>
<g:message code="validated.result.for.treport" />
<g:link action="show" controller="TReport" id="${validatorResult.getTReport().id}">
${validatorResult.getTReport().getName() }
</g:link>
</p>

<p><b>
<g:if test="${validatorResult.isReportValid()}">
<g:message code="report.is.valid" />
</g:if>
<g:else test="${!validatorResult.isReportValid()}">
<g:message code="report.is.notvalid" />
</g:else>
</b></p>

<p><b><g:message code="report.required.indicators" /></b></p>
<g:if test="${validatorResult.getRequiredIndicators()}">
<div id="out" style="border: 1px solid darkgrey; width: 90%;">
<table class="list">
	<tr>
		<th><g:message code="indicator.shortName.label" /></th>
		<th><g:message code="indicator.name.label" /></th>	
	</tr>

	<g:each in="${validatorResult.getRequiredIndicators()}" status="i"
		var="validatorResult">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "shortName")}
			</g:link></td>
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "name")}
			</g:link></td>	
		</tr>
	</g:each>
</table>
</div>
</g:if>

<g:if test="${validatorResult.getRequiredIndicators().isEmpty()}">
<p><g:message code="report.nomissing.required.indicators" /></p>
</g:if>

<p><b><g:message code="report.missing.required.indicators" /></b>
<g:if test="${validatorResult.getMissingIndicators()}">

<div id="out" style="border: 1px solid darkgrey; width: 90%;">
<table class="list">
	<tr>
		<th><g:message code="indicator.shortName.label" /></th>
		<th><g:message code="indicator.name.label" /></th>
	</tr>

	<g:each in="${validatorResult.getMissingIndicators()}" status="i"
		var="missing">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td><g:link action="show" controller="indicator" id="${missing.id}">
			${missing.shortName}
			</g:link></td>
			<td><g:link action="show" controller="indicator" id="${missing.id}">
			${missing.name}
			</g:link></td>
		</tr>
	</g:each>
</table>
</div>
</p>
</g:if>

<g:if test="${validatorResult.getMissingIndicators().isEmpty()}">
<p><g:message code="report.nomissing.required.indicators" /></p>
</g:if>

<p><b><g:message code="report.missing.optional.indicators" /></b>
<g:if test="${validatorResult.getMissingOptionalIndicators()}">
<div id="out" style="border: 1px solid darkgrey; width: 90%;">
<table class="list">
	<tr>
		<th><g:message code="indicator.shortName.label" /></th>
		<th><g:message code="indicator.name.label" /></th>
	</tr>

	<g:each in="${validatorResult.getMissingOptionalIndicators()}" status="i"
		var="validatorResult">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "shortName")}
			</g:link></td>
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "name")}
			</g:link></td>
		</tr>
	</g:each>
</table>
</div>
</p>
</g:if>

<g:if test="${validatorResult.getMissingOptionalIndicators().isEmpty()}">
<p><g:message code="report.no.missing.optional.indicators" /></p>
</g:if>



</body>
</html>
