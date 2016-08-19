<%@ page language="java" import="java.util.*,com.bw.fit.common.models.LoginUser" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

<script language="javascript" type="text/javascript" >
$(document).ready(function(){ 
	var arr = new Array(60);
	 for(var i in arr.length){
		 arr[i] = "-9";		 
	 }	 arr[0] = "ROLE"; 
	 takeTypeDataList($("#giveRole_role_cd"),"getSysItems.action","1", arr);	
}); 

$('#treeAuth').click(function(){	
		$('#dog').attr("href","system/powerSet.html");		
	});
    function getZtree(){    
    	var giveRole_role_cd = $("#giveRole_role_cd");
    	if(giveRole_role_cd == "-9"){
    		alertMsg.info("请选择角色");return ;
    	}
    	window.showModalDialog("system/powerSet.html",$("#giveRole_role_cd").val(),"height:600, width:220, minH:40, minW:50, total:20, max:false, mask:false, resizable:true, drawable:true, maxable:true,minable:true,fresh:true");
    }
    // 
    $("#giveRole_role_cd", navTab.getCurrentPanel()).change(function() {	 
    	var giveRole_role_cd = $("#giveRole_role_cd", navTab.getCurrentPanel()).val() ;
    	var arr = new Array(60);
    	for(var i in arr.length){
    		 arr[i] = "-9";		 
    	}arr[0] = giveRole_role_cd;arr[1] = $("#staff_id", navTab.getCurrentPanel()).val() ;
    	createJsonAndAjaxNew("checkRoleAndStaffValidate.action", arr,dealAfter,'json',true); 
    });
    function dealAfter(data){
    	alertToUserMsg(data,function(data){ 
    	}); 
    }
</script>
  </head>
  
  <body>
<div class="pageFormContent"   >
			<p>
				<label>待选角色：</label>
				<select name="giveRole_role_cd" id="giveRole_role_cd" class="required  "  >
				</select>
			</p>
			<p>
				<input type="hidden" id="staff_id" readonly value="<%=((LoginUser)session.getAttribute("LoginUser")).getStaff_id() %>"  />
				<input type="hidden" id="org_id" readonly  />
				<input type="hidden"  id="org_name"   readonly  />
				<a class="btnLook" style="float:left" href="system/powerSet.html" lookupGroup="orgLookup">权限分配</a>		
			</p>
		</div>		
  </body>
</html>
