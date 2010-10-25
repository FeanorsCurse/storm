
<%@ page import="systemadministration.externalsystems.DatabaseConnection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'databaseConnection.label', default: 'DatabaseConnection')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'databaseConnection.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'databaseConnection.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'databaseConnection.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'databaseConnection.url.label', default: 'Url')}" />
                        
                            <th><g:message code="databaseConnection.author.label" default="Author" /></th>
                   	    
                            <g:sortableColumn property="description" title="${message(code: 'databaseConnection.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${databaseConnectionInstanceList}" status="i" var="databaseConnectionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${databaseConnectionInstance.id}">${fieldValue(bean: databaseConnectionInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${databaseConnectionInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: databaseConnectionInstance, field: "password")}</td>
                        
                            <td>${fieldValue(bean: databaseConnectionInstance, field: "url")}</td>
                        
                            <td>${fieldValue(bean: databaseConnectionInstance, field: "author")}</td>
                        
                            <td>${fieldValue(bean: databaseConnectionInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${databaseConnectionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
