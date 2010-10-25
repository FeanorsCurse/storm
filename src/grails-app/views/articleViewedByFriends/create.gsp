
<%@ page import="systemadministration.recommender.ArticleViewedByFriends" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleViewedByFriends.label', default: 'ArticleViewedByFriends')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${articleViewedByFriendsInstance}">
            <div class="errors">
                <g:renderErrors bean="${articleViewedByFriendsInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="friend"><g:message code="articleViewedByFriends.friend.label" default="Friend" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByFriendsInstance, field: 'friend', 'errors')}">
                                    <g:select name="friend.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${articleViewedByFriendsInstance?.friend?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="article"><g:message code="articleViewedByFriends.article.label" default="Article" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByFriendsInstance, field: 'article', 'errors')}">
                                    <g:select name="article.id" from="${ReportEditorMain.ArticleEditor.Article.list()}" optionKey="id" value="${articleViewedByFriendsInstance?.article?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="counter"><g:message code="articleViewedByFriends.counter.label" default="Counter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByFriendsInstance, field: 'counter', 'errors')}">
                                    <g:textField name="counter" value="${fieldValue(bean: articleViewedByFriendsInstance, field: 'counter')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
