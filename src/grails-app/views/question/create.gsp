
<%@ page import="systemadministration.modulmanagement.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
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
            <g:hasErrors bean="${questionInstance}">
            <div class="errors">
                <g:renderErrors bean="${questionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="question"><g:message code="question.question.label" default="Question" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:textArea name="question" cols="40" rows="5" value="${questionInstance?.question}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author"><g:message code="question.author.label" default="Author" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'author', 'errors')}">
                                    <g:select name="author.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${questionInstance?.author?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="question.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${questionInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="editor"><g:message code="question.editor.label" default="Editor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'editor', 'errors')}">
                                    <g:select name="editor.id" from="${systemadministration.usermanagement.User.list()}" optionKey="id" value="${questionInstance?.editor?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="answer"><g:message code="question.answer.label" default="Answer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'answer', 'errors')}">
                                    <g:textField name="answer" value="${questionInstance?.answer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="question.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${questionInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="question.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${questionInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="active"><g:message code="question.active.label" default="Active" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'active', 'errors')}">
                                    <g:checkBox name="active" value="${questionInstance?.active}" />
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
