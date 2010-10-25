
<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="ReportEditorMain.ArticleEditor.ArtInd"%>
<%@ page defaultCodec="none" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="layout" content="admin" />
		<g:set var="entityName"
			value="${message(code: 'article.label', default: 'Article')}" />
		<title>
			<g:message code="article.list.label" args="[entityName]" />
		</title>
		<resource:autoComplete skin="default/autocomplete" />
	</head>
	<body>
		<div class="nav">
			  <span class="menuButton">
				<g:link class="list" controller="report" action="list">
					<g:message code="report.list" default="List Reports"/>
				</g:link>
			  </span>&nbsp;
		      <span class="menuButton">
		        <g:link class="list" controller="article" action="list">
		          <g:message code="artInd.list.article.list" default="List articles" />
		        </g:link>
		      </span>
		</div>

		<div 
			class="body">
			<h1>
				<g:message code="articleindicator.list.indicator" default="Indicators" />
			</h1>
			<h2>
				${session.report}
					
			</h2>

			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>

   			<div id="searchfilter">
   			<table>
   			<tr>
   			<form method="get">
		      		<td>
		      			<g:message code="artIndicator.list.indicator" default="Indicator" />
		      		</td>
		      		<td>
				        <richui:autoComplete name="searchfor" 
						maxResultsDisplayed="20" minQueryLength="3" value="${params.searchfor}"
						style="width:200px;" 
						action="${createLinkTo('dir': 'articleIndicator/autocomplete')}" />			
					</td>
					<td>
						<select name="article" size="1">
				          <option value="1">
				          	<g:message code="articleIndicator.list.article" default="Article" />
				          </option>
						
						  <g:each in="${articleFilterNames}" var="article">
							<option value="${article.id}"
				         		<g:if 
				          			test="${article==Article.get(params.article)}"  > selected 
				          		</g:if>>
				          		${article.name}
				         	  </option>
			         	  </g:each>
			         	
						</select>	
			       
		  	     		<input type="submit" value="Filter">
				    </form>
				</td>
				</tr>
   			</table>
			</div>
			
			<div class="float_clear"></div>
			
			<g:if test="${articleIndicatorInstanceList.size()>0}">
			
	   		<g:form action="actionSelect" name="form">
				<table class="list">
					<thead>
						<tr>
							<th>
								${message(code: 'articleindicator.list.table.name', default: 'Name')}
							</th>
							<th>
								${message(code: 'articleindicator.list.table.value', default: 'Value')}
							</th>
							<th>
								${message(code: 'articleindicator.list.table.unit', default: 'Unit')}
							</th>
							<th>
								${message(code: 'articleindicator.list.table.article', default: 'Article')}
							</th>
							<th>
								${message(code: 'articleindicator.list.table.status', default: 'Status')}
							</th>
						</tr>
					</thead>
				
					<tbody>
						<g:each in="${articleIndicatorInstanceList}" status="i" var="articleIndicatorInstance">
							<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
								<td>
								<g:link controller="indicator" action="show"
									id="${articleIndicatorInstance.indicator.id}">
									${fieldValue(bean: articleIndicatorInstance, field: "indicator.name")}
								</g:link>	
								</td>
								<td>
									${fieldValue(bean: articleIndicatorInstance, field: "indicator.value")}
								</td>
								<td>
									${fieldValue(bean: articleIndicatorInstance, field: "indicator.unit")}
								</td>
								<td>
								<g:link controller="article" action="edit"
									id="${articleIndicatorInstance.article.id}">
									${fieldValue(bean: articleIndicatorInstance, field: "article.name")}
								</g:link>
								</td>
								<td class="center">
									<g:if test="${articleIndicatorInstance.indicator.value=='' || articleIndicatorInstance.indicator.value==0}">
										<img src="${resource(dir:'images/ReportEditor',file:'closeStatus.png')}"
										title="${message(code: 'articleindicator.list.status.value.empty', default: 'value empty')}" alt="${message(code: 'articleindicator.list.status.value.empty', default: 'value empty')}" />
									</g:if>
									<g:else>
										<img src="${resource(dir:'images/ReportEditor',file:'releasedStatus.png')}"
										title="${message(code: 'articleindicator.list.action.value.set', default: 'value set')}" alt="${message(code: 'articleindicator.list.status.value.set', default: 'value set')}" />
									</g:else>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</g:form>
			<div id="caption">
			<table>
				<tr>
					<td><img src="${resource(dir:'images/ReportEditor',file:'closeStatus.png')}" /></td>
					<td><g:message code="indicator.status.not.ok" default="Indicator value not set" /></td>
				</tr>
				<tr>
					<td><img src="${resource(dir:'images/ReportEditor',file:'releasedStatus.png')}" /></td>
					<td><g:message code="indicator.status.ok" default="Indicator value set" /></td>
				</tr>
			</table>
			</div>
			</g:if>
		</div>
	</body>
</html>
