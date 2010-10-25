
<%@ page import="systemadministration.usermanagement.User" %>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>

    <table>
    <td><h2>${message(code: 'user.myFollowers.Fellower', default: 'Follower')}</h2> </td>
    <td><h2>${message(code: 'user.myFollowers.Artikel', default: 'Artikel')}</h2>
</td>
<td><h2>${message(code: 'user.myFollowers.comment', default: 'comment')}</h2>
</td>
<tr></tr>
    <g:each in="${allFriendsContent}" status="ii" var="content">
<td> <h3> ${content[0].username}</h3></td>
   <td></td><td></td>
<tr></tr>
      <% [0,1,2].each { yy -> %>
            <tr>
    <td></td>
    <td>

    <g:if test="${content[1].id[yy]!=null}">
     <b><g:replaceHTML text="${content[1].name[yy].toString()}" /> </b> 
     <g:replaceHTML text="${content[1].content[yy].toString()}" lenght="50"/>
     ... <br>&nbsp;&nbsp;&nbsp;&nbsp; <FONT COLOR="#6b6b6b" size="2"> ${message(code: 'welcome.from', default: 'aus')} <g:displayUsername user="${content[1].author[yy]}"/>  <g:link controller="article" action="display" id="${content[1].id[yy]}"> ${message(code: 'welcome.showmore', default: 'weitere ...')} </g:link>  </FONT> 
     <hr></hr>
	</g:if>

    </td>
    <td>

    <g:if test="${content[2].id[yy]!=null}">
     ${content[2].title[yy]}
     
     <b><g:replaceHTML text="${content[2].title[yy].toString()}" /> </b> 
     <g:replaceHTML text="${content[2].content[yy].toString()}" lenght="50"/>
     ... <br>&nbsp;&nbsp;&nbsp;&nbsp; <FONT COLOR="#6b6b6b" size="2"> von: <g:displayUsername user="${content[2].commentator[yy]}"/> aus: <g:link controller="article" action="display" id="${content[2].article[yy].getId()}"> ${content[2].article[yy]} </g:link>  </FONT> 
     <hr></hr>
     
	</g:if>
    
    </td>
    </tr>
    
    
    <%}%>

    </g:each>
</table>


    </body>
</html>
