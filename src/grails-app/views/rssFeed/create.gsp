
<%@ page import="systemadministration.modulmanagement.RssFeed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rssFeed.label', default: 'RssFeed')}" />
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
            <g:hasErrors bean="${rssFeedInstance}">
            <div class="errors">
                <g:renderErrors bean="${rssFeedInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="showDescription"><g:message code="rssFeed.showDescription.label" default="Show Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'showDescription', 'errors')}">
                                    <g:checkBox name="showDescription" value="${rssFeedInstance?.showDescription}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="rssFeed.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${rssFeedInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="showDate"><g:message code="rssFeed.showDate.label" default="Show Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'showDate', 'errors')}">
                                    <g:checkBox name="showDate" value="${rssFeedInstance?.showDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="rssFeed.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${rssFeedInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxNumItems"><g:message code="rssFeed.maxNumItems.label" default="Max Num Items" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'maxNumItems', 'errors')}">
                                    <g:textField name="maxNumItems" value="${fieldValue(bean: rssFeedInstance, field: 'maxNumItems')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="showAuthor"><g:message code="rssFeed.showAuthor.label" default="Show Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'showAuthor', 'errors')}">
                                    <g:checkBox name="showAuthor" value="${rssFeedInstance?.showAuthor}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="rssFeed.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${rssFeedInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="active"><g:message code="rssFeed.active.label" default="Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'active', 'errors')}">
                                    <g:checkBox name="active" value="${rssFeedInstance?.active}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="rssFeed.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${rssFeedInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="showInNewWindow"><g:message code="rssFeed.showInNewWindow.label" default="Show In New Window" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'showInNewWindow', 'errors')}">
                                    <g:checkBox name="showInNewWindow" value="${rssFeedInstance?.showInNewWindow}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="showTitle"><g:message code="rssFeed.showTitle.label" default="Show Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'showTitle', 'errors')}">
                                    <g:checkBox name="showTitle" value="${rssFeedInstance?.showTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="maxDescriptionLength"><g:message code="rssFeed.maxDescriptionLength.label" default="Max Description Length" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: rssFeedInstance, field: 'maxDescriptionLength', 'errors')}">
                                    <g:textField name="maxDescriptionLength" value="${fieldValue(bean: rssFeedInstance, field: 'maxDescriptionLength')}" />
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
