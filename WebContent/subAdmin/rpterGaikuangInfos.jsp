<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<script type="text/javascript">
var card_id = "<%= request.getParameter("card_id")%>";
$(function(){ 
	if(card_id==""){alertMsg.error("请刷身份证卡片,并点击读取按钮");return ;}
	var array = new Array(card_id) ;
	createJsonAndAjaxNew('getPersonRptedInfo.action', array,function(data){
		 if(data.res!="2"){
			 alertMsg.info(data.msg);
			 var ajaxbg = $("#background,#progressBar");
				ajaxbg.hide();
			 return ;
		 }
		 var json = data.list ;  
		 $("#person_name", $.pdialog.getCurrent()).val(json[0].person_name);
		 $("#first_date", $.pdialog.getCurrent()).val(json[0].first_time.substr(0,10));
		 $("#person_age", $.pdialog.getCurrent()).val(json[0].first_zhousui);
		 $("#gender", $.pdialog.getCurrent()).val(json[0].gender);
		 $("#person_state", $.pdialog.getCurrent()).val(json[0].state);
		 $("#state", $.pdialog.getCurrent()).val(json[0].state_code); 
		 $("#rpted_months", $.pdialog.getCurrent()).val(json[0].yueshu);
		 var array = getPersonTypeName(json[0].first_zhousui,json[0].gender) ; 
		 $("#person_type", $.pdialog.getCurrent()).val(array[1]); 
		 
		 var max_months = getMaxMonthsByCard(card_id,json[0].gender,json[0].first_zhousui,array[0]) ;
		 $("#can_months", $.pdialog.getCurrent()).val(max_months); 
		 
	 },'JSON',true ) ; 
});
</script>
</head>
<body>

	<div class="pageContent">
	<form  class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>申报人姓名：</label>
				<input   id="person_name"    type="text"    style="float:left" readonly    />
			</p>  
			<p>
				<label>初次申报日期：</label>
				<input   id="first_date"    type="text"    style="float:left"   readonly    />
			</p>  
			<p>
				<label>人员类型：</label>
				<input   id="person_type"    type="text"    style="float:left"   readonly    />
			</p>  
			<p>
				<label>初次申领当年周岁为：</label>
				<input   id="person_age"    type="text"    style="float:left"   readonly    />
			</p>  
			<p>
				<label>性别：</label>
				<select id="gender"  name="gender"   style="float:left" disabled > 
					<option value="-9" selected>请选择</option>
					<option value="0">女</option>
					<option value="1">男</option>
				</select>
			</p> 
			<p>
				<label>人员状态：</label>
				<input   id="person_state"    type="text"    style="float:left"   readonly    />
				<input  type="hidden"  id="state" />
			</p>  
			<p>
				<label>已补总月数：</label>
				<input   id="rpted_months"    type="text"    style="float:left"   readonly    />
			</p>  
			<p>
				<label>最长可享受总月数(无断补情况)：</label>
				<input   id="can_months"    type="text"    style="float:left"   readonly    />
			</p>  
		</div> 
	</form>
</div> 
</body>
</html>