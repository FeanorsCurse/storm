
<%@ page import="interactiveFeatures.Help.HelpSection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <title><g:message code="help.listsections.label" default="List Help Sections" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.listsections.label" default="List Help Sections" /></h1>
            <div class="list">
                <table class="list">
                    <thead>
                        <tr>
                            <th><g:message code="default.action.label" default="Action" /></th>
                            <g:sortableColumn property="name" title="${message(code: 'default.name.label', default: 'Name')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${helpSectionInstanceList}" status="i" var="helpSectionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                                <g:link controller="help" action="editsection" id="${helpSectionInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-edit.png')}" title="${message(code: 'help.editarticle.label', default: 'Edit Help Article')}" alt="${message(code: 'help.editsection.label', default: 'Edit Help Section')}" /></g:link>
                                <g:link controller="help" action="deletesection" id="${helpSectionInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-close.png')}" title="${message(code: 'help.deletearticle.label', default: 'Delete Help Article')}" alt="${message(code: 'help.deletesection.label', default: 'Delete Help Section')}" /></g:link>
                            </td>
                            <td><g:link controller="help" action="section" id="${helpSectionInstance?.id}">${helpSectionInstance?.name}</g:link></td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${helpSectionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
