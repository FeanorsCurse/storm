
<%@ page import="shemaEditor.shemaAdministration.TNode" %>
<%@ page import="shemaEditor.shemaAdministration.TReport" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<g:message code="node.choose.toimport" default="Import" /><br>
<g:message code="node.choose.toimport.warning" default="Import nodes" />

<p>
<g:message code="selected.node.label" default="Node: " /> <g:select style="width:130px;" name="tnodeListSelection" id="tnodeListSelection" optionKey="id"  noSelection="${['0':message(code:'choose.node.selected')]}" from="${nodeList}"></g:select>
</p>

