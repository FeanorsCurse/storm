<SCRIPT LANGUAGE="JavaScript">

function CheckGlobal(chk){

if(document.indi_add.indiCheckCtrl.checked==true){
for (i = 0; i < chk.length; i++)
	chk[i].checked = true ;
}else{
	UnCheckAll(chk);
}

}

function CheckLocal(chk){

	if(document.indi_remove.indiCheckCtrl.checked==true){
	for (i = 0; i < chk.length; i++)
		chk[i].checked = true ;
	}else{
		UnCheckAll(chk);
	}

}

function getIUsage(){
	return document.getElementById("i_usage").value;
}

function UnCheckAll(chk){
for (i = 0; i < chk.length; i++)
chk[i].checked = false ;
}



</script>


<b><g:message code="indicator.available.list"
	default="Available indicators" /></b>
<g:if test="${globalList}">
	<p>
	<form name="indi_add">
	<table class="list">
		<thead>
			<tr>
				<th><input
					onClick="CheckGlobal(document.indi_add.globalIndicatorList)"
					type="checkbox" name="indiCheckCtrl"></th>
				<th></th>

				<th>
				${message(code: 'Indicator.shortName.label', default: 'Short')}
				</th>

				<th>
				${message(code: 'Indicator.name.label', default: 'Name')}
				</th>

			</tr>
		</thead>

		<tbody>

			<g:each in="${globalList}" status="i" var="globalList">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

					<td><input type="checkbox"
						name="globalIndicatorList"
						value="${fieldValue(bean: globalList, field: "id")}"></td>

					<td><g:remoteLink name="addIndiLink"
						action="addIndicator" controller="TNode" update="optionsBox"
						params="'report=${report}&snode=${snode}&globalIndicatorList=${globalList.id}&i_usage='+getIUsage()">
						<img src="${resource(dir:'images/schemaEditor',file:'add.png')}"
							title="${message(code: 'indicator.add.globallist', default: 'Add indicator')}"
							alt="${message(code: 'indicator.add.globallist', default: 'Add indicator')}" />
					</g:remoteLink></td>


					<td><g:link action="show" controller="indicator"
						id="${globalList.id}">
						${fieldValue(bean: globalList, field: "shortName")}
					</g:link></td>

					<td>

					<table>
						<tr>
							<td><g:if test="${globalList.isIndicator()}">
								<img
									src="${resource(dir:'images/schemaEditor/indicators',file:'indicator.png')}"
									title="${message(code: 'indicator.tip.label', default: 'Indicator')}"
									alt="${message(code: 'indicator.tip.label', default: 'Indicator')}" />
							</g:if> <g:if test="${!globalList.isIndicator()}">
								<img
									src="${resource(dir:'images/schemaEditor/indicators',file:'contentguideline.png')}"
									title="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}"
									alt="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}" />

							</g:if></td>
							<td><g:remoteLink action="showIndiDetails"
								controller="indicator" update="indicatorDetails"
								params="[indicatorId:globalList.id]">
								<g:indicatorProgress id="${globalList.id}" />
							</g:remoteLink></td>
						</tr>

					</table>


					</td>


				</tr>
			</g:each>
		</tbody>

	</table>


	<p><g:message code="i_usage.indicator.type.schema"
		default="Usage indicators" /><select name="i_usage" id="i_usage"
		onchange="getIUsage()">
		<g:if test="${i_usage=='1'}">
			<option value="1" selected><g:message code="indi_usage.optional.label"
		default="Optional" /></option>
			<option value="0"><g:message code="indi_usage.obligatory.label"
		default="Obligatory" /></option>
		</g:if>
		<g:if test="${i_usage=='0'}">
			<option value="1"><g:message code="indi_usage.optional.label"
		default="Optional" /></option>
			<option value="0" selected><g:message code="indi_usage.obligatory.label"
		default="Obligatory" /></option>
		</g:if>

		<g:if test="${!i_usage}">
			<option value="1"><g:message code="indi_usage.optional.label"
		default="Optional" /></option>
			<option value="0" selected><g:message code="indi_usage.obligatory.label"
		default="Obligatory" /></option>
		</g:if>


	</select></p>

	<input type="hidden" value="${snode}" name="snode" /> <input
		type="hidden" value="${report}" name="report" />

	<p>
	<div align="right"><g:submitToRemote id="addIndicator"
		action="addIndicator" name="addIndicator" controller="TNode"
		value="${message(code: 'indicator.add.label', default: 'Add')}" update="optionsBox" /></div>
	</p>
	</form>
</g:if>

<g:if test="${!globalList}">
	<p>- <g:message code="no.indicator.available"
		default="No available indicators" /> -</p>
</g:if>

<div id="indicatorDetails" name="indicatorDetails"></div>



