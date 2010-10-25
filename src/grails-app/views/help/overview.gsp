
<%@ page import="interactiveFeatures.Help.HelpSection" %>
<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<%@ page defaultCodec="none" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="default.help.label"/></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1><g:message code="help.overview.label" default="Help Overview"/></h1>
            <div class="list">
                    <g:each in="${helpSectionList}" status="i" var="section">
                      <h2><g:link action="section" id="${section?.id}">${section?.name}</g:link></h2>
                      <p>${section?.description}</p>
<%--
                      <ul>
                        <g:each in="${section?.articles}" var="article">
                          <li><g:link action="article" id="${article?.id}">${article?.title}</g:link></li>
                        </g:each>
                      </ul>
--%>
                    </g:each>
            </div>
        </div>
    </body>
</html>