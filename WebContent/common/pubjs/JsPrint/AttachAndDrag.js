//用于实现web页面上的拖拽处理
//todo:因为是js,部分代码未做严格的代码保护检查,将来可能需要完善

//常量定义
var RESIZE_NW="NW";
var RESIZE_SW="SW";
var RESIZE_NE="NE";
var RESIZE_SE="SE";

var RESIZE_N ="N";
var RESIZE_S ="S";
var RESIZE_W ="W";
var RESIZE_E ="E";

var DOT_WIDTH = 6;
var DOT_SIZE_DIST=DOT_WIDTH/2-1;
var NO_CHANGE=999999; //由于程序逻辑原因,需要一个数字来表示不做任何修改,因为无法用null表示

//---- 全局变量 ----------
var globalAttachAndDragObject = null; //用于保存当前拖拽类,因为要事件处理需要实例化的句柄

function processMouseDown() {
	globalAttachAndDragObject.processOnMouseDown();
}

function processMouseUp() {
	globalAttachAndDragObject.processOnMouseUp();
}

function processMouseMove() {
	globalAttachAndDragObject.processOnMouseMove();
}
//--------- 辅助函数 ---------------
//取得绝对坐标(外部拷贝来的函数)
function getAbsCoord(el){
	for (var lx=0,ly=0;el!=null;
		lx+=el.offsetLeft,ly+=el.offsetTop,el=el.offsetParent);
	return {x:lx,y:ly}
}
	
function getAbsWidth(targetTd) {
	var curObj = targetTd;
	while(curObj!=null) {
		if (curObj.offsetWidth!=null)
			return curObj.offsetWidth;
			
		curObj = curObj.offsetParent;
	}
	
	return null;
}

function getAbsHeight(targetTd) {
	var curObj = targetTd;
	while(curObj!=null) {
		if (curObj.offsetHeight!=null)
			return curObj.offsetHeight;
			
		curObj = curObj.offsetParent;
	}
	
	return null;
}

