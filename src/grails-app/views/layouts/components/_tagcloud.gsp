
<!--
  This component lists the tagcloud
  @author: Frank, Christian
  
-->

<%@ page import="ReportEditorMain.ArticleEditor.Article" %>

<tc:tagCloud bean="${Article}" controller="article" action="findByTag" paramName="tag" size="${[start: 14, end: 30, unit: 'px']}" color="${[start: '#000', end: '#000']}" sort="true" />
  