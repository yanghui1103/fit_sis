package com.bw.fit.system.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.bw.fit.common.models.SystemCommonModel;

public interface SystemAdminService {

    public JSONObject loginCheckAndBackUserInfos(SystemCommonModel c);
    public JSONObject getAuthTreeList(SystemCommonModel c);
    public JSONObject getUserAuthExists(SystemCommonModel c);
    public String getAuthorityBtnsByThisUserService(SystemCommonModel c);
    public List<SystemCommonModel> getDragWindowList(SystemCommonModel c);
    public JSONObject getCustomOrgTree(SystemCommonModel c);
    public JSONObject getOrgTreeStructs(SystemCommonModel c);
    public JSONObject getAllChildsTreeStructs(SystemCommonModel c);
    public JSONObject getNextChildsTreeStructs(SystemCommonModel c);
    public JSONObject getFunctionsTreeStructs(SystemCommonModel c);
    public JSONObject checkRoleAndStaffValidate(SystemCommonModel c);
    public JSONObject changePasswd(SystemCommonModel c);
    public JSONObject clearPasswd(SystemCommonModel c);
    public JSONObject createNewSysOrgService(SystemCommonModel c);
    public JSONObject getAllOrgsService(SystemCommonModel c);
    public JSONObject deleteSelectedOrgs(SystemCommonModel c);
    public JSONObject initOrgInfoEdit(SystemCommonModel c);
    public JSONObject updateOrgInfo(SystemCommonModel c);
    
}
