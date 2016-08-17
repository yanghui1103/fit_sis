<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>上传图片页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<SCRIPT src="<%=basePath %>/pub/js/cm/jquery-1.6.2.js"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/ControlFactory.js"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlDomReader.js"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlRPC.js"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlHttp.js"></SCRIPT>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/date.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/common.js"></script>
<script language="javascript" type="text/javascript">
     function getPhots(){
     // 
     var eDom = createParamDom1(document.getElementById('uploadfile').value);
     alert(eDom.xml);
     var url = "<%=basePath%>/uploadPhtoo.action" ;
     var rpc = new XmlRPC(url);
     rpc.sendXml(eDom);          
     var ttDom = rpc.getXml() ; 
     return ttDom ;
     
  }
</script>    
  </head>
  
  <body>
    <FORM  name="uploadform">
     <INPUT type="file" id = "uploadfile" name="uploadfile">
     <INPUT type="button" onclick="getPhots();" value="上传图片">     
    </FORM>

  </body>
</html>
