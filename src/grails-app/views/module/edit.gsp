
<%@ page import="systemadministration.modulmanagement.Module" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'module.label', default: 'Module')}" />
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
            <g:hasErrors bean="${moduleInstance}">
            <div class="errors">
                <g:renderErrors bean="${moduleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${moduleInstance?.id}" />
                <g:hiddenField name="version" value="${moduleInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="detachable"><g:message code="module.detachable.label" default="Detachable" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'detachable', 'errors')}">
                                    <g:select name="detachable" from="${moduleInstance.constraints.detachable.inList}" value="${fieldValue(bean: moduleInstance, field: 'detachable')}" valueMessagePrefix="module.detachable"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="configs"><g:message code="module.configs.label" default="Configs" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'configs', 'errors')}">
                                    <g:select name="configs" from="${systemadministration.modulmanagement.Config.list()}" multiple="yes" optionKey="id" size="5" value="${moduleInstance?.configs}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="module.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${moduleInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="module.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${moduleInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastUpdated"><g:message code="module.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${moduleInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateCreated"><g:message code="module.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${moduleInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rules"><g:message code="module.rules.label" default="Rules" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: moduleInstance, field: 'rules', 'errors')}">
                                    
<ul>
<g:each in="${moduleInstance?.rules?}" var="r">
    <li><g:link controller="rule" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="rule" action="create" params="['module.id': moduleInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'rule.label', default: 'Rule')])}</g:link>

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
