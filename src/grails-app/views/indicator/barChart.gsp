<%@ page import="shemaEditor.indicatorAdministration.Indicator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>

  
  
<g:pieChart data="${values}" type="extended" fill="${'bg,s,efefef'}" labels="${labels}" size="${[500,200]}" colors="${colors}" pie="" title="Indikatorverlauf: ${indicatorInstance}"></g:pieChart> 
</body>
</html>