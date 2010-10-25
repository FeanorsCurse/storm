
<%@ page import="systemadministration.log.SecurityLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'securityLog.label', default: 'SecurityLog')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'securityLog.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="kindOfAction" title="${message(code: 'securityLog.kindOfAction.label', default: 'Kind Of Action')}" />
                        
                            <g:sortableColumn property="userIP" title="${message(code: 'securityLog.userIP.label', default: 'User IP')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'securityLog.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="user" title="${message(code: 'securityLog.user.label', default: 'User')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${securityLogInstanceList}" status="i" var="securityLogInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${securityLogInstance.id}">${fieldValue(bean: securityLogInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: securityLogInstance, field: "kindOfAction")}</td>
                        
                            <td>${fieldValue(bean: securityLogInstance, field: "userIP")}</td>
                        
                            <td><g:formatDate date="${securityLogInstance.dateCreated}" /></td>
                        
                            <td>${fieldValue(bean: securityLogInstance, field: "user")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${securityLogInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
