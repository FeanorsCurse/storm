

<%@ page import="shemaEditor.shemaAdministration.TNode"%>
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title></title>

</head>
<body>

<div id="topichead">
<h7><g:message code="scheme.import.label" default="Import schema" /></h7>
</div>


<div id="option"><g:message code="scheme.choose.scheme.toimport" default="Import schema" /><br>


<form>
<p><g:select name="treportListSelection" id="treportListSelection"
	optionKey="id" noSelection="${['0':message(code:'scheme.choose.for.import')]}"
	onchange="${remoteFunction(controller:'TReport', action:'listNodes', update:'tnodeList', params:'\'rootId=\'+this.value')}"
	from="${TReport.list()}"></g:select></p>
<div id="tnodeList" name="tnodeList"></div>

<g:hiddenField name="report" id="" report"" value="${report}" /> <g:hiddenField
	name="selectedNode" id="selectedNode" value="${id}" /> 
	<g:actionSubmit	id="subImport" action="importSchema" name="subImport" value="${message(code: 'scheme.import.menu', default: 'Import')}" />

</form>

</div>
</body>
</html>