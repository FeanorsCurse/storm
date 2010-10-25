<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="backend" />
  <title><g:message code="link.text.profile" default="My Data" /></title>
</head>
<body>
  <h1><g:message code="link.text.profile" default="My Data" /></h1>
<%--<g:render template="/layouts/grailsDefaultTemplate" />--%>
    <ul>
      <li class="menuheader">Links für den User</li>
      <li><g:link controller="user" action="createUser"><g:message code="Registrieren" args="[entityName]"/></g:link></li>
      <li><g:link controller="user" action="editUser" id="${session.user.id}"><g:message code="Profil bearbeiten"/></g:link></li>
    </ul>
</body>
</html>
