
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta name="layout" content="admin" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />

<g:set var="entityName"
	value="${message(code: 'TReport.label', default: 'TReport')}" />
<title><g:message code="scheme.show.title" default="Show schema" /></title>
<export:resource />
<script type="text/javascript"
	src="${resource(dir:'js/simpletreeview',file:'simpletreemenu.js')}">
/***********************************************
* Simple Tree Menu- � Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/
</script>
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css/standard',file:'simpletree.css')}" />




</head>
<body>


<h2>
${fieldValue(bean: TReportInstance, field: "name")}
</h2>
<div id="treeOuterBox">
<div id="topichead"><h7><g:message code="scheme.structure.label" default="Scheme-structure" /></h7></div>
<div id="treeBox">

<%= domTreeAsHtml %> 


<script type="text/javascript">

ddtreemenu.createTree("treemenu", false)

</script> 

</div>

</div>

<div id="detailsBox">

<div class="nav" style="margin-top: 20px; text-align: right;"><span
	class="menuButton"> <g:remoteLink action="edit"
	controller="TReport" update="optionsBox"
	params="[id:TReportInstance.id]">
	<g:message code="scheme.details.edit" />
</g:remoteLink> </span> | <span class="menuButton"> <g:link action="delete"
	controller="TReport" params="[id:TReportInstance.id]"
	onclick="return confirm('${message(code: 'scheme.delete.button.confirm.message', default: 'Are you sure?')}');"
	>
	<g:message code="scheme.complete.delete" />
</g:link> </span></div>
<div id="optionsBox"></div>
<div id="topichead"><h7><g:message code="scheme.details.show" /></h7></div>

<div id="option"><g:if test="${flash.message}">
	<br>
	<div class="message">
	${flash.message}
	</div>
	<br>
</g:if>
<g:if test="${flash.info}">
	<br>
	<div class="info">
	${flash.info}
	</div>
	<br>
</g:if>
<div>



<g:if test="${importchildid}">
<div align="right">
<g:def var="report" value="${TReportInstance.id}"/>
<g:def var="childid" value="${importchildid}"/>


<g:link action="show" id="${TReportInstance.id}">
<img src="${resource(dir:'images/schemaEditor',file:'notundo.png')}" title="${message(code:'schema.continue.import', default:'Continue Import')}" alt="${message(code:'schema.continue.import', default:'Continue Import')}" />
</g:link>

<g:link action="undo" params="[report:report,importchildid:childid]">
<img src="${resource(dir:'images/schemaEditor',file:'undo.png')}" title="${message(code:'schema.decline.import', default:'Decline Import')}" alt="${message(code:'schema.decline.import', default:'Decline Import')}" />
</g:link>
</div>
</g:if>


<table class="option">
	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.name.label" default="Schema name" /></td>

		<td valign="top" class="value">
		${TReportInstance?.name}
		</td>

	</tr>

	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.root.label" default="Titel" /></td>

		<td valign="top" class="value">
		${TReportInstance?.root?.encodeAsHTML()}
		</td>

	</tr>


	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.creationDate.label" default="Creation Date" /></td>

		<td valign="top" class="value"><g:formatDate
			format="dd.MM.yyyy HH:mm:ss" date="${TReportInstance?.creationDate}" /></td>

	</tr>

	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.lastModified.label" default="Last Modified" /></td>

		<td valign="top" class="value"><g:formatDate
			format="dd.MM.yyyy HH:mm:ss" date="${TReportInstance?.lastModified}" /></td>

	</tr>


	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.description.label" default="Description" /></td>

		<td valign="top" class="value">
		${fieldValue(bean: TReportInstance, field: "description")}
		</td>

	</tr>

	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.lastModifiedBy.label" default="Last modified by" /></td>

		<td valign="top" class="value"><g:link controller="user"
			action="show" id="${TReportInstance?.lastModifiedBy?.id}">
			${TReportInstance?.lastModifiedBy?.encodeAsHTML()}
		</g:link></td>

	</tr>

	<tr>
		<td valign="top" class="name"><g:message
			code="scheme.creator.label" default="Creator" /></td>

		<td valign="top" class="value"><g:link controller="user"
			action="show" id="${TReportInstance?.creator?.id}">
			${TReportInstance?.creator?.encodeAsHTML()}
		</g:link></td>

	</tr>


</table>
</div>
</div>
<div id="topichead"><h7><g:message
	code="indicators.schema.list" /></h7></div>

<div id="option">
<table class="list">

	<g:if test="${!indicatorList}">
		<g:message code="noindicators.schema.list" />
	</g:if>

	<g:if test="${indicatorList}">
		<export:formats action="exportIndicatorsFromSchema"
			controller="TReport" params="[report: TReportInstance.id]"
			formats="['csv', 'excel', 'ods', 'pdf', 'rtf']" />
		<br>

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
			<tr>

				<td><g:link action="show" controller="indicator" id="${indicatorList.id}"><g:indicatorProgress id="${indicatorList.id}"/></g:link></td>
				<td>
				${fieldValue(bean: indicatorList, field: "category")}
				</td>

			</tr>
	</g:each>
	</tbody>
</table>

</div>

</div>

<div style='clear: both'></div>
</body>
</html>
