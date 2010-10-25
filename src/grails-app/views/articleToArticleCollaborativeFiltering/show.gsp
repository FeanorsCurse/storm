
<%@ page import="systemadministration.recommender.ArticleToArticleCollaborativeFiltering" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleToArticleCollaborativeFiltering.label', default: 'ArticleToArticleCollaborativeFiltering')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="articleToArticleCollaborativeFiltering.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="articleToArticleCollaborativeFiltering.typeAccess.label" default="Type Access" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "typeAccess")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="articleToArticleCollaborativeFiltering.articleA.label" default="Article A" /></td>
                            
                            <td valign="top" class="value"><g:link controller="article" action="show" id="${articleToArticleCollaborativeFilteringInstance?.articleA?.id}">${articleToArticleCollaborativeFilteringInstance?.articleA?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="articleToArticleCollaborativeFiltering.articleB.label" default="Article B" /></td>
                            
                            <td valign="top" class="value"><g:link controller="article" action="show" id="${articleToArticleCollaborativeFilteringInstance?.articleB?.id}">${articleToArticleCollaborativeFilteringInstance?.articleB?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="articleToArticleCollaborativeFiltering.counter.label" default="Counter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: articleToArticleCollaborativeFilteringInstance, field: "counter")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${articleToArticleCollaborativeFilteringInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
