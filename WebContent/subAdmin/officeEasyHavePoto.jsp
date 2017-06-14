<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.common.utils.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="subAdmin/officeAptNTCamera.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	Init();
	var hide_id = $("#hide_id", navTab.getCurrentPanel()).val();
	 $("#flow_id", navTab.getCurrentPanel()).val(hide_id);
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}
	arr[0] = "212";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr);
	 arr[0] = "ROLE2SUBTYPE"; 
	 takeCustomValueByOther($("#rpt_type",navTab.getCurrentPanel()),"getCustomValueByOther.action","1", arr);	
	 arr[0] = "UNIT_TYPE"; 
	 takeTypeDataList($("#unit_type",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
	 arr[0] = "Company2SubCycle"; 
	 takeCustomValueByOther($("#sub_cycle",navTab.getCurrentPanel()),"getCustomValueByOther.action","1", arr);	
});
  $('#getCardInfo',navTab.getCurrentPanel()).click(function() {	   	  
	readCard();
	 $('#getPersonInfo', navTab.getCurrentPanel()).trigger("click");
	 // 获取人员类型
	 var card_id = $("#card_id",navTab.getCurrentPanel()).val();
	 var array = new Array(card_id) ;
	 createJsonAndAjaxNew('getPsnBaseInfo.action', array, function(data){ 
		 if(data.res=="2"){
			 var json = data.list ;
			 var first_date = json[0].first_time;
			 var person_gender = json[0].person_gender;
			 first_date = first_date.substring(0,4);
			 var birst = card_id.substring(6,10); 
			 var age =  parseInt(first_date) - parseInt(birst) ; 
			 var ss = getPersonTypeName(age,person_gender);
			 $("#p_type",navTab.getCurrentPanel()).val("");
			 $("#p_type",navTab.getCurrentPanel()).val(ss[1]);
		 }else{
			 // 如果是新人
			 var person_gender = $("#gender",navTab.getCurrentPanel()).val();
			 var date=new Date;
			 var year=date.getFullYear(); 
			 var birst = card_id.substring(6,10);
			 var age =   parseInt(year) - parseInt(birst);
			 var ss = getPersonTypeName(age,person_gender);
			 $("#p_type",navTab.getCurrentPanel()).val("");
			 $("#p_type",navTab.getCurrentPanel()).val(ss[1]);
		 }
	 },'JSON',false ) ;
});


