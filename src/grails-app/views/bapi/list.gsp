
<%@ page import="systemadministration.externalsystems.Bapi" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bapi.label', default: 'Bapi')}" />
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
            <div class="list" style="overflow:scroll;">
                <table class="list">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'bapi.id.label', default: 'Id')}" />
                        
                            <th><g:message code="bapi.connection.label" default="Connection" /></th>
                   	    
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'bapi.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="importParameter" title="${message(code: 'bapi.importParameter.label', default: 'Import Parameter')}" />
                        
                            <th><g:message code="bapi.author.label" default="Author" /></th>
                   	    
                            <g:sortableColumn property="description" title="${message(code: 'bapi.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${bapiInstanceList}" status="i" var="bapiInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${bapiInstance.id}">${fieldValue(bean: bapiInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: bapiInstance, field: "connection")}</td>
                        
                            <td><g:formatDate date="${bapiInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: bapiInstance, field: "importParameter")}</td>
                        
                            <td>${fieldValue(bean: bapiInstance, field: "author")}</td>
                        
                            <td>${fieldValue(bean: bapiInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${bapiInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
