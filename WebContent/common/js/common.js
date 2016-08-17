function AjaxExchangeBackXMLDataByJson(url,edom){
	   var rpc = new XmlRPC(url+"?context="+edom);	
	    rpc.sendText();
		var xml= rpc.getXml();
		return xml ;
	}
function openwindow(url,name,iWidth,iHeight)
{
var url; //转向网页的地址;
var name; //网页名称，可为空;
var iWidth; //弹出窗口的宽度;
var iHeight; //弹出窗口的高度;
var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
var re =  window.showModalDialog(url,name,'height='+iHeight+',,innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
return re ;
}
//所有提交，查询等操作按钮走的JS校验
function checkStaffFuncButton(button_id,obj){
     // 按钮的ID  -- button_id
    //（1） 该处后面改造，从登录的session中获取该员工的域权限，按后与按钮ID核对是否可以操作

    return true ;
}
// 将空值替换为-9
function replaceNullValToNumber9(val){
   if(val == '' || val == undefined || val=='null'||val == null){
       val = '-9' ;
   }
   return val ;
}
// 将-9替换为空
function replaceF9ValToNull(val){
   if(parseInt(val) == -9 ){
       val = '' ;
   }
   return val ;
}
//将-9替换为不详
function replaceF9ValToUnknown(val){
   if(parseInt(val) == -9 ){
       val = '不详' ;
   }
   return val ;
}
function clrThisPage(){
      window.location.reload();
   }

function replaceNullValToNumberPlus1(val){
   if(val == '' || val == undefined || val=='null'||val == null){
       val = '-1' ;
   } 
   return val ;
}
function replaceNullValToOtherVal(val,toVal){
   if(val == '' || val == undefined || val=='null'||val == null){
       val = toVal ;
   }
   return val ;
}
function clrPage(){
   window.location.reload();   
}
// 返回处理结果insert update delete 等
function alertUpOrInOver(ttDom,plusMes){
       var res = ttDom.selectSingleNode('./root/res').text;
       var msg = ttDom.selectSingleNode('./root/msg').text;
       if(plusMes == '' ||plusMes ==undefined||plusMes == null){
          alert(msg);
       }else{
          alert(msg+":"+plusMes);
       }
       
          window.location.reload();
          return ;
}

// 统一提示
function alertMsgBoxByXmlData(resultXml){
	var res = resultXml.selectSingleNode('./root/res').text;
    var msg = resultXml.selectSingleNode('./root/msg').text;
    if(res == '1'){
    	alertMsg.error(msg);
    }else if(res=='2'){
    	alertMsg.correct(msg);
    }else if(res=='3'){
    	alertMsg.info(msg);    	
    }else{
    	alertMsg.warn(msg);
    }
}
function AjaxExchangeBackTextData(url,edom){
	   var rpc = new XmlRPC(url+"?context="+edom);	
	    rpc.sendText();
		var text= rpc.getText();
		return text ;
	}
function AjaxExchangeBackTextDataV2(url,eJson){ 
	var resultData = "";
	$.post(url,"context="+eJson,function(data){
		resultData = data; 
	});	
	   return resultData ;
 }
 

function alertToUserMsg(json){ 
	var res = json.res;
    var msg = json.msg;    if(res == '1'){
    	alertMsg.error(msg);
    }else if(res=='2'){
    	alertMsg.correct(msg);
    }else if(res=='3'){
    	alertMsg.info(msg);
    }else{
    	alertMsg.warn(msg);
    }
}

function alertToUserMsg(json,deal){
	var res = json.res;
    var msg = json.msg;
    if(res == '0'){
    	$.pdialog.open("login_dialog.html","dlglogin1","登录");
    }else  if(res == '1'){
    	alertMsg.error(msg);
    	deal(json) ;
    }else if(res=='2'){
    	alertMsg.correct(msg);
    	deal(json) ;
    }else if(res=='3'){
    	alertMsg.info(msg);
    	deal(json) ;
      }
}


// 文件上传
function ajaxFileUpload(foregin_id)
{ 
	$("#loading")
	.ajaxStart(function(){
		$(this).show();
	})//开始上传文件时显示一个图片
	.ajaxComplete(function(){
		$(this).hide();
	});//文件上传完成将图片隐藏起来
	
	$.ajaxFileUpload
	(
		{
			url:'fileUploadAction.action',//用于文件上传的服务器端请求地址
			secureuri:false,//一般设置为false
			fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
			dataType: 'json',//返回值类型 一般设置为json 
			success: function (data, status)  //服务器成功响应处理函数
			{
				if(typeof(data.error) != 'undefined')
				{
					if(data.error != '')
					{
						alertMsg.error(data.error);
					}else
					{						
						 //var edom = createParamDom3(foregin_id, data.message ,data.beforeName);  
						// var ttDom = AjaxExchangeBackXMLDataByXML('createForeignAndAttachmentRelation.action',edom);   
						
						 var array = new Array(foregin_id, data.message ,data.beforeName );   
							createJsonAndAjax('createForeignAndAttachmentRelation.action', array, dealUploadList,
									'JSON');	
					}
				}
			},
			error: function (data, status, e)//服务器响应失败处理函数
			{
				alertMsg.error(e);
			}
		}
	)		
	return false;

} 
function dealUploadList(data){ 
	if(data.res =='2'){
		 alertMsg.correct("文件上传成功");
	}else{
		 alertMsg.error(data.msg);
	}
}
// 检查时候登录信息session过时
function isSessionValidate(user){
    if(user == ''||user == 'null'||user == null|| user == undefined){
       alert('登录信息已经失效，请重新登录') ;
       return false ;
    }
    return true ;
}
// 请选择选项
function addPleaseCheck(control){
	// 添加---请选择---
	var option = document.createElement("<option value='-9'></option>");
	option.appendChild(document.createTextNode("--请选择--"));
	control.appendChild(option);
	}
// 创建不同个数参数的DOM
function createParamDom1(
                          param1val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		rootNode.appendChild(param1Node);
  
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom2(
                           param1val,
                           param2val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
  
  		reDom.appendChild(rootNode);
  		return reDom ;
} 

function createParamDom3(
                           param1val,
                           param2val,
                           param3val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom4(
                           param1val,
                           param2val,
                           param3val,
                           param4val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom5(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom6(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom7(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom8(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 

function createParamDom9(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 

function createParamDom10(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
        rootNode.appendChild(param10Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 

function createParamDom11(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
        rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
// 12 param
function createParamDom12(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
        rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
// 13 param
function createParamDom13(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
        rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
// 14 param
function createParamDom14(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val,
                           param14val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        var param14Node = reDom.createElement("param14");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		if (param14val != null)
		{
			param14Node.text = param14val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
        rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
        rootNode.appendChild(param14Node);        
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
//over
function createParamDom15(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val,
                           param14val,
                           param15val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        var param14Node = reDom.createElement("param14");
        var param15Node = reDom.createElement("param15");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		if (param14val != null)
		{
			param14Node.text = param14val;
		}
		if (param15val != null)
		{
			param15Node.text = param15val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
		rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
        rootNode.appendChild(param14Node);
        rootNode.appendChild(param15Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 


function createParamDom16(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val,
                           param14val,
                           param15val,
                           param16val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        var param14Node = reDom.createElement("param14");
        var param15Node = reDom.createElement("param15");
        var param16Node = reDom.createElement("param16");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		if (param14val != null)
		{
			param14Node.text = param14val;
		}
		if (param15val != null)
		{
			param15Node.text = param15val;
		}
		if (param16val != null)
		{
			param16Node.text = param16val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
		rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
        rootNode.appendChild(param14Node);
        rootNode.appendChild(param15Node);
        rootNode.appendChild(param16Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 
function createParamDom17(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val,
                           param14val,
                           param15val,
                           param16val,
                           param17val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        var param14Node = reDom.createElement("param14");
        var param15Node = reDom.createElement("param15");
        var param16Node = reDom.createElement("param16");
        var param17Node = reDom.createElement("param17");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		if (param14val != null)
		{
			param14Node.text = param14val;
		}
		if (param15val != null)
		{
			param15Node.text = param15val;
		}
		if (param16val != null)
		{
			param16Node.text = param16val;
		}
		if (param17val != null)
		{
			param17Node.text = param17val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
		rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
        rootNode.appendChild(param14Node);
        rootNode.appendChild(param15Node);
        rootNode.appendChild(param16Node);
        rootNode.appendChild(param17Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 




function createParamDom19(
                           param1val,
                           param2val,
                           param3val,
                           param4val,
                           param5val,
                           param6val,
                           param7val,
                           param8val,
                           param9val,
                           param10val,
                           param11val,
                           param12val,
                           param13val,
                           param14val,
                           param15val,
                           param16val,
                           param17val,
                           param18val,
                           param19val){
        var reDom = f_createDom(); 
        var rootNode = reDom.createElement("root");
        var param1Node = reDom.createElement("param1");
        var param2Node = reDom.createElement("param2");
        var param3Node = reDom.createElement("param3");
        var param4Node = reDom.createElement("param4");
        var param5Node = reDom.createElement("param5");
        var param6Node = reDom.createElement("param6");
        var param7Node = reDom.createElement("param7");
        var param8Node = reDom.createElement("param8");
        var param9Node = reDom.createElement("param9");
        var param10Node = reDom.createElement("param10");
        var param11Node = reDom.createElement("param11");
        var param12Node = reDom.createElement("param12");
        var param13Node = reDom.createElement("param13");
        var param14Node = reDom.createElement("param14");
        var param15Node = reDom.createElement("param15");
        var param16Node = reDom.createElement("param16");
        var param17Node = reDom.createElement("param17");
        var param18Node = reDom.createElement("param18");
        var param19Node = reDom.createElement("param19");
        if (param1val != null)
		{
			param1Node.text = param1val;
		}
		if (param2val != null)
		{
			param2Node.text = param2val;
		}
		if (param3val != null)
		{
			param3Node.text = param3val;
		}
		if (param4val != null)
		{
			param4Node.text = param4val;
		}
		if (param5val != null)
		{
			param5Node.text = param5val;
		}
		if (param6val != null)
		{
			param6Node.text = param6val;
		}
		if (param7val != null)
		{
			param7Node.text = param7val;
		}
		if (param8val != null)
		{
			param8Node.text = param8val;
		}
		if (param9val != null)
		{
			param9Node.text = param9val;
		}
		if (param10val != null)
		{
			param10Node.text = param10val;
		}
		if (param11val != null)
		{
			param11Node.text = param11val;
		}
		if (param12val != null)
		{
			param12Node.text = param12val;
		}
		if (param13val != null)
		{
			param13Node.text = param13val;
		}
		if (param14val != null)
		{
			param14Node.text = param14val;
		}
		if (param15val != null)
		{
			param15Node.text = param15val;
		}
		if (param16val != null)
		{
			param16Node.text = param16val;
		}
		if (param17val != null)
		{
			param17Node.text = param17val;
		}
		if (param18val != null)
		{
			param18Node.text = param18val;
		}
		if (param19val != null)
		{
			param19Node.text = param19val;
		}
		rootNode.appendChild(param1Node);
		rootNode.appendChild(param2Node);
        rootNode.appendChild(param3Node);
        rootNode.appendChild(param4Node);
        rootNode.appendChild(param5Node);
        rootNode.appendChild(param6Node);
        rootNode.appendChild(param7Node);
        rootNode.appendChild(param8Node);
        rootNode.appendChild(param9Node);
		rootNode.appendChild(param10Node);
        rootNode.appendChild(param11Node);
        rootNode.appendChild(param12Node);
        rootNode.appendChild(param13Node);
        rootNode.appendChild(param14Node);
        rootNode.appendChild(param15Node);
        rootNode.appendChild(param16Node);
        rootNode.appendChild(param17Node);
        rootNode.appendChild(param18Node);
        rootNode.appendChild(param19Node);
  		reDom.appendChild(rootNode);
  		return reDom ;
} 

function createParamJson(paramArray ){  
    var obj = new Object(); 
  obj.param1 = paramArray[0]==""?"-9":paramArray[0]; 
		obj.param2 = paramArray[1]==""?"-9":paramArray[1]; 
		obj.param3 = paramArray[2]==""?"-9":paramArray[2]; 
		obj.param4 = paramArray[3]==""?"-9":paramArray[3]; 
		obj.param5 = paramArray[4]==""?"-9":paramArray[4]; 
		obj.param6 = paramArray[5]==""?"-9":paramArray[5]; 
		obj.param7 = paramArray[6]==""?"-9":paramArray[6]; 
		obj.param8 = paramArray[7]==""?"-9":paramArray[7]; 
		obj.param9 = paramArray[8]==""?"-9":paramArray[8]; 
		obj.param10 = paramArray[9]==""?"-9":paramArray[9];  
		obj.param11 = paramArray[10]==""?"-9":paramArray[10]; 
		obj.param12 =  paramArray[11]==""?"-9":paramArray[11];  
		obj.param13 =  paramArray[12]==""?"-9":paramArray[12]; 
		obj.param14 =  paramArray[13]==""?"-9":paramArray[13]; 
		obj.param15 =  paramArray[14]==""?"-9":paramArray[14]; 
		obj.param16 =  paramArray[15]==""?"-9":paramArray[15]; 
		obj.param17 =  paramArray[16]==""?"-9":paramArray[16]; 
		obj.param18 =  paramArray[17]==""?"-9":paramArray[17]; 
		obj.param19 =  paramArray[18]==""?"-9":paramArray[18]; 
		obj.param20 =  paramArray[19]==""?"-9":paramArray[19]; 			 
		obj.param21 = paramArray[20]==""?"-9":paramArray[20];  
		obj.param22 =  paramArray[21]==""?"-9":paramArray[21]; 
		obj.param23 =  paramArray[22]==""?"-9":paramArray[22]; 
		obj.param24 =  paramArray[23]==""?"-9":paramArray[23]; 
		obj.param25 =  paramArray[24]==""?"-9":paramArray[24]; 
		obj.param26 =  paramArray[25]==""?"-9":paramArray[25]; 
		obj.param27 =  paramArray[26]==""?"-9":paramArray[26]; 
		obj.param28 =  paramArray[27]==""?"-9":paramArray[27]; 
		obj.param29 =  paramArray[28]==""?"-9":paramArray[28]; 
		obj.param30 =  paramArray[29]==""?"-9":paramArray[29]; 
		var lst=[];	 var json={};lst.push(obj);json['content']=lst; 
		return json ;
} 

function createParamJsonRpt(paramArray ){  
    var obj = new Object();  
  obj.param1 = paramArray[0]==undefined?"-9":paramArray[0]; 
		obj.param2 = paramArray[1]==undefined?"-9":paramArray[1]; 
		obj.param3 = paramArray[2]==undefined?"0":paramArray[2]; 
		obj.param4 = paramArray[3]==undefined?"0":paramArray[3]; 
		obj.param5 = paramArray[4]==undefined?"0":paramArray[4]; 
		obj.param6 = paramArray[5]==undefined?"0":paramArray[5]; 
		obj.param7 = paramArray[6]==undefined?"0":paramArray[6]; 
		obj.param8 = paramArray[7]==undefined?"0":paramArray[7]; 
		obj.param9 = paramArray[8]==undefined?"0":paramArray[8]; 
		obj.param10 = paramArray[9]==undefined?"0":paramArray[9];  
		obj.param11 = paramArray[10]==undefined?"0":paramArray[10]; 
		obj.param12 =  paramArray[11]==undefined?"0":paramArray[11];  
		obj.param13 =  paramArray[12]==undefined?"0":paramArray[12]; 
		obj.param14 =  paramArray[13]==undefined?"0":paramArray[13]; 
		obj.param15 =  paramArray[14]==undefined?"0":paramArray[14]; 
		obj.param16 =  paramArray[15]==undefined?"0":paramArray[15]; 
		obj.param17 =  paramArray[16]==undefined?"0":paramArray[16]; 
		obj.param18 =  paramArray[17]==undefined?"0":paramArray[17]; 
		obj.param19 =  paramArray[18]==undefined?"0":paramArray[18]; 
		obj.param20 =  paramArray[19]==undefined?"0":paramArray[19]; 			 
		obj.param21 = paramArray[20]==undefined?"0":paramArray[20];  
		obj.param22 =  paramArray[21]==undefined?"0":paramArray[21]; 
		obj.param23 =  paramArray[22]==undefined?"0":paramArray[22]; 
		obj.param24 =  paramArray[23]==undefined?"0":paramArray[23]; 
		obj.param25 =  paramArray[24]==undefined?"0":paramArray[24]; 
		obj.param26 =  paramArray[25]==undefined?"0":paramArray[25]; 
		obj.param27 =  paramArray[26]==undefined?"0":paramArray[26]; 
		obj.param28 =  paramArray[27]==undefined?"0":paramArray[27]; 
		obj.param29 =  paramArray[28]==undefined?"0":paramArray[28]; 
		obj.param30 =  paramArray[29]==undefined?"0":paramArray[29]; 
		obj.param31 =  paramArray[30]==undefined?"0":paramArray[30]; 
		obj.param32 =  paramArray[31]==undefined?"0":paramArray[31]; 
		obj.param33 =  paramArray[32]==undefined?"0":paramArray[32]; 
		obj.param34 =  paramArray[33]==undefined?"0":paramArray[33]; 
		obj.param35 =  paramArray[34]==undefined?"0":paramArray[34]; 
		obj.param36 =  paramArray[35]==undefined?"0":paramArray[35]; 
		obj.param37 =  paramArray[36]==undefined?"0":paramArray[36]; 
		obj.param38 =  paramArray[37]==undefined?"0":paramArray[37]; 
		obj.param39 =  paramArray[38]==undefined?"0":paramArray[38]; 
		obj.param40 =  paramArray[39]==undefined?"0":paramArray[39]; 
		obj.param41 =  paramArray[40]==undefined?"0":paramArray[40]; 
		obj.param42 =  paramArray[41]==undefined?"0":paramArray[41]; 
		obj.param43 =  paramArray[42]==undefined?"0":paramArray[42]; 
		obj.param44 =  paramArray[43]==undefined?"0":paramArray[43]; 
		obj.param45 =  paramArray[44]==undefined?"0":paramArray[44]; 
		obj.param46 =  paramArray[45]==undefined?"0":paramArray[45]; 
		obj.param47 =  paramArray[46]==undefined?"0":paramArray[46]; 
		obj.param48 =  paramArray[47]==undefined?"0":paramArray[47]; 
		obj.param49 =  paramArray[48]==undefined?"0":paramArray[48]; 
		obj.param50 =  paramArray[49]==undefined?"0":paramArray[49]; 
		obj.param51 =  paramArray[50]==undefined?"0":paramArray[50]; 
		obj.param52 =  paramArray[51]==undefined?"0":paramArray[51]; 
		obj.param53 =  paramArray[52]==undefined?"0":paramArray[52]; 
		obj.param54 =  paramArray[53]==undefined?"0":paramArray[53]; 
		obj.param55 =  paramArray[54]==undefined?"0":paramArray[54]; 
		obj.param56 =  paramArray[55]==undefined?"0":paramArray[55]; 
		var lst=[];	 var json={};lst.push(obj);json['content']=lst; 
		return json ;
} 
//****************************************************************
// Description: sInputString 为输入字符串，iType为类型，分别为
// 0 - 去除前后空格; 1 - 去左边空格; 2 - 去右边空格
//****************************************************************
function cTrim(sInputString,iType)
{
var sTmpStr = ' '
var i = -1
if(iType == 0 || iType == 1)
{
while(sTmpStr == ' ')
{
++i
sTmpStr = sInputString.substr(i,1)
}
sInputString = sInputString.substring(i)
}
if(iType == 0 || iType == 2)
{
sTmpStr = ' '
i = sInputString.length
while(sTmpStr == ' ')
{
--i
sTmpStr = sInputString.substr(i,1)
}
sInputString = sInputString.substring(0,i+1)
}
return sInputString
}

// 
function initTableData(src){
    //var src = document.getElementById("tabApplyInfos");
	var rowCount = src.rows.length;
	for(var j=1;j<rowCount;j++)
	{
		src.deleteRow();
	}
 }
 // 统一使用检查className为空的
function checkPageItemNotNull(obj){

	for(var i =0;i<obj.length;i++){ 
		if(obj.eq(i).val() == ""  ){  
			return false;
		}
	}
	return true ;
}
 

function takeTypeDataListV4(control,json,isSelect){
	// 请选择
	if(isSelect == '1'){
		control.append("<option value='-9'>请选择</option>");
	}    
	for(var i=0;i< json.length;i++){
		control.append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	}
 }
function takeTypeDataListV3(control,url,isSelect,edom){
	var json = AjaxExchangeBackTextData(url,edom);  
	json = JSON.parse(json);
	// 请选择
	if(isSelect == '1'){
		control.append("<option value='-9'>请选择</option>");
	}    
	for(var i=0;i< json.length;i++){
		control.append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	}
 }
 
function getTotalPageCnt(nodeslength,len){
    if(len >= nodeslength) {
       return 1;
     }  
    var ye = nodeslength%len ;
    if(ye == 0){
       return nodeslength / len ;
    }else{
       return Math.floor(nodeslength/len) +1 ;
    }
}


loadXML = function(xmlFile){
        var xmlDoc=null;
        //判断浏览器的类型
        //支持IE浏览器
        if(!window.DOMParser && window.ActiveXObject){
            var xmlDomVersions = ['MSXML.2.DOMDocument.6.0','MSXML.2.DOMDocument.3.0','Microsoft.XMLDOM'];
            for(var i=0;i<xmlDomVersions.length;i++){
                try{
                    xmlDoc = new ActiveXObject(xmlDomVersions[i]);
                    break;
                }catch(e){
                }
            }
        }
        //支持Mozilla浏览器
        else if(document.implementation && document.implementation.createDocument){
            try{
                /* document.implementation.createDocument('','',null); 方法的三个参数说明
                 * 第一个参数是包含文档所使用的命名空间URI的字符串； 
                 * 第二个参数是包含文档根元素名称的字符串； 
                 * 第三个参数是要创建的文档类型（也称为doctype）
                 */
                xmlDoc = document.implementation.createDocument('','',null);
            }catch(e){
            }
        }
        else{
            return null;
        }

        if(xmlDoc!=null){
            xmlDoc.async = false;
            xmlDoc.load(xmlFile);
        }
        return xmlDoc;
    }
    
    
    function isNullCheck(obj){
    
      if(obj==''||obj==null||obj=='undefined'||obj=='-1'||obj=='-9'){
          return true ;
      }
      return false ;
    }
    
// // Ajax 文件下载
//    $.download = function(url, data, method){    // 获得url和data 
//    if( url && data ){ 
//        // data 是 string 或者 array/object
//        data = typeof data == 'string' ? data : jQuery.param(data);        // 把参数组装成 form的  input
//        var inputs = '';
//        jQuery.each(data.split('&'), function(){ 
//            var pair = this.split('=');
//            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ pair[1] +'" />'; 
//        });        // request发送请求
//        jQuery('<form action="'+ url +'" method="'+ (method||'post') +'">'+inputs+'</form>')
//        .appendTo('body').submit().remove();
//    };
//} ;


// 删除表头以外的行
function removeTableData(obj,cnt){ 
	obj.find("tr").remove();
}
// 创建一个30长度的数组Json,并做ajax请求
function createJsonAndAjax(action,arr, ff,dataFormat) {

	var eJson = createParamJson(arr);
	eJson = JSON.stringify(eJson);  
	var args = {
		"context" : eJson
	}; 
	baidu.post(action, args, function(data) {
		ff(data)
	},  dataFormat);
}
// 上报专用
//创建一个30长度的数组Json,并做ajax请求
function createJsonAndAjaxRpt(action,arr, succ,dataFormat) { 
	var eJson = createParamJsonRpt(arr); 
	eJson = JSON.stringify(eJson);  
	var args = {	"context" : eJson	}; 
//	baidu.post(action, args, function(data) { 	ff(data) 	},  dataFormat);
	$.ajax({
		url:action,
		type:"POST",
		async:false,  // false:同步请求,同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		data:args,
		dataType:dataFormat,
		success:function(data, textStatus, jqXHR){ 
			$("#background,#progressBar").hide();
			succ(data);
		} ,error: function(jqXHR, textStatus, errorMsg){
			$("#background,#progressBar").hide();
			alertMsg.error(errorMsg);
		},
		beforeSend:function(jqXHR){
			$("#background,#progressBar").show();		  
		}
	});
}
function createJsonAndAjaxRpt2(action,arr, ff,dataFormat) { 
	var eJson = createParamJsonRpt(arr); 
	eJson = JSON.stringify(eJson);  
	var args = {	"context" : eJson	}; 
	baidu.post(action, args, function(data) {
		ff(data)
	},  dataFormat);
}
function createJsonAndAjaxRptAjax(action,arr, ff,dataFormat) { 
	var eJson = createParamJsonRpt(arr); 
	eJson = JSON.stringify(eJson);  
	var args = {
		"context" : eJson
	}; 
	baidu.ajax({url:action, type:"POST",data:args,async:false,success: function(data) {
		ff(data)
	}
	});
}
function renderBtnsToDivV0(obj,action,array){
	var eJson = createParamJson(array); 
	eJson = JSON.stringify(eJson); 
	var text = AjaxExchangeBackTextData(action,eJson); 
	obj.html(text);
}

function renderBtnsToDiv(obj,action,array){ 
	var eJson = createParamJson(array);
	eJson = JSON.stringify(eJson); 
	var args = {
		"context" : eJson
	};
	baidu.post(action, args, function(data) {   
		obj.html(data);
	},  'html');
} 
function initPageSelectList(obj,pageNum,obj2,tatol,thisPageNo){ 
    obj.find("option").remove(); 
	for(var i =0;i<pageNum;i++){  
		var j = i+1 ;
		if(thisPageNo == i){
			obj.append("<option selected value='"+i+"'>"+j+"</option>"); 
		}else{
		obj.append("<option value='"+i+"'>"+j+"</option>"); }
	}	  
	obj2.text(tatol);
	if(thisPageNo > 0){ 
		obj.val(thisPageNo);
	}
}


function takeTypeDataList(control,action,isSelect,array){ 
	var eJson = createParamJson(array);
	eJson = JSON.stringify(eJson); 
	var args = {
		"context" : eJson
	};
	baidu.post(action, args, function(json) {   
		// 请选择
		if(isSelect == '1'){
			control.append("<option value='-9'>---请选择---</option>");
		}    
		for(var i=0;i< json.length;i++){
			control.append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
		}
	},  'json');
 } 