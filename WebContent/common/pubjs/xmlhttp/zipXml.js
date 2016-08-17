 function zipXml(alldom){
     
     var compressDom = f_createDom();
     
     compressDom.appendChild(alldom.documentElement.cloneNode(true));
     
     
     var pages = compressDom.selectNodes("//Pages");
    
     for(var i=0;i<pages.length;i++) {
         clearUnUsedOptions(pages[i]);
     }

     return compressDom;
  }
  
  function clearUnUsedOptions(pages){
    
     clearOrderTypeInfo(pages);
     //clearCoModifyInfo(pages);
     clearSla(pages);
     clearSlaFul(pages);
     cleareProdAttr(pages);
     clearAssureInfo(pages);
     clearNwk(pages);
     clearNwkProps(pages);
     clearNwkRules(pages);
     clearFeeInfo(pages);
     clearFeeInfoAttr(pages);
     clearFeeInfoRules(pages);
     clearFeeInfoRelation(pages);
     clearFeeInfoValidate(pages);
  }
  
  function clearCoModifyInfo(pages){
     try{
        
         var xpath = "./Page[@id='coModifyInfo']/Control[@id='ReturnReason']";
         clean(pages,xpath);
     }catch(x){
     	
     }
  }
  
  function clearSla(pages){
     try{
         
         var xpath = "./Page[@id='baseInfo']/Control[@id='CustOrder.SlaSa']/Item[not(@selected = 'true') or not(@selected)]";
         
         clean(pages,xpath);
     }catch(x){
         
     }
  }
  
  
  function clearSlaFul(pages){
     try{
         var xpath = "./Page[@id='baseInfo']/Control[@id='SLA_FUL']/Item[not(@selected = 'true') or not(@selected)]";
         
         clean(pages,xpath);
     }catch(x){
        
     }
  }
  
  function cleareProdAttr(pages){
     try{
         var xpath = "./Page[@id='baseInfo']/Control[@id='Panel.CustOrder.CoProdAttrs']/Property[@type='DropDownList']/Item[not(@selected = 'true') or not(@selected)]";
         clean(pages,xpath);
     }catch(x){
     }
  }
  
  function clearAssureInfo(pages){
      try{
         var xpath = "./Page[@id='baseInfo']/Control[@id='CoAssureInfo']/Property[@id='CoAssureInfo.AssureTypeCd']/Item[not(@selected = 'true') or not(@selected)]";
         clean(pages,xpath);
     }catch(x){
     }
  }
  
  function clearOrderTypeInfo(pages){
     try{
         var xpath = "./Page[@id='orderTypeInfo']/Control[@id='OrderType']/Item[not(@checked = 'true') or not(@checked)]";
         clean(pages,xpath);
     }catch(x){     
     }
  }
  
   function clearNwk(pages){
     try{
         var xpath = "./Page[@id='nwkInfo']/Control[@id='AllNwk']/Item[./Column[not(@checked = 'true') or not(@checked)]]";
       
         clean(pages,xpath);
     }catch(x){  
     }
  }
  
  function clearNwkProps(pages){
     try{
         var xpath = "./Page[@id='nwkInfo']/Control[@id='AllNwk']/Props[@id='NwkProps']/Property";
         var itemSpec = "./Page[@id='nwkInfo']/Control[@id='AllNwk']";
         var nwk = pages.selectSingleNode(itemSpec);
         var props = pages.selectNodes(xpath);
         if(nwk == null) {
            return;
         }

         for(var i=0;i<props.length;i++){
            try{
                var dependId = props[i].getAttribute("dependId");
                if(dependId != null) {
                   var qry = "./Item/Column[@id='" + dependId + "']";
            
                   var spec=nwk.selectSingleNode(qry);               
                   if(spec == null){
                      props[i].parentNode.removeChild(props[i]);
                   }
                }
            }
            catch(e){
            }
         }
         
     }catch(x){  
     }
  }
  
  function clearNwkRules(pages){
      try{
         var xpath = "./Page[@id='nwkInfo']/Control[@id='AllNwk']/Rules[@id='NwkRule']";
         var items = pages.selectNodes(xpath);
         
         clean(pages,xpath);
     }catch(x){  
        
     }
  }
  
  function clearFeeInfo(pages){
     try{
         var xpath = "./Page[@id='feeInfo']/Control[@id='PricePackage']/Item[Column[@checked='false']]";
         var items = pages.selectNodes(xpath);
        
         clean(pages,xpath);
     }catch(x){ 
     	
     }
  }
  
  function clearFeeInfoAttr(pages){
     try{
         var xpath = "./Page[@id='feeInfo']/Control[@id='PricePackage']/Props[@id='PricePackageProps']/Property";
         var feeInfoProps = "./Page[@id='feeInfo']/Control[@id='PricePackage']";
         var feeInfo = pages.selectSingleNode(feeInfoProps);
         var props = pages.selectNodes(xpath);
         if(feeInfo == null) {
            return;
         }

         for(var i=0;i<props.length;i++){
            try{
                var packageId = props[i].getAttribute("packageId");
                if(packageId != null) {
                   var qry = "./Item[Column[@packageId='" + packageId + "']]";
            
                   var fee=feeInfo.selectSingleNode(qry);               
                   if(fee == null){
                      props[i].parentNode.removeChild(props[i]);
                   }
                }
            }
            catch(e){alert('001');}
         }
         
     }catch(x){  
     }
  }
  
  function clearFeeInfoRules(pages){
      try{
         var xpath = "./Page[@id='feeInfo']/Control[@id='PricePackage']/Rules[@id='package']";
         clean(pages,xpath);
     }catch(x){  
     }
  }
  
  function clearFeeInfoRelation(pages){
      try{
         var xpath = "./Page[@id='feeInfo']/Control[@id='PricePackage']/Relations[@id='package']";
         clean(pages,xpath);
     }catch(x){  
     }
  }
  
  function clearFeeInfoValidate(pages){
     try{
         var xpath = "./Page[@id='feeInfo']/Control[@id='PricePackage']/Props[@id='Validity']/Property";
         var feeInfoProps = "./Page[@id='feeInfo']/Control[@id='PricePackage']";
         var feeInfo = pages.selectSingleNode(feeInfoProps);
         var props = pages.selectNodes(xpath);
         if(feeInfo == null) {
            return;
         }

         for(var i=0;i<props.length;i++){
            try{
                var packageId = props[i].getAttribute("packageId");
                if(packageId != null) {
                   var qry = "./Item[Column[@packageId='" + packageId + "']]";
            
                   var fee=feeInfo.selectSingleNode(qry);               
                   if(fee == null){
                      props[i].parentNode.removeChild(props[i]);
                   }
                }
            }
            catch(e){
              
            }
         }
         
       }catch(x){  
       }
  }
  
  function clean(pages,xpath){
     try{
         var items = pages.selectNodes(xpath);
        
         items.removeAll();
     }catch(x){
     	     
     }
  }