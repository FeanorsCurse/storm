<%@ page defaultCodec="none" %>
<%@ page import="systemadministration.usermanagement.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    	
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.id.label" default="Id" /></td>

                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "id")}</td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.gravatar.label" default="Gravatar" /></td>

                            <td valign="top" class="value"><avatar:gravatar email="${userInstance?.email}" size="80"/></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.username.label" default="Username" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "username")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.email.label" default="Email" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "email")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.lastname.label" default="Lastname" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "lastname")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.firstname.label" default="Firstname" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "firstname")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.academicTitle.label" default="Academic Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "academicTitle")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.sex.label" default="Sex" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "sex")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.birthday.label" default="Birthday" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${userInstance?.birthday}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.address.label" default="Address" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "address")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.postcode.label" default="Postcode" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "postcode")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.city.label" default="City" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "city")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.country.label" default="Country" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: userInstance, field: "country")}</td>
                            
                        </tr>
                    
						<tr class="prop">
                            <td valign="top" class="name"><g:message code="user.interestGroup.label" default="Interest Group" /></td>
                            
                            <td valign="top" class="value"><g:link controller="interestGroup" action="show" id="${userInstance?.interestGroup?.id}">${userInstance?.interestGroup?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
						
						<tr class="prop">
                            <td valign="top" class="name"><g:message code="user.isDeleted.label" default="Is Deleted" /></td>
                            
                            <td valign="top" class="value"><g:formatBoolean boolean="${userInstance?.isDeleted}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.dateCreated.label" default="Date Created" /></td>

                            <td valign="top" class="value"><g:formatDate date="${userInstance?.dateCreated}" /></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.lastUpdated.label" default="Last Updated" /></td>

                            <td valign="top" class="value"><g:formatDate date="${userInstance?.lastUpdated}" /></td>

                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.reports.label" default="Reports" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.reports}" var="r">
                                    <li><g:link controller="report" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>  
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.articles.label" default="Articles" /></td>

                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.articles}" var="a">
                                    <li><g:link controller="article" action="display" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                        </tr>

                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.roles.label" default="Roles" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.roles}" var="r">
                                    <li><g:link controller="role" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.friends.label" default="Friends" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.friends}" var="f">
                                    <li><g:link controller="user" action="show" id="${f.id}">${f?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
							
							                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.usercomments.label" default="Usercomments" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.usercomments}" var="u">
                                    <li><g:link controller="usercomment" action="show" id="${u.id}">${u?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
						
						                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.reports.label" default="Reports" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.reports}" var="r">
                                    <li><g:link controller="report" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
						
						                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.articles.label" default="Articles" /></td>
                            
                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.articles}" var="a">
                                    <li><g:link controller="article" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
						
						
                            
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="user.infocarts.label" default="Infocarts" /></td>

                            <td valign="top" style="text-align: left;" class="value">
                                <ul>
                                <g:each in="${userInstance.infocarts}" var="icart">
                                    <li><g:link controller="infocart" action="show" id="${icart.id}">${icart?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>

                        </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${userInstance?.id}" />
                   
                    <span class="button"><g:actionSubmit class="editPwd" action="editPwd" value="${message(code:'user.changePwd')}" /></span>
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
        
        <g:displayUsername user="${userInstance}"/>
        
    </body>
</html>
