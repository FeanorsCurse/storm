<!--
   @autor Rachid
   @changed Kerem 28.08.2010
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="main" />
	<title><g:message code="link.text.Sitemap" default="Sitemap" /></title>
</head>
<body>
<h1><g:message code="link.text.sitemap" default="Sitemap" /></h1>

<ul>
<li><a href="/Storm/welcome/welcomePage">Home</a></li>

<li>Anmeldung</li>
<ul>
<li><a href="/Storm/user/authenticate">Login</a></li>
<li><a href="/Storm/report/user/createUser">Registrieren</a></li>
<li><a href="/Storm/">Passwort vergessen?</a></li> </ul>

<li>Nachhaltigkeitsberichte</li>
<ul>
<li><a href="/Storm/report/display/3">Nachhaltigkeitsbericht 2008</a></li> <ul> <li><a href="/Storm/article/display/15">Nachhaltiges Leitbild</a></li> <li><a href="/Storm/article/display/22">Studium und Lehre</a></li> <li><a href="/Storm/article/display/25">Forschung</a></li>
<li><a href="/Storm/article/display/27">Universität und Umweltschutz</a></li> <li><a href="/Storm/article/display/28">Menschen</a></li>
</ul>
<li><a href="/Storm/report/display/1">Nachhaltigkeitsbericht 2006</a></li> </ul>

<li>Die beliebtesten Artikel</li>
<ul>
<li><a href="/Storm/welcome/lastViewedArticles">Update last Viewed Articles</a></li> <li><a href="/Storm/welcome/articleRecommendation">Update Recommendation List</a></li> <li><a href="/Storm/welcome/topArticles">Update top Articles</a></li> </ul>

<li>Last answered Questions</li>

<li>RSS Feeds</li>

<g:if test="${(session.user.username != 'Anonym')}">

        <!-- ***Dieser Part ist auskommentiert, weil die Sitemap im Backend nicht erwünscht ist. -->
        <!--
	<g:if test="${session.user.username == 'admin'}">
	<li>Admin</li>
	<ul>
		<li><a href="/Storm/user/list">User bearbeiten</a></li> <li><a href="/Storm/role/list">Rolle bearbeiten</a></li> <li><a href="/Storm/rule/list">Rechte bearbeiten</a></li> <li><a href="/Storm/module/list">Module bearbeiten</a></li> <li><a href="/Storm/teamplate/list">Teamplate bearbeiten</a></li> <li><a href="/Storm/language/list">Report Sprachen</a></li> <li><a href="/Storm/helparticle/list">Hilfeartikel anlegen</a></li> <li><a href="/Storm/SAPConnection/list">SAPConnections</a></li>
		<li><a href="/Storm/bapi/list">BAPIs</a></li>
		<li><a href="/Storm/rssFeed/list">RSSFeed</a></li>
		<li><a href="/Storm/question/list">Questions</a></li>
		<li><a href="/Storm/databaseConnection/list">Databases</a></li>
		<li><a href="/Storm/SQLStatement/list">SQL</a></li>
		<li><a href="/Storm/newsletter/list">Newsletter</a></li>
	</ul>

	<li>Schema</li>
	<ul>
		<li><a href="/Storm/TReport/create">Schema anlegen</a></li> <li><a href="/Storm/TReport/list">Schemata verwalten</a></li> <li><a href="/Storm/category/list">Kategorien verwalten</a></li> <li><a href="/Storm/indicator/create">Indikator anlegen</a></li> <li><a href="/Storm/indicator/list?show=all">Indikator verwalten</a></li> </ul> </g:if> <li>Eingabe</li> <ul> <li><a href="/Storm/report/create">Bericht anlegen</a></li> <li><a href="/Storm/report/list">Berichte verwalten</a>
		</li>
	</ul>

	<li>Dateien</li>
	<ul>
		<li><a href="/Storm/media/create">Datei hochladen</a></li> <li><a href="/Storm/media/list">Dateien verwalten</a></li>
	</ul>

	<li>Logs</li>
	<ul>
		<li><a href="/Storm/changeLog/list">ChangeLog anzeigen</a></li> <li><a href="/Storm/securityLog/list">SecurityLog anzeigen</a></li> <li><a href="/Storm/accessLog/list">AccessLog anzeigen</a></li>
	</ul>
	</g:if>
        -->

	<li><a href="/Storm/home/newsletter">Newsletter</a></li>
	<li><a href="/Storm/home/sitemap">Sitemap</a></li>
	<li><a href="/Storm/home/impressum">Impressum</a></li>
	<li><a href="/Storm/home/agb">AGB</a></li>
	<li><a href="/Storm/home/contact">Kontakt</a></li>
	<li><a href="/Storm/home/privacy">Datenschutz</a></li>
</ul>

</body>
</html>
