<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="admin" />
		<title>
			<g:message code="articleindicator.allocate.allocate" default="Allocate indicators" />
		</title>
		<resource:autoComplete skin="default/autocomplete" />
	</head>
	<body>
		<div class="nav">
			<span class="menuButton">
				<g:link class="list" controller="report" action="list">
					<g:message code="articleindicator.allocate.report" default="Reports"/>
				</g:link>
			</span>&nbsp;
			<span class="menuButton">
				<g:link class="list" controller="article" action="list">
					<g:message code="articleindicator.allocate.article" default="List articles"/>
				</g:link>
			</span>
		</div>

		<div class="body">
			<h1>
				<g:message code="articleindicator.allocate.allocate" default="Allocate indicators" />
			</h1>
			
			<h2>
				${message(code: 'articleindicator.allocate.table.article', default: 'Article')}
					"${articleInstance.name}"
			</h2>
			
			<g:if test="${flash.message}">
				<div class="message">${flash.message}
				</div>
			</g:if> 
			<g:hasErrors bean="${indicatorInstance}">
				<div class="errors">
					<g:renderErrors bean="${indicatorInstance}" as="list" />
				</div>
			</g:hasErrors>

			<div id="searchfilter">
				<table>
				  <tr>
				  	<td>Indicator: </td>
				  	<td>
		      		<form method="get">
				        <richui:autoComplete name="searchfor" 
						maxResultsDisplayed="20" minQueryLength="3" value="${params.searchfor}"
						style="width:200px;" 
						action="${createLinkTo('dir': 'articleIndicator/autocomplete')}" />			
		  	     	</td>	
		  	     	<td><input type="submit" value="Filter"></td>
				    </form>
				    </tr>
				</table>
			</div>
			
			<div class="float_clear"></div>
			
			<div id="indicatorList">
				<g:form name="form" action="updateAllocation" method="post">
				<g:hiddenField name="id" value="${articleInstance?.id}" />
					
					<table class="list">
					<thead>
						<tr>
							<th>
								<input type="checkbox" name="checkall" onclick="checkAll()" />
							</th>
							<th>
								${message(code: 'articleindicator.allocate.table.name', default: 'Name')}
							</th>
							<th>
								${message(code: 'articleindicator.allocate.table.description', default: 'Description')}
							</th>
							<th>
								${message(code: 'articleindicator.allocate.table.unit', default: 'Unit')}
							</th>
						</tr>
					</thead>
					<tbody>
						<g:each in="${indicatorInstanceList}" status="i" var="indicatorInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>
									<input type="checkbox" name="checkbox" value="${indicatorInstance.id}" id="${indicatorInstance.id}" />
								</td>
								<td>
									<g:link controller="indicator" action="show"
										id="${fieldValue(bean: indicatorInstance, field: 'id')}">
										${fieldValue(bean: indicatorInstance, field: "name")}
									</g:link>	
									</td>
								<td>
									${fieldValue(bean: indicatorInstance, field: "description")}
								</td>
								<td>
									${fieldValue(bean: indicatorInstance, field: "unit")}
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			<div class="buttons">
				<span class="button">
					<g:submitButton name="updateAllocation" action="updateAllocation"
						value="${message(code: 'articleindicator.allocate.button.allocate', default: 'Allocate marked indicators')}" />
				</span>
				<span class="button">
					<g:actionSubmit name="cancel" action="cancel" id="${articleInstance.id}"
						value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" />
				</span>
				<span class="button">
					<g:actionSubmit name="createPrivateIndicator" action="createPrivateIndicator" 
						value="${message(code: 'articleindicator.allocate.button.new', default: 'Create new Indicator')}" />
				</span>
			</div>
		</g:form>
	</div>
</body>
</html>
