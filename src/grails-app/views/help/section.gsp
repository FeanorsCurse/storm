
<%@ page import="interactiveFeatures.Help.HelpSection" %>
<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<%@ page defaultCodec="none" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${helpSection?.name?.encodeAsHTML()} - <g:message code="help.label" default="Help"/></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
              <div class="message">${flash.message}</div>
            </g:if>
            <h1>${helpSection?.name?.encodeAsHTML()}</h1>
             <p>
               ${helpSection?.description}
             </p>
            <g:each in="${helpSection?.articles}" var="article">
             <h2><g:link action="article" id="${article?.id}">${article?.title}</g:link></h2>
            </g:each>
        </div>
    </body>
</html>
