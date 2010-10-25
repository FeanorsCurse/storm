
<%@ page import="systemadministration.modulmanagement.RssFeed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rssFeed.label', default: 'RssFeed')}" />
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
                            <td valign="top" class="name"><g:message code="rssFeed.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rssFeedInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.showDescription.label" default="Show Description" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.showDescription}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${rssFeedInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.showDate.label" default="Show Date" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.showDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.url.label" default="Url" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rssFeedInstance, field: "url")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.maxNumItems.label" default="Max Num Items" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rssFeedInstance, field: "maxNumItems")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.showAuthor.label" default="Show Author" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.showAuthor}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rssFeedInstance, field: "name")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.active.label" default="Active" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.active}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${rssFeedInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.showInNewWindow.label" default="Show In New Window" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.showInNewWindow}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.showTitle.label" default="Show Title" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${rssFeedInstance?.showTitle}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="rssFeed.maxDescriptionLength.label" default="Max Description Length" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: rssFeedInstance, field: "maxDescriptionLength")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${rssFeedInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
