package com.bw.fit.system.struts.interceptors;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bw.fit.common.models.*;
import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.struts2.*;
import org.json.simple.JSONObject;

import com.bw.fit.system.service.impl.SystemAdminServiceImpl;
import com.opensymphony.xwork2.*;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor {
    /***
     * 权限拦截器
     */
    @Override
    public String intercept(ActionInvocation invocation) {
        try {
            ActionContext actionContext = invocation.getInvocationContext();
            HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
            HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
            JSONObject info = new JSONObject();
            response.setContentType("text/plain;charset=UTF-8");
            Writer wr = response.getWriter();
            Map map = invocation.getInvocationContext().getSession();

            // 判断用户是否有权限进行操作
            ServletContext sc = ServletActionContext.getServletContext();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            SystemAdminServiceImpl systemAdminServiceImpl = (SystemAdminServiceImpl) ctx
                    .getBean("systemAdminServiceImpl");
            String staff_id = ((LoginUser) map.get("LoginUser")).getStaff_id();
            // 拦截的action的名字
            String action_name = invocation.getInvocationContext().getName();
            SystemCommonModel c = new SystemCommonModel();
            c.setStaff_id(staff_id);
            c.setAction_name(action_name);
            JSONObject obj = systemAdminServiceImpl.getUserAuthExists(c);
            if ("2".equals(obj.get("res").toString())) {// 存在这个权限
                String result = invocation.invoke();
                // 这块可以拿到response里的返回值数据，
                // 切记拦截器执行顺序，a->b->action ->b->a 拦截器是压栈方式存放。
                Object action = invocation.getInvocationContext().get("action");
                String method = invocation.getProxy().getMethod();
                boolean s = invocation.getProxy().getExecuteResult();
                // response,已经向外抛一次了 所以不能再次getOutStream了
                // OutputStream out = response.getOutputStream();
                // JSON json = JSONSerializer.toJSON(out);
                return result;
            }
            info.put("res", "1");
            info.put("msg", obj.get("msg").toString());
            wr.write(info.toJSONString());
            wr.close();
            System.out.println("无权限");
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
