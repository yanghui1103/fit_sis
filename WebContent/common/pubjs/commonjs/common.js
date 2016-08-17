//?????????????????????(?????tabl?)
document.onkeydown = win_keydown;
function win_keydown() {
	if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='checkbox' && event.srcElement.type!='radio' && event.srcElement.type!='')
    	event.keyCode=9;
    if (event.keyCode==13 && event.srcElement.type =='checkbox' && event.srcElement.type=='button' && event.srcElement.type=='submit' && event.srcElement.type=='reset' && event.srcElement.type=='radio') {
    	event.srcElement.click();
    }
}

//???????????????????????"allbox"??checkbox ?????box 
function checkAll(theForm) {
  for (var i=0;i<theForm.elements.length;i++) {
    var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' && 
            (e.type.indexOf("checkbox") == 0)) {
        	e.checked = theForm.allbox.checked;		
		}
	} 
}


//???? display:none ???????
function toggleDisplay(targetId)
{
    if (document.getElementById) {
        target = document.getElementById(targetId);
    	if (target.style.display == "none"){
    		target.style.display = "";
    	} else {
    		target.style.display = "none";
    	}
    }
}

//????????????????????
function toggleVisibility(targetId) {
    if (document.getElementById) {
        target = document.getElementById(targetId);
    	if (target.style.visibility == "hidden"){
    		target.style.visibility = "visible";
    	} else {
    		target.style.visibility = "hidden";
    	}
    }
}

//填加小数的显示
function decimalDataDisplay(dataValue) {
	var pointLocation = dataValue.indexOf('.');
	
	if (pointLocation == -1) {
		dataValue += '.00';
		return dataValue;
	}
	
	var pointData =  dataValue.split('.')[1];
	
	if (pointData.length = 1) {
		dataValue += '0';
		return dataValue;
	}
	
	return dataValue;
}

