
<%--
Navigation Frontend
@author: Frank
@changed 28.08.2010: Gerrit
--%>

<div id="nav">
<%-- help-List --%> <g:if test="${params.controller=='help'||params.controller=='helpSection'||params.action=='infosearch' ||params.action=='helparticleshow' ||params.action=='infoserchDetails'  ||params.action=='searchhelparticleshow'}">
	<div class="title"><g:message code="sidebar.help.title" default="Help" /></div>
	<g:render template="/layouts/components/sidebar_help" />
</g:if>
<%-- Report-List --%>
	<div class="title"><g:message code="sidebar.report.title" default="Reports" /></div>
	<g:render template="/layouts/components/sidebar_report" />
<%-- published Infocarts --%> <g:if test="${params.controller=='welcome'||params.controller=='infocart'}">
	<div class="title"><g:message code="sidebar.publishedinfocarts.title" default="Published Infocarts" /></div>
	<g:render template="/layouts/components/sidebar_publishedinfocarts" />
</g:if>
<%-- Recommender Engine --%>
	<g:render template="/layouts/components/recommendations" />
<%-- Check if the User is logged in --%> <g:if test="${session.user.username == 'Anonym'}"></g:if> <g:else>
	<%-- Profile-Section --%>
	<g:render template="/layouts/components/profile" />
	<%-- Recommendations-List --%>
</g:else>
<%-- Cooperations --%>
        <g:render template="/layouts/components/sidebar_cooperations" /></div>
