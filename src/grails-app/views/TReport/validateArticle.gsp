<script type="text/javascript">

function CheckGlobal(chk){

	if(document.indi_add.indiCheckCtrl.checked==true){
	for (i = 0; i < chk.length; i++)
		chk[i].checked = true ;
	}else{
		UnCheckAll(chk);
	}

	}

function UnCheckAll(chk){
	for (i = 0; i < chk.length; i++)
	chk[i].checked = false ;
	}

</script>

<div class="info"><g:if
	test="${validatorResult.isReportValid()}">
	<img src="${resource(dir:'images/schemaEditor',file:'isValid.png')}"
		title="${message(code: 'report.is.valid', default: 'Report is valid')}"
		alt="${message(code: 'report.is.valid', default: 'Report is valid')}" />
	<g:message code="report.is.valid" />
</g:if> <g:else test="${!validatorResult.isReportValid()}">
	<img src="${resource(dir:'images/schemaEditor',file:'isNotValid.png')}"
		title="${message(code: 'report.is.notvalid', default: 'Report is not valid')}"
		alt="${message(code: 'report.is.notvalid', default: 'Report is not valid')}" />
	<g:message code="report.is.notvalid" />
</g:else></div>

<g:if test="${validatorResult.getMissingIndicators()}">
	<p><b><g:message code="report.missing.required.indicators" /></b>

	<g:form name="indi_add" id="indi_add"
		url="[action:'updateAllocation', controller:'articleIndicator']">
		<g:hiddenField name="id" value="${articleInstance?.id}"
			id="${articleInstance?.id}" />
		<div id="out">
		<table class="list">
			<tr>
			<th><input type="checkbox" name=indiCheckCtrl
					onClick="CheckGlobal(document.indi_add.checkbox)" /></th>
				<th><g:message code="indicator.shortName.label" /></th>
				<th><g:message code="indicator.name.label" /></th>
			</tr>

			<g:each in="${validatorResult.getMissingIndicators()}" status="i"
				var="missing">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
				<td><input type="checkbox" name="checkbox"
						value="${missing.id}" id="${missing.id}" /></td>
					<td><g:link action="show" controller="indicator"
						id="${missing.id}">
						${missing.shortName}
					</g:link></td>
					<td><g:link action="show" controller="indicator"
						id="${missing.id}">
						${missing.name}
					</g:link></td>
				</tr>
			</g:each>
		</table>
		</div>
		<div class="buttons"><span class="button"> <input
			type="submit" value="${message(code: 'indicator.add.label', default: 'Add')}" /> </span></div>
	</g:form></p>
</g:if>


<g:if test="${validatorResult.getMissingOptionalIndicators()}">
<p><b><g:message code="report.missing.optional.indicators" /></b>
<g:form name="optindi_add" id="optindi_add"
		url="[action:'updateAllocation', controller:'articleIndicator']">
		<g:hiddenField name="id" value="${articleInstance?.id}"
			id="${articleInstance?.id}" />
<div id="out">
<table class="list">
	<tr>
	<th><input type="checkbox" name=indiCheckCtrl
			onClick="CheckGlobal(document.optindi_add.checkbox)" /></th>
		<th><g:message code="indicator.shortName.label" /></th>
		<th><g:message code="indicator.name.label" /></th>
	</tr>

	<g:each in="${validatorResult.getMissingOptionalIndicators()}" status="i"
		var="validatorResult">
		<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
		<td><input type="checkbox" name="checkbox" value="${validatorResult.id}" id="${validatorResult.id}" /></td>
		
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "shortName")}
			</g:link></td>
			<td><g:link action="show" controller="indicator" id="${validatorResult.id}">
			${fieldValue(bean: validatorResult, field: "name")}
			</g:link></td>
			</tr>
	</g:each>
</table>
</div>
<div class="buttons"><span class="button"> <input type="submit" value="${message(code: 'indicator.add.label', default: 'Add')}" /> </span></div>
</g:form>
</p>
</g:if>
