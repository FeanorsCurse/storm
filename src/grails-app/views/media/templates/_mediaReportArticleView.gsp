<%--
This template shows all articles and its attached media.
    author: Olaf Roeder
--%>

<g:each var="report" in="${reportInstanceList}">
  <ul id="report${report.id}">
    <g:reportArticleView report="${report}" />
  </ul>
</g:each>