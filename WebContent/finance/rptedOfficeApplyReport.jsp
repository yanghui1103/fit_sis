<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"   src="finance/rptedOfficeApplyReport.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "401";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr); 
	arr[0] = "SUBCYCLE"; 
	 takeTypeDataList($("#sub_cycle",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
});

function dealDaoRpt(data){  
	if(data.res !='2'){alertMsg.error(data.msg);return ;}
	window.location.href = "<%=basePath %>/uploadfiles/"+ data.msg ;
}

</script>
</head>
<body>

<div class="pageFormContent"> 
		<table >
			<tr>   
			<td>
				<label>机构：</label>
				<input type="text"  id="org_name"  ename="机构"   style="float:left"  readonly name="orgLookup.orgName" value="" suggestFields="orgNum,orgName"   lookupGroup="orgLookup" />
				<a   style="float:left"  class="btnLook" href="system/orgStructure_qry.jsp?custom_org_function_id=401&select_org_multi=0&select_cascade=''&unselect_cascade=''"   lookupGroup="orgLookup">查找带回</a>		
				<input type="hidden"    readonly="readonly"   style="float:left"  id="org_id" name="orgLookup.id" />
			</td>  
			<td>
				<label>申报周期：</label>
				<select id="sub_cycle"  name="sub_cycle"      style="float:left" > 
			</select></td></tr>
			</table>
	<div class="subBar" id="subBar">  
	</div> 
</div>
</body>
</html>