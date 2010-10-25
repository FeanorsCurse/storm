
<%@ page import="systemadministration.log.ChangeLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'changeLog.label', default: 'ChangeLog')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'changeLog.id.label', default: 'Id')}" />
                        
                            <th><g:message code="changeLog.module.label" default="Module" /></th>
                   	    
                            <g:sortableColumn property="value" title="${message(code: 'changeLog.value.label', default: 'Value')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'changeLog.dateCreated.label', default: 'Date Created')}" />
                        
                            <th><g:message code="changeLog.user.label" default="User" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${changeLogInstanceList}" status="i" var="changeLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${changeLogInstance.id}">${fieldValue(bean: changeLogInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: changeLogInstance, field: "module")}</td>
                        
                            <td>${fieldValue(bean: changeLogInstance, field: "value")}</td>
                        
                            <td><g:formatDate date="${changeLogInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: changeLogInstance, field: "user")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${changeLogInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
