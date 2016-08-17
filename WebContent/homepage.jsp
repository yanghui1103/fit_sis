<%@ page language="java" contentType="text/html; charset=UTF-8"  import="com.bw.fit.common.models.LoginUser"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" href="images/icon.gif" type="images/gif"/> 
<title>系统</title>
<link href="common/css/css_old.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>     
<link rel="stylesheet" href="common/amcharts/style.css" type="text/css">
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="common/amcharts/amcharts.js" type="text/javascript"></script>
<script src="common/amcharts/serial.js" type="text/javascript"></script>
<script src="js/speedup.js" type="text/javascript"></script> 
<script type="text/javascript" src="chart/raphael.js"></script>
<script type="text/javascript" src="chart/g.raphael.js"></script>
<script type="text/javascript" src="chart/g.bar.js"></script>
<script type="text/javascript" src="chart/g.line.js"></script>
<script type="text/javascript" src="chart/g.pie.js"></script>
<script type="text/javascript" src="chart/g.dot.js"></script>
<script src="js/jquery-1.7.2.js" type="text/javascript"></script> 
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script src="js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>
<script src="js/yanue.pop.js" type="text/javascript"></script>
<script src="js/Chart.js" type="text/javascript"></script>
<script src="js/json2.js" type="text/javascript"></script>
<script src="js/ajaxfileupload.js" type="text/javascript"></script>
<script src="js/dwz.core.js" type="text/javascript"></script>
<script src="js/dwz.util.date.js" type="text/javascript"></script>
<script src="js/dwz.validate.method.js" type="text/javascript"></script>
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="js/dwz.barDrag.js" type="text/javascript"></script>
<script src="js/dwz.drag.js" type="text/javascript"></script>
<script src="js/dwz.tree.js" type="text/javascript"></script>
<script src="js/dwz.accordion.js" type="text/javascript"></script>
<script src="js/dwz.ui.js" type="text/javascript"></script>
<script src="js/dwz.theme.js" type="text/javascript"></script>
<script src="js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="js/dwz.navTab.js" type="text/javascript"></script>
<script src="js/dwz.tab.js" type="text/javascript"></script>
<script src="js/dwz.resize.js" type="text/javascript"></script>
<script src="js/dwz.dialog.js" type="text/javascript"></script>
<script src="js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="js/dwz.cssTable.js" type="text/javascript"></script>
<script src="js/dwz.stable.js" type="text/javascript"></script>
<script src="js/dwz.taskBar.js" type="text/javascript"></script>
<script src="js/dwz.ajax.js" type="text/javascript"></script>
<script src="js/dwz.pagination.js" type="text/javascript"></script>
<script src="js/dwz.database.js" type="text/javascript"></script>
<script src="js/dwz.datepicker.js" type="text/javascript"></script>
<script src="js/dwz.effects.js" type="text/javascript"></script>
<script src="js/dwz.panel.js" type="text/javascript"></script>
<script src="js/dwz.checkbox.js" type="text/javascript"></script>
<script src="js/dwz.history.js" type="text/javascript"></script>
<script src="js/dwz.combox.js" type="text/javascript"></script>
<script src="js/dwz.print.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="common/pubjs/xmlhttp/ControlFactory.js"></script>
<script language="javascript" type="text/javascript"
	src="common/pubjs/xmlhttp/XmlDomReader.js"></script>
<script language="javascript" type="text/javascript"
	src="common/pubjs/xmlhttp/XmlRPC.js"></script>
<script language="javascript" type="text/javascript"
	src="common/pubjs/xmlhttp/XmlHttp.js"></script>
<script language="javascript" type="text/javascript"
	src="common/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="common/js/date.js"></script>
<script type="text/javascript" src="common/js/tangram-min.js"></script>
<script type="text/javascript" src="common/js/dwz_common.js"></script>
<script type="text/javascript" src="common/js/baiduTemplate.js"></script>
<style>
    body{position: relative;height: 1000px;}
    .footer{position: absolute;bottom: 0;left: 0;height: 20px;width: 100%;position:fixed;bottom:0;}
</style>
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="system/homepage.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){ 
		generateTree();
   		initDwzPage();   		
});
 
</script>
</head>

<body  scroll="yes" >
	
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" >标志</a>
				<ul class="nav">
					<li><a id="zhanghao">您好,<%=((LoginUser)session.getAttribute("LoginUser")).getStaff_name() %></a></li>
					<li><a id="zhanghao">角色:<%=((LoginUser)session.getAttribute("LoginUser")).getRole_name() %></a></li>
					<li><a id="company">所属机构:<%=((LoginUser)session.getAttribute("LoginUser")).getOrg_name() %></a></li>
					<li><a href="logOut.action">退出</a></li>
				</ul> 
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
				<div id ="leftMenu" class="accordion" fillSpace="sidebar">
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"> 
						<ul class="navTab-tab">
							<li tabid="main" class="main"> </li>
						</ul>
					</div>
				</div> 
				<div class="navTab-panel tabsPageContent layoutBox"  style="overflow-y:scroll">
					<div class="page unitBox">
						<div class="accountInfo">							
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
						</div>
						<div><input type=hidden id="user" value="<%=((LoginUser)session.getAttribute("LoginUser")).getStaff_id() %>" />
						<input type=hidden  id="pwr"  value=<%=((LoginUser)session.getAttribute("LoginUser")).getPwrTreeJson() %> /></div> 
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>