
<%@ page import="systemadministration.recommender.ArticleToArticleCollaborativeFiltering" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'articleToArticleCollaborativeFiltering.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="typeAccess" title="${message(code: 'articleToArticleCollaborativeFiltering.typeAccess.label', default: 'Type Access')}" />
                        
                            <th><g:message code="articleToArticleCollaborativeFiltering.articleA.label" default="Article A" /></th>
                   	    
                            <th><g:message code="articleToArticleCollaborativeFiltering.articleB.label" default="Article B" /></th>
                   	    
                            <g:sortableColumn property="counter" title="${message(code: 'articleToArticleCollaborativeFiltering.counter.label', default: 'Counter')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${articleToArticleCollaborativeFilteringInstanceList}" status="i" var="articleToArticleCollaborativeFilteringInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${articleToArticleCollaborativeFilteringInstance.id}">${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "typeAccess")}</td>
                        
                            <td>${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "articleA")}</td>
                        
                            <td>${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "articleB")}</td>
                        
                            <td>${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "counter")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${articleToArticleCollaborativeFilteringInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
