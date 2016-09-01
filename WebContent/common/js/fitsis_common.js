 
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

/***
 * 计算人员类型
 */
function getPersonTypeName(age,gender){
	var array = new Array();
	if(gender =='0'){
		if(age >=40 && age <45){
			array[0] = '101';array[1]="距退休年龄超5年人员";
			return array ;
		}else if(age >=45 && age <=50){
			array[0] = '102';array[1]="距退休年龄不足5年人员";
			return array ;
		}
	}else  {
			if(age >=50 && age <55){
				array[0] = '101';array[1]="距退休年龄超5年人员";
				return array ;
			}else if(age >=55 && age <=60){
				array[0] = '102';array[1]="距退休年龄不足5年人员";
				return array ;
			}
		}
	   array[0]="-9";array[1]="年龄不符合";
	   return array ;
}
 