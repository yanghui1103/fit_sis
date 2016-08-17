// XmlRPC.js
function f_createDom(){
   var dom = null;
   try{
      // dom = new ActiveXObject('Msxml2.DOMDocument.4.0');
	   dom = new ActiveXObject('Microsoft.XMLDOM');
   }catch(x)
   {}
  
   if(dom == null){ 
	  return null;
   }
   dom.setProperty("SelectionLanguage", "XPath");
   return dom;
}

var objXMLHTTP;

function XmlRPC(p_connection,p_method,asy) {
   this.baseUrl = "";
   this.connection = p_connection?p_connection:"err.jsp";
   this.method = p_method?p_method:"POST";
   this.asy = asy?asy:false;
   this.sendXml = f_sendXml;
   this.sendText = f_sendText;
   this.send = f_send;
   this.getXml = f_getXml;
   this.getText = f_getText;
   this.abort = f_abort;
}

function f_sendXml(dom){
   
   this.send(dom);
}


function f_sendText(text){  
   this.send(text);
}



function f_send(arg){
 
    var content = arg?arg:'';
    objXMLHTTP = new ActiveXObject("Microsoft.XMLHTTP");
    objXMLHTTP.Open(this.method, this.connection,this.asy);
    objXMLHTTP.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
   // objXMLHTTP.setRequestheader("content-length",content.length); 
    objXMLHTTP.setRequestHeader("charset","utf-8");   
    //objXMLHTTP.setRequestHeader("charset","ISO8859-1");   
    
    objXMLHTTP.Send(content); 
}

function f_getXml(){
    if(objXMLHTTP.status == '200'){
       var dom = f_createDom();
       dom.load(objXMLHTTP.responseXML);

       //objXMLHTTP = null;
       dom.setProperty("SelectionLanguage", "XPath");
       

      if(dom.xml != ''){
          var error = dom.documentElement.selectSingleNode('//Error');
      
          if( error != null){
            f_showError(dom);
            return null;
          }
       } 
//       alert(dom.xml);
       return dom;
    }
    else{
       alert("ERR" + objXMLHTTP.status );
       
       return null;
       //throw new Error('error');
    }
}

function f_showError(dom){
	if(dom.selectSingleNode('//Error').xml.split("#$")[1]!=null)
	{
		alert(dom.selectSingleNode('//Error').xml.split("#$")[1]);
		return ;
	}
  else   if(dom.selectSingleNode('//Detail') != null){
       var modal = window.showModalDialog("/coManager/common/showError.jsp",dom,"dialogWidth:400px; dialogHeight:400px; center:yes;help:no;resizable:yes");
    } else {
       var modal = window.showModalDialog("/coManager/common/showError.jsp",dom,"dialogWidth:400px; dialogHeight:150px; center:yes;help:no;resizable:yes");
    }
}


function f_abort(){
    objXMLHTTP.abort();
}


function f_getText(){
       var dom = f_createDom();
       
       try{
         dom.load(objXMLHTTP.responseXML);

       //objXMLHTTP = null;
         dom.setProperty("SelectionLanguage", "XPath");

         if(dom.xml != ''){
            var error = dom.documentElement.selectSingleNode('//Error');
      
            if( error != null){
              f_showError(dom);
              return null;
            }
         } 
       }
       catch(e){
       }
       return objXMLHTTP.responseText;
}

