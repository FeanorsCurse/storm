
<%@ page import="systemadministration.recommender.ArticleViewedByInterestgroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'articleViewedByInterestgroup.id.label', default: 'Id')}" />
                        
                            <th><g:message code="articleViewedByInterestgroup.article.label" default="Article" /></th>
                   	    
                            <th><g:message code="articleViewedByInterestgroup.group.label" default="Group" /></th>
                   	    
                            <g:sortableColumn property="counter" title="${message(code: 'articleViewedByInterestgroup.counter.label', default: 'Counter')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${articleViewedByInterestgroupInstanceList}" status="i" var="articleViewedByInterestgroupInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${articleViewedByInterestgroupInstance.id}">${fieldValue(bean: articleViewedByInterestgroupInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: articleViewedByInterestgroupInstance, field: "article")}</td>
                        
                            <td>${fieldValue(bean: articleViewedByInterestgroupInstance, field: "group")}</td>
                        
                            <td>${fieldValue(bean: articleViewedByInterestgroupInstance, field: "counter")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${articleViewedByInterestgroupInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
