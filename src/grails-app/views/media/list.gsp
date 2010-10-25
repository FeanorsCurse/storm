
<%@ page import="ReportEditorMain.MediaCenter.Media" %>
<%@ page import="ReportEditorMain.ReportEditor.Report" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="admin" />
  <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
  <script type="text/javascript" src="/Storm/js/checkAll.js"></script>
  <script type="text/javascript" src="/Storm/js/alterDisplay.js"></script>
  <script type="text/javascript" src="/Storm/js/alterColor.js"></script>
  <resource:autoComplete skin="default/autocomplete" />
</head>

<body>
  <div class="body">
    <div class="nav">
      <span class="menuButton">
        <g:link class="create" action="create">
          <g:message code="media.create" default="Upload Media" />
        </g:link>
      </span>
    </div>

    <h1><g:message code="default.list.label" args="[entityName]" /></h1>

    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${flash.info}">
      <div class="info">${flash.info}</div>
    </g:if>

    <div class="debug">
      <p>params.checkedBoxes: ${params.checkedBoxes}</p>
    </div>

    <div id="searchfilter">
      <table>
        <g:form url='[controller: "media", action: "searchFilter"]' id="searchableForm" name="searchableForm" method="get">
          <td>	<richui:autoComplete name="mediaSearch" maxResultsDisplayed="20" minQueryLength="3" value="${params.mediaSearch}"
                                    action="${createLinkTo('dir': 'media/searchAJAX')}" /></td>
          <td>	<input type="submit" value="${message(code: 'default.button.search.label', default: 'Search')}" /></td>
        </g:form>

        <form method="get" action="list">
          <td><select name="mediaType" size="1">
              <option value="all"><g:message code="media.list.filter.nofilter" default="no filter" /></option>
              <option value="img"><g:message code="media.list.filter.pictures" default="pictures" /></option>
              <option value="vid"><g:message code="media.list.filter.video" default="video" /></option>
              <option value="snd"><g:message code="media.list.filter.audio" default="audio" /></option>
              <option value="other"><g:message code="media.list.filter.other" default="other" /></option>
            </select></td>
          <td><input type="submit" value="${message(code: 'default.button.filter.label', default: 'Filter')}" /></td>
        </form>

        <g:form url='[controller: "media", action: "list"]' method="get">
          <td><input type="submit" value="${message(code: 'default.button.reset.label', default: 'Reset')}" /></td>
        </g:form>
      </table>
    </div>

    <div class="float_clear" ></div>

    <div class="mediaTable">
      <%--<form action="actionChecked" id="mediaListForm" method="get">--%>
        <table class="list">
          <thead>
            <tr>
          <g:sortableColumn property="id" title="${message(code: 'media.id.label', default: 'Aktion')}" />
          <g:sortableColumn property="name" title="${message(code: 'media.name.label', default: 'Name')}" />
          <g:sortableColumn property="description" title="${message(code: 'media.description.label', default: 'Description')}" />
          <th class="sortable">${message(code: 'media.thumbnail.label', default: 'Thumbnail')}</th>
          <g:sortableColumn property="date" title="${message(code: 'media.date.label', default: 'Date')}" />
          <g:sortableColumn property="owner" title="${message(code: 'media.owner.label', default: 'Owner')}" />
          <g:sortableColumn property="fileType" title="${message(code: 'media.fileType.label', default: 'File Type')}" />
          <th class="sortable">${message(code: 'media.addtoview.label', default: 'Add to View')}</th>
          </tr>
          </thead>

          <tbody>

          <g:each in="${mediaInstanceList}" status="i" var="mediaInstance">
		  <g:if test="${mediaInstance.isPublicMedia}">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} actions">
            <g:link action="show" id="${mediaInstance.id}"><img src="${resource(dir:'images/ReportEditor',file:'show.png')}" title="${message(code: 'media.show.label', default: 'show media')}" alt="${message(code: 'media.show.label', default: 'show media')}" /></g:link>
            <g:link action="edit" id="${mediaInstance.id}"><img src="${resource(dir:'images/ReportEditor',file:'edit.png')}" title="${message(code: 'media.edit.label', default: 'edit media')}" alt="${message(code: 'media.edit.label', default: 'edit media')}" /></g:link>
            <g:link action="delete" id="${mediaInstance.id}"><img src="${resource(dir:'images/ReportEditor',file:'delete.png')}" title="${message(code: 'media.delete.label', default: 'delete media')}" alt="${message(code: 'media.delete.label', default: 'delete media')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/></g:link>
            </td>

            <td class="${(i % 2) == 0 ? 'odd' : 'even'} name"><g:link action="show" id="${mediaInstance.id}">
${fieldValue(bean: mediaInstance, field: "name")}
            </g:link></td>

            <td class="${(i % 2) == 0 ? 'odd' : 'even'} desc"><g:link action="show" id="${mediaInstance.id}">
${fieldValue(bean: mediaInstance, field: "description")}
            </g:link></td>

            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} thumb"><g:displayThumbnailController media="${mediaInstance}" id="${mediaInstance.id}" /></td>

            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} date"><g:niceDate date="${mediaInstance.date}" /></td>

            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} owner"><g:link controller="user"
                                                                                 action="show" id="${mediaInstance.owner.id}">
${fieldValue(bean: mediaInstance, field: "owner.username")}</g:link></td>

            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'} type rightEdge">${fieldValue(bean: mediaInstance, field: "fileType")}</td>

            <td class="${(i % 2) == 0 ? 'mediaOdd' : 'mediaEven'}"><g:render template="templates/viewDropDown" model="['mediaInstance':mediaInstance,'viewIDInstanceList':viewIDInstanceList]" /></td>

            </tr>
			</g:if>
          </g:each>
          </tbody>
        </table>
    </div>

    <div class="paginateButtons">
      <g:paginate controller="media" action="list" params="${params}" total="${mediaInstanceTotal}" />
    </div>

    <h1><a href="" onclick="javascript:alterDisplay('articleView');return false;">${message(code: 'media.showhideArticle.label', default: 'Show/Hide Articles')}</a></h1>
    <div id="articleView" class="articles">
      <g:render template="templates/mediaReportArticleView" />
    </div>

  </div>
</body>
</html>
