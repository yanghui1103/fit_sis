/*
	------------------------------
	 JScript TreeListControl v2.0
	------------------------------
	author:	Nathan Ridley
	email:	generalgherkin@yahoo.com
	web:	www.extroversity.com (inactive at the moment - September 14 2002)
	
	Legal:
	This control is free for commercial and non-commercial use provided that I am credited and
	this header is not removed or changed in any way. Please email me if you are using it!
	
*/

function tlcRelocateDivider(x) {
	var t1 = new Number(x.parentElement.offsetLeft);
	var t2 = new Number(document.body.leftMargin);
	var t3 = t1+t2-18;
	var t4 = 0;//new Number(x.parentElement.parentElement.parentElement.parentElement.offsetTop);
	var t5 = new Number(x.parentElement.parentElement.parentElement.parentElement.parentElement.offsetLeft);
	if(x.dragging) {
		try
		{
			t1 = window.event.clientX;
			t2 = document.body.leftMargin;
			t3 = x.parentElement.previousSibling.offsetLeft;
			var dist = (t1-t2) - t3 - t5;
			if(dist>4) {
				var w1 = x.parentElement.previousSibling.style.width;
				var cell = x.parentElement.previousSibling;
				cell.style.width = dist+"px";
				cell.width = dist;
				var tbl = x.parentElement.parentElement.parentElement.parentElement;
				tbl.rows[1].cells[cell.cellIndex].firstChild.style.width = (dist-4)+"px";
				cell.lastChild.style.width = (dist)+"px";
				x.style.left = window.event.clientX-150-t5;
				//x.style.top = "0px";//(window.event.clientY-100);
				x.style.width = "300px";
				x.style.height = "200px";
				if(tbl.rows[1].cells[cell.cellIndex].firstChild.offsetWidth < tbl.rows[1].cells[cell.cellIndex].firstChild.scrollWidth)
					tbl.rows[1].cells[cell.cellIndex].firstChild.className = "tlcRowDivClipped";
				else
					tbl.rows[1].cells[cell.cellIndex].firstChild.className = "tlcRowDiv";
			}
		}
		catch(ex)
		{ }
	} else {
		x.style.top = t4;
		x.style.left = t3;
		x.style.width = "17px";
		x.style.height = "16px";
	}
}

za = 0;
function TreeListControl(id) {
	document.onmousemove = document.recalc;
	//public members
	this.id = id;
	this.rowcolor1 = "white";
	this.rowcolor2 = "azure";
	this.showHeaders = true;
	this.autoRedrawIcons = true;

	//private members
	this._columns = new Array();
	this._columnCount = 0;
	this._created = false;
	this._nextID = 0;
	this._tree = null;
	this._nodequeue = new Array();
}

TreeListControl.prototype.pathtoicons = "";

TreeListControl.prototype.iconConnectBridge = "icon_connect_bridge.gif";
TreeListControl.prototype.iconConnectMore = "icon_connect_more.gif";
TreeListControl.prototype.iconConnectEnd = "icon_connect_end.gif";
TreeListControl.prototype.iconEmpty = "icon_empty.gif";
TreeListControl.prototype.iconCollapseMore = "icon_collapse_more.gif";
TreeListControl.prototype.iconCollapseEnd = "icon_collapse_end.gif";
TreeListControl.prototype.iconExpandMore = "icon_expand_more.gif";
TreeListControl.prototype.iconExpandEnd = "icon_expand_end.gif";

TreeListControl.prototype.getLabelObj = function(id,col){
	var o;
	try {
		if(!col || col==0)
			o = document.getElementById(this.id+"_node_"+id+"_label");
		else
			o = document.getElementById(this.id+"_node_"+col+"_"+id+"_label");
	}
	catch(ex) { o = null; }
	return o; 
}

TreeListControl.prototype.getChildrenObj = function(id,col){
	var o;
	try {
		if(!col || col==0)
			o = document.getElementById(this.id+"_node_"+id+"_children");
		else
			o = document.getElementById(this.id+"_node_"+col+"_"+id+"_children");
	}
	catch(ex) { o = null; }
	return o; 
}

