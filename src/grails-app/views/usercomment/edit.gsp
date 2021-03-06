
<%@ page import="interactiveFeatures.Usercomments.Usercomment" %>
<%@ page defaultCodec="none" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'usercomment.label', default: 'User Comment')}" />
  <g:set var="entityID" value="${usercommentInstance.id}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
  <script type="text/javascript" src="/Storm/js/flvplayer/flvplayer-1.9.7b/flvplayer/swfobject.js"></script>
  <script type="text/javascript" src="${resource(dir:'js/',file:'embed.js')}" ></script>
  <resource:richTextEditor type="simple"/>
  <g:javascript library="yui" />
</head>
<body>
  <div class="nav" style="float: right; font-size: small">
  </div>
  <div class="body">
    <g:if test="${flash.message}">
      <div class="message">
        <img src="${resource(dir:'images/oxygen',file:'dialog-information.png')}" title="Info Message" alt="Info Message"/>
        &nbsp;
        ${flash.message}
      </div>
    </g:if>
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
    <g:include controller="usercomment" action="displaysingle" id="${usercommentInstance?.id}"/>
    <h2><g:message code="usercomment.edithere.label" default="Edit your comment here" /></h2>
      <g:if test="session.user != 'Anonym'">
        <table style="width: 100%; font-size: small">
          <g:form controller="usercomment">
            <g:hiddenField name="id" value="${usercommentInstance?.id}" />
            <g:hiddenField name="version" value="${usercommentInstance?.version}" />
            <g:hiddenField name="article" value="${usercommentInstance?.article?.id}" />
            <g:hiddenField name="reference" value="${usercommentInstance?.reference?.id}" />
          <tr>
            <td><g:textField name="title" value="${usercommentInstance?.title}" style="width: 300px; border: 1px"/></td>
          </tr>
          <tr>
            <td><richui:richTextEditor name="commentcontent" value="${usercommentInstance?.content}" style="width: 300px; height: 100px"/></td>
          </tr>
          <tr>
            <td>
              <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'usercomment.button.updatecomment.label', default: 'Update Comment')}" /></span>
            </td>
          </tr>
          </g:form>
        </table>
      </g:if>
  </div>
</body>
</html>

