
<%@ page import="shemaEditor.indicatorAdministration.Category"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<meta name="layout" content="admin" />
<g:set var="entityName"
	value="${message(code: 'category.label', default: 'Category')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<title><g:message code="category.list.label" args="[entityName]" /></title>
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
<div class="nav" align="right"><span class="menuButton"><g:link
	class="create" action="create">
	<g:message code="category.create.label" args="[entityName]" />
</g:link></span></div>
<div class="body">
<h1><g:message code="category.list.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
	<br>
</g:if>
<g:if test="${flash.info}">
	<div class="info">
	${flash.info}
	</div>
	<br>
</g:if>
<div class="schemalist">
<g:form name="myform" action="deleteSelectedCat">
<table class="list">
	<thead>
		<tr>
			<th><input name="Check_ctr" type="checkbox"
				onClick="CheckAll(document.myform.categorycheck)" value="yes" /></th>

			<th style="width: 11%;"><g:message code="schema.action.label"
				default="Action" /></th>

			<g:sortableColumn property="name"
				title="${message(code: 'category.name.label', default: 'Name')}" />

		</tr>
	</thead>
	<tbody>
		<g:each in="${categoryInstanceList}" status="i" var="categoryInstance">
			<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

				<td><input type="checkbox" name="categorycheck"	value="${categoryInstance.id}" id="${categoryInstance.id}"/></td>
				<td><g:link controller="category" action="show"
					id="${categoryInstance.id}">
					<img src="${resource(dir:'images/ReportEditor',file:'show.png')}"
						title="${message(code: 'category.show.details', default: 'Category details')}"
						alt="${message(code: 'category.show.details', default: 'Category details')}" />
				</g:link> <g:link controller="category" action="edit"
					id="${categoryInstance.id}">
					<img src="${resource(dir:'images/ReportEditor',file:'edit.png')}"
						title="${message(code: 'category.edit.label', default: 'Edit category')}"
						alt="${message(code: 'category.edit.label', default: 'Edit category')}" />
				</g:link> <g:link controller="category" action="delete"
					id="${categoryInstance.id}">
					<img src="${resource(dir:'images/ReportEditor',file:'delete.png')}"
						title="${message(code: 'category.delete.label', default: 'Delete category')}"
						alt="${message(code: 'category.delete.label', default: 'Delete category')}" />
				</g:link></td>

				<td><g:link action="show" id="${categoryInstance.id}">
					${fieldValue(bean: categoryInstance, field: "name")}
				</g:link></td>

			</tr>
		</g:each>
	</tbody>
</table>
<input type="submit" value="${message(code: 'category.delete.label', default: 'Delete')}" />
</g:form>
</div>

<div class="paginateButtons"><g:paginate
	total="${categoryInstanceTotal}" /></div>
</div>
</body>
</html>
