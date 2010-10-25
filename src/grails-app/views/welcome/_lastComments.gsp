
<g:each var="comment" in="${lastComments}">
     <b><g:replaceHTML text="${comment.title.toString()}" /> </b> 
     <g:replaceHTML text="${comment.content.toString()}" lenght="50"/>
     ... <br>&nbsp;&nbsp;&nbsp;&nbsp; <FONT COLOR="#6b6b6b" size="2"> <g:message code="welcome.by" /> <g:displayUsername user="${comment.commentator}"/> <g:message code="welcome.from" /> <g:link controller="article" action="display" id="${comment.article.getId()}"> ${comment.article} </g:link>  </FONT> 
     <hr></hr>
     
     
</g:each>


