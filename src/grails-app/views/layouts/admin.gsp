<!-- 
This layout switches between the Templates
@author: Gerrit
 -->

<%@ page import="systemadministration.modulmanagement.Template"%>
<g:render template="/layouts/templates/${Template.findByActivated(true) }/admin" />