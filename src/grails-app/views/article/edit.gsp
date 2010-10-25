
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.MediaCenter.Media"%>
<html>
  <head>
  <g:render template="/layouts/components/richtexteditor" />

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="admin" />
  <title><g:message code="article.edit" default="Edit article" /></title>

  <script type="text/javascript" src="${resource(dir:'js/flvplayer/flvplayer-1.9.7b/flvplayer/',file:'swfobject.js')}" ></script>
  <script type="text/javascript" src="${resource(dir:'js/',file:'embed.js')}" ></script>

</head>
<body>
  <div class="nav">
    <span class="menuButton">
      <g:link class="list" controller="report" action="list">
        <g:message code="article.list.reports.list" default="List Reports"/>
      </g:link>
    </span>&nbsp;
    <span class="menuButton">
      <g:link class="list" action="list">
        <g:message code="article.edit.list" default="Article list" />
      </g:link>
    </span>
  </div>
  
  <div class="debug">
  ${articleInstance?.content}
  </div>

  <div class="body">
    <h1>
      <g:message code="article.edit" default="Edit article" />
    </h1>

<%--calls the  validateReport-Action and renders a result of validation if the author deleted a required indicator, validateArticle.gsp--%>
    <div>
      <g:include action="validateReport" controller="report" params="[articleId:articleInstance.id]"/>
    </div>

    <!-- Message about Revision -->
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:if test="${flash.info}">
      <div class="info">${flash.info}</div>
    </g:if>

  <g:hasErrors bean="${articleInstance}">
    <div class="errors">
      <g:renderErrors bean="${articleInstance}" as="list" />
    </div>
  </g:hasErrors>

  <g:form method="post">
    <g:hiddenField name="id" value="${articleInstance?.id}" />
    <g:hiddenField name="version" value="${articleInstance?.version}" />
    <div class="dialog">
      <table class="list">
        <tr class="prop">
          <td valign="top" class="name">
            <label for="name">
              <g:message	code="article.edit.name" default="Name" />
            </label>
          </td>
          <td valign="top" class="value ${hasErrors(bean: articleInstance, field: 'name', 'errors')}">
        <g:textField name="name" value="${articleInstance?.name}" />
        </td>
        </tr>
      </table>

      <div>
        <textarea rows="20" cols="20"
          name="content">${articleInstance?.content}
        </textarea>
      </div>

      <table class="list">
        <tr class="prop">
          <td
            valign="top" class="name">
            <label for="parentArticle">
              <g:message code="article.edit.parentarticle" default="ParentArticle" />
            </label>
          </td>
          <td
            valign="top" class="value
${hasErrors(bean: articleInstance, field: 'parentArticle', 'errors')}">
        <g:select name="parentArticle.id"
                  from="${articleInstanceList}" optionKey="id"
                  value="${articleInstance?.parentArticle?.id}" />
        </td>
        </tr>

        <tr class="odd">
          <td valign="top" class="name"><label for="sequence"><g:message
                code="article.edit.sequence" default="sequence" /></label></td>
          <td valign="top"
              class="value ${hasErrors(bean: articleInstance, field: 'sequence', 'errors')}">
        <g:textField name="sequence" value="${articleInstance?.sequence}" /></td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name"><label for="tags"><g:message
                code="article.edit.tags" default="tags" /></label></td>
          <td valign="top"
              class="value ${hasErrors(bean: articleInstance, field: 'tags', 'errors')}">
        <g:textField name="tags" value="${articleInstance?.tags.toString().replace('[','').replace(']','')}" /></td>
        </tr>
        </tbody>
      </table>
      <div class="buttons"><span class="button"><g:actionSubmit
            class="save" action="update"
            value="${message(code: 'article.edit.button.update', default: 'Save')}" /></span>
        <span class="button" />
        <span class="button"><g:actionSubmit
            class="cancel" action="cancel"
            value="${message(code: 'article.edit.button.cancel', default: 'Cancel')}" /></span>
        <span class="button" />
      </div>
      <br>

      <h2>
        <g:message code="article.edit.functions" default="Functions"/>
      </h2>
      <g:if test="${articleInstance.status.status=='Development'||articleInstance.status.status=='New'}">
        <g:checkAccess modulname="article" actionname="approve">
          <g:actionSubmit	class="approve" action="approve" value="${message(code: 'article.edit.button.approve', default: 'Approve')}" />
        </g:checkAccess>
      </g:if>

      <span class="button"><g:actionSubmit
          class="preview" action="preview" id="${articleInstance?.id}"
          value="${message(code: 'article.edit.button.preview', default: 'Preview')}" />
      </span>
      <span class="button"><g:actionSubmit
          class="cancel" action="cancel" id="${articleInstance?.id}"
          value="${message(code: 'article.edit.button.release.lock', default: 'Release Lock')}" />
      </span>
  </g:form>



  <h2><g:message code="article.indicator" default="Indicators for this article"/></h2>
  <g:form controller="articleIndicator" method="post" name="form">
    <g:hiddenField name="id" value="${articleInstance?.id}" />

    <g:if test="${indicatorList?.size>0 }">
      <table class="list">
        <tr>
          <th>
