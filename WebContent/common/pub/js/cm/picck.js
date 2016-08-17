// JavaScript Document
function ImgAuto(i){//你必须给图片提前设定初始宽度和高度，建议直接就是最大宽度和高度
var MaxW=150; //定义图片显示的最大宽度
var MaxH=80; //定义图片显示的最大高度
var o=new Image();o.src=i.src;var w=o.width;var h=o.height;var t;if (w>MaxW){t=MaxW;}else{t=w;}
if ((h*t/w)>MaxH){i.height=MaxH;i.width=MaxH/h*w;}else{i.width=t;i.height=t/w*h;}
}
var ImgObj=new Image();   //建立一个图像对象
var AllImgExt=".jpg|.jpeg|.gif|.bmp|.png|"; //全部图片格式类型
var FileObj,ImgFileSize,ImgWidth,ImgHeight,FileExt,ErrMsg,FileMsg,HasCheked,IsImg; //全局变量 图片相关属性
//以下为限制变量
var AllowExt=".jpg|.gif|.png|"; //允许上传的文件类型 .为无限制 每个扩展名后边要加一个"|" 小写字母表示
//var AllowExt=0
var AllowImgFileSize=1000;  //允许上传图片文件的大小 0为无限制  单位：KB 
var AllowImgWidth=400;   //允许上传的图片的宽度 为无限制　单位：px(像素)
var AllowImgHeight=300;   //允许上传的图片的高度 为无限制　单位：px(像素)
HasChecked=false;
function CheckProperty(obj)  //检测图像属性
{
  FileObj=obj;
  if(ErrMsg!="")   //检测是否为正确的图像文件　返回出错信息并重置
  {
    ShowMsg(ErrMsg,false);
    return false;   //返回
  }
  if(ImgObj.readyState!="complete") //如果图像是未加载完成进行循环检测
  {
    setTimeout("CheckProperty(FileObj)",500);
    return false;
  }
  ImgFileSize=Math.round(ImgObj.fileSize/1024*100)/100;//取得图片文件的大小
  ImgWidth=ImgObj.width;   //取得图片的宽度
  ImgHeight=ImgObj.height;  //取得图片的高度
  FileMsg="\n图片大小:"+ImgWidth+"*"+ImgHeight+"px";
  FileMsg=FileMsg+"\n图片文件大小:"+ImgFileSize+"Kb";
  FileMsg=FileMsg+"\n图片文件扩展名:"+FileExt;
  if(AllowImgWidth!=0&&AllowImgWidth<ImgWidth)
    ErrMsg=ErrMsg+"\n图片宽度超过限制。请上传宽度小于"+AllowImgWidth+"px的文件，当前图片宽度为"+ImgWidth+"px";

  if(AllowImgHeight!=0&&AllowImgHeight<ImgHeight)
    ErrMsg=ErrMsg+"\n图片高度超过限制。请上传高度小于"+AllowImgHeight+"px的文件，当前图片高度为"+ImgHeight+"px";

  if(AllowImgFileSize!=0&&AllowImgFileSize<ImgFileSize)
    ErrMsg=ErrMsg+"\n图片文件大小超过限制。请上传小于"+AllowImgFileSize+"KB的文件，当前文件大小为"+ImgFileSize+"KB";

  if(ErrMsg!="")
    ShowMsg(ErrMsg,false);
  else
    ShowMsg(FileMsg,true);
}
ImgObj.onerror=function(){ErrMsg='\n图片格式不正确或者图片已损坏!';};

function ShowMsg(msg,tf) //显示提示信息 tf=true 显示文件信息 tf=false 显示错误信息 msg-信息内容
{
  msg=msg.replace("\n","<li>");
  msg=msg.replace(/\n/gi,"<li>");
  if(!tf)
  {

    FileObj.outerHTML=FileObj.outerHTML;
    document.getElementById("preview").innerHTML=msg;
    HasChecked=false;
  }
  else
  {
    if(IsImg)

       document.getElementById("preview").innerHTML="<img src='"+ImgObj.src+"' onload='ImgAuto(this)' />";
    else

     // document.getElementById("preview").innerHTML="非图片文件";
     document.getElementById("preview").innerHTML=msg;
    HasChecked=true;
  }
}
function CheckExt(obj)
{
  ErrMsg="";
  FileMsg="";
  FileObj=obj;
  IsImg=false;
  HasChecked=false;

   document.getElementById("preview").innerHTML="";
  if(obj.value=="")return false;
  
  FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
  if(AllowExt!=0&&AllowExt.indexOf(FileExt+"|")==-1) //判断文件类型是否允许上传
  {
    ErrMsg="\n该文件类型不允许上传。请上传 "+AllowExt+" 类型的文件，当前文件类型为"+FileExt;
    ShowMsg(ErrMsg,false);
    return false;
  }
  if(AllImgExt.indexOf(FileExt+"|")!=-1)  //如果图片文件，则进行图片信息处理
  {
    IsImg=true;
    ImgObj.src=obj.value;
    CheckProperty(obj);
    return false;
  }
  else
  {
    FileMsg="\n文件扩展名:"+FileExt;
    ShowMsg(FileMsg,true);
  }
}