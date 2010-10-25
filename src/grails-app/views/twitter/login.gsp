<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />


<script type="text/javascript">
var http = null;

function request(url){
	
if (window.XMLHttpRequest) {
   http = new XMLHttpRequest();
} else if (window.ActiveXObject) {
   http = new ActiveXObject("Microsoft.XMLHTTP");
}
if (http != null) {
   http.open("GET", url, true);
   http.onreadystatechange = out('fdgdfgdfg');
   http.send(null);
}
}
function out(url) {
   if (http.readyState == 4) {
      document.getElementById("twitterPin").innerHTML =url;
   }
}
</script>


</head>


<body>
<g:if test='${flash.message}'>
	<div class="message">
	${flash.message}
	</div>
</g:if>
<h1><g:message code="default.twitter.welcome" default="Welcome" />
</h1>


<g:message code="default.twitter.recievepin" default="" />

<p><a href="${twitter_acces_url}" target="Zielname"
	onclick="javascript:window.open('','Zielname','width=800, height=400, directories=yes, toolbar=no, location=yes, menubar=no, scrollbars=yes, status=no, resizable=no, dependent=no')"><g:message
	code="default.pin.label" default="Request PIN" /></a></p>

<g:if test="${logedIn}">
	<g:form action="sendMessage" controller="twitter">
		<p><textarea cols="60" rows="4" name="m"></textarea></p>
		<input type="submit" value="${message(code: 'twitter.send.button', default: 'Send')}" />

	</g:form>

	<%--Show Status --%>

<g:each in="${statuses}" var="s">

${s}

</g:each>



</g:if>
<g:else>

	<p><g:form action="pin" controller="twitter">
<g:message code="twitter.pin.label" default="Pin: " />
<input type="text" name="pin" />
		<input type="submit" value="${message(code: 'twitter.send.button', default: 'Send')}" />
	</g:form></p>
</g:else>


</body>

</html>