// 保存申报信息
baidu(document).on('click', '#save212', function() {	  
	 var card_id = $("#card_id",navTab.getCurrentPanel()).val();
	 var gender = $("#gender",navTab.getCurrentPanel()).val();
	 var rpt_type = $("#rpt_type",navTab.getCurrentPanel()).val(); 
	 var sub_cycle = $("#sub_cycle",navTab.getCurrentPanel()).val(); 
	 var unit_type = $("#unit_type",navTab.getCurrentPanel()).val(); 
	 $("#person_gender",navTab.getCurrentPanel()).val(gender);
	 if(card_id.length!=18){alertMsg.info("身份证号码应该为18位");return ;}
	 if(gender=="-9"){alertMsg.info("请选择申报人性别");return ;}
	 if(rpt_type=="-9"){alertMsg.info("请选择申报类型");return ;}
	 if(sub_cycle=="-9"|| sub_cycle==""||sub_cycle=="undefined" ){alertMsg.info("请选择申报周期");return ;}
	 if(unit_type=="-9"){alertMsg.info("请选择就业单位类型");return ;}
	alertMsg.confirm("是否确认录入该人的申报数据！", {
		 okCall: function(){ 
			 createJsonAndPost2Java('createEasyRptRecond.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 navTab.closeCurrentTab();
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
});

//拍摄仪上传
$('#getNTPhoto', navTab.getCurrentPanel()).click( function() {	  
	var flow_id = $("#flow_id",navTab.getCurrentPanel()).val();  
	if(flow_id=="") {return ;}
	$(".nthrefCss").attr("href","subAdmin/CapWeb.htm");	
	$(".nthrefCss").trigger("click"); 
}); 
//获取附件页 
$('#getPhoto', navTab.getCurrentPanel()).click( function() {	  
	var flow_id = $("#flow_id",navTab.getCurrentPanel()).val();  
	if(flow_id=="") {return ;}
	$(".ahrefCss").attr("href","system/attachmentPageNtc.jsp?isRead=0&foregin_id="+flow_id+"");	
	$(".ahrefCss").trigger("click"); 
}); 

$('#getPersonInfo', navTab.getCurrentPanel()).click( function() {	  
	var card_id = $("#card_id",navTab.getCurrentPanel()).val();   
	if(card_id==""){alertMsg.error("请刷身份证卡片,并点击读取按钮");return ;}
	var array = new Array(card_id) ;
	createJsonAndAjaxNew('getPersonRptedInfo.action', array,function(data){
		 if(data.res!="2"){
			 alertMsg.info(data.msg); 
			 // 如果是新来的人员,那么首先校验他年龄是否符合，可能有高校毕业生所以不做下面这事了
			 // checkNewPerson();
			 return ;
		 } 
		 var json = data.list ;   
		 if(json[0].state_code !="2"){
			 $("#card_id",navTab.getCurrentPanel()).val("");
			 alertMsg.info(json[0].state); 
		 }   
			$(".rptedInfos").attr("href","subAdmin/rpterGaikuangInfos.jsp?card_id="+card_id+"");	
			$(".rptedInfos").trigger("click"); 
	 },'JSON',true) ;
}); 

function checkNewPerson(){
	var myDate = new Date(); 
	var year = myDate.getFullYear();  
	var card_id = $("#card_id",navTab.getCurrentPanel()).val();   
	var gender = $("#gender",navTab.getCurrentPanel()).val();   
	var age  = (year -  card_id.substr(6,4));

	 var array = getPersonTypeName(age,gender) ; 
	 if(array[0] == "-9"){
		 $("#card_id",navTab.getCurrentPanel()).val("");
		 alertMsg.info(array[1]); 
		 return false ;
	 } 
	 return true ;
}
</script>
</head>
<body>
	<div class="pageContent">
	
  <object id="CardReader1" codebase="subAdmin/FirstActivex.cab#version=1,0,3,1" classid="CLSID:F225795B-A882-4FBA-934C-805E1B2FBD1B" >
	<param name="_Version" value="65536"/>
	<param name="_ExtentX" value="2646"/>
	<param name="_ExtentY" value="1323"/>
	<param name="_StockProps" value="0"/>
	<param name="port" value="串口1"/>
	<param name="PhotoPath" value=""/>
	<param name="ActivityLFrom" value=""/>
	<param name="ActivityLTo" value="" />
	</object> 
	<form method="post" action=""  id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>申报人姓名：</label>
				<input   id="person_name" name="person_name"   class="required" type="text"    style="float:left"     />
			</p>  
			<p>
				<label>身份证号码：</label>
				<input   id="card_id" name="card_id"   class="required" type="text"   maxlength=18  style="float:left"     />
			</p> 
			<p>
				<label>申报人性别：</label> 
				<select id="gender"  name="gender"     style="float:left"     > 
				<option value="-9">请选择</option>
				<option value="0">女</option>
				<option value="1">男</option>
				</select>
				<input  type="hidden"  id="person_gender" name="person_gender"     />
			</p>   
			<p>
				<label>出生日期：</label>
				<input   id="birchday" name="birchday"   class="required" type="text"    style="float:left"           />
			</p>   
			<p>
				<label>民族：</label>
				<input   id="nation" name="nation"   class="required" type="text"    style="float:left"           />
			</p>   
			<p>
				<label>签发机构：</label>
				<input   id="orgin" name="orgin"   class="required" type="text"    style="float:left"           />
			</p>    
			<p>
				<label>联系电话：</label>
				<input   id="phone" name="phone"   class="required" type="text"    style="float:left" maxlength=12           />
			</p>     
			<p>
				<label>人员类型：</label>
				<input   id="p_type" name="p_type"   class="required" type="text"    style="float:left" maxlength=12          />
			</p>  
		<div><input type="button"  id="getCardInfo"  value="读取身份证卡信息"/>
		<input type="button"  id="getPersonInfo"  value="申领概况"/></div>
		<div class="divider"></div>	
		<p>
				<label>就业单位名称：</label>
				<input   id="unit_name" name="unit_name"   class="required" type="text"    style="float:left"  maxlength=20         />
			</p>  
		<p>
				<label>就业单位类型：</label>				
				<select id="unit_type"  name="unit_type"   style="float:left" > 
				</select>
			</p>  
		<p>
				<label>补贴类型：</label>				
				<select id="rpt_type"  name="rpt_type"   style="float:left" > 
				</select>
			</p> 
		<p>
				<label>申报周期：</label>				
				<select id="sub_cycle"  name="sub_cycle"   style="float:left" > 
				</select>
			</p>   
				<p>
				<label>票据缴费开始月份：</label>
				<input id=start_date ename="开始日期" name="start_date" type="text" class="date required "  datefmt='yyyyMM' style="float:left"  ="true" />
			</p>
				<p>
				<label>票据缴费结束月份：</label>
				<input id=end_date ename="结束日期"  name="end_date" type="text" class="date required "   datefmt='yyyyMM' style="float:left"  ="true" />
			</p><p>
		<input   type="hidden"   id="flow_id"  name="flow_id"  />
		<input   type="hidden"   id="hide_id"   value="<%=PubFun.getUUID() %>" />
		<button id="getPhoto"   type="button" >附件列表</button>
<!--         <button id="getNTPhoto"   type="button" >拍摄上传</button>  -->
		</p>
		</form>
</div> 	

		<div   style="display:none;">
		<a class="button nthrefCss"  target="navTab" rel="dlg_page1f0" mask="true" title="高拍仪"><span>高拍仪</span></a> 
		<a class="button ahrefCss"  target="dialog" rel="dlg_page10" mask="true" title="附件"><span>附件</span></a> 
		<a class="button rptedInfos"  target="dialog" rel="dlg_page13" mask="true" title="申领概况"><span>申领概况</span></a> 
		</div>
		
		<div class="formBar" id="subBar">
		</div>
</body>
</html>