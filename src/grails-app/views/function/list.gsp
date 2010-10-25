
<%@ page import="demo.Function" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="backend" />
        <g:set var="entityName" value="${message(code: 'function.label', default: 'Function')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'function.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="notiz" title="${message(code: 'function.notiz.label', default: 'Notiz')}" />
                        
                            <g:sortableColumn property="moduleName" title="${message(code: 'function.moduleName.label', default: 'Module Name')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${functionInstanceList}" status="i" var="functionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${functionInstance.id}">${fieldValue(bean: functionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: functionInstance, field: "notiz")}</td>
                        
                            <td>${fieldValue(bean: functionInstance, field: "moduleName")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${functionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