TreeListControl.prototype.getNodeObj = function(id,col){
	var o;
	try {
		if(!col || col==0)
			o = document.getElementById(this.id+"_node_"+id);
		else
			o = document.getElementById(this.id+"_node_"+col+"_"+id);
	}
	catch(ex) { o = null; }
	return o; 
}

TreeListControl.prototype.createIn = function(obj) { 
	if(this._created) return;
	this._created = true;
	
	var outer = document.createElement("div");
	outer.className = "tlcOuterDiv";
	var t = document.createElement("table");
	this._tree = t;
	t.id = this.id;
	t.className = "tlcMainTable";
	t.border = 0;
	t.cellPadding = 0;
	t.cellSpacing = 0;
	t.width = "100%";
	t.onselectstart = function() {
		return false;
	}
	
	t.insertRow();
	for(var i=0; i<this._columnCount; i++) {
		t.rows[0].insertCell();
		var c = t.rows[0].cells[i];
		c.className = "tlcHeaderCell";
		c.noWrap = true;
		if(i==0) c.style.paddingLeft = "1px";
		var s2 = document.createElement("span");
		if(this._columns[i].align) {
			s2.style.textAlign = this._columns[i].align;
			c.style.textAlign = this._columns[i].align;
			c.align = this._columns[i].align;
		}
		s2.className = "tlcHeaderLabel";
		s2.innerHTML = this._columns[i].name;
		if(i>0) {
			c.appendChild(s2);
			var s1 = document.createElement("span");
			s1.dragging = false;
			s1.className = "tlcColumnDivider";
			s1.onmousedown = function() {
				this.dragging = true;
			}
			s1.onmouseup = function() {
				this.dragging = false;
			}
			s1.onmousemove = function() {
				if(this.dragging) {
					this.style.pixelLeft = window.event.clientX;
				}
			}
			s1.onmouseleave = function() {
				this.dragging = false;
			}
			s1.id = this.id+"_cmover"+i;
			c.insertBefore(s1,s2);
		} else {
			c.appendChild(s2);
		}
	}
	t.insertRow();
	t.rows[1].style.backgroundColor = "white";
	for(i=0; i<this._columnCount; i++) {
		t.rows[1].insertCell();
		c = t.rows[1].cells[i];
		c.className = "tlcRow";
		var div = document.createElement("div");
		div.className = "tlcRowDiv";
		if(this._columns[i].width) {
			div.style.width = (this._columns[i].width-4)+"px";
		}
		c.appendChild(div);
		var w = this._columns[i].width;
	}
	outer.appendChild(t);
	obj.appendChild(outer);
	for(var i=0; i<this._columnCount-1; i++) {
		c = t.rows[0].cells[i];
		if(this._columns[i].width){
			c.style.width = this._columns[i].width+"px";
			c.width = this._columns[i].width;
		} else {
			c.style.width = (this._columns[i].name.length*8)+"px";
			c.width = this._columns[i].name.length*8;
		}
	}
}

TreeListControl.prototype.addColumn = function(name, width, align) {
	this._columns[this._columnCount] = new tlcColumn(name, width, align);
	this._columnCount++;
}

TreeListControl.prototype.addNode = function(parentid, iconsrc, expand) {  //arguments beyond what is defined are the text to go in each column e.g. addNode(parentid, columntext1, columntext2, ...)
	if(parentid!=null && parentid!=0) { //add as child of specified parent
		var o, id;
		o = this.getChildrenObj(parentid);
		if(o == null) { //add to the queue of floating nodes (nodes that can't be added because their parent doesn't exist)
			this._nodequeue.push(new tlcFloatingNode(arguments));
		} else { //insert into the tree
			id = this.addChildTo(arguments);
			this.checkQueue(id);
			return id;
		}
	} else { //add to root
		id = this.addChildTo(arguments);
		this.checkQueue(id);
		return id;
	}
	return null;
}

