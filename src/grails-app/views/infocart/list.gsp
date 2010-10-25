
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
            <h1><g:message code="infocart.list.label" default="Your Infocarts"/></h1>
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
                            <th align="left"><g:message code="infocart.name.label" default="Name"/></th>
<%--
                            <th align="left"><g:message code="infocart.created.label" default="Created"/></th>
--%>
                            <th><g:message code="infocart.visible.label" default="Visible"/></th>
                            <th><g:message code="infocart.items.label" default="Items"/></th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                            <th>&nbsp;</th>
                        </tr>
                    <g:each in="${infocartInstanceList}" status="i" var="infocartInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${infocartInstance?.id}">${infocartInstance?.name}</g:link></td>
                            <td align="center"><g:message code="infocart.bool.${infocartInstance?.visible}" /></td>
                            <td align="right">${infocartInstance?.items.size()}</td>
                            <td>&nbsp;</td>
                            <td><g:link action="load" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-open.png')}" title=" ${message(code: 'infocart.load.label', default: 'Load Infocart')}" alt="${message(code: 'infocart.load.label', default: 'Load Infocart')}"/></g:link></td>
                            <td><g:link action="edit" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-edit.png')}" title=" ${message(code: 'infocart.properties.label', default: 'Edit Infocart Properties')}" alt="${message(code: 'infocart.properties.label', default: 'Edit Infocart Properties')}"/></g:link></td>
                            <td><g:link action="delete" id="${infocartInstance?.id}"><img src="${resource(dir:'images/oxygen',file:'document-close.png')}" title="${message(code: 'infocart.deleted.label', default: 'Delete Infocart')}" alt="${message(code: 'infocart.deleted.label', default: 'Delete Infocart')}"/></g:link></td>
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