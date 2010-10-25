
<%@ page import="systemadministration.usermanagement.User" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
  <title>Registrieren</title>
</head>
<body>
  <div class="nav"></div>
        <div class="body">
            <h1>Registrieren</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="username"><g:message code="user.username.label" default="Username" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
                                    <g:textField name="username" maxlength="15" value="${userInstance?.username}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="password"><g:message code="user.password.label" default="Password" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
                                    <g:passwordField name="password" maxlength="15" value="${userInstance?.password}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email"><g:message code="user.email.label" default="Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'email', 'errors')}">
                                    <g:textField name="email" value="${userInstance?.email}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastname"><g:message code="user.lastname.label" default="Lastname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'lastname', 'errors')}">
                                    <g:textField name="lastname" value="${userInstance?.lastname}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="firstname"><g:message code="user.firstname.label" default="Firstname" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'firstname', 'errors')}">
                                    <g:textField name="firstname" value="${userInstance?.firstname}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="academicTitle"><g:message code="user.academicTitle.label" default="Academic Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'academicTitle', 'errors')}">
                                    <g:select name="academicTitle" from="${userInstance.constraints.academicTitle.inList}" value="${userInstance?.academicTitle}" valueMessagePrefix="user.academicTitle" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="sex"><g:message code="user.sex.label" default="Sex" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'sex', 'errors')}">
                                    <g:select name="sex" from="${userInstance.constraints.sex.inList}" value="${userInstance?.sex}" valueMessagePrefix="user.sex" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="birthday"><g:message code="user.birthday.label" default="Birthday" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'birthday', 'errors')}">
                                    <g:datePicker name="birthday" precision="day" value="${userInstance?.birthday}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="address"><g:message code="user.address.label" default="Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'address', 'errors')}">
                                    <g:textField name="address" value="${userInstance?.address}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="postcode"><g:message code="user.postcode.label" default="Postcode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'postcode', 'errors')}">
                                    <g:textField name="postcode" value="${userInstance?.postcode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="city"><g:message code="user.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'city', 'errors')}">
                                    <g:textField name="city" value="${userInstance?.city}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="country"><g:message code="user.country.label" default="Country" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'country', 'errors')}">
                                    <g:select name="country" from="${userInstance.constraints.country.inList}" value="${userInstance?.country}" valueMessagePrefix="user.country" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="interestGroup"><g:message code="user.interestGroup.label" default="Interest Group" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'interestGroup', 'errors')}">
                                    <g:select name="interestGroup.id" from="${systemadministration.usermanagement.InterestGroup.list()}" optionKey="id" value="${userInstance?.interestGroup?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                           
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="Registrieren" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
