<%--
This template will show a thumbnail list of uploaded media files.
    author: Olaf Roeder
--%>
<%@ page import="ReportEditorMain.MediaCenter.Media"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="mediaTable">
	<table class="list">
		<thead>
		<tr><th><g:message code="article.edit.pickthumbnail"
                  default="To choose an image, click on the thumbnail or name or description." /></th></tr>
		</thead>
	</table>
</div>
<div class="mediaTable" >
  <table class="list">
	<thead>
      <tr>
        <th>${message(code: 'media.name.label', default: 'Name')} </th>
        <th>${message(code: 'media.description.label', default: 'Description')} </th>
        <th>${message(code: 'media.thumbnail.label', default: 'Thumbnail')}</th>
      </tr>
    </thead>

    <tbody>

    <g:each in="${mediaInstanceList}" status="i" var="mediaInstance">
      <tr class="${(i % 2) == 0 ? 'even' : 'odd'} name">

        <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} name">
          <a href="" onclick="javascript:FileBrowserDialogue.mySubmit('${mediaInstance.url}');;return false;">
${fieldValue(bean: mediaInstance, field: "name")}
          </a>
        </td>

        <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} desc">
          <a href="" onclick="javascript:FileBrowserDialogue.mySubmit('${mediaInstance.url}');;return false;">
${fieldValue(bean: mediaInstance, field: "description")}
          </a>
        </td>
		
		<g:if test="${mediaInstance.mediaType=='image'}">
        <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} thumb" onclick="FileBrowserDialogue.mySubmit('${mediaInstance.url}');">
      <g:displayThumbnail media="${mediaInstance}" /></td></g:if>
      </tr>
    </g:each>
    </tbody>
  </table>

</div>
