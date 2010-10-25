<%@ page import="shemaEditor.shemaAdministration.TNode"%>
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<script type="text/javascript">

function unuse(){
	if(document.getElementById('targetNode').value==0){
		document.getElementById('moveNode').disabled =true;
	}else{
		document.getElementById('moveNode').disabled =false;
	}
}

</script>

</head>
<body>

<dvi id="topichead">
<h7><g:message code="node.move.label"/></h7>
</div>         
<div id="option">
<g:message code="choose.node.to.move" />
<form>
<p><g:select name="targetNode" id="targetNode"
	optionKey="id" noSelection="${['0':message(code:'choose.node.selected')]}"
	
	onchange="unuse()"
	from="${allNodes}"></g:select></p>
	

<div id="tnodeList" name="tnodeList"></div>

<g:hiddenField name="report" id="" report"" value="${report}" /> <g:hiddenField
	name="selectedNode" id="selectedNode" value="${id}" /> 

	<g:actionSubmit	id="moveNode" action="move" name="moveNode" value="${message(code: 'scheme.move.menu', default: 'Move')}" disabled="disabled"/>

</form>

</div>
</body>
</html>