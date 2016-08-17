
var dialog;
function popup_dialog(title, url, width, height) {
	var html = "<iframe id=\"html_content\" src=\"" + url + "\" width=\"" + width + "\" height=\"" + height + "\" frameborder=\"0\" scrolling=\"auto\"></iframe>";
	dialog = new Ext.Window({title:title, width:width + 26, height:height + 44, minWidth:300, minHeight:200, layout:"fit", plain:true, bodyStyle:"padding:5px;", modal:true, html:html});
	dialog.on("resize", function (dialog, width, height) {
		var obj = document.getElementById("html_content");
		obj.width = width - 26;
		obj.height = height - 44;
	});
	dialog.show();
	if (height === 0) {
		var content = document.getElementById("html_content");
		if (!content.addEventListener) {
			content.addEventListener = function (type, listener, useCapture) {
				if (type.substring(0, 2) != "on") {
					type = "on" + type;
				}
				content.attachEvent(type, listener);
			};
			content.removeEventListener = function (type, listener, useCapture) {
				if (type.substring(0, 2) != "on") {
					type = "on" + type;
				}
				content.detachEvent(type, listener);
			};
		}
		content.addEventListener("load", function (e) {
			dialog.setSize(dialog.getSize().width, content.contentWindow.document.body.scrollHeight + 44);
			dialog.center();
		}, false);
	}
}
function close_dialog() {
	dialog.close();
}

