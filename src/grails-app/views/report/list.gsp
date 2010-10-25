<%@ page import="ReportEditorMain.ReportEditor.Report"%>
<%@ page import="ReportEditorMain.Enum.ReportStatus"%>
<%@ page import="systemadministration.usermanagement.User"%>
<%@ page import="shemaEditor.shemaAdministration.TReport"%>
<%@ page defaultCodec="none" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="admin" />
		<g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
			<title>
				<g:message code="report.list" args="[entityName]" />
			</title>
		<resource:autoComplete skin="default" />
	</head>
	<body>

		<div class="nav">
			<span class="menuButton">
				<g:link class="create" action="create">
					<g:message code="report.list.create.report" default="Create Report" />
				</g:link> 
			</span>
		</div>

		<div class="body">
			<h1>
				<g:message code="report.list.name" default="Reports" />
			</h1>
			<g:if test="${!tilde}">
				<g:if test="${flash.message}">
					<div class="message">${flash.message}</div>
				</g:if>
			</g:if>
			<g:if test="${tilde}">
				<g:if test="${flash.message}">
					<div class="message">${flash.message} <g:each in="${searchResults}" status="i" max="3" var="alternativeResult">
						<g:if test="${i<3}">
							<g:link controller="report" action="list" params="[searchfor:alternativeResult.name]">
									${alternativeResult.name}
								</g:link>, 
							</g:if>
					</g:each></div>
				</g:if>
			</g:if> 

			<div id="searchfilter">
				<table>	
				<tr>
				<g:form method="post">
					<td>Name: </td>
		        	<td><richui:autoComplete name="searchfor" 
						maxResultsDisplayed="20" minQueryLength="3" value="${params.searchfor}"
						style="width:200px;" 
						action="${createLinkTo('dir': 'report/autocomplete')}" />	
					</td>
					<td>
					<select name="scheme" size="1">
						<option value="1">
							<g:message code="report.schema.label" default="Scheme" />
						</option>
						<g:each in="${TReport.list()}" var="treport">
							<option value="${treport}"
								<g:if test="${treport.name==params.scheme}">
									selected 
								</g:if>>
								${treport}
							</option>
						</g:each>
					</select> 
					
					<select name="author" size="1">
						<option value="1">
							<g:message code="report.author.label" default="Author" />
						</option>
						<g:each in="${User.list()}" var="author">
							<option value="${author}"
								<g:if test="${author.username==params.author}"> 
									selected 
								</g:if>>
								${author}
							</option>
						</g:each>
					</select> 
					
					<select name="status" size="1">
						<option value="1">
							<g:message code="report.status.label" default="Status" />
						</option>
						<g:each in="${ReportStatus.list()}" var="status">
							<option value="${status}"
								<g:if test="${status.status==params.status}"> 
									selected 
								</g:if>>
								<g:message code="report.status.${status.toString().toLowerCase()}" default="${status.toString()}" />
							</option>
						</g:each>
					</select>
					
					<input type="submit" value="Filter">
					</td>
				</g:form>
				</tr>
				</table>
			</div>
			
			<div class="float_clear"></div>
			
			<g:if test="${!tilde&&reportInstanceList.size()>0}">
			<g:form action="actionSelect" name="form">
				<table class="list">
					<thead>
						<tr>
							<th style="width:11%;">
								<g:message code="report.action.label" default="Aktion" />
							</th>
							<th class="full">
								${message(code: 'report.name.label', default: 'Name')}
							</th>
							<th style="width:20%;">
								${message(code: 'report.scheme.label', default: 'Scheme')}
							</th>
							<th>
								${message(code: 'report.author.label', default: 'Author')}
							</th>
							<th>
								${message(code: 'report.status.label', default: 'Status')}
							</th>
						</tr>
					</thead>
				
					<tbody>
						<g:each in="${reportInstanceList}" status="i" var="reportInstance">
							<tr 
								class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>
									<g:link controller="article" action="list" params="[reportid:reportInstance.id]">	
										<img
											src="${resource(dir:'images/ReportEditor',file:'article.png')}"
											title="${message(code: 'report.list.img.article', default: 'List articles')}" alt="${message(code: 'report.list.img.article', default: 'List aticles')}" />
									</g:link> 
								
									<g:link controller="articleIndicator" action="list" params="[reportid:reportInstance.id]">
										<img src="${resource(dir:'images/ReportEditor',file:'indicator.png')}"
											title="${message(code: 'report.list.img.indicator', default: 'List indicators')}" alt="${message(code: 'report.list.img.indicator', default: 'List indicators')}" />
									</g:link> 
								
									<g:link action="edit" id="${reportInstance.id}">
										<img src="${resource(dir:'images/ReportEditor',file:'edit.png')}"
											title="${message(code: 'report.list.img.edit', default: 'Edit report')}" alt="${message(code: 'report.list.img.edit', default: 'Edit report')}" />
									</g:link> 
								</td>
								
								<td>
									<g:link action="show" id="${reportInstance.id}">
										${fieldValue(bean:reportInstance,field: "name")}
									</g:link>
								</td>
								
																
								<td>
									<g:link controller="TReport" action="show"
										id="${fieldValue(bean: reportInstance, field: 'treport.id')}">
										${fieldValue(bean: reportInstance, field: "treport")}
									</g:link>
								</td>
								
								<td>
									<g:link controller="user" action="show"
										id="${fieldValue(bean: reportInstance, field: 'author.id')}">
										${fieldValue(bean: reportInstance, field: "author")}
									</g:link>
								</td>
				
								<td class="center">
									<img src="${resource(dir:reportInstance.status.imageDir,file:reportInstance.status.imageFile)}"
									title='<g:message code="report.status.${reportInstance.status.toString().toLowerCase()}" default="${reportInstance.status.toString()}" />' />
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>

				<div id="caption">
					<table>
						<g:each in="${ReportStatus.list()}" var="status">
							<option value="${status}">
								<tr>
									<td>
										<img src="${resource(dir:status.imageDir,file:status.imageFile)}"/>
									</td>
									<td>
										<g:message code="report.status.${status.toString().toLowerCase()}" default="${status.toString()}" />
									</td>
								</tr>
							</option>
						</g:each>
					</table>
				</div>
			</g:form>
			</g:if>
		</div>
	</body>
</html>
