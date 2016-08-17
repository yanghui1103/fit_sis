/**
 *  权限等级分配Js
 */
	var num =0;
	var total = 30;   
	$(document).ready(function() {
			var arr = new Array(20);
			for(var i in arr.length){
				 arr[i] = "-9";		 
			}arr[0] = "109";arr[1] = "navTab"; 
			 renderBtnsToDiv($("#functionPwr_div"),'getAuthorityBtnsByThisUser.action',arr);
			 arr[0] = "ROLE"; 
			 takeTypeDataList($("#funcConf_role_cd"),"getSysItems.action","1", arr);	 
			 arr[0] = "QRYLEVEL"; 
			 takeTypeDataList($("#funcConf_pwr"),"getSysItems.action","0", arr);	 
				qryRoleRelatFuncInfoList(0);	 
	});
	$(document).on('click', '#qry109', function() {		 
		qryRoleRelatFuncInfoList(0);	 
		});

	function qryRoleRelatFuncInfoList(num){ 
		var funcConf_role_cd = $("#funcConf_role_cd").val() == "" ? "-9" : $("#funcConf_role_cd").val();
		var array = new Array(funcConf_role_cd, num*total + "", (num*total + total)+"",total+"" );  
		createJsonAndAjax('qryRoleRelatFuncInfoList.action', array, dealQryRoleRelatFuncInfoList,
				'JSON');		

	}
	
	function dealQryRoleRelatFuncInfoList(data){ 
		// 先清空翻页 
		initPageSelectList($("#roleFuncInfoListPageNo"),data.pageNum,$("#roleFuncInfoListTatol"),data.tatol,num);
		$("#powerConfAll").attr("checked",false);
		var $tbody = $("#roleFuncLevelDiv").find("tbody");
		$tbody.find("tr").remove();
		if (data.res == "2") {
			var jsonArr = data.list;
			for (var i = 0; i < jsonArr.length; i++) {
				var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
				$tr.append($("<td width=4%>").html("<input type=checkbox name=roleFuncId value='"+replaceF9ValToUnknown(jsonArr[i].role_id+";"+jsonArr[i].func_id)+"'/>"));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].role_name)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].func_name)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].parent_func_name)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].func_level)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].version_time)));
				$tr.append($("<td width=15%>").html(replaceF9ValToUnknown(jsonArr[i].operator)));
				$tr.hover(function(){
					$(this).addClass("hover selected");
				},
				function(){
					$(this).removeClass("hover selected");
				});
				$tbody.append($tr);
			}
		}

	}
	$(document).on('click','#powerConfAll',function(){
		var $this = $(this); 
		if($this.prop("checked")){
			$("input[name=roleFuncId]").attr("checked",true);
		}else{
			$("input[name=roleFuncId]").attr("checked",false);
		}
		
	});
	 
	
	$(document).on('click', '#save109', function() {		 
		alertMsg.confirm("是否确认保存此权级！", {
			 okCall: function(){
				 saveRoleFunctionLevel();
			 },
			 cancelCall : function() {}
			});
	});
	

	
	function saveRoleFunctionLevel(){
		// 保存权级信息
		var ids = "";
		if($("input[name=roleFuncId]:checked").length <1){
			alertMsg.info("请选择记录");
			return ;
		}
		for(var i=0;i<baidu("input[name=roleFuncId]:checked").length;i++){
			ids = ids + baidu("input[name=roleFuncId]:checked").eq(i).val()+ ",";			
		} 
		var array = new Array(ids,$("#funcConf_pwr").val() );  
		createJsonAndAjax('saveRoleFuncLevelInfo.action', array, dealSaveRoleFuncLevel,
				'JSON');
	}
	function dealSaveRoleFuncLevel(data){
		alertToUserMsg(data);		
	}
	baidu(document).on('change', '#roleFuncInfoListPageNo', function() {		 
		var $this = $(this);
		num = $this.val();	  
		qryRoleRelatFuncInfoList(num);	   
	});