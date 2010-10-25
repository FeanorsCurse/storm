
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
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
</head>
<body>
<p>
<div class="nav" align="right"><span class="menuButton"><g:link
	class="list" action="list">
	<g:message code="default.list.label" args="[entityName]" />
</g:link></span></div>
</p>
<div class="body">
<h1><g:message code="article.importe.label"
	default="Import indicator" /></h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> <g:hasErrors bean="${indicatorInstance}">
	<div class="errors"><g:renderErrors bean="${indicatorInstance}"
		as="list" /></div>
</g:hasErrors>
<div id="indicatorList"><g:form name="myform"
	action="importIndicator" method="post">
	<table width="100%" style="margin-top: 40px;">
		<thead>
			<tr>
				<th><input name="Check_ctr" type="checkbox"
					onClick="CheckAll(document.myform.frm)" value="yes" /></th>

				<td><g:message code="schema.action.label" default="Action" /></td>

				<g:sortableColumn property="name"
					title="${message(code: 'indicator.name.label', default: 'Name')}" />

				<g:sortableColumn property="category"
					title="${message(code: 'indicator.category.label', default: 'Category')}" />

				<g:sortableColumn property="creator"
					title="${message(code: 'indicator.creator.label', default: 'Creator')}" />

				<g:sortableColumn property="unit"
					title="${message(code: 'indicator.unit.label', default: 'Unit')}" />

				<g:sortableColumn property="creationDate"
					title="${message(code: 'indicator.creationDate.label', default: 'Creation Date')}" />

				<g:sortableColumn property="lastModifiedDate"
					title="${message(code: 'indicator.lastModifiedDate.label', default: 'Last Modified Date')}" />
			</tr>
		</thead>
		<tbody>
			<g:each in="${indicatorInstanceList}" status="i"
				var="indicatorInstance">
				<tr class="btnav" onmouseover="style.backgroundColor='#8ABADA';"
					onmouseout="style.backgroundColor='WHITE'"
					class="${(i % 2) == 0 ? 'odd' : 'even'}">
					<td><input type="checkbox" name="frm"
						value="${indicatorInstance.id}" /></td>
					<td><g:link controller="indicator" action="show"
						id="${indicatorInstance.id}">
						<img src="${resource(dir:'images/ReportEditor',file:'show.png')}"
							title="${message(code: 'indicator.show.details', default: 'Indicator-Details')}"
							alt="${message(code: 'indicator.show.details', default: 'Indicator-Details')}" />
					</g:link> <g:link action="edit" id="${indicatorInstance.id}">
						<img src="${resource(dir:'images/ReportEditor',file:'edit.png')}"
							title="${message(code: 'indicator.edit.label', default: 'Edit indicator')}"
							alt="${message(code: 'indicator.edit.label', default: 'Edit indicator')}" />
					</g:link></td>
					<td><g:link action="show" id="${indicatorInstance.id}">
						${fieldValue(bean: indicatorInstance, field: "name")}
					</g:link></td>

					<td>
					${fieldValue(bean: indicatorInstance, field: "category")}
					</td>


					<td>
					${fieldValue(bean: indicatorInstance, field: "creator")}
					</td>


					<td>
					${fieldValue(bean: indicatorInstance, field: "unit")}
					</td>

					<td><g:formatDate format="dd.MM.yyyy HH:mm:ss"
						date="${indicatorInstance?.creationDate}" /></td>

					<td><g:formatDate format="dd.MM.yyyy HH:mm:ss"
						date="${indicatorInstance?.lastModifiedDate}" /></td>
				</tr>
			</g:each>
		</tbody>
	</table></div>
<div class="buttons"><span class="button"><g:submitButton
	name="import" class="importIndicator"
	value="${message(code: 'default.button.importe.label', default: 'Import')}" /></span>
</div>
</g:form></div>
</body>
</html>
