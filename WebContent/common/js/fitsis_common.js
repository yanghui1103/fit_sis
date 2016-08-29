 
function takeCustomValueByOther(control,action,isSelect,array){ 
	// 0：放置的是ITYPE   1：放置的是STAFFID
	var eJson = createParamJsonCommon(array);
	eJson = JSON.stringify(eJson); 
	var args = {
		"context" : eJson
	};
	baidu.post(action, args, function(data) {  
		if(data.res=="1"){return ;}
		var json = data.list ;
		// 请选择
		if(isSelect == '1'){
			control.append("<option value='-9'>---请选择---</option>");
		}    
		for(var i=0;i< json.length ;i++){
			control.append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		}
	},  'json');
 }


 