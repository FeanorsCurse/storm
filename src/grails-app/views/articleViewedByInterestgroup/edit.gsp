
<%@ page import="systemadministration.recommender.ArticleViewedByInterestgroup" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'articleViewedByInterestgroup.label', default: 'ArticleViewedByInterestgroup')}" />
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
            <g:hasErrors bean="${articleViewedByInterestgroupInstance}">
            <div class="errors">
                <g:renderErrors bean="${articleViewedByInterestgroupInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${articleViewedByInterestgroupInstance?.id}" />
                <g:hiddenField name="version" value="${articleViewedByInterestgroupInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="article"><g:message code="articleViewedByInterestgroup.article.label" default="Article" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByInterestgroupInstance, field: 'article', 'errors')}">
                                    <g:select name="article.id" from="${ReportEditorMain.ArticleEditor.Article.list()}" optionKey="id" value="${articleViewedByInterestgroupInstance?.article?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="group"><g:message code="articleViewedByInterestgroup.group.label" default="Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByInterestgroupInstance, field: 'group', 'errors')}">
                                    <g:select name="group.id" from="${systemadministration.usermanagement.InterestGroup.list()}" optionKey="id" value="${articleViewedByInterestgroupInstance?.group?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="counter"><g:message code="articleViewedByInterestgroup.counter.label" default="Counter" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: articleViewedByInterestgroupInstance, field: 'counter', 'errors')}">
                                    <g:textField name="counter" value="${fieldValue(bean: articleViewedByInterestgroupInstance, field: 'counter')}" />
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
