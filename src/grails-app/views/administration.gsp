<!--
  This template includes header, menu and footer to be included from other backend views.
-->
<html>
<head>
  <meta name="layout" content="backend" />
  <title>STORM - Administration</title>
</head>
<body>
  <h1><g:message code="link.text.backend" default="Backend" /></h1>
<div>
  <p><g:message code="messages.backend.greeting" /></p>
</div>
    <ul>
      <li class="menuheader">Links fuer den User</li>
      <li><g:link controller="user" action="createUser"><g:message code="Registrieren" args="[entityName]"/></g:link></li>
      <li><g:link controller="user" action="editUser" id="1"><g:message code="Profil bearbeiten"/></g:link></li>
    </ul>
</body>
</html>