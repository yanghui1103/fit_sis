<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<script type="text/javascript">
$(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	} 
	 arr[0] = "ROLE2SUBTYPE"; 
	 takeCustomValueByOther($("#rpt_type",navTab.getCurrentPanel()),"getCustomValueByOther.action","1", arr);	
	 arr[0] = "UNIT_TYPE"; 
	 takeTypeDataList($("#unit_type",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
	 arr[0] = "CHECKRT"; 
	 takeTypeDataList($("#check_result",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
	 arr[0] = "Company2SubCycle"; 
	 takeCustomValueByOther($("#sub_cycle",navTab.getCurrentPanel()),"getCustomValueByOther.action","1", arr);	
	 
	var allJlId = $("input[name='allJlId']:checked").val();
	if($("input[name='allJlId']:checked").length <1){
		alertMsg.error("请选择记录");return ;
	} 
	var array = new Array();
	array[0] = allJlId ;
	createJsonAndAjaxNew('getThisCheckInfoAll.action', array,dealInitCheckTopPage,'json',true  ) ;
});
function dealInitCheckTopPage(data){ 
	if(data.res!='2'){
		alertToPageMsg(data);return ;}
	var json = data.binfo_list ; 
	$("#person_name",navTab.getCurrentPanel()).val(json[0].person_name);
	$("#card_id",navTab.getCurrentPanel()).val(json[0].card_id);
	$("#phone",navTab.getCurrentPanel()).val(json[0].person_phone);
	$("#gender",navTab.getCurrentPanel()).val(json[0].person_gender);
	$("#nation",navTab.getCurrentPanel()).val(json[0].person_nation);
	$("#first_date",navTab.getCurrentPanel()).val(json[0].first_time); 
	json = data.flow_list ; 
	$("#unit_name",navTab.getCurrentPanel()).val(json[0].unit_name);
	$("#unit_type",navTab.getCurrentPanel()).val(json[0].unit_type);
	$("#rpt_type",navTab.getCurrentPanel()).val(json[0].rpt_type);
	$("#sub_cycle",navTab.getCurrentPanel()).val(json[0].sub_cycle);
	$("#start_date",navTab.getCurrentPanel()).val(json[0].pay_start);
	$("#end_date",navTab.getCurrentPanel()).val(json[0].pay_end);   
	data.photo_list ;
	$("#flow_id",navTab.getCurrentPanel()).val(data.fdid);
	$("#fdid",navTab.getCurrentPanel()).val(data.fdid);
}


//获取附件页 
$('#getPhoto', navTab.getCurrentPanel()).click( function() {	   
	var flow_id = $("#flow_id",navTab.getCurrentPanel()).val();  
	if(flow_id=="") {alertMsg.info("无图片附件");return ;} 
	$(".ahrefCss").attr("href","system/attachmentPage.jsp?isRead=0&foregin_id="+flow_id+"");	
	$(".ahrefCss").trigger("click"); 
}); 
//确认修改,立即进入初审
$("#saveToNext", navTab.getCurrentPanel()).click( function() {	   
	alertMsg.confirm("是否确认对此记录修改?", {
		 okCall: function(){ 
			 createJsonAndPost2Java('checkRpt.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
}); 
$("#deleteRt", navTab.getCurrentPanel()).click( function() {	   
	alertMsg.confirm("是否确认对此记录作废?", {
		 okCall: function(){ 
			 createJsonAndPost2Java('deleteRptRecond.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
}); 
</script>
</head>
<body>

	<div class="pageContent">
	<form method="post" action=""  id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
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
				<input   id="phone" name="phone"    type="text"    style="float:left" maxlength=12  readonly    />
			</p>     
			<p>
				<label>初次申报日期：</label>
				<input   id="first_date" name="first_date"     type="text"    style="float:left"     readonly  />
			</p>    
		<div class="divider"></div>	
		<p>
				<label>就业单位名称：</label>
				<input   id="unit_name" name="unit_name"   class="required" type="text"   readonly  style="float:left"  maxlength=20     />
			</p>  
		<p>
				<label>就业单位类型：</label>				
				<select id="unit_type"  name="unit_type" disabled   style="float:left" > 
				</select>
			</p>  
		<p>
				<label>补贴类型：</label>				
				<select id="rpt_type"  name="rpt_type"  disabled style="float:left" > 
				</select>
			</p> 
		<p>
				<label>申报周期：</label>				
				<select id="sub_cycle"  name="sub_cycle"  disabled style="float:left" > 
				</select>
			</p>   
				<p>
				<label>票据缴费开始月份：</label>
				<input id=start_date ename="开始日期" name="start_date" type="text" class="date required "  disabled datefmt='yyyyMM' style="float:left"  readonly="true" />
			</p>
				<p>
				<label>票据缴费结束月份：</label>
				<input id=end_date ename="结束日期"  name="end_date" type="text" class="date required " disabled  datefmt='yyyyMM' style="float:left"  readonly="true" />
			</p><p>
		<input   type="hidden"   id="fdid"  name="fdid"  />
		<input   type="hidden"   id="flow_id"  name="flow_id"  />
		<input   type="hidden"   id="pass_type"  name="pass_type"  value="1" />
		<button id="getPhoto"   type="button" >查看附件</button>
		</p>
		</div> 
		</form>
</div> 	

		<div   style="display:none;">
		<a class="button ahrefCss"  target="dialog" rel="dlg_page101" mask="true" title="附件"><span>附件</span></a> 
		</div>
		
		<div class="formBar" id="subBar">
		<button type=button id="saveToNext">修改确认</button>
		<button type=button id="deleteRt">作废确认</button>
		</div>
</body>
</html>