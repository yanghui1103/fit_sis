// JsDebug.js

// newFunction
function Log() {
   /*debug,error,normal*/
   //this.level ;
   //this.render;
}
Log.level = "error";

Log.debug = function f_debug(message,lbl){

   if(this.getLevelCode() < 2) {
       var mess = '';
   
       var mess = mess + "页面路径：" + document.URL + "\n\n";
   
       if(this.debug.caller != null){
           mess = mess + "调用函数：" + this.debug.caller + "\n\n";
       }
       mess = mess + "调试信息：" + message;
       if(lbl == null){
          alert(mess);
       }
       else{
          lbl.innerText = mess;
       }
       
   }
}

Log.error = function f_error(message,lbl){
   
   if(this.getLevelCode() <= 2) {
       var mess = '';
   
       var mess = mess + "页面路径：" + document.URL + "\n\n";
   
       if(this.error.caller != null){
           //mess = mess + "调用函数：" + this.error.caller + "\n\n";
       }
       mess = mess + "错误信息：" + message;
       if(lbl == null){
          alert(mess);
       }
       else{
          lbl.innerText = mess;
       }
       
   }

}

Log.getLevelCode = function f_getLevelCode(){
   switch(Log.level){
      case 'debug':
          return 1;
      case 'error':
          return 2;
   }
}

