
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usercomment.label', default: 'Usercomment')}" />
        <div class="nav">
            <span class="menuButton" style="font-size:1.2em;"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1 style="color: #0070c0; font-size: 1.2em;"><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'usercomment.id.label', default: 'Id')}" />
                            <g:sortableColumn property="dateCreated" title="${message(code: 'usercomment.date.label', default: 'Date Created')}" />
                            <g:sortableColumn property="article" title="${message(code: 'usercomment.article.label', default: 'Article')}" />
                            <g:sortableColumn property="title" title="${message(code: 'usercomment.title.label', default: 'Title')}" />
                            <g:sortableColumn property="user" title="${message(code: 'usercomment.user.label', default: 'User')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${usercommentInstanceList}" status="i" var="usercommentInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${usercommentInstance.id}">${fieldValue(bean: usercommentInstance, field: "id")}</g:link></td>
                            <td><g:formatDate date="${usercommentInstance.dateCreated}" type="datetime" style="SHORT" timeStyle="SHORT"/></td>
                            <td><g:link controller="article" action="display" id="${usercommentInstance?.article?.id}">${usercommentInstance?.article}</g:link></td>
                            <td><g:link controller="usercomment" action="show" id="${usercommentInstance?.id}">${usercommentInstance?.title}</g:link></td>
                            <td><g:link controller="user" action="show" id="${usercommentInstance?.commentator?.id}">${usercommentInstance?.commentator}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${usercommentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
