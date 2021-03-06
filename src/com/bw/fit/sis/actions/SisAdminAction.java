package com.bw.fit.sis.actions;

import static com.bw.fit.common.utils.PubFun.buildJSON;
import static com.bw.fit.common.utils.PubFun.getUUID;

import com.bw.fit.sis.service.impl.SisServiceImpl;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import com.bw.fit.common.utils.PubFun;
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
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
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
                } else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
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
    /****
     * createPersonRptRecord
     * 单笔，录入申报记录
     * @throws Exception 
     */
    public String createPersonRptRecord() throws Exception{ 
        JSONObject info = new JSONObject();
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
            } else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
            } else if("origin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
            } else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
            } else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
            } else if("pay_start".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
            } else if("pay_end".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
            } else if("person_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_type(((JSONObject)array.get(i)).get("value").toString());
            } else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
            } else if("sub_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
            }  else if("first_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFirst_time(((JSONObject)array.get(i)).get("value").toString());
            }  else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
            } 
        }    
        /**
         * start:这个人是否有正在申报中的记录,如果有就跳出去
         */
        // c.setSql("sisAdminDAO.checkPsnRpting");
        JSONObject info3 = new JSONObject();
        info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                .checkPsnRpting(c);
        if(!"2".equals(info3.get("res"))){
            wr.write(info3.toJSONString());
            wr.close();
            return null ;
        }
        info3 = new JSONObject();
        info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                .getTheCheckResaultApply(c);
        if(!"2".equals(info3.get("res"))){
            wr.write(info3.toJSONString());
            wr.close();
            return null ;
        }
        /*
         * end
         */
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
        try {
            String f_id = PubFun.getUUID()  ;
            TaskService taskService = (TaskService)getBean("taskService");    
            RuntimeService runtimeService = (RuntimeService)getBean("runtimeService");   
            Map<String, Object> p = new HashMap<String, Object>();    
            // 开始流程  
            p.put("flow_id", f_id);
            runtimeService.startProcessInstanceByKey("myProcess", p);
            List<Task> tasks = taskService.createTaskQuery().taskAssignee("role1").list();  
            if(tasks.size()<1){
                info.put("res", "1");
                info.put("msg","录入失败，请联系系统管理员");
                wr.write(info.toJSONString());
                wr.close();
            }            
            for (Task task : tasks) {
                if ("node1".equals(task.getTaskDefinitionKey())) {                
                    // 设置填报人单位编码记录在节点  
                    taskService.setVariable(task.getId(), "fdid", f_id ); 
                    taskService.setVariable(task.getId(), "card_id",  c.getCard_id()  );   
                    taskService.setVariable(task.getId(), "gender",  c.getPerson_gender()  );    
                    taskService.setVariable(task.getId(), "nation",  c.getPerson_nation()  );   
                    taskService.setVariable(task.getId(), "person_name", c.getPerson_name()  );   
                    taskService.setVariable(task.getId(), "origin",  c.getPerson_orgin()  ); 
                    taskService.setVariable(task.getId(), "unit_type",  c.getPerson_unit_type()  ); 
                    taskService.setVariable(task.getId(), "unit_name", c.getPerson_unit()  ); 
                    taskService.setVariable(task.getId(), "pay_start",  c.getRpt_start()  ); 
                    taskService.setVariable(task.getId(), "pay_end", c.getRpt_end() ); 
                    taskService.setVariable(task.getId(), "person_type",  c.getPerson_type() ); 
                    taskService.setVariable(task.getId(), "sub_cycle",  c.getRpt_cycle()  ); 
                    taskService.setVariable(task.getId(), "sub_type",  c.getRpt_type()  ); 
                    taskService.setVariable(task.getId(), "first_date", c.getFirst_time()  ); 
                    taskService.setVariable(task.getId(), "person_phone",  c.getPerson_phone() );                     
                    taskService.setVariable(task.getId(), "creator", c.getStaff_id()  ); 
                    taskService.setVariable(task.getId(), "create_time",  PubFun.getSysDate()  );                     
                    taskService.setVariable(task.getId(), "pass1",  "2"  );  // 录入，或修改继续下一审核环节                                        
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                    info.put("res", "2");
                    info.put("msg","录入成功");
                    wr.write(info.toJSONString());
                    wr.close();
                }  
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
        }finally{
            return null ;
        } 
    }
    /***
     * 修改申报记录
     */
    public String updatePersonRptRecord(){
        JSONObject info = new JSONObject();
        response.setContentType("text/plain;charset=UTF-8");
        SystemCommonModel c = new SystemCommonModel();
        String str =  (request.getParameter("context")) ; 
        JSONArray array  = (JSONArray) JSONValue.parse(str);  
        for(int i=0;i<array.size();i++){
            if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
            }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
            }else if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFdid(((JSONObject)array.get(i)).get("value").toString());
            }  else if("gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
            }  else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
            }  else if("first_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFirst_time(((JSONObject)array.get(i)).get("value").toString());
            }  else if("state".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_state(((JSONObject)array.get(i)).get("value").toString());
            } else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
            } else if("origin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
            } else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
            } else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
            } else if("pay_start".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
            } else if("pay_end".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
            } else if("person_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_type(((JSONObject)array.get(i)).get("value").toString());
            } else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
            } else if("sub_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
            }  else if("first_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFirst_time(((JSONObject)array.get(i)).get("value").toString());
            }  else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
            } 
        }    
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
        try {
            String f_id = PubFun.getUUID()  ;
            Writer wr = response.getWriter();
            TaskService taskService = (TaskService)getBean("taskService");    
            List<Task> tasks = taskService.createTaskQuery()  .processVariableValueEquals("flow_id", c.getFdid()) .taskAssignee("role1").list();  
            if(tasks.size()<1){
                info.put("res", "1");
                info.put("msg","修改失败，或记录已不存在");
                wr.write(info.toJSONString());
                wr.close();
            }            
            for (Task task : tasks) {
                if ("node1".equals(task.getTaskDefinitionKey())) {                
                    // 设置填报人单位编码记录在节点  
                    taskService.setVariable(task.getId(), "fdid", f_id ); 
                    taskService.setVariable(task.getId(), "card_id",  c.getCard_id()  );   
                    taskService.setVariable(task.getId(), "gender",  c.getPerson_gender()  );    
                    taskService.setVariable(task.getId(), "nation",  c.getPerson_nation()  );   
                    taskService.setVariable(task.getId(), "person_name", c.getPerson_name()  );   
                    taskService.setVariable(task.getId(), "origin",  c.getPerson_orgin()  ); 
                    taskService.setVariable(task.getId(), "unit_type",  c.getPerson_unit_type()  ); 
                    taskService.setVariable(task.getId(), "unit_name", c.getPerson_unit()  ); 
                    taskService.setVariable(task.getId(), "pay_start",  c.getRpt_start()  ); 
                    taskService.setVariable(task.getId(), "pay_end", c.getRpt_end() ); 
                    taskService.setVariable(task.getId(), "person_type",  c.getPerson_type() ); 
                    taskService.setVariable(task.getId(), "sub_cycle",  c.getRpt_cycle()  ); 
                    taskService.setVariable(task.getId(), "sub_type",  c.getRpt_type()  ); 
                    taskService.setVariable(task.getId(), "first_date", c.getFirst_time()  ); 
                    taskService.setVariable(task.getId(), "person_phone",  c.getPerson_phone() );                     
                    taskService.setVariable(task.getId(), "creator", c.getStaff_id()  ); 
                    taskService.setVariable(task.getId(), "create_time",  PubFun.getSysDate()  );                     
                    taskService.setVariable(task.getId(), "pass1",  "2"  );  // 修改继续下一审核环节                                        
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                    info.put("res", "2");
                    info.put("msg","修改成功");
                    wr.write(info.toJSONString());
                    wr.close();
                }  
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
        }finally{
            return null ;
        } 
    }
    /***
     * 初审
     */
    public String firstRptAudit(){
        JSONObject info = new JSONObject();
        response.setContentType("text/plain;charset=UTF-8");
        SystemCommonModel c = new SystemCommonModel();
        String str =  (request.getParameter("context")) ; 
        JSONArray array  = (JSONArray) JSONValue.parse(str);  
        for(int i=0;i<array.size();i++){
            if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFdid(((JSONObject)array.get(i)).get("value").toString());
            }else if("pass2".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
            }else if("pass2_reason".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str2(((JSONObject)array.get(i)).get("value").toString());
            }
        }
        try {
            Writer wr = response.getWriter();
            TaskService taskService = (TaskService)getBean("taskService");    
            List<Task> tasks = taskService.createTaskQuery()
                    .processVariableValueEquals("flow_id", c.getFdid())  .taskAssignee("role2").list();  
            if(tasks.size()<1){
                info.put("res", "1");
                info.put("msg","初审失败，审批记录已不存在");
                wr.write(info.toJSONString());
                wr.close();
            }            
            for (Task task : tasks) {
                if ("node2".equals(task.getTaskDefinitionKey())) {                
                    // 设置填报人单位编码记录在节点                
                    taskService.setVariable(task.getId(), "pass2", c.getTemp_str1()  );  // 录入，或修改继续下一审核环节     
                    taskService.setVariable(task.getId(), "pass2_reason", c.getTemp_str2()  );                                     
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                    info.put("res", "2");
                    info.put("msg","初审成功");
                    wr.write(info.toJSONString());
                    wr.close();
                }  
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
        }finally{
            return null ;
        } 
    }
    /***
     * 复审
     */
    public String secondRptAudit(){
        JSONObject info = new JSONObject();
        response.setContentType("text/plain;charset=UTF-8");
        SystemCommonModel c = new SystemCommonModel();
        String str =  (request.getParameter("context")) ; 
        JSONArray array  = (JSONArray) JSONValue.parse(str);  
        for(int i=0;i<array.size();i++){
            if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFdid(((JSONObject)array.get(i)).get("value").toString());
            }else if("pass3".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
            }else if("pass3_reason".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setTemp_str2(((JSONObject)array.get(i)).get("value").toString());
            }
        }
        try {
            Writer wr = response.getWriter();
            TaskService taskService = (TaskService)getBean("taskService");    
            List<Task> tasks = taskService.createTaskQuery() .processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("role3").list();  
            if(tasks.size()<1){
                info.put("res", "1");
                info.put("msg","复审失败，审批记录已不存在");
                wr.write(info.toJSONString());
                wr.close();
            }            
            for (Task task : tasks) {
                if ("node3".equals(task.getTaskDefinitionKey())) {                
                    // 设置填报人单位编码记录在节点             
                    taskService.setVariable(task.getId(), "pass3", c.getTemp_str1()  );  // 录入，或修改继续下一审核环节     
                    taskService.setVariable(task.getId(), "pass3_reason", c.getTemp_str2()  );                                     
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                    info.put("res", "2");
                    info.put("msg","复审成功");
                    wr.write(info.toJSONString());
                    wr.close();
                }  
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
        }finally{
            return null ;
        } 
    }
    /****
     * 办事点作废申报数据
     */
    public String deleteRptRecord(){
        JSONObject info = new JSONObject();
        response.setContentType("text/plain;charset=UTF-8");
        SystemCommonModel c = new SystemCommonModel();
        String str =  (request.getParameter("context")) ; 
        JSONArray array  = (JSONArray) JSONValue.parse(str);  
        for(int i=0;i<array.size();i++){
            if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                c.setFdid(((JSONObject)array.get(i)).get("value").toString());
            } 
        }    
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id());
        c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());  
        try {
            Writer wr = response.getWriter();
            TaskService taskService = (TaskService)getBean("taskService");    
            List<Task> tasks = taskService.createTaskQuery() .processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("role1").list();  
            if(tasks.size()<1){
                info.put("res", "1");
                info.put("msg","作废失败，记录已不存在");
                wr.write(info.toJSONString());
                wr.close();
            }            
            for (Task task : tasks) {
                if ("node1".equals(task.getTaskDefinitionKey())) {                
                    // 设置填报人单位编码记录在节点                    
                    taskService.setVariable(task.getId(), "pass1",  "1"  );  // 作废                                     
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                    info.put("res", "2");
                    info.put("msg","作废成功");
                    wr.write(info.toJSONString());
                    wr.close();
                }  
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(); 
        }finally{
            return null ;
        } 
    }
    /***
     * 根据别的信息得出码表信息
     * 例如 根据工号id，查询出拥有的申报类型，
     * 或根据工号查询他所在机构拥有的申报周期
     */
    public String getCustomValueByOther(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            c.setTemp_str1(param1);
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getCustomValueByOther(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
        return null ;
    }
    /***
     * createOldPersonRptRecond
     * 创建旧申报记录
     */
    public String createOldPersonRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
                } else if("gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }    else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
                }else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("orgin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
                } else if("first_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFirst_time(((JSONObject)array.get(i)).get("value").toString());
                } else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .createOldPersonRptRecond(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        return null ;
    }
    /***
     * （附件上传）
     *  新增一个申报人的申报记录
     */
    public String createThisPersonRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                } else if("person_gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }    else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
                }else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("orgin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
                }  else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }   else if("flow_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    

            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());

            /**
             * start:这个人是否有正在申报中的记录,如果有就跳出去
             */
            // c.setSql("sisAdminDAO.checkPsnRpting");
            JSONObject info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .checkPsnRpting(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;
            } 
            info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getTheCheckResaultApply(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;  
            } 
            /*
             * end
             */
            
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getExistsPsn(c);
            RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");   
            FormService formService = (FormService) getBean("formService");   
            TaskService taskService = (TaskService) getBean("taskService");  
            c.setCreate_time(PubFun.getSysDate());
            c.setFirst_time(PubFun.getTruncSysDate());
            if("2".equals(objItem.get("res").toString())){ // 申报人存在，就只保存申报记录
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). luruNewRptFlow(c,runtimeService, formService, taskService);
            }else{// 申报人不存在，就把申报人信息和其申报信息都记录
                c.setTemp_str1(PubFun.getTruncSysDate());
                c.setCreate_time(PubFun.getSysDate());
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). luruNewPsnAndHisRptFlow(c,runtimeService, formService, taskService);
            }
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    /****
     * 保存为暂存状态
     * @return
     */
    public String createThisTmpRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                } else if("person_gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }    else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
                }else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("orgin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
                }  else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }   else if("flow_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    

            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());

            /**
             * start:这个人是否有正在申报中的记录,如果有就跳出去
             */
            // c.setSql("sisAdminDAO.checkPsnRpting");
            JSONObject info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .checkPsnRpting(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;
            } 
            info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getTheCheckResaultApply(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;  
            } 
            /*
             * end
             */
            
            JSONObject objItem = new JSONObject();
            objItem= ((SisServiceImpl) getBean("sisServiceImpl"))
                    .createThisTmpRptRecond(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    
    public String deleteTempRecord(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            String[] array2 = param1.split(",");
            c.setFdid(array2[0]); 
            c.setCard_id(array2[1]); 
            
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).deleteTempRecord(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    

    public String qryTempRecordList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5")));
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setTemp_str3((String) (((JSONObject) array.get(0)).get("param9")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryTempRecordList(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    
    
    /***
     * 查询该角色，所选中的组织
     * 下所有的待审核记录列表
     */
    public String qryWaitCheckRecordList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5")));
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setTemp_str3((String) (((JSONObject) array.get(0)).get("param9")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryWaitCheckRecordList(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    

    public String qryEasyWaitCheckRecordList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5")));
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setTemp_str3((String) (((JSONObject) array.get(0)).get("param9")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryEasyWaitCheckRecordList(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 查询该角色，所选中的组织
     * 下所有的财务拨款记录列表
     */
    public String qryWaitCheckRecordList_finance(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5")));
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setTemp_str3((String) (((JSONObject) array.get(0)).get("param9")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryWaitCheckRecordList_finance(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    
    public String getTempPsnInfo(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            String[] array2 = param1.split(",");
            c.setFdid(array2[0]);  
            c.setCard_id(array2[1]);
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getThisTmpCheckInfoAll(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    
    /***
     * 根据流程id（记录id）获取这个
     * 人这条申报记录的全部信息
     */
    public String getThisCheckInfoAll(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();  
            String[] array2 = param1.split(",");
            c.setFdid(array2[0]); 
            c.setTemp_str1(array2[1]);
            c.setTemp_str2(array2[2]);
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getThisCheckInfoAll(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * 审核确认
     */
    public String checkRpt(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                }else if("check_result".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCheck_result(((JSONObject)array.get(i)).get("value").toString());
                } else if("check_info".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCheck_info(((JSONObject)array.get(i)).get("value").toString());
                }    else if("pass_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = new JSONObject();
            RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");   
            FormService formService = (FormService) getBean("formService");   
            TaskService taskService = (TaskService) getBean("taskService");  
            c.setCreate_time(PubFun.getSysDate());
            c.setFirst_time(PubFun.getTruncSysDate()); 
            if("1".equals(c.getTemp_str1())){
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). checkRpt(c,runtimeService, formService, taskService);
            }else{ //复审
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). checkRptSecond(c,runtimeService, formService, taskService);
            }             
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    /***
     * 修改申报记录
     * 办事点
     */
    public String updateRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString());
                } else if("gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }    else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
                }else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("orgin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
                }  else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }   else if("flow_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    
            /**
             * start:这个人是否有正在申报中的记录,如果有就跳出去
             */ 
            JSONObject info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getTheCheckResaultApply(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;  
            } 
            /*
             * end
             */
 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getExistsPsn(c);
            RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");   
            FormService formService = (FormService) getBean("formService");   
            TaskService taskService = (TaskService) getBean("taskService");  
            c.setCreate_time(PubFun.getSysDate());
            c.setFirst_time(PubFun.getTruncSysDate()); 
            // 只修改申报数据
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). updateRptRecond(c,runtimeService, formService, taskService);
             
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    /***
     * 作废这条申报记录
     */
    public String deleteRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                } 
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = new JSONObject();
            RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");   
            FormService formService = (FormService) getBean("formService");   
            TaskService taskService = (TaskService) getBean("taskService");  
            c.setCreate_time(PubFun.getSysDate());
            c.setFirst_time(PubFun.getTruncSysDate());  
                objItem = ((SisServiceImpl) getBean("sisServiceImpl")). deleteRptRecond(c,runtimeService, formService, taskService);
                  
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    /***
     * 全量拨款
     */
    public String grantFinanceToPsn(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();   
            
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5"))); 
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords());
            c.setTemp_str3((String) (((JSONObject) array.get(0)).get("param9")));
            c.setEnd_num("-9");
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");   
            FormService formService = (FormService) getBean("formService");   
            TaskService taskService = (TaskService) getBean("taskService");  
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).grantFinanceToPsn(c,runtimeService, formService, taskService);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * qryRptedRecordList
     * 查询已经归档的且补贴的记录
     */
    public String qryRptedRecordList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5"))); 
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords()); 
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryRptedRecordList(c);
            wr.write(objItem.toJSONString());
            wr.close(); 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /***
     * qryRptingRecordList
     * 查询正在申报的人员
     */
    public String qryRptingRecordList(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setPerson_name(param1);
            c.setCard_id((String) (((JSONObject) array.get(0)).get("param2")));
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param3")));
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param4")));
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param5"))); 
            c.setStart_num((String) (((JSONObject) array.get(0)).get("param6")));
            c.setEnd_num((String) (((JSONObject) array.get(0)).get("param7")));
            c.setTotal_reords((String) (((JSONObject) array.get(0)).get("param8")));
            c.setRecord_tatol(c.getTotal_reords()); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).qryRptingRecordList(c);
            wr.write(objItem.toJSONString());
            wr.close(); 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /****
     * 查询这个人的
     * 申领概况
     */
    public String getPersonRptedInfo(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setCard_id(param1); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getPersonRptedInfo(c);
            wr.write(objItem.toJSONString());
            wr.close(); 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null ;
    }
    /**
     * 生成补贴报表
     */
    public String rptOfficeApplyedReport(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setSelect_company_id(param1);            
            c.setRpt_type((String) (((JSONObject) array.get(0)).get("param2")));            
            c.setRpt_cycle((String) (((JSONObject) array.get(0)).get("param3"))); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).rptOfficeApplyedReport(c, ServletActionContext.getServletContext().getRealPath("/uploadfiles"));
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return null ;
    }
    /***
     * 获取流程ID审核历史
     */
    public String getCheckHistory(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setFlow_id(param1);             
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getCheckHistory(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return null ;
    }
    /***
     * getAllRptingSituation
     * 获取申报情况图
     */
    public String getAllRptingSituation(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setRpt_cycle(param1);             
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getAllRptingSituation(c);
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return null ; 
    }
    /**
     * 高拍仪上传图
     */
    public String uploadNTPhotos(){
        String path = "" ;
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setFlow_id(param1);
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param2")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).uploadNTPhotos(c,request,path,(String) (((JSONObject) array.get(0)).get("param3")));
           String  new_path = ServletActionContext.getServletContext()   
                       .getRealPath("\\uploadfiles\\"); 
            objItem = ((SisServiceImpl) getBean("sisServiceImpl")).uploadNTPhotos2(c,request,path,new_path);
            
            
            // 建立关联关系
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }             
        return null ;
    }
    /**
     * uploadNTPhotosNew
     * 根据文件夹找出所有相片上传
     */
    public String uploadNTPhotosNew(){
        String path = "" ;
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setFlow_id(param1);
            c.setTemp_str1((String) (((JSONObject) array.get(0)).get("param2")));
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).uploadNTPhotos(c,request,path,(String) (((JSONObject) array.get(0)).get("param3")));
           String  new_path = ServletActionContext.getServletContext()   
                       .getRealPath("\\uploadfiles\\"); 
            objItem = ((SisServiceImpl) getBean("sisServiceImpl")).uploadNTPhotos2(c,request,path,new_path);
            
            
            // 建立关联关系
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }             
        return null ;
    }
    /**
     * 
     */
    public String getThisApplyInfoByFlowId(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            JSONObject obj = (JSONObject) JSONValue.parse(str);
            JSONArray array = (JSONArray) obj.get("content");
            String param1 = (String) (((JSONObject) array.get(0)).get("param1")); 
            SystemCommonModel c = new SystemCommonModel();               
            c.setFlow_id(param1); 
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")) .getStaff_id());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject objItem = ((SisServiceImpl) getBean("sisServiceImpl")).getThisApplyInfoByFlowId(c);
            // 建立关联关系
            wr.write(objItem.toJSONString());
            wr.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }             
        return null ;
    }
    /***
     * 简化版，录入
     */
    public String createEasyRptRecond(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }else if("person_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_name(((JSONObject)array.get(i)).get("value").toString()); 
                } else if("person_gender".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_gender(((JSONObject)array.get(i)).get("value").toString());
                }    else if("nation".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_nation(((JSONObject)array.get(i)).get("value").toString());
                }else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("orgin".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_orgin(((JSONObject)array.get(i)).get("value").toString());
                }  else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }   
            }    

            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            c.setCreate_time(PubFun.getSysDate());
            c.setFirst_time( PubFun.getTruncSysDate());
            /**
             * start:这个人是否有正在申报中的记录,如果有就跳出去
             */ 
            JSONObject info3 = new JSONObject();
            info3  = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .checkPsnRpting(c);
            if(!"2".equals(info3.get("res"))){
                wr.write(info3.toJSONString());
                wr.close();
                return null ;  
            } 
            /*
             * end
             */
            JSONObject json = new JSONObject();
            json = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .createEasyRptRecond(c);
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /****
     * 获取订单详情
     */
    public String getRptDetail(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONObject j  = (JSONObject) JSONValue.parse(str);  
            JSONArray array = (JSONArray)j.get("content");
            c.setFdid(((JSONObject)array.get(0)).get("param1").toString());
            JSONObject json = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .getRptDetail(c);
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null ;
    }
    /**
     * 简化版审核
     */
    public String checkEasyRpt(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                }else if("check_result".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCheck_result(((JSONObject)array.get(i)).get("value").toString());
                } else if("check_info".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCheck_info(((JSONObject)array.get(i)).get("value").toString());
                }    else if("pass_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setTemp_str1(((JSONObject)array.get(i)).get("value").toString());
                }  
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject json  = new JSONObject();

            json = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .checkEasyRpt(c);
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
    }
    /***
     * 删除简化版记录
     */
    public String deleteEasyRpt(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                } 
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject json  = new JSONObject();

            json = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .deleteEasyRpt(c);
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                
        
        return null ;
        
        
    }
    /***
     * 简化版修改
     * updateEasyRpt
     */

    public String updateEasyRpt(){
        try {
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            String str =  (request.getParameter("context")) ; 
            SystemCommonModel c = new SystemCommonModel();  
            JSONArray array  = (JSONArray) JSONValue.parse(str);  
            for(int i=0;i<array.size();i++){
                if("fdid".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setFdid(((JSONObject)array.get(i)).get("value").toString());
                } else if("card_id".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setCard_id(((JSONObject)array.get(i)).get("value").toString());
                }  else if("phone".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_phone(((JSONObject)array.get(i)).get("value").toString());
                }   else if("sub_cycle".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_cycle(((JSONObject)array.get(i)).get("value").toString());
                } else if("rpt_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_type(((JSONObject)array.get(i)).get("value").toString());
                } else if("start_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_start(((JSONObject)array.get(i)).get("value").toString());
                } else if("end_date".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setRpt_end(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_type".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit_type(((JSONObject)array.get(i)).get("value").toString());
                }   else if("unit_name".equalsIgnoreCase(((JSONObject)array.get(i)).get("name").toString())){
                    c.setPerson_unit(((JSONObject)array.get(i)).get("value").toString());
                }   
            }    
            c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
            c.setStaff_company_id(((LoginUser) session.getAttribute("LoginUser")).getOrg_cd());
            c.setAction_name(Thread.currentThread().getStackTrace()[1].getMethodName());
            JSONObject json  = new JSONObject();

            json = ((SisServiceImpl) getBean("sisServiceImpl"))
                    .updateEastRpt(c);
            wr.write(json.toJSONString());
            wr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }                        
        return null ;
        
        
    }
}
