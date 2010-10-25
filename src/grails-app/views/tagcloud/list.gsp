
<%@ page import="interactiveFeatures.Tagcloud.Tagcloud" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tagcloud.label', default: 'Tagcloud')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'tagcloud.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="tagName" title="${message(code: 'tagcloud.tagName.label', default: 'Tag Name')}" />
                        
                            <g:sortableColumn property="tagLink" title="${message(code: 'tagcloud.tagLink.label', default: 'Tag Link')}" />
                        
                            <g:sortableColumn property="cssStyle" title="${message(code: 'tagcloud.cssStyle.label', default: 'Css Style')}" />
                        
                            <th><g:message code="tagcloud.accesses.label" default="Accesses" /></th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tagcloudInstanceList}" status="i" var="tagcloudInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tagcloudInstance.id}">${fieldValue(bean: tagcloudInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: tagcloudInstance, field: "tagName")}</td>
                        
                            <td>${fieldValue(bean: tagcloudInstance, field: "tagLink")}</td>
                        
                            <td>${fieldValue(bean: tagcloudInstance, field: "cssStyle")}</td>
                        
                            <td>${fieldValue(bean: tagcloudInstance, field: "accesses")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${tagcloudInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
