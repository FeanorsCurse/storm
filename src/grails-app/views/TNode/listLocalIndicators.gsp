<b><g:message code="indicators.allready.added.list"
	default="Added indicators" /></b>
<p>
<div id="key"><img
	src="${resource(dir:'images/ReportEditor',file:'closeStatus.png')}"
	alt="${message(code: 'i_usage.obligatory.image', default: 'Obligatory indicator')}" />
<g:message code="i_usage.obligatory.image"
	default="Obligatory Indicator" /><br>
<img
	src="${resource(dir:'images/ReportEditor',file:'releasedStatus.png')}"
	alt="${message(code: 'i_usage.optional.image', default: 'Optional indicator')}" />
<g:message code="i_usage.optional.image" default="Optional Indicator" />
</div>
</p>
<g:if test="${nodeIndicators}">
	<p>

	<form name="indi_remove" id="indi_remove">
	<table class="list">
		<thead>
			<tr>
				<th><input type="checkbox"
					onClick="CheckLocal(document.indi_remove.localIndicatorList)"
					name="indiCheckCtrl"></th>
				<th></th>

				<th>
				${message(code: 'indicator.shortName.label', default: 'Short')}
				</th>
				<th>
				${message(code: 'indicator.name.label', default: 'Name')}
				</th>
				<th>
				${message(code: 'indicator.usage.label', default: 'Usage')}
				</th>
			</tr>
		</thead>
		<tbody>

			<g:each in="${nodeIndicators}" status="i" var="nodeIndicators">
				<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

					<td><input type="checkbox" name="localIndicatorList"
						value="${fieldValue(bean: nodeIndicators, field: "id")}">
					</td>
					<td><g:remoteLink action="deleteIndicator" controller="TNode"
						update="optionsBox"
						params="[snode:snode, nodeIndicatorList:nodeIndicators.id, report:report]">
						<img
							src="${resource(dir:'images/schemaEditor',file:'remove.png')}"
							title="${message(code: 'Indicator.shortName.label', default: 'Remove indicator')}"
							alt="${message(code: 'Indicator.shortName.label', default: 'Remove indicator')}" />
					</g:remoteLink></td>

					<td><g:link action="show" controller="indicator"
						id="${nodeIndicators.id}">
						${fieldValue(bean: nodeIndicators, field: "shortName")}
					</g:link></td>

					<td>

					<table>
						<tr>
							<td><g:if test="${nodeIndicators.isIndicator()}">
								<img
									src="${resource(dir:'images/schemaEditor/indicators',file:'indicator.png')}"
									title="${message(code: 'indicator.tip.label', default: 'Indicator')}"
									alt="${message(code: 'indicator.tip.label', default: 'Indicator')}" />
							</g:if> <g:if test="${!nodeIndicators.isIndicator()}">
								<img
									src="${resource(dir:'images/schemaEditor/indicators',file:'contentguideline.png')}"
									title="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}"
									alt="${message(code: 'guideline.tip.label', default: 'Content-Guideline')}" />

							</g:if></td>
							<td><g:link action="show" controller="indicator"
								id="${nodeIndicators.id}">
								<g:indicatorProgress id="${nodeIndicators.id}" />
							</g:link></td>
						</tr>

					</table>


					</td>

					<td><g:if test="${isObligatoryList.get(i)}">
						<img
							src="${resource(dir:'images/ReportEditor',file:'closeStatus.png')}"
							title="${message(code: 'i_usage.obligatory.image', default: 'Obligatory Indicator')}"
							alt="${message(code: 'i_usage.obligatory.image', default: 'Obligatory indicator')}" />
					</g:if> <g:if test="${!isObligatoryList.get(i)}">
						<img
							src="${resource(dir:'images/ReportEditor',file:'releasedStatus.png')}"
							title="${message(code: 'i_usage.optional.image', default: 'Optional Indicator')}"
							alt="${message(code: 'i_usage.optional.image', default: 'Optional indicator')}" />
					</g:if></td>
				</tr>
			</g:each>

		</tbody>
	</table>
	</p>
	<p>
	<div align="right"><select name="i_usage">
		<option value="4"><g:message code="choose.action.label"
			default="-Choose Action-" /></option>
		<option value="0"><g:message
			code="indi_usage.obligatory.label" default="Obligatory" /></option>
		<option value="1"><g:message code="indi_usage.optional.label"
			default="Optional" /></option>
		<option value="2"><g:message code="indicator.delete.label"
			default="Delete" /></option>
	</select> <g:submitToRemote id="indi_action" action="updateIndicatorUsage"
		name="indi_action" controller="TNode"
		value="${message(code: 'indicator.save.label', default: 'Save')}"
		update="optionsBox" /></div>
	<input type="hidden" value="${snode}" name="snode" /> <input
		type="hidden" value="${report}" name="report" id="report" /></p>
	</form>
</g:if>

<g:if test="${!nodeIndicators}">
	<p><g:message code="noindicators.node.list"
		default="None indicators" /></p>
</g:if>

