
<div id="topichead">
            <h7><g:message code="scheme.tnode.delete" default="Delete node" /></h7>
            </div>

<div id="option">
<g:message code="scheme.tnode.delete.warning" default="Are you sure" /><br>
<g:message code="scheme.tnode.delete.warningmessage" default="Are you sure" />
<p>
<div class="buttons"><g:form>
	<g:hiddenField name="report" id="report" value="${report}" />
	<g:hiddenField name="snode" id="snode"
		value="${snode}" />
	<span class="button"><g:actionSubmit class="delete"
		action="delete"
		value="${message(code: 'default.button.delete.label', default: 'Delete')}"
		onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
</g:form></div>
</p>
</div>
