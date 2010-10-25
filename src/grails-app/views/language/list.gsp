
<%@ page import="systemadministration.modulmanagement.Language" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'language.label', default: 'Language')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'language.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="countryCode" title="${message(code: 'language.countryCode.label', default: 'Country Code')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'language.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'language.dateCreated.label', default: 'Date Created')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'language.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <th><g:message code="language.author.label" default="Author" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${languageInstanceList}" status="i" var="languageInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${languageInstance.id}">${fieldValue(bean: languageInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: languageInstance, field: "countryCode")}</td>
                        
                            <td>${fieldValue(bean: languageInstance, field: "name")}</td>
                        
                            <td><g:formatDate date="${languageInstance.dateCreated}" /></td>
                        
                            <td><g:formatDate date="${languageInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: languageInstance, field: "author")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${languageInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
