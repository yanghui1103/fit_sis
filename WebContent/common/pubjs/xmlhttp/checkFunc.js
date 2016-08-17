//检查函数

//去除两边的空格author wang
function trim(string){
	return string.replace(/(^\s*)|(\s*$)/g, "");
}
//判断日期与当前日期的前后author wang
// dateTemp [即日期控件的输入值][格式 xxxx-xx-xx]
// 如果大于当前日期，retrun true  
// 如果小于当前日期, retrun false
function checkIsAfterNow(dateTemp){
    dateTemp = trim(dateTemp);
    nowDate = new Date();
    var array = dateTemp.split('-');
    var month = nowDate.getMonth()+1;
	var date = nowDate.getDate();
	var year = nowDate.getYear();
	//alert("date="+date+string);
    if (parseInt(eval(array[0])) > year ){
    	return true;
    }else if (parseInt(eval(array[0])) == year){
    	if(parseInt(eval(array[1]))>month){
    		return true;
    	}else if (parseInt(eval(array[1]))==month){
    			if(parseInt(eval(array[2]))>=date){
    				return true;
    			}else{
    				return false;
    			}    		
    	}
    }
}

//判断日期是否是今天 wang  (使用大体同上)
function checkIsToday(dateTemp){
    dateTemp = trim(dateTemp);
    nowDate = new Date();
    var array = dateTemp.split('-');
    var month = nowDate.getMonth()+1;
	var date = nowDate.getDate();
	var year = nowDate.getYear();
	//alert("date="+date+string);
    if ( parseInt(eval(array[0])) != year ){
    	return false;
    }else if( parseInt(eval(array[1])) != month ){
    		return false;
    }else if( parseInt(eval(array[2])) !=date ){
    		return false;
    }else{
    		return true;
    }    		
}

