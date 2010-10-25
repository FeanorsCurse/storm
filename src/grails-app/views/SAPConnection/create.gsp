
<%@ page import="systemadministration.externalsystems.SAPConnection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'SAPConnection.label', default: 'SAPConnection')}" />
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
            <g:hasErrors bean="${SAPConnectionInstance}">
            <div class="errors">
                <g:renderErrors bean="${SAPConnectionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="client"><g:message code="SAPConnection.client.label" default="Client" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'client', 'errors')}">
                                    <g:textField name="client" value="${SAPConnectionInstance?.client}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locale"><g:message code="SAPConnection.locale.label" default="Locale" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'locale', 'errors')}">
                                    <g:textField name="locale" value="${SAPConnectionInstance?.locale}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="SAPConnection.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${SAPConnectionInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="SAPConnection.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'password', 'errors')}">
                                    <g:textField name="password" value="${SAPConnectionInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="SAPConnection.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${SAPConnectionInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author"><g:message code="SAPConnection.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${SAPConnectionInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="system"><g:message code="SAPConnection.system.label" default="System" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'system', 'errors')}">
                                    <g:textField name="system" value="${SAPConnectionInstance?.system}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="SAPConnection.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${SAPConnectionInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="SAPConnection.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${SAPConnectionInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="SAPConnection.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${SAPConnectionInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="login"><g:message code="SAPConnection.login.label" default="Login" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SAPConnectionInstance, field: 'login', 'errors')}">
                                    <g:textField name="login" value="${SAPConnectionInstance?.login}" />
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
