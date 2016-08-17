// ControlPage.js
/*页面控制*/
var componentInfoArr = new Array();
var g_orderType = 0;
var framesTd = new Array();
framesTd[0] = 'baseInfoTD';
framesTd[1] = 'feeInfoTD';
framesTd[2] = 'nwkInfoTD';
framesTd[3] = 'acctInfoTD';
initFrameTD = 'baseInfoTD';
	
function ControlPageManager(){   
    this.controlAll = f_controlAll;				    //控制页面元素
    this.controlAppointEle = f_controlAppointEle;	//控制指定的元素
}

function f_controlAll(eventPageId,orderType,controlDomObject,isClick) {
	var controlAllElement = new ControlAllElement();
	return controlAllElement.controlInfo(eventPageId,orderType,controlDomObject,isClick);
}

function f_controlAppointEle(controlElement,isVisible,isDisable) {
	var controlAppointElement = new ControlAppointElement();
	return controlAppointElement.controlOneElement(controlElement,isVisible,isDisable);
}

//公共控制
function PublicContol() {
}
PublicContol.prototype.findRec = function f_findRec(id,orderType){
	for (var i = 1;i < componentInfoArr.length+1;i++) {		
		if ((componentInfoArr[i-1].id == id) && (componentInfoArr[i-1].isEnable=='true') && (componentInfoArr[i-1].orderType != orderType))  {
			return i;
		}
	}	
	return 0;
}

PublicContol.prototype.findPageTd = function f_findPageTd(id,orderType){
	for (var i = 1;i < componentInfoArr.length+1;i++) {		
		if ((componentInfoArr[i-1].id == id) && (componentInfoArr[i-1].isEnable=='true'))  {
			return i;
		}
	}	
	return 0;
}

PublicContol.prototype.setComponentEnable = function f_setComponentEnable(id,orderType) {
	for (var i = 0;i < componentInfoArr.length;i++) {
		if ((componentInfoArr[i].id == id) && (componentInfoArr[i].orderType == orderType))  {
			componentInfoArr[i].isEnable = 'false';
		}
	}
}

PublicContol.prototype.componentType = function f_componentType() {
	var id = 0;
	var orderType = 0;
	var isEnable = 0;
}

PublicContol.prototype.setDocumentDisable = function f_setDocumentDisable() {
	var documentElements = document.all;
	for (var i = 0;i < documentElements.length;i++) {
		if ((documentElements[i].comptype =='Panel') || (documentElements[i].comptype =='Component')) {
			documentElements[i].disabled = true;
		}
	}
}

//显示正确的页面
PublicContol.prototype.setPageDisplay = function f_setPageDisplay(orderType){
	var i = 0,returnNum = 0,iframesTd = '',iframeInfo = '';
	var frames = document.getElementsByTagName('Iframe');
	var oldtd = document.getElementById(initFrameTD);
	
	for (i = 0; i < frames.length;i++) {
		returnNum = this.findPageTd(frames[i].id+'TD',orderType);
		if (returnNum != 0) {
			iframesTd = frames[i].id+'TD';
			break;
		}
	}

	if (iframesTd == '') {
		oldtd.className = "tag2";
		initFrame(frames,'baseInfo');
   		rollFrame(frames,'baseInfo');
		return false;
	}

	if (iframesTd == 'baseInfoTD') {
		iframeInfo = 'baseInfo';
	}
	
	if (iframesTd == 'feeInfoTD') {
		iframeInfo = 'feeInfo';
	}
	
	if (iframesTd == 'nwkInfoTD') {
		iframeInfo = 'nwkInfo';
	}
	
	if (iframesTd == 'acctInfoTD') {
		iframeInfo = 'acctInfo';
	}
  	
   	frames(iframeInfo).style.display == 'block';
   	initFrame(frames,iframeInfo);
   	rollFrame(frames,iframeInfo);   	  
			
	var newtd = document.getElementById(iframesTd);
	oldtd.className = "tag2";
	newtd.className = "tag1";
	initFrameTD = newtd.id;
}

//控制控件
function ControlControl() {
}
ControlControl.prototype = new PublicContol();
ControlControl.prototype.constructor = PublicContol;
ControlControl.prototype.supperclass = PublicContol.prototype;

