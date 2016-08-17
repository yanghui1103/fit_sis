/*============================
	Author : fason 阿信
	Email		: fason_pfx@hotmail.com
============================*/

var Icon = {
	root :	"folderclose.gif",
	folderopen :  "folderopen.gif",
	folderclosed :  "folderclose.gif",
	Rminus:	  "minus_m.gif",
	Rplus:	 "plus_m.gif",
	minusbottom:	   "minus_end.gif",
	plusbottom:	  "plus_end.gif",
	minus:	 "minus.gif",
	plus:	 "plus.gif",
	join:	 "midblk.gif",
	joinbottom:	 "lastblk.gif",
	blank:	 "blank.gif",
	line:	 "line.gif",
	file:	 "file.gif"
}
var tem;
window.TV = [];
function TreeView()
{
	this.id = window.TV.length;
	window.TV[this.id] = this;
	this.target = "_self";
	this.checkbox = false;
	this.checkChild = false;
	this.Nodes ={ "root" : { ID : "root", ParentID : "sroot", Text : null, Href : null, Image : null, childNodes: new Array() } };
}
var tv = TreeView.prototype;
tv.setTarget = function(v) {
	this.target = v;
}
tv.setCheckChild = function(v) {
	this.checkChild = v;
}
tv.setCheckbox = function(v) {
	this.checkbox = v;
}
tv.setName = function(v) {
	this.name = v;
}
tv.setImagePath = function(sPath) {
	for(o in Icon){
		tmp = sPath + Icon[o];
		Icon[o] = new Image();
		Icon[o].src = tmp;
	}
}
//tv.add = function(iD,ParentiD,sText,sHref,sImage) {   modify by qiurun,   实现checkbox制定，实现checkbox（onclick）事件的自定义
//	this.Nodes[iD] = { ID : iD, ParentID : ParentiD, Text : sText, Href : sHref, Image : sImage , childNodes: new Array() , open : false , checked : false };
tv.add = function(iD,ParentiD,sText,sHref,sImage,sControl) {

	
	try{
		if (sImage != null && sImage != "")
			sImage = Icon[sImage].src;
	}catch(e){
		//alert(e.message);
		sImage = null;
	}
	this.Nodes["'"+iD+"'"] = { ID : iD, ParentID : ParentiD, Text : sText, Href : sHref, Image : sImage , childNodes: new Array() , open : false , checked : false , control : sControl};
	var ch = this.getNode(ParentiD).childNodes;
	ch[ch.length] = this.Nodes["'"+iD+"'"];
};
tv.getNode = function(sKey) {
	try {
		var tmp = this.Nodes[sKey].ID;
	}catch(e){
		sKey = "'" + sKey + "'";
	}
	if (typeof this.Nodes[sKey] != "undefined")
	{
		return this.Nodes[sKey];
	}
	return null;
};
tv.getParentNode = function(ID) {
	var key = this.getNode(ID).ParentID;
	if(this.getNode(key) == null) return null;
	return this.getNode(key);
};
tv.hasChildNodes = function(sKey) {
	return this.getNode(sKey).childNodes.length > 0;
};
tv.isLastNode = function(sKey) {
	var node = this.getNode(sKey);
	var par = this.getParentNode(sKey);
	if(par == null) node.isLast = true;
	else if (typeof node["isLast"] == "undefined") {
		for(var i = 0;i<par.childNodes.length;i++)
			if(par.childNodes[i].ID == sKey) break;
		node.isLast =  (i == par.childNodes.length-1)
	}
	return node.isLast;
};
tv.getRoot = function(ID) {
	var par = this.getParentNode(ID);
	if (this.getNode(ID).ParentID == "root")
	{
		return this.getNode(ID);
	}
	else
	{
		return this.getRoot(par.ID);
	}
}
tv.drawNode = function(ID) {
	var html = "";
	try {
		var node = this.getNode(ID);
	} catch(e) {
		var node = this.getNode("'"+ID+"'");
	}
	//alert(node);
	//alert(this.getRoot(ID));
	var rootid = this.getRoot(ID).ID;
	var hc = this.hasChildNodes(ID);
	html += '<div class="TreeNode" nowrap>'+this.drawIndent(ID)+
				'<label style="CURSOR: hand" id="node'+ID+'" class="Anchor" target="'+this.target+'" onclick ="'+ node.Href +';tmp=\''+ID+'\';setSelected(\''+ID+'\')" ondblclick="window.TV['+this.id+'].openHandler(\''+ID+'\')">'+
				(node.control ? node.control : '') +
				'<img id="folder'+ID+'" src="'+( node.Image ? node.Image : Icon.folderclosed.src)+'" align="absmiddle">'+
				'<span style="font: 12px;">'+ node.Text +'</span></label></div>\n';
				//(node.checkbox ? ('<input type=checkbox id="'+ID+'" name="chk" onclick="'+ node.sCheckBoxOnCli +'" '+ (node.ifchk ? 'checked' : '') +' >') : '')+
				//实现获取所有的checkbox,所有的checkbox同名，id为节点的key
				//(node.checkbox ? ('<input type=checkbox id="checkbox'+ID+'" name="'+this.name+'" onclick="window.TV['+this.id+'].oncheck('+ID+')">') : '')+
	
	if (hc) {
		var io = ID ==  rootid;
		node.open = io;
		html += ('<div id="container'+ID+'" style="display:'+(io ? '' : 'none')+'">\n');
		html += this.addNode(ID);
		html += '</div>\n';
	}
	return html;
}
//从这里开始遍历
tv.addNode = function(ID) {
	
	var node = this.getNode(ID);
	var html = "";
	for(var i = 0;i<node.childNodes.length;i++) {
		html += this.drawNode(node.childNodes[i].ID);};
	return html;
}

