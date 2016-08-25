package com.bw.fit.sis.actions;

import static com.bw.fit.common.utils.PubFun.buildJSON;

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
    /****
     * createPersonRptRecord
     * 单笔，录入申报记录
     */
    public String createPersonRptRecord(){ 
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
        c.setStaff_id(((LoginUser) session.getAttribute("LoginUser")).getStaff_id()); 
        try {
            String f_id = PubFun.getUUID()  ;
            Writer wr = response.getWriter();
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
}
