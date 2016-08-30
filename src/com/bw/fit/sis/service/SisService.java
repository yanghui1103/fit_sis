package com.bw.fit.sis.service;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
    public JSONObject createPersonRptRecord(SystemCommonModel c);
    public JSONObject getCustomValueByOther(SystemCommonModel c);
    public JSONObject createOldPersonRptRecond(SystemCommonModel c);
    public JSONObject getExistsPsn(SystemCommonModel c);
    public  JSONObject luruNewRptFlow(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public  JSONObject luruNewPsnAndHisRptFlow(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public JSONObject qryWaitCheckRecordList(SystemCommonModel c);
    public JSONObject getThisCheckInfoAll(SystemCommonModel c);
}
