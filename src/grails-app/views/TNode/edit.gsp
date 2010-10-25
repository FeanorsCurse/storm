
<%@ page import="shemaEditor.shemaAdministration.TNode" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

        <g:set var="entityName" value="${message(code: 'TNode.label', default: 'TNode')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        
        <div class="body">
            <dvi id="topichead">
            <h7><g:message code="scheme.tnode.edit" default="Edit node" /></h7>
            </div>
            
             <div id="option">
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${TNodeInstance}">
            <div class="errors">
                <g:renderErrors bean="${TNodeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${TNodeInstance?.id}" />
                 <g:hiddenField name="rootId" id="rootId" value="${rootId}" />
                <g:hiddenField name="version" value="${TNodeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        </tr>
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="node.name.label" default="Name" /></label>
                                </td>
                                </tr>
                                    <tr>
                                <td valign="top" class="value ${hasErrors(bean: TNodeInstance, field: 'name', 'errors')}">
                                    <g:textField size="60" name="name" value="${TNodeInstance?.name}" />
                                </td>
                            </tr>
                             <tr class="prop">
                              <td height="10"> 
                             </td>
                             <td>
                             </td>
                             </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="node.description.label" default="Description" /></label>
                                </td></tr>
                                <tr><td valign="top" class="value ${hasErrors(bean: TNodeInstance, field: 'description', 'errors')}">
                                    <g:textArea class="text" rows="8" cols="60"  name="description" value="${TNodeInstance?.description}" />
                                </td>
                            
                        
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
                <g:hiddenField name="report" id="report" value="${report}" />
            </g:form>
        </div>

      <br>
  </div>

    </body>
</html>
