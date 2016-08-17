// XmlRPC.js
function f_createDom() {
	return new ActiveXObject('Microsoft.XMLDOM');
}

var objXMLHTTP;
function XmlRPC(p_connection, p_method, asy) {
	this.baseUrl = "";
	this.connection = p_connection ? p_connection : "err.jsp";
	this.method = p_method ? p_method : "POST";
	this.asy = asy ? asy : false;
	this.sendXml = f_sendXml;
	this.sendText = f_sendText;
	this.send = f_send;
	this.getXml = f_getXml;
	this.getText = f_getText;
	this.abort = f_abort;
}

function f_sendXml(dom) {

	this.send(dom);
}

function f_sendText(text) {
	this.send(text);
}

function f_send(arg) {

	var content = arg ? arg : '';

	objXMLHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	objXMLHTTP.Open(this.method, this.connection, this.asy);
	objXMLHTTP.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	// objXMLHTTP.setRequestheader("content-length",content.length);
	objXMLHTTP.setRequestHeader("charset", "UTF-8");
	// objXMLHTTP.setRequestHeader("charset","ISO8859-1");

	objXMLHTTP.Send(content);
}
function f_getXml() {
	if (objXMLHTTP.status == '200') {
		var dom = f_createDom();
		dom.load(objXMLHTTP.responseXML);

		// objXMLHTTP = null;
		dom.setProperty("SelectionLanguage", "XPath");

		if (dom.xml != '') {
			var error = dom.documentElement.selectSingleNode('//Error');

			if (error != null) {
				alert(error.text);
				return null;
			}
		}
		// alert(dom.xml);
		return dom;
	} else {
		// alert("err" + objXMLHTTP.status );
		alert("系统切换，请重新连接...");
		window.location.reload();
		return null;
	}
}
function f_abort() {
	objXMLHTTP.abort();
}
function f_getText() {
	return objXMLHTTP.responseText;
}
