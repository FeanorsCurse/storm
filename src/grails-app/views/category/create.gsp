
<%@ page import="shemaEditor.indicatorAdministration.Category" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
                 <link rel="stylesheet" href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
        <title><g:message code="category.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" align="right">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="category.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="category.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${categoryInstance}">
            <div class="errors">
                <g:renderErrors bean="${categoryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table class="option">
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="category.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${categoryInstance?.name}" />
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
