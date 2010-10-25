
<%@ page import="shemaEditor.shemaAdministration.TReport" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
        <g:set var="entityName" value="${message(code: 'TReport.label', default: 'TReport')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        
        <div class="body">
        <div id="topichead">
            <h5><g:message code="default.edit.label" args="[entityName]" /></h5>
            </div>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${TReportInstance}">
            <div class="errors">
                <g:renderErrors bean="${TReportInstance}" as="list" />
            </div>
            </g:hasErrors>
             <div id="option">
            <g:form method="post" >
                <g:hiddenField name="id" value="${TReportInstance?.id}" />
                <g:hiddenField name="version" value="${TReportInstance?.version}" />
                <div class="schemadialog">
               
                    <table>
                        <tbody>
                        
                           
                        
                            <tr class="prop">
                                <td valign="top" class="name" width="40">
                                  <label for="name"><g:message code="scheme.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: TReportInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${TReportInstance?.name}" size="60"/>
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="scheme.description.label" default="Description" /></label>
                                </td>
                                <td >
                                   
                                </td>
                            </tr>
                        
                        
                            <tr class="prop">
       
                                <td  colspan="2" valign="top" class="value ${hasErrors(bean: TReportInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${TReportInstance?.description}" cols="56" rows="6"/>
                                </td>
                                 <td >
                                   
                                </td>
                            </tr>

                        </tbody>
                    </table>
                   
                </div>
                <p>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>

                </div>
                </p>
            </g:form>
             </div>
        </div>
    </body>
</html>
