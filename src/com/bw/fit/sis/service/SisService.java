package com.bw.fit.sis.service;

import org.json.simple.JSONObject;

import com.bw.fit.common.models.SystemCommonModel;

public interface SisService {

    public JSONObject createSubCycle(SystemCommonModel c);
    public JSONObject qryAllCycleInfoList(SystemCommonModel c);
    public JSONObject deleteSubCycle(SystemCommonModel c);
    public JSONObject openOrCloseSubCycle(SystemCommonModel c);
    public JSONObject getRoleDeSubType(SystemCommonModel c);
    public JSONObject createRole2SubType(SystemCommonModel c);
    public JSONObject getPsnBaseInfo(SystemCommonModel c);
    public JSONObject changePsnBaseInfo(SystemCommonModel c);
}
