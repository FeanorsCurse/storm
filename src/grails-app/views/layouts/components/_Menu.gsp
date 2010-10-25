
<%--
  This component lists the Admin-Menu
  @author: Frank
--%>

<li class="sub"><g:message code="menu.infocart" default="Infocart" />
<ul>
	<li><g:link controller="infocart" action="show">
		<g:message code="menu.infocart.show" />
	</g:link></li>
        <g:if test="${session?.user?.username != 'Anonym'}">
	<li><g:link controller="infocart" action="list">
		<g:message code="menu.infocart.myinfocarts" />
	</g:link></li>
        </g:if>
	<li><g:link controller="infocart" action="listvisible">
		<g:message code="menu.infocart.listvisible" />
	</g:link></li>
	<li><g:link controller="infocart" action="create">
		<g:message code="menu.infocart.createnew" />
	</g:link></li>
</ul>
</li>
<li class="sub"><g:message code="menu.help" default="Help" />
<ul>
	<li><g:link controller="help" action="overview">
		<g:message code="menu.help.overview" />
	</g:link></li>
	<li><g:link controller="help" action="search">
		<g:message code="help.search" />
	</g:link></li>
</ul>
</li>
