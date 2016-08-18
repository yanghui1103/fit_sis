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
            HttpSession session = request.getSession(false); 
            if(session==null || (LoginUser)session.getAttribute("LoginUser")==null  ){ 
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
        response.setContentType("text/json;charset=UTF-8");
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
}
