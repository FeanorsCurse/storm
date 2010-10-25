
<%@ page import="interactiveFeatures.Tagcloud.Tagcloud" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tagcloud.label', default: 'Tagcloud')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${tagcloudInstance}">
            <div class="errors">
                <g:renderErrors bean="${tagcloudInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${tagcloudInstance?.id}" />
                <g:hiddenField name="version" value="${tagcloudInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tagName"><g:message code="tagcloud.tagName.label" default="Tag Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tagcloudInstance, field: 'tagName', 'errors')}">
                                    <g:textField name="tagName" value="${tagcloudInstance?.tagName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tagLink"><g:message code="tagcloud.tagLink.label" default="Tag Link" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tagcloudInstance, field: 'tagLink', 'errors')}">
                                    <g:textField name="tagLink" value="${tagcloudInstance?.tagLink}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cssStyle"><g:message code="tagcloud.cssStyle.label" default="Css Style" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tagcloudInstance, field: 'cssStyle', 'errors')}">
                                    <g:textField name="cssStyle" value="${tagcloudInstance?.cssStyle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="accesses"><g:message code="tagcloud.accesses.label" default="Accesses" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: tagcloudInstance, field: 'accesses', 'errors')}">
                                    <g:select name="accesses.id" from="${systemadministration.log.AccessLog.list()}" optionKey="id" value="${tagcloudInstance?.accesses?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
