
<%@ page import="systemadministration.usermanagement.Rule" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rule.label', default: 'Rule')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'rule.id.label', default: 'Id')}" />
                        
                            <th><g:message code="rule.module.label" default="Module" /></th>
                   	    
                            <g:sortableColumn property="description" title="${message(code: 'rule.description.label', default: 'Description')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'rule.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'rule.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'rule.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${ruleInstanceList}" status="i" var="ruleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${ruleInstance.id}">${fieldValue(bean: ruleInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: ruleInstance, field: "module")}</td>
                        
                            <td>${fieldValue(bean: ruleInstance, field: "description")}</td>
                        
                            <td>${fieldValue(bean: ruleInstance, field: "name")}</td>
                        
                            <td><g:formatDate date="${ruleInstance.lastUpdated}" /></td>
                        
                            <td><g:formatDate date="${ruleInstance.dateCreated}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${ruleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
