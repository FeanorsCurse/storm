<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>
<h1>Ebene löschen</h1>

Sind Sie sicher, dass Sie die markierte Ebene löschen möchten? Alle Unterebenen werden ebenfalls gelöscht!
<br>
<br>
<g:actionSubmit class="delete" action="delete" value="Ja" onclick="return confirm('${message(code: 'default.button.delete', default: 'Are you sure ?')}');" />
<g:submitButton name="update" value="Nein" />
<div class="buttons">
                <g:form>
                    <g:hiddenField name="rootId" id="rootId" value="${rootId}" />
                    <g:hiddenField name="selectedNode" id="selectedNode" value="${selectedNode}" />
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
<br>
<hr>