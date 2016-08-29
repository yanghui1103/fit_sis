<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.bw.fit.common.utils.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">  
//获取附件页 
$("#getPhoto").on('click', function() {	  
	$(".ahrefCss").attr('href','system/attachmentPage.jsp?isRead=0&foregin_id=12323');
	$(".ahrefCss").trigger("click"); 
}); 
</script>
</head>
<body>
	<div class="pageContent">
		<form method="post" action=""  id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56"> <p>
		<input   type="hidden"   id="flow_id"  name="flow_id" value="<%=PubFun.getUUID() %>" />
		<button id="getPhoto"  type="button"  >上传附件</button>
		</p>
		</form>
</div> 	

		<div   style="display:none;">
		<a class="button ahrefCss"  href="javascript:return false;" target="dialog" rel="dlg_page10" mask="true" title="附件"><span id="fffff">附件2</span></a> 
		</div>
		
		<div class="formBar" id="subBar">
		</div>
</body>
</html>