
<%@ page import="systemadministration.modulmanagement.Question" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'question.label', default: 'Question')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <rateable:resources/>
    </head>
    <body>
        <div class="body">
        	<h1><g:message code="question.display.title" default="Question-System" /></h1>
               
            <p><g:message code="question.display.text" default="You can see new questions, top-rated questions and answered questions. If you are logged in, you can rate questions and create own questions." /></p>   
                
            <h2><g:message code="question.display.new" default="New Questions" /></h2>
            <p><g:message code="question.display.new.text" default="Here you see new Questions" /></p>  
            <table class="list">
            <g:each in="${newQuestionInstanceList}" status="i" var="questionInstance">
                   	<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                   	<td>
                   	<g:link action="show2" id="${questionInstance.id}">
                  	 	${fieldValue(bean: questionInstance, field: "title")}
                  	</g:link>
                  	</td>
                  	</tr>
            </g:each>   
            </table> 
            <g:link action="allNew">
                  ${message(code: 'question.display.show.new', default: 'Show all unanswered Questions...')}
            </g:link> 
            
            <h2><g:message code="question.display.top" default="Top rated Questions" /></h2>
            <p><g:message code="question.display.top.text" default="All top-rated Questions are listed here" /></p>   
             <table class="list">
            <g:each in="${topQuestionInstanceList}" status="i" var="questionInstance">
                   	<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                   	<td>
                   	<g:link action="show2" id="${questionInstance.id}">
                  	 	${fieldValue(bean: questionInstance, field: "title")}
                  	</g:link>
                   </td>
                  	</tr>   
            </g:each>  
            </table> 
            <g:link action="allTop">
                  ${message(code: 'question.display.show.top', default: 'Show all top rated Questions...')}
            </g:link>   

            <h2><g:message code="question.display.answered" default="Answered Questions" /></h2>
             <p><g:message code="question.display.answered.text" default="All answered Questions are listed here" /></p>   
            <table class="list">
            <g:each in="${answeredQuestionInstanceList}" status="i" var="questionInstance">
                   	<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                   	<td>
                   	<g:link action="show2" id="${questionInstance.id}">
                  	 	${fieldValue(bean: questionInstance, field: "title")}
                  	</g:link>
                  </td>
                  	</tr>    
            </g:each>
            </table> 
            <g:link action="allAnswered">
                  ${message(code: 'question.display.show.answered', default: 'Show all answered Questions...')}
            </g:link>    
            
            
            <g:if test="${session.user&&session.user.username!='Anonym'}">
            <h2><g:message code="question.display.create.question" default="Create Question" /></h2>
             <p><g:message code="question.display.create.question.text" default="Fill out the Form below to create your own question" /></p>   
            <g:form action="userSave" method="post" >
            <div class="dialog">
                    <table class="list">
                        <tbody>
                            <tr class="even">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="question.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${questionInstance?.title}" />
                                </td>
                            </tr>
                            <tr class="odd">
                                <td valign="top" class="name">
                                    <label for="question"><g:message code="question.question.label" default="Question" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: questionInstance, field: 'question', 'errors')}">
                                    <g:textArea style="width:100%" name="question" cols="40" rows="5" value="${questionInstance?.question}" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>  
            </g:if>
        </div>
    </body>
</html>
