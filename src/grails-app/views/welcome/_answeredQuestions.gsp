<%@ page import="systemadministration.modulmanagement.Question" %>
<%
def answeredQuestionInstanceList = Question.findAllByAnswerIsNotNullOrAnswerNotLike("",[max:5, sort:"lastUpdated", order:"desc"]);
%>

<g:each in="${answeredQuestionInstanceList}" status="i" var="questionInstance">
	<g:link controller="question" action="show2" id="${questionInstance.id}">
                  	 	${fieldValue(bean: questionInstance, field: "title")}
    </g:link>
	<br>
</g:each>
<p>
<g:link controller="question" action="display">
   ${message(code: 'welcome.showmore')}
</g:link>
</p>
