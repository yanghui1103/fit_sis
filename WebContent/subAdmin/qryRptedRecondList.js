var num =0;
var total = 30;   
$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "208";arr[1] = "navTab"; 
	renderBtnsToDiv( $("#subBar", navTab.getCurrentPanel())  ,'getAuthorityBtnsByThisUser.action',arr);  
	 arr[0] = "SUB_TYPE"; 
	 takeTypeDataList($("#rpt_type",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
	 arr[0] = "SUBCYCLE"; 
	 takeTypeDataList($("#sub_cycle",navTab.getCurrentPanel()),"getSysItems.action","1", arr);	
});

baidu(document).on('click', '#qry208', function() {
	initRptedList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),"0"); 	
});


function initRptedList(obj,num){ 
	var person_name = $("#person_name", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#person_name", navTab.getCurrentPanel()).val();
	var card_id = $("#card_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#card_id", navTab.getCurrentPanel()).val();  
	var org_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	var rpt_type = $("#rpt_type", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#rpt_type", navTab.getCurrentPanel()).val();  
	var sub_cycle = $("#sub_cycle", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#sub_cycle", navTab.getCurrentPanel()).val();  

	 var array = new Array(person_name,card_id,org_id,rpt_type,sub_cycle, num*total + "", (num*total + total)+"",total+""  );   
	 createJsonAndAjaxNew('qryRptedRecordList.action', array, function(data){
		initPageSelectList($("#PageNo", navTab.getCurrentPanel()),data.pageNum,$("#Tatol", navTab.getCurrentPanel()),data.tatol,num);	 
		var $tbody =  obj ;
		$tbody.find("tr").remove();		
		if (data.res == "2") {
			var jsonArr = data.list;
			for (var i = 0; i < jsonArr.length; i++) {
				var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
				$tr.append($("<td width=3%>").html("<input type=radio name=allRptedId value='"+(jsonArr[i].flow_id ) + "'/>"));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].person_name)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].card_id))); 
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].rpt_start)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].rpt_end)));
				$tr.append($("<td width=5%>").html(replaceF9ValToUnknown(jsonArr[i].yueshu)));
				$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_name)));
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
			alertMsg.info("暂无数据");return ;
		}
	},
			'JSON',false);	
}


$("#PageNo", navTab.getCurrentPanel()).change(function(){	
	num = $("#PageNo", navTab.getCurrentPanel()).val();
	initRptedList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),num+""); 
});
