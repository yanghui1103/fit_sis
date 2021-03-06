package com.bw.fit.system.service.impl;
import static com.bw.fit.common.utils.PubFun.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.bw.fit.common.models.Function;
import com.bw.fit.common.models.SystemCommonModel;
import com.bw.fit.common.utils.MD5;
import com.bw.fit.system.dao.utils.SystemMybatisDaoUtil;
import com.bw.fit.system.service.SystemAdminService;
import com.bw.fit.common.models.*;
@Transactional
@Service
public class SystemAdminServiceImpl implements SystemAdminService {
    private  Log log = LogFactory.getLog(SystemAdminServiceImpl.class); 
    @Autowired
    private SystemMybatisDaoUtil systemMybatisDaoUtil;
    /***
     * pc登录验证并返回登录后的用户信息
     */
    @Override
    public JSONObject loginCheckAndBackUserInfos(SystemCommonModel c) {
        JSONObject info = new JSONObject();
        try {
            c.setSql("systemAdminDAO.loginCheckAndBackUserInfos"); 
            MD5 m = new MD5();
            c.setPasswd_mm(m.getMD5ofStr(c.getPasswd()));
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData( c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "登录成功");
                JSONArray array = new JSONArray();
                for (int i = 0; i < 1; i++) {
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("staff_id", (list.get(i)).getStaff_id());
                    jsonObjArr.put("staff_name", (list.get(i)).getStaff_name());
                    jsonObjArr.put("create_time", (list.get(i)).getCreate_time());
                    jsonObjArr.put("staff_company_id", (list.get(i)).getStaff_company_id());
                    jsonObjArr.put("staff_company_name", (list.get(i)).getStaff_company_name());
                    jsonObjArr.put("role_id", (list.get(i)).getRole_id());
                    jsonObjArr.put("role_name", (list.get(i)).getRole_name()); 
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null;
            } else {
                info.put("res", "1");
                info.put("msg", "登录信息不符或无此帐号");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }
    /***
     * 获取该用户拥有的权限树
     */
    @Override
    public JSONObject getAuthTreeList(SystemCommonModel c) {
        List<SystemCommonModel> ls = new ArrayList<SystemCommonModel>(); 
        JSONObject json = new JSONObject();
        List<Function> nodeList = new ArrayList<Function>(); 
        List<Function> inNodeList = new ArrayList<Function>(); 
        try {
            c.setSql("systemAdminDAO.getAuthTreeList");
            ls = systemMybatisDaoUtil.getListData(c.getSql(), c);
            if(ls.size() <1){
              json.put("res", "1");
              json.put("msg","没有赋予该用户任何权限，请联系业务管理员索取权限");
                return json ;
            } 
            for (int i = 0; i < ls.size(); i++) {  /// 先将取出来的放入一个list
                Function e = new Function();
                e.setFunction_id(Integer.valueOf(ls.get(i).getFunc_id()));
                e.setFunction_name(ls.get(i).getFunc_name());
                e.setFunction_url(ls.get(i).getFunc_address());
                e.setFunction_level(ls.get(i).getFunc_level());
                e.setFunction_p_id(Integer.valueOf(ls.get(i).getFunc_p_id()));
                e.setFunction_rel(ls.get(i).getFunc_rel());
                e.setFunction_target(ls.get(i).getFunc_target());
                nodeList.add(e);
                e = null ;
            }
            ls = null;
            Collections.sort(nodeList, new FunctionComparator());

            JSONArray array = new JSONArray();
            for (int i=0;i<nodeList.size() && !notInList(inNodeList,nodeList.get(i));i++){
                Function node = nodeList.get(i);        
                JSONArray array2 = new JSONArray(); 
                for(int j=i+1;j<nodeList.size();j++){
                    if(node.getFunction_id() == nodeList.get(j).getFunction_p_id()){  
                        JSONObject json2 =new JSONObject();
                        json2.put("id", nodeList.get(j).getFunction_id());
                        json2.put("level", nodeList.get(j).getFunction_level());
                        json2.put("page_name", nodeList.get(j).getFunction_name());
                        json2.put("p_id", nodeList.get(j).getFunction_p_id());
                        json2.put("rel", nodeList.get(j).getFunction_rel());
                        json2.put("target", nodeList.get(j).getFunction_target());
                        json2.put("page_url", nodeList.get(j).getFunction_url());
                        array2.add(json2);
                        inNodeList.add(nodeList.get(j));
                        JSONArray array3 = new JSONArray(); 
                        for(int k=j+1;k<nodeList.size();k++){
                            if(nodeList.get(j).getFunction_id() == nodeList.get(k).getFunction_p_id()){  
                                JSONObject json3 =new JSONObject();
                                json3.put("id", nodeList.get(k).getFunction_id());
                                json3.put("level", nodeList.get(k).getFunction_level());
                                json3.put("page_name", nodeList.get(k).getFunction_name());
                                json3.put("p_id", nodeList.get(k).getFunction_p_id());
                                json3.put("rel", nodeList.get(k).getFunction_rel());
                                json3.put("target", nodeList.get(k).getFunction_target());
                                json3.put("page_url", nodeList.get(k).getFunction_url());
                                array3.add(json3);
                                inNodeList.add(nodeList.get(k));
                            }
                        }
 
                        if(array3.size()>0)
                            json2.put("childs",array3);
                    }
                }
                JSONObject json1 = new JSONObject();
                json1.put("id", node.getFunction_id());
                json1.put("level", node.getFunction_level());
                json1.put("page_name", node.getFunction_name());
                json1.put("p_id", node.getFunction_p_id());
                json1.put("rel", node.getFunction_rel());
                json1.put("target", node.getFunction_target());
                json1.put("page_url", node.getFunction_url());
                if(array2.size()>0)
                    json1.put("childs",array2); 
                array.add(json1);
            }
            json.put("list", array);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    class FunctionComparator implements Comparator<Function> {
        @Override
        public int compare(Function o1, Function o2) {
        if (o1.getFunction_id()> o2.getFunction_id())
        return 1;
        if (o1.getFunction_id()< o2.getFunction_id())
        return -1;
        else
        return 0;
        }
    }
    public boolean  notInList(List<Function> ls,Function e){
        for(int i=0;i<ls.size();i++){
            if(e.getFunction_id() == ls.get(i).getFunction_id())
                return true ;
        }
        return false ;
    }
    /***
     *  检查用户，使用该action是否有权
     */
    @Override
    public JSONObject getUserAuthExists(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();         
            try {
                c.setSql("systemAdminDAO.getUserAuthExists");
                List<SystemCommonModel> list = systemMybatisDaoUtil.getListData( c.getSql(), c);
                if (list.size() > 0) {
                    info.put("res", "2");
                    info.put("msg", "执行成功");
                    }else{
                        info.put("res", "1");
                        info.put("msg", "抱歉，您无此功能权限");
                    }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        return info;
    }
    /***
     * 获取该用户该功能拥有的按钮权限
     */
    @Override
    public String getAuthorityBtnsByThisUserService(SystemCommonModel c) {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        try {
            c.setSql("systemAdminDAO.getAuthorityBtnsByThisUserService");
            List<SystemCommonModel> btnLs = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);// 全部按钮
            sb.append("<div style='float:right'>");
            for (int i = 0; i < btnLs.size(); i++) {
                if ("a_url".equalsIgnoreCase(btnLs.get(i).getBtn_type())) {
                    sb.append("<a class=buttonActive href='"
                            + btnLs.get(i).getFunc_address() + "'  fresh=true target="
                            + c.getFunc_target() + " ><span id='"
                            + btnLs.get(i).getFunc_btn() + "'>"
                            + btnLs.get(i).getBtn_name() + "</span></a>");
                } else if ("common"
                        .equalsIgnoreCase(btnLs.get(i).getBtn_type())) {
                    sb.append("<div class=buttonActive><div class=buttonContent><button id='"
                            + btnLs.get(i).getFunc_btn()
                            + "'  type=button>"
                            + btnLs.get(i).getBtn_name()
                            + "</button></div></div>");
                }
            } // end for
            sb.append("</div>");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
/****
 * 获取下拉菜单选项
 */

    @Override
    public List getDragWindowList(SystemCommonModel c) {
        List<SystemCommonModel> ls = new ArrayList();
        try {
            c.setSql("systemAdminDAO.getDragWindowList");
            ls = systemMybatisDaoUtil.getListData(c.getSql(), c);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public JSONObject getCustomOrgTree(SystemCommonModel c) {
        // 根据用户，功能查询出组织架构树
        JSONObject info = new JSONObject();
        try {
            c.setSql("systemAdminDAO.getCustomOrgTreeLevel");
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if ("1".equals(list.get(0).getFunc_level())) {
                // 所有组织
                c.setStaff_parent_company_id("0");
                return getOrgTreeStructs(c);
            } else if ("2".equals(list.get(0).getFunc_level())) {
                // 只有自身
                c.setStaff_parent_company_id(c.getStaff_company_id());
                c.setSql("systemAdminDAO.getThisOrgTreeStructs");
                List<SystemCommonModel> list_this = systemMybatisDaoUtil
                        .getListData(c.getSql(), c);
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray();
                JSONObject jsonObjArr = new JSONObject();
                jsonObjArr.put("id", (list_this.get(0)).getStaff_company_id());
                jsonObjArr
                        .put("pId", (list_this.get(0)).getStaff_parent_company_id());
                jsonObjArr.put("name", (list_this.get(0)).getStaff_company_name() );
                jsonObjArr.put("open", false);
                jsonObjArr.put("click", (list_this.get(0)).isFunc_click()); 
                array.add(jsonObjArr);
                jsonObjArr = null;
                info.put("list", array);
                array = null;
                return info;
            } else if ("3".equals(list.get(0).getFunc_level())) {// 自身及子组织
                c.setStaff_parent_company_id(c.getStaff_company_id());
                return getOrgTreeStructs(c);
            }else if ("4".equals(list.get(0).getFunc_level())) {// 所有子组织
                c.setStaff_parent_company_id(c.getStaff_company_id());
                return getAllChildsTreeStructs(c);
            }else if ("5".equals(list.get(0).getFunc_level())) {// 只下级子组织
                c.setStaff_parent_company_id(c.getStaff_company_id());
                return getNextChildsTreeStructs(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }

    @Override
    public JSONObject getOrgTreeStructs(SystemCommonModel c) {
        /**
         * 得出组织架构树
         */
        JSONObject info = new JSONObject();
        
        try {
            c.setSql("systemAdminDAO.getOrgTreeStructs");
            c.setFdid(getUUID());
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray();
                if(c.getTemp_int1()!=1){
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObjArr = new JSONObject();
                        jsonObjArr.put("id", (list.get(i)).getStaff_company_id());
                        jsonObjArr.put("pId", (list.get(i)).getStaff_parent_company_id());
                        jsonObjArr.put("name", (list.get(i)).getStaff_company_name());
                        jsonObjArr.put("open", false);
                        jsonObjArr.put("click", (list.get(i)).isFunc_click());
                        jsonObjArr.put("task", (list.get(i)).getTemp_str1());
                        array.add(jsonObjArr);
                        jsonObjArr = null;
                        }
                    info.put("list", array);
                    array = null;
                }else{
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObjArr = new JSONObject();
                        jsonObjArr.put("id", (list.get(i)).getStaff_company_id());
                        jsonObjArr.put("pId", (list.get(i)).getStaff_parent_company_id());
                        jsonObjArr.put("name", (list.get(i)).getStaff_company_name() );
                        jsonObjArr.put("open", false);
                        jsonObjArr.put("click", (list.get(i)).isFunc_click());  
                        array.add(jsonObjArr);
                        jsonObjArr = null;
                        }
                    info.put("list", array);
                    array = null;
                }
            } else {
                info.put("res", "2");
                info.put("msg", "查询失败");
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }

    @Override
    public JSONObject getAllChildsTreeStructs(SystemCommonModel c) {
        /**
         * 得出组织架构树
         */
        JSONObject info = new JSONObject();     
        try {
            c.setSql("systemAdminDAO.getAllChildsTreeStructs");
            c.setFdid(getUUID());
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray(); 
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObjArr = new JSONObject();
                        jsonObjArr.put("id", (list.get(i)).getStaff_company_id());
                        jsonObjArr.put("pId", (list.get(i)).getStaff_parent_company_id());
                        jsonObjArr.put("name", (list.get(i)).getStaff_company_name());
                        jsonObjArr.put("open", false);
                        jsonObjArr.put("click", (list.get(i)).isFunc_click()); 
                        array.add(jsonObjArr);
                        jsonObjArr = null;
                        }
                    info.put("list", array);
                    array = null;
                 
            } else {
                info.put("res", "2");
                info.put("msg", "查询失败");
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }

    @Override
    public JSONObject getNextChildsTreeStructs(SystemCommonModel c) {
        /**
         * 得出组织架构树
         */
        JSONObject info = new JSONObject();     
        try {
            c.setSql("systemAdminDAO.getNextChildsTreeStructs");
            c.setFdid(getUUID());
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray(); 
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObjArr = new JSONObject();
                        jsonObjArr.put("id", (list.get(i)).getStaff_company_id());
                        jsonObjArr.put("pId", (list.get(i)).getStaff_parent_company_id());
                        jsonObjArr.put("name", (list.get(i)).getStaff_company_name());
                        jsonObjArr.put("open", false);
                        jsonObjArr.put("click", (list.get(i)).isFunc_click()); 
                        array.add(jsonObjArr);
                        jsonObjArr = null;
                        }
                    info.put("list", array);
                    array = null;
                 
            } else {
                info.put("res", "2");
                info.put("msg", "查询失败");
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }
    /***
     * 角色分配的树
     */

    @Override
    public JSONObject getFunctionsTreeStructs(SystemCommonModel c) {
        /**
         * 获取功能树，及选中
         */
        JSONObject info = new JSONObject();
        try {
            c.setSql("systemAdminDAO.getFunctionsTreeStructs");
            c.setFdid(getUUID());
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("id", (list.get(i)).getFunc_id());
                    jsonObjArr.put("pId", (list.get(i)).getFunc_p_id());
                    jsonObjArr.put("name", (list.get(i)).getFunc_name());
                    jsonObjArr.put("open", false);
                    if ("0".equals(list.get(i).getFunc_check())) {
                        jsonObjArr.put("nochecked", true);
                    } else {
                        jsonObjArr.put("checked", true);
                    }
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null;
            } else {
                info.put("res", "1");
                info.put("msg", "查询失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }
    /****
     * 角色和用户使用合法性校验
     */
    @Override
    public JSONObject checkRoleAndStaffValidate(SystemCommonModel c) {
        // TODO Auto-generated method stub

        JSONObject info = new JSONObject(); 
            info.put("res", "2");
            info.put("msg", "校验合法"); 
        return info;
    }

    @Override
    public JSONObject changePasswd(SystemCommonModel c) {
        // 修改密码
        JSONObject info = new JSONObject();
        try {
            c.setCreate_time(getSysDate());
            c.setFdid(getUUID());
            c.setSql("systemAdminDAO.changePasswd"); 
            info =  systemMybatisDaoUtil.sysUpdateData( 
                    c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;
    }

    @Override
    public JSONObject clearPasswd(SystemCommonModel c) {
        // 重置密码
        JSONObject info = new JSONObject();
        try {
            c.setCreate_time(getSysDate());
            c.setFdid(getUUID());
            c.setSql("systemAdminDAO.checkPersonExists");
            List list = systemMybatisDaoUtil.getListData( c.getSql(), c); 
            if(list.size()<1){
                info.put("res", "1");
                info.put("msg","该登录名不存在或已失效!");
                return info ;
            }
            c.setSql("systemAdminDAO.clearPasswd");
            info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    @Override
    public JSONObject createNewSysOrgService(SystemCommonModel c) {
        /**
         * 新建组织
         */
        JSONObject info = new JSONObject();
        try {
            c.setCreate_time(getSysDate()); 
            // 获取该机构的子机构的最大编码加1
            c.setSql("systemAdminDAO.getHasChild"); 
            List<SystemCommonModel> list0= systemMybatisDaoUtil.getListData(c.getSql(), c); 

            c.setSql("systemAdminDAO.getChildOrgMaxFdID"); 
            List<SystemCommonModel> list= systemMybatisDaoUtil.getListData(c.getSql(), c); 
            if(list0.size()>0){
                c.setFdid(list.get(0).getFdid());
            }else{
                c.setFdid(c.getStaff_parent_company_id()+"01");
            }
            c.setSql("systemAdminDAO.createNewSysOrg"); 
            info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;
    }

    @Override
    public JSONObject getAllOrgsService(SystemCommonModel c) {
        /**
         * 查询机构
         */
        JSONObject info = new JSONObject();
        try {
            c.setSql("systemAdminDAO.getAllOrgs"); 
            c.setFdid(getUUID());
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("company_cd", (list.get(i)).getSelect_company_id());
                    jsonObjArr.put("company_name",
                            (list.get(i)).getSelect_company_name());
                    jsonObjArr.put("level", (list.get(i)).getTemp_str1());
                    jsonObjArr.put("up_company_name",
                            (list.get(i)).getTemp_str2());
                    jsonObjArr.put("create_time",
                            (list.get(i)).getCreate_time());
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                // 算出总条数和总页数
                c.setStart_num("-9");
                c.setEnd_num("-9");
                List<SystemCommonModel> listTatol = systemMybatisDaoUtil
                        .getListData(c.getSql(), c);
                info.put(
                        "pageNum",
                        getPageTatolNum(listTatol.size(),
                                Integer.valueOf(c.getRecord_tatol())));
                info.put("tatol", listTatol.size());
            } else {
                info.put("res", "1");
                info.put("msg", "查询失败");
                info.put("pageNum","0");
                info.put("tatol", "0");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }
    /**
     * 删除组织(循环)
     */
    @Override
    public JSONObject deleteSelectedOrgs(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
            c.setVersion_time(getSysDate()); 
            String[] array = c.getTemp_str1().split(",");
            for(int i=0;i<array.length ;i++){
                c.setSelect_company_id(array[i]);
                c.setSql("systemAdminDAO.deleteSelectedOrgs"); 
                info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
                if("1".equals(info.get("res").toString())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info; 
    }
    @Override
    public JSONObject initOrgInfoEdit(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();     
        try {
            c.setSql("systemAdminDAO.initOrgInfoEdit"); 
            List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                    c.getSql(), c);
            if (list.size() > 0) {
                info.put("res", "2");
                info.put("msg", "查询成功");
                JSONArray array = new JSONArray(); 
                    for (int i = 0; i < 1; i++) {
                        JSONObject jsonObjArr = new JSONObject();
                        jsonObjArr.put("company_id", (list.get(i)).getSelect_company_id());
                        jsonObjArr.put("company_name", (list.get(i)).getSelect_company_name());
                        jsonObjArr.put("p_company_name", (list.get(i)).getTemp_str1());
                        jsonObjArr.put("p_company_id", (list.get(i)).getTemp_str2());
                        jsonObjArr.put("company_type",  (list.get(i)).getTemp_str3());
                        jsonObjArr.put("company_address", (list.get(i)).getSelect_company_address()); 
                        array.add(jsonObjArr);
                        jsonObjArr = null;
                        }
                    info.put("list", array);
                    array = null;
                 
            } else {
                info.put("res", "1");
                info.put("msg", "查询失败");
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        return info;
    }
    /***
     * 编辑组织资料
     */
    @Override
    public JSONObject updateOrgInfo(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
                c.setVersion_time(getSysDate());    
                c.setSql("systemAdminDAO.updateOrgInfo"); 
                info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
                if("1".equals(info.get("res").toString())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                } 
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;  
    }
    @Override
    public JSONObject createNewRole(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
                c.setVersion_time(getSysDate());   
                c.setFdid(getUUID());
                c.setSql("systemAdminDAO.createNewRole"); 
                info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
                if("1".equals(info.get("res").toString())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                } 
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;  
    } 
        @Override
        public JSONObject giveThisRoleFuntions(SystemCommonModel c) {
            // 赋权给角色ID
            JSONObject info = new JSONObject();
            try { 
                c.setSql("systemAdminDAO.qryAuthorityRoleRelation");
                if (systemMybatisDaoUtil.getListData(c.getSql(), c).size() > 0) { // 如果该角色下有功能
                    c.setSql("systemAdminDAO.deleteThisRoleFuntions");
                    info = systemMybatisDaoUtil.sysDeleteData(c.getSql(), c);
                    if("1".equals(info.get("res").toString())){
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                        return info ;
                    }

                    c.setSql("systemAdminDAO.qryAuthorityRoleRelation2");
                    if (systemMybatisDaoUtil.getListData(c.getSql(), c).size() > 0) {
                        c.setSql("systemAdminDAO.deleteThisRoleFuntionsLevel");
                        JSONObject info_level = systemMybatisDaoUtil.sysDeleteData(
                                c.getSql(), c); 
                        if("1".equals(info_level.get("res").toString())){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                            return info ;
                        }
                    }
                }

                String role_cd = c.getRole_id();
                String[] nodeArray = c.getTemp_str1().split(",");
                for (int i = 0; i < nodeArray.length; i++) {
                    c.setFunc_id(nodeArray[i]);
                    c.setSql("systemAdminDAO.checkAuthorityExiste");
                    SystemCommonModel cc = (SystemCommonModel) systemMybatisDaoUtil
                            .getOneData(c.getSql(), c);
                    if (Integer.valueOf(cc.getTemp_int1()) < 1) {
                        // 说明functions表中不存在这个ID的功能,肯定是在btn表里
                        c.setSql("systemAdminDAO.qryAuthBtnExiste");
                        List<SystemCommonModel> ls = systemMybatisDaoUtil
                                .getListData(c.getSql(), c);
                        c.setSql("systemAdminDAO.giveAuthorityToRoleService");
                        c.setBtn_cd(ls.get(0).getBtn_cd()); // btn_cd
                        c.setFunc_id(ls.get(0).getFunc_id()); // 把这个btn的function_cd找到并赋进去
                        info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                        if("1".equals(info.get("res").toString())){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                            return info ;
                        }
                        
                    } else {// 这样就把该功能的展示权限赋上
                        c.setSql("systemAdminDAO.giveAuthorityToRoleService");
                        c.setBtn_cd("view");// 展示权限
                        info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                        if("1".equals(info.get("res").toString())){
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                            return info ;
                        }
                    }
                    if (!"2".equals(info.get("res").toString())) { 
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                        return info;
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            info.put("res", "2");
            info.put("msg", "赋权成功");
            return info;
        }
        @Override
        public JSONObject qryRoleRelatFuncInfoList(SystemCommonModel c) {
            // TODO Auto-generated method stub
         // 根据角色查询功能权级信息列表
            JSONObject info = new JSONObject();
            try {
                c.setSql("systemAdminDAO.qryRoleRelatFuncInfoList");
                List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                if(list.size()<1){
                    info.put("res", "1");
                    info.put("msg","无数据");
                    info.put("pageNum","0");
                    info.put("tatol", "0");
                    return info ;
                }else{
                    info.put("res", "2");
                    info.put("msg","执行成功"); 
                }
                JSONArray array = new JSONArray();
                for(int i=0;i<list.size();i++){
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("role_id", (list.get(i)).getFdid());
                    jsonObjArr.put("func_id", (list.get(i)).getFunc_id());
                    jsonObjArr.put("role_name", (list.get(i)).getRole_name());
                    jsonObjArr.put("func_name", (list.get(i)).getFunc_name());
                    jsonObjArr.put("parent_func_name", (list.get(i)).getTemp_str1());
                    jsonObjArr.put("func_level", (list.get(i)).getFunc_level());
                    jsonObjArr.put("version_time", (list.get(i)).getTemp_str2());
                    jsonObjArr.put("operator", (list.get(i)).getStaff_name());
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null;
                c.setEnd_num("-9");
                List<SystemCommonModel> list_total = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                info.put("pageNum", 
                        getPageTatolNum(list_total.size(),
                                Integer.valueOf(c.getRecord_tatol())));
                info.put("tatol", list_total.size());
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info(ex.getMessage());
            }
            return info;   
        }
        /**
         * 保存权级信息
         */
        
        @Override
        public JSONObject saveRoleFuncLevelInfo(SystemCommonModel c) { 
            JSONObject info1 =  new JSONObject();
            JSONObject info =  new JSONObject();
            try {
                c.setCreate_time(getSysDateM());
                String[] roleFuncArray = c.getTemp_str1().split(",");// 角色功能数组
                for(int i =0;i<roleFuncArray.length;i++){
                    String[] array = roleFuncArray[i].split(";");
                    c.setRole_id(array[0]);             c.setFunc_id(array[1]);
                    c.setSql("systemAdminDAO.qryRoleRelatFuncExsistInfo");
                    List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                            c.getSql(), c); 
                    c.setFdid(getUUID()); 
                    if(list.size()>0){
                        c.setSql("systemAdminDAO.changeRoleFuncLevelInfo");
                        info1 = systemMybatisDaoUtil.sysUpdateData(c.getSql(),c);
                    }else{
                        c.setSql("systemAdminDAO.createRoleFuncLevelInfo");
                        info1 = systemMybatisDaoUtil.sysUpdateData(c.getSql(),c);
                    }
                    if(!"2".equals(info1.get("res").toString())){
                        info = info1 ;
                        info1 = null ;
                        TransactionAspectSupport.currentTransactionStatus()
                        .setRollbackOnly();                 
                        return info;
                    }
                }

            }catch(Exception ex){
                ex.printStackTrace();
                
            }
            info.put("res", "2");
            info.put("msg", "执行成功");
            return info;
        }

        @Override
        public JSONObject createNewSysUser(SystemCommonModel c) {
            // 新建用户资料
            JSONObject info1 = new JSONObject();
            JSONObject info = new JSONObject();
            try {
                c.setCreate_time(getSysDateM());
                c.setFdid(getUUID());
                c.setSql("systemAdminDAO.createNewSysUser");
                info1 = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                if ("2".equals(info1.get("res").toString())) {
                    c.setTemp_str1(getUUID());
                    c.setSql("systemAdminDAO.createStaff2Role");
                    info1 = systemMybatisDaoUtil.sysUpdateData(
                            c.getSql(), c);
                    if ("2".equals(info1.get("res").toString())) {
                        c.setSql("systemAdminDAO.createStaff2Company");
                        info1 = systemMybatisDaoUtil.sysUpdateData(
                                c.getSql(), c);
                        if ("2".equals(info1.get("res").toString())) { 
                            return info1;
                        } else {
                            TransactionAspectSupport.currentTransactionStatus()
                                    .setRollbackOnly();
                            info.put("res", "1");
                            info.put("msg", "系统出错");
                            return info;
                        }
                    } else {
                        TransactionAspectSupport.currentTransactionStatus()
                                .setRollbackOnly();
                        info.put("res", "1");
                        info.put("msg", "系统出错");
                        return info;
                    }

                } else {
                    TransactionAspectSupport.currentTransactionStatus()
                            .setRollbackOnly();
                    info.put("res", "1");
                    info.put("msg", "系统出错");
                    return info;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return info;
        }

        @Override
        public JSONObject qryAllUserInfoList(SystemCommonModel c) {
            // 用户列表
            JSONObject info = new JSONObject();
            try {
                c.setSql("systemAdminDAO.qryAllUserInfoList"); 
                String[] comps = c.getSelect_company_id().split(",");
                List<String> ls = Arrays.asList(comps);
                c.setTemp_list(ls);
                List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                if(list.size()<1){
                    info.put("res", "1");
                    info.put("msg","无数据");
                    info.put("pageNum","0");
                    info.put("tatol", "0");
                    return info ;
                }else{
                    info.put("res", "2");
                    info.put("msg","执行成功"); 
                }
                JSONArray array = new JSONArray();
                for(int i=0;i<list.size();i++){
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("staff_id", (list.get(i)).getFdid());
                    jsonObjArr.put("staff_number", (list.get(i)).getStaff_number());
                    jsonObjArr.put("staff_name", (list.get(i)).getStaff_name());
                    jsonObjArr.put("phone", (list.get(i)).getStaff_phone()); 
                    jsonObjArr.put("address", (list.get(i)).getSelect_company_address());
                    jsonObjArr.put("role_name", (list.get(i)).getRole_name());
                    jsonObjArr.put("company_name", (list.get(i)).getSelect_company_name());
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null;
                c.setEnd_num("-9");
                List<SystemCommonModel> list_total = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                info.put("pageNum", 
                        getPageTatolNum(list_total.size(),
                                Integer.valueOf(c.getRecord_tatol())));
                info.put("tatol", list_total.size());
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info(ex.getMessage());
            }
            return info;
        }

        @Override
        public JSONObject deleteUserInfo(SystemCommonModel c) {
            // 删除用户
            JSONObject info = new JSONObject();
            try { 
                c.setCreate_time(getSysDate());
                String[] array = c.getTemp_str1().split(",");
                for(int i =0;i<array.length;i++){
                    c.setFdid(array[i]);
                    c.setSql("systemAdminDAO.deleteUserInfo"); 
                    info= systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                    if(!"2".equals(info.get("res").toString())){
                         TransactionAspectSupport.currentTransactionStatus()
                            .setRollbackOnly(); 
                                return info;
                     } 
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return info;
        }

        @Override
        public JSONObject getThisUserInfo(SystemCommonModel c) {
            // 获取一个用户资料
            JSONObject info = new JSONObject();
            try {
                c.setSql("systemAdminDAO.getThisUserInfo");
                List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                if(list.size()<1){
                    info.put("res", "1");
                    info.put("msg","无数据"); 
                    return info ;
                }else{
                    info.put("res", "2");
                    info.put("msg","执行成功"); 
                }
                JSONArray array = new JSONArray();
                for(int i=0;i<1;i++){
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("staff_number", (list.get(i)).getStaff_number());
                    jsonObjArr.put("staff_name", (list.get(i)).getStaff_name());
                    jsonObjArr.put("company_name", (list.get(i)).getSelect_company_name());
                    jsonObjArr.put("company_id", (list.get(i)).getSelect_company_id());
                    jsonObjArr.put("staff_phone", (list.get(i)).getStaff_phone()); 
                    jsonObjArr.put("staff_address", (list.get(i)).getSelect_company_address()); 
                    jsonObjArr.put("role_id", (list.get(i)).getRole_id()); 
                    jsonObjArr.put("staff_type_cd", (list.get(i)).getTemp_str1()); 
                    jsonObjArr.put("create_time", (list.get(i)).getCreate_time()); 
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null; 
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info(ex.getMessage());
            }
            return info;
        }

        @Override
        public JSONObject updateUserInfo(SystemCommonModel c) {
            // 用户资料编辑
            JSONObject info = new JSONObject();
            try {  
                    c.setSql("systemAdminDAO.updateUserInfo"); 
                    info= systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                    if(!"2".equals(info.get("res").toString())){
                         TransactionAspectSupport.currentTransactionStatus()
                            .setRollbackOnly(); 
                                return info;
                    }
                    // 变更组织
                    c.setSql("systemAdminDAO.updateUserComp"); 
                    info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                    if(!"2".equals(info.get("res").toString())){
                         TransactionAspectSupport.currentTransactionStatus()
                            .setRollbackOnly(); 
                                return info;
                    }
                    //变更角色
                    c.setSql("systemAdminDAO.updateUserRole"); 
                    info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c);
                    if(!"2".equals(info.get("res").toString())){
                         TransactionAspectSupport.currentTransactionStatus()
                            .setRollbackOnly(); 
                                return info;
                    } 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return info;
        }

        @Override
        public JSONObject getFileListByForeginId(SystemCommonModel c) {
            // TODO Auto-generated method stub
            JSONObject info = new JSONObject();
            try {
                c.setSql("systemAdminDAO.getFileListByForeginId");
                List<SystemCommonModel> list = systemMybatisDaoUtil.getListData(
                        c.getSql(), c); 
                if(list.size()<1){
                    info.put("res", "1");
                    info.put("msg","无数据"); 
                    return info ;
                }else{
                    info.put("res", "2");
                    info.put("msg","执行成功"); 
                }
                JSONArray array = new JSONArray();
                for(int i=0;i<list.size();i++){
                    JSONObject jsonObjArr = new JSONObject();
                    jsonObjArr.put("fdid", (list.get(i)).getFdid());
                    jsonObjArr.put("filename", (list.get(i)).getTemp_str1());
                    jsonObjArr.put("create_time", (list.get(i)).getCreate_time());
                    jsonObjArr.put("staff_name", (list.get(i)).getStaff_name());
                    jsonObjArr.put("temp_str2", (list.get(i)).getTemp_str2()); 
                    jsonObjArr.put("before_name", (list.get(i)).getTemp_str3()); 
                    array.add(jsonObjArr);
                    jsonObjArr = null;
                }
                info.put("list", array);
                array = null; 
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info(ex.getMessage());
            }
            return info;
        }

        @Override
        public JSONObject createForeignAndAttachmentRelation(SystemCommonModel c) {
            // TODO Auto-generated method stub
            JSONObject info = new JSONObject();
            try {
                c.setCreate_time(getSysDate());
                c.setFdid(getUUID());
                c.setSql("systemAdminDAO.createForeignAndAttachmentRelation"); 
                info = systemMybatisDaoUtil.sysUpdateData(c.getSql(), c); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return info;
        }

        @Override
        public JSONObject deleteAttachmentFile(SystemCommonModel c) {
            // TODO Auto-generated method stub
            JSONObject info = new JSONObject();
            try {                
                c.setSql("systemAdminDAO.deleteAttachmentFile"); 
                info = systemMybatisDaoUtil.sysDeleteData(c.getSql(), c); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return info;
        }
}
