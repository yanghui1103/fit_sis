// JsDebug.js

// newFunction
function JsDebug(level) {
   /*debug,error,normal*/
   this.level = level;
}

JsDebug.debug = function f_debug(message,lbl){

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

JsDebug.error = function f_error(message,lbl){
   
   if(this.getLevelCode() <= 2) {
       var mess = '';
   
       var mess = mess + "页面路径：" + document.URL + "\n\n";
   
       if(this.error.caller != null){
           mess = mess + "调用函数：" + this.error.caller + "\n\n";
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

JsDebug.getLevelCode = function f_getLevelCode(){
   switch(JsDebug.level){
      case 'debug':
          return 1;
      case 'error':
          return 2;
   }
}


JsDebug.prototype.debug = function f_debug(message,lbl){

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

JsDebug.prototype.error = function f_error(message,lbl){
   
   if(this.getLevelCode() <= 2) {
       var mess = '';
   
       var mess = mess + "页面路径：" + document.URL + "\n\n";
   
       if(this.error.caller != null){
           mess = mess + "调用函数：" + this.error.caller + "\n\n";
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

JsDebug.prototype.getLevelCode = function f_getLevelCode(){
   switch(this.level){
      case 'debug':
          return 1;
      case 'error':
          return 2;
   }
}
