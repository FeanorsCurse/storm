
<%@ page import="systemadministration.recommender.ArticleViewedByFriends" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'articleViewedByFriends.id.label', default: 'Id')}" />
                        
                            <th><g:message code="articleViewedByFriends.friend.label" default="Friend" /></th>
                   	    
                            <th><g:message code="articleViewedByFriends.article.label" default="Article" /></th>
                   	    
                            <g:sortableColumn property="counter" title="${message(code: 'articleViewedByFriends.counter.label', default: 'Counter')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${articleViewedByFriendsInstanceList}" status="i" var="articleViewedByFriendsInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${articleViewedByFriendsInstance.id}">${fieldValue(bean: articleViewedByFriendsInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: articleViewedByFriendsInstance, field: "friend")}</td>
                        
                            <td>${fieldValue(bean: articleViewedByFriendsInstance, field: "article")}</td>
                        
                            <td>${fieldValue(bean: articleViewedByFriendsInstance, field: "counter")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${articleViewedByFriendsInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
