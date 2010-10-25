<%@ page import="ReportEditorMain.ReportEditor.Report" %>
<%@ page defaultCodec="none" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'report.label', default: 'Report')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        
        <div class="body">
        <div id="rating" style="float:right">
        
        <div id="sd" style="float:right">
        <g:link action="toPdf" params="[id:reportInstance.id]">
      		<img src="${resource(dir:'images/oxygen',file:'application-pdf.png')}" title="${message(code: 'infocart.download.pdf')}" alt="${message(code: 'article.pdf')}"/>
   		</g:link><br></br>
   		</div>
   		
        <rateable:resources/><rateable:ratings bean='${reportInstance}' />
    </div>

 <h1>${fieldValue(bean: reportInstance, field: "name")}</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
            ${reportInstance.description}  
            </div>
           <div id="reportindicators">
           <g:if test="${artIndList.size>0 }">
            <h2><g:message code="report.display.indicator" default="Indicators" /></h2>
		        <table class="list">
		        <tr>
		                <th><g:message code="report.display.indicators.indicator" default="Indicator" /></th>
		                <th><g:message code="report.display.indicators.value" default="Value" /></th>
		                <th><g:message code="report.indicator.unit" default="Unit" /></th>
		        </tr>
		            <g:each in="${artIndList}" status="i" var="artInd">
		                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
		                    <td><g:link controller="indicator" action="show" id="${artInd?.indicator?.id}">        
		                    <g:indicatorProgress id="${artInd?.indicator?.id}"/></g:link></td>
		                    <td>${artInd?.value}</td>
		                    <td>${artInd?.indicator?.unit}</td>
		                </tr>
		            </g:each>
		     </table>
		     </g:if>
		    </div>
        </div>
    </body>
</html>
