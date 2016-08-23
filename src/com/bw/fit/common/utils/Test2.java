package com.bw.fit.common.utils;

import java.util.*;

import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.json.simple.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test2  extends Test{
    
    public static void main(String[] args){ 
        // 加载spring配置  
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(  
                "springAppContext.xml");  
        RuntimeService runtimeService = (RuntimeService) ctx  
                .getBean("runtimeService");  
        ProcessEngine processEngine = (ProcessEngine) ctx  
                .getBean("processEngine");  
        FormService formService = (FormService) ctx.getBean("formService");  
        TaskService taskService = (TaskService) ctx.getBean("taskService");  
        // 发布流程  
        RepositoryService repositoryService = processEngine  
                .getRepositoryService();  
        repositoryService.createDeployment()  
                .addClasspathResource("MyProcess1.bpmn").deploy();  
        
        processEngine.getProcessEngineConfiguration().getJobExecutor().start();
        
        taskNode1(runtimeService, formService, taskService);  
        taskNode2(runtimeService, formService, taskService);  
    }
    
    private static void taskNode1(RuntimeService runtimeService,  
            FormService formService, TaskService taskService) {   
        Map<String, Object> p = new HashMap<String, Object>();   
        p.put("id", "100");
            // 开始流程  
            runtimeService.startProcessInstanceByKey("myProcess", p);  
            System.out  
                    .println("=====================role1开始 =======================");  
            // query kermit's tasks;  
            List<Task> tasks = taskService.createTaskQuery()  
                    .taskAssignee("role1").list();  
            for (Task task : tasks) {  
                if ("node1".equals(task.getTaskDefinitionKey())) {  
                    // 设置填报人单位编码记录在节点  
                    taskService.setVariable(task.getId(), "accoutCode",  
                            "testtest 1111"  );  
                    taskService.setVariable(task.getId(), "tid",  
                            task.getId() );  
                    // 设置该流程实例的填报单位  
                    taskService.setVariable(task.getId(), "fillAccount", "A110"     );   
                    // 节点任务结束  
                    taskService.complete(task.getId());  
                    System.out  
                            .println("=============role1 填写 任务已完成=====================");  
                    System.out.println();  
                }  
            }   
    }   
    

    private static void taskNode2(RuntimeService runtimeService,  
            FormService formService, TaskService taskService) {   
        List<Task> tasks2 = taskService.createTaskQuery()  
                .taskAssignee("role2")  
                .processVariableValueEquals("id", "100")  
                .processDefinitionKey("myProcess").list();   
        System.out.println("2de task数"+tasks2.size());
    }

}
