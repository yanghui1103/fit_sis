
function byId(id)
{
	return document.getElementById(id);
}
function Init()
{   
	 var obj=byId("CardReader1");
	 obj.setPortNum(0);
	
}
function setPort()
{
	var obj=byId("CardReader1");
	var form1=byId("createForm");
	var iPort=form1.port.value;
	obj.setPortNum(iPort);
}


function readCard()
{
	var cardId;
	var path;
	var imgfile;
	var obj = byId("CardReader1");
	var form1=byId("createForm");
    obj.Flag=1; 
	var rst = obj.ReadCard();
	form1.person_name.value  =obj.NameL(); 
	form1.gender.value    =obj.sex(); 
	cardId=obj.CardNo();
	form1.card_id.value  =cardId;
	form1.birchday.value   =obj.BornL();
	form1.orgin.value  =obj.Police();
	form1.nation.value=obj.NationL(); 

    imgfile=obj.PhotoPath;
    if(path=="")
    {
	    imgfile+="image.bmp";
	}
	else
	{
	    imgfile+=cardId+".bmp";
	} 
	//document.perImg.src=imgfile;
}
