
<%@ page import="systemadministration.modulmanagement.Newsletter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'newsletter.label', default: 'Newsletter')}" />
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
            <g:hasErrors bean="${newsletterInstance}">
            <div class="errors">
                <g:renderErrors bean="${newsletterInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${newsletterInstance?.id}" />
                <g:hiddenField name="version" value="${newsletterInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastUpdated"><g:message code="newsletter.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${newsletterInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="provider"><g:message code="newsletter.provider.label" default="Provider" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'provider', 'errors')}">
                                    <g:textField name="provider" value="${newsletterInstance?.provider}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="firstname"><g:message code="newsletter.firstname.label" default="Firstname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'firstname', 'errors')}">
                                    <g:textField name="firstname" value="${newsletterInstance?.firstname}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="author"><g:message code="newsletter.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${newsletterInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="email"><g:message code="newsletter.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${newsletterInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="newsletter.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${newsletterInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="newsletter.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${newsletterInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="action"><g:message code="newsletter.action.label" default="Action" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'action', 'errors')}">
                                    <g:textField name="action" value="${newsletterInstance?.action}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="active"><g:message code="newsletter.active.label" default="Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'active', 'errors')}">
                                    <g:checkBox name="active" value="${newsletterInstance?.active}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateCreated"><g:message code="newsletter.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: newsletterInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${newsletterInstance?.dateCreated}"  />
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
