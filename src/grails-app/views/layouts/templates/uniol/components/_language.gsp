<%@ page import="systemadministration.modulmanagement.Language"%>
<!-- <g:each in="${Language.findAllByEnabledAndSite(true,true)}" var="language">
<a href="?lang=${language.countryCode}" target="_self">
		<img src="${resource(dir:'images',file:language.name+'.png')}" alt="${language.name}" />
</a>
</g:each>

 -->


<a href="?lang=de" target="_self"> <img
	src="${resource(dir:'images',file:'de.png')}" alt="Deutsch" /> </a>
<a href="?lang=en" target="_self"> <img
	src="${resource(dir:'images',file:'gb.png')}" alt="Englisch" /> </a>


