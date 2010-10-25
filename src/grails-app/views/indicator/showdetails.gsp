
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:set var="entityName"
	value="${message(code: 'indicator.label', default: 'Indicator')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<br>
<hr />
<div class="body"><dvi id="topichead"> <h7>
${indicatorInstance} (${indicatorInstance.shortName})<g:message
	code="scheme.details.label" default="- Details" /></h7></div>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>
<div class="option">
<table class="option">
	<tbody>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.name.label" default="Name" /></td>

			<td valign="top" class="value">
			${fieldValue(bean: indicatorInstance, field: "name")}
			</td>

		</tr>
		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.description.label" default="Description" /></td>

			<td valign="top" class="value">
			${fieldValue(bean: indicatorInstance, field: "description")}
			</td>

		</tr>


		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.creationDate.label" default="Creation Date" /></td>

			<td valign="top" class="value"><g:formatDate
				format="yyyy-MM-dd HH:mm:ss"
				date="${indicatorInstance?.creationDate}" /></td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.unit.label" default="Unit" /></td>

			<td valign="top" class="value">
			${fieldValue(bean: indicatorInstance, field: "unit")}
			</td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.category.label" default="Category" /></td>

			<td valign="top" class="value"><g:link controller="category"
				action="show" id="${indicatorInstance?.category?.id}">
				${indicatorInstance?.category}
			</g:link></td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.lastModifiedDate.label" default="Last Modified Date" /></td>

			<td valign="top" class="value"><g:formatDate
				format="yyyy-MM-dd HH:mm:ss"
				date="${indicatorInstance?.lastModifiedDate}" /></td>

		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.creator.label" default="Creator" /></td>

			<td valign="top" class="value"><g:link controller="user"
				action="show" id="${indicatorInstance?.creator?.id}">
				${indicatorInstance?.creator?.encodeAsHTML()}
			</g:link></td>
		</tr>

		<tr class="prop">
			<td valign="top" class="name"><g:message
				code="indicator.lastModifiedBy.label" default="Last Modified By" /></td>

			<td valign="top" class="value"><g:link controller="user"
				action="show" id="${indicatorInstance?.lastModifiedBy?.id}">
				${indicatorInstance?.lastModifiedBy?.encodeAsHTML()}
			</g:link></td>

		</tr>

	</tbody>
</table>
</div>

<div align="right"><g:link controller="indicator" action="show"
	id="${indicatorInstance.id}">
	<g:message code="scheme.more.details.label" default="More details" />
</g:link></div>
</body>
</html>
