
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>

<%@page import="ReportEditorMain.ReportEditor.Report"%><html>
<head>
<resource:richTextEditor type="medium" />
<meta name="layout" content="admin" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
<g:set var="entityName"
	value="${message(code: 'indicator.label', default: 'Indicator')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<%@ page defaultCodec="none"%>

</head>
<body>
<g:checkAccess modulname="indicator" actionname="delete">
<div id="edit" align="right">


<g:link action="delete"
	params="[id:indicatorInstance.id]">
	
	<g:if test="${indicatorInstance.isIndicator()}">
	<g:message code="indicator.delete.label" default="Delete indicator" />
	</g:if><g:else>
	<g:message code="guideline.delete.label" default="Delete indicator" />
	</g:else>
</g:link></div>

</g:checkAccess>
<div id="indicatorDetails" name="indicatorDetails" style="width: 50%;">

<div id="topichead"><h7>
${fieldValue(bean: indicatorInstance, field: "name")} <g:if
	test="${indicatorInstance.isIndicator()}"><g:message code="indicator.tooltip.label" default="Indicator" />
</g:if> <g:if test="${!indicatorInstance.isIndicator()}"><g:message code="guideline.tooltip.label" default="Content-Guideline" />
</g:if></h7> <g:if test="${flash.message}">
	<div class="message">
	${flash.message}
	</div>
</g:if> 
<g:if test="${flash.info}">
		<div class="info">
		${flash.info} 
		</div>
</g:if>

<div id="edit" align="right">
<g:checkAccess modulname="indicator" actionname="edit">
<g:remoteLink action="edit"
	controller="indicator" update="indibg"
	params="[id:indicatorInstance.id]">
	<g:message code="indicator.edit.details" default="Edit" />
</g:remoteLink>
</g:checkAccess>
</div>

</div>
</div>

<table>

	<tr>
		<td width="54%" valign="top">

		<div id="indibg"
			style="background: #FFFFE1; margin-right: 10%; padding: 10px;">

		<table style="border-spacing: 10px;" width="100%">
			<tbody>



				<tr>
					<td valign="top" align="left" class="name" width="35%"><g:message
						code="indicator.description.label" default="Description" /></td>

					<td valign="top" align="left" class="value" width="65%">
					${fieldValue(bean: indicatorInstance, field: "description")}
					</td>

				</tr>


				<tr>
					<td valign="top" align="left" class="name"><g:message
						code="indicator.creationDate.label" default="Creation Date" /></td>

					<td valign="top" align="left" class="value"><g:formatDate
						format="dd.MM.yyyy HH:mm:ss"
						date="${indicatorInstance?.creationDate}" /></td>

				</tr>

				<g:if test="${indicatorInstance.isIndicator()}">
				<tr>
					<td valign="top" align="left" class="name"><g:message
						code="indicator.unit.label" default="Unit" /></td>

					<td valign="top" align="left" class="value">
					${fieldValue(bean: indicatorInstance, field: "unit")}
					</td>

				</tr>

				</g:if>

				<tr>
					<td valign="top" align="left" class="name"><g:message
						code="indicator.category.label" default="Category" /></td>

					<td valign="top" align="left" class="value">
				
				<g:if test="${session.user.username !='Anonym' && session.user.username !='User'}">	
					<g:link	controller="category" action="show"id="${indicatorInstance?.category?.id}">
					${indicatorInstance?.category?.encodeAsHTML()}
					</g:link>
				</g:if><g:else>
				${indicatorInstance?.category?.encodeAsHTML()}
				</g:else>
					</td>

				</tr>


				<tr>
					<td valign="top" align="left" class="name"><g:message
						code="indicator.creator.label" default="Creator" /></td>

					<td valign="top" align="left" class="value">
					
					<g:if test="${session.user.username !='Anonym'}">	
					<g:link
						controller="user" action="show"
						id="${indicatorInstance?.creator?.id}">
						${indicatorInstance?.creator?.encodeAsHTML()}
					</g:link>
					</g:if><g:else>
					${indicatorInstance?.creator?.encodeAsHTML()}
					</g:else>
					
					</td>
				</tr>

<g:checkAccess modulname="indicator" actionname="edit">
				<tr>
					<td colspan="2"
					
					
					><g:message
						code="indicator.lastModifiedBy.label" default="Last modified by " />
					
					<g:link controller="user" action="show"
						id="${indicatorInstance?.lastModifiedBy?.id}">
						${indicatorInstance?.lastModifiedBy?.encodeAsHTML()}
					</g:link> am <g:formatDate format="dd.MM.yyyy HH:mm:ss" date="${new Date()}" />
	</g:checkAccess>			
					
					</td>
					<td></td>
				</tr>

				<g:if test="${categoryInstance.indicators && indicatorInstance.isIndicator()}">
					<tr>


						<td colspan="2"><b><g:message
							code="indicator.category.recommendation"
							default="Further indicators" /></b><br>
						<g:each in="${categoryInstance.indicators}" var="i">
							<br>- <g:link controller="indicator" action="show"
								id="${i.id}">
								<g:indicatorProgress id="${i.id}" />
							</g:link>
						</g:each></td>
						<td></td>
					</tr>
				</g:if>
			</tbody>
		</table>

		</div>

		</td>

		<td valign="top"><g:if test="${indicatorInstance.isIndicator()}">
			<div id="chartView" name="chartView"><g:include action="chart"
				controller="indicator" id="${indicatorInstance.id}" /></div>
		</g:if></td>
	</tr>