TreeListControl.prototype.checkQueue = function(parentid) {
	//check the floating node queue to see if any of them can now be inserted into the tree
	var parentobj = this.getChildrenObj(parentid);
	for(var i=0; i<this._nodequeue.length; i++) {
		if(this._nodequeue[i].args[0] == parentid) {
			var fnode = this._nodequeue.removeElement(i);
			this.checkQueue(this.addChildTo(fnode.args));
			i=0;
		}
	}
}

TreeListControl.prototype.getNodeDepth = function(nodeobj) {
	var depth = arguments.length == 1 ? 0 : arguments[1];
	if(nodeobj.parentElement.parentElement.tagName == "TD")
		return depth;
	else
		return this.getNodeDepth(nodeobj.parentElement.parentElement, depth+1);
}

TreeListControl.prototype.addChildTo = function(args) {
	//this function must, for each column, locate the parent node and create a new item at the end of its child list
	var id = this._nextID;
	var parent, obj;
	if(args[0]){ //if we have a parent, add node as its child
		parent = this.getChildrenObj(args[0]);
		obj = this.createNode(args[3], this.getNodeDepth(this.getNodeObj(args[0]))+1, args[1], args[2]);
	} else { //otherwise add as a root node
		parent = this._tree.rows[1].cells[0].firstChild;
		obj = this.createNode(args[3], 0, args[1], args[2]);
	}
	parent.appendChild(obj);
	var label2Index = 4;
	//now add the secondary column items
	for(var i=label2Index; i<(this._columnCount-1)+label2Index; i++) {
		if(args[0])
			parent = this.getChildrenObj(args[0], i-(label2Index-1));
		else
			parent = this._tree.rows[1].cells[i-(label2Index-1)].firstChild;
		var obj2 = this.createSecondaryNode(args[i], id, i-(label2Index-1), args[2]);
		parent.appendChild(obj2);
	}
	if(this.autoRedrawIcons) {
		//check to see if the column is clipping any content and adjust the class to match
		var tbl = this._tree;
		for(i=0; i<this._columnCount-1; i++) {
			if(tbl.rows[1].cells[i].firstChild.offsetWidth < tbl.rows[1].cells[i].firstChild.scrollWidth)
				tbl.rows[1].cells[i].firstChild.className = "tlcRowDivClipped";
			else
				tbl.rows[1].cells[i].firstChild.className = "tlcRowDiv";
		}
		this.redrawIcons(obj);
		if(obj.parentElement.parentElement.tagName != "TD"){ //need to redraw previous sibling so that the new bridging icons show
			this.redrawIcons(obj.parentElement.parentElement);
		}
	}
	//return the id of the new child
	return id;
}

TreeListControl.prototype.createNode = function (label, depth, iconsrc, showChildren) {
	var id = this._nextID
	this._nextID++;
	var oLabel = document.createElement("div"); //create the container div for the label row
	oLabel.className = "tlcNodeLabel";
	oLabel.id = this.id+"_node_"+id+"_label";
	for(var i=0; i<depth; i++) {//create a blank image for each depth level
		var img = document.createElement("img");
		img.width = 16;
		img.height = 16;
		img.align = "absmiddle";
		oLabel.appendChild(img);
	}
	img = document.createElement("img"); //create an image for the expand/collapse icon
	img.width = 16;
	img.height = 16;
	img.align = "absmiddle";
	oLabel.appendChild(img);
	img = document.createElement("img"); //create an image for the node's icon
	img.width = 16;
	img.height = 16;
	img.align = "absmiddle";
	img.src = iconsrc;
	oLabel.appendChild(img);
	var txt = document.createElement("span"); //create the span tag to hold the actual label text
	txt.className = "tlcNodeLabelText";
	txt.innerHTML = label.replace(/\[\[id\]\]/g, id+"");
	txt.id = this.id+"_node_"+id+"_labeltext";
	oLabel.appendChild(txt);
	
	var oChildren = document.createElement("div"); //create the container div for all of this node's children
	oChildren.className = "tlcNodeChildren";
	oChildren.id = this.id+"_node_"+id+"_children";
	if(!showChildren)
		oChildren.style.display = "none";
	
	var oNode = document.createElement("div") //create the container div for all the node contents
	oNode.className = "tlcNode";
	oNode.id = this.id+"_node_"+id;
	oNode.nodeID = id;
	oNode.appendChild(oLabel);
	oNode.appendChild(oChildren);
	oNode.parentTree = this;
	
	return oNode;
}

