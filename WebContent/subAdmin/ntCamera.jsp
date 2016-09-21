<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language = "javascript">
    function getCaptureFiles2() {
    	var flow_id =  $("#flow_id",navTab.getCurrentPanel()).val();  
     	var photo =  "D://NTCamera//bmp//" ;  
     	var array = new Array(flow_id,photo,"d://uploadfiles_bak//");
		createJsonAndAjaxNew('uploadNTPhotosNew.action', array, function(data){
		alertToPageMsg(data);
		}, 'JSON',false  );	
    }
    function getCaptureFiles(){
    	$("#file").attr("onchange","D://NTCamera//bmp//");
    	var flow_id =  $("#flow_id",navTab.getCurrentPanel()).val();  
    	if(flow_id ==""||flow_id==undefined||flow_id==null ){
    		ajaxFileUpload(flow_id);
    	}
    } 
</script>
</HEAD>
<BODY>
<OBJECT ID="AXCamera" CLASSID="CLSID:1E9D4FC2-D4AB-4E2D-8396-EEDE2B1CB29A" width="1000" height="900">

<param name="BorderStyle" value="1" />
<param name="MousePointer" value="0" />
<param name="Enabled" value="1" />
<param name="Min" value="0" />
<param name="Max" value="10" />

</OBJECT>
</BODY>
</HTML>