<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="systemadministration.modulmanagement.Newsletter"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title><g:message code="newsletter" default="Newsletter" /></title>
</head>
<body>
<h1><g:message code="newsletter.title" default="Newsletter" /></h1>
<g:message code="newsletter.text" default="Fill out the Form below to get a Newsletter" />

<g:each in="${Newsletter.findAllByActive(true)}" var="newsletter">
	<h2>${newsletter.provider}</h2>
	<form action="${newsletter.action}" method="post" target="_blank"><input type="hidden" name="url" value="" />
	<table class="list">
		<tr class="even">
			<td><g:message code="newsletter.email" default="Email" /></td>
			<g:if test="${session.user.username=='Anonym'}">
				<td><input type="text" name="${newsletter.email}" value="" /></td>
			</g:if>
			<g:else>
				<td><input type="text" name="${newsletter.email}" value="${session.user.email}" /></td>
			</g:else>
		</tr>
		<tr class="odd">
			<td><g:message code="newsletter.firstname" default="Firstname" /></td>
			<g:if test="${session.user.username=='Anonym'}">
				<td><input type="text" name="${newsletter.firstname}" value="" /></td>
			</g:if>
			<g:else>
				<td><input type="text" name="${newsletter.firstname}" value="${session.user.firstname}" /></td>
			</g:else>
		</tr>
		<tr class="even">
			<td><g:message code="newsletter.lastname" default="Lastname" /></td>
			<g:if test="${session.user.username=='Anonym'}">
				<td><input type="text" name="${newsletter.name}" value="" /></td>
			</g:if>
			<g:else>
				<td><input type="text" name="${newsletter.name}" value="${session.user.lastname}" /></td>
			</g:else>
		</tr>
	</table>
	<input type="submit" value="${message(code: 'newsletter.inscribe', default: 'Inscribe')}" /></form>
	</br>
</g:each>
</body>
</html>