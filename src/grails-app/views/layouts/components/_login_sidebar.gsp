<!--
  This component lists the Login area in the Sidebar (Important for uniol-Template)
  
  @author: Gerrit
-->
<ul>
	
	<form action="/Storm/user/authenticate" name="login_header" id="login_header" method="post">
	<li>
        <p><label for="username_login">Username: </label></p>
    </li>
	<li>
        <p><input id="username_login" name="username" type="text" /></p>
    </li>
    <li>
    	<p><label for="password_login">Passwort: </label></p>
    </li>
    <li>
    	<p><input id="password_login" name="password" type="password" /></p>
    </li>
    <li>
        <p><input type="submit" value="Login" /></p>
    </li>    
    </form>
    
	<li>
    	<a class="passwort" href="${createLink(uri: '/user/forgotPassword', absolute: false)}"><g:message code="link.label.password" default="Passwort vergessen?" /></a>
    </li>
    <li>
		<a class="Registrieren" href="${createLink(uri: '/user/createUser', absolute: false)}"> <g:message code="link.label.register" default="Registrieren" /> </a>	
    </li>
</ul>