
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.Enum.ArticleStatus"%>
<%@ page import="systemadministration.usermanagement.User"%>
<%@ page import="ReportEditorMain.ReportEditor.Report"%>
<%@ page import="ReportEditorMain.Enum.ReportStatus"%>
<%@ page import="ReportEditorMain.ReportEditor.ReportService"%>
<%@ page import="systemadministration.modulmanagement.Language"%>

<%@ page defaultCodec="none" %>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="search.results.list" default="Searchresults" /></title>
</head>
<body>
<div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="link.text.home" default="Home" /></a> </span>

        </div>
<h1><g:message code="results.list.label" default="Results of Search" /></h1>

<g:if test="${!articleInstanceList}">
        <g:if test="${!searchkey}">
                <g:message code="results.nokey.label" default="No key given" />
        </g:if>
        <g:else>
                <g:message code="results.notfound.label" default="No results found for key" /> ${searchkey}
        </g:else>
</g:if>
<%--
Search on Root-Element shall not be allowed
Frank Gerken
--%>
<g:elseif test="${searchkey == 'Root' || searchkey == 'root'}">
        <g:message code="results.notallowed.label" default="No Search allowed" />
</g:elseif>


<g:else>


  <g:if test="${searchTyp == 'all' || searchTyp == 'article'}">
        <table class="list">
                <tr>
                        <th><g:message code="results.nr.label" default="No." /></th>
                        <th><g:message code="resultsarticle.title.label" default="Name of Article" /></th>
                </tr>
                          <g:set var="counter" value="${1}" />
                          <g:each in="${articleInstanceList}" status="i" var="articleInstance">
                  <tr>
                          <g:if test="${articleInstance.name != 'Root'}">
                                  <td style="vertical-align:top; width:5%;">${counter}</td>
                                  <g:set var="counter" value="${counter + 1}" />
                                <td> <g:link controller="article" action="display" params="[id:articleInstance.id]">${articleInstance.name}</g:link></td>
                        </g:if>
                        <g:else>
                        </g:else>
                </tr>
                </g:each>
        </table>
        <div class="paginateButtons">&nbsp;</div>

</g:if>


<g:if test="${searchTyp == 'all' || searchTyp == 'report'}">
        <table class="list">
                <tr>
                        <th><g:message code="results.nr.label" default="No." /></th>
                        <th><g:message code="resultsreport.title.label" default="Name of Report" /></th>
                </tr>
                          <g:set var="counter" value="${1}" />

     <g:each status="i" var="report" in="${reportInstanceList}">
              <tr>
                          <g:if test="${report.name != 'Root'}">
                                 <td style="vertical-align:top; width:5%;"><br />${counter}</td>
                                  <g:set var="counter" value="${counter + 1}" />
                <td style="vertical-align:top">
                <ul style="list-style-type:none; margin-left:-40px;" id="report${report.id}">
                <g:reportArticleSearch report="${report}" />
        </ul>
                </td>

                </g:if>
                        <g:else>
                        </g:else>
                </tr>
        </g:each>


 </table>
        <div class="paginateButtons">&nbsp;</div>


   </g:if>

  <g:if test="${searchTyp == 'all' || searchTyp == 'comment'}">
 <table class="list">
                <tr>
                        <th><g:message code="results.nr.label" default="No." /></th>
                        <th><g:message code="resultscoment.title.label" default="Name of Comment" /></th>
                </tr>
                          <g:set var="counter" value="${1}" />
                          <g:each in="${commentInstanceList}" status="i" var="commentInstance">
                  <tr>
                          <g:if test="${commentInstance.content != 'Root'}">
                                  <td style="vertical-align:top; width:5%;" >${counter}</td>
                                  <g:set var="counter" value="${counter + 1}" />
                                <td>${commentInstance.title}



                  <ul>
                        <g:each in="${commentInstance?.article}" var="commentarticle">
                          <li> <g:link controller="article" action="display" params="[id:commentInstance?.article.id]"> ${commentarticle?.name} </g:link></li>
                        </g:each>
                      </ul>


                                 </td>


                        </g:if>
                        <g:else>
                        </g:else>
                </tr>
                </g:each>
        </table>
        <div class="paginateButtons">&nbsp;</div>

</g:if>
</g:else>



</body>
</html>