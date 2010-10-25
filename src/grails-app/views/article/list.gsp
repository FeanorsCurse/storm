<%@ page import="ReportEditorMain.ArticleEditor.Article"%>
<%@ page import="ReportEditorMain.ArticleEditor.ArtInd"%>
<%@ page import="ReportEditorMain.Enum.ArticleStatus"%>
<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<%@ page import="systemadministration.usermanagement.User"%>
<%@ page defaultCodec="none"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="admin" />
<title><g:message code="article.list" default="Article List" /></title>
<resource:autoComplete skin="default/autocomplete" />
</head>

<body>
<div class="nav"><span class="menuButton"> <g:link class="list" controller="report" action="list">
	<g:message code="article.list.reports.list" default="List Reports" />
</g:link> </span>&nbsp; <span class="menuButton"> <g:link class="create" action="create">
	<g:message code="article.list.create.article" default="Create Article" />
</g:link> </span></div>

<div class="body">
<h1><g:message code="article.list" default="Article List" /></h1>
<h2>${reportName}</h2>

<g:if test="${!tilde}">
	<g:if test="${flash.message}">
		<div class="message">${flash.message}</div>
	</g:if>
</g:if>
<g:if test="${tilde}">
	<g:if test="${flash.message}">
		<div class="message">${flash.message} <g:each in="${searchResults}" status="i" max="3" var="alternativeResult">
			<g:if test="${i<3}">
				<g:link controller="article" action="list" params="[searchfor:alternativeResult.name]">
						${alternativeResult.name}
					</g:link>, 
				</g:if>
		</g:each></div>
	</g:if>
</g:if> 

<div id="searchfilter">
<table>
	<tr>
		<form method="post">
		<td><g:message code="article.list.content" default="Content" /></td>
		<td><richui:autoComplete name="searchfor" maxResultsDisplayed="20" minQueryLength="3" value="${params.searchfor}" style="width:200px;" action="${createLinkTo('dir': 'article/autocomplete')}" /></td>
		<td><select name="author" size="1">
			<option value="1"><g:message code="article.author.label" default="Author" /></option>
			<g:each in="${User.list()}" var="author">
				<option value="${author}" <g:if 
	          			test="${author.username==params.author}"> selected 
	          		</g:if>>${author}</option>
			</g:each>
		</select> <select name="status" size="1">
			<option value="1"><g:message code="article.status.status" default="Status" /></option>
			<g:each in="${ArticleStatus.list()}" var="status">
				<option value="${status}" <g:if
		            	test="${status.status==params.status}">	selected 
		          	</g:if>><g:message code="article.status.${status.toString().toLowerCase()}" default="${status.toString()}" /></option>
			</g:each>
		</select> <input type="submit" value="Filter">
		</form>
		</td>
	</tr>
</table>
</div>
<div class="float_clear"></div>

<g:if test="${!tilde&&articleInstanceList.size()>0}">
	<g:form action="actionSelect" name="form">
		<table class="list">
			<thead>
				<tr>
					<th><input type="checkbox" name="checkall" onclick="checkAll()" /></th>
					<th><g:message code="article.action.label" default="Aktion" /></th>
					<th>${message(code: 'article.name.label', default: 'Name')}</th>
					<th>${message(code: 'article.author.label', default: 'Author')}</th>
					<th>${message(code: 'article.status.label', default: 'Status')}</th>
				</tr>
			</thead>
			<tbody>
				<g:each in="${articleInstanceList}" status="i" var="articleInstance">
					<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
						<td><input type="checkbox" name="checkbox" value="${articleInstance.id}" id="${articleInstance.id}" /></td>
						<td style="width: 10%;">
						<g:if test="${ArtInd.findAllByArticle(articleInstance)?.size()>0 }">
							<g:link controller="articleIndicator" action="list" params="[articleid:articleInstance.id]">
								<img src="${resource(dir:'images/ReportEditor',file:'indicator.png')}" title="${message(code: 'article.list.img.indicator', default: 'List indicators')}" alt="${message(code: 'article.list.indicator.list', default: 'List indicators')}" />
							</g:link>
						</g:if>
						<g:else>
							<img src="${resource(dir:'images/ReportEditor',file:'noindicator.png')}" title="${message(code: 'article.list.img.no.indicator', default: 'No indicators for this article')}" alt="${message(code: 'article.list.img.no.indicator', default: 'No indicator for this article')}" />
						</g:else>	
						 <g:link action="preview" id="${articleInstance.id}">
							<img src="${resource(dir:'images/ReportEditor',file:'show.png')}" title="${message(code: 'article.list.show.article', default: 'Preview article')}" alt="${message(code: 'article.list.show.article', default: 'Preview article')}" />
						</g:link> <!-- if article is locked, exclamation mark is shown and article cannot be edited. otherwise edit-symbol is shown and article can be edited  --> <g:if test="${articleInstance.lockTimestamp+6*60*60>(System.currentTimeMillis() / 1000) && session.user.id!=articleInstance.lockUser.id}">
							<img src="${resource(dir:'images/ReportEditor',file:'warning.png')}" title="${message(code: 'article.list.locked.article', default: 'Article locked', args:[articleInstance.lockUser.username])}" alt="${message(code: 'article.list.locked.article', default: 'Article locked', args:[articleInstance.lockUser.username])}" />
						</g:if> <g:else>
							<g:link action="edit" id="${articleInstance.id}">
								<img src="${resource(dir:'images/ReportEditor',file:'edit.png')}" title="${message(code: 'article.list.edit.article', default: 'Edit article')}" alt="${message(code: 'article.list.edit.article', default: 'Edit article')}" />
							</g:link>
						</g:else>

						<td style="width: 100%;"><g:link action="show" id="${articleInstance.id}">
							${fieldValue(bean:articleInstance,field: "number")}
							${fieldValue(bean:articleInstance,field: "name")}
	        			</g:link></td>

						<td><g:link controller="user" action="show" id="${fieldValue(bean: articleInstance, field: 'author.id')}">
						${fieldValue(bean: articleInstance, field: "author")}
	        		</g:link></td>

						<td class="center"><img src="${resource(dir:articleInstance.status.imageDir,file:articleInstance.status.imageFile)}" title='<g:message code="article.status.${articleInstance.status.toString().toLowerCase()}" default="${articleInstance.status.toString()}" />' /></td>
					</tr>
				</g:each>
			</tbody>
		</table>
		<select name="actions" size="1">
			<option value="0"><g:message code="article.list.actions.choose" default="Choose Action..." /></option>
			<option value="1"><g:message code="article.list.actions.copy" default="Copy selected Articles" /></option>
			<option value="2"><g:message code="article.list.actions.delete" default="Close selected Articles" /></option>
			<option value="3"><g:message code="article.list.actions.development" default="Open selected Articles" /></option>
		</select>
		<input type="submit" value="${message(code: 'article.list.actions.execute', default: 'Execute')}" />
	</g:form>
	<div id="caption">
	<table>
		<g:each in="${ArticleStatus.list()}" var="status">
			<option value="${status}">
			<tr>
				<td><img src="${resource(dir:status.imageDir,file:status.imageFile)}" /></td>
				<td><g:message code="article.status.${status.toString().toLowerCase()}" default="${status.toString()}" /></td>
			</tr>
			</option>
		</g:each>
	</table>
	</div>
</g:if>



</div>
</body>
</html>
