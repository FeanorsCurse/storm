

<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="shemaEditor.shemaAdministration.TNode"%>
<g:javascript library="prototype" />


<div id="topichead"><h7><g:message
	code="indicator.list.label" default="Indicators" /></h7></div>
<div id="option"><g:message code="indicator.category.label"
	default="Category" /> <g:select name="category" STYLE="width: 135px"
	from="${shemaEditor.indicatorAdministration.Category.list()}"
	optionKey="id" value="${indicatorInstance?.category?.id}"
	noSelection="${['0':message(code:'category.show.all.dropdown')]}"
	onchange="${remoteFunction(controller:'indicator', action:'listGlobalIndicators', update:'globalIndicatorList', params:'\'cId=\'+this.value+\'&snode=\'+document.getElementById(\'snode\').value+\'&ignore=\'+document.getElementById(\'ignore\').value+\'&report=\'+document.getElementById(\'report\').value' )}" />
<br>
<p><g:message code="indicator.indicator.label"
	default="Indicator" /><g:remoteField controller="indicator"
	action="searchIndicator" update="globalIndicatorList" name="title"
	value="${lastkey}" id="${snode }" /></p>

<input type="hidden" value="${snode}" name="snode" id="snode"> <input
	type="hidden" value="${report}" name="report" id="report"> <input
	type="hidden" value="1" name="ignore" id="ignore">

<p>
<g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
	<br>
</g:if>
<g:if test="${flash.info}">
	<div class="info">
	${flash.info}
	</div>
	<br>
</g:if>
</p>


<%--calls the  listGlobalIndicators-Action of Indicator and renders the listGlobalIndicators.gsp (views/indicator/--%>
<div id="globalIndicatorList"><g:include
	action="listGlobalIndicators" controller="indicator" /></div>

<p>
<hr>
<p><%--calls the  listLocalIndicators-Action of Indicator and renders the listLocalIndicators.gsp (views/indicator/--%>
<div id="localIndicatorList"><g:include
	action="listLocalIndicators" controller="TNode" id="${snode}" /></div>
</p>

<div id="indicatorDetails" name="indicatorDetails"></div>
</div>





