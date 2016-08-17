
bss = {
    'version': '0.1',
    'navigator':navigator.userAgent.toLowerCase(),

    getBody: function(){
        return document.body || document.documentElement
    },

    emptyFn: function(){
    },

    create: function(tag){
        return document.createElement(tag);
    },

    apply: function(o, c, override){
        if (override == null) 
            override = true;
		else
			override = eval(override);
        if (o && c && typeof c == 'object') {
            for (var p in c) {
                if (override) 
                    o[p] = c[p];
                else 
                    if (o[p] == null) {
                        o[p] = c[p];
                    }
            }
        }
        return o;
    },

	findAllInput:function(type){
		var inputs = document.all.tags('INPUT');
		var inputArr = new Array();
		var index = 0;
		if(type==null || type == '')
			return inputs;
		else
			type = type.toLowerCase();
		for(var i=0,len=inputs.length;i<len;i++){
			var input = inputs[i];
			if(input.type.toLowerCase() == type)
				inputArr[index++] = inputs[i];
		}
		return inputArr;
	},

    findObj: function(map, _key){
        if (_key == null) {
            _key = map;
            return document.getElementById(_key);
        }
        for (var key in map) {
            key = key.toLowerCase();
            if (_key == key) 
                return map[_key];
        }
        return null;
    },

    getMsDom: function(selectionLanguage){
		{
			var dom = null;
			try {
				dom = new ActiveXObject('Microsoft.XMLDOM');
			} 
			catch (e) {
				dom = new ActiveXObject('Msxml2.DOMDocument.5.0');
			}
			if (dom == null) {
				alert('err');
				return null;
			}
			dom.setProperty("SelectionLanguage", selectionLanguage ? selectionLanguage : "XPath");
			return dom;
		}
    },
	extend:function(child,base){ 
		child.prototype = eval('new '+ base +'()');
		bss.apply(child.prototype,eval('new ' + child +'()'));
		child.prototype.constructor = base;
		child.prototype.supperclass = base;
	}, 
	isIE7:function(){
		return this.navigator.indexOf("msie 7") > -1;
	}
};


$ = bss.findObj;

bss.util = {};

bss.util.array = {

	contains:function(obj){
		var len = this.length;
		for(var i=0;i<len;i++){
			if(this[i] == obj)
				return true;
		};
		return false;
	}
};
bss.apply(Array.prototype,bss.util.array);

bss.util.html = {
    hide: function(obj){
		{
			if (obj) {
				obj.style.display = 'none';
			}
		}
    },

    show: function(obj){
		{
			if (obj) 
				obj.style.display = 'block';
		}
    },

	toggleDisplay:function(obj){
		if(obj){
			if(obj.style.display == 'none'){
				this.show(obj);
			}else{
				this.hide(obj);
			}
		}
	},
	disable:function(obj){
		if (obj) {
			obj.disabled  = true;
		}
	},
	enable:function(obj){
		if (obj) {
			obj.disabled  = false;
		}	
	},
	toggleDisable:function(obj){
		if(obj){
			if(obj.style.disabled){
				this.enable(obj);
			}else{
				this.disable(obj);
			}
		}		
	},
	underline:function(obj,ifTrue){
		if(ifTrue==null || ifTrue =='undefined')
			ifTrue = true;
		if(obj){
			obj.style.textDecorationUnderline = eval(ifTrue);
		}
	},
	lineThrough:function(obj,ifTrue){
		if(ifTrue==null || ifTrue =='undefined')
			ifTrue = true;
		if(obj){
			obj.style.textDecorationLineThrough = eval(ifTrue);
		}
	}
};
/**
 * ʱ���ദ��
 * �˾�̬�����󶨵� Date������Ӻ�����ע�⴦�� this����
 */
