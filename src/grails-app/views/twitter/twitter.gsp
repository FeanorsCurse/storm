<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
</head>


<body>
<g:if test='${flash.message}'>
	<div class="message">
	${flash.message}
	</div>
</g:if>


<h2><g:message code="default.twitter.welcome" default="Inform the Community" /></h2>


<g:form action='sendMessage' name='sendmessage'>
<p>
<g:message code="default.twitter.textboxlabel" default="Message" />
	</p>
	<g:textArea name="message" cols="60" rows="3"/>
	<p>
	<input type='submit' value='${message(code: 'twitter.submit.button', default: 'Submit')}' />
	</p>
</g:form>

</body>

</html>