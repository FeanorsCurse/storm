
<%@ page import="systemadministration.usermanagement.User" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
  <title><g:message code="link.text.login" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="link.text.login" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="authenticate" method="post" >
<div class="logindialog">
<table>
<tbody>
<tr class="prop">
<td valign="top" class="name">
<label for="login">Username:</label>
</td>
<td valign="top">
<input type="text" id="username" name="username" />
</td>
</tr>
<tr class="prop">
<td valign="top" class="name">
<label for="password">Password:</label>
</td>
<td valign="top">
<input type="password" id="password" name="password" />
</td>
</tr>
</tbody>
</table>
</div>
<div class="buttons">
<span class="button">
<input type="submit" value="Login" />
</span>
</div>
</g:form>
</div>
</body>
</html>