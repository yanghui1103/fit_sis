package com.bw.fit.system.actions;
import com.bw.fit.system.service.impl.*;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.bw.fit.common.utils.PubFun.*;

import javax.servlet.ServletContext; 
import javax.servlet.http.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bw.fit.common.models.LoginUser;
import com.bw.fit.common.models.SystemCommonModel;
import com.bw.fit.common.utils.MD5;
import com.bw.fit.system.service.impl.SystemAdminServiceImpl;


@Controller
public class SystemAction extends BaseAction{
    private  HttpServletResponse response = ServletActionContext.getResponse() ;
    private  HttpServletRequest request = ServletActionContext.getRequest(); 
    private  Log log = LogFactory.getLog(this.getClass()); 
    private ServletContext servletContext = request.getServletContext(); 
    
    /*****
     * 2016-8-15
     * pc登录
     * @return
     */
    public String userLoginStyleStaff(){        
        try { 
                SystemCommonModel c = new SystemCommonModel();
                c.setStaff_number(request.getParameter("j_username"));
                c.setPasswd(request.getParameter("j_password"));
                c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
                SystemAdminServiceImpl impl = (SystemAdminServiceImpl)getBean("systemAdminServiceImpl");
                JSONObject  obj = impl.loginCheckAndBackUserInfos(c) ;
                JSONArray array = ((JSONArray)obj.get("list")); 
                if("2".equals(obj.get("res").toString())){
                    LoginUser u = new LoginUser();
                    u.setStaff_id(((JSONObject) array.get(0)).get("staff_id").toString());
                    u.setStaff_name(((JSONObject) array.get(0)).get("staff_name").toString());
                    u.setStaff_number(c.getStaff_number());
                    u.setOrg_cd(((JSONObject) array.get(0)).get("staff_company_id").toString()); 
                    u.setOrg_name(((JSONObject) array.get(0)).get("staff_company_name").toString());  
                    u.setRole_id(((JSONObject) array.get(0)).get("role_id").toString());  
                    u.setRole_name(((JSONObject) array.get(0)).get("role_name").toString());  
                    u.setCreate_time(((JSONObject) array.get(0)).get("create_time").toString());   
                    //登录成功后，获取这个人的权限树JSON
                    SystemCommonModel c2 = new SystemCommonModel();
                    c.setStaff_id(u.getStaff_id());  // 用户编码
                    c.setAction_name("getAuthTreeList");   
                    JSONObject obj2 = ((SystemAdminServiceImpl)getBean("systemAdminServiceImpl")).getAuthTreeList(c);
                    u.setPwrTreeJson(obj2.toJSONString());
                    HttpSession session_new = request.getSession(true);  // 新开辟一个会话
                    session_new.setAttribute("LoginUser", u);
                    return "SUCCESS" ;
                }else{
                    return "FAIL" ;
                } 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return "SUCCESS" ;
    }
    /*****
     * 2016-8-15
     * 登录用户，获取拥有的权限树
     * @return
     */
    public String getAuthTreeList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();   
            JSONObject objBefore = (JSONObject) JSONValue.parse(buildJSON(request));
            SystemCommonModel c = new SystemCommonModel();
            c.setStaff_id(objBefore.get("param1").toString());  // 用户编码
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
            JSONObject obj = ((SystemAdminServiceImpl)getBean("systemAdminServiceImpl")).getAuthTreeList(c);
            wr.write(obj.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 
     * logOut
     * 登出系统
     */
    public String logOut(){
        // 把session杀死
        HttpSession session = request.getSession(false); 
        session.invalidate();
        return "SUCCESS" ;
    }
    /***
     * 这个用户的按钮显示
     */

    /**
     * getAuthorityBtnsByThisUser
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public String getAuthorityBtnsByThisUser() {
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ;
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            JSONObject objBefore = ((JSONObject) array.get(0)) ;
            SystemCommonModel c = new SystemCommonModel();
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            c.setFunc_id(objBefore.get("param1").toString()); // 当前功能页面
            c.setStaff_id(((LoginUser) request.getSession(false).getAttribute("LoginUser"))
                    .getStaff_id()); // 当前用户
            c.setFunc_target(objBefore.get("param2").toString());
            String txt = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .getAuthorityBtnsByThisUserService(c);
            wr.write(txt);
            wr.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取下拉select
     */
    public String getSysItems() {
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));
            SystemCommonModel c = new SystemCommonModel();
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            c.setItem_type(param1);
            List<SystemCommonModel> lsResault = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .getDragWindowList(c);
            JSONObject objItem;
            JSONArray arrayItem = new JSONArray();
            for (int i = 0; i < lsResault.size(); i++) {
                objItem = new JSONObject();
                objItem.put("id",  lsResault.get(i).getItem_id());
                objItem.put("name", lsResault.get(i).getItem_name());
                arrayItem.add(objItem);
            }
            wr.write(arrayItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取该用户的此功能，所拥有的
     * 层级的树
     */
    public String getCustomOrgTree() throws Exception {
        HttpSession session = request.getSession(false); 
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
        SystemCommonModel c = new SystemCommonModel(); 
        c.setFunc_id(param1) ;
        c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser"))
                .getOrg_cd());
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .getCustomOrgTree(c);
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
    }
    
    /***
     * 获取这个角色的权限树及没有选中的情况
     */
    public String getFunctionsTreeStructs(){
        try {
            HttpSession session = request.getSession(false); 
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setRole_id(param1) ; 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .getFunctionsTreeStructs(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /***
     * 角色和用户之间的合法性
     */
    public String checkRoleAndStaffValidate(){
        try {
            HttpSession session = request.getSession(false); 
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setRole_id(param1) ; 
            c.setStaff_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .checkRoleAndStaffValidate(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    
    /**
     * 修改密码
     */
    public String changePasswd() throws Exception {
        HttpSession session = request.getSession(false); 
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
        SystemCommonModel c = new SystemCommonModel();
        MD5 m = new MD5();      
        c.setTemp_str1(m.getMD5ofStr(param1));
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .changePasswd(c);
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
    }
    public String clearPasswd() throws Exception {
        HttpSession session = request.getSession(false); 
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1= (String) (((JSONObject) array.get(0)).get("param1")); 
        String param2= (String) (((JSONObject) array.get(0)).get("param2")); 
        SystemCommonModel c = new SystemCommonModel();
        MD5 m = new MD5();      
        c.setTemp_str1(m.getMD5ofStr(param2));
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        c.setStaff_number(param1);
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .clearPasswd(c);
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
    }
    /**
     * createNewSysOrg
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public String createNewSysOrg() throws Exception {
        HttpSession session = request.getSession(false);  
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        SystemCommonModel c = new SystemCommonModel();
        String str =  (request.getParameter("context")) ; 
        JSONArray array  = (JSONArray) JSONValue.parse(str); 
        for(int i=0;i<array.size();i++){
            if("create_company_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setStaff_company_name(((JSONObject)array.get(i)).get("value").toString());
            }else if("orgLookup.id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setStaff_parent_company_id(((JSONObject)array.get(i)).get("value").toString());
            }else if("createComp_address".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
            }else if("create_uporg_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str2(((JSONObject)array.get(i)).get("value").toString());
            }
        }  
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
        JSONObject  json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .createNewSysOrgService(c);
        wr.write(json.toJSONString());
        wr.close();
        return null;
    }
    
    /**
     * getAllOrgs获取组织架构
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public String getAllOrgs() throws Exception {
        response.setContentType("text/xml;charset=UTF-8");
        Writer wr = response.getWriter();
        SystemCommonModel c = new SystemCommonModel();
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
        String str = (request.getParameter("context")) ;
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        c.setSelect_company_name((String) (((JSONObject) array.get(0)).get("param1"))); 
        c.setStart_num((String)(((JSONObject) array.get(0)).get("param2"))); 
        c.setEnd_num((String)(((JSONObject) array.get(0)).get("param3"))); 
        c.setRecord_tatol((String)(((JSONObject) array.get(0)).get("param4"))); 

        JSONObject json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .getAllOrgsService(c); 
        wr.write(json.toJSONString());
        wr.close();

        return null;
    }
    /****
     * deleteSelectedOrgs
     * 删除组织
     */
    public String deleteSelectedOrgs(){
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/xml;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
            String str = (request.getParameter("context")) ;
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param1")));  
            JSONObject json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .deleteSelectedOrgs(c); 
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }

        return null ;
    }
    /**
     * 编辑组织资料
     */
    public String updateOrgInfo(){
        try {
            HttpSession session = request.getSession(false);   
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str); 
            
            for(int i=0;i<array.size();i++){
                if("orgEditId".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setSelect_company_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("create_company_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setSelect_company_name(((JSONObject)array.get(i)).get("value").toString());
                }else if("orgLookup.id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setStaff_parent_company_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("create_uporg_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                }else if("createComp_address".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setSelect_company_address(((JSONObject)array.get(i)).get("value").toString());
                }
            }  
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id()); 
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());     
            JSONObject json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .updateOrgInfo(c); 
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null ;
    }
    /****
     * 组织编辑页面初始化
     */
    public String initOrgInfoEdit(){
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/xml;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
            String str = (request.getParameter("context")) ;
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            c.setFdid((String) (((JSONObject) array.get(0)).get("param1")));  
            JSONObject json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .initOrgInfoEdit(c); 
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 新建角色
     */
    public String createNewRole(){
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/xml;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str);             
            for(int i=0;i<array.size();i++){
                if("role_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRole_name(((JSONObject)array.get(i)).get("value").toString());
                }else if("remark".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                } 
            }  
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id()); 
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());     
            JSONObject json = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .createNewRole(c); 
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 赋权给角色
     */
    public String giveThisRoleFuntions()   {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));
            SystemCommonModel c = new SystemCommonModel();
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            c.setRole_id(param1);
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param2")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .giveThisRoleFuntions(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    
    /**
     * 根据角色查询功能权级信息
     */
    public String qryRoleRelatFuncInfoList()  {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
            SystemCommonModel c = new SystemCommonModel(); 
            c.setStart_num( (String) (((JSONObject) array.get(0)).get("param2")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRecord_tatol((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRole_id(param1); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .qryRoleRelatFuncInfoList(c); 
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 保存权级信息
     * @author yangh
     */
    public String saveRoleFuncLevelInfo()  {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
            SystemCommonModel c = new SystemCommonModel();  
            c.setTemp_str1(param1); 
            c.setItem_type((String) (((JSONObject) array.get(0)).get("param2")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .saveRoleFuncLevelInfo(c);
            log.info((objItem.toJSONString()));
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 新建用户资料
     */
    public String createNewSysUser()  throws Exception {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str); 
            for(int i=0;i<array.size();i++){
                if("createUser_user_cd".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setStaff_number(((JSONObject)array.get(i)).get("value").toString());
                }else if("createUser_user_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setStaff_name(((JSONObject)array.get(i)).get("value").toString());
                }else if("orgLookup.id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setSelect_company_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("createUser_phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setStaff_phone(((JSONObject)array.get(i)).get("value").toString());
                }else if("createUser_address".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setSelect_company_address(((JSONObject)array.get(i)).get("value").toString());
                }else if("createUser_role_cd".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRole_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("createUser_type_cd".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str2(((JSONObject)array.get(i)).get("value").toString());
                } 
            }  
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .createNewSysUser(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return null ;
    }
    /**
     * qryAllUserInfoList
     * 用户列表
     */
    public String qryAllUserInfoList()   throws Exception {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
            SystemCommonModel c = new SystemCommonModel();  
            c.setFdid(getUUID());
            c.setTemp_str1(param1);  
            c.setSelect_company_id( (String) (((JSONObject) array.get(0)).get("param2")));
            c.setRole_id( (String) (((JSONObject) array.get(0)).get("param3")));
            c.setStart_num( (String) (((JSONObject) array.get(0)).get("param4")));
            c.setEnd_num( (String) (((JSONObject) array.get(0)).get("param5")));
            c.setRecord_tatol( (String) (((JSONObject) array.get(0)).get("param6")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .qryAllUserInfoList(c); 
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    
    /**
     * 删除用户
     */
    public String deleteUserInfo()  throws Exception {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
            SystemCommonModel c = new SystemCommonModel();  
            c.setFdid(getUUID());
            c.setTemp_str1(param1);   
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .deleteUserInfo(c); 
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 获取单个用户资料
     */
    public String getThisUserInfo()  throws Exception {
        try {
            HttpSession session = request.getSession(false);  
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
            SystemCommonModel c = new SystemCommonModel();  
            c.setFdid(param1);   
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
            JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                    .getThisUserInfo(c); 
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * updateUserInfo
     * 用户资料编辑
     */
    public String updateUserInfo()  throws Exception {
        HttpSession session = request.getSession(false);  
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
        SystemCommonModel c = new SystemCommonModel();  
        c.setFdid(param1);   
        c.setStaff_number((String) (((JSONObject) array.get(0)).get("param2")));
        c.setStaff_name((String) (((JSONObject) array.get(0)).get("param3")));
        c.setSelect_company_id((String) (((JSONObject) array.get(0)).get("param4")));
        c.setStaff_phone((String) (((JSONObject) array.get(0)).get("param5")));
        c.setSelect_company_address((String) (((JSONObject) array.get(0)).get("param6")));
        c.setRole_id((String) (((JSONObject) array.get(0)).get("param7")));
        c.setCreate_time((String) (((JSONObject) array.get(0)).get("param8")));
        c.setTemp_str2((String) (((JSONObject) array.get(0)).get("param9")));
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .updateUserInfo(c);
        log.info((objItem.toJSONString()));
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
    }
    /**
     * 根据外键查询关联的附件
     */
    public String getFileListByForeginId()   throws Exception {
        HttpSession session = request.getSession(false);  
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
        SystemCommonModel c = new SystemCommonModel();  
        c.setTemp_str1(param1.trim());  
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .getFileListByForeginId(c);
        log.info((objItem.toJSONString()));
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
    }
    /**
     * 附件与外键进行关联
     */
    public String createForeignAndAttachmentRelation() throws Exception {
        HttpSession session = request.getSession(false);  
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
        SystemCommonModel c = new SystemCommonModel();  
        c.setFdid(getUUID());
        c.setTemp_str1(param1);  
        c.setTemp_str2( (String) (((JSONObject) array.get(0)).get("param2")));
        c.setTemp_str3( (String) (((JSONObject) array.get(0)).get("param3")));
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .createForeignAndAttachmentRelation(c); 
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
        
    }
    /**
     * 删除附件
     */
    public String deleteAttachmentFile()  throws Exception {
        HttpSession session = request.getSession(false);  
        response.setContentType("text/plain;charset=UTF-8");
        Writer wr = response.getWriter();
        String str =  (request.getParameter("context")) ; 
        JSONObject obj = (JSONObject) JSONValue.parse(str);
        JSONArray array = (JSONArray) obj.get("content");
        String param1 = (String) (((JSONObject) array.get(0)).get("param1"));       
        SystemCommonModel c = new SystemCommonModel();   
        c.setFdid(param1);   
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                .getStaff_id());
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName()); 
        JSONObject objItem = ((SystemAdminServiceImpl) getBean("systemAdminServiceImpl"))
                .deleteAttachmentFile(c);
        log.info((objItem.toJSONString()));
        wr.write(objItem.toJSONString());
        wr.close();
        
        return null ;
        
    }
}
