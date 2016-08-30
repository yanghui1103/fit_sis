
var num =0;
var total = 30;   //每页记录数 

baidu(document).on('click', '#qry400', function() {
	initCycleList6($("#allDiv", navTab.getCurrentPanel()).find("tbody"),"0"); 	
});
function initCycleList6(obj,num){ 
	var person_name = $("#person_name", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#person_name", navTab.getCurrentPanel()).val();
	var card_id = $("#card_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#card_id", navTab.getCurrentPanel()).val();  
	var org_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	var rpt_type = $("#rpt_type", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#rpt_type", navTab.getCurrentPanel()).val();  
	var sub_cycle = $("#sub_cycle", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#sub_cycle", navTab.getCurrentPanel()).val();  
	if(org_id =="-9"){
		alertMsg.info("请选择机构");return ;
	}
	 var array = new Array(person_name,card_id,org_id,rpt_type,sub_cycle, num*total + "", (num*total + total)+"",total+"" ,'finance');   
	 createJsonAndAjaxNew('qryWaitCheckRecordList.action', array, function(data){
		initPageSelectList($("#PageNo", navTab.getCurrentPanel()),data.pageNum,$("#Tatol", navTab.getCurrentPanel()),data.tatol,num);	 
		var $tbody =  obj ;
		$tbody.find("tr").remove();		
		if (data.res == "2") {
			var jsonArr = data.list;
			for (var i = 0; i < jsonArr.length; i++) {
				var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >"); 
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].person_name)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].card_id))); 
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].start_date)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].end_date)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].creator)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_company_name)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].create_time)));
				$tr.hover(function(){
					$(this).addClass("hover selected");
				},
				function(){
					$(this).removeClass("hover selected");
				});
				$tbody.append($tr);
			}
		} else{
			alertMsg.info("暂无需拨款申报记录");return ;
		}
	},
			'JSON',false);	
}


baidu(document).on('click', '#save400', function() { 
	var person_name = $("#person_name", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#person_name", navTab.getCurrentPanel()).val();
	var card_id = $("#card_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#card_id", navTab.getCurrentPanel()).val();  
	 
	var org_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	var rpt_type = $("#rpt_type", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#rpt_type", navTab.getCurrentPanel()).val();  
	var sub_cycle = $("#sub_cycle", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#sub_cycle", navTab.getCurrentPanel()).val();  
	if(org_id =="-9"){
		alertMsg.info("请选择机构");return ;
	}
	if(rpt_type =="-9"){
		alertMsg.info("请选择申报补贴类型");return ;
	}
	if(sub_cycle =="-9"){
		alertMsg.info("请选择申报周期");return ;
	}

	 var array = new Array(person_name,card_id,org_id,rpt_type,sub_cycle, num*total + "", (num*total + total)+"",total+"" ,'finance');   
	 // var array = new Array(org_id,rpt_type,sub_cycle);  
	 alertMsg.confirm("是否确认全量拨款补贴！", {
		 okCall: function(){ 			 
			 createJsonAndAjaxNew('grantFinanceToPsn.action', array, function(data){
				 alertToPageMsg(data);
			 },'json',false);
		 },
		 cancelCall : function() {}
		});			
});

$("#PageNo", navTab.getCurrentPanel()).change(function(){	
	num = $("#PageNo", navTab.getCurrentPanel()).val();
	initCycleList6($("#allDiv", navTab.getCurrentPanel()).find("tbody"),num+""); 
});