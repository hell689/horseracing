function Selected(a) {
    var label = a.value;
    var mess = "Коэффициент ставки = "
    var tr = a.parentNode.parentNode;
    rate = tr.getElementsByTagName("td")[2].innerHTML;
    betAmount = tr.getElementsByTagName("td")[3].childNodes[1].value;


    if (label==0) {
    	mess = mess + '<b>' + rate * 1 + '</b>';
    	if (betAmount != ""){
    		mess = mess + ". В случае победы вы получите " + '<b>' + rate * betAmount + '</b>';
    	}	

    } else if (label==1) {
    	rate = (Math.sqrt(Math.sqrt(Math.sqrt(rate))) * 1).toFixed(2);
    	mess = mess + '<b>' + rate + '</b>';
    	if (betAmount != ""){
    		mess = mess + ". В случае победы вы получите " + '<b>' + (rate * betAmount).toFixed(2) + '</b>';
    	}	
    } else if (label==2) {
    	rate = (Math.sqrt(rate) * 1).toFixed(2);
    	mess = mess + '<b>' + rate + '</b>';
    	if (betAmount != ""){
    		mess = mess + ". В случае победы вы получите " + '<b>' + (rate * betAmount).toFixed(2) + '</b>';}
         
    } else {
        document.getElementById("rateMessage").style.display='none';

    }
    document.getElementById("rateMessage").innerHTML = mess;
    document.getElementById("rateMessage").style.display='block';
}
