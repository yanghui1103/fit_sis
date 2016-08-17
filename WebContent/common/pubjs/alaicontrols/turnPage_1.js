// turnPage.js


var theRows = new Array();
var pageIndex;
var pageSize;
var pageCount;
var tempTableId;
var counts;

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
	pageSize = 10;
	if(theRows.length%pageSize == 0)
		pageCount = parseInt(theRows.length/pageSize);
	else pageCount = parseInt(theRows.length/pageSize) + 1;
	counts = theRows.length;
}

function initTable_nopageSize(id,size){
    //alert('ok');
    tempTb = document.getElementById(id);
    
	if(theRows.length == 0){
		for(i=0;i<tempTb.rows.length-1;i++){
		    theRows[i] = new Array(tempTb.rows[i+1].cells[0].innerText.toLowerCase(),tempTb.rows[i+1].outerHTML);
		} 
	}
	//alert(theRows.length);
	tempTableId = id;
	//alert("from initTable_nopageSize pageIndex:" + pageIndex);
	pageIndex = 0;
	//alert("from initTable_nopageSize pageIndex:" + pageIndex);
	if(size != null)
		pageSize = size;

	if(theRows.length%pageSize == 0)
		pageCount = parseInt(theRows.length/pageSize);
	else pageCount = parseInt(theRows.length/pageSize) + 1;
	counts = theRows.length;
}

function bindPage(){
	
	//alert(tempTableId);
	//alert("throw from bindPage pageIndex:" + pageIndex);
    var tempTb = document.getElementById(tempTableId);
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
     tempTb.outerHTML = head + firstrow + str+'</TABLE>';
     formatTable(tempTableId);

}

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
 
 function turnNext(){
    if(pageIndex == pageCount) return;
    //alert("throw from turnNext pageIndex:" + pageIndex);
    pageIndex++;
    //alert("throw from turnNext pageIndex:" + pageIndex);
    bindPage();
 }
 
 function turnPrev(){
    if(pageIndex == 1) return;  
    //alert("throw from turnPrev pageIndex:" + pageIndex);
    pageIndex--; 
    //alert("throw from turnPrev pageIndex:" + pageIndex);
    bindPage();  
 }
 
 function turnFirst(){
    pageIndex = 1;
    bindPage();   
 }
 
 function turnEnd(){
    pageIndex = pageCount;
    bindPage();  
 }

/*
 function formatTable(tableId)
	{
		var table = document.getElementById(tableId)
		if(table.rows.length < 2)
		return false;
		var tr, styleName;
		tr = table.rows[0];
 
		for(var i = 0; i < tr.cells.length; ++i)
		{
			tr.cells[i].className = "greytable";
		}
  
		for(var i = 1; i < table.rows.length; ++i)
		{
			tr = table.rows[i];
			styleName = i % 2 == 0 ? "list-deepblue" : "list-blue";
			for(var j = 0; j < tr.cells.length; ++j)
			{
				tr.cells[j].className = styleName;
			}
		}
	}
*/
 function formatTable(tableId)
	{
		var table = document.getElementById(tableId);
		if(table.rows.length < 2)
		return false;
		var tr, styleName;
		tr = table.rows[0];
 
		for(var i = 0; i < tr.cells.length; ++i)
		{
			tr.cells[i].className = "greytable";
		}
  
		for(var i = 1; i < table.rows.length; ++i)
		{
			styleName = i % 2 == 0 ? "list-deepblue" : "list-blue";
			table.rows[i].className = styleName;
		}
	}
function goToPage(index){
	if(pageCount == 0)
		return;
	if(index < 1 )
	{
		alert("pageIndex 不能小于1");
		return;
	}
	if(index > pageCount)
	{
		alert("pageIndex 不能大于总页数");
		return;
	}
	pageIndex = index;
	//alert("throw from goToPage pageIndex:" + pageIndex);
    bindPage(); 
}

function setBaseVariable(){
	document.getElementsByName("pageIndex")[0].value = pageIndex;
	//alert(pageIndex);
	//alert(pageCount);
}