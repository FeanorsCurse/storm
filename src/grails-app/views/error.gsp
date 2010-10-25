<!--
  This template includes header, menu and footer to be included from other backend views.
-->
<html>
<head>
  <meta name="layout" content="main" />
  <title>STORM - Systemfehler</title>
</head>
<body>
  <h1>Grails Runtime Exception</h1>
  <h2>Error Details</h2>

  <div class="message">
    <strong>Error ${request.'javax.servlet.error.status_code'}:</strong> ${request.'javax.servlet.error.message'.encodeAsHTML()}<br/>
    <strong>Servlet:</strong> ${request.'javax.servlet.error.servlet_name'}<br/>
    <strong>URI:</strong> ${request.'javax.servlet.error.request_uri'}<br/>
    <g:if test="${exception}">
      <strong>Exception Message:</strong> ${exception.message?.encodeAsHTML()} <br />
      <strong>Caused by:</strong> ${exception.cause?.message?.encodeAsHTML()} <br />
      <strong>Class:</strong> ${exception.className} <br />
      <strong>At Line:</strong> [${exception.lineNumber}] <br />
      <strong>Code Snippet:</strong><br />
      <div class="snippet">
        <g:each var="cs" in="${exception.codeSnippet}">
          ${cs?.encodeAsHTML()}<br />
        </g:each>
      </div>
    </g:if>
  </div>
  <g:if test="${exception}">
    <h2>Stack Trace</h2>
    <div class="stack">
      <pre><g:each in="${exception.stackTraceLines}">${it.encodeAsHTML()}<br/></g:each></pre>
    </div>
  </g:if>
</body>
</html>