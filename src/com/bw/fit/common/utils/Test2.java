package com.bw.fit.common.utils;

import org.json.simple.*;
import org.json.simple.JSONValue;

public class Test2  extends Test{
    
    public static void main(String[] args){ 
        String s = "[{\"name\":\"create_company_name\",\"value\":\"斯多夫\"},{\"name\":\"orgLookup.orgName\",\"value\":\"虚拟管理机构\"},{\"name\":\"orgLookup.id\",\"value\":\"1506\"},{\"name\":\"create_uporg_type\",\"value\":\"-9\"},{\"name\":\"createComp_address\",\"value\":\"\"}]";

        JSONArray array  = (JSONArray) JSONValue.parse(s); 
        
        System.out.println(((JSONObject)array.get(0)).get("name"));
        
    }
}
