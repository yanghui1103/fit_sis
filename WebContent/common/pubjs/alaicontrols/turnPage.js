// turnPage.js


var theRows = new Array();//存储table的所有行
var pageIndex;            //当前页码
var pageSize = null;      //每页行数
var pageCount;            //总页数
var tempTableId;
var counts;               //总行数

//初始化table设置
function initTable(id){
    //alert('ok');
    tempTb = document.getElementById(id);

	if(theRows.length == 0){
		for(i=0;i<tempTb.rows.length-1;i++){
		    theRows[i] = new Array(tempTb.rows[i+1].cells[0].innerText.toLowerCase(),tempTb.rows[i+1].outerHTML);
		}
	}
	//alert(theRows.length);
	tempTableId = id;
	pageIndex = 0;
	if(pageSize == null) pageSize = 5;
	pageCount = parseInt(theRows.length/pageSize) + 1;
	counts = theRows.length;
}

function initTable_nopageSize(id,size){
    tempTb = document.getElementById(id);

	if(theRows.length == 0){
		for(i=0;i<tempTb.rows.length;i++){//修改
		    theRows[i] = new Array(tempTb.rows[i].cells[0].innerText.toLowerCase(),tempTb.rows[i].outerHTML);
		}
	}

	tempTableId = id;
	pageIndex = 0;

	if(size != null)
		pageSize = size;

	if(theRows.length%pageSize == 0)
		pageCount = parseInt(theRows.length/pageSize);
	else pageCount = parseInt(theRows.length/pageSize) + 1;
	counts = theRows.length;
}

//显示数据
function bindPage(){
    var tempTb = document.getElementById(tempTableId);;
    var rows = theRows.slice((pageIndex-1)*pageSize,pageIndex*pageSize)
    //alert((pageIndex-1)*pageSize+1);
    var str = '';
       for(i=0;i<rows.length;i++){
          str += rows[i][1];
       }

     var tempHead = tempTb.outerHTML ;
     var tempInner = tempTb.innerHTML;
     var head = tempHead.replace(tempInner,'');

     var head = head.replace('</TABLE>','');
     var firstrow = tempTb.rows[0].outerHTML;
     tempTb.outerHTML = head  + str+'</TABLE>';//修改原来是head+ firstrow + str+'</TABLE>'
}

//翻页函数
function turnPage(command){
     switch(command){
        case 'prev':
           turnPrev();
           break;
        case 'next':
           turnNext()
           break;
        case 'first':
           turnFirst();
           break;
        case 'end':
           pageIndex = pageCount;
           turnEnd();
           break;
     }
     //pageInfo.innerText = pageIndex + '/' + pageCount;
 }

 //下一页
 function turnNext(){
    if(pageIndex == pageCount) return;
    pageIndex++;
    bindPage();
 }

 //上一页
 function turnPrev(){
    if(pageIndex == 1) return;
    pageIndex--;
    bindPage();
 }

 //第一页
 function turnFirst(){
    pageIndex = 1;
    bindPage();
 }

 //末页
 function turnEnd(){
    pageIndex = pageCount;
    bindPage();
 }
