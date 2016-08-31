package com.bw.fit.sis.service.impl; 
import static com.bw.fit.common.utils.PubFun.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.bw.fit.common.models.SystemCommonModel;
import com.bw.fit.sis.dao.utils.SisMybatisDaoUtil;
import com.bw.fit.sis.service.SisService;
import com.bw.fit.system.dao.utils.SystemMybatisDaoUtil;
import com.bw.fit.system.service.impl.SystemAdminServiceImpl;
@Transactional
@Service
public class SisServiceImpl implements SisService {
    private  Log log = LogFactory.getLog(SisServiceImpl.class); 
    @Autowired
    private SisMybatisDaoUtil sisMybatisDaoUtil;

    /***
     * 新建申报周期
     */
    @Override
    public JSONObject createSubCycle(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(sdf.parse(c.getStart_date()).after(sdf.parse(c.getEnd_date()))||c.getStart_date().equals(c.getEnd_date())){
                info.put("res", "1");
                info.put("msg", "开始日期不得晚于或等于结束日期"); 
                return info ;
            }
            c.setCreate_time(getSysDate());
            String[] array = c.getTemp_str1().split(",");
            for(int i=0;i<array.length;i++){
                c.setFdid(getUUID());
                c.setTemp_str3(array[i]);
                // 检查是否日期重复
                c.setSql("sisAdminDAO.checkCompanyExisteSubCycle"); 
                List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(c.getSql(), c);  
                Calendar calendar_start = Calendar.getInstance(); 
                Calendar calendar_end = Calendar.getInstance(); 
                calendar_start.setTime(sdf.parse(c.getStart_date()));
                calendar_end.setTime(sdf.parse(c.getEnd_date()));
                List bList = getAllDateBetweenDays(calendar_start,calendar_end );
                for(int j=0;j<list.size();j++){
                    calendar_start = Calendar.getInstance(); 
                    calendar_end = Calendar.getInstance(); 
                    calendar_start.setTime(sdf.parse(list.get(j).getStart_date()));
                    calendar_end.setTime(sdf.parse(list.get(j).getEnd_date()));
                    List aList = getAllDateBetweenDays(calendar_start,calendar_end );
                    for(int i1 = 0 ;i1<bList.size();i1++){
                        for(int i2=0;i2<aList.size();i2++){
                            if(bList.get(i1).toString().equals(aList.get(i2).toString())){
                                info.put("res", "1");
                                info.put("msg", "机构存在交叉日期");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                                return info ;
                            }
                        }
                    }
                }
                c.setSql("sisAdminDAO.createSubCycle"); 
                info =  sisMybatisDaoUtil.sysUpdateData( 
                        c.getSql(), c);
                if("1".equals(info.get("res").toString())){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                    return info ;
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;
    }

    @Override
    public JSONObject qryAllCycleInfoList(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
            List<String> ls = new ArrayList<String>();
            String[] arr = c.getTemp_str1().split(",");
            for(int i=0;i<arr.length;i++){
                ls.add(arr[i]);
            }
            c.setTemp_list(ls);
            c.setSql("sisAdminDAO.qryAllCycleInfoList");
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(
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
                jsonObjArr.put("fdid", (list.get(i)).getFdid());
                jsonObjArr.put("cycle_name", (list.get(i)).getCycle_name());
                jsonObjArr.put("org_name", (list.get(i)).getSelect_company_name());
                jsonObjArr.put("start_date", (list.get(i)).getStart_date());
                jsonObjArr.put("end_date", (list.get(i)).getEnd_date());
                jsonObjArr.put("create_time", (list.get(i)).getCreate_time());
                jsonObjArr.put("creator", (list.get(i)).getTemp_str1()); 
                jsonObjArr.put("state", (list.get(i)).getTemp_str3()); 
                array.add(jsonObjArr);
                jsonObjArr = null;
            }
            info.put("list", array);
            array = null;
            c.setEnd_num("-9");
            List<SystemCommonModel> list_total = sisMybatisDaoUtil.getListData(
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
    public JSONObject deleteSubCycle(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
            c.setVersion_time(getSysDate()); 
            c.setSql("sisAdminDAO.deleteSubCycle"); 
            info =  sisMybatisDaoUtil.sysUpdateData( 
                    c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;
    }

    @Override
    public JSONObject openOrCloseSubCycle(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
            if(c.getTemp_list().size()<1){
                info.put("res", "1");
                info.put("msg","请选择记录");
                return info ;
            }
            c.setVersion_time(getSysDate()); 
            c.setSql("sisAdminDAO.openOrCloseSubCycle"); 
            info =  sisMybatisDaoUtil.sysUpdateData( 
                    c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
        }
        return info;
    }
    /***
     * 获取补贴类型
     */
    @Override
    public JSONObject getRoleDeSubType(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        c.setSql("sisAdminDAO.getRoleDeSubType"); 
        List<SystemCommonModel> list =  sisMybatisDaoUtil.getListData( c.getSql(), c);
        if(list.size()<1){
            info.put("res", "1");
            info.put("msg","无数据");
            info.put("pageNum","0");
            info.put("tatol", "0");
            return info ;
        }else{
            info.put("res", "2");
            info.put("msg","执行成功"); 

        JSONArray array = new JSONArray();
        for(int i=0;i<list.size();i++){
            JSONObject jsonObjArr = new JSONObject();
            jsonObjArr.put("sub_type_cd", (list.get(i)).getFdid()); 
            array.add(jsonObjArr);
            jsonObjArr = null;
        }
        info.put("list", array);
        array = null;
        }
        return info;
    }

    @Override
    public JSONObject createRole2SubType(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        c.setSql("sisAdminDAO.getRoleDeSubType"); 
        List<SystemCommonModel> list =  sisMybatisDaoUtil.getListData( c.getSql(), c);
        if(list.size()>0){
            c.setSql("sisAdminDAO.deleteRoleDeSubType"); 
            info = sisMybatisDaoUtil.sysDeleteData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            }
        }
        List<String> ls = c.getTemp_list() ;
        for(int k=0;k<ls.size();k++){
            c.setSql("sisAdminDAO.createRole2SubType"); 
            c.setTemp_str3(ls.get(k));
            info = sisMybatisDaoUtil.sysDeleteData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            }
        }
        return info;
    }

    @Override
    public JSONObject getPsnBaseInfo(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try { 
            c.setSql("sisAdminDAO.getPsnBaseInfo");
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(
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
                jsonObjArr.put("person_name", (list.get(i)).getPerson_name());
                jsonObjArr.put("person_phone", (list.get(i)).getPerson_phone());
                jsonObjArr.put("person_gender", (list.get(i)).getPerson_gender());
                jsonObjArr.put("person_nation", (list.get(i)).getPerson_nation());
                jsonObjArr.put("person_orgin", (list.get(i)).getPerson_orgin());
                jsonObjArr.put("first_time", (list.get(i)).getFirst_time());
                jsonObjArr.put("person_state", (list.get(i)).getPerson_state()); 
                jsonObjArr.put("select_company_name", (list.get(i)).getSelect_company_name()); 
                jsonObjArr.put("create_time", (list.get(i)).getCreate_time()); 
                jsonObjArr.put("staff_name", (list.get(i)).getStaff_name()); 
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
    
    /****
     * 修改申报人基础信息
     */
    @Override
    public JSONObject changePsnBaseInfo(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();  
            c.setSql("sisAdminDAO.changePsnBaseInfo"); 
            info = sisMybatisDaoUtil.sysDeleteData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            } 
        return info;
    }

    @Override
    public JSONObject createPersonRptRecord(SystemCommonModel c) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JSONObject getCustomValueByOther(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();  
        if("ROLE2SUBTYPE".equalsIgnoreCase(c.getTemp_str1())){

            c.setSql("sisAdminDAO.getRptValueByStaff"); 
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData( c.getSql(), c); 
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
                jsonObjArr.put("id", (list.get(i)).getTemp_str1());
                jsonObjArr.put("name", (list.get(i)).getTemp_str2()); 
                array.add(jsonObjArr);
                jsonObjArr = null;
            }
            info.put("list", array);
            array = null; 
        }else if("Company2SubCycle".equalsIgnoreCase(c.getTemp_str1())){
            c.setSql("sisAdminDAO.getCompany2SubCycle"); 
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData( c.getSql(), c); 
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
                jsonObjArr.put("id", (list.get(i)).getTemp_str1());
                jsonObjArr.put("name", (list.get(i)).getTemp_str2()); 
                array.add(jsonObjArr);
                jsonObjArr = null;
            }
            info.put("list", array);
            array = null; 
            
        }
        return info;
    }

    @Override
    public JSONObject createOldPersonRptRecond(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();  
        c.setFdid(getUUID());
        c.setCreate_time(getSysDate());
        c.setSql("sisAdminDAO.getExistsPsn");
        List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(c.getSql(), c);
        if(list.size()>0){ //那么只保存记录即可
            c.setSql("sisAdminDAO.createOldPersonRptRecond"); 
            info = sisMybatisDaoUtil.sysUpdateData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            } 
        }else{
            c.setSql("sisAdminDAO.createPersonInfo"); 
            info = sisMybatisDaoUtil.sysInsertData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            } 
            c.setSql("sisAdminDAO.createOldPersonRptRecond"); 
            info = sisMybatisDaoUtil.sysUpdateData(c.getSql(), c);
            if("1".equals(info.get("res").toString())){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
                return info ;
            } 
        }
    return info;
    }

    @Override
    public JSONObject getExistsPsn(SystemCommonModel c) {
        // TODO Auto-generated method stub
        //如果这个人已经存在，那么申报人基础资料不予保存
        JSONObject info = new JSONObject();    
        c.setSql("sisAdminDAO.getExistsPsn");
        List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(c.getSql(), c);
        if(list.size()>0){ //那么只保存记录即可
            info.put("res", "2");
            info.put("msg","已存在");
            return info ;
        }else{
            info.put("res", "1");
            info.put("msg","不存在");
            return info;
        } 
    }

    @Override
    public JSONObject luruNewRptFlow(SystemCommonModel c,RuntimeService runtimeService, FormService formService, TaskService taskService) {
        // TODO Auto-generated method stub
        Map<String, Object> p = new HashMap<String, Object>();    
        // 开始流程  
        p.put("flow_id", c.getFdid());
        runtimeService.startProcessInstanceByKey("myProcess", p);
        // query kermit's tasks;  
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("creator").list();  
        for (Task task : tasks) {  
            if ("node1".equals(task.getTaskDefinitionKey())) {                 // 设置填报人单位编码记录在节点  
                taskService.setVariable(task.getId(), "fdid", c.getFdid()  ); 
                taskService.setVariable(task.getId(), "card_id",  c.getCard_id() );   
                taskService.setVariable(task.getId(), "person_name",  c.getPerson_name() );   
                taskService.setVariable(task.getId(), "unit_type",  c.getPerson_unit_type() ); 
                taskService.setVariable(task.getId(), "unit_name", c.getPerson_unit() ); 
                taskService.setVariable(task.getId(), "pay_start", c.getRpt_start()  ); 
                taskService.setVariable(task.getId(), "pay_end",  c.getRpt_end() );  
                taskService.setVariable(task.getId(), "sub_cycle",  c.getRpt_cycle()  ); 
                taskService.setVariable(task.getId(), "rpt_type",  c.getRpt_type()  );  
                taskService.setVariable(task.getId(), "person_phone",  c.getPerson_phone() );  
                taskService.setVariable(task.getId(), "creator",  c.getStaff_id() ); 
                taskService.setVariable(task.getId(), "create_company",  c.getStaff_company_id() ); 
                taskService.setVariable(task.getId(), "create_time",  c.getCreate_time()  );                 
                taskService.setVariable(task.getId(), "pass1",  "2"  );                 
                // 节点任务结束  
                taskService.complete(task.getId());  
                log.info("录入申报信息:card :"+c.getCard_id());  
            }  
        }
        JSONObject info = new JSONObject();  
        info.put("res", "2");
        info.put("msg", "执行成功");
        return info ;
    }

    @Override
    public JSONObject luruNewPsnAndHisRptFlow(SystemCommonModel c,RuntimeService runtimeService, FormService formService, TaskService taskService) {
        // TODO Auto-generated method stub
        // 这个人的资料不存在，那么就新建
        JSONObject info = new JSONObject();  
        c.setSql("sisAdminDAO.createPersonInfo"); 
        info = sisMybatisDaoUtil.sysInsertData(c.getSql(), c);
        if("1".equals(info.get("res").toString())){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 侵入式开发
            return info ;
        } 
        Map<String, Object> p = new HashMap<String, Object>();    
        // 开始流程  
        p.put("flow_id", c.getFdid());
        runtimeService.startProcessInstanceByKey("myProcess", p);
        // query kermit's tasks;  
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("creator").list();  
        for (Task task : tasks) {  
            if ("node1".equals(task.getTaskDefinitionKey())) {                 // 设置填报人单位编码记录在节点  
                taskService.setVariable(task.getId(), "fdid", c.getFdid()  ); 
                taskService.setVariable(task.getId(), "card_id",  c.getCard_id() );   
                taskService.setVariable(task.getId(), "person_name",  c.getPerson_name() );   
                taskService.setVariable(task.getId(), "unit_type",  c.getPerson_unit_type() ); 
                taskService.setVariable(task.getId(), "unit_name", c.getPerson_unit() ); 
                taskService.setVariable(task.getId(), "pay_start", c.getRpt_start()  ); 
                taskService.setVariable(task.getId(), "pay_end",  c.getRpt_end() );  
                taskService.setVariable(task.getId(), "sub_cycle",  c.getRpt_cycle()  ); 
                taskService.setVariable(task.getId(), "rpt_type",  c.getRpt_type()  );  
                taskService.setVariable(task.getId(), "person_phone",  c.getPerson_phone() );  
                taskService.setVariable(task.getId(), "creator",  c.getStaff_id() ); 
                taskService.setVariable(task.getId(), "create_company",  c.getStaff_company_id() ); 
                taskService.setVariable(task.getId(), "create_time",  c.getCreate_time()  );                 
                taskService.setVariable(task.getId(), "pass1",  "2"  );                 
                // 节点任务结束  
                taskService.complete(task.getId());  
                log.info("录入申报信息:card :"+c.getCard_id());  
            }  
        }
        info.put("res", "2");
        info.put("msg", "执行成功");
        return info ; 
    }

    @Override
    public JSONObject qryWaitCheckRecordList(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        try {
            List<String> ls = new ArrayList<String>();
            String[] arr = c.getTemp_str1().split(",");
            for(int i=0;i<arr.length;i++){
                ls.add(arr[i]);
            }
            c.setTemp_list(ls);
            c.setSql("sisAdminDAO.qryWaitCheckRecordList");
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(
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
                jsonObjArr.put("proc_inst_id", (list.get(i)).getProc_inst_id());
                jsonObjArr.put("fdid", (list.get(i)).getFdid());
                jsonObjArr.put("person_name", (list.get(i)).getPerson_name());
                jsonObjArr.put("card_id", (list.get(i)).getCard_id()); 
                jsonObjArr.put("start_date", (list.get(i)).getStart_date());
                jsonObjArr.put("end_date", (list.get(i)).getEnd_date());
                jsonObjArr.put("creator", (list.get(i)).getStaff_name()); 
                jsonObjArr.put("create_time", (list.get(i)).getCreate_time()); 
                jsonObjArr.put("staff_company_name", (list.get(i)).getStaff_company_name()); 
                array.add(jsonObjArr);
                jsonObjArr = null;
            }
            info.put("list", array);
            array = null;
            c.setEnd_num("-9");
            List<SystemCommonModel> list_total = sisMybatisDaoUtil.getListData(
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
    public JSONObject getThisCheckInfoAll(SystemCommonModel c) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();
        info.put("fdid",c.getFdid() );
        try { 
            c.setSql("sisAdminDAO.getCardInfoByFlowId");
            List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(
                    c.getSql(), c); 
             if(list.size()<1){
                 info.put("res","1");
                 info.put("msg", "流程中,此人不存在");
                 return info ;
             }
             c.setCard_id(list.get(0).getCard_id());
             c.setSql("sisAdminDAO.getPsnBaseInfo");
             List<SystemCommonModel> list2 = sisMybatisDaoUtil.getListData(
                     c.getSql(), c);  // 这样就把基础资料拿出来了

             if(list2.size()<1){
                 info.put("res","1");
                 info.put("msg", "此人不存在");
                 return info ;
             }

             JSONArray array = new JSONArray();
             for(int i=0;i<list2.size();i++){
                 JSONObject jsonObjArr = new JSONObject();
                 jsonObjArr.put("person_name", (list2.get(i)).getPerson_name());
                 jsonObjArr.put("card_id", (list2.get(i)).getCard_id());
                 jsonObjArr.put("person_phone", (list2.get(i)).getPerson_phone());
                 jsonObjArr.put("person_gender", (list2.get(i)).getPerson_gender());
                 jsonObjArr.put("person_nation", (list2.get(i)).getPerson_nation());
                 jsonObjArr.put("person_orgin", (list2.get(i)).getPerson_orgin());
                 jsonObjArr.put("first_time", (list2.get(i)).getFirst_time());
                 jsonObjArr.put("person_state", (list2.get(i)).getPerson_state()); 
                 jsonObjArr.put("select_company_name", (list2.get(i)).getSelect_company_name()); 
                 jsonObjArr.put("create_time", (list2.get(i)).getCreate_time()); 
                 jsonObjArr.put("staff_name", (list2.get(i)).getStaff_name()); 
                 array.add(jsonObjArr);
                 jsonObjArr = null;
             }
             info.put("binfo_list", array);
             array = null; 
             array = new JSONArray();
             // 然后把申报记录的数据拿出来
             c.setSql("sisAdminDAO.getThisCheckInfoAll");
             List<SystemCommonModel> list3 = sisMybatisDaoUtil.getListData(
                     c.getSql(), c);  // 这样就把基础资料拿出来了
             if(list3.size()<1){
                 info.put("res","1");
                 info.put("msg", "此人申报记录已不在初审环节");
                 return info ;
             }
             for(int i=0;i<list3.size();i++){
                 JSONObject jsonObjArr = new JSONObject();
                 jsonObjArr.put("pay_start", (list3.get(i)).getStart_date());
                 jsonObjArr.put("pay_end", (list3.get(i)).getEnd_date());
                 jsonObjArr.put("unit_type", (list3.get(i)).getPerson_unit_type());
                 jsonObjArr.put("unit_name", (list3.get(i)).getPerson_unit());
                 jsonObjArr.put("rpt_type", (list3.get(i)).getRpt_type());
                 jsonObjArr.put("sub_cycle", (list3.get(i)).getRpt_cycle()); 
                 array.add(jsonObjArr);
                 jsonObjArr = null;
             }
             info.put("flow_list", array);
             array = null; 
             array = new JSONArray();
             // 最后把图片附件加入
             c.setSql("sisAdminDAO.getPhotoByFdid");
             List<SystemCommonModel> list4 = sisMybatisDaoUtil.getListData(
                     c.getSql(), c);   
             if(list4.size()<1){
                 info.put("res","2"); 
                 info.put("msg", "此人申报记录中图片未知");
                 return info ;
             }
             for(int i=0;i<list4.size();i++){
                 JSONObject jsonObjArr = new JSONObject();
                 jsonObjArr.put("photo_after_name", (list4.get(i)).getTemp_str2()); 
                 jsonObjArr.put("flow_id", (list4.get(i)).getFdid()); 
                 array.add(jsonObjArr);
                 jsonObjArr = null;
             }
             info.put("photo_list", array);
             array = null; 
             info.put("res","2"); 
             info.put("msg", "查询成功");
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.getMessage());
        }
        
        return info;   
    }
    

    @Override
    public JSONObject checkRpt(SystemCommonModel c,RuntimeService runtimeService, FormService formService, TaskService taskService) {
        // TODO Auto-generated method stub 
        // query kermit's tasks;  
        JSONObject info = new JSONObject();  
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("CHECKER1").list();  
        if(tasks.size()<1){
            info.put("res", "1");
            info.put("msg", "任务不存在");
            return info ;
        }
        for (Task task : tasks) {  
            if ("node2".equals(task.getTaskDefinitionKey())) {                 // 设置填报人单位编码记录在节点  
                taskService.setVariable(task.getId(), "fdid", c.getFdid()  );  
                if("YES".equalsIgnoreCase(c.getCheck_result())){
                    taskService.setVariable(task.getId(), "pass2",  "2"  ); 
                    taskService.setVariable(task.getId(), "pass2_code",  c.getCheck_result()  ); 
                    taskService.setVariable(task.getId(), "pass2_info",  c.getCheck_info()  );                     
                }else{
                    taskService.setVariable(task.getId(), "pass2",  "1"  );  
                    taskService.setVariable(task.getId(), "pass2_code", c.getCheck_result()  ); 
                    taskService.setVariable(task.getId(), "pass2_info",  c.getCheck_info()  );  
                }
                // 节点任务结束  
                taskService.complete(task.getId());   
            }  
        }
        info.put("res", "2");
        info.put("msg", "初审成功");
        return info ;
    }
    

    @Override
    public JSONObject checkRptSecond(SystemCommonModel c,RuntimeService runtimeService, FormService formService, TaskService taskService) {
        // TODO Auto-generated method stub 
        // query kermit's tasks;  
        JSONObject info = new JSONObject();  
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("CHECKER2").list();  
        if(tasks.size()<1){
            info.put("res", "1");
            info.put("msg", "任务不存在");
            return info ;
        }
        for (Task task : tasks) {  
            if ("node3".equals(task.getTaskDefinitionKey())) {                 // 设置填报人单位编码记录在节点  
                taskService.setVariable(task.getId(), "fdid", c.getFdid()  );  
                if("YES".equalsIgnoreCase(c.getCheck_result())){
                    taskService.setVariable(task.getId(), "pass3",  "2"  ); 
                    taskService.setVariable(task.getId(), "pass3_code",  c.getCheck_result()  ); 
                    taskService.setVariable(task.getId(), "pass3_info",  c.getCheck_info()  );                     
                }else{
                    taskService.setVariable(task.getId(), "pass3",  "1"  );  
                    taskService.setVariable(task.getId(), "pass3_code", c.getCheck_result()  ); 
                    taskService.setVariable(task.getId(), "pass3_info",  c.getCheck_info()  );  
                }
                // 节点任务结束  
                taskService.complete(task.getId());   
            }  
        }
        info.put("res", "2");
        info.put("msg", "复审成功");
        return info ;
    }

    @Override
    public JSONObject deleteRptRecond(SystemCommonModel c, RuntimeService runtimeService, FormService formService,
            TaskService taskService) {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();  
        List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", c.getFdid()).taskAssignee("creator").list();  
        if(tasks.size()<1){
            info.put("res", "1");
            info.put("msg", "任务不存在");
            return info ;
        }
        for (Task task : tasks) {  
            if ("node1".equals(task.getTaskDefinitionKey())) {                 // 设置填报人单位编码记录在节点  
                taskService.setVariable(task.getId(), "fdid", c.getFdid()  );   
                taskService.setVariable(task.getId(), "pass1",  "1"  );    
                // 节点任务结束  
                taskService.complete(task.getId());   
            }  
        }
        info.put("res", "2");
        info.put("msg", "申报记录作废成功");
        return info ;
    }
      
    @Override
    public JSONObject grantFinanceToPsn(SystemCommonModel c, RuntimeService runtimeService, FormService formService,
            TaskService taskService)  {
        // TODO Auto-generated method stub
        JSONObject info = new JSONObject();  
        List<String> ls = new ArrayList<String>();
        String[] arr = c.getTemp_str1().split(",");
        for(int i=0;i<arr.length;i++){
            ls.add(arr[i]);
        }
        c.setTemp_list(ls);
        c.setSql("sisAdminDAO.qryWaitCheckRecordList");
        List<SystemCommonModel> list = sisMybatisDaoUtil.getListData(
                c.getSql(), c); 
        if(list.size()<1){
            info.put("res", "1");
            info.put("msg", "无数据需拨款");
            return info ;
        }
        for(int k=0;k<list.size();k++){
            List<Task> tasks = taskService.createTaskQuery().processVariableValueEquals("flow_id", list.get(k).getFdid()).taskAssignee("finance").list();  
            for (Task task : tasks) {  
                if ("node4".equals(task.getTaskDefinitionKey())) {                 // 财务拨款通过 
                    taskService.setVariable(task.getId(), "fdid", c.getFdid()  );   
                    taskService.setVariable(task.getId(), "pass4",  "2"  ); 

                    // 与此同时，将这条记录归档          
                    SystemCommonModel c2 = new SystemCommonModel();
                    c2.setPerson_name(taskService.getVariable(task.getId(), "person_name").toString());
                    c2.setCard_id(taskService.getVariable(task.getId(), "card_id").toString());
                    c2.setPerson_unit_type(taskService.getVariable(task.getId(), "unit_type").toString());
                    c2.setPerson_unit(taskService.getVariable(task.getId(), "unit_name").toString());
                    c2.setRpt_start(taskService.getVariable(task.getId(), "pay_start").toString());
                    c2.setRpt_end(taskService.getVariable(task.getId(), "pay_end").toString());
                    c2.setRpt_cycle(taskService.getVariable(task.getId(), "sub_cycle").toString());
                    c2.setRpt_type(taskService.getVariable(task.getId(), "rpt_type").toString());
                    c2.setFlow_id(taskService.getVariable(task.getId(), "flow_id").toString());
                    c2.setFdid(getUUID());
                    c2.setCreate_time(getSysDate()); 
                    c2.setStaff_id(c.getStaff_id());
                    c2.setSql("sisAdminDAO.grantFinanceToPsn");
                    info = sisMybatisDaoUtil.sysInsertData(c2.getSql(), c2);
                    // 节点任务结束  
                    taskService.complete(task.getId());   
                }  
            }
        }
        return info;
    }
}
