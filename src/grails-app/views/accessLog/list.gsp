
<%@ page import="systemadministration.log.AccessLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'accessLog.label', default: 'AccessLog')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accessLog.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="typeAccess" title="${message(code: 'accessLog.typeAccess.label', default: 'Type Access')}" />
                        
                            <th><g:message code="accessLog.user.label" default="User" /></th>
                   	    
                            <th><g:message code="accessLog.module.label" default="Module" /></th>
                   	    
                            <th><g:message code="accessLog.article.label" default="Article" /></th>
                   	    
                            <g:sortableColumn property="dateCreated" title="${message(code: 'accessLog.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accessLogInstanceList}" status="i" var="accessLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accessLogInstance.id}">${fieldValue(bean: accessLogInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accessLogInstance, field: "typeAccess")}</td>
                        
                            <td>${fieldValue(bean: accessLogInstance, field: "user")}</td>
                        
                            <td>${fieldValue(bean: accessLogInstance, field: "module")}</td>
                        
                            <td>${fieldValue(bean: accessLogInstance, field: "article")}</td>
                        
                            <td><g:formatDate date="${accessLogInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accessLogInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
