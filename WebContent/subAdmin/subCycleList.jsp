<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var num =0;
var total = 30;   
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "201";arr[1] = "navTab"; 
	 renderBtnsToDiv($("#subBar", navTab.getCurrentPanel()),'getAuthorityBtnsByThisUser.action',arr); 
});

baidu(document).on('click', '#qry201', function() {
	initCycleList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),"0"); 	
});
function initCycleList(obj,num){ 
	var userInfokeyword = $("#cycle_name", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#cycle_name", navTab.getCurrentPanel()).val();
	var userInfoorg_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	 var array = new Array(userInfokeyword,userInfoorg_id, num*total + "", (num*total + total)+"",total+"" );   
	 createJsonAndAjaxNew('qryAllCycleInfoList.action', array, function(data){
		initPageSelectList($("#PageNo"),data.pageNum,$("#Tatol"),data.tatol,num);	 
		var $tbody =  obj ;
		$tbody.find("tr").remove();
		if (data.res == "2") {
			var jsonArr = data.list;
			for (var i = 0; i < jsonArr.length; i++) {
				var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
				$tr.append($("<td width=5%>").html("<input type=checkbox name=allId value='"+(jsonArr[i].fdid )+"'/>"));
				$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].cycle_name)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].org_name)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].start_date)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].end_date)));
				$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].create_time)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].creator)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].state)));
				$tr.hover(function(){
					$(this).addClass("hover selected");
				},
				function(){
					$(this).removeClass("hover selected");
				});
				$tbody.append($tr);
			}
		} 
	},
			'JSON',false);	
}


$("#PageNo", navTab.getCurrentPanel()).change(function(){	
	num = $("#PageNo", navTab.getCurrentPanel()).val();
	initUserInfoList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),num+""); 
});
baidu(document).on('click', '#delete201', function() {
	var s = "" ;
	for(var i=0;i<$("input[name='allId']:checked").length;i++){
		s = s +  $("input[name='allId']:checked").eq(i).val() + ",";
	}
	if(s==""){
		alertMsg.error("请选择记录");return ;
	}
	var array = new Array(s) ;
	alertMsg.confirm("是否确认删除该申报周期！", {
		 okCall: function(){ 
				createJsonAndAjaxNew('deleteSubCycle.action',array,function(data){
					alertToPageMsg(data);
				},'JSON',true) ;	
		 },
		 cancelCall : function() {}
		});			
});


baidu(document).on('click', '#open201', function() {
	var s = "" ;
	for(var i=0;i<$("input[name='allId']:checked").length;i++){
		s = s +  $("input[name='allId']:checked").eq(i).val() + ",";
	}
	if(s==""){
		alertMsg.error("请选择记录");return ;
	}
	var array = new Array(s,"2") ;
	alertMsg.confirm("是否确认开启选中的申报周期！", {
		 okCall: function(){ 
				createJsonAndAjaxNew('openOrCloseSubCycle.action',array,function(data){
					alertToPageMsg(data);
				},'JSON',true) ;	
		 },
		 cancelCall : function() {}
		});			
});


baidu(document).on('click', '#close201', function() {
	var s = "" ;
	for(var i=0;i<$("input[name='allId']:checked").length;i++){
		s = s +  $("input[name='allId']:checked").eq(i).val() + ",";
	}
	if(s==""){
		alertMsg.error("请选择记录");return ;
	}
	var array = new Array(s,"1") ;
	alertMsg.confirm("是否确认关闭选中的申报周期！", {
		 okCall: function(){ 
				createJsonAndAjaxNew('openOrCloseSubCycle.action',array,function(data){
					alertToPageMsg(data);
				},'JSON',true) ;	
		 },
		 cancelCall : function() {}
		});			
});
</script>
</head>
<body>
<div class="pageFormContent"> 
		<table >
			<tr>
				<td><label>周期名称：</label> <input id="cycle_name" type="text"
			size="20" /></td>
			<td>
				<label>机构：</label>
				<input type="text"  id="org_name"  ename="机构"   style="float:left" class="required" readonly name="orgLookup.orgName" value="" suggestFields="orgNum,orgName"   lookupGroup="orgLookup" />
				<a   style="float:left"  class="btnLook" href="system/orgStructure_qry.jsp?custom_org_function_id=201&select_org_multi=1&select_cascade=''&unselect_cascade=''"   lookupGroup="orgLookup">查找带回</a>		
				<input type="hidden"    readonly="readonly"   style="float:left"  id="org_id" name="orgLookup.id" />
			</td>  
			</tr>
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
					<th width=20%>周期名称</th>
					<th width=10%>机构</th>
					<th width=10%>开始时间</th>
					<th width=10%>结束时间</th>
					<th width=20%>录入时间</th>
					<th width=10%>录入员</th>
					<th width=10%>状态</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>