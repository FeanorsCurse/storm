<script language="JavaScript">
function CheckAllCatIndi(chk)
{
if(document.selectCatIndi.catIndicatorCheck.checked==true){
for (i = 0; i < chk.length; i++)
chk[i].checked = true ;
}else{

for (i = 0; i < chk.length; i++)
chk[i].checked = false ;
}
}


  </script>


<g:if test="${cindis}">


	<form name="selectCatIndi">


	<table width="100%">
		<tr>

			<td>
			<table class="list">

				<thead>

					<tr>
						<th width="13%"><input name="catIndicatorCheck"
							type="checkbox"
							onClick="CheckAllCatIndi(document.selectCatIndi.category)"
							value="yes" /></th>
						<th><g:message code="indicator.name.label" default="Name" /></th>
					</tr>


				</thead>


				<tbody>
					<g:each in="${cindis}" var="indi" status="i">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><g:checkBox name="category" value="${indi.id}"
								checked="false" /></td>

							<td><g:link controller="indicator" action="show"
								id="${indi.id}">
								${indi}
							</g:link></td>

						</tr>
					</g:each>
				</tbody>



			</table>
			</td>
		</tr>

	</table>


	</form>
</g:if>
<g:if test="${!cindis}">
	<g:message code="no.indicators.in.category" default="No indicators" />
</g:if>