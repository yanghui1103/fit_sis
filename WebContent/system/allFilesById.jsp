<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
var foregin_id = "<%=request.getParameter("foregin_id")%>";
var isRead = "<%=request.getParameter("isRead")%>";
$(function(){  
	
	initUserInfoList( $("#table2") ,foregin_id);
	$("#"+foregin_id+"Div").find("button").eq(1).click(function(){		
		if(isRead == '1'){alertMsg.info("无权限");return ;} 
		ajaxFileUpload(foregin_id);
		initUserInfoList($("#table2") ,foregin_id); 
	});
	$("#"+foregin_id+"Div").find("button").eq(0).click(function(){		
		if(isRead == '1'){alertMsg.info("无权限"); return ;}
		var valll= $("input[name='fileAllId']:checked").val() ;
		var array = valll.split(","); 
		var array = new Array(array[0] );   
		createJsonAndAjax('deleteAttachmentFile.action', array, dealDeleteAttachmentFile,
				'JSON');	
	});
	$("#"+foregin_id+"Div").find("button").eq(2).click(function(){	 
		var valll= $("input[name='fileAllId']:checked").val() ;
		var array = valll.split(","); 
		window.location.href = "uploadfiles" +"/"+ array[1];
	});
});
function dealDeleteAttachmentFile(data){
	alertToUserMsg(data);
}
function initUserInfoList(obj,foregin_id){ 
	obj.empty();
	//把本级机构及以下机构的人员查询出来
	var array = new Array(foregin_id );   
	createJsonAndAjax('getFileListByForeginId.action', array, dealInitList,
			'JSON');	
}	
function dealInitList(data){ 
	if (data.res == "2") { 
		var jsonArr = data.list;  
		for (var i = 0; i < jsonArr.length; i++) {
			var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
			$tr.append($("<td width=5%>").html("<input type=radio name=fileAllId value='"+replaceF9ValToUnknown(jsonArr[i].fdid)+","+replaceF9ValToUnknown(jsonArr[i].filename)+"'/>"));
			$tr.append($("<td width=30% >").html(replaceF9ValToUnknown(jsonArr[i].before_name)));
			$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].create_time)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_name)));
			$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].temp_str2))); 
			$tr.hover(function(){
				$(this).addClass("hover selected");
			},
			function(){
				$(this).removeClass("hover selected");
			});
			 $("#table2").append($tr);
		}
	}
}
</script>
</head>
<body>
<div id="<%=request.getParameter("foregin_id")%>Div"   class="pageContent">
		<div class="formBar"  >
    	 	<img src="system/loading.gif" id="loading" style="display: none;">
			<input type="file" id="file" name="file" /> 
		</div>
	 <table width="40%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<div class="button"><div class="buttonContent deleteFileCss"><button>删除</button></div></div>
						
						<div class="button"><div class="buttonContent upFileCss"  ><button>上传</button></div></div>
						
						<div class="button"><div class="buttonContent downFileCss"><button>下载</button></div></div>
						</td>
					</tr>
	</table>
<div class="pageContent"  style='width: 100%; height: 150px;' >	
	<table   class="table"   width="100%" >
		<thead>
			<tr>			
				<th width="5%">选择</th>
				<th width="30%">文件名称</th>
				<th width="20%">上传时间</th>
				<th width="10%">上传者</th>
				<th width="15%">说明</th>
			</tr>
		</thead>	 	
	</table> 
	<table id="table2"  width="100%"  height="50%"></table>
	</div>
</div>

</body>
</html>