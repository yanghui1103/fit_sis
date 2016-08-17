package com.bw.fit.system.dao.utils;

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
public class SystemMybatisDaoUtil {
	private static Log log = LogFactory.getLog(SystemMybatisDaoUtil.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * getTheCheckResualt()
	 * @param sql
	 * @param param
	 * @return
	 */
	 public Document getTheCheckResault(SystemCommonModel c) {
		Document document = DocumentHelper.createDocument();
		try {
			Connection conn = sqlSessionTemplate.getConnection();
			Element rootR = document.addElement("root");
			if (conn == null) {
				rootR.addElement("res").addText("1");
				rootR.addElement("msg").addText("数据库连接失败，请联系系统管理员");
				return document;
			}

			CallableStatement proc = null;
			proc = conn
					.prepareCall("call BUSINESS_RULE_CHECK.bussiness_admin_check_enter(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			 
			proc.registerOutParameter(13, java.sql.Types.VARCHAR);
			proc.registerOutParameter(14, java.sql.Types.VARCHAR);

			proc.execute(); 
			proc.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
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
	    int res = sqlSessionTemplate.update(sql, param);
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