</table>

<table width="90%">
	<tr>

		<td width="60%" valign="top"><g:if
			test="${indicatorInstance.isIndicator()}">

			<div id="relevance" name="relevance" style="width: 90%;">
			<div id="topichead"><h8><g:message
				code="indicator.relevance.titel" /></h8>
			<div id="edit" align="right">
			<g:checkAccess modulname="indicator" actionname="edit">
			<g:remoteLink
				action="editDefinitions" controller="indicator" update="relevance"
				params="[id:indicatorInstance.id, section:1]">
				<g:message code="indicator.edit.details" default="Edit" />
			</g:remoteLink>
			</g:checkAccess>
			</div>
			</div>

			${fieldValue(bean: indicatorInstance, field: "relevance").decodeHTML()}
			<g:if test="${!indicatorInstance?.relevance}">
				<g:message code="indicator.relevance.message" />"${indicatorInstance?.name}"</g:if>
			</div>
		</g:if> <g:if test="${indicatorInstance.isIndicator()}">
			<div id="compilation" name="compilation" style="width: 90%;">
			<div id="topichead"><h8><g:message
				code="indicator.compilation.titel" /></h8>
				
			<div id="edit" align="right">
			<g:checkAccess modulname="indicator" actionname="edit">
			<g:remoteLink
				action="editDefinitions" controller="indicator" update="compilation"
				params="[id:indicatorInstance.id, section:2]">
				<g:message code="indicator.edit.details" default="Edit" />
			</g:remoteLink>
			</g:checkAccess>
			</div>
			</div>
			${fieldValue(bean: indicatorInstance, field: "compilation").decodeHTML()}
			<g:if test="${!indicatorInstance?.compilation}">
				<g:message code="indicator.compilation.message" />"${indicatorInstance?.name}"</g:if>
			</div>

		</g:if>

		<div id="documentation" name="documentation" style="width: 90%;">
		<div id="topichead"><h8><g:message
			code="indicator.documentation.titel" /></h8>
		<div id="edit" align="right">
		<g:checkAccess modulname="indicator" actionname="edit">
		<g:remoteLink
			action="editDefinitions" controller="indicator"
			update="documentation" params="[id:indicatorInstance.id, section:3]">
			<g:message code="indicator.edit.details" default="Edit" />
		</g:remoteLink>
		</g:checkAccess>
		</div>
		</div>
		${fieldValue(bean: indicatorInstance, field: "documentation").decodeHTML()}
		<g:if test="${!indicatorInstance?.documentation}">
			<g:message code="indicator.documentation.message" />"${indicatorInstance?.name}"</g:if>
		</div>
		<div id="reference" name="reference" style="width: 90%;">
		<div id="topichead"><h8><g:message
			code="indicator.reference.titel" /></h8>
		<div id="edit" align="right">
		<g:checkAccess modulname="indicator" actionname="edit">
		<g:remoteLink
			action="editDefinitions" controller="indicator" update="reference"
			params="[id:indicatorInstance.id, section:4]">
			<g:message code="indicator.edit.details" default="Edit" />
		</g:remoteLink>
		</g:checkAccess>
		</div>
		</div>
		${fieldValue(bean: indicatorInstance, field: "reference").decodeHTML()}
		<g:if test="${!indicatorInstance?.reference}">
			<g:message code="indicator.reference.message" />"${indicatorInstance?.name}" </g:if>
		</div>
		</td>

		<td valign="top">
		<div id="definition" name="definition">
		<div id="topichead"><h8><g:message
			code="indicator.definition.titel" /></h8>
		<div id="edit" align="right">
		<g:checkAccess modulname="indicator" actionname="edit">
		<g:remoteLink
			action="editDefinitions" controller="indicator" update="definition"
			params="[id:indicatorInstance.id, section:5]">
			<g:message code="indicator.edit.details" default="Edit" />
		</g:remoteLink>
		</g:checkAccess>
		</div>
		</div>
		${fieldValue(bean: indicatorInstance, field: "definition").decodeHTML()}
		<g:if test="${!indicatorInstance?.definition}">
			<g:message code="indicator.definition.message" />"${indicatorInstance?.name}" </g:if>
		</div>



		</td>
	</tr>
</table>



</body>
</html>
