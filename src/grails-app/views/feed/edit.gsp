
<%@ page import="rss.Feed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'feed.label', default: 'Feed')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${feedInstance}">
            <div class="errors">
                <g:renderErrors bean="${feedInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${feedInstance?.id}" />
                <g:hiddenField name="version" value="${feedInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="feed.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" maxlength="100" value="${feedInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="feed.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${feedInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="copyright"><g:message code="feed.copyright.label" default="Copyright" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'copyright', 'errors')}">
                                    <g:textField name="copyright" value="${feedInstance?.copyright}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="link"><g:message code="feed.link.label" default="Link" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'link', 'errors')}">
                                    <g:textField name="link" value="${feedInstance?.link}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="author"><g:message code="feed.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${feedInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastModifiedBy"><g:message code="feed.lastModifiedBy.label" default="Last Modified By" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'lastModifiedBy', 'errors')}">
                                    <g:select name="lastModifiedBy.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${feedInstance?.lastModifiedBy?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="creationDate"><g:message code="feed.creationDate.label" default="Creation Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'creationDate', 'errors')}">
                                    <g:datePicker name="creationDate" precision="day" value="${feedInstance?.creationDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastModified"><g:message code="feed.lastModified.label" default="Last Modified" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'lastModified', 'errors')}">
                                    <g:datePicker name="lastModified" precision="day" value="${feedInstance?.lastModified}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
