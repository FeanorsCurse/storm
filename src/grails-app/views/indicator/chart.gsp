<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>



<g:if test="${src}">
<div id="chart" name="chart"  style="width: 54%;">
	<img alt="Chart" src="${src}">
</div>
</g:if>

<g:formRemote name="chartform" url="[controller:'indicator',action:'chart']"  update="chartView">

<strong><g:message code="indicator.chart.show" default="Show" /> </strong>
<g:message code="indicator.chart.year.from" default="From" />

<g:select name="fromYear" noSelection="${['0':message(code:'indicator.year.selection')]}" from="${years}">
</g:select>
<g:message code="indicator.chart.year.to" default="To" />
<g:select name="toYear" noSelection="${['0':message(code:'indicator.year.selection')]}" from="${years}">
</g:select>

<input type="hidden" name="id" value="${indicatorInstance.id}"/>

<input type="submit" value="${message(code: 'indicator.update.button', default: 'Update')}" />

</g:formRemote>