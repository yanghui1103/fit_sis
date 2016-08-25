<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	}arr[0] = "200";arr[1] = "navTab"; 
	 renderBtnsToDiv($("#subBar", navTab.getCurrentPanel()),'getAuthorityBtnsByThisUser.action',arr); 
});

baidu(document).on('click', '#save200', function() {	 
		alertMsg.confirm("是否确认新增该申报周期！", {
			 okCall: function(){ 
				 createJsonAndPost2Java('createSubCycle.action',$("#createForm", navTab.getCurrentPanel()),function(data){
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
				<label>周期名称：</label>
				<input ename="周期名称"  id="cycle_name" name="cycle_name"   class="required chinese" type="text"    style="float:left"  size="30" maxlength=10       alt="请输入汉字"/>
			</p> 
			<p>
				<label>机构名称：</label>
				<input type="text"  id="org_name"  ename="机构"   style="float:left" class="required" readonly name="orgLookup.orgName" value="" suggestFields="orgNum,orgName"   lookupGroup="orgLookup" />
				<a   style="float:left"  class="btnLook" href="system/orgStructure_qry.jsp?custom_org_function_id=200&select_org_multi=1&select_cascade=''&unselect_cascade=''"   lookupGroup="orgLookup">查找带回</a>		
				<input type="hidden"    readonly="readonly"   style="float:left"  id="org_id" name="orgLookup.id" />
			</p>
				<p>
				<label>开始日期：</label>
				<input id=start_date ename="开始日期" name="start_date" type="text" class="date required "  style="float:left"  readonly="true" />
			</p>
				<p>
				<label>结束日期：</label>
				<input id=end_date ename="结束日期"  name="end_date" type="text" class="date required "  style="float:left"  readonly="true" />
			</p>
		</div>
		<div class="formBar" id="subBar">
		</div>
	</form>
</div> 
</body>
</html>