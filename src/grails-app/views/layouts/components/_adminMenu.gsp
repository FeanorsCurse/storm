
<%--
  This component lists the Admin-Menu
  @author: Frank
--%>

<li class="sub"><g:message code="menu.admin" default="Admin" />
<ul>
<g:checkAccess modulname="user" actionname="list">
	<li><g:link controller="user" action="list">
		<g:message code="menu.user.edit" default="Edit Users"/>
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="role" actionname="list">
	<li><g:link controller="role" action="list">
		<g:message code="menu.role.edit" default="Edit Roles"/>
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="rule" actionname="list">
	<li><g:link controller="rule" action="list">
		<g:message code="menu.right.edit" default="Edit Rights" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="module" actionname="list">
	<li><g:link controller="module" action="list">
		<g:message code="menu.module.edit" default="Edit Modules" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="template" actionname="list">
	<li><g:link controller="template" action="list">
		<g:message code="menu.template.edit" default="Edit Templates" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="language" actionname="list">
	<li><g:link controller="language" action="list">
		<g:message code="menu.language.edit" default="Edit Languages" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="SAPConnection" actionname="list">
	<li><g:link controller="SAPConnection" action="list">
		<g:message code="menu.sap.edit" default="Edit SAP" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="bapi" actionname="list">
		<li><g:link controller="bapi" action="list">
		<g:message code="menu.bapi.edit" default="Edit BAPIs" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="rssFeed" actionname="list">
	<li><g:link controller="rssFeed" action="list">
		<g:message code="menu.rss.import.edit" default="Edit RSS Imports" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="feed" actionname="list">
	<li><g:link controller="feed" action="list">
		<g:message code="menu.rss.edit" default="Edit RSS" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="question" actionname="list">
	<li><g:link controller="question" action="list">
		<g:message code="menu.question.edit" default="Edit Questions" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="databaseConnection" actionname="list">
	<li><g:link controller="databaseConnection" action="list">
		<g:message code="menu.database.edit" default="Edit Databases" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="SQLStatement" actionname="list">
	<li><g:link controller="SQLStatement" action="list">
	<g:message code="menu.sql.edit" default="Edit SQL" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="newsletter" actionname="list">
	<li><g:link controller="newsletter" action="list">
		<g:message code="menu.newsletter.edit" default="Edit Newsletter" />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.scheme"
	default="Schemes" />
<ul>
	
	<g:checkAccess modulname="TReport" actionname="list">
	<li><g:link controller="TReport" action="create">
		<g:message code="menu.scheme.create" default="Create Scheme"  />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="TReport" actionname="list">
	<li><g:link controller="TReport" action="list">
		<g:message code="menu.scheme.edit" default="Edit Schemes"  />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="category" actionname="list">
	<li><g:link controller="category" action="list">
		<g:message code="menu.category.edit" default="Edit Categories"  />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="indicator" actionname="create">
	<li><g:link controller="indicator" action="create">
		<g:message code="menu.indicator.create" default="Create Indicator" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="indicator" actionname="list">
	<!-- Gerrit: show:all important for filtering Indicator-List -->
	<li><g:link controller="indicator" action="list" params="[show:'all']">
		<g:message code="menu.indicator.edit" default="Edit Indicators"  />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.report"
	default="Reports" />
<ul>
	<g:checkAccess modulname="report" actionname="create">
		<li><g:link controller="report" action="create">
		<g:message code="menu.report.create" default="Create Report" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="report" actionname="list">
	<li><g:link controller="report" action="list">
		<g:message code="menu.report.edit" default="Edit Reports" />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.media" default="Media" />
<ul>
<g:checkAccess modulname="media" actionname="create">
	<li><g:link controller="media" action="create">
		<g:message code="menu.media.create" default="Create Media" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="media" actionname="list">
	<li><g:link controller="media" action="list">
		<g:message code="menu.media.edit" default="Edit Media" />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.logs" default="Logs" />
<ul>
<g:checkAccess modulname="securityLog" actionname="list">
	<li><g:link controller="securityLog" action="list">
		<g:message code="menu.log.securitylog" default="Edit SecurityLogs" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="accessLog" actionname="list">
	<li><g:link controller="accessLog" action="list">
		<g:message code="menu.log.accesslog" default="Edit AccessLogs" />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.infocart" default="Infocart" />
<ul>
<g:checkAccess modulname="infocart" actionname="show">
	<li><g:link controller="infocart" action="show">
		<g:message code="menu.infocart.show" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="infocart" actionname="list">
	<li><g:link controller="infocart" action="list">
		<g:message code="menu.infocart.myinfocarts" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="infocart" actionname="listvisible">
	<li><g:link controller="infocart" action="listvisible">
		<g:message code="menu.infocart.listvisible" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="infocart" actionname="create">
	<li><g:link controller="infocart" action="create">
		<g:message code="menu.infocart.createnew" />
	</g:link></li></g:checkAccess>
</ul>
</li>
<li class="sub"><g:message code="menu.help" default="Help" />
<ul>
	<g:checkAccess modulname="help" actionname="createsection">
	<li><g:link controller="help" action="createsection">
		<g:message code="menu.help.create"  default="Create Help" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="help" actionname="listsections">
	<li><g:link controller="help" action="listsections">
		<g:message code="menu.help.edit"  default="Edit Help" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="help" actionname="createarticle">
	<li><g:link controller="help" action="createarticle">
		<g:message code="menu.help.create.article"  default="Create Help Article" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="help" actionname="listarticles">
	<li><g:link controller="help" action="listarticles">
		<g:message code="menu.help.edit.article"  default="Edit Help Articles" />
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="help" actionname="overview">
	<li><g:link controller="help" action="overview">
		<g:message code="menu.help.overview" default="Overview"/>
	</g:link></li></g:checkAccess>
	
	<g:checkAccess modulname="help" actionname="search">
	<li><g:link controller="help" action="search">
		<g:message code="help.search"  default="Search" />
	</g:link></li></g:checkAccess>
	
</ul>
</li>
