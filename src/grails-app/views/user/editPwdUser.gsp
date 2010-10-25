
<%@ page import="systemadministration.usermanagement.User" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
  <title><g:message code="user.changePwd" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="link.text.changePwd" args="[entityName]" />:
    <g:if test="{userInstance.username}">  ${userInstance.username}</g:if>
    </h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    
<g:form action="changePwdUser" method="post" >
<g:hiddenField name="id" value="${userInstance?.id}" />
<g:hiddenField name="version" value="${userInstance?.version}" />

<div class="dialog">

<table>
<tbody>

	<tr class="prop">
		<td valign="top" class="name">
			<label for="password"><g:message code="user.oldPwd" default="Altes Pwd" /></label>
		</td>
		<td valign="top">
			<input type="password" id="oldpassword" name="oldpassword"/>
		</td>
	</tr>

	<tr class="prop">
		<td valign="top" class="name">
			<label for="password"><g:message code="user.newPwd" /></label>
		</td>
		<td valign="top">
			<input type="password" id="newpassword1" name="newpassword1"/>
		</td>
	</tr>
		
	<tr class="prop">
		<td valign="top" class="name">
			<label for="password"><g:message code="user.newPwd2" /></label>
		</td>
		<td valign="top">
			<input type="password"	id="newpassword2" name="newpassword2"/>
		</td>
	</tr>

</tbody>
</table>
</div>
<div class="buttons">
<span class="button">
<input type="submit" value="Go" />
</span>
</div>
</g:form>
</div>
</body>
</html>