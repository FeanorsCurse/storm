<!--
  This component lists the Login area
  
  @author: Frank
  @changed 28.08.2010: Gerrit
-->

<form action="/Storm/user/authenticate" name="login_header" id="login_header" method="post">
	<label for="username_login"><g:message code="header.username" default="Username:" /> </label>
    <input id="username_login" name="username" type="text" />
    &nbsp;&nbsp;
    <label for="password_login"><g:message code="header.password" default="Password:" /> </label>
    <input id="password_login" name="password" type="password" />
    <input type="submit" value="Login" />

	<div id="lost_register">
		<a class="password" href="${createLink(uri: '/user/forgotPassword', absolute: false)}"><g:message code="header.password.forget" default="Password forgotten?" /></a>
		<br />
		<a class="register" href="${createLink(uri: '/user/createUser', absolute: false)}"> <g:message code="header.register" default="Register" /></a>
	</div>
</form>