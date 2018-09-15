package com.bw.fit.sis.dao.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bw.fit.common.models.SystemCommonModel;
 
@Transactional
@Repository
public class SisMybatisDaoUtil {
	private static Log log = LogFactory.getLog(SisMybatisDaoUtil.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * getTheCheckResualt()
	 * @param sql
	 * @param param
	 * @return
	 */
	 public JSONObject getTheCheckResault(SystemCommonModel c) {
	        JSONObject info = new JSONObject();
		try {
			Connection conn = sqlSessionTemplate.getConnection(); 
			if (conn == null) {
			    info.put("res", "1");
				info.put("msg","数据库连接失败，请联系系统管理员");
				return info;
			}

			CallableStatement proc = null;
			proc = conn
					.prepareCall("call BUSINESS_RULE_CHECK.bussiness_admin_check_enter(?,?,?,?,?,"
					        + "?,?,?,?,?,"
					        + "?,?,?,?,?,"
					        + "?,?,?,?,?,"
					        + "?,?,?,?,?,"
					        + "?,?)");
			proc.setString(1, c.getAction_name());
            proc.setString(2, c.getSelect_company_id());
            proc.setString(3, c.getRpt_date());
            proc.setString(4, c.getRole_id());
            proc.setString(5, c.getSql());
            proc.setString(6, c.getStaff_id());
            proc.setString(7, c.getStaff_company_id());
            proc.setString(8, c.getStaff_number());
            proc.setString(9, c.getFdid());
            proc.setString(10, c.getStaff_name());
            proc.setString(11, c.getCard_id());
            proc.setString(12, c.getPerson_gender());
            proc.setString(13, c.getPerson_phone());
            proc.setString(14, c.getPerson_unit_type());
            proc.setString(15, c.getRpt_type());
            proc.setString(16, c.getRpt_cycle());
            proc.setString(17, c.getRpt_start());
            proc.setString(18, c.getRpt_end());
            proc.setString(19, c.getPerson_nation());
            proc.setString(20, c.getPerson_state());
            proc.setString(21, c.getFirst_time());
            proc.setString(22, c.getTemp_str1());
            proc.setString(23, c.getTemp_str2());
            proc.setString(24, c.getTemp_str3());
            proc.setString(25, c.getPerson_first_age());
			proc.registerOutParameter(26, java.sql.Types.VARCHAR);
			proc.registerOutParameter(27, java.sql.Types.VARCHAR);
			proc.execute(); 

            info.put("res", proc.getString(26));
            info.put("msg", proc.getString(27));
			proc.close(); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return info;
	} 
	/*
	 * 得到一个数据 yangh
	 */
	public Object getOneData(String sql, Object param) {  
		Object obj = sqlSessionTemplate.selectOne(sql, param);
		return obj;
	}

	/*
	 * 得到list数据
	 */
	public List getListData(String sql, Object param) {
		List list = sqlSessionTemplate.selectList(sql, param);
		return list;
	}

	/*
	 * 插入或更新数据 yangh
	 */
	public JSONObject sysUpdateData(String sql, Object param)  {
        JSONObject obj = new JSONObject();
        JSONObject o = getTheCheckResault((SystemCommonModel)param) ;
	    if(!"2".equals(o.get("res"))){ 
            return o ;
	    }
	    int res = 0;
	            try {
                    res = sqlSessionTemplate.update(sql, param);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
	    if(res<1){
	        obj.put("res", "1");
	        obj.put("msg","执行失败");
	        return obj ;
	    }
        obj.put("res", "2");
        obj.put("msg","执行成功");
        return obj ;
	}

    public JSONObject sysInsertData(String sql, Object param)  {
        JSONObject obj = new JSONObject();
        JSONObject o = getTheCheckResault((SystemCommonModel)param) ;
        if(!"2".equals(o.get("res"))){ 
            return o ;
        }
        int res = 0;
        try {
            res = sqlSessionTemplate.insert(sql, param);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(res<1){
            obj.put("res", "1");
            obj.put("msg","执行失败");
            return obj ;
        }
        obj.put("res", "2");
        obj.put("msg","执行成功");
        return obj ;
    }
	public JSONObject sysDeleteData(String sql, Object param) {
        JSONObject obj = new JSONObject();
        JSONObject o = getTheCheckResault((SystemCommonModel)param) ;
        if(!"2".equals(o.get("res"))){ 
            return o ;
        }
        int res = sqlSessionTemplate.delete(sql, param);
        if(res<1){
            obj.put("res", "1");
            obj.put("msg","执行失败");
            return obj ;
        }
        obj.put("res", "2");
        obj.put("msg","执行成功");
        return obj ;
	}

}
