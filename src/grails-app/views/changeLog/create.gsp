
<%@ page import="systemadministration.log.ChangeLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'changeLog.label', default: 'ChangeLog')}" />
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
            <g:hasErrors bean="${changeLogInstance}">
            <div class="errors">
                <g:renderErrors bean="${changeLogInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="module"><g:message code="changeLog.module.label" default="Module" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeLogInstance, field: 'module', 'errors')}">
                                    <g:select name="module.id" from="${systemadministration.modulmanagement.Module.list()}" optionKey="id" value="${changeLogInstance?.module?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="value"><g:message code="changeLog.value.label" default="Value" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeLogInstance, field: 'value', 'errors')}">
                                    <g:textField name="value" value="${changeLogInstance?.value}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="changeLog.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeLogInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${changeLogInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user"><g:message code="changeLog.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: changeLogInstance, field: 'user', 'errors')}">
                                    <g:select name="user.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${changeLogInstance?.user?.id}"  />
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
