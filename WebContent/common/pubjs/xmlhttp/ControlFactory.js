
/* gh use
*/
//存储临时行号
var lastIndex = 0;
//生成行的id号
var id1 = 0;

/*
	------------------------------
	 ControlFactory
	------------------------------
	author:	     hongh
	description: create html element.support label,textbox,password,
	             radio,radiolist,checkbox,checkboxlist,dropdownlist,
	             table,property(control collection)

*/
function ControlFactory(){
   
   this.createLabel = f_createLabel;
   this.createTextBox = f_createTextBox;
   this.createPassword = f_createPassword;
   this.createRadio = f_createRadio;
   this.createRadioList = f_createRadioList;
   this.createCheckbox = f_createCheckbox;
   this.createCheckboxList = f_createCheckboxList;
   this.createDropDownList = f_createDropDownList;
   this.createTable = f_createTable;
   this.createProperty = f_createProperty;
   this.createHidden = f_createHidden;
   //创建guanhao's Table
   this.createSpecialTable = f_createSpecialTable;
   
}

function f_createSpecialTable(node,control,flag){
   var specialTable = new SpecialTable();
   return specialTable.create(node,control,flag);
}

function f_createHidden(node,control){
   var hidden = new Hidden();
   return hidden.create(node,control);
}

function f_createProperty(nodes,sameName){
    
    var property = new Property();
    return property.create(nodes,sameName);
}

function f_createLabel(node,control){
   var label = new Label();
   return label.create(node,control);
}

function f_createTable(node,control){
   var table = new Table();
   
   return table.create(node,control);
}

function f_createTextBox(node,control){
   var textBox = new TextBox();
   return textBox.create(node,control);
}


function f_createPassword(node,control) {
   var pwd = new Password();
   return pwd.create(node,control);
   
}

function f_createRadio(item,id,i) {
   var radio = new Radio();
   return radio.create(item,id,i);
}

function f_createRadioList(node,control) {
   var radioList = new RadioList();
   return radioList.create(node,control);
}

function f_createCheckbox(item,id,i) {
   var checkbox = new Checkbox();
   return checkbox.create(item,id,i);
}



function f_createCheckboxList(node,control) {
    var checkboxList = new CheckboxList();
    return checkboxList.create(node,control);
}

function f_createDropDownList(node,control) {
    var list = new DropDownList();
    return list.create(node,control);
}


/*
	------------------------------
	 HtmlControl
	------------------------------
	description: HtmlControl
	             

*/
function HtmlControl(){
}

HtmlControl.prototype.createTitle = function f_createTitle(node){
   var reader = new XmlDomReader();
   var lbl = reader.getAttribute(node,"label");
   return document.createTextNode(lbl);
}



/*
	------------------------------
	 Table
	------------------------------
	description: Table,create table element,auto build columns
	             not support caption
	method:      create(node,table)
	             createRow(cols,name);           

*/
function Table(){
}
Table.prototype.create = function f_create(node,table){
   var reader = new XmlDomReader();
   var items = reader.getItems(node);
   var name = node.getAttribute("id");
   
   if(!table){
      table = document.createElement("TABLE");
      tbody = document.createElement("TBODY");
      table.appendChild(tbody);
   }
   for(var i=0;i<items.length;i++){
      var cols = items[i].selectNodes("./Column");
      var row = this.createRow(cols,name);
      table.children[0].appendChild(row);
   }
   
   return table;
}

Table.prototype.createRow = function f_createRow(cols,name){
   var tr = document.createElement("<TR class='greytable'>");
   
   for(var i=0;i<cols.length;i++){
      var td = document.createElement("<TD class='list-blue'>");
      var type = cols[i].getAttribute("type");
      if(type != "Checkbox"){
         td.innerText = cols[i].text;
      }
      else{
         var chk = document.createElement("<input type='checkbox' name='" + name + "'/>")
         chk.setAttribute("value",cols[i].text);
         td.appendChild(chk);
      }
      tr.appendChild(td);      
   }
   return tr;
}

