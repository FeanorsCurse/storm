
<%@ page import="interactiveFeatures.Infocart.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'infocart.label', default: 'Infocart')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" style="float: right; font-size: small">
          <g:checkAccess modulname="infocart" actionname="save">
            <g:link action="save">
              <img src="${resource(dir:'images/oxygen',file:'document-save.png')}" title="${message(code: 'menu.infocart.save', default: 'Save Infocart')}" alt="${message(code: 'menu.infocart.save', default: 'Save Infocart')}"/>
            </g:link>
          </g:checkAccess>
          <g:checkAccess modulname="infocart" actionname="edit">
            <g:link action="edit" id="${infocartInstance.id}">
              <img src="${resource(dir:'images/oxygen',file:'document-edit.png')}" title="${message(code: 'menu.infocart.edit', default: 'Edit Infocart')}" alt="${message(code: 'menu.infocart.edit', default: 'Edit Infocart')}"/>
            </g:link>
          </g:checkAccess>
          <g:checkAccess modulname="infocart" actionname="delete">
            <g:link action="delete" id="${infocartInstance?.id}">
              <img src="${resource(dir:'images/oxygen',file:'document-close.png')}" title="${message(code: 'menu.infocart.delete', default: 'Delete Infocart')}" alt="${message(code: 'menu.infocart.delete', default: 'Delete Infocart')}"/>
            </g:link>
          </g:checkAccess>
            <g:link action="showAsOnePage" id="${infocartInstance.id}">
              <img src="${resource(dir:'images/oxygen',file:'document-preview.png')}" title="${message(code: 'menu.infocart.show.onepage', default: 'Infocart content on a single page')}" alt="${message(code: 'menu.infocart.show.onepage', default: 'Infocart content on a single page')}"/>
            </g:link>
            <g:link action="toPdf" id="${infocartInstance.id}">
              <img src="${resource(dir:'images/oxygen',file:'application-pdf.png')}" title="${message(code: 'menu.infocart.show.pdf', default: 'Infocart as PDF')}" alt="${message(code: 'menu.infocart.show.pdf', default: 'Infocart as PDF')}"/>
            </g:link>
            <g:link action="xml" id="${infocartInstance.id}">
              <img src="${resource(dir:'images/oxygen',file:'text-xml.png')}" title="${message(code: 'menu.infocart.show.xml', default: 'Infocart as XML')}" alt="${message(code: 'menu.infocart.show.xml', default: 'Infocart as XML')}"/>
            </g:link>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">
              <img src="${resource(dir:'images/oxygen',file:'dialog-information.png')}" title="Info Message" alt="Info Message"/>
              &nbsp;
              ${flash.message}
            </div>
            </g:if>
            <h2><g:message code="article.articles.label" default="Articles" /></h2>
            <g:if test="${infocartItemList.size() != 0}">
              <div class="list">
                <table width="100%">
                  <tr>
                    <th align="left">${message(code: 'default.name.label', default: 'Name')}</th>
                    <th align="left">${message(code: 'default.comments.label', default: 'Comments')}</th>
                    <th align="left" width="100">${message(code: 'default.rating.label', default: 'Rating')}</th>
                    <th>&nbsp;</th>
                  </tr>
                    <g:each in="${infocartItemList}" status="i" var="item">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                          <td><g:link controller="article" action="display" id="${item?.article?.id}">${item?.article?.number} ${item?.article?.name}</g:link></td>
                          <td align="center">${item?.article?.usercomments?.size()}</td>
                          <td align="left" width="100"><g:include controller="article" action="starsrated" id="${item?.article?.id}"/></td>
                          <td align="right" width="80">
                            <g:link controller="article" action="pdf" params="[id:item?.article?.id,prev:'0']"><img src="${resource(dir:'images/oxygen',file:'application-pdf.png')}" title="${message(code: 'article.pdf')}" alt="${message(code: 'article.pdf')}"/></g:link>
                            <g:link controller="article" action="xml" params="[id:item?.article?.id]"><img src="${resource(dir:'images/oxygen',file:'text-xml.png')}" title="${message(code: 'article.xml')}" alt="${message(code: 'article.xml')}"/></g:link>
                            <g:link controller="infocart" action="deleteItem" id="${item?.id}"><img src="${resource(dir:'images/oxygen',file:'document-close.png')}" title="${message(code: 'infocart.removearticle')}" alt="${message(code: 'infocart.removearticle')}"/></g:link>
                          </td>
                        </tr>
                    </g:each>
                </table>
              </div>
            </g:if>
            <g:else>
              <g:message code="infocart.isempty.label" default="Your Infocart is empty" />
            </g:else>
        </div>
    </body>
</html>
