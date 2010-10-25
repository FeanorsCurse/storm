
<!--
  This component lists all Reports with the Status "approved" in the Sidebar menu


  TODO: List only Reports for the specified language

  @author: Irene, Gerrit
-->

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ReportEditorMain.ReportEditor.Report"%>
<%@ page import="ReportEditorMain.Enum.ReportStatus"%>
<%@ page import="ReportEditorMain.ReportEditor.ReportService"%>
<%@ page import="systemadministration.modulmanagement.Language"%>


<!-- Sidebar Menu -->
<%
//Gerrit: set default language german (de)
if(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']==null){
	session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE']= new Locale("de")
}
%>
<ul class="reports">

	<g:each status="i" var="report" in="${Report.findAllByStatusAndLanguage(ReportStatus.findByStatus('Approved'), Language.findByCountryCode(session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'].toString()), [max:5, sort:'id', order:'desc'])}">
		<g:reportSidebar report="${report}" status="${i}"/>
	</g:each>
</ul>