
<%@ page import="systemadministration.usermanagement.Rule" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'rule.label', default: 'Rule')}" />
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
            <g:hasErrors bean="${ruleInstance}">
            <div class="errors">
                <g:renderErrors bean="${ruleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                   		<thead>
                    		<tr>
                    			<th>${message(code: 'rule.create.tabledata.row1.label', default: 'Detail')}</th>
                    			<th>${message(code: 'rule.create.tabledata.row2.label', default: 'Value')}</th>
                    		</tr>
                    	</thead>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="module"><g:message code="rule.module.label" default="Module" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'module', 'errors')}">
                                    <g:select name="module.id" from="${systemadministration.modulmanagement.Module.list()}" optionKey="id" value="${ruleInstance?.module?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="rule.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${ruleInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="rule.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${ruleInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated"><g:message code="rule.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${ruleInstance?.lastUpdated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated"><g:message code="rule.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${ruleInstance?.dateCreated}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="actionname"><g:message code="rule.actionname.label" default="Actionname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: ruleInstance, field: 'actionname', 'errors')}">
                                    <g:textField name="actionname" value="${ruleInstance?.actionname}" />
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