bss.util.date = {
	formats: {
		'SHORTDATE': 'y-m-d',
		'FULLDATE':'Y-m-d',
		'FULLDATETIME':'Y-m-d H:i:s',
		'TIME':'H:i:s'
	},
	daysInMonth : [31,28,31,30,31,30,31,31,30,31,30,31],
	/**
	 * ��ȡ������ʱ��
	 */
	getServerTime:function(direct){
		var d = new Date();
		if(direct || d == null){
			//���ط�����ʱ��,���·�����ʱ���
		}
		return d;
	},
	/**
	 * ȡ���·��������
	 * @param {Object} month
	 */
	getMaxDaysInMonth:function(month){
		var d = this.daysInMonth[month];
		return d!=null?d:-1;
	},
	/**
	 * �Ƿ�������
	 */
	isLeapYear:function(checkYear){
		if(checkYear==null || checkYear==""){
			checkYear = this.getYear();
		}
		if (!bss.util.validation.isNumber(checkYear)){
			alert("�������ݸ�ʽ�Ƿ�");
			return false;
		}
		return (0==checkYear%4&&((checkYear%100!=0)||(checkYear%400==0)));
	},
	/**
	 * ��ʽ��ʱ���ַ����ַ�ת��Ϊʱ�����
	 * ShortDate:'08-12-01'
	 * FULLDATE:'2008-12-01'
	 * FULLDATETIME:'2008-12-01 16:05:01'
	 * TIME:'16:05:01'
	 * @param {Object} format
	 */
	parseDate:function(str,formatType){
		if(formatType == null){
			for(var key in this.formats){
				var d = this.parseDate(str,key);
				if(d!=null)
					return d;
			}
		}
		var format = this.getFormat(formatType);
		var tmpDate = new Date();
		var y=tmpDate.getFullYear(),m=tmpDate.getMonth(),d=tmpDate.getDate(),h=0,i=0,s=0;
		try{
			if(format == this.formats['SHORTDATE']){
				//��ʽΪ 08-12-23
				if(!/^\d{2}-\d{2}-\d{2}$/.test(str)){
					return null;
				}
				y = parseInt(str.substring(0,2),10);
				m = parseInt(str.substring(3,5),10);
				d = parseInt(str.substring(6,8),10);
			}else if(format == this.formats['FULLDATE']){
				//��ʽΪ 2008-12-23
				if(!/^\d{4}-\d{2}-\d{2}$/.test(str)){
					return null;
				}
				y = parseInt(str.substring(0,4),10);
				m = parseInt(str.substring(5,7),10);
				d = parseInt(str.substring(8,10),10);
			}else if(format == this.formats['FULLDATETIME']){
				//��ʽΪ 2008-12-23 23:21:59
				if(!/^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}$/.test(str)){
					return null;
				}
				y = parseInt(str.substring(0,4),10);
				m = parseInt(str.substring(5,7),10);
				d = parseInt(str.substring(8,10),10);
				h = parseInt(str.substring(11,13),10);
				i = parseInt(str.substring(14,16),10);
				s = parseInt(str.substring(17,19),10);
			}else if(format == this.formats['TIME']){
				//��ʽΪ 23:21:59
				if(!/^\d{2}:\d{2}:\d{2}$/.test(str)){
					return null;
				}
				h = parseInt(str.substring(0,2),10);
				i = parseInt(str.substring(3,5),10);
				s = parseInt(str.substring(6,8),10);				
			}else{
				return null;
			}
		}catch(e){
			return null;
		}
		return new Date(y,m,d,h,i,s);
	},
	/**
	 * ��ʽ�����ʱ���ַ���ʱ�����ת��Ϊ�ַ�
	 * ShortDate:'08-12-01'
	 * FULLDATE:'2008-12-01'
	 * FULLDATETIME:'2008-12-01 16:05:01'
	 * TIME:'16:05:01'
	 * @param {Object} format
	 */
	dateFormat:function(formatType,format){
		if(formatType == null)
			formatType = 'FULLDATETIME';
		if(typeof formatType=='string' && format == null){
			format = this.getFormat(formatType);
		}
		var str = format;
		str = str.replace(/Y/g,this.getFullYear());
		str = str.replace(/y/g,this.getFullYear().toString().substring(2,4));
		str = str.replace(/m/g,bss.util.string.leftPad(this.getMonth() + 1, 2, '0'));
		str = str.replace(/d/g,bss.util.string.leftPad(this.getDate(), 2, '0'));
		str = str.replace(/h/g,bss.util.string.leftPad((this.getHours() % 12) ? this.getHours() % 12 : 12, 2, '0'));//12Сʱ��
		str = str.replace(/H/g,bss.util.string.leftPad(this.getHours(), 2, '0'));//24Сʱ��
		str = str.replace(/i/g,bss.util.string.leftPad(this.getMinutes(), 2, '0'));
		str = str.replace(/s/g,bss.util.string.leftPad(this.getSeconds(), 2, '0'));
		return str;
	},
	/**
	 * ��ȡʱ���ʽ,��Ϊ����ΪFULLDATETIME
	 * @param {Object} type
	 */
	getFormat:function(type){
		if(type == null || type == 'undefined')
			type = '';
		return this.formats[type]!=null?this.formats[type]:this.formats['FULLDATETIME'];
	},
	/**
	 * ʱ��Ƚ�
	 * ���һ����ڵڶ���� >0
	 * @param {Object} dt1
	 * @param {Object} dt2
	 */
	compare:function(dt1,dt2){
		//��ֻ����һ�����this���ɵ�һ�����
		if (dt2 == null) {
			dt2 = dt1;
			dt1 = this;
		}
		return dt1.getTime()-dt2.getTime();
	},
	/**
	 * �Ƿ��ǽ���
	 */
	isToday:function(){
		var d = new Date();
		return this.getFullYear() == d.getFullYear() && this.getMonth() == d.getMonth() && this.getDate() == d.getDate() ;
	}
};
bss.apply(Date.prototype,bss.util.date);
/**
 * �ַ�
 * �˾�̬�����󶨵� String������Ӻ�����ע�⴦�� this����
 */
