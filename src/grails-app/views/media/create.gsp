
<%@ page import="ReportEditorMain.MediaCenter.Media" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="admin" />
  <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${flash.info}">
      <div class="info">${flash.info}</div>
    </g:if>

    <g:hasErrors bean="${mediaInstance}">
      <div class="errors">
        <g:renderErrors bean="${mediaInstance}" as="list" />
      </div>
    </g:hasErrors>

    <p><g:message code="media.upload.file" default="Please choose one: upload a local media file ..." /></p>

  <g:uploadForm action="save" method="post" >
    <div class="mediaTable create mediaUpload">
      <table class="list">
        <tbody>
          <tr class="odd">
            <td valign="top" class="name">
              <label><g:message code="media.step1.label" default="File" /></label>
            </td>
            <td valign="top" class="value">
              <input type="file" id="uploadfile" name="uploadfile" />
            </td>
          </tr>

          <tr>
            <td valign="top" class="name">
              <label><g:message code="media.name.label" default="Name" /></label>
            </td>
            <td valign="top" class="value">
        <input type="text" name="name" value="${mediaInstance?.name}"/>
        </td>
        </tr>

        <tr class="odd">
          <td valign="top" class="name">
            <label><g:message code="media.description.label" default="Description" /></label>
          </td>
          <td valign="top" class="value">
        <input type="text" name="description" value="${mediaInstance?.description}" />
        </td>
        </tr>

        <tr>
          <td valign="top" class="name">
            <label><g:message code="media.alt.label" default="ALT" /></label>
          </td>
          <td valign="top" class="value">
        <input type="text" name="alt" value="${mediaInstance?.alt}" />
        </td>
        </tr>

        </tbody>
      </table>
    </div>

    <div class="buttons">
      <span class="button"><input type="submit" name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
    </div>
  </g:uploadForm>


  <p><g:message code="media.get.file" default="... or get a file from an online source." /></p>

<g:uploadForm action="download" method="post" >
  <div class="mediaTable create mediaGet">
    <table  class="list">
      <tbody>
        <tr class="odd">
          <td valign="top" class="name">
            <label><g:message code="media.step1a.label" default="Source" /></label>
          </td>
          <td valign="top" class="value"><g:textField id="externalUrl" name="externalUrl" value="${mediaInstance?.externalUrl}" /></td>
      </tr>

      <tr>
        <td valign="top" class="name">
          <label><g:message code="media.name.label" default="Name" /></label>
        </td>
        <td valign="top" class="value">
      <input type="text" name="name" value="${mediaInstance?.name}"/>
      </td>
      </tr>

      <tr class="odd">
        <td valign="top" class="name">
          <label><g:message code="media.description.label" default="Description" /></label>
        </td>
        <td valign="top" class="value">
      <input type="text" name="description" value="${mediaInstance?.description}" />
      </td>
      </tr>

      <tr>
        <td valign="top" class="name">
          <label><g:message code="media.alt.label" default="ALT" /></label>
        </td>
        <td valign="top" class="value">
      <g:textField name="alt" value="${mediaInstance?.alt}" />
      </td>
      </tr>

      </tbody>
    </table>
  </div>
  <div class="buttons">
    <span class="button"><input type="submit" name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
  </div>
</g:uploadForm>


</div><%--class="body"--%>
</body>
</html>
