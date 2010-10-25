
<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <title><g:message code="help.listarticles.label" default="List Help Articles" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.listarticles.label" default="List Help Articles" /></h1>
            <div class="list">
                <table class="list">
                    <thead>
                        <tr>
                            <th><g:message code="default.action.label" default="Action" /></th>
                            <g:sortableColumn property="title" title="${message(code: 'helpArticle.title.label', default: 'Title')}" />
                            <g:sortableColumn property="section" title="${message(code: 'helpArticle.section.label', default: 'Section')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${helpArticleInstanceList}" status="i" var="helpArticleInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                                <g:link controller="help" action="editarticle" id="${helpArticleInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-edit.png')}" title="${message(code: 'help.editarticle.label', default: 'Edit Help Article')}" alt="${message(code: 'help.editarticle.label', default: 'Edit Help Article')}" /></g:link>
                                <g:link controller="help" action="deletearticle" id="${helpArticleInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-close.png')}" title="${message(code: 'help.deletearticle.label', default: 'Delete Help Article')}" alt="${message(code: 'help.deletearticle.label', default: 'Delete Help Article')}" /></g:link>
                            </td>
                            <td><g:link controller="help" action="article" id="${helpArticleInstance?.id}">${helpArticleInstance?.title}</g:link></td>
                            <td><g:link controller="help" action="section" id="${helpArticleInstance?.section?.id}">${helpArticleInstance?.section}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${helpArticleInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