/*
guan hao use SpecialTable
注释:flag为标志位,"0"代表onclick触发选择产品信息事件,"1"代表onclick触发选择订单类型目录事件
3:代表只需生成一个复选框
2:代表生成cc的table
*/
function SpecialTable(){
}
SpecialTable.prototype.create = function f_create(node,table,flag){
    //var reader = new XmlDomReader();
   // var items = reader.getItems(node);
//    var name = node.getAttribute("id");
    if(!table){
       table = document.createElement("TABLE");
       tbody = document.createElement("TBODY");
       table.appendChild(tbody);
    }
  
   if ( flag == "3") {
   		this.createRowCheckOrdType(node,table);
   } else {
   		var reader = new XmlDomReader();
    	var items = reader.getItems(node);
	    for(var i=0;i<items.length;i++){
	       id1 = lastIndex + 1;	
		   lastIndex += 1;
	       var cols = items[i].selectNodes("./Column");
	//       var hids = items[i].selectNodes("./Column/@hidden");
	       
	      /* if (flag =="3"){
	          //var row = this.createRowCheckOrdType(cols,flag);
	          var row = this.createRowCheckOrdType(items[i],flag);
	       }*/
	       var row = this.createRow(cols,flag);
	       if (flag =="2"){
	       	  var name = items[i].getAttribute("dependId");
	       	  var id = items[i].getAttribute("id");
	       	  row = this.createRowAcct(cols,flag,name,id);
	       }
	       table.children[0].appendChild(row);
	       
	    }
    }
    return table;
}
//帐户的table
SpecialTable.prototype.createRowAcct = function f_createRowAcct(cols,flag,name,id){
	var tr = "";
	var td = "";
	if(name == null){
    	tr = document.createElement("<TR  id='"+id+"' onclick='select(this)'>");
	}else{
		tr = document.createElement("<TR  id='"+id+"' onclick='select(this)' name='"+name+"'>");
	}
    for(var i=0;i<cols.length;i++){
    	var type = cols[i].getAttribute("type");
    	if(type == "hidden"){
        	td = document.createElement("<TD style='display:none'>");
        }else{
        	td = document.createElement("<TD class='list-blue' >");
        }
        td.innerText = cols[i].text;
        tr.appendChild(td); 
    }
     
    return tr;
}
/*由于受理界面订单类型的展示的XML文件是共用于在用产品；所以只要生成一个复选框*/
SpecialTable.prototype.createRowCheckOrdType = function f_createRowCheckOrdType(node,tmpTable){
    /*var cols = node.selectNodes('Item');
    var row =''
    for (var i = 0;i < cols.length;i++) {
    	 if (cols[i].getAttribute('hasChilds') == 'false') {
    		row = this.createChildRow(cols[i]);
    		if (row == '') {
    			continue;
    		}
    		tmpTable.children[0].appendChild(row);
   		 } else {
   			var childCols = cols[i].selectNodes('Item'); 
   			for (var j = 0;j < childCols.length;j++) {
   				row = this.createChildRow(childCols[j]);
   				if (row == '') {
	    			continue;
	    		}
   				tmpTable.children[0].appendChild(row);	
   			}
   		} 			
    }*/
    var col = node.selectSingleNode('Item');
    row = this.createChildRow(col);
    tmpTable.children[0].appendChild(row);	     
}

SpecialTable.prototype.createChildRow = function f_createChildRow(col) {
   		var tr = document.createElement("<TR valign='top'>");
	   	var hidden = col.getAttribute("hidden");
	   	var isEnable = col.getAttribute("isEnable");
	    if(hidden != null){
	       var tdChk = document.createElement("<TD class='black1'>");
	       //if (isEnable !='true') {
	      //     	return '';
	      // }
	       
	       chk = document.createElement("<input id ='"+hidden+"' type='checkbox' onclick ='orderTypeTableClick(this)'>");
	       
	       chk.setAttribute("value",hidden);
	       tdChk.appendChild(chk);          
	       tr.appendChild(tdChk);  
	       var tdText = document.createElement("<TD class='black1'>");   
	       tdText.innerText = col.getAttribute('label');
	       tr.appendChild(tdText);
	    }
   	return tr;
}

SpecialTable.prototype.createRow = function f_createRow(cols,flag){
    var tr = '';
    if(flag == '0'){
       tr = document.createElement("<TR class='greytable' style='cursor:hand' id='"+id1+"' onclick='chooseUnfinishOrder(this)'>");
    }else{
       var isEnable = cols[0].getAttribute("isEnable"); 
       if (isEnable =='true') {
       		tr = document.createElement("<TR class='greytable' id='"+id1+"' ondblclick='chooseOrderType(this)' isEnabled ='true'>");
       } else {
       		tr = document.createElement("<TR class='greytable' id='"+id1+"' onmouseover='displayReason(this,1)' onmouseout='displayReason(this,0)' isEnabled ='false'>");
       }
       	
    }
    
    for(var i=0;i<cols.length;i++){
        var hidden   = cols[i].getAttribute("hidden");
//        alert(hidden);
        if(hidden != null){
           var td = document.createElement("<TD class='list-blue' id='"+hidden+"'>");
        }else{
           var td = document.createElement("<TD class='list-blue'>");
        }
        
        var type = cols[i].getAttribute("type");
//        alert(type);
        if(type != "Checkbox"){
           td.innerText = cols[i].text;
        }
        else{
           var chk = document.createElement("<input type='checkbox' id='prod"+ id1 +"'/>");
           chk.setAttribute("value",cols[i].text);
           td.appendChild(chk);          
        }
        tr.appendChild(td);     
    }
    return tr;
}


