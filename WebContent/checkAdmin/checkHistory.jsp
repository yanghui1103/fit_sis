<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var flow_id = '<%=request.getParameter("flow_id") %>' ;  
var array = new Array(flow_id);   
createJsonAndAjaxNew('getCheckHistory.action', array, function(data){ 
	var $table =  $("#his",navTab.getCurrentPanel()) ; 		
	if (data.res == "2") {
		var jsonArr = data.list;
		for (var i = 0; i < jsonArr.length; i++) {
			var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >"); 
			$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].node_name)));
			$tr.append($("<td width=65%>").html(replaceF9ValToUnknown(jsonArr[i].node_remark)));  
			$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].create_time)));  
			$tr.hover(function(){
				$(this).addClass("hover selected");
			},
			function(){
				$(this).removeClass("hover selected");
			});
			$table.append($tr);
		}
	} 
},
		'JSON',false);	
</script>
</head>
<body>
<div>
	<table width=100% id="his" class="list"> 
	</table>
</div>
</body>
</html>