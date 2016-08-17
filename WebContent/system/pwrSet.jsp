<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>功能树</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
	<link rel="stylesheet" href="common/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="common/js/common.js"></script>
	<script type="text/javascript" src="common/js/jquery.ztree.core-3.0.js"></script>
	<script type="text/javascript" src="common/js/jquery.ztree.excheck-3.0.js"></script>
<script language="javascript"  type="text/javascript" src="common/pubjs/xmlhttp/ControlFactory.js" ></script>
<script language="javascript"  type="text/javascript" src="common/pubjs/xmlhttp/XmlDomReader.js" ></script>
<script language="javascript"  type="text/javascript" src="common/pubjs/xmlhttp/XmlRPC.js" ></script>
<script language="javascript"  type="text/javascript" src="common/pubjs/xmlhttp/XmlHttp.js" ></script>

	<SCRIPT LANGUAGE="JavaScript">
		
	    var role_cd_value = '-1';
		$(document).ready(function(){
			role_cd_value =$("#giveRole_role_cd").val();
			 
				var setting = {
					view: {
						selectedMulti: false
					},
					check: {
						enable: true
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						beforeCheck: beforeCheck,
						onCheck: onCheck
					}
				};

				var zNodes = getThisRoleAuthTreeJson();
				var code, log, className = "dark";
			// -- 
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#checkTrue").bind("click", {type:"checkTrue"}, checkNode);
			$("#checkFalse").bind("click", {type:"checkFalse"}, checkNode);
			$("#checkTruePS").bind("click", {type:"checkTruePS"}, checkNode);
			$("#checkFalsePS").bind("click", {type:"checkFalsePS"}, checkNode);
			$("#checkAllTrue").bind("click", {type:"checkAllTrue"}, checkNode);
			$("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNode);
			$("#btn_sub").click(function(){		

				 var nodeStr  ="";
			      var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = zTree.getCheckedNodes(true);					
			    		for (var i=0; i<nodes.length;  i++) {
			      			nodeStr = nodeStr +nodes[i].id ;
			      			if(i<nodes.length-1){
			      			    nodeStr = nodeStr + ",";
			      			}
			   			}	 
			    		var arr = new Array(20);
						 for(var i in arr.length){
							 arr[i] = "-9";		 
						 }arr[0] = role_cd_value;arr[1]=nodeStr;
						 var eobj = createParamJson(arr); 
						 var eJson = JSON.stringify(eobj);   
							var resultJson = AjaxExchangeBackTextData('giveAuthorityToRole.action',eJson);  
							alertToUserMsg(resultJson); 
			});
			// --start
			function beforeCheck(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.doCheck !== false);
		}
		function onCheck(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		}		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 6) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		function checkNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			type = e.data.type,
			nodes = zTree.getSelectedNodes();
			if (type.indexOf("All")<0 && nodes.length == 0) {
				alert("请先选择一个节点");
			}

			if (type == "checkAllTrue") {
				zTree.checkAllNodes(true);
			} else if (type == "checkAllFalse") {
				zTree.checkAllNodes(false);
			} else {
				for (var i=0, l=nodes.length; i<l; i++) {
					if (type == "checkTrue") {
						zTree.checkNode(nodes[i], true);
					} else if (type == "checkFalse") {
						zTree.checkNode(nodes[i], false);
					}else if (type == "checkTruePS") {
						zTree.checkNode(nodes[i], true, true);
					} else if (type == "checkFalsePS") {
						zTree.checkNode(nodes[i], false, true);
					}
				}
			}
		}
			//-- over
		});
		// 获取权限树JSON
		function getThisRoleAuthTreeJson(){ 
			var arr = new Array(20);
			 for(var i in arr.length){
				 arr[i] = "-9";		 
			 }arr[0] = role_cd_value;
			 var eobj = createParamJson(arr); 
			 var eJson = JSON.stringify(eobj);  
			var jsonText = AjaxExchangeBackTextData('getThisRoleAuthTreeJson.action',eJson);    
			jsonText = eval("["+jsonText+"]");
			return jsonText ;
		}
	</SCRIPT>
</HEAD>

<BODY>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>	 
			<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id=btn_sub type="button">保存</button></li>
			</ul>
		</div>		
</div>
</BODY>
</HTML>