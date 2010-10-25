
<%@ page import="shemaEditor.shemaAdministration.TReport" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
         <link rel="stylesheet" href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />
        <g:set var="entityName" value="${message(code: 'TReport.label', default: 'TReport')}" />
        <title><g:message code="scheme.create.label" args="[entityName]" /></title>
    </head>
    <body>
      
    	<form>
      
    	  	<g:hiddenField name="report" id="report" value="${report}" />
   		  	<g:hiddenField name="tnodeListSelection" id="tnodeListSelection"  value="${tnodeListSelection}" />
      
     	 	<g:submitToRemote id="subImport" action="importSchema" name="subImport" value="${message(code: 'schema.decline.import', default: 'Decline')}" update="option"/>
      	</form>
      	
      	<form>
      		<g:submitToRemote id="subImport" action="importSchema" name="subImport" value="${message(code: 'schema.continue.import', default: 'Continue')}" update="option"/>
      		<g:hiddenField name="report" id="report" value="${report}" />
      	</form>
      
    </body>
</html>
