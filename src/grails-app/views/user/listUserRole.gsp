
<%@ page import="systemadministration.usermanagement.User" %>
<%@ page import="systemadministration.usermanagement.Role" %>
<g:javascript library="prototype"/>

<div id="topichead">
            <h7>Rollen</h7>
</div>

             <div id="option">
</p>


${flash.message}
<table width="100%">
  <tbody>

    <tr class="prop">

      <td valign="top" class="name">
verfuegbare Rollen:
  <g:formRemote name="globalRoleListForm" url="[controller:'Role',action:'addRole']" update="optionsBox">
    <g:select name="globalUserList" WIDTH="400" STYLE="width: 200px" id="globalUserList" optionKey="id" from="${userInstanceList}" multiple="yes" size="10" onClick="${remoteFunction(controller:'role', action:'showRoleDetails', update:'roleDetails', params:'\'roleId=\'+this.value')}"/>
   
    <g:hiddenField name="id" id="id" value="${id}"/>
    <g:hiddenField name="suser" id="suser" value="${suser}"/>
    <br>
     <input type="submit" name="add" value="Hinzufgen" />
  </g:formRemote>


</td>

<td valign="top" class="value">
hinzugefuegte Rollen:
<g:formRemote name="nodeRoleList" url="[controller:'User',action:'deleteRole']" update="optionsBox">
  <g:select name="nodeRoleList" optionKey="id" from="${results}" multiple="yes"  WIDTH="400" STYLE="width: 200px" size="10" onClick="${remoteFunction(controller:'role', action:'showRoleDetails', update:'roleDetails', params:'\'roleId=\'+this.value')}"/>
  <input type="hidden" name="suser" value="${suser}"/><br>
  <input type="submit" name="delete" value="Entfernen" />
</g:formRemote>

</td>

</tr>



</tbody>
</table>
<div id="roleDetails" name="roleDetails">

</div>
</div>

