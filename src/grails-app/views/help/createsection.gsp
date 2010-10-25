
<%@ page import="interactiveFeatures.Help.HelpSection" %>
<html>
    <head>
        <resource:richTextEditor type="full" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <title><g:message code="help.createsection.label" default="Create Help Section" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.createsection.label" default="Create Help Section" /></h1>
            <g:hasErrors bean="${helpSectionInstance}">
            <div class="errors">
                <g:renderErrors bean="${helpSectionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="default.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpSectionInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${helpSectionInstance?.name}" style="width:100%;" />
                                </td>
                            </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="help.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: helpSectionInstance, field: 'name', 'errors')}">
                                    <richui:richTextEditor name="description" value="${helpSectionInstance?.description}" height="500"/>
                                </td>
                            </tr>
                            <tr class="prop">
                              <td>&nbsp;</td>
                              <td>
                                <div class="buttons">
                                  <span class="button"><g:actionSubmit class="save" action="savesection" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                                  <span class="button"><g:actionSubmit class="cancel" action="listsections" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" /></span>
                                </div>
                              </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </g:form>
        </div>
    </body>
</html>