bss.util.string = {
	/**
	 * ȥ���ַ���β�ո�
	 * 
	 * var str = ' text ';
	 * str.trim();
	 * var str1 = ' text ';
	 * str1 = bss.util.String.trim(str1);
	 * 
	 * @param {Object} str
	 */
	trim: function(str){
		{
			var re = /^\s+|\s+$/g;
			
			if (str != null) {
				return str.replace(re, "");
			}
			else {
				//�������򽫵�������Ϊ���ַ����
				return this.replace(re, "");
			}
		}
	},
	/**
	 * ��ʽ���ַ�
	 * var str = '{0}{1},���';
	 * str = str.format('Kunee','����');
	 */
	format:function(){
		{
			var args = Array.prototype.slice.call(arguments, 0);
			return this.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
	/**
	 * ��ʽ���ַ�(��̬����)
	 * var str = '{0}{1},���';
	 * str = bss.util.String.sFormat(str,'Kunee','����');
	 */
	sFormat:function(str){
		{
			var args = Array.prototype.slice.call(arguments, 1);
			return str.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
	},
    /**
     * �ַ�ת��ΪJSON����
     * @param {Object} str
     */
    decodeJson: function(str){
        if (str == null) 
            str = this;
        return eval("(" + str + ")");
    },
	/**
	 * �Ƿ���ĳ�ַ�ʼ
	 * @param {Object} str
	 */
	startsWith:function(str){
		 return this.indexOf(str) == 0;
	},
	/**
	 * �Ƿ���ĳ�ַ����
	 * @param {Object} str
	 */
	endsWith: function(str) {
    	var d = this.length - str.length;
    	return d >= 0 && this.lastIndexOf(str) == d;
	},
	/**
	 * �����ַ�
	 * 123 - > 00123
	 * @param {Object} val
	 * @param {Object} size
	 * @param {Object} ch
	 */
    leftPad : function (val, size, ch) {
        var result = new String(val);
        if(!ch) {
            ch = " ";
        }
        while (result.length < size) {
            result = ch + result;
        }
        return result.toString();
    },
	/**
	 * �����ַ�
	 * 123 - > 12300
	 * @param {Object} val
	 * @param {Object} size
	 * @param {Object} ch
	 */
    rightPad : function (val, size, ch) {
        var result = new String(val);
        if(!ch) {
            ch = " ";
        }
        while (result.length < size) {
            result = result + ch;
        }
        return result.toString();
    },
	/**
	 * ��ʽ������
	 * @param {Object} number ����
	 * @param {Object} nAfterDot С����λ��
	 */
	formatNumber:function(number,nAfterDot){
		if(nAfterDot==null){
			//��ֻ��һ���������Ϊ���ַ����ֱ�ӵ����˴˷���
			nAfterDot = number;
			number = this;
		}
		if(!bss.util.validation.isFloat(number)){
			alert('���ָ�ʽ�Ƿ�');
			return "";
		}
		number = parseFloat(number).toString();
		var index = number.indexOf('.');
		if(index == -1){
			return number + '.' + this.rightPad('',nAfterDot,'0');
		}
		var strAftDot = number.substring(index+1,number.length);
		if(strAftDot.length > nAfterDot){
			strAftDot = strAftDot.substring(0,nAfterDot);
		}else{
			strAftDot = this.rightPad(strAftDot,nAfterDot,'0');
		}
		return number.substring(0,index+1) + strAftDot;
	},

	/**
	 * ��������֣���-�����֣�(Сд)ת��������(��д��
	 * @param {Object} Num ����
	 */
	numToChinese:function(Num){
		if(Num == null)
			Num = this;
	  	var tmpnewchar = "";
		for(i=Num.length-1;i>=0;i--){
			Num = Num.replace(",","")//�滻tomoney()�еġ�,��
			Num = Num.replace(" ","")//�滻tomoney()�еĿո�
		}
	   	Num = Num.replace("��","")//�滻����ܳ��ֵģ��ַ�
	   	if(isNaN(Num)) { //��֤������ַ��Ƿ�Ϊ����
	    		alert("����Сд����Ƿ���ȷ");
	    		return;
	   	}
		//---�ַ�����ϣ���ʼת����ת������ǰ��}���ֱַ�ת��---//
		part = String(Num).split(".");
		var newchar = "";
		//С���ǰ����ת��
		for(var i=part[0].length-1;i>=0;i--){
			if(part[0].length > 10){ 
			   alert("λ�����޷�����");
			   return "";
			}//��������ʰ�ڵ�λ����ʾ
			tmpnewchar = ""
			perchar = part[0].charAt(i);
			switch(perchar){
				case "0": tmpnewchar="��" + tmpnewchar ;break;
				case "1": tmpnewchar="Ҽ" + tmpnewchar ;break;
				case "2": tmpnewchar="��" + tmpnewchar ;break;
				case "3": tmpnewchar="��" + tmpnewchar ;break;
				case "4": tmpnewchar="��" + tmpnewchar ;break;
				case "5": tmpnewchar="��" + tmpnewchar ;break;
				case "6": tmpnewchar="½" + tmpnewchar ;break;
				case "7": tmpnewchar="��" + tmpnewchar ;break;
				case "8": tmpnewchar="��" + tmpnewchar ;break;
				case "9": tmpnewchar="��" + tmpnewchar ;break;
			}
			switch(part[0].length-i-1){
				case 0: tmpnewchar = tmpnewchar +"Ԫ" ;break;
				case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"ʰ";break;
				case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"��";break;
				case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"Ǫ";break;
				case 4: tmpnewchar= tmpnewchar +"��" ;break;
				case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"ʰ";break;
				case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"��";break;
				case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"Ǫ";break;
				case 8: tmpnewchar= tmpnewchar +"��" ;break;
				case 9: tmpnewchar= tmpnewchar +"ʰ" ;break;
			}
			newchar = tmpnewchar + newchar;
		}
		//С���֮�����ת��
		if(Num.indexOf(".")!=-1){
			if(part[1].length > 2) {
				//alert("С���֮��ֻ�ܱ���}λ,ϵͳ���Զ��ض�");
				part[1] = part[1].substr(0,2)
			}
			var tmpCount = "";
			for(var i=0;i<part[1].length;i++){
				perchar = part[1].charAt(i);
				switch(perchar){
					case "0": tmpCount= tmpCount + "��";break;
					case "1": tmpCount= tmpCount + "Ҽ";break;
					case "2": tmpCount= tmpCount + "��";break;
					case "3": tmpCount= tmpCount + "��";break;
					case "4": tmpCount= tmpCount + "��";break;
					case "5": tmpCount= tmpCount + "��";break;
					case "6": tmpCount= tmpCount + "½";break;
					case "7": tmpCount= tmpCount + "��";break;
					case "8": tmpCount= tmpCount + "��";break;
					case "9": tmpCount= tmpCount + "��";break;
				}
				if(i==0){
					tmpCount =tmpCount + "��";
				}
				if(i==1){
					tmpCount = tmpCount + "��";
				}
			}
			newchar = newchar + tmpCount;
		}
		var ifCheck = true;
		var ifReplace = false;
		while (ifCheck){
			ifReplace = false;
			if (newchar.indexOf("����") != -1){
				newchar = newchar.replace("����", "��");
				ifCheck = true;
				ifReplace = true;
			}
			if (newchar.indexOf("����") != -1){
				newchar = newchar.replace("����", "��");
				ifCheck = true;
				ifReplace = true;
			}
			
			if (newchar.indexOf("����") != -1){
				newchar = newchar.replace("����", "��");
				ifCheck = true;
				ifReplace = true;
			}
			
			if (newchar.indexOf("����") != -1){
				newchar = newchar.replace("����", "��");
				ifCheck = true;
				ifReplace = true;
			}
			if (newchar.indexOf("��Ԫ") != -1 && newchar.indexOf("��Ԫ") != 0){
				newchar = newchar.replace("��Ԫ", "Ԫ");
				ifCheck = true;
				ifReplace = true;
			}
			if (newchar.indexOf("���") != -1){
				if (newchar.indexOf("���") == -1 && newchar.indexOf("��") == -1){
					newchar = newchar.replace("���", "");
					ifCheck = true;
					ifReplace = true;
				}
			}
			
			if (newchar.indexOf("���") != -1){
				newchar = newchar.replace("���", "");
				ifCheck = true;
				ifReplace = true;
			}
			if (!ifReplace){
				ifCheck = false;
			}
		}
		if (newchar.charAt(newchar.length-1) == "Ԫ" || newchar.charAt(newchar.length-1) == "��"){
			newchar = newchar+"��";
		}
		return newchar;
	}
};
bss.apply(String.prototype,bss.util.string);

bss.util.validation = {
	/**
	 * �Ƿ�������
	 * @param {Object} value
	 */
	isNumber:function(value){
		for (var i=0; i<value.length; i++)
			if (value.charCodeAt(i) < 0x0030 || value.charCodeAt(i) > 0x0039) return false;
		return true;		
	},
	/**
	 * �Ƿ��Ǹ���
	 * @param {Object} value
	 */
	isFloat:function(value) {
		var fValue = parseFloat(value);
		return (!isNaN(fValue));
	},
	/**
	 * �Ƿ���ʱ��
	 * @param {Object} value
	 * @param {Object} format
	 */
	isDate:function(value){
		var d = null;
		for(var key in bss.util.date.formats){
			d = bss.util.date.parseDate(value,key);
			if(d!=null)
				break;
		}
		return d!=null;
	}

};

bss.ajax = {
    getXmlHttpRequest: function(){
	    var xhr = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();
        return xhr;
    },
	/**
	 * Զ�̻�ȡ
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 */
	post:function(url,data,callback){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'POST',
			'async':callback!=null
		};
		return this.load(parameter,callback);
	},
	/**
	 * Զ�̻�ȡ
	 * @param {Object} url
	 * @param {Object} data
	 * @param {Object} callback
	 */
	get:function(url,data,callback){
		var parameter = {
			'url':url,
			'urlpara':data,
			'p_method':'GET',
			'async':callback!=null
		};
		return this.load(parameter,callback);
	},
	/**
	 * Զ�̼���
	 * @param {Object} paramters ����
	 * @param {Object} callback �ص���
	 */
    load: function(paramters, callback){
		//��������
        paramters = this.getDefaultSetting(paramters);
        
        callback = callback || bss.emptyFn;
        if(paramters.url.indexOf('?')>-1){
        	if(!paramters.url.endsWith('&')){
        		paramters.url = paramters.url + '&';
        	}
        	paramters.url = paramters.url + this.getUrlParamter(paramters['urlpara']);
        }else
	        paramters.url = paramters.url + '?' + this.getUrlParamter(paramters['urlpara']);

        
        var xhr = this.getXmlHttpRequest();
        if (paramters.async) {
			//�첽ģʽ������Ժ�ִ�лص���
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						eval(callback).call(this,true,bss.ajax.getResult(xhr,paramters.dataType));
					}else{
						eval(callback).call(this,false,bss.ajax.getResult(xhr,paramters.dataType));
					}
				}
			}
        }
		xhr.open(paramters.p_method,paramters.url,paramters.async);
		xhr.setRequestHeader("Content-Type",paramters.contentType);
		xhr.setRequestHeader('charset',paramters.charset);
        xhr.send(paramters.data);
		
		if(paramters.async){
			//�첽ģʽ������XMLHTTP���
			return xhr;
		}else{
			//ͬ��ģʽ��ֱ�ӷ���ֵ
			return this.getResult(xhr,paramters.dataType);
		}
    },
	/**
	 * ��ȡ����ֵ
	 * @param {Object} xhr
	 * @param {Object} dataType
	 */
	getResult:function(xhr,dataType){
		var dom = bss.getMsDom();
		dom.load(xhr.responseXML);
		if(dom==null || dom.xml == '')
			return xhr.responseText;
		return dom;
	},
	/**
	 * ��������
	 * @param {Object} xhr
	 */
    abort: function(xhr){
        if (xhr)
            xhr.abort();
    },
	/**
	 * URL����
	 * @param {Object} obj
	 */
    getUrlParamter: function(obj){
        var str = '';
		if (obj != null) {
			for (var key in obj) {
				str += key + '=' + encodeURIComponent(encodeURIComponent(obj[key])) + '&';
			}
		}
        str += 'reconnectKeyCode=' + Math.random();
        return str;
    },
	/**
	 * Ĭ������
	 * @param {Object} paramters
	 */
    getDefaultSetting: function(paramters){
        paramters['url'] = paramters['url'];
        paramters['p_method'] = paramters['p_method'] ? paramters['p_method'] : 'GET';
        paramters['async'] = paramters['async'] ? eval(paramters['async']) : false;
        paramters['charset'] = paramters['charset'] ? paramters['charset'] : 'utf-8';
        paramters['data'] = paramters['data'] ? paramters['data'] : '';
        paramters['contentType'] = paramters['contentType'] ? paramters['contentType'] : "application/x-www-form-urlencoded";
        paramters['dataType'] = paramters['dataType'] ? paramters['dataType'] : 'text';
        return paramters;
    }
};
/**
 * ���Թ���
 */