function InputControl(){   
}
InputControl.prototype.create = function f_create(node,control,sameName){
 
   var reader = new XmlDomReader();
   var name = '';
   if(sameName == null && sameName != undefined) {
      name = reader.getAttribute(node,'id');
   } else {
      name = sameName;
   }
   if(!control){
      control = document.createElement("<input type='text' class='box1' name='" + name + "'/>");
   }
   return this.bind(node,control);
}
InputControl.prototype.bind = function f_bind(node,element){
   var reader = new XmlDomReader();   
   var id = reader.getAttribute(node,'id');
   var text = reader.getNodeItemText(node,0);
   element.setAttribute("id",id);
   element.setAttribute("value",text);
   return element;
}


/*
	------------------------------
	 TextBox
	------------------------------
	description: TextBox,create <input> element ,value of type is text 
	             control is null will return a new <input> element.
	             else set the control value
	method:      create(node,control)
	             bind(node,element);           

*/
function TextBox(){
}
TextBox.prototype = new InputControl();
TextBox.prototype.constructor = InputControl;
TextBox.prototype.supperclass = InputControl.prototype;

TextBox.prototype.create = function f_create(node,control,sameName){
  
   control = InputControl.prototype.create.apply(this,arguments);
   control.setAttribute('type','text');
   return control;
   
}



/*
	------------------------------
	 Password
	------------------------------
	description: Password,create <input> element ,value of type is password 
	             control is null will return a new <input> element.
	             else set the control value
	method:      create(node,control)
	             inherit bind(node,element) method from TextBox;           

*/
function Password(){
   
}
Password.prototype = new InputControl();
Password.prototype.constructor = InputControl;
Password.supperclass = InputControl.prototype;

Password.prototype.create = function f_create(node,control,sameName){
   control = InputControl.prototype.create.apply(this,arguments);
   control.setAttribute('value','');
   control.setAttribute("type","password");
   return control;
}


function Hidden(){
}

Hidden.prototype = new InputControl();
Hidden.prototype.constructor = InputControl;
Hidden.supperclass = InputControl.prototype;

Hidden.prototype.create = function f_create(node,control,sameName){
   control = InputControl.prototype.create.apply(this,arguments);
   control.setAttribute('type','hidden');
   return control;
}

/*
	------------------------------
	 Radio
	------------------------------

	description: Radio,create <input> element ,value of type is radio 
	             return radio control;
	method:      create(item,id)
	             bind(element,item)          

*/
function Radio(){
   
}

Radio.prototype = new HtmlControl();
Radio.prototype.constructor = HtmlControl;
Radio.prototype.supperclass = HtmlControl.prototype;

Radio.prototype.create = function f_create(item,id){
   var name = '';
   if(sameName != null) {
      name = sameName
   } else {
      name = id;
   }
   var element = document.createElement("<input type='radio' name='" + name + "' id='" + id + "'/>"); 
   return this.bind(element,item); 
}

Radio.prototype.bind = function f_radiobind(element,item){
   var result = new Array();
      
   var reader = new XmlDomReader(); 
   var text = reader.getItemText(item); 
   var val = reader.getItemValue(item);
  
   element.setAttribute("value",val);
   var checked = reader.getAttribute(item,"checked");
   if(checked != '') option.setAttribute("checked",checked);
	      
   var lab = f_createLabel(text);
   result.push(lab);
   result.push(element);
   return result;
}


/*
	------------------------------
	 Checkbox
	------------------------------
	description: Checkbox,create <input> element ,value of type is checkbox 
	             return Checkbox control;
	method:      create(item,id)
	             inherit bind(element,item) method from Radio;          

*/
function Checkbox(){
}

Checkbox.prototype = new Radio();
Checkbox.prototype.constructor = Radio;
Checkbox.prototype.supperclass = Radio.prototype;

Checkbox.prototype.create = function f_create(item,id,sameName){
   var name = '';
   if(sameName != null) {
      name = sameName
   } else {
      name = id;
   }
   var element = document.createElement("<input type='checkbox' name='" + name + "' id='" + id + "'/>"); 
   return this.bind(element,item);
}


/*
	------------------------------
	 RadioList
	------------------------------
	description: RadioList,composite <input> element ,value of type is radio 
	             return span control;
	method:      create(node,control)
	             return span control;          

*/
function RadioList(){
}

