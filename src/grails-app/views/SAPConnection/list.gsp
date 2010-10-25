
<%@ page import="systemadministration.externalsystems.SAPConnection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'SAPConnection.label', default: 'SAPConnection')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'SAPConnection.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="client" title="${message(code: 'SAPConnection.client.label', default: 'Client')}" />
                        
                            <g:sortableColumn property="locale" title="${message(code: 'SAPConnection.locale.label', default: 'Locale')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'SAPConnection.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="password" title="${message(code: 'SAPConnection.password.label', default: 'Password')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'SAPConnection.url.label', default: 'Url')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${SAPConnectionInstanceList}" status="i" var="SAPConnectionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${SAPConnectionInstance.id}">${fieldValue(bean: SAPConnectionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: SAPConnectionInstance, field: "client")}</td>
                        
                            <td>${fieldValue(bean: SAPConnectionInstance, field: "locale")}</td>
                        
                            <td><g:formatDate date="${SAPConnectionInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: SAPConnectionInstance, field: "password")}</td>
                        
                            <td>${fieldValue(bean: SAPConnectionInstance, field: "url")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${SAPConnectionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
