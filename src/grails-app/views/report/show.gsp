
<%@ page import="ReportEditorMain.ReportEditor.Report" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="admin" />
        <g:set var="entityName" value="${message(code: 'report.show.report', default: 'Report')}" />
        <title><g:message code="report.show.name" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="report.list" args="[entityName]" /></g:link></span> |
            <span class="menuButton"><g:link class="create" action="create"><g:message code="report.show.create.report" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
			 <h1>
 				<g:message code="report.show.report" default="Show reports" />
 			</h1>
            <g:if test="${flash.message}">
           		<div 
           			class="message">${flash.message}
           		</div>
            </g:if>
            <div class="dialog">
                <table class="list">
                <tr>
                	<th>Attribute</th>
                	<th>Value</th>
                </tr>
                    <tbody>
                    
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.id" default="Id" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "id")}
                            </td>
                       </tr>                
                    
                       <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.name" default="Name" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "name")}
                            </td>
                        </tr>
                    
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.description" default="Description" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "description")}
                            </td>
                        </tr>
                        
                       	<tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.status." default="Status" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "status")}
                           	</td>
                            
                        </tr>
                        
						<g:if test="${reportInstance.releasedDate}">
						<tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.releasedDate" default="released at" />
                            </td>
                            <td 
                            	valign="top" class="value"><g:niceDate date="${reportInstance.releasedDate}" />
                           </td>
                        </tr>
						</g:if>
						<g:else>
						<tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.releasedDate" default="released at" />
                            </td>
                            <td 
                            	valign="top" class="value"><g:message code="report.show.notReleased" default="not yet released" />
                           </td>
                        </tr>
						</g:else>
						
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.language" default="Language" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "language")}
                           </td>
                        </tr>
                    
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.author" default="Author" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "author")}
                            </td>
                        </tr>
                        
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.schema" default="Schema" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "treport")}
                            </td>
                        </tr>
                                 
                        <tr class="prop">
                            <td 
                            	valign="top" class="name"><g:message code="report.show.RootArticle" default="RootArticle" />
                            </td>
                            <td 
                            	valign="top" class="value">${fieldValue(bean: reportInstance, field: "rootArticle")}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${reportInstance?.id}" />
                    <span 
                    	class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'report.button.edit', default: 'Edit')}"
                    /></span>
                	<span 
                		class="button"><g:actionSubmit class="cancel" action="list" value="${message(code: 'report.button.cancel', default: 'Cancel')}" />
    				</span>	
                	<span 
                		class="button"><g:actionSubmit action="validateReport" value="${message(code: 'report.button.validate', default: 'Validate')}" />
    				</span>	
                </g:form>
            </div>
        </div>
    </body>
</html>
