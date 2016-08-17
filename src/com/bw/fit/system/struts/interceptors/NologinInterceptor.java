package com.bw.fit.system.struts.interceptors;

import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bw.fit.common.models.LoginUser;
import com.bw.fit.common.models.SystemCommonModel;
import com.bw.fit.system.service.impl.SystemAdminServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class NologinInterceptor   extends AbstractInterceptor {
    /***
     * 登录拦截器
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
            ServletContext sc = ServletActionContext.getServletContext();
            if (map == null || "".equals(map.get("LoginUser"))) {
                info.put("res", "1");
                info.put("msg", "请登录");
                wr.write(info.toJSONString());
                wr.close();
                return null;
            }  
             String s = invocation.invoke(); 
             return s ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}