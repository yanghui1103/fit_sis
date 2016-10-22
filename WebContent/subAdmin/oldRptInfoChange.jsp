<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "204";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr);
	 arr[0] = "ROLE2SUBTYPE"; 
	 takeCustomValueByOther($("#rpt_type"),"getCustomValueByOther.action","1", arr);	
	 arr[0] = "UNIT_TYPE"; 
	 takeTypeDataList($("#unit_type"),"getSysItems.action","1", arr);	
});


baidu(document).on('click', '#getInfo2', function() {	 
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = $("#card_id", navTab.getCurrentPanel()).val() ;
	createJsonAndAjaxNew("getPsnBaseInfo.action", arr,function(data){
		if(data.res!="2"){
			$("#person_name", navTab.getCurrentPanel()).val( "");
			$("#gender", navTab.getCurrentPanel()).val( "-9");
			$("#nation", navTab.getCurrentPanel()).val( "");
			$("#orgin", navTab.getCurrentPanel()).val( "");
			$("#phone", navTab.getCurrentPanel()).val("" );
			$("#first_date", navTab.getCurrentPanel()).val( "");
			alertMsg.info("系统中不存在此人信息");return ;
		}
		var json = data.list ;
		$("#person_name", navTab.getCurrentPanel()).val(json[0].person_name);
		$("#gender", navTab.getCurrentPanel()).val(json[0].person_gender);
		$("#nation", navTab.getCurrentPanel()).val(json[0].person_nation);
		$("#orgin", navTab.getCurrentPanel()).val(json[0].person_orgin);
		$("#phone", navTab.getCurrentPanel()).val(json[0].person_phone);
		$("#first_date", navTab.getCurrentPanel()).val(json[0].first_time); 
	},'json',true); 
});

baidu(document).on('click', '#save204', function() {	 
	 var card_id = $("#card_id",navTab.getCurrentPanel()).val();
	 var gender = $("#gender",navTab.getCurrentPanel()).val();
	 var rpt_type = $("#rpt_type",navTab.getCurrentPanel()).val(); 
	 if(card_id.length!=18){alertMsg.info("身份证号码应该为18位");return ;}
	 if(gender=="-9"){alertMsg.info("请选择申报人性别");return ;}
	 if(rpt_type=="-9"){alertMsg.info("请选择申报类型");return ;}
	alertMsg.confirm("是否确认录入该人的旧申报数据！", {
		 okCall: function(){ 
			 createJsonAndPost2Java('createOldPersonRptRecond.action',$("#createForm", navTab.getCurrentPanel()),function(data){
				 alertToPageMsg(data);		
			 },'JSON',false) ;
		 },
		 cancelCall : function() {}
		});		
});
</script>
</head>
<body>
	<div class="pageContent">
	<form method="post" action=""  id=createForm class="pageForm required-validate">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>身份证号码：</label>
				<input ename="身份证号码"  id="card_id" name="card_id"   class="required" type="text"    style="float:left"  size="20" maxlength=18       alt="请输入身份证号码"/>
				<button type="button" id="getInfo2">抽取</button>
			</p>  
			<p>
				<label>申报人姓名：</label>
				<input ename="申报人姓名"  id="person_name" name="person_name"   class="required chinese" type="text"    style="float:left"    maxlength=10       alt="请输入汉字"/>
			</p>  
			<p>
				<label>性别：</label>
				<select id="gender"  name="gender"   style="float:left"  class="combox" > 
					<option value="-9" selected>请选择</option>
					<option value="0">女</option>
					<option value="1">男</option>
				</select>
			</p> 
				<p>
				<label>民族：</label>
				<input id=nation  ename="民族" name="nation" type="text"   maxlength=11    class="required chinese"  style="float:left"   />
			</p>
				<p>
				<label>籍贯：</label>
				<input id=orgin  name=orgin  type="text"   maxlength=11    class="required chinese"  style="float:left"   />
			</p>
				<p>
				<label>联系电话：</label>
				<input id=phone  ename="联系电话" name="phone" type="text"   class="number " style="float:left"  maxlength=11  />
			</p>
				<p>
				<label>初次申报日期：</label>
				<input id=first_date  ename="初次申报时间" name="first_date" type="text"    class="date required"  readonly style="float:left"   />
			</p>	
			
		<div class="divider"></div>		
		<p><span><font color=red>注意：如果申报人信息在系统中已经存在，那么此处修改将不予生效；故输入身份证号码后最好先"抽取"</font></span></p>
		<div class="divider"></div>	
				<p>
				<label>本次就业单位类型：</label>
				<select id="unit_type"  name="unit_type"   style="float:left" > 
				</select>
			</p>		
				<p>
				<label>本次就业单位：</label>
				<input id=unit_name    name="unit_name" type="text"     style="float:left"  maxlength=20  />
				</select>
			</p>		
				<p>
				<label>本次申报周期：</label>
				<select id="sub_cycle"  name="sub_cycle"   style="float:left" > 
				<option value="OLD">旧申报周期</option>
				</select>
			</p>
				<p>
				<label>本次申报类型：</label>
				<select id="rpt_type"  name="rpt_type"   style="float:left" > 
				</select>
			</p>
				<p>
				<label>本次缴费开始月份：</label>
				<input id=start_date ename="开始日期" name="start_date" type="text" class="date required "  datefmt='yyyyMM' style="float:left"  readonly="true" />
			</p>
				<p>
				<label>本次缴费结束月份：</label>
				<input id=end_date ename="结束日期"  name="end_date" type="text" class="date required "   datefmt='yyyyMM' style="float:left"  readonly="true" />
			</p>
		</div>
		<div class="formBar" id="subBar">
		</div>
	</form>
</div> 
</body>
</html>