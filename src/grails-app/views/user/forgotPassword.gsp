
<%@ page import="systemadministration.usermanagement.User" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
  <title><g:message code="link.text.forgotPassword" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="link.text.forgotPassword" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="forgotPassword_SetandSendMail" method="post" >
<div class="dialog">
<table>
<tbody>

<tr class="prop">
<td valign="top" class="name">
<label for="email_label">Your E-Mail address:</label>
</td>
<td valign="top">
<input type="email"
id="EMail" name="email"/>
</td>
</tr>

<tr class="prop">
<td valign="top" class="name">
<label for="username_label">Your username</label>
</td>
<td valign="top">
<input type="username"
id="username" name="username"/>
</td>
</tr>


</tbody>
</table>
</div>
<div class="buttons">
<span class="button">
<input type="submit" value="Get new Password" />
</span>
</div>
</g:form>
</div>
</body>
</html>