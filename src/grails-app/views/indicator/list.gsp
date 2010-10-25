
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="shemaEditor.indicatorAdministration.Category"%>
<%@ page import="systemadministration.usermanagement.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'indicator.label', default: 'Indicator')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<title><g:message code="indicator.list.label"
	args="[entityName]" /></title>
<export:resource />
<g:javascript library="prototype" />
<resource:autoComplete skin="default" />
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
<div class="nav"><export:formats action="exportAllIndicators"
	controller="indicator" formats="['csv', 'excel', 'ods', 'pdf', 'rtf']" /><br>
<span class="menuButton"><g:link class="create" action="create">
	<g:message code="indicator.create.label" args="[entityName]" />
</g:link></span> <g:if test="${session.article!=null}">
 | <span class="menuButton"><g:link class="create"
		action="importe">
		<g:message code="article.importe.label" default="Import indicator" />
	</g:link></span>
 | <span class="menuButton"><g:link class="create" action="list"
		controller="article">
		<g:message code="article.list.label" default="Article List" />
	</g:link></span>
</g:if></div>
<div class="body">
<h1><g:message code="indicator.list.label" default="Indicators" /></h1>
<g:if test="${tilde}">
	<g:if test="${flash.info}">
		<div class="info">
		${flash.info} <g:each in="${indicatorInstanceList}" status="i"
			var="indicatorInstance">
			<g:link controller="indicator" action="searchFilter"
				params="[indicator:indicatorInstance.name]">
				${indicatorInstance.name}
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
	url='[controller: "indicator", action: "searchFilter"]'
	id="searchableForm" name="searchableForm" method="get">

	<table>
		<tr>
			<td><g:message code="indicator.indicator.label"
				default="Indicator: " /></td>
			<td><richui:autoComplete name="indicator"
				maxResultsDisplayed="20" minQueryLength="3"
				value="${params.indicator}" style="width:200px;"
				action="${createLinkTo('dir': 'indicator/autocomplete')}" />
			<td><select name="category" size="1">
				<option value="0"><g:message
					code="indicator.category.label" default="Category" /></option>
				<g:each in="${Category.list()}" var="category">
					<option value="${category}"
						<g:if test="${category.name==params.category}"> 
									selected 
								</g:if>>
					${category}
					</option>
				</g:each>
			</select></td>
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
	<div id="indicatorList"><g:if test="${indicatorInstanceList}">
		<div class="float_clear"></div>
		<g:form name="myform">
		<table class="list">
			<thead>
				<tr>
					<th><input name="Check_ctr" type="checkbox"
						onClick="CheckAll(document.myform.indicatorcheck)" value="yes" /></th>

					<th><g:message code="schema.action.label"
						default="Action" /></th>


					<g:sortableColumn property="name"
						title="${message(code: 'indicator.name.label', default: 'Name')}" />

					<g:sortableColumn property="category"
						title="${message(code: 'indicator.category.label', default: 'Category')}" />

					<g:sortableColumn property="creator"
						title="${message(code: 'indicator.creator.label', default: 'Creator')}" />

					<g:sortableColumn property="unit"
						title="${message(code: 'indicator.unit.label', default: 'Unit')}" />


					<g:sortableColumn property="lastModifiedDate"
						title="${message(code: 'indicator.lastModifiedDate.label', default: 'Last Modified Date')}" />
				</tr>
			</thead>
			<tbody>
				<g:each in="${indicatorInstanceList}" status="i"
					var="indicatorInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td><g:checkBox name="indicatorcheck" value="${indicatorInstance.id}" checked="false"/>
						</td>
						<td><g:link controller="indicator"
							action="show" id="${indicatorInstance.id}">
							<img src="${resource(dir:'images/ReportEditor',file:'show.png')}"
								title="${message(code: 'indicator.show.details', default: 'Indicator details')}"
								alt="${message(code: 'indicator.show.details', default: 'Indicator details')}" />
						</g:link> <g:link controller="indicator" action="delete"
							id="${indicatorInstance.id}">
							<img
								src="${resource(dir:'images/ReportEditor',file:'delete.png')}"
								title="${message(code: 'indicator.delete.label', default: 'Delete indicator')}"
								alt="${message(code: 'indicator.delete.label', default: 'Delete indicator')}" />
						</g:link></td>

						<td>

						<table>
							<tr>

								<td><g:if test="${indicatorInstance.isIndicator()}">
									<img
										src="${resource(dir:'images/schemaEditor/indicators',file:'indicator.png')}"
										title="${message(code: 'indicator.tip.label', default: 'Indicator')}"
										alt="${message(code: 'indicator.tip.label', default: 'Indicator')}" />
								</g:if> <g:if test="${!indicatorInstance.isIndicator()}">
									<img
										src="${resource(dir:'images/schemaEditor/indicators',file:'contentguideline.png')}"
										title="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}"
										alt="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}" />

								</g:if></td>

								<td><g:link action="show" id="${indicatorInstance.id}">
									<g:indicatorProgress id="${indicatorInstance.id}" />
								</g:link></td>

							</tr>

						</table>

						</td>

						<td><g:link controller="category" action="show"
							id="${indicatorInstance?.category?.id}">
							${fieldValue(bean: indicatorInstance, field: "category")}
						</g:link></td>

						<td><g:link controller="user" action="show"
							id="${indicatorInstance?.creator?.id}">
							${fieldValue(bean: indicatorInstance, field: "creator")}
						</g:link></td>

						<td>
						${fieldValue(bean: indicatorInstance, field: "unit")}
						</td>

						<td><g:formatDate format="dd.MM.yyyy HH:mm"
							date="${indicatorInstance?.lastModifiedDate}" /></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<g:actionSubmit class="delete" action="deleteSelectedInd" value="${message(code: 'indicators.delete.label', default: 'Delete')}"
		onclick="return confirm('${message(code: 'indicator.delete.button.confirm.message', default: 'Are you sure?')}');" />
		
		</g:form></div>
</g:if> </g:if> <g:if test="${!indicatorInstanceList}">
	<g:message code="indicator.nosuch.results" default="No such results" />
</g:if></div>
</body>
</html>
