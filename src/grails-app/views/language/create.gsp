
<%@ page import="systemadministration.modulmanagement.Language" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'language.label', default: 'Language')}" />
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
            <g:hasErrors bean="${languageInstance}">
            <div class="errors">
                <g:renderErrors bean="${languageInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="countryCode"><g:message code="language.countryCode.label" default="Country Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'countryCode', 'errors')}">
                                    <g:textField name="countryCode" maxlength="5" value="${languageInstance?.countryCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="language.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" maxlength="100" value="${languageInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="language.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${languageInstance?.dateCreated}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="language.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${languageInstance?.lastUpdated}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author"><g:message code="language.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${languageInstance?.author?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="editor"><g:message code="language.editor.label" default="Editor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'editor', 'errors')}">
                                    <g:select name="editor.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${languageInstance?.editor?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="enabled"><g:message code="language.enabled.label" default="Enabled" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'enabled', 'errors')}">
                                    <g:checkBox name="enabled" value="${languageInstance?.enabled}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="site"><g:message code="language.site.label" default="Site" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'site', 'errors')}">
                                    <g:checkBox name="site" value="${languageInstance?.site}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="report"><g:message code="language.report.label" default="Report" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: languageInstance, field: 'report', 'errors')}">
                                    <g:checkBox name="report" value="${languageInstance?.report}" />
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
