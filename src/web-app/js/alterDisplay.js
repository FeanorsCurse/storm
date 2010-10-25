/**
* Author: Olaf Roeder
* This script changes an element's visibility from block to none and back.
*/
function alterDisplay(id){
	el = document.getElementById(id);
	if (el.style.display == "none"){
		el.style.display="block";
	} else {
		el.style.display="none";
	}
}