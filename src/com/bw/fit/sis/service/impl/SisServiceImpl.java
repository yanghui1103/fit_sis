package com.bw.fit.sis.service.impl; 
import static com.bw.fit.common.utils.PubFun.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

}
