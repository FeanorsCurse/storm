
<%@ page import="shemaEditor.shemaAdministration.TNode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="backend" />
        <g:set var="entityName" value="${message(code: 'TNode.label', default: 'TNode')}" />
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
      
                        
                            <g:sortableColumn property="lastModified" title="${message(code: 'node.lastModified.label', default: 'Last Modified')}" />
                        
                            <th><g:message code="node.creator.label" default="Creator" /></th>
                   	    
                            <g:sortableColumn property="creationDate" title="${message(code: 'node.creationDate.label', default: 'Creation Date')}" />
                        
                            <g:sortableColumn property="parentId" title="${message(code: 'node.parentId.label', default: 'Parent Id')}" />
                        
                            <g:sortableColumn property="description" title="${message(code: 'node.description.label', default: 'Description')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${TNodeInstanceList}" status="i" var="TNodeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                           
                            <td><g:formatDate date="${TNodeInstance.lastModified}" /></td>
                        
                            <td>${fieldValue(bean: TNodeInstance, field: "creator")}</td>
                        
                            <td><g:formatDate date="${TNodeInstance.creationDate}" /></td>
                        
                            <td>${fieldValue(bean: TNodeInstance, field: "parent_id")}</td>
                        
                            <td>${fieldValue(bean: TNodeInstance, field: "description")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${TNodeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
