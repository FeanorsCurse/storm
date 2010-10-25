
<%@ page import="systemadministration.externalsystems.Bapi" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bapi.label', default: 'Bapi')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
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
                            <td valign="top" class="name"><g:message code="bapi.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.connection.label" default="Connection" /></td>
                            
                            <td valign="top" class="value"><g:link controller="SAPConnection" action="show" id="${bapiInstance?.connection?.id}">${bapiInstance?.connection?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bapiInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.importParameter.label" default="Import Parameter" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "importParameter")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${bapiInstance?.author?.id}">${bapiInstance?.author?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.exportStructure.label" default="Export Structure" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "exportStructure")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${bapiInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.bapi.label" default="Bapi" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "bapi")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="bapi.exportValue.label" default="Export Value" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: bapiInstance, field: "exportValue")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${bapiInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
