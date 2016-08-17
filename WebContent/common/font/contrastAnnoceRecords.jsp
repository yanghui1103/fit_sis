<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
    <LINK href="../css/css.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body { background-image: url(../common/images/underPhoto.jpg); }
-->
</style>
<SCRIPT src="<%=basePath %>/pub/js/cm/jquery-1.6.2.js" defer="true"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/ControlFactory.js" defer="true"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlDomReader.js" defer="true"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlRPC.js" defer="true"></SCRIPT>
<SCRIPT src="<%=basePath %>/pubjs/xmlhttp/XmlHttp.js" defer="true"></SCRIPT>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/My97DatePicker/WdatePicker.js" defer="true"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/date.js" defer="true"></script>
<script language="javascript" type="text/javascript" src="<%=basePath %>/js/common.js" defer="true"></script>
<script type="text/javascript">
   var co_id = window.dialogArguments ;
   function init(){
      var edom = createParamDom1(co_id);
   var url = "getAnnoceContentByCoId.action";
   var rpc = new XmlRPC(url);
   rpc.sendXml(edom);
   var ttdom = rpc.getXml() ; 
   document.getElementById('zhize_name').value = ttdom.selectSingleNode('./result/annce_name').text;
   document.getElementById('faOffice').value = ttdom.selectSingleNode('./result/office_name').text;
   document.getElementById('create_dt').value = ttdom.selectSingleNode('./result/create_dt').text;
   document.getElementById('content').value = ttdom.selectSingleNode('./result/content').text;
   document.getElementById('aco_id').value = ttdom.selectSingleNode('./result/aco_id').text;
   }
   // readIt() 已读
   function readIt(){
       if(!isSessionValidate(document.getElementById('user_id').value)){
      		return ;
    	}   
       var d = new Date() ; 
       var edom = createParamDom3(co_id,document.getElementById('user_id').value,
         (d.getYear()+'-'+(d.getMonth()+1)+"-"+d.getDate()));
       
   		var url = "readItAnnoce.action";
   		var rpc = new XmlRPC(url);
   		rpc.sendXml(edom);
   		var ttdom = rpc.getXml() ;
   		 
   		var res = ttdom.selectSingleNode('./root/res').text;
   		var msg = ttdom.selectSingleNode('./root/msg').text;
   		if(res == '2'){
   		   alert('已经阅读!');
   		   window.close();
   		   return ; 
   		}
   		alert(msg);
   		window.close();
   		return ;
   }
</script>
  </head>
  
  <body onload="init();">
        <table width="100%" border="0" cellpadding="0" cellspacing="1"
	bordercolor="#BEBEBE" bgcolor="#FFFFFF"
	style="border-collapse: collapse">
	 <tr>	<br>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr colspan=1>
						<td class="blue_10c">标               题</td>
						<td><input id=zhize_name size=135 type=text  class=box_grey readonly type="text" ></td>
					</tr>
	     </table>
	     <br>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr colspan=1>
						<td class="blue_10c">发文机构</td>
						<td><input id="faOffice" type=text  class=box_grey readonly size =30></td>
							
						<td class="blue_10c">发文时间</td>
						<td><input id="create_dt" type=text  class=box_grey readonly size =30></td>	
					</tr>
	     </table>
	     <br>
	 <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr colspan=1>
						<td class="blue_10c">内          容</td>
						<td><textarea id=content rows=10 cols =85  class=box_grey readonly ></textarea></td>
					</tr>
	     </table>
	  <table >
	     <tr> 
	        <td> 
	           <input type=hidden id="aco_id" />
	           <input type=hidden  id="user_id" value = "<%=session.getAttribute("user_id") %>" />
	        </td>
	     </tr>
	  </table>   
	  	<br>
	<table>
			<tr><td>
				<input name="sub"
					type="button" id="sub" value="已    读" class="button_8c"
					onClick="readIt();"></td>
			</tr>
		</table>   
		</tr>
	</table> 
  </body>
</html>
