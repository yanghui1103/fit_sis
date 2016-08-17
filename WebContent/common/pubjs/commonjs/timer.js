// timer.js
// author: hongh
// dagte : 2005-02-03

function Timer(hour,minute,up,down) {
  this.hour = hour;
  this.minute = minute;
  this.upButton = up;
  this.downButton = down;
  this.hour.onkeydown = hourKeydown;
  this.minute.onkeydown = minuteKeydown;
  this.upButton.onmousedown = toUp;
  this.upButton.onmouseup = setActive;
  this.downButton.onmousedown = toDown;
  this.downButton.onmouseup = setActive;
  this.activeElement;
}

//增加事件
function toUp(){
  this.activeElement = document.activeElement;
  var txt = parseInt(this.activeElement.value) + 1

  if(this.activeElement.onkeydown == minuteKeydown) {
      
      if(validateMinute(txt))
          this.activeElement.value = parseInt(this.activeElement.value) + 1;
  }
  if(this.activeElement.onkeydown == hourKeydown) {
      if(validateHour(txt)) {
         this.activeElement.value = parseInt(this.activeElement.value) + 1;
      }
  }
}

//减少事件
function toDown() {
   this.activeElement = document.activeElement;

  var txt = parseInt(this.activeElement.value) - 1
  if(this.activeElement.onkeydown == minuteKeydown) {
      if(validateMinute(txt))
          this.activeElement.value = parseInt(this.activeElement.value) - 1;
  }
  if(this.activeElement.onkeydown == hourKeydown) {
      if(validateHour(txt)) {
         this.activeElement.value = parseInt(this.activeElement.value) - 1;
      }
  }
}

//设置激活状态
function setActive(){

  this.activeElement.setActive();
  this.activeElement.select();
}

//小时输入控制
function hourKeydown() {
   var txt = event.srcElement;
   var myhour = txt.value + String.fromCharCode(event.keyCode);
   
   if(event.keyCode == 32) {
      event.returnValue = false;
      return;
   }
   
   if(event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 39)  return;

   if(validateHour(myhour)) return;
   event.returnValue = false;
}

//验证小时合法性
function validateHour(myhour) {
   
   if(isNaN(myhour)) return false;
   if(parseInt(myhour) <0 || parseInt(myhour) > 23) return false;
   return true;
}

//判断是否为数值键
function isNumber(code){
   if(code > 47 && code < 58) return true;
   return false;
}

//分钟输入控制
function minuteKeydown() {
   var txt = event.srcElement;
   var myminute = txt.value + String.fromCharCode(event.keyCode);
   if(event.keyCode == 32) {
      event.returnValue = false;
      return;
   }
   
   if(event.keyCode == 8 || event.keyCode == 37 || event.keyCode == 39) return;
   
   if(validateMinute(myminute)) return;
   event.returnValue = false;
}

//验证分钟合法性
function validateMinute(myMinute) {
   
   if(isNaN(myMinute)) return false;
   if(parseInt(myMinute) <0 || parseInt(myMinute) > 59) return false;
   return true;
}

