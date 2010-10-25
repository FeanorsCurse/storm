
<%@ page import="systemadministration.externalsystems.SQLStatement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'SQLStatement.label', default: 'SQLStatement')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'SQLStatement.id.label', default: 'Id')}" />
                        
                            <th><g:message code="SQLStatement.author.label" default="Author" /></th>
                   	    
                            <th><g:message code="SQLStatement.connection.label" default="Connection" /></th>
                   	    
                            <g:sortableColumn property="sqlStatement" title="${message(code: 'SQLStatement.sqlStatement.label', default: 'Sql Statement')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'SQLStatement.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'SQLStatement.name.label', default: 'Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${SQLStatementInstanceList}" status="i" var="SQLStatementInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${SQLStatementInstance.id}">${fieldValue(bean: SQLStatementInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: SQLStatementInstance, field: "author")}</td>
                        
                            <td>${fieldValue(bean: SQLStatementInstance, field: "connection")}</td>
                        
                            <td>${fieldValue(bean: SQLStatementInstance, field: "sqlStatement")}</td>
                        
                            <td>${fieldValue(bean: SQLStatementInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: SQLStatementInstance, field: "name")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${SQLStatementInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
