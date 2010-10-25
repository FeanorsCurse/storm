function testPassword(passwd){
var description = new Array();
description[0] = "<table><tr><td><table cellpadding=0 cellspacing=2><tr><td height=4 width=30 bgcolor=#ff0000></td><td height=4 width=120 bgcolor=tan></td></tr></table></td><td>   <b>Weakest</b></td></tr></table>";
description[1] = "<table><tr><td><table cellpadding=0 cellspacing=2><tr><td height=4 width=60 bgcolor=#990000></td><td height=4 width=90 bgcolor=tan></td></tr></table></td><td>   <b>Weak</b></td></tr></table>";
description[2] = "<table><tr><td><table cellpadding=0 cellspacing=2><tr><td height=4 width=90 bgcolor=#990099></td><td height=4 width=60 bgcolor=tan></td></tr></table></td><td>   <b>Improving</b></td></tr></table>";
description[3] = "<table><tr><td><table cellpadding=0 cellspacing=2><tr><td height=4 width=120 bgcolor=#000099></td><td height=4 width=30 bgcolor=tan></td></tr></table></td><td>   <b>Strong</b></td></tr></table>";
description[4] = "<table><tr><td><table><tr><td height=4 width=150 bgcolor=#0000ff></td></tr></table></td><td>   <b>Strongest</b></td></tr></table>";
description[5] = "<table><tr><td><table><tr><td height=4 width=150 bgcolor=tan></td></tr></table></td><td>   <b>Begin Typing</b></td></tr></table>";

var base = 0
var combos = 0
if (passwd.match(/[a-z]/))base = (base+26);
if (passwd.match(/[A-Z]/))base = (base+26);
if (passwd.match(/\d+/))base = (base+10);
if (passwd.match(/[>!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~]/))base = (base+33);

combos=Math.pow(base,passwd.length);

if(combos == 1)strVerdict = description[5];
else if(combos > 1 && combos < 1000000)strVerdict = description[0];
else if (combos >= 1000000 && combos < 1000000000000)strVerdict = description[1];
else if (combos >= 1000000000000 && combos < 1000000000000000000)strVerdict = description[2];
else if (combos >= 1000000000000000000 && combos < 1000000000000000000000000)strVerdict = description[3];
else strVerdict = description[4];

document.getElementById("Words").innerHTML= (strVerdict);
}
////
