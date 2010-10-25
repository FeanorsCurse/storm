<%@ page import="shemaEditor.indicatorAdministration.Category" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'category.label', default: 'Category')}" />
                 <link rel="stylesheet" href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
        <title><g:message code="category.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" align="right"> 
            <span class="menuButton"><g:link class="list" action="list"><g:message code="category.list.label" args="[entityName]" /></g:link></span> |
            <span class="menuButton"><g:link class="create" action="create"><g:message code="category.create.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="category.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${categoryInstance}">
            <div class="errors">
                <g:renderErrors bean="${categoryInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${categoryInstance?.id}" />
                <g:hiddenField name="version" value="${categoryInstance?.version}" />
                <div class="schemadialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="category.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${categoryInstance?.name}" STYLE="width: 300px" size="10"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: categoryInstance, field: 'indicators', 'errors')}">
                                    
<ul>
<g:each in="${categoryInstance?.indicators?}" var="i">
    <li><g:link controller="indicator" action="show" id="${i.id}">${i}</g:link></li>
</g:each>
</ul>

                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons" style="margin-top:50px;">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
