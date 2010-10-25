<%--
  This component lists the Helparticle
  @author: Rachid
--%>

<%@ page import="interactiveFeatures.Help.HelpArticle" %>
<%@ page defaultCodec="none" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${helpArticle?.section?.encodeAsHTML()}: ${helpArticle?.title?.encodeAsHTML()} - <g:message code="help.label" default="Help"/></title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>${helpArticle?.section?.encodeAsHTML()}</h1>
            <h2>${helpArticle?.title?.encodeAsHTML()}</h2>
            <div class="helpcontent">
              ${helpArticle?.helpcontent}
            </div>
        </div>
    </body>
</html>
