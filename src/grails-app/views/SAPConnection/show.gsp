
<%@ page import="systemadministration.externalsystems.SAPConnection" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'SAPConnection.label', default: 'SAPConnection')}" />
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
                            <td valign="top" class="name"><g:message code="SAPConnection.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.client.label" default="Client" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "client")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.locale.label" default="Locale" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "locale")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${SAPConnectionInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.password.label" default="Password" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "password")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.url.label" default="Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "url")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${SAPConnectionInstance?.author?.id}">${SAPConnectionInstance?.author?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.system.label" default="System" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "system")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${SAPConnectionInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="SAPConnection.login.label" default="Login" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: SAPConnectionInstance, field: "login")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${SAPConnectionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
