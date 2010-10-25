
<%@ page import="shemaEditor.indicatorAdministration.Category"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'category.label', default: 'Category')}" />

<title><g:message code="category.show.label" args="[entityName]" /></title>

<script language="JavaScript">
function CheckAll(chk)
{
if(document.cat_form.indicatorCheck.checked==true){
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
<div class="nav" align="right"><span class="menuButton"><g:link
	class="list" action="list">
	<g:message code="category.list.label" args="[entityName]" />
</g:link></span> | <span class="menuButton"><g:link class="create"
	action="create">
	<g:message code="category.create.label" args="[entityName]" />
</g:link></span> | <span class="menuButton"><g:link class="delete"
	action="delete" params="[id:categoryInstance.id]">
	<g:message code="category.delete.label" args="[entityName]" />
</g:link></span> | <span class="menuButton"><g:link class="edit" action="edit"
	params="[id:categoryInstance.id]">
	<g:message code="category.edit.label" args="[entityName]" />
</g:link></span></div>

<div class="body">
<h1><g:message code="category.show.message"
	args="[message(code: 'category.label', default: 'Category'), categoryInstance.name]" /></h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
	<br>
</g:if> <g:if test="${flash.info}">
	<div class="info">
	${flash.info}
	</div>
	<br>
</g:if> <g:message code="category.area.description" default="I" />

<h3><g:message code="category.area.indicators"
	default="Indicators of category" /></h3>

<g:form id="indiCatForm" action="moveIndicator" id="${categoryInstance.id}" controller="category" name="cat_form">

<table width="90%">

<tr>

<td></td>
<td></td>
<td>

<g:message code="category.name.label" default="Category" /> <select
		name="selectedCategory" size="1"
		onChange="${remoteFunction(controller:'category', action:'categoryIndicators', update:'categoryIndicatorDiv', params:'\'cid=\'+this.value')}">
		<option value="0"><g:message code="indicator.category.label"
			default="Category" /></option>
		<g:each in="${categoryInstance.listOtherCategories()}" var="category">
			<option value="${category.id}"
				<g:if test="${category==catname}">
			selected
			</g:if>>
			${category}
			</option>
		</g:each>
	</select>

</td>
</tr>

<tr>

<td width="45%" valign="top">
	
	<table width="100%">
		<tr>

			<td>
			<table class="list">

				<thead>

					<tr>
						<th><input name="indicatorCheck" type="checkbox"
							onClick="CheckAll(document.cat_form.indicatorbox)" value="yes" /></th>

						<th></th>

						<th><g:message code="indicator.name.label" default="Name" /></th>
					</tr>


				</thead>


				<tbody>
					<g:each in="${categoryInstance.indicators}" var="indicator"
						status="i">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

							<td><g:checkBox name="indicatorbox" value="${indicator.id}"
								checked="false" /></td>

							<td></td>

							<td><g:link controller="indicator" action="show"
								id="${indicator.id}">
								${indicator}
							</g:link></td>

						</tr>
					</g:each>
				</tbody>
			</table>
			</td>
		</tr>
	</table>
	
	</td>
<td width="10%" align="center" >
	
	<input type="submit"
		value="<<>>" />

<%--${message(code: 'indicators.move.from.category', default: 'Move indicator')} --%>

</td>
<td width="45%" valign="top">

	 <input type="hidden" name="cat_b" value="${categoryInstance.id}" />

	<div id="categoryIndicatorDiv"><g:if
		test="${destCategoryInstance}">
		<g:include action="categoryIndicators" controller="category"
			id="${destCategoryInstance}" />
	</g:if> <g:else>
		<g:include action="categoryIndicators" controller="category" />
	</g:else></div>
	

</td>

</tr>


</table>


</g:form>











</div>




</body>
</html>
