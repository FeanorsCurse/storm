
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usercomment.label', default: 'Usercomment')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton" style="font-size:1.2em;"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton" style="font-size:1.2em;"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1 style="color: #0070c0; font-size: 1.2em;"><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.title.label" default="Title" /></td>
                            <td valign="top" class="value"><b>${usercommentInstance.title}</b></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.content.label" default="Content" /></td>
                            <td valign="top" class="value">${usercommentInstance.content}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.id.label" default="Id" /></td>
                            <td valign="top" class="value">${usercommentInstance.id}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.date.label" default="Date Created" /></td>
                            <td valign="top" class="value"><g:formatDate date="${usercommentInstance?.dateCreated}" type="datetime" timeStyle="SHORT"/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.commentator.label" default="Commentator" /></td>
                            <td valign="top" class="value">
                              <g:link controller="user" action="show" id="${usercommentInstance?.commentator?.id}"><avatar:gravatar email="${usercommentInstance?.commentator?.email}" size="30" defaultGravatarUrl="identicon"/></g:link>
                              <g:link controller="user" action="show" id="${usercommentInstance?.commentator?.id}">${usercommentInstance?.commentator?.encodeAsHTML()}</g:link>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.thumbrating.label" default="Thumbrating" /></td>
                            <td valign="top" class="value">
                              <g:link controller="usercomment" action="thumbsupvote" id="${usercommentInstance?.id}"><img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs up.png')}" title="Finde ich gut" alt="Finde ich gut" style="margin-top:2px; margin-bottom:-2px;"/></g:link>&nbsp;(${thumbsupcount})
                              <g:link controller="usercomment" action="thumbsdownvote" id="${usercommentInstance?.id}"><img src="${resource(dir:'images/InteractiveFeatures',file:'Thumbs down.png')}" title="Finde ich nicht gut" alt="Finde ich nicht gut" style="margin-top:2px; margin-bottom:-8px;"/></g:link>&nbsp;(${thumbsdowncount})
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.article.label" default="Article" /></td>
                            <td valign="top" class="value"><g:link controller="article" action="display" id="${usercommentInstance?.article?.id}">${usercommentInstance?.article}</g:link></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.references.label" default="References" /></td>
                            <td valign="top" class="value"><g:link controller="usercomment" action="show" id="${usercommentInstance?.reference?.id}">${usercommentInstance?.reference?.title}</g:link></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="usercomment.referencedby.label" default="Referenced by" /></td>
                            <td valign="top" class="value">
                              <ul>
                                <g:each in="${replyList}" status="i" var="reply">
                                  <li>
                                    <g:link controller="usercomment" action="show" id="${reply?.id}">${reply?.title}</g:link>
                                    (<g:link controller="user" action="show" id="${usercommentInstance?.commentator?.id}">${usercommentInstance?.commentator?.encodeAsHTML()}</g:link>, <g:formatDate date="${reply?.dateCreated}" type="datetime" timeStyle="SHORT" />)
                                  </li>
                                </g:each>
                              </ul>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${usercommentInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'usercomment.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>