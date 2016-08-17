//定义定单类型变量
var ORDER_TYPE_NEW 				= "1";  	//新装
var ORDER_TYPE_CHANGENUM 		= '2'; 		//改号
var ORDER_TYPE_REMOVE    		= '3'; 		//拆机
var ORDER_TYPE_CANCEL			= '4'; 		//撤单
var ORDER_TYPE_CHANGECUST		= '5'; 		//修改客户信息
var ORDER_TYPE_CHANGEACCT		= '6'; 		//修改帐户信息
var ORDER_TYPE_CHANGENWK		= '7'; 		//修改程控信息
var ORDER_TYPE_JOINCENTREX		= '8'; 		//入虚拟网
var ORDER_TYPE_QUITCENTREX		= '9'; 		//退虚拟网
var ORDER_TYPE_CREATECENTREX	= '10';		//建虚拟网
var ORDER_TYPE_STEPOTHERCUST	= '11';		//过户
var ORDER_TYPE_CHANGEPAYRELA	= '12';		//修改付费关系
var ORDER_TYPE_CHANGEPAYMETHOD	= '13';		//修改支付方式
var ORDER_TYPE_PATCHPHS			= '14';		//小灵通补机
var ORDER_TYPE_CHANGEPHS		= '15';		//小灵通局方换机
var ORDER_TYPE_CHANGENAME		= '16';		//改名
var ORDER_TYPE_CHANGEFEE		= '17';		//资费变更
var ORDER_TYPE_CHANGEPASSWORD	= '18';		//改客户密码、产品密码
var ORDER_TYPE_STOPOBLIGATENUM	= '19';		//用户申请停机保号
var ORDER_TYPE_OPENNUM			= '20';		//用户申请复机
var ORDER_TYPE_OPENPROMPT		= '21';		//服务通知音
var ORDER_MODIFY				= '63';     //订单修改
var ORDER_STOP					= '46';		//暂停订单
var ORDER_CONTINUE				= '47';		//恢复订单

//数据类型
var DATA_TYPE_STRING			= '1';		//字符型
var DATA_TYPE_BOOLEAN			= '2';		//布尔型
var DATA_TYPE_INT				= '3';		//整型
var DATA_TYPE_DATE				= '4';		//日期型
var DATA_TYPE_FLOAT				= '5';		//浮点型
var DATA_TYPE_PHONE				= '6';		//电话号码
var DATA_TYPE_EMAIL				= '7';		//电子邮箱
var DATA_TYPE_PASSWORD			= '8';		//密码

/*销售品目录类型*/

/*固定电话类销售品*/
var PUBLIC_PHONE                = '10';     //公用电话
var TRUNK_LINE                  = '11';     //中继
var SPECIAL_TELE_LINE           = '12';     //模拟专线
var ORDINARY_PHONE              = '15';     //普通电话
/*无线市话类销售品*/
var WIRELESS_PHONE              = '14';     //小灵通

//程控功能关系
var NWK_RELA_LEAN				= 2;      //依赖
var NWK_RELA_MUTEX				= 1;      //互斥