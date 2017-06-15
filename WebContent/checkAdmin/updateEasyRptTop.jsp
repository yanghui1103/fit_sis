<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<script type="text/javascript">
$(function(){
	
	var allJlId = $("input[name='allJlId']:checked").val();
	if($("input[name='allJlId']:checked").length <1){
		navTab.closeCurrentTab();
		alertMsg.error("请选择记录");return ;
	} 
	var array = new Array();
	array[0] = allJlId ;
	createJsonAndAjaxNew('getRptDetail.action', array,dealInitCheckTopPage,'json',true  ) ;
});
function dealInitCheckTopPage(data){ 
	if(data.res!='2'){
		navTab.closeCurrentTab();
		alertToPageMsg(data);return ;}
	var json = data.list ;     
	$("#person_name",navTab.getCurrentPanel()).val(json[0].person_name);
	$("#card_id",navTab.getCurrentPanel()).val(json[0].card_id);
	$("#phone",navTab.getCurrentPanel()).val(json[0].phone);
	$("#gender",navTab.getCurrentPanel()).val(json[0].gender);
	$("#nation",navTab.getCurrentPanel()).val(json[0].nation);
	$("#first_date",navTab.getCurrentPanel()).val(json[0].first_time);  
	$("#unit_name",navTab.getCurrentPanel()).val(json[0].unit_name);
// 	$("#unit_type",navTab.getCurrentPanel()).val(json[0].unit_type_cd);
// 	$("#rpt_type",navTab.getCurrentPanel()).val(json[0].rpt_type_cd);
// 	$("#sub_cycle",navTab.getCurrentPanel()).val(json[0].cycle_cd);
	$("#start_date",navTab.getCurrentPanel()).val(json[0].start_date);
	$("#end_date",navTab.getCurrentPanel()).val(json[0].end_date);  	 
	$("#flow_id",navTab.getCurrentPanel()).val(json[0].fdid);
	$("#fdid",navTab.getCurrentPanel()).val(json[0].fdid);
	$("#beforeAuditor",navTab.getCurrentPanel()).val(json[0].beforeAuditor);
	$("#beforeAudit",navTab.getCurrentPanel()).val(json[0].beforeAudit);
	
	initSelectedOpt(json[0].unit_type_cd,json[0].rpt_type_cd,json[0].cycle_cd) ;
}