TreeListControl.prototype.createSecondaryNode = function (label, id, column, showChildren) {
	var oLabel = document.createElement("div"); //create the container div for the label row
	oLabel.className = "tlcNodeLabel";
	oLabel.id = this.id+"_node_"+column+"_"+id+"_label";
	oLabel.onmouseover = function() {
		this.parentElement.parentTree.highlightRow(this, true);
	}
	oLabel.onmouseout = function() {
		this.parentElement.parentTree.highlightRow(this, false);
	}
	if(this._columns[column].align)
		oLabel.style.textAlign = this._columns[column].align;
	var txt = document.createElement("span"); //create the span tag to hold the actual label text
	txt.className = "tlcNodeLabelText";
	txt.id = this.id+"_node_"+column+"_"+id+"_labeltext";
	txt.innerHTML = label.replace(/\[\[id\]\]/g, id+"");
	
	oLabel.appendChild(txt);
	
	var oChildren = document.createElement("div"); //create the container div for all of this node's children
	oChildren.className = "tlcNodeChildren";
	oChildren.id = this.id+"_node_"+column+"_"+id+"_children";
	if(!showChildren)
		oChildren.style.display = "none";
	
	var oNode = document.createElement("div") //create the container div for all the node contents
	oNode.className = "tlcNode";
	oNode.id = this.id+"_node_"+column+"_"+id;
	oNode.nodeID = id;
	oNode.appendChild(oLabel);
	oNode.appendChild(oChildren);
	oNode.parentTree = this;
	
	return oNode;
}

TreeListControl.prototype.highlightRow = function(obj, bHover) {
	var classname = bHover ? "tlcNodeLabelTextHovering" : "tlcNodeLabelText";
	var tree = obj.parentElement.parentTree;
	var id = obj.parentElement.nodeID;
	var o = tree.getLabelObj(id).lastChild;
	if(o) o.className = classname;
}

TreeListControl.prototype.redrawIcons = function(node, arr){
	if(!arr){ //arr contains flags specifying whether the indexed depth level contains a bridge icon (true) or an empty icon (false)
		arr = new Array();
		//iterate back to the root to fill arr with the correct values
		var pobj = node;
		do {
			arr.unshift(pobj.nextSibling ? true : false);
			if(pobj.parentElement.parentElement.tagName != "TD")
				pobj = pobj.parentElement.parentElement;
			else
				break;
		} while (true);
	}
	//redraw the bridging icons
	var obj = node.firstChild;
	var s = "";
	for(var i=0; i<obj.childNodes.length-3; i++){ //ignore the last three child nodes because they are the text, icon and attachment icons
		obj.childNodes[i].src = this.pathtoicons + (arr[i] ? this.iconConnectBridge : this.iconEmpty);
	}
	//redraw the connection icon
	obj = obj.childNodes[obj.childNodes.length-3];
	if(node.lastChild.childNodes.length == 0) { //no children, just draw the plain attachment icon
		if(node.nextSibling) { //we are not the last node, so draw a bridging attachment icon
			obj.src = this.pathtoicons + this.iconConnectMore;
		} else { //we are the last node, so draw an end attachment icon
			obj.src = this.pathtoicons + this.iconConnectEnd;
		}
	} else { //this node has children, so we need an expand/collapse icon
		if(node.nextSibling) {
			obj.src = this.pathtoicons + (node.lastChild.style.display == "none" ? this.iconExpandMore : this.iconCollapseMore);
		} else {
			obj.src = this.pathtoicons + (node.lastChild.style.display == "none" ? this.iconExpandEnd : this.iconCollapseEnd);
		}
		obj.onclick = this.expandCollapse;
	}
	arr[obj.parentElement.childNodes.length-3] = node.nextSibling ? true : false; //add this node's bridging state to the array
	obj = node.lastChild.childNodes;
	for(i=0; i<obj.length; i++) //redraw the icons for all the child nodes of this node
		this.redrawIcons(obj[i],arr);
}

