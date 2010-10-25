
<%@ page import="systemadministration.modulmanagement.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="question" action="display"><g:message code="question.link.back" default="Back" /></g:link></span>
        </div>
        <div class="body">
            <h2><g:message code="question.display.new" default="New Questions" /></h2>
            <p><g:message code="question.display.new.text" default="Here you see new Questions" /></p>  
            <table class="list">
	        <g:each in="${questionInstanceList}" status="i" var="questionInstance">
	               	<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	               	<td>
	               	<g:link action="show2" id="${questionInstance.id}">
	              	 	${fieldValue(bean: questionInstance, field: "title")}
	              	</g:link>
	              </td>
	           </tr>    
	        </g:each>
	        </table> 
        </div>
    </body>
</html>
