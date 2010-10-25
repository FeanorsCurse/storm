
<%@ page import="ReportEditorMain.ArticleEditor.Article" %>
<%@ page import="storm.ReportTagLib" %>
<%@ page import="systemadministration.recommender.WelcomeService" %>
<%@ page defaultCodec="none" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'article.label', default: 'Article')}" />
  <g:set var="entityID" value="${articleInstance.id}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
  <script type="text/javascript" src="/Storm/js/flvplayer/flvplayer-1.9.7b/flvplayer/swfobject.js"></script>
  <script type="text/javascript" src="${resource(dir:'js/',file:'embed.js')}" ></script>
  <resource:richTextEditor type="simple"/>
  <g:javascript library="yui" />
</head>
<body>
  <div class="navbutton" style="float: right; font-size: small">
    <g:link controller="infocart" action="addArticle" params="[id:articleInstance.id]">
      <img src="${resource(dir:'images/oxygen',file:'document-new.png')}" title="${message(code: 'infocart.add.article')}" alt="${message(code: 'infocart.add.article')}"/>
    </g:link>
    <g:link action="pdf" params="[id:articleInstance.id,prev:'0']">
      <img src="${resource(dir:'images/oxygen',file:'application-pdf.png')}" title="${message(code: 'article.pdf')}" alt="${message(code: 'article.pdf')}"/>
    </g:link>
    <g:link action="xml" params="[id:articleInstance.id]">
      <img src="${resource(dir:'images/oxygen',file:'text-xml.png')}" title="${message(code: 'article.xml')}" alt="${message(code: 'article.xml')}"/>
    </g:link>
  </div>
  <div class="breadcrumps" style="float: left; font-size: small">
    <g:breadcrumb article="${articleInstance}"/>
  </div>
  <br/>
  <div class="body">
    <g:if test="${flash.message}">
    <br/>
    <br/>
      <div class="info">
        <img src="${resource(dir:'images/oxygen',file:'dialog-information.png')}" title="Info Message" alt="Info Message"/>
        &nbsp;
        ${flash.message}
      </div>
    </g:if>
    <div id="rating" style="float:right">
        <rateable:resources/><rateable:ratings bean='${articleInstance}' />
    </div>
    <div id="articlename"><h1>${articleInstance.number} ${articleInstance.name}</h1>
    </div>

    <div id="articlecontent">${articleInstanceContent}</div>
    <div id="articleindicators">
        <h2><g:message code="article.indicator.label" default="Indicators" /></h2>
        <g:if test="${artIndList.size>0 }">
	        <table class="list">
	        <tr>
	                <th>Indikator</th>
	                <th>Wert</th>
	                <th>Einheit</th>
	        </tr>
	            <g:each in="${artIndList}" status="i" var="artInd">
	
	                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
	                    <td><g:link controller="indicator" action="show" id="${artInd?.indicator?.id}">        <g:indicatorProgress id="${artInd?.indicator?.id}"/></g:link></td>
	                    <td>${artInd?.value}</td>
	                    <td>${artInd?.indicator?.unit}</td>
	                </tr>
	            </g:each>
	     	</table>
     	</g:if>
     	<g:else>
     		<g:message code="article.display.no.indicators" default="No Indicators available" />
     	</g:else>
    </div>
    <div id="articletags" style="font-size: small">
        <h2><g:message code="default.tags.label" default="Tags" /></h2>
        <p>
          <g:if test="${articleInstance?.tags?.size() == 0}">
            <g:message code="article.display.no.tags" default="No tags assigned" />
          </g:if>
          <g:else>
            <g:each in="${articleInstance?.tags}" var="tag">
              <g:link action="findByTag" params="[tag:tag]">${tag.encodeAsHTML()}</g:link>
            </g:each>
          </g:else>
        </p>
        <g:if test="${session?.user?.username != 'Anonym'}">
        <g:form>
          <g:textField name="tag" maxlength="72" />
          <g:hiddenField name="id" value="${articleInstance?.id}" />
          <span class="button"><g:actionSubmit class="save" action="addTag" value="${message(code: 'display.button.addTag.label', default: 'Add Tag')}" /></span>
        </g:form>
        </g:if>
    </div>
    <g:if test="${articleRecommendationList}">
   <div id="articlerecommender">
            <h2>Weitere interessante Artikel</h2>
             <table class="list">
              <g:each in="${articleRecommendationList}" status="i" var="recommendationarticle">
               <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>
                    ${WelcomeService.getArticleInfos(recommendationarticle)}
                    <g:link controller="article" action="display" id="${recommendationarticle?.id}">mehr...</g:link>
                    </td>
               </tr>
            </g:each>
            </table>
    </div>
   </g:if>
   <g:include controller="usercomment" action="listarticlecomments" id="${articleInstance?.id}" />
    <div>
            <h2><g:message code="article.socialnetworks.label" default="Social Networks"/></h2>
            <g:render template="/layouts/components/bookmarks" />
    </div>
  </div>
</body>
</html>