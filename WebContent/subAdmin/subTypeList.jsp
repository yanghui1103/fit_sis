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
				 $("#type_cd", navTab.getCurrentPanel()).attr("checked",false);
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
		<div  class="pageContent "   defH="40">
			<p>
				<label>角色：</label>
				<select id="role_id"  name="role_id"   style="float:left" > 
				</select>
			</p>      
		 </div>
		 <div  class="panel "   defH="40">	
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="101" /><span>测试类型1</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="102" /><span>测试类型2</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="104" /><span>测试类型4</span>
			</p>   
		 </div>
		 <div  class="panel "   defH="40">	
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS01" /><span>4050人员东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS02" /><span>城镇残疾人东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS03" /><span>零就业家庭成员东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS04" /><span>低保城镇失业人员东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS05" /><span>失地农民东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS06" /><span>连续失业一年大龄人员东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS07" /><span>高校毕业大于3年未就业蒙古族或贫困户人员东胜</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="DS08" /><span>高校毕业一年未就业人员东胜</span>
			</p>   
		 </div>
		 <div  class="panel "   defH="40">	
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ01" /><span>4050人员市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ02" /><span>城镇残疾人市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ03" /><span>零就业家庭成员市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ04" /><span>低保城镇失业人员市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ05" /><span>失地农民市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ06" /><span>连续失业一年大龄人员市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ07" /><span>高校毕业大于3年未就业蒙古族或贫困户人员市</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="SQ08" /><span>高校毕业一年未就业人员市</span>
			</p>   
		 </div>
		 <div  class="panel "   defH="40">	
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ01" /><span>4050人员准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ02" /><span>城镇残疾人准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ03" /><span>零就业家庭成员准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ04" /><span>低保城镇失业人员准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ05" /><span>失地农民准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ06" /><span>连续失业一年大龄人员准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ07" /><span>高校毕业大于3年未就业蒙古族或贫困户人员准旗</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="ZQ08" /><span>高校毕业一年未就业人员准旗</span>
			</p>
		</div> 
		 <div  class="panel "   defH="40">	
			<p>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ01" /><span>4050人员康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ02" /><span>城镇残疾人康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ03" /><span>零就业家庭成员康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ04" /><span>低保城镇失业人员康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ05" /><span>失地农民康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ06" /><span>连续失业一年大龄人员康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ07" /><span>高校毕业大于3年未就业蒙古族或贫困户人员康巴什</span>
			<input type=checkbox id="type_cd"  name="type_cd"  value="KQ08" /><span>高校毕业一年未就业人员康巴什</span>
			</p>
		</div> 
				
		<input type="hidden" id="staff_id" readonly value="<%=((LoginUser)session.getAttribute("LoginUser")).getStaff_id() %>"  />
		<div class="subBar" id="subBar">
		</div>
		</div>	
</body>
</html>