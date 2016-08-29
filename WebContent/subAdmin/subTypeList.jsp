<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*,com.bw.fit.common.models.LoginUser"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "202";arr[1] = "navTab"; 
	 renderBtnsToDiv($("#subBar", navTab.getCurrentPanel()),'getAuthorityBtnsByThisUser.action',arr); 
	 arr[0] = "ROLE"; 
	 takeTypeDataList($("#role_id"),"getSysItems.action","1", arr);	
});

$("#role_id", navTab.getCurrentPanel()).change(function() {	 
	var giveRole_role_cd = $("#role_id", navTab.getCurrentPanel()).val() ;
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = giveRole_role_cd;arr[1] = $("#staff_id", navTab.getCurrentPanel()).val() ;
	createJsonAndAjaxNew("checkRoleAndStaffValidate.action", arr,dealAfter,'json',true); 
});
function dealAfter(data){
	alertToUserMsg(data,function(data){ 
		var res = data.res ;
		if(res=="1"){ // 如果权限校验不通过，就自动选择请选择
			$("#role_id", navTab.getCurrentPanel()).val("-9");
		}else{ 
			var array = new Array($("#role_id", navTab.getCurrentPanel()).val());
			createJsonAndAjaxNew("getRoleDeSubType.action", array,function(data){
				if(data.res!="2"){
					 return ;
				}
				var json = data.list ;
				for(var j =0;j<json.length;j++){
					 $("#type_cd[value='"+json[j].sub_type_cd+"']").attr("checked","checked");
				}
			},'json',false); 
		}
	}); 
}

baidu(document).on('click', '#save202', function() {
	alertMsg.confirm("是否确认角色与补贴类型关联！", {
		 okCall: function(){
				createJsonAndPost2Java("createRole2SubType.action",$("#createForm", navTab.getCurrentPanel()), function(data){
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
				<label>角色：</label>
				<select id="role_id"  name="role_id"   style="float:left" > 
				</select>
			</p>   
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="101" /><span>类1</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="102" /><span>类2</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="103" /><span>类3</span>
			</p>
		</div>
		</form>
				
		<input type="hidden" id="staff_id" readonly value="<%=((LoginUser)session.getAttribute("LoginUser")).getStaff_id() %>"  />
		<div class="subBar" id="subBar">
		</div>
		</div>	
</body>
</html>