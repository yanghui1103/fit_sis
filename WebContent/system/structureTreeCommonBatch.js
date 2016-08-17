/**
 *  组织架构图
 */ 
$(document).ready(function(){
	 			var setting = {
					view: {
						selectedMulti: false
					},
					check: {
						enable: true,
						chkStyle: "radio",
						chkboxType:  { "Y" : ys, "N" : ns }
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
				var arr = new Array(20);
				 for(var i in arr.length){
					 arr[i] = "-9";		 
				 }arr[0] = custom_org_function_id +"";
					createJsonAndAjax(
							'getCustomOrgTree.action',
							arr,
							function(data){  
								zNodes =  data.list  ; 
								$.fn.zTree.init($("#orgStrutsTree"), setting, zNodes);
								$("#checkTrue").bind("click", {type:"checkTrue"}, checkNode);
								$("#checkFalse").bind("click", {type:"checkFalse"}, checkNode);
								$("#checkTruePS").bind("click", {type:"checkTruePS"}, checkNode);
								$("#checkFalsePS").bind("click", {type:"checkFalsePS"}, checkNode);
								$("#checkAllTrue").bind("click", {type:"checkAllTrue"}, checkNode);
								$("#checkAllFalse").bind("click", {type:"checkAllFalse"}, checkNode);
								$("#btn_sub").click(function(){		

									 var nodeStr  ="";
								      var zTree = $.fn.zTree.getZTreeObj("orgStrutsTree");
										var nodes = zTree.getCheckedNodes(true);					
								    		for (var i=0; i<nodes.length;  i++) {
								      			nodeStr = nodeStr +nodes[i].id ;
								      			if(i<nodes.length-1){
								      			    nodeStr = nodeStr + ",";
								      			}
								   			}	 
								    		var arr = new Array(20);
											 for(var i in arr.length){
												 arr[i] = "-9";		 
											 }arr[0] = role_cd_value;arr[1]=nodeStr;
											 var eobj = createParamJson(arr); 
											 var eJson = JSON.stringify(eobj);   
												var resultJson = AjaxExchangeBackTextData('giveAuthorityToRole.action',eJson);  
												alertToUserMsg(resultJson); 
								});
							},'JSON'
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
			var zTree = $.fn.zTree.getZTreeObj("orgStrutsTree"),
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
		 
$(document).on('click', '#select_customorg_list', function() {		 
	var treeObj = $.fn.zTree.getZTreeObj("orgStrutsTree");
	var nodes =   treeObj.getCheckedNodes(true); 
	var ids = "", names ="";
	if(nodes.length > 1 &&select_org_multi =='0' ) {
		alertMsg.info("只能选择一个组织");
		return ;
	} 
	for(var i=0;i<nodes.length;i++) {	     
		ids = ids + nodes[i].id +",";
		names = names + nodes[i].name +",";
	}
	ids = ids.substr(0,ids.length-1); names = names.substr(0,names.length-1);
	$.bringBack({id:ids, orgName:names});
});