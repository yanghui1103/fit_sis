var obj2 ;// 跳转页面时传递的数据xml
var firstPageNum = 0 ; // 首页的标识
var totalNum = 0 ; // 此时查询出来结果集的行数
var qryTrue = 0;
var firstPageNum2 = 0 ; // 首页的标识
var totalNum2 = 0 ; // 此时查询出来结果集的行数

function loadFontPage(){
   var edom = createParamDom3(document.getElementById('user_id').value,document.getElementById('office_number').value,'1');
   
   var url = "getFontPageData.action";
   var rpc = new XmlRPC(url);
   rpc.sendXml(edom);
   var dom = rpc.getXml() ;
   	var src = document.getElementById("fontTable");
	// xin
	
	// over xin
	
	var rowCount = src.rows.length;
	for(var j=1;j<rowCount;j++)
	{
		src.deleteRow();
	}
   var nodes = dom.selectNodes('/root/fontInfos');
   var ttDom = dom ;
	
	// over   
   for(var i=0;i<nodes.length;i++)  // 最多一页容5条记录
	{
		var val1 = nodes.item(i).childNodes.item(0).text ;
		var val2 = nodes.item(i).childNodes.item(1).text ;
		var val3 = nodes.item(i).childNodes.item(2).text ;
		var val4 = nodes.item(i).childNodes.item(3).text ;
		var newrow = src.insertRow(src.rows.length);

		newrow.className="list-blue";
		var n =3;
		while(n--)
		{
			var newcell = newrow.insertCell();
			newcell.className = "blue";
			switch(n){				
				case 2: newcell.innerHTML = val1;break;
				case 1: newcell.innerHTML = val2;break;
				case 0: newcell.innerHTML = "<a href=\"JavaScript:showFontInfoDetailByCoId('"+val3+"','"+val4+"');\" class=\"link_list\">查看明细</a>";break;
				//case 0: break;
		     }
		   }
		 }    		
		 // 初始化，公示通知类框
		loadFontAnnoce();
}

// 查看某一条具体的上报记录的详情
function showFontInfoDetailByCoId(val1,val2){
   // 创建dom    
    //getApplyInfoByCoId(createParamDom1(val)) ;
    //var resultData = window.open("topApplyMess.jsp",obj2.xml,'height=900,width=800,top=5,left=15,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no'); // ,
    		//obj2,'dialogheigth=9900px,dialogwidth=9300px,dialogleft=300px,dialogtop=500px') ;
   if(val2 == 'apply'){
      window.parent.document.getElementById("mainFrame").src='./auditAdmin/applyAudit.jsp' ;
      return ;
   } 		 
   var checkArr ;
   // 公告类
   window.showModalDialog('contrastAnnoceRecords.jsp',val1,"dialogwidth=1000px,dialogheigth=900px");
   //loadFontPage();
 }
function loadFontAnnoce(){
   loadFontAnnoce2(document.getElementById("fontAnnoceTable"));
   loadFontAnnoce2(document.getElementById("fontTongZTable"));
}
function loadFontAnnoce2(control){
   
  
   var edom = createParamDom3(document.getElementById('user_id').value,document.getElementById('office_number').value,'1');
   var url = "getFontPageData2.action";
   var rpc = new XmlRPC(url);
   rpc.sendXml(edom);
   var dom = rpc.getXml() ; 
   	var src =control ; // document.getElementById("fontAnnoceTable");
	// xin
	
	// over xin
	
	var rowCount = src.rows.length;
	for(var j=1;j<rowCount;j++)
	{
		src.deleteRow();
	}
   var nodes = dom.selectNodes('/root/fontInfos');
   var ttDom = dom ;	
	
   for(var i=0;i<nodes.length&&i<5;i++)  // 最多一页容5条记录
	{
		var val1 = nodes.item(i).childNodes.item(0).text ;
		var val2 = nodes.item(i).childNodes.item(1).text ;
		var val3 = nodes.item(i).childNodes.item(2).text ;
		var newrow = src.insertRow(src.rows.length);

		newrow.className="list-blue";
		var n = 3;
		while(n--)
		{
			var newcell = newrow.insertCell();
			newcell.className = "blue";
			switch(n){				
				case 0: newcell.innerHTML = "<a href=\"JavaScript:showFontInfoDetailByCoId('"+val2+"','"+val3+"');\" class=\"link_list\">"+val1+"</a>";break;
				// case 0: break;
		     }
		   }
		 }    	
} 
// 翻至首页
function turnPageFirst(){
  if(firstPageNum == 0 ){
     alert('无查询结果，不能操作') ;
     return  ;
  }
  getThisNumPageInfos(firstPageNum) ;// 
  document.getElementById('page_no').value = firstPageNum ;
}
// 翻至首页
function turnPageFirst2(){
  if(firstPageNum == 0 ){
     alert('无查询结果，不能操作') ;
     return  ;
  }
  getThisNumPageInfos2(firstPageNum2) ;// 
  document.getElementById('page_no2').value = firstPageNum2 ;
}
// 翻至尾页
function turnPageLast2(){
    if(totalNum == 0 ){
      alert('无查询结果，不能操作') ;
      return  ;
    }
   getThisNumPageInfos2(totalNum2) ;
   document.getElementById('page_no2').value = totalNum2 ;
}
// 翻至尾页
function turnPageLast(){
    if(totalNum == 0 ){
      alert('无查询结果，不能操作') ;
      return  ;
    }
   getThisNumPageInfos(totalNum) ;
   document.getElementById('page_no').value = totalNum ;
}

