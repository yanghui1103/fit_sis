// DomAsy.js

// XmlDomReader class
function XmlDomReader() {
    this.nodeslist;
    this.getAllXml = f_getAllXml;
    this.getNodesByName = f_getNodesByName;
    this.getAttribute = f_getNodeAttr;
    this.getControlById = f_getControlById;
    this.getControls = f_getControls;
    this.getItems = f_getItems;
    this.getNodeItemText = f_getNodeItemText;
    this.getItemText = f_getItemText;
    this.getItemValue = f_getItemValue;
    this.getLabel = f_getLabel;
    this.getProperty = f_getProperty;
    this.getType = f_getType;
    this.getTableControlById = f_getTableControlById;
}

function f_getType(node){
   return this.getAttribute(node,'type');
}

function f_getLabel(node){
   return this.getAttribute(node,'label');
}

function f_getItems(node){
   return node.selectNodes('./Item');
}

function f_getProperty(node){
   return node.selectNodes('./Property');
}

function f_getNodeItemText(node,index){
    return this.getItemText(node.selectNodes('./Item')[index]);
}


function f_getItemText(item){
    return item.selectNodes('Text')[0].text;
}


function f_getItemValue(item){
    return item.selectNodes('Value')[0].text;
}


function f_getAllXml(dom){
    return dom.xml;
}


function f_getNodesByName(dom,nodeName){
    if(dom.documentElement) return dom.documentElement.selectNodes(nodeName);
    return dom.selectNodes(nodeName);
}


function f_getNodeAttr(node,attribute){
    return node.getAttribute(attribute);
}


function f_getControlById(dom,id){   
    return dom.selectSingleNode('./Control[@id="' + id +'"]');   
}


function f_getControls(dom){
    return this.getNodesByName(dom,'./Control');
}


function f_getTableControlById(dom,id){
    return dom.selectSingleNode('//Control[@id="' + id +'"]'); 
}

