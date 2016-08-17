// Common.js

// newFunction
function ProdSpec(){
   var index;
   var prodInstanceId;
   var prodInstanceName;
   var tagProdSpecId;
   var prodSpecId;
   var prodNumber ;
   var areaId;
   var orderType;
   var custId;
   var custName;
   var coId;
   var isComp;
   var priority;
   var flag;    //标识此业务是否需要输入联系人,联系电话等信息
   var prodStatusCd;   //产品状态ID
   var actionType;
   var actionTypeOld;//订单修改时需要使用原有的动作类型故增加
   var offeringId;
   var coTypeName;
   var categoryNodeId;
   var actionId;   //组合产品用1:新装2:退出虚拟网3:变更角色
   var lockDom;
   var oldOrderType; //订单修改时需要显示原有的订单类型鼓增加
   
   //未竣工订单用
   //var coId,  共用
   var coNbr;
   var state;
   //var prospecId, 共用
   //var custId,  共用
   //var areaId,  共用
   var acessNumber;
   var offferingName;
   //var orderType;  共用
   var orderTypeName;
   var reasonCd;  //退单时，每一张订单的原因cd，以|隔开
}

//成员产品纳入对象
function CompProd(){   
   var accessNum;
   var memberTypeId;  //1.新装,2.已有产品,3.已有订单
   var roleName;
   var memberId;
   var prodId;
   var prodSpecId;
   var prodName;
   var prodSpecName;
   var offeringName;
   var offeringId;
   var newOfferingName;
   var newOfferingId;
   var num;
   var partyId;
   var partyName;
   var roleId;
   var actionId;    //1.纳入,2.退出,3.变更
   var orderType;  //订单类型ID
   var orderTypeId;  //订单类型ID
   var orderTypeName;
   var areaId;
   var priority;
   var flag;    //标识此业务是否需要输入联系人,联系电话等信息
   var actionTypeCd;   //动作类型
   var isAdd;//是否新装,Y:新装纳入，N：老成员，O：老产品纳入
}

//数据转换对象
function DataTransferObj(){
    var preCoId;
    var prodSpecId;
    var prodName;
    var prodSpecName;
    var offeringName;
    var roleId;
    var roleName;
    var accessNum;
    var memberId;
    var memberTypeName;
    var actionId;
    var memberTypeId;
    var orderType;
    var orderTypeId;
    var orderTypeName;
    var actionTypeCd;   //动作类型
    var partyId;
    var partyName;
    var areaId;
    var priority;
    var flag;    //标识此业务是否需要输入联系人,联系电话等信息
    var isAdd;//是否新装,Y:新装纳入，N：老成员，O：老产品纳入
    var offeringId;
    var newOfferingName;
    var newOfferingId;
}

//ADSL加装电话或批量选号时,当前所有订单的参数信息对象
function ExistOrderData(){
    var prodSpecId;   
    var areaId;     //查询在用产品时的入参
    var areaName;   //查询在用产品时的入参
    var prodSpecName;
    var accessNbr;   //接入号码
    var anId;   //主接入号码ID
    var tagProdSpecId;   //目标产品规格ID
    var prodId;      //产品ID(虚拟coId)
    var addrId;
    var addrDetail;
    var tmlId;    //局向ID
    var anTypeCd;  //选号类型
    var channelId;  //渠道ID
    var index;  //当前号码序列号
}

function custMessage(){
        //other
	var partyId; //0
	var partyName;//1
	var defaultIdentityId;//2
	var defaultIdentityNumber;//3
	var defaultIdentityName;//12
	var radioSingelId;//22
	var areaId ;//6
	var areaName;//7
	var addressStr;//8
	var mailAddressStr;//9
	var simpleSpell;//10
	var partyTypeName;//11
	var partyTypeSelectIndex;//16
	var partyTypeCd;//25
	var partyStatusCd;//26
	var partyStatusSelectIndex;//27
	var segmentSelectIndex;//17
	var segmentName;//13
	var segmentId;//28
	var mailAddressId;//14
	var addressId ;//15
	var industryTypeId;//18
	var industryTypeName;//19
	var professionCd;//20
	var professionName;//21
	var passwordSearch;//23
	var passwordBussiness;//24
	var profileArray = new Array();	
	var identityArray = new Array();
}

function profile(){
	var textValue;//提交用
	var CatgCdScript;//提交用
	var id;//控件idhidden  text 用
	var profileName;
}

function identity(){
	var identityCd;//id
	var identityNumber;//value
	var identityName;//name
}