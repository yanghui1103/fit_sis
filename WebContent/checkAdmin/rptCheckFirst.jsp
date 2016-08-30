<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"  src="checkAdmin/rptCheckFirst.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "300";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr);
	 arr[0] = "ROLE2SUBTYPE"; 
	 takeCustomValueByOther($("#rpt_type"),"getCustomValueByOther.action","1", arr);	
	 arr[0] = "Company2SubCycle"; 
	 takeCustomValueByOther($("#sub_cycle",navTab.getCurrentPanel()),"getCustomValueByOther.action","1", arr);	
});

</script>
</head>
<body>
<div class="pageFormContent"> 
		<table >
			<tr>
				<td><label>申报人姓名：</label> <input id="person_name" type="text"
			size="20" /></td>
				<td><label>身份证号码：</label> <input id="card_id" type="text"
			size="20" /></td>
			<td>
				<label>机构：</label>
				<input type="text"  id="org_name"  ename="机构"   style="float:left"  readonly name="orgLookup.orgName" value="" suggestFields="orgNum,orgName"   lookupGroup="orgLookup" />
				<a   style="float:left"  class="btnLook" href="system/orgStructure_qry.jsp?custom_org_function_id=300&select_org_multi=1&select_cascade=''&unselect_cascade=''"   lookupGroup="orgLookup">查找带回</a>		
				<input type="hidden"    readonly="readonly"   style="float:left"  id="org_id" name="orgLookup.id" />
			</td>  
			</tr>
			<br>
			<tr><td>
				<label>补贴类型：</label>
				<select id="rpt_type"  name="rpt_type"   style="float:left" > 
			</select></td><td>
				<label>申报周期：</label>
				<select id="sub_cycle"  name="sub_cycle"   style="float:left" > 
			</select></td></tr>
			</table>
	<div class="subBar" id="subBar">  
	</div> 
</div>
<div id="allDiv">
	<div class="pageContent ">
	<div class="panelBar">
		<ul>
			<li><label>当前</label></li><li><select id="PageNo"  ></select></li><li><label>页，</label></li>
			<li><label>共计</label></li><li><span id="Tatol">0</span></li><li><label>记录</label></li>
		</ul>
	</div>	
		<table class="list" width="100%" layoutH="100">
			<thead>
				<tr>
					<th width=5%></th>
					<th width=15%>申报人姓名</th>
					<th width=10%>身份证号码</th> 
					<th width=10%>票据开始月份</th>
					<th width=10%>票据开始月份</th>
					<th width=10%>录入员</th>
					<th width=10%>录入机构</th>
					<th width=15%>录入时间</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>