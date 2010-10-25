
<%@ page import="systemadministration.usermanagement.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
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
                                  <label for="isDeleted"><g:message code="user.isDeleted.label" default="Is Deleted" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'isDeleted', 'errors')}">
                                    <g:checkBox name="isDeleted" value="${userInstance?.isDeleted}" />
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
                            
                                                    
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="friends"><g:message code="user.friends.label" default="Friends" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'friends', 'errors')}">
                                    <g:select name="friends" from="${systemadministration.usermanagement.User.list()}" multiple="yes" optionKey="id" size="5" value="${userInstance?.friends}" />
                                </td>
                            </tr>
                            
                                                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="usercomments"><g:message code="user.usercomments.label" default="Usercomments" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'usercomments', 'errors')}">
                                    
<ul>
<g:each in="${userInstance?.usercomments?}" var="u">
    <li><g:link controller="usercomment" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="usercomment" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'usercomment.label', default: 'Usercomment')])}</g:link>

                                </td>
                            </tr>
                            
                                                        <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="infocarts"><g:message code="user.infocarts.label" default="Infocarts" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'infocarts', 'errors')}">
                                    
<ul>
<g:each in="${userInstance?.infocarts?}" var="i">
    <li><g:link controller="infocart" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="infocart" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'infocart.label', default: 'Infocart')])}</g:link>

                                </td>
                            </tr>
                            
                            
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="reports"><g:message code="user.reports.label" default="Reports" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'reports', 'errors')}">
                                    
<ul>
<g:each in="${userInstance?.reports?}" var="r">
    <li><g:link controller="report" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="report" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'report.label', default: 'Report')])}</g:link>

                                </td>
                            </tr>
                        

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="articles"><g:message code="user.articles.label" default="Articles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'articles', 'errors')}">
                                    
<ul>
<g:each in="${userInstance?.articles?}" var="a">
    <li><g:link controller="article" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="article" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'article.label', default: 'Article')])}</g:link>

                                </td>
                            </tr>


                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="roles"><g:message code="user.roles.label" default="Roles" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: userInstance, field: 'roles', 'errors')}">
                                    
                                </td>
                            </tr>
                        
                          
                        
                        </tbody>
                    </table>
                </div>
                
               
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
