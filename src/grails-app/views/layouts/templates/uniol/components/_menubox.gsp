    <div id="menu_top">
      <ul>   
	 	
			<!-- Backend Header Menu--> 
			<g:if test="${session.user.username == 'Anonym'}">
				<g:render template="/layouts/components/Menu" /> 
			</g:if>
			<g:else>
				<g:render template="/layouts/components/adminMenu" /> 
				<strong>Welcome ${session.user.username} | <g:link controller="user" action="logout"><g:message code="link.text.logout" default="Logout" /></g:link></strong>
			</g:else>
      </ul>
      
    </div>
   