function getThisNumPageInfos2(){
        // 1，toWords
    var num_page = document.getElementById('page_no2').value ;
    var edom = createParamDom3(document.getElementById('user_id').value,document.getElementById('office_number').value,
                                  num_page);
   var url = "getFontAnnoceData.action";
   var rpc = new XmlRPC(url);
   rpc.sendXml(edom);
   var dom = rpc.getXml() ;   
	var ttDom = dom ;
	var src = document.getElementById("fontAnnoceTable");
	// xin
	
	// over xin
	
	var rowCount = src.rows.length;
	for(var j=1;j<rowCount;j++)
	{
		src.deleteRow();
	}
	var nodes = dom.selectNodes('/root/fontInfos');
		// over
	for(var i=0;i<nodes.length&&i<5;i++)  // 最多一页容5条记录
	{
		var val1 = nodes.item(i).childNodes.item(0).text ;
		var val2 = nodes.item(i).childNodes.item(1).text ;
		var val3 = nodes.item(i).childNodes.item(2).text ;
		var val4 = nodes.item(i).childNodes.item(3).text ;
		var val5 = nodes.item(i).childNodes.item(4).text ;
		var val6 = nodes.item(i).childNodes.item(5).text ;
		var val7 = nodes.item(i).childNodes.item(6).text ;
		var val8 = nodes.item(i).childNodes.item(7).text ;
		var newrow = src.insertRow(src.rows.length);

		newrow.className="list-blue";
		var n = 7;
		while(n--)
		{
			var newcell = newrow.insertCell();
			newcell.className = "blue";
			switch(n){				
				case 6: newcell.innerHTML = val1;break;
				case 5: newcell.innerHTML = val2;break;
				case 4: newcell.innerHTML = val3;break;
				case 3: newcell.innerHTML = val4;break;
				case 2: newcell.innerHTML = val5;break;
				case 1: newcell.innerHTML = val6;break;
				case 0: newcell.innerHTML = "<a href=\"JavaScript:showFontInfoDetailByCoId('"+val7+"','"+val8+"');\" class=\"link_list\">查看明细</a>";break;
				// case 0: break;
		     }
		   }
		 }
}

function getThisNumPageInfos(){
   var num_page = document.getElementById('page_no').value ;
   var edom = createParamDom3(document.getElementById('user_id').value,document.getElementById('office_number').value,num_page);
   var url = "getFontAuditData.action";
   var rpc = new XmlRPC(url);
   rpc.sendXml(edom);
   var dom = rpc.getXml() ; 
   	var src = document.getElementById("fontTable");
	// xin
	
	// over xin
	
	var rowCount = src.rows.length;
	for(var j=1;j<rowCount;j++)
	{
		src.deleteRow();
	}
   var nodes = dom.selectNodes('/root/fontInfos');
   var ttDom = dom ;	
	
	// over   
   for(var i=0;i<nodes.length&&i<5;i++)  // 最多一页容5条记录
	{
		var val1 = nodes.item(i).childNodes.item(0).text ;
		var val2 = nodes.item(i).childNodes.item(1).text ;
		var val3 = nodes.item(i).childNodes.item(2).text ;
		var val4 = nodes.item(i).childNodes.item(3).text ;
		var val5 = nodes.item(i).childNodes.item(4).text ;
		var val6 = nodes.item(i).childNodes.item(5).text ;
		var val7 = nodes.item(i).childNodes.item(6).text ;
		var val8 = nodes.item(i).childNodes.item(7).text ;
		var val9 = nodes.item(i).childNodes.item(8).text ;
		var newrow = src.insertRow(src.rows.length);

		newrow.className="list-blue";
		var n = 8;
		while(n--)
		{
			var newcell = newrow.insertCell();
			newcell.className = "blue";
			switch(n){				
				case 7: newcell.innerHTML = val1;break;
				case 6: newcell.innerHTML = val2;break;
				case 5: newcell.innerHTML = val3;break;
				case 4: newcell.innerHTML = val4;break;
				case 3: newcell.innerHTML = val5;break;
				case 2: newcell.innerHTML = val6;break;
				case 1: newcell.innerHTML = val7;break;
				case 0: newcell.innerHTML = "<a href=\"JavaScript:showFontInfoDetailByCoId('"+val8+"','"+val9+"');\" class=\"link_list\">查看明细</a>";break;
				//case 0: break;
		     }
		   }
		 }    	
}
