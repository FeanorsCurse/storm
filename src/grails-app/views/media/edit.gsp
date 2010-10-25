
<%@ page import="ReportEditorMain.MediaCenter.Media" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="admin" />
  <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
  <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>

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

    <g:form method="post" >
      <g:hiddenField name="id" value="${mediaInstance?.id}" />
      <g:hiddenField name="version" value="${mediaInstance?.version}" />

      <div class="mediaTable editMedia mediaUpload">
        <table class="list">
          <tbody>

            <tr>
              <td valign="top" class="name">
                <label><g:message code="media.url.label" default="File" /></label>
              </td>
              <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: 'url')}</td>
            </tr>

            <tr class="odd">
              <td valign="top" class="name">
                <label><g:message code="media.name.label" default="Name" /></label>
              </td>
              <td valign="top" class="value"><g:textField name="name" value="${mediaInstance?.name}" /></td>
          </tr>

          <tr>
            <td valign="top" class="name">
              <label><g:message code="media.description.label" default="Description" /></label>
            </td>
            <td valign="top" class="value"><g:textField name="description" value="${mediaInstance?.description}" /></td>
          </tr>

          <tr class="odd">
            <td valign="top" class="name">
              <label><g:message code="media.alt.label" default="ALT" /></label>
            </td>
            <td valign="top" class="value"><g:textField name="alt" value="${mediaInstance?.alt}" /></td>
          </tr>

          <tr>
            <td valign="top" class="name">
              <label><g:message code="media.date.label" default="Date" /></label>
            </td>
            <td valign="top" class="value">
          <g:datePicker name="date" precision="day" value="${mediaInstance?.date}"  />
          </td>
          </tr>

          <tr class="odd">
            <td valign="top" class="name">
              <label><g:message code="media.fileType.label" default="File Type" /></label>
            </td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: 'fileType')}</td>
          </tr>

          <tr >
            <td valign="top" class="name">
              <label><g:message code="media.mediaType.label" default="File Type" /></label>
            </td>
            <td valign="top" class="value"><g:select name="mediaType" from="${['image', 'sound', 'video', 'other']}" valueMessagePrefix="media.type" value="${mediaInstance?.mediaType}" /></td>
          </tr>

          <tr class="odd">
            <td valign="top" class="name">
              <label><g:message code="media.owner.label" default="Owner" /></label>
            </td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: 'owner.username')}</td>
          </tr>

          <tr >
            <td valign="top" class="name">
              <label><g:message code="media.isPublic.label" default="public" /></label>
            </td>
            <td valign="top" class="value"><g:checkBox name="isPublicMedia" value="${mediaInstance?.isPublicMedia}" /></td>
          </tr>

          </tbody>
        </table>
      </div>

      <div class="mediaTable editMedia mediaGet">
        <table>
          <tbody>
            <tr>
              <td valign="top" class="mediaEven leftEdge leftTable">
                <label><g:message code="media.externalUrl.label" default="External Url" /></label>
              </td>
              <td valign="top" class="rightTable">
          <g:textField name="externalUrl" value="${mediaInstance?.externalUrl}" />
          </td>
          </tr>

          </tbody>
        </table>
      </div>

      <g:displayFile media="${mediaInstance}" />

      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
