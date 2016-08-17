<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.common.models.LoginUser"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta charset="utf-8" /> 
<script src="<%=basePath %>/js/jquery-1.7.2.js" type="text/javascript"></script> 
<script src="<%=basePath %>/js/json2.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/tangram-min.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/baiduTemplate.js"></script>
<script type="text/javascript" src="<%=basePath %>/common/js/common.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath %>/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="<%=basePath %>/pda/fill/hjwspj_fill.js"></script>
<link href="<%=basePath %>/common/css/css_old.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="<%=basePath %>/themes/default/style.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="<%=basePath %>/themes/css/core.css" rel="stylesheet" type="text/css"
	media="screen" />
<link href="<%=basePath %>/themes/css/print.css" rel="stylesheet" type="text/css"
	media="print" />
<link href="<%=basePath %>/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" media="screen" />
 
</head>
<body>
	<div    width=100%> 
	<br><br>
	<div style="text-align:center"><label><strong>测试Happy</strong></label></div>
	<br><br> 
			<p class="container_p">
				<label style="text-align:left">用户姓名（中文）:</label>
				<input id="hjpj_param3" type="digit" size="10"     class=gray     />
			</p> <br>
			<p class="container_p">
				<label style="text-align:left">用户姓名（法文）:</label>
				<input  id="hjpj_param4"  type="text" size="10"   class=gray  />
			</p>    <br>
			<p class="container_p">
				<label style="text-align:left">所属院校登记（中文）:</label>
				<input id="hjpj_param5" type="digit" size="10"     class=gray     />
			</p> <br>
			<p class="container_p">
				<label style="text-align:left">测试撒旦第三方:</label>
				<input id="hjpj_param5" type="text" size="10"   class=gray  />
			</p>   <br>
			<p class="container_p">
				<label>中文长度中文长度(公里数):</label>
				<input  id="hjpj_param6" type="digit" size="10"     class=gray     />
			</p> <br>
			<p class="container_p">
				<label>测试撒旦第三方:</label>
				<input id="hjpj_param7" type="text" size="10"   class=gray  />
			</p>   
		</div><br><br>
		<div class=formBar ><ul> 
		<li><div class=buttonActive><div class=buttonContent><button type=button   >提交</button></div></div></li>
		</ul></div>
</body>
</html>