TreeListControl.prototype.redrawAllIcons = function(){
	var temp = this.autoRedrawIcons;
	this.autoRedrawIcons = true;
	var c = this._tree.rows[1].cells[0].firstChild.childNodes;
	for(var i=0; i<c.length; i++)
		this.redrawIcons(c[i]);
	this.autoRedrawIcons = temp;
	var tbl = this._tree;
	for(var i=0; i<this._columnCount-1; i++) {
		if(tbl.rows[1].cells[i].firstChild.offsetWidth < tbl.rows[1].cells[i].firstChild.scrollWidth)
			tbl.rows[1].cells[i].firstChild.className = "tlcRowDivClipped";
		else
			tbl.rows[1].cells[i].firstChild.className = "tlcRowDiv";
	}
}

TreeListControl.prototype.expandCollapse = function(){
	var img = event.srcElement;
	var obj = img.parentElement.nextSibling;
	var id = obj.parentElement.nodeID;
	var tree = obj.parentElement.parentTree;
	if(obj.style.display == "none") {
		obj.style.display = "";
		img.src = tree.pathtoicons + (obj.parentElement.nextSibling ? tree.iconCollapseMore : tree.iconCollapseEnd);
	} else {
		obj.style.display = "none";
		img.src = tree.pathtoicons + (obj.parentElement.nextSibling ? tree.iconExpandMore : tree.iconExpandEnd);
	}
	for(var i=1; i<tree._columnCount; i++) {
		var obj2 = tree.getChildrenObj(id, i);
		obj2.style.display = (obj2.style.display == "none") ? "" : "none";
	}
		
	if(tree._tree.rows[1].cells[0].firstChild.offsetWidth < tree._tree.rows[1].cells[0].firstChild.scrollWidth)
		tree._tree.rows[1].cells[0].firstChild.className = "tlcRowDivClipped";
	else
		tree._tree.rows[1].cells[0].firstChild.className = "tlcRowDiv";
}

TreeListControl.prototype.setText = function(id, col, txt) {
	var o;
	try {
		if(!col || col==0)
			o = document.getElementById(this.id+"_node_"+id+"_labeltext");
		else
			o = document.getElementById(this.id+"_node_"+col+"_"+id+"_labeltext");
	}
	catch(ex) { o = null; }
	if(o)
		o.innerHTML = txt.replace(/\[\[id\]\]/g, id+"");
}

TreeListControl.prototype.deleteNode = function(id) {
	var prev;
	for(var i=0; i<this._columnCount; i++) {
		var o = this.getNodeObj(id,i);
		if(o) {
			if(i==0) prev = o.parentElement.parentElement;
			o.removeNode(true);
		}
	}
	if(prev) {
		if(prev.tagName == "TD")
			this.redrawAllIcons();
		else
			this.redrawIcons(prev);
	}
}

TreeListControl.prototype.deleteAllChildren = function(id) {
	for(var i=0; i<this._columnCount; i++) {
		var o = this.getNodeObj(id,i);
		if(o) o.lastChild.innerHTML = "";
	}
}

function tlcFloatingNode(args) {
	this.args = args;
}

function tlcColumn(name, width, align) {
	this.name = name;
	this.width = width;
	this.align = align;
	this.objref = null;
}

Array.prototype.swap = function(a,b) {
	var temp = this[a];
	this[a] = this[b];
	this[b] = temp;
}

Array.prototype.removeElement = function(n) {
	if(n>0)	this.swap(0,n);
	return this.shift();
}
