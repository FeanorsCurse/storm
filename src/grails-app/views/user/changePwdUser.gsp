
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
    <h1><g:message code="link.text.changePwd" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="changePwd" method="post" >
<div class="dialog">
<table>
<tbody>

<tr class="prop">
<td valign="top" class="name">
<label for="password">Old Password:</label>
</td>
<td valign="top">
<input type="oldpassword"
id="oldpassword" name="oldpassword"/>
</td>
</tr>

<tr class="prop">
<td valign="top" class="name">
<label for="password"><g:message code="user.oldPwd" /></label>
</td>
<td valign="top">
<input type="newpassword1"
id="newpassword1" name="newpassword1"/>
</td>
</tr>

<tr class="prop">
<td valign="top" class="name">
<label for="password">Repeate New Password:</label>
</td>
<td valign="top">
<input type="newpassword2"
id="newpassword2" name="newpassword2"/>
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