function initSelectedOpt(unit_type_cd,rpt_type_cd,cycle_cd){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}  
	 arr[0] = "SUB_TYPE"; 
	 takeTypeDataListV2($("#rpt_type",navTab.getCurrentPanel()),"getSysItems.action","1", arr,rpt_type_cd);	
	 arr[0] = "UNIT_TYPE"; 
	 takeTypeDataListV2($("#unit_type",navTab.getCurrentPanel()),"getSysItems.action","1", arr,unit_type_cd);	
	 arr[0] = "CHECKRT"; 
	 takeTypeDataList($("#check_result",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
	 arr[0] = "SUBCYCLE"; 
	 takeTypeDataListV2($("#sub_cycle",navTab.getCurrentPanel()),"getSysItems.action","1", arr,cycle_cd);	 
	 $("#updateERpt",navTab.getCurrentPanel()).initUI();
}
$('#getHis', navTab.getCurrentPanel()).click( function() {	   
	var flow_id = $("#flow_id",navTab.getCurrentPanel()).val();  
	if(flow_id=="") {alertMsg.info("流程id无效");return ;} 
	$(".checkHis").attr("href","checkAdmin/checkHistory.jsp?flow_id="+flow_id+"");	
	$(".checkHis").trigger("click"); 
}); 

//获取附件页 
$('#getPhoto', navTab.getCurrentPanel()).click( function() {	   
	var flow_id = $("#flow_id",navTab.getCurrentPanel()).val();  
	if(flow_id=="") {alertMsg.info("无图片附件");return ;} 
	$(".ahrefCss").attr("href","system/attachmentPage.jsp?isRead=1&foregin_id="+flow_id+"");	
	$(".ahrefCss").trigger("click"); 
}); 
// 
$("#delete", navTab.getCurrentPanel()).click( function() {	   
	alertMsg.confirm("是否确认删除该记录(不可恢复)?", {
		 okCall: function(){ 
			 createJsonAndPost2Java('deleteEasyRpt.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 navTab.closeCurrentTab();
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
}); 


$("#update", navTab.getCurrentPanel()).click( function() {	   
	alertMsg.confirm("是否确认修改该记录?", {
		 okCall: function(){ 
			 createJsonAndPost2Java('updateEasyRpt.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 navTab.closeCurrentTab();
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
}); 

$('#getPersonInfo', navTab.getCurrentPanel()).click( function() {	  
	var card_id = $("#card_id",navTab.getCurrentPanel()).val();   
	if(card_id==""){alertMsg.error("身份证信息不存在");return ;}
	var array = new Array(card_id) ;
	createJsonAndAjaxNew('getPersonRptedInfo.action', array,function(data){
		 if(data.res!="2"){
			 alertMsg.info(data.msg); 
			 return ;
		 } 
		 var json = data.list ;   
		 if(json[0].state_code !="2"){
			 $("#card_id",navTab.getCurrentPanel()).val("");
			 alertMsg.info(json[0].state); 
		 }  
		 var array = getPersonTypeName(json[0].first_zhousui,json[0].gender) ; 
		 if(array[0] == "-9"){
			 $("#card_id",navTab.getCurrentPanel()).val("");
			 alertMsg.info(array[1]); 
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
	 } 
}
</script>
</head>
<body>

	<div id="updateERpt" class="pageFormContent">
	<form method="post" action=""  id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="116">
			<p>
				<label>申报人姓名：</label>
				<input   id="person_name" name="person_name"   class="required" type="text"    style="float:left"   readonly    />
			</p>  
			<p>
				<label>身份证号码：</label>
				<input   id="card_id" name="card_id"   class="required" type="text"    style="float:left"   readonly    />
			</p> 
			<p>
				<label>申报人性别：</label>				
				<select id="gender"  name="gender" disabled  style="float:left" > 
				<option value='0'>女</option>
				<option value='1'>男</option>
				</select>
			</p>    
			<p>
				<label>民族：</label>
				<input   id="nation" name="nation"   type="text"    style="float:left"     readonly  />
			</p>    
			<p>
				<label>联系电话：</label>
				<input   id="phone" name="phone"    type="text"    style="float:left" maxlength=12      />
			</p>     
			<p>
				<label>初次申报日期：</label>
				<input   id="first_date" name="first_date"     type="text"    style="float:left"     readonly  />
			</p>    
		<div class="divider"></div>	
		<p>
				<label>就业单位名称：</label>
				<input   id="unit_name" name="unit_name"   class="required" type="text"     style="float:left"  maxlength=20     />
			</p>  
		<p>
				<label>就业单位类型：</label>				
				<select id="unit_type"  name="unit_type"     style="float:left" > 
				</select>
			</p>  
		<p>
				<label>补贴类型：</label>				
				<select id="rpt_type"  name="rpt_type"    style="float:left" > 
				</select>
			</p> 
		<p>
				<label>申报周期：</label>				
				<select id="sub_cycle"  name="sub_cycle"    style="float:left" > 
				</select>
			</p>   
				<p>
				<label>票据缴费开始月份：</label>
				<input id=start_date ename="开始日期" name="start_date" type="text" class="date required "    datefmt='yyyyMM' style="float:left"   />
			</p>
				<p>
				<label>票据缴费结束月份：</label>
				<input id=end_date ename="结束日期"  name="end_date" type="text" class="date required "   datefmt='yyyyMM' style="float:left"   />
			</p>
		<p>
			<label>上次审核人：</label>
			<input type=text id="beforeAuditor"    readonly/>
		</p>
		<p>
			<label>上次审核意见：</label>
			<input type=text id="beforeAudit"    readonly/>
		</p><p>
		<input   type="hidden"   id="fdid"  name="fdid"  />
		<input   type="hidden"   id="flow_id"  name="flow_id"  />
		<input   type="hidden"   id="pass_type"  name="pass_type"  value="2" />
		<button id="getPhoto"   type="button" >查看附件</button>
		<input type="button"  id="getPersonInfo"  value="申领概况"/> 
		</p>
		</div> 
		<div class="formBar" id="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button"  id="update" >保存</button>
							</div>
						</div></li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button"  id="delete" >确认删除</button>
							</div>
						</div></li>
				</ul>
		</div>
		</form>
</div> 	
 
</body>
</html>