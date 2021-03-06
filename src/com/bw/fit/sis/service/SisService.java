package com.bw.fit.sis.service;

import javax.servlet.http.HttpServletRequest;

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
    /***
     * 查询暂存数据
     * @param c
     * @return
     */
    public JSONObject qryTempRecordList(SystemCommonModel c);
    public JSONObject deleteTempRecord(SystemCommonModel c);
    public JSONObject getThisCheckInfoAll(SystemCommonModel c);
    public JSONObject getThisTmpCheckInfoAll(SystemCommonModel c);
    public  JSONObject checkRpt(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public  JSONObject checkRptSecond(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public  JSONObject deleteRptRecond(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public JSONObject grantFinanceToPsn(SystemCommonModel c, RuntimeService runtimeService, FormService formService,
            TaskService taskService) ;
    public JSONObject qryRptedRecordList(SystemCommonModel c);
    public JSONObject getPersonRptedInfo(SystemCommonModel c);
    public JSONObject checkPsnRpting(SystemCommonModel c);
    public  JSONObject updateRptRecond(SystemCommonModel c,RuntimeService runtimeService,FormService formService, TaskService taskService) ;
    public JSONObject rptOfficeApplyedReport(SystemCommonModel c,String path) ;
    public JSONObject getCheckHistory(SystemCommonModel c);
    public JSONObject qryRptingRecordList(SystemCommonModel c);
    public JSONObject getAllRptingSituation(SystemCommonModel c);
    public JSONObject qryWaitCheckRecordList_finance(SystemCommonModel c);
    public JSONObject uploadNTPhotos(SystemCommonModel c,HttpServletRequest request,String path,String toPath);
    public JSONObject uploadNTPhotos2(SystemCommonModel c,HttpServletRequest request,String path,String new_path);
    public JSONObject getThisApplyInfoByFlowId(SystemCommonModel c);
    public JSONObject createEasyRptRecond(SystemCommonModel c);
    public JSONObject auditEasyRptRecond(SystemCommonModel c);
    public JSONObject guidangRptRecond(SystemCommonModel c);
    public JSONObject qryEasyWaitCheckRecordList(SystemCommonModel c);
    public JSONObject getRptDetail(SystemCommonModel c);
    public JSONObject checkEasyRpt(SystemCommonModel c);
    public JSONObject deleteEasyRpt(SystemCommonModel c);
    public JSONObject updateEastRpt(SystemCommonModel c);
    public JSONObject checkPsnRpting2(SystemCommonModel c) ;
    public JSONObject getTheCheckResaultApply(SystemCommonModel c);
    /*****
     * 保存为暂存
     * @param c
     * @return
     */
    public JSONObject createThisTmpRptRecond(SystemCommonModel c);
}
