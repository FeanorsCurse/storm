/**
 * Script marks all checkboxes within a form checked/unchecked
 * 
 * @author Irene, Gerrit
 */
function checkAll(){
	var  checkboxes = document.form.elements["checkbox"];
	if(window.document.form.checkall.checked){
		for (var i=0;i<checkboxes.length;i++) {
			checkboxes[i].checked = true;
		}
	}else{
		for (var i=0;i<checkboxes.length;i++) {
			checkboxes[i].checked = false;
		}
	}
}