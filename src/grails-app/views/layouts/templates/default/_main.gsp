<!--
  Layout Frontend.
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <g:layoutHead />
  <title><g:layoutTitle default="Grails" /></title>
  <link rel="stylesheet" type="text/css" href="${resource(dir:'css/default',file:'main.css')}" />
  
  <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/default',file:'mainIE.css')}" />
  <![endif]-->
  <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:javascript library="jquery"/>
  <script type="text/javascript" src="${resource(dir:'js', file:'storm.js')}"></script>
  <script type="text/javascript" src="${resource(dir:'js', file:'checkAll.js')}"></script>
  <resource:autoComplete skin="default/autocomplete" />
</head>
<body>
<div id="wrapper">
	<g:render template="/layouts/templates/default/header" />
	
    
    <div id="contentarea">
    	<g:render template="/layouts/templates/default/sidebar" />
        <div id="main"><g:layoutBody /></div>
        <div class="clear">&nbsp;</div>
    </div>
    <div class="clear">&nbsp;</div>
    <g:render template="/layouts/templates/default/footer" />
    
</div>
</body>
</html>