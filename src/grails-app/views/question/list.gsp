
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
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table class="list">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'question.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="question" title="${message(code: 'question.question.label', default: 'Question')}" />
                        
                            <th><g:message code="question.author.label" default="Author" /></th>
                   	    
                            <g:sortableColumn property="title" title="${message(code: 'question.title.label', default: 'Title')}" />
                        
                            <th><g:message code="question.editor.label" default="Editor" /></th>
                   	    
                            <g:sortableColumn property="answer" title="${message(code: 'question.answer.label', default: 'Answer')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${questionInstanceList}" status="i" var="questionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${questionInstance.id}">${fieldValue(bean: questionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "question")}</td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "author")}</td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "title")}</td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "editor")}</td>
                        
                            <td>${fieldValue(bean: questionInstance, field: "answer")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${questionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
