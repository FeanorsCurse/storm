
<%@ page import="systemadministration.modulmanagement.Config" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'config.label', default: 'Config')}" />
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
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'config.id.label', default: 'Id')}" />
                        
                            <th><g:message code="config.module.label" default="Module" /></th>
                   	    
                            <g:sortableColumn property="description" title="${message(code: 'config.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'config.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="value" title="${message(code: 'config.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'config.lastUpdated.label', default: 'Last Updated')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${configInstanceList}" status="i" var="configInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${configInstance.id}">${fieldValue(bean: configInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: configInstance, field: "module")}</td>
                        
                            <td>${fieldValue(bean: configInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: configInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: configInstance, field: "value")}</td>
                        
                            <td><g:formatDate date="${configInstance.lastUpdated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${configInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
