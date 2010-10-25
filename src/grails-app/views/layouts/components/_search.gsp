<!--
  This component shows the Search-Field and Search-Button
  @author: Frank , Rachid
-->

<%@ page import="ReportEditorMain.Enum.ArticleStatus"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>

<g:form controller="home" action="searchArticle" >
        <input type="submit" id="header_search_submit" value="<g:message code="serach.list" default="Search" />" />
        <select name="searchType" size="1" id="header_search_select">
            <option value="all"><g:message code="article.list.filter.generall" default="anywhere" /></option>
        	<option value="report"><g:message code="article.list.filter.report" default="reports" /></option>
        	<option value="article"><g:message code="article.list.filter.article" default="articles" /></option>
        	<option value="comment"><g:message code="article.list.filter.commentar" default="comments" /></option>
         </select>
    <richui:autoComplete id="header_searchtext" name="searchtext" maxResultsDisplayed="20" minQueryLength="3" value="${params.searchtext}" action="${createLinkTo('dir': 'home/autocomplete')}" />
</g:form>