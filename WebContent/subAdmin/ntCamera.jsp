<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language = "javascript">
    function getCaptureFiles() {
    	var flow_id =  $("#flow_id",navTab.getCurrentPanel()).val();  
     	var photo ="d://NTCamera//bmp1;2016-09-20 21-13-07.bmp" ; // AXCamera.GetCaptureFiles();
     	var array = new Array(flow_id,photo,"/opt/app/uploadfiles_bak");
		createJsonAndAjaxNew('uploadNTPhotos.action', array, function(data){
		alertToPageMsg(data);
		}, 'JSON',false  );	
    }
</script>
</HEAD>
<BODY>
<OBJECT ID="AXCamera" CLASSID="CLSID:1E9D4FC2-D4AB-4E2D-8396-EEDE2B1CB29A" width="100%" height="80%"></OBJECT>
<input type = "button" onclick = "getCaptureFiles()" value = "GetFiles" />

</BODY>
</HTML>