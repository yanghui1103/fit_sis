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
}
