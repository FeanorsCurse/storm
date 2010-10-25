
<%@ page import="systemadministration.usermanagement.Role" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'role.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'role.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'role.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'role.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'role.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${roleInstanceList}" status="i" var="roleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${roleInstance.id}">${fieldValue(bean: roleInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: roleInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: roleInstance, field: "name")}</td>
                        
                            <td><g:formatDate date="${roleInstance.lastUpdated}" /></td>
                        
                            <td><g:formatDate date="${roleInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${roleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
