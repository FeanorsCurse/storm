
<%@ page import="shemaEditor.indicatorAdministration.Indicator" %>
<html>
    <head>
     <meta name="layout" content="main" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
         <link rel="stylesheet" href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
        <g:set var="entityName" value="${message(code: 'indicator.label', default: 'Indicator')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" align="right">
          
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            | <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                      <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: indicatorInstance, field: "name")}</td>
                            
                        </tr>
                        
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: indicatorInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.creationDate.label" default="Creation Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm:ss" date="${indicatorInstance?.creationDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.unit.label" default="Unit" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: indicatorInstance, field: "unit")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.category.label" default="Category" /></td>
                            
                            <td valign="top" class="value"><g:link controller="category" action="show" id="${indicatorInstance?.category?.id}">${indicatorInstance?.category?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                   <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.lastModifiedDate.label" default="Last Modified Date" /></td>

                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm:ss" date="${indicatorInstance?.lastModifiedDate}" /></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.creator.label" default="Creator" /></td>

                            <td valign="top" class="value"><g:link controller="user" action="show" id="${indicatorInstance?.creator?.id}">${indicatorInstance?.creator?.encodeAsHTML()}</g:link></td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="indicator.lastModifiedBy.label" default="Last Modified By" /></td>

                            <td valign="top" class="value"><g:link controller="user" action="show" id="${indicatorInstance?.lastModifiedBy?.id}">${indicatorInstance?.lastModifiedBy?.encodeAsHTML()}</g:link></td>

                        </tr>
                    
                    </tbody>
                </table>
            </div>
           
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${indicatorInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
