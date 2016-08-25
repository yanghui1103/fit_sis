package com.bw.fit.sis.actions;

import static com.bw.fit.common.utils.PubFun.buildJSON;

import com.bw.fit.sis.service.impl.SisServiceImpl;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;

import com.bw.fit.common.models.LoginUser;
import com.bw.fit.common.models.SystemCommonModel;
import com.bw.fit.common.utils.MD5;
import com.bw.fit.system.actions.BaseAction;
import com.bw.fit.system.service.impl.SystemAdminServiceImpl;
@Controller
public class SisAdminAction extends BaseAction{
    private  HttpServletResponse response = ServletActionContext.getResponse() ;
    private  HttpServletRequest request = ServletActionContext.getRequest(); 
    private  Log log = LogFactory.getLog(this.getClass()); 
    private ServletContext servletContext = request.getServletContext(); 
    private HttpSession session = request.getSession(false);    
    
    /***%
     * createSubCycle
     * 新建申报周期
     */
    public String createSubCycle(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str); 
            for(int i=0;i<array.size();i++){
                if("cycle_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str2(((JSONObject)array.get(i)).get("value").toString());
                }else if("orgLookup.id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                }else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setStart_date(((JSONObject)array.get(i)).get("value").toString());
                }else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setEnd_date(((JSONObject)array.get(i)).get("value").toString());
                }
            }   
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());   
            JSONObject obj = ((SisServiceImpl)getBean("sisServiceImpl")).createSubCycle(c);
            wr.write(obj.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  
        return null ;
    }
    /****
     * qryAllCycleInfoList
     * 检索申报周期列表
     */
    public String qryAllCycleInfoList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1"));
            String param2 = (String) (((JSONObject) array.get(0)).get("param2"));
            SystemCommonModel c = new SystemCommonModel();
            c.setCycle_name(param1);c.setTemp_str1(param2);
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param3")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param4")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param5")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .qryAllCycleInfoList(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 删除申报周期记录
     */
    public String deleteSubCycle(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setTemp_str1( (param1));
            List<String> list  = new ArrayList<String>();
            String[] array1 = c.getTemp_str1().split(",");
            for(int i=0;i<array1.length;i++){
                list.add(array1[i]);
            }
            c.setTemp_list(list);
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .deleteSubCycle(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 开启周期
     */
    public String openOrCloseSubCycle(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setTemp_str1( (param1));
            List<String> list  = new ArrayList<String>();
            String[] array1 = c.getTemp_str1().split(",");
            for(int i=0;i<array1.length;i++){
                list.add(array1[i]);
            }
            c.setTemp_list(list);
            c.setTemp_str2((String) (((JSONObject) array.get(0)).get("param2")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser"))
                    .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .openOrCloseSubCycle(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * getRoleDeSubType
     * 得到该角色拥有的补贴类型
     * 
     */
    public String getRoleDeSubType(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setRole_id( (param1)); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getRoleDeSubType(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 角色与类型关联
     */
    public String createRole2SubType(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str); 
            List<String> list = new ArrayList<String>();
            for(int i=0;i<array.size();i++){
                if("role_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRole_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("type_cd".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    list.add(((JSONObject)array.get(i)).get("value").toString());
                } 
            }   
            c.setTemp_list(list);
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());  
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .createRole2SubType(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /**
     * 根据身份证号码
     * 查询出申报人信息
     */
    public String getPsnBaseInfo(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel(); 
            c.setCard_id( (param1)); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getPsnBaseInfo(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return null ;
    }
    /***
     * 修改申报人基础信息 
     */
    public String changePsnBaseInfo(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            SystemCommonModel c = new SystemCommonModel();
            String str =  (request.getParameter("context")) ; 
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
                } else if("gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }  else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }  else if("first_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFirst_time(((JSONObject)array.get(i)).get("value").toString());
                }  else if("state".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_state(((JSONObject)array.get(i)).get("value").toString());
                } 
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());  
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .changePsnBaseInfo(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
}
