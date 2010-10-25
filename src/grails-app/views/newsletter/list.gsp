
<%@ page import="systemadministration.modulmanagement.Newsletter" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'newsletter.label', default: 'Newsletter')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table class="list">
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'newsletter.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="lastUpdated" title="${message(code: 'newsletter.lastUpdated.label', default: 'Last Updated')}" />
                        
                            <g:sortableColumn property="provider" title="${message(code: 'newsletter.provider.label', default: 'Provider')}" />
                        
                            <g:sortableColumn property="firstname" title="${message(code: 'newsletter.firstname.label', default: 'Firstname')}" />
                        
                            <th><g:message code="newsletter.author.label" default="Author" /></th>
                   	    
                            <g:sortableColumn property="email" title="${message(code: 'newsletter.email.label', default: 'Email')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${newsletterInstanceList}" status="i" var="newsletterInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${newsletterInstance.id}">${fieldValue(bean: newsletterInstance, field: "id")}</g:link></td>
                        
                            <td><g:formatDate date="${newsletterInstance.lastUpdated}" /></td>
                        
                            <td>${fieldValue(bean: newsletterInstance, field: "provider")}</td>
                        
                            <td>${fieldValue(bean: newsletterInstance, field: "firstname")}</td>
                        
                            <td>${fieldValue(bean: newsletterInstance, field: "author")}</td>
                        
                            <td>${fieldValue(bean: newsletterInstance, field: "email")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${newsletterInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
