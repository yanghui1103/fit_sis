<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织架构</title>
<script type="text/javascript">
var custom_org_function_id = "<%=request.getParameter("custom_org_function_id") %>" ;
var select_org_multi = "<%=request.getParameter("select_org_multi") %>" ;
var ys = "<%=request.getParameter("ys") %>" ;
var ns = "<%=request.getParameter("ns") %>" ;
</script>
<script type="text/javascript" src="system/structureTreeCommonBatch.js"></script>
<link rel="stylesheet" href="common/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript" src="common/js/common.js"></script>
<script type="text/javascript" src="common/js/jquery.ztree.core-3.0.js"></script>
<script type="text/javascript"
	src="common/js/jquery.ztree.excheck-3.0.js"></script>
</head>

<BODY>
	<center>
		<div class="pageFormContent" layoutH="56">
			<frameset rows="80,*" cols="*" frameborder="NO" border="0"
				framespacing="0">
				<frame name="topFrame" scrolling="NO" noresize>
				<div class="zTreeDemoBackground left">
					<ul id="orgStrutsTree" class="ztree"></ul>
				</div>
				</frame>
			</frameset>

	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button id=select_customorg_list type="button">确定</button></li>
		</ul>
	</div>
	</center>
</BODY>
</HTML>