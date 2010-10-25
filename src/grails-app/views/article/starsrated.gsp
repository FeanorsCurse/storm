
<%@ page import="ReportEditorMain.ArticleEditor.Article" %>

<%i=1%>
<g:while test="${i<=articleInstance.averageRating}">
  <img src="${resource(dir:'images/oxygen',file:'rating.png')}" alt="${articleInstance.averageRating} star(s) rated"/><%i++%>
</g:while>
<g:while test="${i<=5}">
  <img src="${resource(dir:'images/oxygen',file:'rating_grey.png')}" alt="${articleInstance.averageRating} star(s) rated"/><%i++%>
</g:while>