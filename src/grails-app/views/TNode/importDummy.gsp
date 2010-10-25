<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<%@ page import="shemaEditor.shemaAdministration.TNode" %>
<%@ page import="shemaEditor.shemaAdministration.TReport" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<h1>Schema importieren</h1>
Wählen Sie das zu importierende Schema:<br>
<p>
<g:select nam="indicatorList" from="${TReport.list()}" />
</p>

Wählen Sie die Ebene aus, die importiert werden soll. <br>Alle Unterebenen werden ebenfalls importiert
<p>
<g:select nam="indicatorList" from="${TNode.list()}" /> 
</p>
<p>
<g:submitButton name="Importieren" value="Importieren"  disabled="disabled" /> <g:submitButton name="Abbrechen" value="Abbrechen"  disabled="disabled" />
</p>
<hr>
