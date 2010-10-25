
<%@ page import="interactiveFeatures.Infocart.*" %>
<%@ page defaultCodec="none" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'infocart.label', default: 'Personal Report')}" />
        <title>${infocartInstance?.name?.encodeAsHTML()} - <g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1>${infocartInstance?.name?.encodeAsHTML()}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:each in="${infocartItemList}" status="i" var="item">
                <div id="articlename"><h2><g:link controller="article" action="display" id="${item?.article?.id}">${item?.article?.number} ${item?.article?.name}</g:link></h2></div>
                <div id="articlecontent">${item?.article?.content}</div>
            </g:each>
        </div>
    </body>
</html>