
<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<html>
    <head>
        <resource:richTextEditor type="full" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <title><g:message code="help.createarticle.label" default="Create Help Article" /></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.createarticle.label" default="Create Help Article" /></h1>
            <g:hasErrors bean="${helpArticleInstance}">
            	<div class="errors">
                	<g:renderErrors bean="${helpArticleInstance}" as="list" />
            	</div>
            </g:hasErrors>
            <g:form method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                            <tr class="prop">
                            	<td valign="top" class="name"><label for="title"><g:message code="helparticle.create.title" default="Title:" /></label></td>
                                <td valign="top" class="value ${hasErrors(bean: helpArticleInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${helpArticleInstance?.title}" style="width:100%;" />
                                </td>
                            </tr>
                            <tr class="prop">
                            	<td valign="top" class="name"><label for="section"><g:message code="helparticle.create.section" default="Section:" /></label></td>
                                <td valign="top" class="value ${hasErrors(bean: helpArticleInstance, field: 'section', 'errors')}">
                                    <g:select name="section.id" from="${interactiveFeatures.Help.HelpSection.list()}" optionKey="id" value="${helpArticleInstance?.section?.id}"  />
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
                                  <span class="button"><g:actionSubmit class="save" action="savearticle" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                                  <span class="button"><g:actionSubmit class="cancel" action="listarticles" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" /></span>
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
