// Decroate.js

// 为控件添加样式
function Decrator(control){
   this.control = control;
}
//设置控件的class值
Decrator.prototype.attachClass = function f_attachClass(tclass){
    this.control.className = tclass;
}
//设置控件样式
Decrator.prototype.attachStyle = function f_attachStyle(style){
    this.control.style.cssText = style.style.cssText;
}

//样式类
function Style(control){
    this.control = control;
    this.style = this.control.style;
}
Style.prototype.fontSize = function f_fontSize(fontSize){
    this.style.fontSize = fontSize;
    return this;
}
Style.prototype.font = function f_font(font){
    this.style.font = font;
    return this;
}
Style.prototype.display = function f_display(display){
    this.style.display = display;
    return this;
}
Style.prototype.cursor = function f_cursor(cursor){
    this.style.cursor = cursor;
    return this;
}
Style.prototype.border = function f_border(border){
    this.style.border = border;
    return this;
}
Style.prototype.background = function f_background(background){
    this.style.background = background;
    return this;
}

Style.prototype.backgroundColor  = function f_backgroundColor (backgroundColor ){
    this.style.backgroundColor = backgroundColor ;
    return this;
}


