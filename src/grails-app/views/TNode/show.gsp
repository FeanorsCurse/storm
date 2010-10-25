
<%@ page import="shemaEditor.shemaAdministration.TNode"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<g:set var="entityName"
	value="${message(code: 'TNode.label', default: 'TNode')}" />
<title><g:message code="node.show.title" args="[entityName]" /></title>
<export:resource />
</head>
<body>
<div class="nav" style="margin-top: 20px; text-align: right;"><span
	class="menuButton"> <g:remoteLink action="create"
	controller="TNode" update="optionsBox"
	params="[snode:TNodeInstance.id, report:report]">
	<g:message code="scheme.new.menu" default="New" />
</g:remoteLink> </span> | <g:if test="${!TNodeInstance.isTitle()}">
	<span class="menuButton"> <g:remoteLink
		action="listNodeIndicator" controller="TNode" update="optionsBox"
		params="[snode:TNodeInstance.id, report:report]">

		<g:message code="indicator.list.label" default="Indicators" />
	</g:remoteLink> </span> | </g:if> <span class="menuButton"> <g:remoteLink action="edit"
	controller="TNode" update="optionsBox"
	params="[id:TNodeInstance.id, report:report]">
	<g:message code="scheme.edit.menu" default="Edit" />
</g:remoteLink> </span> | <g:if test="${!TNodeInstance.isTitle()}">
	<span class="menuButton"> <g:remoteLink action="deleteConfirm"
		controller="TNode" update="optionsBox"
		params="[id:TNodeInstance.id, report:report]">
		<g:message code="scheme.delete.menu" default="Delete" />
	</g:remoteLink> </span>


|
</g:if> <g:if test="${!TNodeInstance.isTitle()}">
	<span class="menuButton"> <g:remoteLink action="move"
		controller="TReport" update="optionsBox"
		params="[id:TNodeInstance.id, report:report]">
		<g:message code="scheme.move.menu" default="Move" />
	</g:remoteLink> </span>

|
</g:if> <span class="menuButton"> <g:remoteLink action="importSchema"
	controller="TReport" update="optionsBox"
	params="[id:TNodeInstance.id, report:report]">
	<g:message code="scheme.import.menu" default="Import" />
</g:remoteLink> </span> | <span class="menuButton"> <g:link action="show"
	controller="TReport" params="[id:report]">
	<g:message code="scheme.overview.menu" default="Overview" />
</g:link> </span></div>
<div class="body">



<div id="optionsBox"></div>

<div id="topichead"><h7><g:if
	test="${TNodeInstance.isTitle()}"><g:message code="scheme.titel.label" default="Title:" /></g:if>${fieldValue(bean: TNodeInstance, field: "name")}<g:message
	code="scheme.details.label" default=" - Details " /></h7></div>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if>
<g:if test="${flash.info}">
	<div class="info">
	${flash.info}
	</div>
</g:if>
<div class="schemadialog">
<div id="option">
<table class="option">
	<tbody>

		<tr>
			<td valign="top" class="name"><g:message code="node.name.label"
				default="Name" /></td>

			<td valign="top" class="value">
			${fieldValue(bean: TNodeInstance, field: "name")}
			</td>

		</tr>


		<tr>
			<td valign="top" class="name"><g:message
				code="node.creator.label" default="Creator" /></td>

			<td valign="top" class="value"><g:link controller="user"
				action="show" id="${TNodeInstance?.creator?.id}">
				${TNodeInstance?.creator?.encodeAsHTML()}
			</g:link></td>

		</tr>



		<tr>
			<td valign="top" class="name"><g:message
				code="node.creationDate.label" default="Creation Date" /></td>

			<td valign="top" class="value"><g:formatDate
				format="dd.MM.yyyy HH:mm:ss" date="${TNodeInstance?.creationDate}" /></td>

		</tr>

		<tr>
			<td valign="top" class="name"><g:message
				code="node.lastModifiedBy.label" default="Last Modified By" /></td>

			<td valign="top" class="value"><g:link controller="user"
				action="show" id="${TNodeInstance?.lastModifiedBy?.id}">
				${TNodeInstance?.lastModifiedBy?.encodeAsHTML()}
			</g:link></td>

		</tr>
		<tr>
			<td valign="top" class="name"><g:message
				code="node.lastModified.label" default="Last Modified" /></td>

			<td valign="top" class="value"><g:formatDate
				format="dd.MM.yyyy HH:mm:ss" date="${TNodeInstance?.lastModified}" /></td>

		</tr>

	</tbody>
</table>
</div>
</div>


<div id="topichead"><h7><g:message
	code="node.description.label" default="Description" /></h7></div>

<div id="option">
${fieldValue(bean: TNodeInstance, field: "description")}
</div>
<g:if test="${!TNodeInstance.title}">
	<div id="topichead"><h7><g:message
		code="indicators.ofnode.list" /></h7></div>

	<div id="option"><g:if test="${indicatorList}">
		<export:formats action="exportIndicatorsFromNode" controller="TNode"
			params="[node: TNodeInstance.id]"
			formats="['csv', 'excel', 'ods', 'pdf', 'rtf']" />
	</g:if>
	<table class="list">

		<g:if test="${!indicatorList}">
			<g:message code="noindicators.node.list" />
		</g:if>

		<g:if test="${indicatorList}">
			<thead>
				<tr>

					<g:sortableColumn property="name"
						title="${message(code: 'indicator.name.label', default: 'Name')}" />

					<g:sortableColumn property="category"
						title="${message(code: 'indicator.category.label', default: 'Category')}" />


				</tr>
			</thead>
		</g:if>
		<g:each in="${indicatorList}" status="i" var="indicatorList">
			<tbody>
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

					<td><g:link action="show" controller="indicator"
						id="${indicatorList.id}">
						${fieldValue(bean: indicatorList, field: "name")}
					</g:link></td>
					<td>
					${fieldValue(bean: indicatorList, field: "category")}
					</td>

				</tr>
		</g:each>
		</tbody>
	</table>

	</div>

</g:if></div>
</body>
</html>
