
<%@ page import="ReportEditorMain.MediaCenter.Media" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="admin" />
  <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div class="body">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${flash.info}">
      <div class="info">${flash.info}</div>
    </g:if>

    <g:if test="${fieldValue(bean: mediaInstance, field: 'url')}">
      <div class="mediaTable mediaUpload show">
        <table class="list">
          <tbody>
          <g:if test="${fieldValue(bean: mediaInstance, field: 'url')}">
            <tr>
              <td valign="top" class="name"><g:message code="media.url.label" default="File" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "url")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'name')}">
            <tr class="odd">
              <td valign="top" class="name"><g:message code="media.name.label" default="Name" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "name")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'description')}">
            <tr>
              <td valign="top" class="name"><g:message code="media.description.label" default="Description" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "description")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'alt')}">
            <tr  class="odd">
              <td valign="top" class="name"><g:message code="media.alt.label" default="ALT" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "alt")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'date')}">
            <tr>
              <td valign="top" class="name"><g:message code="media.date.label" default="Date" /></td>
            <td valign="top" class="value"><g:niceDate date="${mediaInstance?.date}" /></td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'fileType')}">
            <tr  class="odd">
              <td valign="top" class="name"><g:message code="media.fileType.label" default="File Type" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "fileType")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'mediaType')}">
            <tr >
              <td valign="top" class="name"><g:message code="media.mediaType.label" default="File Type" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "mediaType")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'owner')}">
            <tr class="odd">
              <td valign="top" class="name"><g:message code="media.owner.label" default="Owner" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "owner.username")}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'isPublicMedia')}">
            <tr >
              <td valign="top" class="name"><g:message code="media.isPublicMedia.label" default="public" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: 'isPublicMedia')}</td>
            </tr>
          </g:if>

          <g:if test="${fieldValue(bean: mediaInstance, field: 'articles')}">
            <tr class="odd">
              <td valign="top" class="mediaEven leftEdge leftTable"><g:message code="media.article.label" default="used in articles" /></td>
            <td valign="top" class="mediaEven rightEdge rightTable">
			<g:if test="${mediaInstance.articles}">
				<ul>
					<g:each in="${mediaInstance.articles}">
					  <li><a href="${createLink(controller:'article', action:'display', id:it.id)}">${it.name}, ID: ${it.id}, ${it.status}</a></li>
					</g:each>
				</ul>
			</g:if>
			</td>
            </tr>
          </g:if>

          </tbody>
        </table>
      </div>
    </g:if>

    <g:if test="${fieldValue(bean: mediaInstance, field: 'externalUrl')}">
      <div class="mediaTable mediaGet show">
        <table class="list">
          <tbody>
          <g:if test="${fieldValue(bean: mediaInstance, field: 'externalUrl')}">
            <tr>
              <td valign="top" class="name"><g:message code="media.externalUrl.label" default="External Url" /></td>
            <td valign="top" class="value">${fieldValue(bean: mediaInstance, field: "externalUrl")}</td>
            </tr>
          </g:if>

          </tbody>
        </table>
      </div>
    </g:if>

    <g:displayFile media="${mediaInstance}" />

    <div class="buttons">
      <g:form>
        <g:hiddenField name="id" value="${mediaInstance?.id}" />
        <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
        <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
      </g:form>
    </div>
  </div>
</body>
</html>
