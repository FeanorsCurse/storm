
<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<html>
    <head>
        <resource:richTextEditor type="full" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <title><g:message code="help.editarticle.label" default="Edit Help Article" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.editarticle.label" default="Edit Help Article" /></h1>
            <g:hasErrors bean="${helpArticleInstance}">
            <div class="errors">
                <g:renderErrors bean="${helpArticleInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${helpArticleInstance?.id}" />
                <g:hiddenField name="version" value="${helpArticleInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                            	<td valign="top" class="name"><label for="title"><g:message code="helparticle.create.title" default="Title:" /></label></td>
                                <td valign="top" class="value ${hasErrors(bean: helpArticleInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${helpArticleInstance?.title}" style="width:100%;" />
                                    <br /><br />
                                </td>
                            </tr>
                            <tr class="prop">
                            	<td valign="top" class="name"><label for="section"><g:message code="helparticle.create.section" default="Section:" /></label></td>
                                <td valign="top" class="value ${hasErrors(bean: helpArticleInstance, field: 'section', 'errors')}">
                                    <g:select name="section.id" from="${interactiveFeatures.Help.HelpSection.list()}" optionKey="id" value="${helpArticleInstance?.section?.id}"  />
                                    <br /><br />
                                </td>
                            </tr>
                            <tr class="prop">
                            	<td valign="top" class="name"><label for="helpcontent"><g:message code="helparticle.create.helpcontent" default="Content:" /></label></td>
                                <td valign="top" class="value ${hasErrors(bean: helpArticleInstance, field: 'helpcontent', 'errors')}">
                                    <richui:richTextEditor name="helpcontent" value="${helpArticleInstance?.helpcontent}" height="500"/>
                                </td>
                            </tr>
                            <tr class="prop">
                              <td>&nbsp;</td>
                              <td>
                                <div class="buttons">
                                  <span class="button"><g:actionSubmit class="save" action="updatearticle" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                                  <span class="button"><g:actionSubmit class="delete" action="deletearticle" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
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
