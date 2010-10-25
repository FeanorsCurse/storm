
<%@ page import="systemadministration.recommender.Welcome"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'welcome.label', default: 'Welcome')}" />
<title><g:message code="welcome.label" default="Welcome" /></title>
<g:javascript library="jquery" plugin="jquery" />
<style type="text/css">
p{
	text-align:justify;
}
</style>

<script type="text/javascript">
    var timer;
    timedMsg();
	function timedMsg()
	{
		var timer=setTimeout(updateLastComments(),5000);
	}
	function updateLastComments()
	{
		jQuery.ajax({ type: 'POST', url: "/Storm/welcome/lastComments", context: document.body, success: function(data,textStatus) {
			  jQuery('#div10').html(data); 
		    }});
		

	}
	</script>

</head>
<body>

<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<g:if test="${flash.info}">
	<div class="info">${flash.info}</div>
</g:if>

<h1><g:message code="welcome.welcomeMsg" default="Welcome to STORM" /></h1>
<hr></hr>

<g:message code="welcome.text" default="Welcome" />

<hr></hr>
<table>
	<tr>
		<td style="width: 50%; vertical-align: top; padding-right: 20px;">
		<h2><g:message code="welcome.lastComments" default="Last Comments"/></h2>
		<div id="div10" style="display: block"><g:render template="lastComments" lastViewedArticles="${lastComments}" /></div>
		</td>
		<td style="width: 50%; vertical-align: top">
		<h2><g:message code="welcome.flashVideo" default="Video"/>
		</h1>
		<div style="text-align: center">
		<object width="100%" height="230"><param name="movie" value="http://www.youtube.com/v/S8nQ_wV9IaM?fs=1&amp;hl=de_DE&amp;rel=0&amp;hd=1"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param><embed src="http://www.youtube.com/v/S8nQ_wV9IaM?fs=1&amp;hl=de_DE&amp;rel=0&amp;hd=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="100%" height="230"></embed></object>
		</div>
		</td>
	</tr>
</table>
<br></br>

<g:render template="/layouts/components/tagcloud" />
<hr></hr>

<table>
	<tr>
		<td style="width: 50%; vertical-align: top; padding-right: 20px;">
		<g:if test="${topTenArticleInstanceList != null}">
			<h2><g:message code="welcome.mostArticle" default="Top ten"/></h2>
			<g:each var="article" in="${topTenArticleInstanceList}" status="i">
				<g:if test="${i<5}">
					<g:link controller="article" action="display" id="${article.id}">${article.name}</g:link><br></br>
				</g:if>
			</g:each>
		</g:if>
		<h2><g:message code="welcome.lastArticle" default="Last viewed Articles"/></h2>
		<g:each var="article" in="${lastViewedArticles}" status="i">
			<g:if test="${i<5}">
				<g:link controller="article" action="display" id="${article.id}">${article.name}</g:link><br></br>
			</g:if>
		</g:each>
		<g:if test="${lastViewedArticles == null}">
			<div id="div11" style="display: block"><g:message code="welcome.pleaseLogin" default="Please login" /></div>
		</g:if>
		<h2><g:message code="welcome.article2article" default="Recommendations" /></h2>
		<g:each var="article" in="${articleRecommendationList}" status="i">
			<g:if test="${i<5}">
				<g:link controller="article" action="display" id="${article.id}">${article.name}</g:link><br></br>
			</g:if>
		</g:each>

		<g:if test="${((articleRecommendationList == null)&&( session.user.username=='Anonym'))}">
			<g:message code="welcome.pleaseLogin" default="Please login" />
		</g:if>
		<g:if test="${((articleRecommendationList == null)&&(session.user.username!='Anonym'))}">
			<g:message code="welcome.pleaseLoginNoData" default="No Data for you" />
		</g:if>		
		</td>
		
		<td style="width: 50%; vertical-align: top;">

		<h2><g:message code="welcome.yourGroup" default="Interest Group Recommendation"/></h2>
		<g:if test="${interestgroupArticle != null}">
			<g:each var="article" in="${interestgroupArticle}" status="i">
				<g:if test="${i<5}">
					<g:link controller="article" action="display" id="${article.id}">${article.name}</g:link><br></br>
				</g:if>
			</g:each>
		</g:if>

		<g:if test="${((interestgroupArticle == null)&&( session.user.username=='Anonym'))}">
			<g:message code="welcome.pleaseLogin" default="Please login" />
		</g:if>
		<g:if test="${((interestgroupArticle == null)&&(session.user.username!='Anonym'))}">
			<g:message code="welcome.pleaseLoginNoData" default="No Data for you" />
		</g:if>		
		
		
		<h2><g:message code="welcome.articleFollower" default="Friend Recommendations"/></h2>
		<g:if test="${friendsArticle != null}">
			<g:each var="article" in="${friendsArticle}" status="i">
				<g:if test="${i<5}">
					<g:link controller="article" action="display" id="${article.id}">${article.name}</g:link><br></br>
				</g:if>
			</g:each>
		</g:if> 
		<g:if test="${((friendsArticle == null)&&( session.user.username=='Anonym'))}">
			<g:message code="welcome.pleaseLogin" default="Please login" />
		</g:if>
		<g:if test="${((friendsArticle == null)&&(session.user.username!='Anonym'))}">
			<g:message code="welcome.pleaseLoginNoData" default="No Data for you" />
		</g:if>
		</td>
	</tr>
</table>

<hr></hr>
<h2><g:message code="welcome.lastquestions" default="Last answered Questions"/></h2>
<div id="div16" style="display: block"><g:render template="answeredQuestions" /></div>		
		
<hr></hr>
<table>
	<td style="width: 70%; vertical-align: top; padding-right: 20px;">
		<h2><g:message code="welcome.rss" default="More News" /></h2>
		<div id="div17" style="display: block;text-align:justify;"><g:render template="/layouts/components/rssFeed" /></div>
	</td>
</table>

<hr></hr>

<table>
<tr>
<td style="vertical-align: top;">
<g:link controller="feed" action="feed">
	<img src="${resource(dir:'images/schemaEditor',file:'feed-icon.png')}" title="${message(code: 'indicator.feed.abo', default: 'RSS Feed Abo')}" alt="${message(code: 'indicator.feed.abo', default: 'RSS Feed Abo')}" />
</g:link>
</td>
<td style="vertical-align: top;">
<iframe src="http://www.facebook.com/widgets/like.php?href=http://vittel.informatik.uni-oldenburg.de:8080/Storm/welcome/welcomePage" scrolling="no" frameborder="0" style="border: none; width: 100%; height: 80px"></iframe>
</td>
</tr>
</table>

</body>
</html>
