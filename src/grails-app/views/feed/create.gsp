
<%@ page import="rss.Feed" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'feed.label', default: 'Feed')}" />
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
            <g:hasErrors bean="${feedInstance}">
            <div class="errors">
                <g:renderErrors bean="${feedInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
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
                                    <label for="link"><g:message code="feed.content.label" default="content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: feedInstance, field: 'content', 'errors')}">
                                    <g:textArea name="content" value="${feedInstance?.content}" rows="4" cols="40"/>
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
