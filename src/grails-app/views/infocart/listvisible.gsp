
<%@ page import="interactiveFeatures.Infocart.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'infocart.label', default: 'Infocart')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="infocart.visibleinfocarts.label" default="Visible Infocarts of other users"/></h1>
            <g:if test="${flash.message}">
            <div class="message">
              <img src="${resource(dir:'images/oxygen',file:'dialog-information.png')}" title="Info Message" alt="Info Message"/>
              &nbsp;
              ${flash.message}
            </div>
            </g:if>
            <div class="list">
                <table width="100%">
                        <tr>
                            <th align="left" width="100%"><g:message code="infocart.name.label" default="Name"/></th>
                            <th align="left"><g:message code="infocart.user.label" default="User"/></th>
<%--
                            <th align="left"><g:message code="infocart.created.label" default="Created"/></th>
--%>
                            <th><g:message code="infocart.items.label" default="Items"/></th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    <g:each in="${infocartInstanceList}" status="i" var="infocartInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="display" id="${infocartInstance?.id}">${infocartInstance?.name}</g:link></td>
                            <td><g:displayUsername user="${infocartInstance?.user}"/></td>
<%--
                            <td><g:link controller="user" action="show" id="${infocartInstance?.user?.id}">${infocartInstance?.user.username}</g:link></td>
                            <td><g:formatDate date="${infocartInstance?.dateCreated}" type="datetime" style="SHORT" timeStyle="SHORT"/></td>
--%>
                            <td align="right">${infocartInstance?.items?.size()}</td>
                            <td><g:link action="showAsOnePage" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-preview.png')}" title="${message(code: 'menu.infocart.show.onepage')}" alt="${message(code: 'menu.infocart.show.onepage')}"/></g:link></td>
                            <td><g:link action="toPdf" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'application-pdf.png')}" title="${message(code: 'menu.infocart.show.pdf')}" alt="${message(code: 'menu.infocart.show.pdf')}"/></g:link></td>
                            <td><g:link action="xml" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'text-xml.png')}" title="${message(code: 'menu.infocart.show.xml')}" alt="${message(code: 'menu.infocart.show.xml')}"/></g:link></td>
                        </tr>
                    </g:each>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${infocartInstanceTotal}" />
            </div>
        </div>
    </body>
</html>