function alltrim(oldStr){
	var newStr = "";
	for(var i=0;i<oldStr.length;i++){   
		if(oldStr.charAt(i)==" ")   
			newStr+=""   
		else   
			newStr+=oldStr.charAt(i)   
	}   
	return newStr;
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function lrtrim(str){
	return str.replace(/(^\s*)/g, "");
}

function rtrim(str){
	return str.replace(/(\s*$)/g, "");
}
