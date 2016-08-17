// 用于存放PrintControl类的相关脚本

//PrintControl类：用于进行打印控制
function PrintControl(templateFileName) {
	var privateCurPrinterWindow=null;
	var privateLocationTable=null;
	var privateCurTr=null;
	var privateLastTd = null;
	
//---------- 公共方法 ----------
	this.getPrintDocument=function () {
		return privateCurPrinterWindow.document;
	}

    //载入模板文件
    this.loadTemplateFile=function (templateFileName) {
        privateCurPrinterWindow = window.open(templateFileName);
        
        //由于IE对页面采取异步加载方式，以下循环用于保证加载完成！
        do {
        }while(privateCurPrinterWindow.document.readyState!="complete");
    }

	this.setTargetWindow = function (targetWindow) {
		privateCurPrinterWindow = targetWindow;
	}
	
    //将准备好的页面送打印机
    this.print=function () {
		privateCurPrinterWindow.document.execCommand("Print");
    }

	//送固定区域数值
    this.sendValue=function(id,value) {
		var element = privateCurPrinterWindow.document.getElementById(id);
		element.innerText = value;
	}
	
	//送活动区域数值
	this.sendGridValue=function(value,width) {
		var newTd = privateCurPrinterWindow.document.createElement(
				"<td width=\"" + width +
				">" +
				value + 
				"</td>");
		newTd.innerText = value;

		privateCurTr.appendChild(newTd);
		if (newTd.cellIndex==0)
			newTd.className = "xl24b";
		else			
			newTd.className = "xl25b";
		
		privateLastTd = newTd;
	}
	
	//在活动区域开一个新行，以填写活动区域框格
	this.newLine=function() {
		//清除前一行最后一列的宽度，以保持总宽度的一致性
		this.clearWidthOfLastTd();
		
		var newTable = privateCurPrinterWindow.document.createElement(	
			"<table border=0 cellspacing=0 cellpadding=0 id='1'></table>");
		newTable.width = privateLocationTable.width;

		var newTBody = privateCurPrinterWindow.document.createElement("<tbody></tbody>");
		newTable.appendChild(newTBody);
		var newTr = privateCurPrinterWindow.document.createElement("<tr></tr>");
		newTBody.appendChild(newTr);

		privateCurTr = newTr;
		
		privateCurPrinterWindow.document.body.insertBefore(newTable,privateLocationTable);
	}

	//开始一个段
	this.beginSection=function(tableId) {
		privateLocationTable = privateCurPrinterWindow.document.getElementById(tableId);
		if (privateLocationTable==null) return ;
		
		privateLastTd = null;
		
		this.newLine();
	}

	//结束一个段
	this.endSection=function() {
		this.clearWidthOfLastTd();
		if (privateLastTd!=null)
			privateLastTd.removeAttribute("width");
		
	}
			
    //关闭用于打印的预览页面
    this.close=function () {
        privateCurPrinterWindow.close();
    }

	//将生成好的页面送打印机
    this.execute=function () {
        privateCurPrinterWindow.print();
    }

	//取生成的table里面的innerHTML，以便检查。！！用于调试的方法！！
	this.debugGetText=function(tableId) {
		var element = privateCurPrinterWindow.document.getElementById("1");

		return element.outerHTML;
	}
	
//---------- 私有方法 ---------------
	//清除前一行最后一列的宽度，以保持总宽度的一致性
	this.clearWidthOfLastTd=function() {
		if (privateLastTd!=null) {
			privateLastTd.removeAttribute("width");
			privateLastTd = null;
		}
	}
	
	
//---------- 以下构造方法 ----------
	if (templateFileName!=null)
    	this.loadTemplateFile(templateFileName);
}


//-------------- 支持函数 ---------------

