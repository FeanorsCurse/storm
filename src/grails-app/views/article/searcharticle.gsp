
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.Enum.ArticleStatus"%>
<%@ page import="systemadministration.usermanagement.User"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<g:set var="entityName" value="${message(code: 'Article.label', default: 'Article')}" />
<title><g:message code="article.list.label" args="[entityName]" /></title>
<script language="JavaScript">
function CheckAll(chk) {
  if(document.myform.Check_ctr.checked==true) {
    for (i = 0; i < chk.length; i++)
      chk[i].checked = true ;
   }
   else {
    for (i = 0; i < chk.length; i++)
      chk[i].checked = false ;
    }
}
</script>
</head>
<body>

<h1><g:message code="results.list.label" default="Results" /></h1>
<g:if test="${tilde}">
  <g:if test="${flash.message}">
    <div class="message">
      ${flash.message}
      <g:each in="${articleInstanceList}" status="i" var="articleInstance">
        <g:link controller="Article" action="searchArticle" params="[article:articleInstance.name]">${articleInstance.name}</g:link>
      </g:each>
    </div>
  </g:if>
</g:if>

<!--<div id="searchfilter">
  <g:form url='[controller: "Article", action: "searchArticle"]' id="searchableForm" name="searchableForm" method="get">
    <table>
      <tr>
	
        <td>
          <g:select name="creator" optionKey="id" size="1" noSelection="['0':'Alle Autoren']" from="${User.list()}" />
	</td>
	<td>
          <input type="submit" value="Search">
	</td>
      </tr>
    </table>
  </g:form>
</div>-->

<div class="paginateButtons"></div>

<g:if test="${!articleInstanceList}">-Keine Suchergebnisse-</g:if>

</body>
</html>
