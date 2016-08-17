// JavaScript Document
var marqueeContent=new Array();
marqueeContent[0]='<a href="#" target="_blank" class="lan12pt">减税才是王道</a>　<a href="#" target="_blank" class="lan12pt">小悦悦事件</a>　<a href="#" target="_blank" class="lan12pt">卡扎菲之死</a>';
marqueeContent[1]='<a href="#" target="_blank" class="lan12pt">小伊伊事件</a>　<a href="#" target="_blank" class="lan12pt">星际2比赛录像包</a>　<a href="#" target="_blank" class="lan12pt"><倩女幽魂></a>';
marqueeContent[2]='<a href="#" target="_blank" class="lan12pt">售楼处遭围攻</a>　<a href="#" target="_blank" class="lan12pt">豪车村长论坛</a>　<a href="#" target="_blank" class="lan12pt">老翁寻快感</a>';

var marqueeInterval=new Array();
var marqueeId=0;
var marqueeDelay=4000;
var marqueeHeight=25;
function initMarquee() {
	var str=marqueeContent[0];
	document.write('<div id="marqueeBox" class="hot_key" onmouseover="clearInterval(marqueeInterval[0])" onmouseout="marqueeInterval[0]=setInterval(\'startMarquee()\',marqueeDelay)"><div>' + str + '</div></div>');
	marqueeId++;
	marqueeInterval[0]=setInterval("startMarquee()",marqueeDelay);
}

function startMarquee() {
	var str=marqueeContent[marqueeId];
	marqueeId++;
	if(marqueeId>=marqueeContent.length) marqueeId=0;
	if(marqueeBox.childNodes.length==1) {
		var nextLine=document.createElement('DIV');
		nextLine.innerHTML=str;
		marqueeBox.appendChild(nextLine);
	}
	else {
		marqueeBox.childNodes[0].innerHTML=str;
		marqueeBox.appendChild(marqueeBox.childNodes[0]);
		marqueeBox.scrollTop=0;
	}
	clearInterval(marqueeInterval[1]);
	marqueeInterval[1]=setInterval("scrollMarquee()",10);
}
function scrollMarquee() {
	marqueeBox.scrollTop++;
	if(marqueeBox.scrollTop%marqueeHeight==marqueeHeight) {
		clearInterval(marqueeInterval[1]);
	}
}
initMarquee();