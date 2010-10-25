
<%@ page import="systemadministration.modulmanagement.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="question.show2" default="Question Details" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table class="list">
                    <tbody>
                       <tr class="even">
                            <td valign="top" class="name"><g:message code="question.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "title")}</td>
                            
                        </tr>

                        <tr class="odd">
                            <td valign="top" class="name"><g:message code="question.question.label" default="Question" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "question")}</td>
                            
                        </tr>
                    
                        <tr class="even">
                            <td valign="top" class="name"><g:message code="question.author.label" default="Author" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${questionInstance?.author?.id}">${questionInstance?.author?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    <g:if test="${questionInstance.answer&&questionInstance.answer!='' }">
                        <tr class="odd">
                            <td valign="top" class="name"><g:message code="question.answer.label" default="Answer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: questionInstance, field: "answer")}</td>
                            
                        </tr>
                        
                        <tr class="even">
                            <td valign="top" class="name"><g:message code="question.editor.label" default="Editor" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${questionInstance?.editor?.id}">${questionInstance?.editor?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    </g:if> 
                        
                        <tr class="odd">
                            <td valign="top" class="name"><g:message code="question.rating.label" default="Rating" /></td>
                            
                            <td valign="top" class="value">
                            
                            <rateable:resources/><rateable:ratings bean='${questionInstance}' />
                            
                            </td>
                            
                        </tr>
          
                    </tbody>
                </table>
            </div>
            <g:link action="display" controller="question">
                  ${message(code: 'question.link.back', default: 'Show Questions')}
            </g:link>     
        </div>
    </body>
</html>
