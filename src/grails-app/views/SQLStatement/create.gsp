
<%@ page import="systemadministration.externalsystems.SQLStatement" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'SQLStatement.label', default: 'SQLStatement')}" />
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
            <g:hasErrors bean="${SQLStatementInstance}">
            <div class="errors">
                <g:renderErrors bean="${SQLStatementInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author"><g:message code="SQLStatement.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${SQLStatementInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="connection"><g:message code="SQLStatement.connection.label" default="Connection" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'connection', 'errors')}">
                                    <g:select name="connection.id" from="${systemadministration.externalsystems.DatabaseConnection.list()}" optionKey="id" value="${SQLStatementInstance?.connection?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sqlStatement"><g:message code="SQLStatement.sqlStatement.label" default="Sql Statement" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'sqlStatement', 'errors')}">
                                    <g:textField name="sqlStatement" value="${SQLStatementInstance?.sqlStatement}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="SQLStatement.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${SQLStatementInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="SQLStatement.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${SQLStatementInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="SQLStatement.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${SQLStatementInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="SQLStatement.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: SQLStatementInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${SQLStatementInstance?.dateCreated}"  />
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
