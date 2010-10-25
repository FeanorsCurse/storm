
<%@ page import="systemadministration.log.SecurityLog" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'securityLog.label', default: 'SecurityLog')}" />
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
            <g:hasErrors bean="${securityLogInstance}">
            <div class="errors">
                <g:renderErrors bean="${securityLogInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kindOfAction"><g:message code="securityLog.kindOfAction.label" default="Kind Of Action" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: securityLogInstance, field: 'kindOfAction', 'errors')}">
                                    <g:textField name="kindOfAction" value="${fieldValue(bean: securityLogInstance, field: 'kindOfAction')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="userIP"><g:message code="securityLog.userIP.label" default="User IP" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: securityLogInstance, field: 'userIP', 'errors')}">
                                    <g:textField name="userIP" value="${securityLogInstance?.userIP}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="securityLog.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: securityLogInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${securityLogInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user"><g:message code="securityLog.user.label" default="User" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: securityLogInstance, field: 'user', 'errors')}">
                                    <g:textField name="user" value="${securityLogInstance?.user}" />
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