RadioList.prototype = new HtmlControl();
RadioList.prototype.constructor = HtmlControl;
RadioList.prototype.supperclass = HtmlControl.prototype;


RadioList.prototype.create = function f_create(node,control){
   var reader = new XmlDomReader();
   var items = reader.getItems(node);
   var id = reader.getAttribute(node,"id"); 
   
   var spanId = id + 'span';
   var span;
   span = document.getElementById(spanId);
   
   if(!span){
      span = document.createElement("<span id='" + spanId + "'></span>");
   }
   else{
      span.innerHTML = '';
   }
   span.appendChild(this.createTitle(node));
   for(var i=0;i<items.length;i++){
      var radio = new Radio(); 
      var rad = radio.create(items[i],id);
      
      span.appendChild(rad[0]);
      span.appendChild(rad[1]);
   }
   return span;
}



/*
	------------------------------
	 CheckboxList
	------------------------------
	description: CheckboxList,composite <input> element ,value of type is checkbox 
	             return span control;
	method:      create(node,control)
	             return span control;          

*/
function CheckboxList(){
  this.items = new Array();
}

CheckboxList.prototype = new RadioList();
CheckboxList.prototype.constructor = RadioList;
CheckboxList.prototype.supperclass = RadioList.prototype;


CheckboxList.prototype.create = function f_create(node,control){
   var reader = new XmlDomReader();
   var items = reader.getItems(node);
   var id = reader.getAttribute(node,"id"); 
   var spanId = id + '.span';
   var span;
   span = document.getElementById(spanId);
 
   if(!span){
      span = document.createElement("<span id='" + spanId + "'></span>");   
   }
   else{
      span.innerHTML = '';
   }
   
   span.appendChild(this.createTitle(node));
    
   for(var i=0;i<items.length;i++){
      var checkbox = new Checkbox(); 
      var box = checkbox.create(items[i],id,i);      
      span.appendChild(box[0]);
      span.appendChild(box[1]);
   }  
   return span;   
}

/*
	------------------------------
	 DropDownList
	------------------------------
	description: DropDownList
	             return DropDownList;
	method:      create(node,control)
	             return dropdownlist control;          

*/
function DropDownList(){
}

DropDownList.prototype = new HtmlControl();
DropDownList.prototype.constructor = HtmlControl;
DropDownList.prototype.supperclass = HtmlControl.prototype;


DropDownList.prototype.create = function f_create(node,control,sameName){
   var dropdownlist;
   if(control){
      dropdownlist = control;
   }
   else{
      var id = reader.getAttribute(node,"id");
      var name = '';
      if(sameName != null) {
         name = sameName
      } else {
         name = id;
      }
      
      dropdownlist = document.createElement("<select class='box1' name='" + name + "' id='" + id + "'></select>");    
   }
   
   dropdownlist.innerHTML = '';
 
   return this.bind(node,dropdownlist);

}
DropDownList.prototype.bind = function f_bind(node,control){
   var reader = new XmlDomReader();
   var items = reader.getItems(node);
  
   for(var i=0;i<items.length;i++){
       var option = document.createElement("<option value='" + reader.getItemValue(items[i]) + "'></option>");

	   option.appendChild(document.createTextNode(reader.getItemText(items[i])));
	   
	   //var selected = reader.getAttribute(node,"selected");
	   var selected = reader.getAttribute(items[i],"selected");   //modify 10/22
	   
	   if(selected != null) option.setAttribute("selected",selected);
	   
	   control.appendChild(option);
   }
   return control;
   
}

/*
	------------------------------
	 Label
	------------------------------
	description: Label
	             return Label;
	method:      create(node)
	             return Label control;          

*/
function Label(){
}

Label.prototype.create = function f_create(node){
   var reader = new XmlDomReader();
   var lab = reader.getLabel(node);
   element = document.createElement('<Label class="blue_8c">');
   element.innerText = lab?lab:'';
   return element;
}

/*
	------------------------------
	 Property
	------------------------------
	description: Property
	             return Array(Control Collection );
	method:      create(nodes)
	             createSingle(node,type)          

*/
function Property(){
}
Property.prototype.create = function f_create(nodes,sameName){
    
    var reader = new XmlDomReader();
    var controls = new Array();
    var control = null;
    
    for(var i=0;i<nodes.length;i++){
       controls.push(this.createSingle(nodes[i],'Label'));
       
       controls.push(this.createSingle(nodes[i],reader.getType(nodes[i]),sameName));
       
    }
    
    return controls;
}

Property.prototype.createSingle = function f_createSingle(node,type,sameName){
   var control = eval('new ' + type + '()');
  
   return control.create(node,null,sameName);
}