bss.util.test = {
	/**
	 * д�뱾���ļ�
	 */
	saveLocalFile:function(filename,content,append){
		if(append==null)
			append = true;
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		var tf = fso.CreateTextFile(filename, append,true);
		tf.WriteLine(content);
		tf.Close();
	},
	/**
	 * ��ȡ�����ļ���������ʹ��
	 */
	loadLocalFile:function(file,fileType){
		if(fileType == null){
			fileType = 'xml';
		}
		if (fileType == 'xml') {
			var xmldoc = new ActiveXObject("Msxml2.DOMDocument.5.0");//����MS������ʵ��
			xmldoc.async = false;
			xmldoc.load(file);
			return xmldoc;
		}
		return null;
	}
};

/**
*map��
*/
bss.util.map = function mapClass(){
	this.map= new Array();
	//�趨Ԫ��
	this.setAt= function(key,value){
		for (i=0; i<this.map.length;i++){
			if(this.map[i].key == key){
				this.map[i].value = value;
				return true;
			}
		}
		this.map[this.map.length] = new struct(key,value);
		return true;
	};
	//��ѯԪ��
	this.lookUp= function(key){
		for(var i = 0;i<this.map.length; i++){
			if(this.map[i].key == key){
				return this.map[i].value;
			}
		}
		return null;
	};
	//�Ƴ�Ԫ��
	this.removeKey= function(key){
		for(var i = 0;i<this.map.length;i++){
			if(this.map[i].key == key){
				this.map.splice(i,1);
				return;
			}
		}
		return;
	};
	//�õ�MAP����
	this.getCount= function(){
		return this.map.length;
	};
	//�п�
	this.isEmpty= function(){
		return this.map.length <= 0;
	};
};
function struct(key,value){
	this.key = key;
	this.value = value;
};
/**
 * ���������
 */
