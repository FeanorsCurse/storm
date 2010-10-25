
<%@ page import="rss.Feed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'feed.label', default: 'Feed')}" />
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
                            <td valign="top" class="name"><g:message code="feed.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: feedInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: feedInstance, field: "title")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: feedInstance, field: "description")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.copyright.label" default="Copyright" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: feedInstance, field: "copyright")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.link.label" default="Link" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: feedInstance, field: "link")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${feedInstance?.author?.id}">${feedInstance?.author?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.lastModifiedBy.label" default="Last Modified By" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${feedInstance?.lastModifiedBy?.id}">${feedInstance?.lastModifiedBy?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.creationDate.label" default="Creation Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${feedInstance?.creationDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="feed.lastModified.label" default="Last Modified" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${feedInstance?.lastModified}" /></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${feedInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
