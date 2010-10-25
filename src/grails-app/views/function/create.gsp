
<%@ page import="demo.Function" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="backend" />
        <g:set var="entityName" value="${message(code: 'function.label', default: 'Function')}" />
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
            <g:hasErrors bean="${functionInstance}">
            <div class="errors">
                <g:renderErrors bean="${functionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="notiz"><g:message code="function.notiz.label" default="Notiz" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: functionInstance, field: 'notiz', 'errors')}">
                                    <g:textField name="notiz" value="${functionInstance?.notiz}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="moduleName"><g:message code="function.moduleName.label" default="Module Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: functionInstance, field: 'moduleName', 'errors')}">
                                    <g:textField name="moduleName" value="${functionInstance?.moduleName}" />
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