//--------- AttachAndDrag类 -----------
function AttachAndDrag() {
	//todo:以下三个变量是关联的,做成三个变量的原因是希望是代码更容易看懂,有空可以进行修改
	var privateCurHtmlBody = null;
	var privateCurHtmlDocument= null;
	var privateCurHtmlWindow  = null;
	
	var privateAttachedObject = null;  //被选中的对象
	var privateSrcElement = null;      //当前事件的对象,注意与被选中的对象不同
	
	var privateOffsetX = null;	//横纵坐标
	var privateOffsetY = null;
	
	var privateOnAttach = null; //发生Attach时的事件处理句柄,用于外部引用
	
//********* 公共方法 *************
//--------
	this.enablePage = function(documentNeedActive) {
		privateCurHtmlBody = documentNeedActive.body;
		privateCurHtmlDocument = documentNeedActive;
		privateCurHtmlWindow = documentNeedActive.parentWindow;

		privateCurHtmlBody.onmousedown = processMouseDown;
		privateCurHtmlBody.onmouseup = processMouseUp;
		privateCurHtmlBody.onmousemove = processMouseMove;
	}
	
	this.disablePage = function() {
		if (privateCurHtmlBody==null)
			return ;
			
		this.privateDetachObject();
		
		privateCurHtmlBody.onmousedown = null;
		privateCurHtmlBody.onmouseup = null;
		privateCurHtmlBody.onmousemove = null;

		privateCurHtmlBody = null;
	}

	this.setOnAttach = function(attachEventHandler) {
		privateOnAttach = attachEventHandler;
	}
	
//--------
	this.processOnMouseDown = function() {
		if (privateCurHtmlWindow.event.button!=1) return ;
		privateOffsetX = privateCurHtmlWindow.event.offsetX;
		privateOffsetY = privateCurHtmlWindow.event.offsetY;
		
		privateSrcElement = privateCurHtmlWindow.event.srcElement;
		var srcElementType = this.privateJudgeElementType(privateSrcElement);
		switch (srcElementType) {
			case "V" : //vml,vml对象
			case "O" : //object,界面对象
				this.privateAttachObject();
				break;
			case "B" : //body,即点在空白上面
				this.privateDetachObject();
				break;
			case "R" : //在放下时,不必做任何事情
				break;
			default  : //其他未识别的类型,暂时不采取任何措施
				privateSrcElement=null;
				break;
		}
	}

	this.processOnMouseUp = function() {
		if (privateCurHtmlWindow.event.button!=1) return ;
	
		privateSrcElement = null;
	}

	this.processOnMouseMove = function() {
		if (privateCurHtmlWindow.event.button!=1) return ;
		
		var srcElementType = this.privateJudgeElementType(privateSrcElement);
		switch (srcElementType) {
			case "V" : //vml,vml对象
			case "O" : //object,界面对象
				this.privateMoveObject();
				break;
			case "B" : //body,即点在空白上面,不必做任何处理
				break;
			case "R" : //resize,指对象外面的8个resize小点
				this.privateResizeObject();
				break;
			default  : //其他未识别的类型,暂时不采取任何措施
				break;
		}
		
	}
	

//************** 私有方法 ***************
	this.privateJudgeElementType = function(srcElement) {
		//如果有"dotType"自定义属性,说明是生成的resize小点
		if (srcElement.dotType!=null) {
			return "R";
		}

		//如果是body,则直接返回
		if (srcElement==privateCurHtmlBody) { 
			return "B";
		}

		//如果是系统能够attach的几种对象,返回"O"(对象)
		srcElementTagName = srcElement.tagName.toUpperCase();
		switch (srcElementTagName) {
			case "RECT" :
			case "OVAL" :
				return "V";
			case "TD"   :
				return "O";
		}
		
		//以上几种已知情况都不是,返回"U"(未知的)
		return "U"; 
	}
	
	this.privateAttachObject = function() {
		if (privateAttachedObject===privateCurHtmlWindow.event.srcElement)
			return ;

		if ( privateAttachedObject!=null )
			this.deleteAllDots();

		privateAttachedObject = privateCurHtmlWindow.event.srcElement;
		this.createAllDots();
		
		if (privateOnAttach!=null)
			privateOnAttach(privateAttachedObject);
	}

	this.privateDetachObject = function() {
		if ( privateAttachedObject==null )
			return ;
		this.deleteAllDots();
		privateAttachedObject = null;
	}
	
	this.privateMoveObject = function() {
		var newLeft = privateCurHtmlWindow.event.x-privateOffsetX;
		var newTop = privateCurHtmlWindow.event.y-privateOffsetY;
		
		var srcElementType = this.privateJudgeElementType(privateSrcElement);
		if (srcElementType=="V") {
			privateAttachedObject.style.left = newLeft;
			privateAttachedObject.style.top  = newTop;
		} else {
			privateAttachedObject.left = newLeft;
			privateAttachedObject.top  = newTop;
		}

		this.deleteAllDots();
		this.createAllDots();
	}

	this.privateResizeObject = function() {
		if ( (privateSrcElement.dotType!=RESIZE_E) && (privateSrcElement.dotType!=RESIZE_S) ) {
			if ( this.privateJudgeElementType(privateAttachedObject)=="O" )
				return ;
		}
		
		
		var newLeft,newTop,newWidth,newHeight;

		var eventX = parseFloat(privateCurHtmlWindow.event.x)-DOT_SIZE_DIST;
		var eventY = parseFloat(privateCurHtmlWindow.event.y)-DOT_SIZE_DIST;

		var locationAndSize = this.privateGetObjectLocationAndSize(privateAttachedObject);
		var oldLeft   = locationAndSize.left;
		var oldTop    = locationAndSize.top;
		var oldWidth  = locationAndSize.width;
		var oldHeight = locationAndSize.height;

		switch(privateSrcElement.dotType) {
			case RESIZE_NW :
				newLeft  = eventX;
				newTop   = eventY;
				newWidth = oldWidth+oldLeft-eventX;
				newHeight= oldHeight+oldTop-eventY;
				break;
			case RESIZE_SW :
				newLeft  = eventX;
				newTop   = oldTop;
				newWidth = oldWidth+oldLeft-eventX;
				newHeight= eventY-oldTop;
				break;
			case RESIZE_NE :
				newLeft  = oldLeft;
				newTop   = eventY;
				newWidth = eventX-oldLeft;
				newHeight= oldHeight+oldTop-eventY;
				break;
			case RESIZE_SE :
				newLeft  = oldLeft;
				newTop   = oldTop;
				newWidth = eventX-oldLeft;
				newHeight= eventY-oldTop;
				break;

			case RESIZE_N :
				newLeft  = oldLeft;
				newTop   = eventY;
				newWidth = NO_CHANGE;
				newHeight= oldHeight+oldTop-newTop;
				break;
			case RESIZE_S :
				newLeft  = oldLeft;
				newTop   = oldTop;
				newWidth = NO_CHANGE;
				newHeight= eventY-oldTop;
				break;
			case RESIZE_W :
				newLeft  = eventX;
				newTop   = oldTop;
				newWidth = oldWidth+oldLeft-eventX;
				newHeight= NO_CHANGE;
				break;
			case RESIZE_E :
				newLeft  = oldLeft;
				newTop   = oldTop;
				newWidth = eventX-oldLeft;
				newHeight= NO_CHANGE;
				break;
			default : 
				window.alert("Unknown dot type!");
				return ;
		}
		
		if ( (newLeft<=0) || (newTop<=0) || (newWidth<=0) || (newHeight<=0) ) {
			return ;
		}
		
		this.privateSetLocationAndSize(privateAttachedObject,newLeft,newTop,newWidth,newHeight);
		
		this.deleteAllDots();
		this.createAllDots();
	}

//-----------	
	var DOT_INIT_STYLE = "'position:absolute;left:0;top:0;width:6;height:6;'"; 
	var DOT_INIT_COLOR = "black";
	var DOT_CURSOR_NAME_SUFFIX = "-resize";
	this.createOneDot = function (left,top,dotName) {
		var dot = privateCurHtmlDocument.createElement(
					"<v:rect id=\""+dotName+"\" style="+DOT_INIT_STYLE+" dotType=\"U\"/>");
		privateCurHtmlBody.firstChild.appendChild(dot);
		dot.style.left = left;
		dot.style.top = top;
		dot.fillcolor = DOT_INIT_COLOR;
		dot.dotType = dotName;
		
		dot.style.cursor=dotName+DOT_CURSOR_NAME_SUFFIX; 
	}	

	this.privateGetObjectLocationAndSize = function(obj) {
		var tagName = obj.tagName.toUpperCase();
		switch (tagName) {
			case "RECT" :
			case "OVAL" :
				return { 
					left:parseFloat(obj.style.left),
					top:parseFloat(obj.style.top),
					width:parseFloat(obj.style.width),
					height:parseFloat(obj.style.height) };
			case "TD"   :
				var coord = getAbsCoord(obj);
				var curWidth  = getAbsWidth(obj);
				var curHeight = getAbsHeight(obj);
				return {
					left:coord.x,
					top:coord.y,
					width:curWidth,
					height:curHeight };
		}

	}
	
	//todo : 以下两个函数应该移到外面
	function setTdWidth(targetTd,newWidth) {
		var curTr = targetTd.parentElement;

			//将该行最后一个td的宽度置空,以避免TD按比例调整的混乱
		curTr.lastChild.width = "";
		targetTd.width = newWidth;
	}
	
	function setTdHeight(targetTd,newHeight) {
		var curTr = targetTd.parentElement;
		for (var i=0;i<curTr.children.length;i++) {
			var curTd = curTr.children(i);
			if (i==0) {
				curTd.height = newHeight;
			} else {
				curTd.height = "";
			}
		} 
	}
	
	this.privateSetLocationAndSize = function(obj,left,top,width,height) {
		var tagName = obj.tagName.toUpperCase();
		switch (tagName) {
			case "RECT" :
			case "OVAL" :
				obj.style.left = left;
				obj.style.top = top;
				if (width!=NO_CHANGE)
					obj.style.width = width;
				if (height!=NO_CHANGE) {
					obj.style.height = height;
				}
				break;
			case "TD"   :
				if (width!=NO_CHANGE)
					setTdWidth(obj,width);
				if (height!=NO_CHANGE)
					setTdHeight(obj,height);
				break;
		}
		
	}
	
	this.createAllDots = function () {
		var locationAndSize = this.privateGetObjectLocationAndSize(privateAttachedObject);
		var curLeft   = locationAndSize.left-DOT_SIZE_DIST;
		var curTop    = locationAndSize.top-DOT_SIZE_DIST;
		var curWidth  = locationAndSize.width;
		var curHeight = locationAndSize.height;

		var curRight = curLeft+curWidth;
		var curBottom= curTop +curHeight;

		this.createOneDot(curLeft,curTop,RESIZE_NW);
		this.createOneDot(curLeft,curBottom,RESIZE_SW);
		this.createOneDot(curRight,curTop,RESIZE_NE);
		this.createOneDot(curRight,curBottom,RESIZE_SE);

		this.createOneDot( (curLeft+curRight)/2,curTop,RESIZE_N );
		this.createOneDot( (curLeft+curRight)/2,curBottom,RESIZE_S );
		this.createOneDot( curLeft,(curTop+curBottom)/2,RESIZE_W );
		this.createOneDot( curRight,(curTop+curBottom)/2,RESIZE_E );
	}
	
	this.deleteElementFromBody = function (elementId) {
		var targetElement = privateCurHtmlDocument.getElementById(elementId);
		if (targetElement===null)
			return ;

		targetElement.removeNode();
	}

	this.deleteAllDots = function () {
		this.deleteElementFromBody(RESIZE_NW);
		this.deleteElementFromBody(RESIZE_SW);
		this.deleteElementFromBody(RESIZE_NE);
		this.deleteElementFromBody(RESIZE_SE);
		this.deleteElementFromBody(RESIZE_N);
		this.deleteElementFromBody(RESIZE_S);
		this.deleteElementFromBody(RESIZE_W);
		this.deleteElementFromBody(RESIZE_E);
	}
	
//********* 构造方法 *************
	
}

function enablePageForAttach(targetDocument) {
	alert("开始编辑模板: "+targetDocument.title);
	globalAttachAndDragObject = new AttachAndDrag();
	globalAttachAndDragObject.enablePage(targetDocument);
	
	return globalAttachAndDragObject;
}

function disablePageForAttach() {
	globalAttachAndDragObject.disablePage();
}

