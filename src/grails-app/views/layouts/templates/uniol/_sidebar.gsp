<!--
  Sidebar Frontend
  
  
  @author: Gerrit
-->


<ul>

	<!-- Report-List -->
	<li class="sidemenu_header">
	<p>Reports</p>
	<g:render template="/layouts/components/sidebar_report" /></li>

	<!-- Check if the User is logged in -->
	<g:if test="${session.user.username == 'Anonym'}">
		<!-- Login Box -->
		<li class="sidemenu_header">
		<p>Register & Login</p>
		<g:render template="/layouts/components/login_sidebar" />
		</li>
	</g:if>
	<g:else>
		<!-- Profile Box -->
		<li class="sidemenu_header">
		<g:render template="/layouts/components/profile" /></li>
	</g:else>
	
	<!-- Last viewed Articles -->
	<li class="sidemenu_header">
	<g:render template="/layouts/components/recommendations" /></li>
</ul>

