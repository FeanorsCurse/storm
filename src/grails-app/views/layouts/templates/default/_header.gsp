
<!--
  This component shows the Header
  @author: Frank
  @changed 28.08.2010: Gerrit
-->
<%@ page contentType="text/html;charset=UTF-8" %>

<div id="header">
		<div id="header_top">
			<div id="leftcorner">&nbsp;</div>
			<div id="header_top_center">
			<g:if test="${session.user.username == 'Anonym'}">
				<g:render template="/layouts/components/login" />
				<div id="login_logout_info">
           			<g:render template="/layouts/components/language" />
            	</div>
            </g:if>
            <g:else>
            	<div id="login_logout_info">
           			<g:message code="header.welcome" default="Welcome" /> ${session.user.username} | <g:link controller="user" action="logout"><g:message code="header.logout" default="Logout" /></g:link>
            	    <g:render template="/layouts/components/language" />
            	</div>
            </g:else>
	  		</div>
      		<div id="rightcorner">&nbsp;</div>
		</div>
		<div id="header_center">
			<a href="${createLink(uri: '/welcome/welcomePage', absolute: false)}">
	        	<img src="${resource(dir:'images',file:'Logo_Pg_Storm.png')}" alt="STORM Home" id="header_logo" width="300"/>
	        </a>
	       <div id="header_search">
	       	<g:render template="/layouts/components/search" />
	       </div>
		</div>

    	<div id="header_bottom">
    		<g:if test="${session.user.username != 'Anonym'}">
        		<div id="menu">
        			<ul>
                        		<g:render template="/layouts/components/adminMenu" />
                        	</ul>
                        </div>
		</g:if>
    		<g:if test="${session.user.username == 'Anonym'}">
                        <div id="menu">
                                <ul>
                                        <g:render template="/layouts/components/Menu" />
				</ul>
			</div>
		</g:if>
		</div>
	</div>
