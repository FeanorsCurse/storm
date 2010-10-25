
<%@ page import="systemadministration.externalsystems.DatabaseConnection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'databaseConnection.label', default: 'DatabaseConnection')}" />
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
            <g:hasErrors bean="${databaseConnectionInstance}">
            <div class="errors">
                <g:renderErrors bean="${databaseConnectionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="databaseConnection.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${databaseConnectionInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="databaseConnection.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'password', 'errors')}">
                                    <g:textField name="password" value="${databaseConnectionInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="databaseConnection.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${databaseConnectionInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author"><g:message code="databaseConnection.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${databaseConnectionInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="databaseConnection.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${databaseConnectionInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="databaseConnection.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${databaseConnectionInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="databaseConnection.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${databaseConnectionInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="driver"><g:message code="databaseConnection.driver.label" default="Driver" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'driver', 'errors')}">
                                    <g:textField name="driver" value="${databaseConnectionInstance?.driver}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user"><g:message code="databaseConnection.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: databaseConnectionInstance, field: 'user', 'errors')}">
                                    <g:textField name="user" value="${databaseConnectionInstance?.user}" />
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