tv.drawIndent = function(ID) {
	var s = ''
	var ir = this.getRoot(ID).ID == ID;
	var hc = this.hasChildNodes(ID);
	if(this.getParentNode(ID) != null)
		s += ((hc ? '<Label onclick="javascript:void window.TV['+this.id+'].openHandler(\''+ID+'\');" target="_self">':'')+'<img id="handler'+ID+'" src="'+ (this.hasChildNodes(ID) ? (ir ? Icon.Rminus.src : (this.isLastNode(ID) ? Icon.plusbottom.src : Icon.plus.src)) : (ir ? Icon.blank.src : (this.isLastNode(ID) ? Icon.joinbottom.src : Icon.join.src))) + '" align="absmiddle">'+(hc?'</Label>':''));
	var p = this.getParentNode(ID);
	while(p != null)
	{
		if(this.getParentNode(p.ID) == null)break;
		s = ('<img src="'+(this.isLastNode(p.ID) ? Icon.blank.src : Icon.line.src) + '" align="absmiddle">')+s;
		p = this.getParentNode(p.ID);
	}
	return s;
}
tv.setSelected = function(ID) {
	if(this.selectedID) { document.getElementById("node" + this.selectedID).className = "Anchor";}
	this.selectedID = ID;
	document.getElementById("node" + ID).className = "selected";
}

setSelected = function(ID) {
	
	if(tem != null) { document.getElementById("node" + tem).className = "Anchor";}
	tem = ID;
	document.getElementById("node" + ID).className = "selected";
}
tv.oncheck = function(ID) {
	
	var o = this.getNode(ID);
	var v = o.checked;
	o.checked = document.getElementById(ID).checked;
	//if (v == false)
	//{   
		this.checkChildren(ID,o.checked);
	//}
	
	//this.checkParent(ID);
};
tv.check = function(ID,v){
	this.getNode(ID).checked = v;
	try {
		document.getElementById(ID).checked = v;
	} catch(e) {
		null;
	}
}
tv.checkChildren = function(ID,v){
	var ch = this.getNode(ID).childNodes;
	for(var i = 0;i<ch.length;i++){
		this.check(ch[i].ID,v);
		this.checkChildren(ch[i].ID,v);
	}
}
tv.checkParent = function(ID,v) {
	var par = this.getParentNode(ID);
	if(ID != this.getRoot(ID).ID){
		for(var j = 0;j<par.childNodes.length;j++)
			if(!par.childNodes[j].checked) break;
		//alert(par.ID);
		this.check(par.ID,v);
		this.checkParent(par.ID,v);
	}
}

tv.openHandler = function(ID) {
	if (this.hasChildNodes(ID)) {
		if (this.getNode(ID).open) {
			this.collapse(ID);
		}
		else {
			this.expand(ID);
		}
	}
}
tv.expand = function(ID) {
	var handler = document.getElementById("handler"+ID);
	var container = document.getElementById("container"+ID);
	handler.src = this.getRoot(ID).ID == ID ? Icon.Rminus.src : ( this.getNode(ID).isLast ? Icon.minusbottom.src : Icon.minus.src);
	container.style.display = '';
	this.getNode(ID).open = true;
}
tv.collapse = function(ID) {
	var handler = document.getElementById("handler"+ID);
	var container = document.getElementById("container"+ID);
	handler.src = this.getRoot(ID).ID == ID ? Icon.Rplus.src : ( this.getNode(ID).isLast ? Icon.plusbottom.src : Icon.plus.src);
	container.style.display = 'none';
	this.getNode(ID).open = false;
}
tv.openFolder = function(ID)
{	
	if(this.selectedID){
		if(this.getNode(this.selectedID).Image == null) {document.getElementById("folder"+this.selectedID).src = Icon.folderclosed.src;}
	}
	var folder = document.getElementById("folder" + ID);
	if(this.getNode(ID).Image == null) folder.src = Icon.folderopen.src;
	this.setSelected(ID);
	this.selectedID = ID;
	folder.parentNode.blur();
}
tv.toString = function() {
	return this.addNode("root");
}
//调用该函数，使得初始化树的时候，树是关闭的
tv.closeAll = function() {
	var nodes = this.getNode(0).childNodes;
	for (var i = 0; i < nodes.length; i++) {
		this.collapse(nodes[i].ID);	
	}
}