package com.bw.fit.system.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bw.common.beans.BwBaseAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author yangh
 *所有Action都会继承该类
 */
public class BaseAction extends ActionSupport {
	private  HttpServletResponse response = ServletActionContext.getResponse() ;
	private  HttpServletRequest request = ServletActionContext.getRequest(); 
	private  Log log = LogFactory.getLog(BaseAction.class); 
	private ServletContext servletContext = request.getServletContext();

	public Object getBean(String name){
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext); 
		return  (Object) ctx.getBean(name);  
 	}
}
