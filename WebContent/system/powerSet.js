/**
 *  角色分配权限
 */ 

$(document).ready(function(){
	var arr = new Array(60);
	for(var i in arr.length){
		 arr[i] = "-9";		 
	}arr[0] = "104";arr[1] = "navTab"; 
	 renderBtnsToDiv($("#powerSetDiv" ),'getAuthorityBtnsByThisUser.action',arr);
	
				var setting = {
					view: {
						selectedMulti: false
					},
					check: {
						enable: true,
						chkStyle: "checkbox" ,
						chkboxType:  { "Y" : 'ps', "N" : '' }
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onCheck: onCheck
					}
				}; 
				var zNodes = "" ;
				var arr = new Array(60);
				 for(var i in arr.length){
					 arr[i] = "-9";		 
				 }arr[0] =$("#giveRole_role_cd").val() ; 
				 createJsonAndAjaxNew(
							'getFunctionsTreeStructs.action',
							arr,
							function(data){  
								zNodes = data.list ; 
								$.fn.zTree.init($("#roleFunctionTree"), setting, zNodes);
								$("#checkTrue").bind("click", {type:"checkTrue"}, checkNode);
								$("#checkFalse").bind("click", {type:"checkFalse"}, checkNode);
								$("#checkTruePS").bind("click", {type:"checkTruePS"}, checkNode);
								$("#checkFalsePS").bind("click", {type:"checkFalsePS"}, checkNode);
								$("#checkAllTrue").bind("click", {type:"checkAllTrue"}, checkNode);
								$("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNode); 
							},'JSON',true
				    );
				var code, log, className = "dark";
			// -- 
			
			// --start
			function beforeCheck(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
			return (treeNode.doCheck !== false);
		}
		function onCheck(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		}		
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 6) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		function checkNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("roleFunctionTree"),
			type = e.data.type,
			nodes = zTree.getSelectedNodes();
			if (type.indexOf("All")<0 && nodes.length == 0) {
				alert("请先选择一个节点");
			}

			if (type == "checkAllTrue") {
				zTree.checkAllNodes(true);
			} else if (type == "checkAllFalse") {
				zTree.checkAllNodes(false);
			} else {
				for (var i=0, l=nodes.length; i<l; i++) {
					if (type == "checkTrue") {
						zTree.checkNode(nodes[i], true);
					} else if (type == "checkFalse") {
						zTree.checkNode(nodes[i], false);
					}else if (type == "checkTruePS") {
						zTree.checkNode(nodes[i], true, true);
					} else if (type == "checkFalsePS") {
						zTree.checkNode(nodes[i], false, true);
					}
				}
			}
		}
			//-- over
		});
		 
$(document).on('click', '#save104', function() {
    // 点击确认
	var treeObj = $.fn.zTree.getZTreeObj("roleFunctionTree");
	var nodes =   treeObj.getCheckedNodes(true); 
	var ids = "" ;
	for(var i=0;i<nodes.length;i++) {	     
		ids = ids + nodes[i].id +","; 
	}
	if($("#giveRole_role_cd", navTab.getCurrentPanel()).val() == "-9"||$("#giveRole_role_cd", navTab.getCurrentPanel()).val()=="-1"){
		alertMsg.info("请选择角色");
		return ;
	}
	var arr = new Array($("#giveRole_role_cd", navTab.getCurrentPanel()).val(),ids);
	createJsonAndAjaxNew("giveThisRoleFuntions.action", arr,dealGiveAuthMsg,'json',true); 
});


function dealGiveAuthMsg(data){
	alertToPageMsg(data);		
}