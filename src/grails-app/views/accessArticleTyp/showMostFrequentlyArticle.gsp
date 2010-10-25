
<%@ page import="systemadministration.recommender.AccessArticleTyp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'accessArticleTyp.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="typeAccess" title="${message(code: 'accessArticleTyp.typeAccess.label', default: 'Type Access')}" />
                        
                            <th><g:message code="accessArticleTyp.article.label" default="Article" /></th>
                   	    
                            <g:sortableColumn property="countAll" title="${message(code: 'accessArticleTyp.countAll.label', default: 'Count All')}" />
                        
                            <g:sortableColumn property="countPara0" title="${message(code: 'accessArticleTyp.countPara0.label', default: 'Count Para0')}" />
                        
                            <g:sortableColumn property="countPara1" title="${message(code: 'accessArticleTyp.countPara1.label', default: 'Count Para1')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${accessArticleTypInstanceList}" status="i" var="accessArticleTypInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${accessArticleTypInstance.id}">${fieldValue(bean: accessArticleTypInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: accessArticleTypInstance, field: "typeAccess")}</td>
                        
                            <td>${fieldValue(bean: accessArticleTypInstance, field: "article")}</td>
                        
                            <td>${fieldValue(bean: accessArticleTypInstance, field: "countAll")}</td>
                        
                            <td>${fieldValue(bean: accessArticleTypInstance, field: "countPara0")}</td>
                        
                            <td>${fieldValue(bean: accessArticleTypInstance, field: "countPara1")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${accessArticleTypInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
