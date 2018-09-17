
var num =0;
var total = 20;   //每页记录数 

baidu(document).on('click', '#delete219', function() {
	var allJlId = $("input[name='allJlId']:checked").val();
	if($("input[name='allJlId']:checked").length <1){
		alertMsg.error("请选择记录");return ;
	}
	var array = new Array();
	array[0] = allJlId ;
	createJsonAndAjaxNew('deleteTempRecord.action', array,function(data){
		alert(data.msg);
	},'json',true  ) ;
});

baidu(document).on('click', '#qry219', function() {
	initCycleList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),"0"); 	
});
function initCycleList(obj,num){ 
	var person_name = $("#person_name", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#person_name", navTab.getCurrentPanel()).val();
	var card_id = $("#card_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#card_id", navTab.getCurrentPanel()).val();  
	var org_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	if(org_id =="-9"){
		alertMsg.info("请选择机构");return ;
	}
	 var array = new Array(person_name,card_id,org_id,"-9","-9", num*total + "", (num*total + total)+"",total+"" ,'creator');   
	 createJsonAndAjaxNew('qryTempRecordList.action', array, function(data){
		initPageSelectList($("#PageNo", navTab.getCurrentPanel()),data.pageNum,$("#Tatol", navTab.getCurrentPanel()),data.tatol,num);	 
		var $tbody =  obj ;
		$tbody.find("tr").remove();		
		if (data.res == "2") {
			var jsonArr = data.list;
			for (var i = 0; i < jsonArr.length; i++) {
				var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
				$tr.append($("<td width=5%>").html("<input type=radio name=allJlId value='"+(jsonArr[i].fdid ) +","+jsonArr[i].card_id +","+jsonArr[i].card_id +"'/>"));
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
			alertMsg.info("无记录");return ;
		}
	},
			'JSON',false);	
}



$("#PageNo", navTab.getCurrentPanel()).change(function(){	
	num = $("#PageNo", navTab.getCurrentPanel()).val();
	initCycleList($("#allDiv", navTab.getCurrentPanel()).find("tbody"),num+""); 
});