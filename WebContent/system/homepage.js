/**
 * 
 */
function initDwzPage(){ 
		DWZ.init("system/dwz.frag.xml", {
			loginUrl:"login.html", loginTitle:"登录",	// 弹出登录对话框
			statusCode:{ok:200, error:300, timeout:301}, //【可选】
	        pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
	        keys: {statusCode:"statusCode", message:"message"}, //【可选】
	        ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
	        debug:false,    // 调试模式 【true|false】
	        callback:function(){
	            initEnv(); 
	        }
	    });
	    $.ajaxSettings.global=true ;  
}

function generateTree(data){ 
    var pwr = JSON.parse($("#pwr").val()) ; 
	var info = pingMenuHead(pwr.list);
	$("#leftMenu").html(info).initUI()  ;
}

function pingMenuHead(data){
	$("#leftMenu").html("");
	var info = "";
    /*
     * 获取菜单信息
     */
    if(null != data && data.length > 0){
        for(var i = 0 ; i < data.length ; i++){
            var d = data[i];
            info += "<div class='accordionHeader'>";
            info += "<h2><span>Folder</span>" + d.page_name + "</h2>";
            info += "</div>";
            info += "<div class='accordionContent'>";
            info += "<ul class='tree treeFolder'>";
            info += pingMenuInfo(d.childs,1);
            info += "</ul>";
            info += "</div>";
        }
    }
    return info;
}

function pingMenuInfo(data,isFirst){
    var info = "";
    /*
     * 获取菜单信息
     */
    if(null != data && data.length > 0){
        if(1 != isFirst){
            info += "<ul>";
        }
        for(var i = 0 ; i < data.length ; i++){
            var d = data[i];
            if('' == d.page_url || null == d.page_url || '-9' == d.page_url){
                /*
                 * 非叶子节点
                 */
                info += "<li>";
                info += "<a>" + d.page_name + "</a>";
                info += pingMenuInfo(d.childs,0);
                info += "</li>";
            }else{
                /*
                 * 叶子节点
                 */
                info += "<li><a href='" + d.page_url +  "' target='navTab' rel='" + d.rel + "'>" + d.page_name + "</a></li>";
            }
        }
        if(1 != isFirst){
            info += "</ul>";
        }
    }
    return info;
}