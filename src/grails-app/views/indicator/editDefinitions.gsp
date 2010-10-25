
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<g:form method="post">
	<g:hiddenField name="id" value="${indicatorInstance?.id}" />
	<g:hiddenField name="version" value="${indicatorInstance?.version}" />
	<g:hiddenField name="section" value="${section}" />
	<g:if test="${section=='1'}">
	
		<div id="topichead"><h7><g:message code="indicator.relevance.titel" /></h7><br><br>
		<richui:richTextEditor name="definitions1"
			value="${fieldValue(bean:indicatorInstance, field: 'relevance').decodeHTML()}" width="56%"/>
		<p>
		<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
	</div>
		</p>
		</div>
	</g:if>

	<g:if test="${section=='2'}">
		<div id="topichead"><h7><g:message code="indicator.compilation.titel" /></h7><br><br>
		<richui:richTextEditor name="definitions2"
			value="${indicatorInstance?.compilation}" width="56%" />
		<p>
		<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
		</div>
		</p>
		</div>
	</g:if>

	<g:if test="${section=='3'}">
		<div id="topichead"><h7><g:message code="indicator.documentation.titel" /></h7><br><br>
		<richui:richTextEditor name="definitions3"
			value="${indicatorInstance?.documentation}" width="56%" />
		<p>
		<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
		</div>
		</p>
		</div>
	</g:if>

	<g:if test="${section=='4'}">
		<div id="topichead"><h7><g:message code="indicator.reference.titel" /></h7><br><br>
		<richui:richTextEditor name="definitions4"
			value="${indicatorInstance?.reference}" width="56%" />
		<p>
		<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
		</div>
		</p>
		</div>
	</g:if>

	<g:if test="${section=='5'}">
		<div id="topichead"><h7><g:message code="indicator.definition.titel" /></h7><br><br>
		<richui:richTextEditor name="definitions5"
			value="${indicatorInstance?.definition}" width="56%" />
		<p>
		<div class="buttons" align="right"><span class="button"><g:actionSubmit
		class="save" action="update"
		value="${message(code: 'indicator.update.button', default: 'Update')}" /></span>
		</div>
		</p>
		</div>
	</g:if>
</g:form>
