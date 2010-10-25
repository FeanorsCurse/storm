<!--
  Layout Frontend.
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<g:layoutHead />
<title><g:layoutTitle default="Grails" /></title>
<link rel="stylesheet"
	href="${resource(dir:'css/uniol',file:'stylesheet.css')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/uniol',file:'uni.css')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/uniol',file:'banner.css')}" />
<link rel="shortcut icon"
	href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
</head>

<body>

<!-- printstopp -->
<div id="page_margins4" />
<div id="page_margins3" />
<div id="page_margins2" />
<div id="page_margins" />
<div id="page" class="hold_floats" >

<!-- Beginn Header -->
<div id="header">
<g:render template="/layouts/templates/uniol/header" /> 
<!-- End Header -->
</div>

<div id="menu_box">
	 <g:render template="/layouts/templates/uniol/components/menubox" />  
</div>

<!-- #main: Beginn des mehrspaltigen Bereichs -->
<div id="main"><!-- #col1: Erste Float-Spalte des Inhaltsbereiches -->

<div id="col1">
<div id="col1_content" class="clearfix"><!-- #nav: Hauptnavigation -->
<a id="navigation" name="navigation"></a> <!-- Skiplink-Anker: Navigation -->


<div id="menue"><a name="site-navi"></a>
<h2 class="unsichtbar">Navigationsmen&uuml;</h2>




<g:render template="/layouts/templates/uniol/sidebar" /> <!-- Ende Menü -->
</div>

</div>
</div>
<!-- #col1: - Ende --> <!-- #col2: zweite Float-Spalte des Inhaltsbereiches -->
<div id="col2">
<div id="col2_content" class="clearfix"><!-- derzeit ungenutzter Bereich --><!-- #BeginEditable "RechteSpalte" --><!-- #EndEditable --></div>
</div>
<!-- #col2: - Ende --> <!-- #col3: Statische Spalte des Inhaltsbereiches -->
<div id="col3">
<div id="col3_content" class="clearfix">

<!-- Brotkrumenzeile --> 
<a id="breadcrumb" name="breadcrumb"></a> 
	
<!-- Begin Content -->
<div id="inhalt" class="floatbox">
<g:layoutBody /> 
<!-- End Content -->
</div>

</div>
</div>

<!-- Begin Footer -->
<div id="footer"><g:render
	template="/layouts/templates/uniol/footer" /> 
<!-- End Footer -->
</div>
</div>
</body>
</html>
