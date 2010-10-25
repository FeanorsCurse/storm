
<%@ page import="systemadministration.recommender.Welcome" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'welcome.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="text" title="${message(code: 'welcome.text.label', default: 'Text')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'welcome.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'welcome.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${welcomeInstanceList}" status="i" var="welcomeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${welcomeInstance.id}">${fieldValue(bean: welcomeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: welcomeInstance, field: "text")}</td>
                        
                            <td><g:formatDate date="${welcomeInstance.lastUpdated}" /></td>
                        
                            <td><g:formatDate date="${welcomeInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${welcomeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
