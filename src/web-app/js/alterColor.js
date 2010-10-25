function alterColor(id){
	el = document.getElementById(id);
	if (el.style.color != "gray"){
		el.style.color="gray";
	} else {
		el.style.color="black";
	}
}