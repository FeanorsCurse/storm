<!--
  Layout Admin.
  @author Irene, Gerrit
  @changed 28.08.2010: Gerrit
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title><g:layoutTitle default="Grails" /></title>
  <link rel="stylesheet" href="${resource(dir:'css/default',file:'main.css')}" />
  <link rel="stylesheet" href="${resource(dir:'css/default',file:'ee.css')}" />
  <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <script type="text/javascript" src="${resource(dir:'js', file:'storm.js')}"></script>
  <g:layoutHead />
  <g:javascript library="jquery"/>
</head>
<body>
  <div id="wrapper">
  <g:render template="/layouts/templates/default/header" />
	<div id="contentarea">
    	<div id="mainadmin"><g:layoutBody /></div>
        <div class="clear">&nbsp;</div>
    </div>
    <div class="clear">&nbsp;</div>
    <g:render template="/layouts/templates/default/footer" />
    <g:include controller="function" action="anzeigen"></g:include> 
  </div>
</body>
</html>