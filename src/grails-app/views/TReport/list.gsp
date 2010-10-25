
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<%@ page import="systemadministration.usermanagement.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:javascript library="prototype" />
<resource:autoComplete skin="default" />
<export:resource />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'TReport.label', default: 'TReport')}" />
<title><g:message code="scheme.list.label" args="[entityName]" /></title>
<script language="JavaScript">
function CheckAll(chk)
{
if(document.myform.Check_ctr.checked==true){
for (i = 0; i < chk.length; i++)
chk[i].checked = true ;
}else{

for (i = 0; i < chk.length; i++)
chk[i].checked = false ;
}
}
</script>
</head>
<body>
<div class="nav"><export:formats action="exportAllSchemes"
	controller="TReport" formats="['csv', 'excel', 'ods', 'pdf', 'rtf']" /><br>
<span class="menuButton"><g:link class="create" action="create">
	<g:message code="scheme.create.label" args="[entityName]" />
</g:link></span></div>
<div class="body">
<h1><g:message code="scheme.list.label" default="Scheme list" /></h1>
<g:if test="${tilde}">
	<g:if test="${flash.info}">
		<div class="info">
		${flash.info} <g:each in="${TReportInstanceList}" status="i"
			var="TReportInstance">
			<g:link controller="TReport" action="searchFilter"
				params="[schema:TReportInstance.name]">
				${TReportInstance.name}
			</g:link>
		</g:each></div>
	</g:if>
</g:if>

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

<div id="searchfilter"><g:form
	url='[controller: "TReport", action: "searchFilter"]'
	id="searchableForm" name="searchableForm" method="get">

	<table>
		<tr>
			<td><g:message code="scheme.inputfield.search" /></td>
			<td><richui:autoComplete name="schema" maxResultsDisplayed="20"
				minQueryLength="3" value="${params.schema}" style="width:200px;"
				action="${createLinkTo('dir': 'TReport/autocomplete')}" />
			<td><select name="creator" size="1">
				<option value="0"><g:message code="indicator.creator.label"
					default="Creator" /></option>
				<g:each in="${User.list()}" var="creator">
					<option value="${creator}"
						<g:if test="${creator.username==params.creator}"> 
									selected 
								</g:if>>
					${creator}
					</option>
				</g:each>
			</select></td>
			<td><input type="submit" value="${message(code: 'default.button.filter.label', default: 'Filter')}"></td>
			</td>
		</tr>
	</table>

</g:form></div>

<g:if test="${!tilde}">

	<div id="indicatorList"><g:if test="${TReportInstanceList}">
		<div class="float_clear"></div>

		<form name="myform">
		<table class="list">
			<thead>
				<tr>
					<th><input name="Check_ctr" type="checkbox"
						onClick="CheckAll(document.myform.treportcheck)" value="yes" /></th>
					<th style="width: 11%;"><g:message code="report.action.label"
						default="Action" /></th>
					<g:sortableColumn property="name"
						title="${message(code: 'scheme.name.label', default: 'Name')}" />
					<g:sortableColumn property="creationDate"
						title="${message(code: 'scheme.creationDate.label', default: 'Creation Date')}" />
					<g:sortableColumn property="lastModified"
						title="${message(code: 'scheme.lastModified.label', default: 'Last Modified')}" />
					<g:sortableColumn property="creator"
						title="${message(code: 'scheme.creator.label', default: 'Creator')}" />
					<g:sortableColumn property="lastModifiedBy"
						title="${message(code: 'scheme.lastModifiedBy.label', default: 'Modified By')}" />

				</tr>
			</thead>
			<tbody>
				<g:each in="${TReportInstanceList}" status="i" var="TReportInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td><input type="checkbox" name="treportcheck" value="${TReportInstance.id}" /></td>
						<td><g:link controller="TReport" action="show"
							id="${TReportInstance.id}">
							<img src="${resource(dir:'images/ReportEditor',file:'show.png')}"
								title="${message(code: 'scheme.details.show', default: 'Schema-Details')}"
								alt="${message(code: 'scheme.details.show', default: 'Schema-Details')}" />
						</g:link> <g:link controller="TReport" action="delete"
							id="${TReportInstance.id}">
							<img
								src="${resource(dir:'images/ReportEditor',file:'delete.png')}"
								title="${message(code: 'scheme.delete.label', default: 'Delete schema')}"
								alt="${message(code: 'scheme.delete.label', default: 'Delete schema')}" />
						</g:link></td>
						<td><g:link action="show" params="[id:TReportInstance.id]">
							${fieldValue(bean: TReportInstance, field: "name")}
						</g:link></td>
						<td><g:formatDate format="dd.MM.yyyy HH:mm:ss"
							date="${TReportInstance?.creationDate}" /></td>
						<td><g:formatDate format="dd.MM.yyyy HH:mm:ss"
							date="${TReportInstance?.lastModified}" /></td>
						<td><g:link controller="user" action="show"
							id="${TReportInstance?.creator?.id}">
							${fieldValue(bean: TReportInstance, field: "creator")}
						</g:link></td>
						<td><g:link controller="user" action="show"
							id="${TReportInstance?.lastModifiedBy?.id}">
							${fieldValue(bean: TReportInstance, field: "lastModifiedBy")}
						</g:link></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<g:actionSubmit class="delete" action="deleteSelectedTReport"
			value="${message(code: 'scheme.multidelete.label', default: 'Delete')}"
			onclick="return confirm('${message(code: 'scheme.delete.button.confirm.message', default: 'Are you sure?')}');" />
		</form></div>

</g:if></g:if> <g:if test="${!TReportInstanceList}">
	<g:message code="indicator.nosuch.results" default="No such results" />
</g:if></div>

</body>
</html>
