<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  

	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="system/photo/lib/jquery.mousewheel.pack.js?v=3.1.3"></script>

	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="system/photo/source/jquery.fancybox.pack.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="system/photo/source/jquery.fancybox.css?v=2.1.5" media="screen" />

	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="system/photo/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="system/photo/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="system/photo/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="system/photo/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="system/photo/source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>

<script type="text/javascript"> 
var foregin_id = '<%=request.getParameter("foregin_id")%>';
var isRead ='<%=request.getParameter("isRead")%>' ;

$(function(){   
	// 图片渲染控件
	$('.fancybox',$.pdialog.getCurrent()).fancybox();
    initAttachmentList();
    $("#ntc_cap").load("subAdmin/CapWeb.htm");
});
function initAttachmentList(){
	var array = new Array();  
	array[0] = foregin_id ;  
	createJsonAndAjaxNew('getFileListByForeginId.action', array, dealInitList,
			'JSON',true  );	
}
function dealInitList(data){ 
	 $("#attach_list",$.pdialog.getCurrent() ).empty();
	if(data.res =="2"){
		var jsonArr = data.list;  
		for (var i = 0; i < jsonArr.length; i++) {
			var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
			$tr.append($("<td width=5%>").html("<input type=radio name=fileAllId value='"+replaceF9ValToUnknown(jsonArr[i].fdid)+ "'/>"));
			$tr.append($("<td width=30% >").html(replaceF9ValToUnknown(jsonArr[i].before_name)));
			$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].create_time)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_name)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].temp_str2))); 
			var str = "<div id=gallery2 class=gallery><a href=uploadfiles/"+ jsonArr[i].filename + "  data-fancybox-group=gallery   class=fancybox >看图</a></div>" ;
			$tr.append($("<td width=5%>").html(str)); 
			$tr.hover(function(){
				$(this).addClass("hover selected");
			},
			function(){
				$(this).removeClass("hover selected");
			});		 
			 $("#attach_list",$.pdialog.getCurrent() ).append($tr);
		}
	} 
} 
$("#upFileCss",$.pdialog.getCurrent() ).click(function(){		 
	ajaxFileUpload(foregin_id);
	$("#refreshFileCss",$.pdialog.getCurrent() ).trigger("click");
});
$("#refreshFileCss",$.pdialog.getCurrent() ).click(function(){		 
	initAttachmentList();
});
$("#deleteFileCss",$.pdialog.getCurrent() ).click(function(){	
	var fileAllId = $("input[name='fileAllId']:checked",$.pdialog.getCurrent()).val();
	if($("input[name='fileAllId']:checked",$.pdialog.getCurrent()).length <1){
		alertMsg.info("请选择附件记录");return ;
	}
	alertMsg.confirm("是否确认删除附件！", {
		 okCall: function(){ 
			var array = new Array(fileAllId);
			createJsonAndAjaxNew('deleteAttachmentFile.action', array, function(data){
			alertToPageMsg(data);
			}, 'JSON',false  );	
		 },
		 cancelCall : function() {}
		});		
});
</script> 
</head>
<body>
<div  class="pageContent">  
		<div id="ntc_cap"></div>
		<div class="formBar"   >
    	 	<img src="system/loading.gif" id="loading" style="display: none;">
			<input type="file" id="file" name="file" /> 
		</div>
	 <table width="40%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
						<% String s = (String)request.getParameter("isRead") ;
						if(s.equals("0")){
						    %>
						<div class="button"><div class="buttonContent" id="deleteFileCss"><button>删除</button></div></div>
							<div class="button"><div class="buttonContent"  id="upFileCss"><button>上传</button></div></div>
					    <% 
						}
						%>
						
						<div class="button"><div class="buttonContent"  id="refreshFileCss"><button>刷新</button></div></div>
						<!-- 
						<div class="button"><div class="buttonContent"  id="downFileCss"><button>下载</button></div></div>
						 -->
						</td>
					</tr>
	</table>
	<table   class="list"   width="100%" >
		<thead>
			<tr>			
				<th width="5%">选择</th>
				<th width="30%">文件名称</th>
				<th width="20%">上传时间</th>
				<th width="10%">上传者</th>
				<th width="10%">说明</th>
				<th width="5%"></th>
			</tr>
		</thead>
		<tbody id="attach_list"></tbody>	 	
	</table> 
</div> 
</body>
</html>