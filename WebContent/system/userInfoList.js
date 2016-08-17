/**
 *  用户列表
 */
	var num =0;
	var total = 30;   
$(document).ready(function(){	
	var arr = new Array(20);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "102";arr[1] = "navTab"; 
	renderBtnsToDivV0($("#userListSubBar"),'getAuthorityBtnsByThisUser.action',arr); 
	 arr[0] = "ROLE"; 
	 takeTypeDataList($("#userInfo_role_cd"),"getSysItems.action","1", arr);	
	});
$(document).on('click', '#qry102', function() {		
	initUserInfoList($("#allUserInfoListDiv").find("tbody"),"0"); 	
});

$(document).on('change', '#userInfoListPageNo', function() {	
	num = $(this).val();
	initUserInfoList($("#allUserInfoListDiv").find("tbody"),num+""); 
});

$(document).on('click','#delete102',function(){
	alertMsg.confirm("是否确认删除该用户！", {
		 okCall: function(){
			 deleteUserFunc();
		 },
		 cancelCall : function() {}
		});
});
function deleteUserFunc(){
	var allUserId = "";
	for(var i=0;i<$("input[name=allUserId]:checked").length;i++){
		allUserId = allUserId + $("input[name=allUserId]:checked").eq(i).val() + ",";
	}
	if(allUserId==""||allUserId==undefined){
		alertMsg.info("请选择记录");return ;
	}
	var array = new Array(allUserId );   
	createJsonAndAjax('deleteUserInfo.action', array, function(data){alertToUserMsg(data);		},
			'JSON');	
}  
function initUserInfoList(obj,num){ 
	var userInfokeyword = $("#userInfokeyword").val() == "" ? "-9" : $("#userInfokeyword").val();
	var userInfoorg_id = $("#userInfoorg_id").val() == "" ? "-9" : $("#userInfoorg_id").val();  
	var userInfo_role_cd = $("#userInfo_role_cd").val() == "" ? "-9" : $("#userInfo_role_cd").val();
	var array = new Array(userInfokeyword,userInfoorg_id, userInfo_role_cd,num*total + "", (num*total + total)+"",total+"" );  
	if(userInfoorg_id=="-9"){alertMsg.info("请选择机构!");return ;} 
	createJsonAndAjax('qryAllUserInfoList.action', array, dealQryAllUserInfoList,
			'JSON');	
}
function dealQryAllUserInfoList(data){ 
	initPageSelectList($("#userInfoListPageNo"),data.pageNum,$("#userInfoListTatol"),data.tatol,num);
 
	var $tbody = $("#allUserInfoListDiv").find("tbody");
	$tbody.find("tr").remove();
	if (data.res == "2") {
		var jsonArr = data.list;
		for (var i = 0; i < jsonArr.length; i++) {
			var $tr = $("<tr  target=sid_user rel=" + (i + 1) + " >");
			$tr.append($("<td width=5%>").html("<input type=checkbox name=allUserId value='"+(jsonArr[i].staff_id )+"'/>"));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_number)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].staff_name)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].phone)));
			$tr.append($("<td width=10%>").html(replaceF9ValToUnknown(jsonArr[i].address)));
			$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].role_name)));
			$tr.append($("<td width=20%>").html(replaceF9ValToUnknown(jsonArr[i].company_name)));
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

 