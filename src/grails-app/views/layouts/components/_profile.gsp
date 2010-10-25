<!--
  This component lists the profile menu if the user is logged in
  @author: Frank
  @changed 28.08.2010: Gerrit
-->
<div class="title">Profil (${session.user.username})</div>
<ul>
	<li><g:link controller="user" action="showUser" id="${session.user.id}">
		<g:message code="sidebar.profile" default="My Profile" />
	</g:link></li>
	<li><g:link controller="user" action="myRecommendation">
		<g:message code="sidebar.recommendation" default="My Recommendations" />
	</g:link></li>
		<li><g:link controller="user" action="myFollowers">
		<g:message code="sidebar.follower" default="My Followers" />
	</g:link></li>
	<li><g:link controller="twitter" action="login">
		<g:message code="sidebar.twitter" default="Twitter" />
	</g:link></li>
		
</ul>

