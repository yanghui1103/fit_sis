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
	}arr[0] = "203";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr);
});

baidu(document).on('click', '#save203', function() {	 
		alertMsg.confirm("是否确认修改申报人员的基本信息，且注意慎重修改人员状态！", {
			 okCall: function(){
				 if($("#state",navTab.getCurrentPanel()).val() =="-9"){
					 alertMsg.info("请选择状态");return ;
				 }
				 if($("#gender",navTab.getCurrentPanel()).val() =="-9"){
					 alertMsg.info("请选择性别");return ;
				 }				 
				 createJsonAndPost2Java('changePsnBaseInfo.action',$("#createForm", navTab.getCurrentPanel()),function(data){alertToPageMsg(data);	},'JSON',false) ;
			 },
			 cancelCall : function() {}
			});		
	});

baidu(document).on('click', '#getInfo', function() {	 
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = $("#card_id", navTab.getCurrentPanel()).val() ;
	createJsonAndAjaxNew("getPsnBaseInfo.action", arr,function(data){
		if(data.res!="2"){

			$("#person_name", navTab.getCurrentPanel()).val( "");
			$("#gender", navTab.getCurrentPanel()).val( "-9");
			$("#nation", navTab.getCurrentPanel()).val( "");
			$("#orgin", navTab.getCurrentPanel()).val( "");
			$("#phone", navTab.getCurrentPanel()).val("" );
			$("#first_date", navTab.getCurrentPanel()).val( "");
			$("#state", navTab.getCurrentPanel()).val("-9"); 
			alertMsg.info("系统中不存在此人信息");return ;
		}
		var json = data.list ;
		$("#person_name", navTab.getCurrentPanel()).val(json[0].person_name);
		$("#gender", navTab.getCurrentPanel()).val(json[0].person_gender);
		$("#nation", navTab.getCurrentPanel()).val(json[0].person_nation);
		$("#orgin", navTab.getCurrentPanel()).val(json[0].person_orgin);
		$("#phone", navTab.getCurrentPanel()).val(json[0].person_phone);
		$("#first_date", navTab.getCurrentPanel()).val(json[0].first_time);
		$("#state", navTab.getCurrentPanel()).val(json[0].person_state); 
	},'json',true); 
});
</script>
</head>
<body>
<div class="pageContent">
	<form   id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>身份证号码：</label>
				<input ename="身份证号码"  id="card_id" name="card_id"   class="required" type="text"    style="float:left"  size="20" maxlength=18      />
				<button type=button id="getInfo">获取</button>
			</p>  
				<p>
				<label>姓名：</label>
				<input id=person_name ename="姓名" name="person_name" type="text" class="chinese required "  style="float:left"   />
			</p>
			<p>
				<label>性别：</label>
				<select id="gender"  name="gender"   style="float:left"  > 
					<option value="-9" selected>请选择</option>
					<option value="0">女</option>
					<option value="1">男</option>
				</select>
			</p> 
				<p>
				<label>民族：</label>
				<input id=nation  ename="民族" name="nation" type="text"  readonly style="float:left"   />
			</p>
				<p>
				<label>籍贯：</label>
				<input id=orgin   type="text"  readonly style="float:left"   />
			</p>
				<p>
				<label>联系电话：</label>
				<input id=phone  ename="联系电话" name="phone" type="text"   class="number  required" style="float:left"  maxlength=11  />
			</p>
				<p>
				<label>初次申报日期：</label>
				<input id=first_date  ename="初次申报时间" name="first_date" type="text"    class="date required"  readonly style="float:left"   />
			</p>
			<p>
				<label>状态：</label>
				<select id="state"  name="state"   style="float:left"   > 
					<option value="-9" selected>请选择</option>
					<option value="2">正常享受中</option>
					<option value="1">暂停享受</option>
					<option value="0">已享受满</option>
				</select>
			</p> 
		</div>
		<div class="formBar" id="subBar">
		</div>
	</form>
</div> 
</body>
</html>