
<%@ page import="shemaEditor.shemaAdministration.TNode"%>
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<g:set var="entityName"
	value="${message(code: 'TNode.label', default: 'TNode')}" />
<title><g:message code="default.create.label"
	args="[entityName]" /></title>
</head>
<body>

<div class="body"><dvi id="topichead"> <h7><g:message code="node.create.label"
		default="New node" /></h7></div>



<div id="option">


<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> <g:hasErrors bean="${TNodeInstance}">
	<div class="errors"><g:renderErrors bean="${TNodeInstance}"
		as="list" /></div>
</g:hasErrors> 
<g:form name="form" url="[controller:'TNode',action:'save']" >

	<div class="dialog">
	<table>
		<tbody>

		

<tr class="prop">
				<td valign="top" class="name"><label for="name"><g:message
					code="node.name.label" default="Name" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TNodeInstance, field: 'name', 'errors')}">
				<g:textField name="name" value="${TNodeInstance?.name}" size="40"/></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="description"><g:message
					code="node.description.label" default="Description" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TNodeInstance, field: 'description', 'errors')}">
				<g:textArea name="description"
					value="${TNodeInstance?.description}" cols="50" rows="6"/></td>
			</tr>
			
			<tr class="prop">
				<td valign="top" class="name"><label for="creator"><g:message
					code="node.creator.label" default="Creator" /></label></td>
				<td valign="top"
					class="value ${hasErrors(bean: TNodeInstance, field: 'creator', 'errors')}">
					${session.user}
				</td>
			</tr>
			
			
		</tbody>
	</table>


	<g:hiddenField name="snode" value="${TNodeInstance?.parent_id}" />
	<g:hiddenField name="report" value="${report}" /></div>
	<div class="buttons"><span class="button"><g:submitButton
		name="create" class="save"
		value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
	</div>
</g:form>
</div>
</div>

</body>
</html>
