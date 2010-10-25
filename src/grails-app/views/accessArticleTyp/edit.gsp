
<%@ page import="systemadministration.recommender.AccessArticleTyp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'accessArticleTyp.label', default: 'AccessArticleTyp')}" />
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
            <g:hasErrors bean="${accessArticleTypInstance}">
            <div class="errors">
                <g:renderErrors bean="${accessArticleTypInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${accessArticleTypInstance?.id}" />
                <g:hiddenField name="version" value="${accessArticleTypInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="typeAccess"><g:message code="accessArticleTyp.typeAccess.label" default="Type Access" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'typeAccess', 'errors')}">
                                    <g:textField name="typeAccess" value="${fieldValue(bean: accessArticleTypInstance, field: 'typeAccess')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="article"><g:message code="accessArticleTyp.article.label" default="Article" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'article', 'errors')}">
                                    <g:select name="article.id" from="${ReportEditorMain.ArticleEditor.Article.list()}" optionKey="id" value="${accessArticleTypInstance?.article?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countAll"><g:message code="accessArticleTyp.countAll.label" default="Count All" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countAll', 'errors')}">
                                    <g:textField name="countAll" value="${fieldValue(bean: accessArticleTypInstance, field: 'countAll')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countPara0"><g:message code="accessArticleTyp.countPara0.label" default="Count Para0" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countPara0', 'errors')}">
                                    <g:textField name="countPara0" value="${fieldValue(bean: accessArticleTypInstance, field: 'countPara0')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countPara1"><g:message code="accessArticleTyp.countPara1.label" default="Count Para1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countPara1', 'errors')}">
                                    <g:textField name="countPara1" value="${fieldValue(bean: accessArticleTypInstance, field: 'countPara1')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countPara2"><g:message code="accessArticleTyp.countPara2.label" default="Count Para2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countPara2', 'errors')}">
                                    <g:textField name="countPara2" value="${fieldValue(bean: accessArticleTypInstance, field: 'countPara2')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countPara3"><g:message code="accessArticleTyp.countPara3.label" default="Count Para3" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countPara3', 'errors')}">
                                    <g:textField name="countPara3" value="${fieldValue(bean: accessArticleTypInstance, field: 'countPara3')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="countPara4"><g:message code="accessArticleTyp.countPara4.label" default="Count Para4" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'countPara4', 'errors')}">
                                    <g:textField name="countPara4" value="${fieldValue(bean: accessArticleTypInstance, field: 'countPara4')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="dateCreated"><g:message code="accessArticleTyp.dateCreated.label" default="Date Created" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'dateCreated', 'errors')}">
                                    <g:datePicker name="dateCreated" precision="day" value="${accessArticleTypInstance?.dateCreated}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lastUpdated"><g:message code="accessArticleTyp.lastUpdated.label" default="Last Updated" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: accessArticleTypInstance, field: 'lastUpdated', 'errors')}">
                                    <g:datePicker name="lastUpdated" precision="day" value="${accessArticleTypInstance?.lastUpdated}" noSelection="['': '']" />
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