ControlControl.prototype.controlComponent = function f_controlComponent(eventPageId,componentUnit,documentEle,isClick,orderType){
	var recordNum = 0;

	var componentReadOnly = componentUnit.getAttribute('readOnly');
	var componentIsVisible= componentUnit.getAttribute('isVisible');
	var componentElement  = documentEle.getElementById(componentUnit.getAttribute('controlId'));

	if (componentElement == null) {
			return  false;
	}
		
	if (eventPageId != '') {							//从IFRAME进入控制
		if (componentReadOnly == '1') {					//可读写
			componentElement.disabled = false;
			
			componentInfoArr[componentInfoArr.length] = new this.componentType();
			componentInfoArr[componentInfoArr.length-1].id = componentElement.getAttribute('id');
			componentInfoArr[componentInfoArr.length-1].orderType = orderType;
			componentInfoArr[componentInfoArr.length-1].isEnable = 'true';
			
		}
		if (componentIsVisible  == '0') {
			componentElement.style.display = 'none';
		}
		
		return true;
	}
	
	if (isClick != 'click') {				//非点击事件
		if (componentReadOnly == '1') {				//可读写控件
			componentElement.disabled = false;
			
			componentInfoArr[componentInfoArr.length] = new this.componentType();
			componentInfoArr[componentInfoArr.length-1].id = componentElement.getAttribute('id');
			componentInfoArr[componentInfoArr.length-1].orderType = orderType;
			componentInfoArr[componentInfoArr.length-1].isEnable = 'true';
		}
		if (componentIsVisible  == '0') {
			componentElement.style.display = 'none';
		}
		return true;
	}
	
	if (event.srcElement.checked) {					//选中某个业务时
		if (componentReadOnly == '1') {				//可读写控件
			componentElement.disabled = false;
		
			componentInfoArr[componentInfoArr.length] = new this.componentType();
			componentInfoArr[componentInfoArr.length-1].id = componentElement.getAttribute('id');
			componentInfoArr[componentInfoArr.length-1].orderType = orderType;
			componentInfoArr[componentInfoArr.length-1].isEnable = 'true';
		}
		if (componentIsVisible  == '0') {
			componentElement.style.display = 'none';
		}
		if (componentIsVisible  == '1') {
			componentElement.style.display = '';
		}
	} else {										//取消选中的业务
		this.setComponentEnable(componentElement.getAttribute('id'),orderType);
		recordNum = this.findRec(componentElement.getAttribute('id'),orderType);
		
		if (recordNum == 0) {						//只与该业务有关
			componentElement.disabled = true;
		}
	}
}

//控制面板 
function PanelControl() {
}
PanelControl.prototype = new ControlControl();
PanelControl.prototype.constructor = ControlControl;
PanelControl.prototype.supperclass = ControlControl.prototype;

PanelControl.prototype.controlPanel = function f_controlPanel(eventPageId,panelUnit,documentEle,isClick,orderType){
	var panelVisible = panelUnit.getAttribute('isVisible');	
	var panelReadOnly = panelUnit.getAttribute('readOnly');	
	var panelElement = documentEle.getElementById(panelUnit.getAttribute('controlId'));
		
	if (panelElement == null) {   //找不到元素
		return false;
	}	
	if (panelReadOnly == '1') {
		documentEle.getElementById(panelUnit.getAttribute('controlId')).disabled = false;
		
		var componentNodes = panelUnit.selectNodes('Component');
		
		for (var i = 0;i < componentNodes.length;i++) {
			this.controlComponent(eventPageId,componentNodes[i],documentEle,isClick,orderType);
		}
	}
	/*
	if (panelVisible == '0') {				//该面板不可见		
		panelElement.style.display = 'none';
	} else {							    //面板可见,控制其里的控件	
		var componentNodes = panelUnit.selectNodes('Component');
		
		for (var i = 0;i < componentNodes.length;i++) {
			this.controlComponent(eventPageId,componentNodes[i],documentEle,isClick,orderType);
		}
	}*/
}

