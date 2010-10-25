
<%@ page import="systemadministration.modulmanagement.RssFeed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rssFeed.label', default: 'RssFeed')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'rssFeed.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="showDescription" title="${message(code: 'rssFeed.showDescription.label', default: 'Show Description')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'rssFeed.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="showDate" title="${message(code: 'rssFeed.showDate.label', default: 'Show Date')}" />
                        
                            <g:sortableColumn property="url" title="${message(code: 'rssFeed.url.label', default: 'Url')}" />
                        
                            <g:sortableColumn property="maxNumItems" title="${message(code: 'rssFeed.maxNumItems.label', default: 'Max Num Items')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${rssFeedInstanceList}" status="i" var="rssFeedInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${rssFeedInstance.id}">${fieldValue(bean: rssFeedInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatBoolean boolean="${rssFeedInstance.showDescription}" /></td>
                        
                            <td><g:formatDate date="${rssFeedInstance.lastUpdated}" /></td>
                        
                            <td><g:formatBoolean boolean="${rssFeedInstance.showDate}" /></td>
                        
                            <td>${fieldValue(bean: rssFeedInstance, field: "url")}</td>
                        
                            <td>${fieldValue(bean: rssFeedInstance, field: "maxNumItems")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${rssFeedInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
