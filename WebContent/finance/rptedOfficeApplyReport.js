
baidu(document).on('click', '#qry401', function() {
	var org_id = $("#org_id", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#org_id", navTab.getCurrentPanel()).val();  
	var rpt_type = $("#rpt_type", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#rpt_type", navTab.getCurrentPanel()).val();  
	var sub_cycle = $("#sub_cycle", navTab.getCurrentPanel()).val() == "" ? "-9" : $("#sub_cycle", navTab.getCurrentPanel()).val();  
	if(org_id =="-9"){
		alertMsg.info("请选择机构");return ;
	}
	 var array = new Array(org_id,rpt_type,sub_cycle  );
		createJsonAndAjaxNew(
				'rptOfficeApplyedReport.action',
				array,
				dealDaoRpt,'JSON',true
	    );
});
