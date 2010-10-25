<%--
This template creates dropdown-fields to chose to attach media files to articles.
    author: Olaf Roeder
--%>

<table>
  <tr>
  <td><g:form action="addToView" method="get">
    <table><tr><td><select name="view" style="width:150px;">
        <g:each in="${viewIDInstanceList}" var="viewID">
          <g:each in="${reportInstanceList}" var="report">
            <g:if test="${viewID.reportId == report.id}">
              <option value="${viewID.view}">${report}</option>
            </g:if>
          </g:each>

          <g:each in="${articleInstanceList}" var="article">
            <g:if test="${viewID.articleId == article.id}">
              <option value="${viewID.view}">&#160;&#160;&#160;&#160;&#160;${article}</option>
            </g:if>
          </g:each>
        </g:each>
      </select></td>
    <td><input type="hidden" name="id" value="${mediaInstance.id}" /></td>
    <td><input type="submit" value="+" /></td></tr></table>
  </g:form></td>
</tr>
</table>