${message(code: 'articleindicator.edit.action', default: 'Action')}
          </th>
          <th>
${message(code: 'articleindicator.edit.name', default: 'Name')}
          </th>
          <th>
${message(code: 'articleindicator.edit.value', default: 'Value')}
          </th>
          <th>
${message(code: 'articleindicator.edit.unit', default: 'Unit')}
          </th>
        </tr>
        <g:each in="${indicatorList}" status="i" var="artIndInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
          <g:link controller="articleIndicator" action="edit" id="${artIndInstance.id}">
            <img src="${resource(dir:'images/ReportEditor',file:'edit.png')}"
                 title="${message(code: 'article.indicator.edit', default: 'Edit indicator')}" alt="${message(code: 'article.indicator.edit', default: 'Edit indicator')}" />
          </g:link>
          <g:link controller="articleIndicator" action="delete" id="${artIndInstance.id}">
            <img src="${resource(dir:'images/ReportEditor',file:'delete.png')}"
                 title="${message(code: 'article.indicator.delete', default: 'Delete indicator')}" alt="${message(code: 'article.indicator.delete', default: 'Delete indicator')}" />
          </g:link>

          </td>
          <td class="full">
          <g:link controller="indicator" action="show"
                  id="${artIndInstance.indicator.id}">
            <g:indicatorProgress id="${artIndInstance.indicator.id}"/>
          </g:link>
          </td>
          <td>
${artIndInstance.value}
          </td>
          <td>
${artIndInstance.indicator.unit}
          </td>
          </tr>
        </g:each>
      </table>
    </g:if>
    <g:else>
      <p><g:message code="article.no.indicator" default="No Indicators for this Article available"/></p>
    </g:else>
    <span class="button">
      <g:actionSubmit class="allocate" action="allocate" value="${message(code: 'articleindicator.list.allocate', default: 'Allocate Indicators')}" />
    </span>
  </g:form>

  <h2><g:message code="article.revision" default="Revisions for this article"/></h2>
  <g:form method="post">
    <g:hiddenField name="id" value="${articleInstance?.id}" />
    <g:if test="${articleInstanceRevisionList?.size()>0 }">
      <table class="list">
        <tr>
          <th></th>
          <th>
${message(code: 'article.revision.date', default: 'Date')}
          </th>
          <th>
${message(code: 'article.revision.author', default: 'Author')}
          </th>
        </tr>
        <g:each in="${articleInstanceRevisionList}" status="i" var="revisionInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td>
              <input type="radio" name="radio" value="${revisionInstance.id}" id="${revisionInstance.id}" />
            </td>
            <td class="full">
          <g:link controller="article" action="show"
                  id="${fieldValue(bean: revisionInstance, field: 'id')}">
${revisionInstance.dateCreated}
          </g:link>
          </td>
          <td>
${revisionInstance.author}
          </td>
          </tr>
        </g:each>
      </table>

      <div class="buttons"><span class="button"><g:actionSubmit
            class="save" action="revision"
            value="${message(code: 'article.revision.button.load', default: 'Load revision')}" /></span>
      </div>
    </g:if>
    <g:else>
      <p><g:message code="article.no.revision" default="No Revisions for this Article available"/></p>
    </g:else>
  </g:form></div>
<div>
  <h2><g:message code="article.list.files" default="Files to add for this article"/></h2>
  <g:articleView article="${articleInstance}" />
</div>
</div>
</body>
</html>
