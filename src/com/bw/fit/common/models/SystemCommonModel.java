package com.bw.fit.common.models;

import java.util.List;


public class SystemCommonModel {    
    private String staff_number ;
    private String staff_name ;
    private String passwd;
    private String passwd_mm;
    private String create_time; 
    private String version_time;
    private String fdid ;
    private String isDeleted ;
    private String select_company_id ;
    public String select_company_name ;
    public String select_company_address ;
    private String staff_id ;
    private String staff_parent_company_id ;
    private String staff_company_id ;
    private String staff_company_name ;
    private String staff_phone;
    public String address ;
    public String start_date ;
    public String end_date ;
    private String role_id ; 
    private String role_name ; 
    private String start_num ;
    private String end_num;
    private String total_reords ;
    private String sql ;
    private String action_name ;
    private String desp ;
    private String position_id ;
    private String position_name ;   
    private String system_res ;
    private String system_msg;
    private String func_name ;
    private String func_id ;
    private String func_p_id;
    private String func_level;
    private String func_address;
    private String func_target;
    private String func_rel;
    private boolean func_click;
    private String func_check;
    private String btn_cd ;
    private String btn_type ;
    private String btn_name ; 
    private String func_btn;
    private String record_tatol ;
    private String item_type ;
    private String item_id ;
    private String item_name ;
    private String item_state ;
    /**
     * 以上是系统设置通用字段
     * 下面是业务部分字段
     */
    private int temp_int1;
    private int temp_int2;
    private int temp_int3;
    private String temp_str1;
    private String temp_str2;
    private String temp_str3;
    private List<String> temp_list ;
    private String rpt_date; 
    private String card_id; 
    private String person_name; 
    private String person_phone;     
    private String person_gender; 
    private String person_nation; 
    private String person_orgin; 
    private String person_unit; 
    private String person_unit_type; 
    private String person_minzu; 
    private String first_time; 
    private String person_state; 
    private String rpt_start; 
    private String rpt_end;  
    private String person_type ;
    private String rpt_type ;
    private String rpt_cycle; // 申报周期
    private String rpt_cycle_start ;
    private String rpt_cycle_end ;
    private String rpt_cycle_state ;  
    private String rpt_month_count ;
    private String cycle_name;
    
    
    
    
    
    
    
    
    public String getFirst_time() {
        return first_time;
    }
    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }
    public String getPerson_nation() {
        return person_nation;
    }
    public void setPerson_nation(String person_nation) {
        this.person_nation = person_nation;
    }
    public String getPerson_orgin() {
        return person_orgin;
    }
    public void setPerson_orgin(String person_orgin) {
        this.person_orgin = person_orgin;
    }
    public String getPerson_phone() {
        return person_phone;
    }
    public void setPerson_phone(String person_phone) {
        this.person_phone = person_phone;
    }
    public String getCycle_name() {
        return cycle_name;
    }
    public void setCycle_name(String cycle_name) {
        this.cycle_name = cycle_name;
    }
    public String getStaff_phone() {
        return staff_phone;
    }
    public void setStaff_phone(String staff_phone) {
        this.staff_phone = staff_phone;
    }
    public String getSelect_company_address() {
        return select_company_address;
    }
    public void setSelect_company_address(String select_company_address) {
        this.select_company_address = select_company_address;
    }
    public String getStaff_parent_company_id() {
        return staff_parent_company_id;
    }
    public void setStaff_parent_company_id(String staff_parent_company_id) {
        this.staff_parent_company_id = staff_parent_company_id;
    }
    public int getTemp_int1() {
        return temp_int1;
    }
    public void setTemp_int1(int temp_int1) {
        this.temp_int1 = temp_int1;
    }
    public int getTemp_int2() {
        return temp_int2;
    }
    public void setTemp_int2(int temp_int2) {
        this.temp_int2 = temp_int2;
    }
    public int getTemp_int3() {
        return temp_int3;
    }
    public void setTemp_int3(int temp_int3) {
        this.temp_int3 = temp_int3;
    }
    public String getTemp_str1() {
        return temp_str1;
    }
    public void setTemp_str1(String temp_str1) {
        this.temp_str1 = temp_str1;
    }
    public String getTemp_str2() {
        return temp_str2;
    }
    public void setTemp_str2(String temp_str2) {
        this.temp_str2 = temp_str2;
    }
    public String getTemp_str3() {
        return temp_str3;
    }
    public void setTemp_str3(String temp_str3) {
        this.temp_str3 = temp_str3;
    }
    public String getStaff_number() {
        return staff_number;
    }
    public void setStaff_number(String staff_number) {
        this.staff_number = staff_number;
    }
    public String getStaff_name() {
        return staff_name;
    }
    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }
    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getPasswd_mm() {
        return passwd_mm;
    }
    public void setPasswd_mm(String passwd_mm) {
        this.passwd_mm = passwd_mm;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getVersion_time() {
        return version_time;
    }
    public void setVersion_time(String version_time) {
        this.version_time = version_time;
    }
    public String getFdid() {
        return fdid;
    }
    public void setFdid(String fdid) {
        this.fdid = fdid;
    }
    public String getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getSelect_company_id() {
        return select_company_id;
    }
    public void setSelect_company_id(String select_company_id) {
        this.select_company_id = select_company_id;
    }
    public String getSelect_company_name() {
        return select_company_name;
    }
    public void setSelect_company_name(String select_company_name) {
        this.select_company_name = select_company_name;
    }
    public String getStaff_id() {
        return staff_id;
    }
    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }
    public String getStaff_company_id() {
        return staff_company_id;
    }
    public void setStaff_company_id(String staff_company_id) {
        this.staff_company_id = staff_company_id;
    }
    public String getStaff_company_name() {
        return staff_company_name;
    }
    public void setStaff_company_name(String staff_company_name) {
        this.staff_company_name = staff_company_name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    public String getEnd_date() {
        return end_date;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public String getRole_id() {
        return role_id;
    }
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
    public String getRole_name() {
        return role_name;
    }
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
    public String getStart_num() {
        return start_num;
    }
    public void setStart_num(String start_num) {
        this.start_num = start_num;
    }
    public String getEnd_num() {
        return end_num;
    }
    public void setEnd_num(String end_num) {
        this.end_num = end_num;
    }
    public String getTotal_reords() {
        return total_reords;
    }
    public void setTotal_reords(String total_reords) {
        this.total_reords = total_reords;
    }
    public String getSql() {
        return sql;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }
    public String getAction_name() {
        return action_name;
    }
    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }
    public String getDesp() {
        return desp;
    }
    public void setDesp(String desp) {
        this.desp = desp;
    }
    public String getPosition_id() {
        return position_id;
    }
    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }
    public String getPosition_name() {
        return position_name;
    }
    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }
    public String getSystem_res() {
        return system_res;
    }
    public void setSystem_res(String system_res) {
        this.system_res = system_res;
    }
    public String getSystem_msg() {
        return system_msg;
    }
    public void setSystem_msg(String system_msg) {
        this.system_msg = system_msg;
    }
    public String getFunc_name() {
        return func_name;
    }
    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }
    public String getFunc_id() {
        return func_id;
    }
    public void setFunc_id(String func_id) {
        this.func_id = func_id;
    }
    public String getFunc_p_id() {
        return func_p_id;
    }
    public void setFunc_p_id(String func_p_id) {
        this.func_p_id = func_p_id;
    }
    public String getFunc_level() {
        return func_level;
    }
    public void setFunc_level(String func_level) {
        this.func_level = func_level;
    }
    public String getFunc_address() {
        return func_address;
    }
    public void setFunc_address(String func_address) {
        this.func_address = func_address;
    }
    public String getFunc_target() {
        return func_target;
    }
    public void setFunc_target(String func_target) {
        this.func_target = func_target;
    }
    public String getFunc_rel() {
        return func_rel;
    }
    public void setFunc_rel(String func_rel) {
        this.func_rel = func_rel;
    }
    public boolean isFunc_click() {
        return func_click;
    }
    public void setFunc_click(boolean func_click) {
        this.func_click = func_click;
    }
    public String getFunc_check() {
        return func_check;
    }
    public void setFunc_check(String func_check) {
        this.func_check = func_check;
    }
    public String getBtn_cd() {
        return btn_cd;
    }
    public void setBtn_cd(String btn_cd) {
        this.btn_cd = btn_cd;
    }
    public String getBtn_type() {
        return btn_type;
    }
    public void setBtn_type(String btn_type) {
        this.btn_type = btn_type;
    }
    public String getBtn_name() {
        return btn_name;
    }
    public void setBtn_name(String btn_name) {
        this.btn_name = btn_name;
    }
    public String getFunc_btn() {
        return func_btn;
    }
    public void setFunc_btn(String func_btn) {
        this.func_btn = func_btn;
    }
    public String getRecord_tatol() {
        return record_tatol;
    }
    public void setRecord_tatol(String record_tatol) {
        this.record_tatol = record_tatol;
    }
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    public String getItem_id() {
        return item_id;
    }
    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
    public String getItem_name() {
        return item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public String getItem_state() {
        return item_state;
    }
    public void setItem_state(String item_state) {
        this.item_state = item_state;
    }
    public String getRpt_date() {
        return rpt_date;
    }
    public void setRpt_date(String rpt_date) {
        this.rpt_date = rpt_date;
    }
    public String getCard_id() {
        return card_id;
    }
    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }
    public String getPerson_name() {
        return person_name;
    }
    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
    public String getPerson_gender() {
        return person_gender;
    }
    public void setPerson_gender(String person_gender) {
        this.person_gender = person_gender;
    }
    public String getPerson_unit() {
        return person_unit;
    }
    public void setPerson_unit(String person_unit) {
        this.person_unit = person_unit;
    }
    public String getPerson_unit_type() {
        return person_unit_type;
    }
    public void setPerson_unit_type(String person_unit_type) {
        this.person_unit_type = person_unit_type;
    }
    public String getPerson_minzu() {
        return person_minzu;
    }
    public void setPerson_minzu(String person_minzu) {
        this.person_minzu = person_minzu;
    }
    public String getRpt_start() {
        return rpt_start;
    }
    public void setRpt_start(String rpt_start) {
        this.rpt_start = rpt_start;
    }
    public String getRpt_end() {
        return rpt_end;
    }
    public void setRpt_end(String rpt_end) {
        this.rpt_end = rpt_end;
    }
    public String getPerson_state() {
        return person_state;
    }
    public void setPerson_state(String person_state) {
        this.person_state = person_state;
    }
    public String getPerson_type() {
        return person_type;
    }
    public void setPerson_type(String person_type) {
        this.person_type = person_type;
    }
    public String getRpt_type() {
        return rpt_type;
    }
    public void setRpt_type(String rpt_type) {
        this.rpt_type = rpt_type;
    }
    public String getRpt_cycle() {
        return rpt_cycle;
    }
    public void setRpt_cycle(String rpt_cycle) {
        this.rpt_cycle = rpt_cycle;
    }
    public String getRpt_cycle_start() {
        return rpt_cycle_start;
    }
    public void setRpt_cycle_start(String rpt_cycle_start) {
        this.rpt_cycle_start = rpt_cycle_start;
    }
    public String getRpt_cycle_end() {
        return rpt_cycle_end;
    }
    public void setRpt_cycle_end(String rpt_cycle_end) {
        this.rpt_cycle_end = rpt_cycle_end;
    }
    public String getRpt_cycle_state() {
        return rpt_cycle_state;
    }
    public void setRpt_cycle_state(String rpt_cycle_state) {
        this.rpt_cycle_state = rpt_cycle_state;
    }
    public String getRpt_month_count() {
        return rpt_month_count;
    }
    public void setRpt_month_count(String rpt_month_count) {
        this.rpt_month_count = rpt_month_count;
    }
    public List<String> getTemp_list() {
        return temp_list;
    }
    public void setTemp_list(List<String> temp_list) {
        this.temp_list = temp_list;
    }
    
    
    
}
