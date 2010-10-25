//Author: Olaf Roeder
//Script marks all checkboxes within a form checked/unchecked

//variable to distinguish between check and uncheck
var checkMe=false;
function checksAll(){
    //get all checkboxes
    var x = document.getElementsByName("checkedBoxes");

    //checkboxes have to be checked
    if (!checkMe){
        checkMe=true;
    }
    //checkboxes have to be unchecked
    else {
        checkMe=false;
    }

    //perform checking/unchecking
    for (i=0;i<x.length;i++)
    {
        x[i].checked=checkMe;
    }
}