//比较两个日期的先后 author wang
function checkTwoDate(first,second){//如果first<second，则返回true;否则返回false
    first = trim(first).split("-");
    second = trim(second).split("-");
	if( parseInt(eval(first[0])) < parseInt(eval(second[0])) )  {
		return true;
	}else if ( parseInt(eval(first[0])) == parseInt(eval(second[0])) ){
		if( parseInt(eval(first[1])) < parseInt(eval(second[1])) ){			
			return true;
		}else if ( parseInt(eval(first[1])) == parseInt(eval(second[1])) ){
			if ( parseInt(eval(first[2])) < parseInt(eval(second[2])) ){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}else{
		return false;
	}
}

//检查密码
//定义密码类型
function passwordType() {
	var value ='0';
	var passwordElement = '0';
}

//检查密码
function checkPassword() {
	var passwordArray = new Array();
	var i=0;j = 0;
	var inputElements = document.getElementsByTagName('INPUT');
	for (var i=0;i<inputElements.length;i++) {
		if (inputElements[i].getAttribute('type') =='password') {
			passwordArray[j] = new passwordType();
			passwordArray[j].value = inputElements[i].value;
			passwordArray[j].passwordElement = inputElements[i];
			j++;
		}
	}
	
	for (var k=1;k<passwordArray.length;k++) {
		if ((k %2) ==1) {
			if (passwordArray[k].value != passwordArray[k-1].value) {		//密码不相等
				alert('你输入的初始密码与验证密码不一致,请重输入！');
				passwordArray[k-1].passwordElement.focus();
				return false;
			}
		}
	}
	return true;
} 

//检查密码结束

//检查邮箱格式
function checkMail(chkElementValue,chkElement) {
	var intIndex = -1,intPointIndex =-1;
	
	intIndex = chkElementValue.indexOf('@');

	if (intIndex ==-1) 	{	//不含有'@'
		chkElement.select();
		chkElement.focus();
		alert("你输入的电子邮箱格式不对，请重输！");
		return false;
	}
	
	intPointIndex = chkElementValue.indexOf('.');

	if (intPointIndex == -1) { //不含有'.'
		chkElement.select();
		chkElement.focus();
		alert("你输入的电子邮箱格式不对，请重输！");
		return false;
	}
	
	if (intIndex>intPointIndex) { //格式不对
		chkElement.select();
		chkElement.focus();
		alert("你输入的电子邮箱格式不对，请重输！");
		return false;
	}
	
	return true;
}

//检查电话号码
function checkPhone(chkElement) {
	var intIndex = -1;
	intIndex = chkElement.value.indexOf('-');
	
	if (intIndex == -1) {					//不存在'-'
		if (isNaN(chkElement.value)) {
			alert("你输入的电话号码格式不对，请重输！");
			chkElement.focus();
			chkElement.select();
			return false;
		} 
		
		return true;
	}
	
	if ((intIndex !=-1) || (intIndex !=4) || (intIndex !=5)) {	//存在'-'但不在第四位或第五位
		chkElement.select();
		chkElement.focus();
		alert("你输入的电话号码格式不对，请重输！");
		return false;
	}
	
	phoneArray = chkElement.value.split('-');
	
	if (phoneArray.length>2) {
		chkElement.select();
		chkElement.focus();
		alert("你输入的电话号码格式不对，请重输！");
		return false;
	}
	
	if (isNaN(phoneArray[0]) || isNaN(phoneArray[1])) {
		alert("你输入的电话号码格式不对，请重输！");
		chkElement.focus();
		chkElement.select();
		return false;
	}
		
	return true;
}

//检查整型
function checkInt(chkElementValue,chkElement) {
	var intIndex = -1;
	
	intIndex = chkElementValue.indexOf('.');
	
	if (intIndex != -1) {
		alert("你输入的不是整数，请重输！");
		chkElement.focus();
		chkElement.select();
		return false;
	}
	
	if (isNaN(chkElementValue)) {
		alert("你输入的不是整数，请重输！");
		chkElement.focus();
		chkElement.select();
		return false;
	}
		
	return true;
}

//检查浮点型
function checkFloat(chkElementValue,chkElement) {
	var intIndex = -1,intIndexBear = -1;
	
	intIndexBear = chkElementValue.indexOf('-');
	
	intIndex = chkElementValue.indexOf('.');
	
	if (intIndexBear != -1) {				//存在'-'
		floatArray = chkElementValue.split('-');
		chkElementValue = floatArray[1];
				
		if (floatArray[0])	{				//非空
			alert("你输入的数字格式不对，请重输！");
			chkElement.focus();
			chkElement.select();
			return false;
		}
	}
		
	if (intIndex == -1) {					//不存在'.'
		if (isNaN(chkElementValue)) {
			alert("你输入的数字格式不对，请重输！");
			chkElement.focus();
			chkElement.select();
			return false;
		} 
		
		return true;
	}
	
	floatArray = chkElementValue.split('.');
	
	if (floatArray.length>2) {
		alert("你输入的数字格式不对，请重输！");
		chkElement.focus();
		chkElement.select();
		return false;
	}
	
	if (isNaN(floatArray[0]) || isNaN(floatArray[1])) {
		alert("你输入的数字格式不对，请重输！");
		chkElement.focus();
		chkElement.select();
		return false;
	}
		
	return true;
}

//检查日期
function checkDate(chkElement){
//正则表达
   	var dateFormat= /^((\d{2}(([02468][048])|([13579][26]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|([1-2][0-9])))))|(\d{2}(([02468][1235679])|([13579][01345789]))[\-\/\s]?((((0?[13578])|(1[02]))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\-\/\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\-\/\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\s(((0?[1-9])|(1[0-2]))\:([0-5][0-9])((\s)|(\:([0-5][0-9])\s))([AM|PM|am|pm]{2,2})))?$/;

	var checkValue=chkElement.value.match(dateFormat);
	
	if(checkValue!=null)
		return true;
	else
		alert("日期格式须遵循YYYY-MM-DD，请重输！");
		chkElement.focus();
		chkElement.select();
	 	return false;
}

//检查时间
//定义时间类型
function dateType() {
	var value ='0';
	var dateElement = '0';
}

//检查时间
function compDate() {
	var dateArray = new Array();
	var i=0;j = 0;
	var inputElements = document.getElementsByTagName('INPUT');
	for (var i=0;i<inputElements.length;i++) {
		if (inputElements[i].getAttribute('type') =='Date') {
			dateArray[j] = new dateType();
			dateArray[j].value = inputElements[i].value;
			dateArray[j].dateElement = inputElements[i];
			j++;
		}
	}
	
	for (var k=0;k<dateArray.length;k++) {
		if (!checkSysteDate(dateArray[k].dateElement)) {
			return false;
		}
		
		if ((k %2) ==1) {
			if (dateArray[k].value < dateArray[k-1].value) {		
				alert('结束时间不能小于开始时间,请重输入！');
				dateArray[k-1].dateElement.focus();
				return false;
			}
		}
	}
	return true;
} 

//与系统时间比较
//与系统时间比较
function checkSysteDate(dateElement){
    var sysDate = new Date();
    
    var dateValue = dateElement.value.split('-');
    
    if (parseInt(dateValue[0]) < parseInt(sysDate.getYear())) {
    	alert('时间不能少于当前时间,请重输入！');
		dateElement.focus();
		return false;
    } else if (parseInt(dateValue[0]) > parseInt(sysDate.getYear())) {
    	return true;
    }
    
    if (parseInt(dateValue[1]) < (parseInt(sysDate.getMonth())+1)) {
    	alert('时间不能少于当前时间,请重输入！');
		dateElement.focus();
		return false;
    } else if (parseInt(dateValue[1]) > (parseInt(sysDate.getMonth())+1)) {
    	return true;
    }
    
    if (parseInt(dateValue[2]) < parseInt(sysDate.getDate())) {
    	alert('时间不能少于当前时间,请重输入！');
		dateElement.focus();
		return false;
    }

    return true;
}
//检查时间结束