//控制页面
function PageControl() {
}
PageControl.prototype = new PanelControl();
PageControl.prototype.constructor = PanelControl;
PageControl.prototype.supperclass = PanelControl.prototype;

PageControl.prototype.controlPage = function f_controlPage(eventPageId,pageId,pageUnit,isClick,orderType){
	var i = 0;
	var pageChildNodes = pageUnit.childNodes;
	var pageReadOnly = pageUnit.getAttribute("readOnly");
	var frames = '',documentEle = '';
	if (eventPageId == '') {
		
		if (pageId == 'orderInfo') {			//当前页面
			documentEle = document;
		} else if (isClick=='click') {								//控制IFRAME
			frames = document.getElementsByTagName('Iframe');
			documentEle = document.frames(pageId).document;
		} else if ((pageId != 'orderInfo') && (isClick=='onload')){
			return false;
		}
	} else {
		documentEle = document;
	}
	
	for (i = 0;i < pageChildNodes.length;i++) {			//控制页面中的元素
		if(pageChildNodes[i].getAttribute("controlType") == "Component") {		//控制控件
			this.controlComponent(eventPageId,pageChildNodes[i],documentEle,isClick,orderType);
		}		
		if(pageChildNodes[i].getAttribute("controlType") == "Panel") {			//控制面板
			this.controlPanel(eventPageId,pageChildNodes[i],documentEle,isClick,orderType);
		}
	}
	if ((i == pageChildNodes.length) &&(eventPageId == '')) {
		this.setPageDisplay(orderType);
	}
}

//总控制信息
function ControlAllElement() {
}
ControlAllElement.prototype = new PageControl();
ControlAllElement.prototype.constructor = PageControl;
ControlAllElement.prototype.supperclass = PageControl.prototype;

ControlAllElement.prototype.controlInfo = function f_controlInfo(eventPageId,orderType,controlDomObject,isClick){     	
 	if (orderType == ORDER_TYPE_NEW) {
 		return true;
 	}
 	
 	if (controlDomObject.controlDom == null) {
 		/*if (eventPageId =='') {
 			alert("该业务类型对应的控制信息没有配置,请与系统管理员联系!");
 		}*/
 		return false;
 	}
 	
 	if (componentInfoArr.length == 0) {
 		this.setDocumentDisable();
 	}
  	g_orderType = orderType;
  	
 	var pageId = '',pageNodes = '';

    pageNodes   = controlDomObject.controlDom.selectNodes('Page[@orderType="'+orderType+'"]');

    for (var i = 0;i < pageNodes.length;i++) {    	
    	if (!pageNodes[i].hasChildNodes()) {
    		continue;
    	}
    	if (eventPageId =='') {
    		pageId = pageNodes[i].getAttribute('controlId');	    	
	    	this.controlPage(eventPageId,pageId,pageNodes[i],isClick,orderType);
	    } else { 	
	    	pageId = eventPageId;
	    	
	    	if (pageNodes[i].getAttribute('controlId') ==eventPageId) {
	    		this.controlPage(eventPageId,pageId,pageNodes[i],isClick,orderType);
	    	}
	    }
    } 
}

function ControlAppointElement() {
}
ControlAppointElement.prototype.controlOneElement = function f_controlOneElement(controlElement,isVisible,isDisabled){
	//isVisible:none,block
	//isDisabled:true,false
	var documentElement = document.getElementById(controlElement);
	if (isVisible =='') {
		documentElement.style.display = isVisible;
	}

	if (isDisabled =='') {
		documentElement.disabled = isDisabled;
	}
}

//控制主框架标签的移动
function controlOrder() {
	var bodyInfo = document.getElementsByTagName('body');
	var orderTd = '',win = '',id = '';
	if (bodyInfo[0].getAttribute('id') == 'custInfo')   {
		orderTd = document.getElementById('order');
		//win = parent.parent;
		id = 'frameOrder';
	} else if (bodyInfo[0].getAttribute('id') == 'orderInfo') {
		orderTd = parent.document.getElementById('car');
		win = parent;
		id = 'frameCar';
		win.switchFrame(orderTd,id,true);
	}
	orderTd.disabled = false;
}