bss.appearance = {};
/**
 * XML������
 */
bss.xml = {};

/**
 * ���ܣ�ҵ���ռ䶨��
 * ����
 * ���ߣ�Kunee Hwang
 */
bss.business = {};
bss.business.DomReader = {
	getAttribute:function(node,name){
		return node.getAttribute(name);
	},	
	getType:function(node){
		return this.getAttribute(node,'type');
	},
	getLabel:function(node,label){
		label = label?label:'label';
		return this.getAttribute(node,label);
	},
	getItems:function(node){
		return node.selectNodes('./Item');
	},
	getItemText:function(node){
		return node.selectNodes('Text')[0].text;
	},
	getItemValue:function(node){
		return node.selectNodes('Value')[0].text;
	},
	getNodeItemText:function(node,index){
		if(index==null||index=='undefined')
			index = 0;
		var item = node.selectNodes('./Item')[index];
		if(item==null)
			return '';
		return this.getItemText(node.selectNodes('./Item')[index]);
	},
	getPropertys:function(node){
		return node.selectNodes('./Property');
	}
};
DomReader = bss.business.DomReader;
bss.business.component = {};
bss.business.component.print = {
    version: '1.0',
    description: '��ӡ'
};
bss.business.component.print.receipt = {
    version: '1.0',
    description: '��ִ��ӡ'
};
