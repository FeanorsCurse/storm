
<%@ page import="shemaEditor.shemaAdministration.TReport" %>
<%@ page import="shemaEditor.shemaAdministration.TNode" %>
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta name="layout" content="admin" />
<link rel="stylesheet"
	href="${resource(dir:'css/standard',file:'schemaEditor.css')}" />

<g:set var="entityName"
	value="${message(code: 'TReport.label', default: 'TReport')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<export:resource />
<script type="text/javascript"
	src="${resource(dir:'js/simpletreeview',file:'simpletreemenu.js')}">
/***********************************************
* Simple Tree Menu- � Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/
</script>
<link rel="stylesheet" type="text/css"
	href="${resource(dir:'css/standard',file:'simpletree.css')}" />


  

</head>
<body onload ="ddtreemenu.createTree('treemenu', false)">

<h2>${fieldValue(bean: TReportInstance, field: "name")}</h2>
        <div id="treeOuterBox">
         <div id="topichead"><h7><g:message code="scheme.structure.label" default="Schema-Structure" /></h7></div>
        <div id="treeBox">
       
       <%= domTreeAsHtml %> 

        </div>
         
        </div>
        <div id="detailsBox">
        <div class="nav" style="margin-top:20px;text-align:right;">
            <span class="menuButton">
             <g:remoteLink action="create" controller="TNode"  update="optionsBox" params="[snode:TReportInstance.root.id, report:TReportInstance.id]">
             <g:message code="scheme.new.menu" default="New" />
              </g:remoteLink>
              </span>

          |
          <span class="menuButton">
            <g:remoteLink action="listNodeIndicator" controller="TNode" update="optionsBox" params="[snode:TReportInstance.root.id, report:TReportInstance.id]">
             
              <g:message code="indicator.list.label" default="Indicators" />
            </g:remoteLink>
          </span>


          |
<span class="menuButton">
             <g:remoteLink action="edit" controller="TNode"  update="optionsBox" params="[id:TReportInstance.root.id, report:TReportInstance.id]">
              <g:message code="scheme.edit.menu" default="Edit" />
              </g:remoteLink>
              </span>


          
          |

    
<span class="menuButton">
             <g:remoteLink action="deleteConfirm" controller="TNode"  update="optionsBox" params="[id:TReportInstance.root.id, report:TReportInstance.id]">
               <g:message code="scheme.delete.menu" default="Delete" />
              </g:remoteLink>
              </span>


|
<span class="menuButton">
             <g:remoteLink action="importSchema" controller="TReport"  update="optionsBox" params="[id:TReportInstance.root.id, report:TReportInstance.id]">
              <g:message code="scheme.import.menu" default="Import" />
              </g:remoteLink>
              </span>


|
<span class="menuButton">
             <g:link action="show" controller="TReport"  params="[id:TReportInstance.id]">
              <g:message code="scheme.overview.menu" default="Oberview" />
              </g:link>
              </span>

        </div>
        


       <div id="optionsBox">
       
       </div>
   
            <div id="topichead"><h7>${TReportInstance.root?.name } <g:message code="scheme.details.label" default="- Details" /></h7> </div>
            
            <div id="option">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:if test="${params.importnode}">
            <p>
            <g:message code="scheme.undo.message" default="Undo" />
            </p>
            <div align="right">
            
            <g:form action="delete" controller="TNode">
	<g:hiddenField name="snode" id="snode" value="${params.importnode}" />
	<g:hiddenField name="report" id="report" value="${params.report}" />
	
	<span class="button"><g:actionSubmit 
		action="delete"
		controller="TNode"
		value="Undo"
		onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
</g:form>
            
            </div>
            
            </g:if>
            
            <div>
                <table>
                   
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.name.label" default="Name" /></td>
                            
                            <td valign="top" class="value">${TReportInstance?.root?.encodeAsHTML()}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.description.label" default="Description" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: TReportInstance.root, field: "description")}</td>
                            
                        </tr>
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.creator.label" default="Creator" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${TReportInstance.root?.creator?.id}">${TReportInstance?.creator?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.creationDate.label" default="Creation Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm:ss" date="${TReportInstance.root?.creationDate}" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.lastModifiedBy.label" default="Last Modified By" /></td>
                            
                            <td valign="top" class="value"><g:link controller="user" action="show" id="${TReportInstance.root?.lastModifiedBy?.id}">${TReportInstance.root?.lastModifiedBy?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="scheme.lastModified.label" default="Last Modified" /></td>
                            
                            <td valign="top" class="value"><g:formatDate format="dd.MM.yyyy HH:mm:ss" date="${TReportInstance.root?.lastModified}" /></td>
                            
                        </tr>
                             
                </table>
            </div>
            </div>
          
           
        </div>
        
     <div style='clear:both'></div>

</body>
</html>
