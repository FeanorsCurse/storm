
<%@ page import="systemadministration.externalsystems.Bapi" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bapi.label', default: 'Bapi')}" />
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
            <g:hasErrors bean="${bapiInstance}">
            <div class="errors">
                <g:renderErrors bean="${bapiInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${bapiInstance?.id}" />
                <g:hiddenField name="version" value="${bapiInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="connection"><g:message code="bapi.connection.label" default="Connection" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'connection', 'errors')}">
                                    <g:select name="connection.id" from="${systemadministration.externalsystems.SAPConnection.list()}" optionKey="id" value="${bapiInstance?.connection?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastUpdated"><g:message code="bapi.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${bapiInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="importParameter"><g:message code="bapi.importParameter.label" default="Import Parameter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'importParameter', 'errors')}">
                                    <g:textField name="importParameter" value="${bapiInstance?.importParameter}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="author"><g:message code="bapi.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${bapiInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="bapi.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${bapiInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="bapi.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${bapiInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="exportStructure"><g:message code="bapi.exportStructure.label" default="Export Structure" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'exportStructure', 'errors')}">
                                    <g:textField name="exportStructure" value="${bapiInstance?.exportStructure}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateCreated"><g:message code="bapi.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${bapiInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="bapi"><g:message code="bapi.bapi.label" default="Bapi" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'bapi', 'errors')}">
                                    <g:textField name="bapi" value="${bapiInstance?.bapi}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="exportValue"><g:message code="bapi.exportValue.label" default="Export Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: bapiInstance, field: 'exportValue', 'errors')}">
                                    <g:textField name="exportValue" value="${bapiInstance?.exportValue}" />
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
