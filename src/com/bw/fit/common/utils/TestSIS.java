package com.bw.fit.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Task;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 

public class TestSIS {

    public static void main(String[] args) {

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
                .addClasspathResource("sisProcess.bpmn.xml").deploy();  
        
        processEngine.getProcessEngineConfiguration().getJobExecutor().start();

          //taskNode1(runtimeService, formService, taskService,repositoryService);  

        taskNode2(runtimeService, formService, taskService);  
        processEngine.getProcessEngineConfiguration().getJobExecutor().shutdown();
    }

    private static void taskNode1(RuntimeService runtimeService,FormService formService, TaskService taskService, RepositoryService repositoryService) {   
        Map<String, Object> p = new HashMap<String, Object>();    
            // 开始流程  
        p.put("id", "100");
            runtimeService.startProcessInstanceByKey("myProcess", p);
            // query kermit's tasks;  
            List<Task> tasks = taskService.createTaskQuery().taskAssignee("role1").list();  
            for (Task task : tasks) {  
                if ("node1".equals(task.getTaskDefinitionKey())) {  
                    // 设置填报人单位编码记录在节点  

                    taskService.setVariable(task.getId(), "fdid",  "10000001"  ); 
                    taskService.setVariable(task.getId(), "card_id",  "152722199001096711"  );   
                    taskService.setVariable(task.getId(), "gender",  "0"  );    
                    taskService.setVariable(task.getId(), "nation",  "汉族"  );   
                    taskService.setVariable(task.getId(), "person_name",  "沈某某"  );   
                    taskService.setVariable(task.getId(), "origin",  "东胜区"  ); 
                    taskService.setVariable(task.getId(), "unit_type",  "91"  ); 
                    taskService.setVariable(task.getId(), "unit_name",  "某某超市"  ); 
                    taskService.setVariable(task.getId(), "pay_start",  "2016-01"  ); 
                    taskService.setVariable(task.getId(), "pay_end",  "2016-12"  ); 
                    taskService.setVariable(task.getId(), "person_type",  "101"  ); 
                    taskService.setVariable(task.getId(), "sub_cycle",  "201601"  ); 
                    taskService.setVariable(task.getId(), "sub_type",  "S01"  ); 
                    taskService.setVariable(task.getId(), "first_date",  "2015-03-04"  ); 
                    taskService.setVariable(task.getId(), "person_phone",  "18904772110"  );  
                    taskService.setVariable(task.getId(), "creator",  "w323489sd7889sdf78df"  ); 
                    taskService.setVariable(task.getId(), "create_time",  "2016-09-09 12:12:12"  ); 
                    
                    taskService.setVariable(task.getId(), "pass1",  "2"  ); 
                    
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
               .processVariableValueEquals("flow_id", "88449950cbed462e88bd36464136d2c8")  
                .processDefinitionKey("myProcess").list();   
        System.out.println("task2数"+tasks2.size());
    }
}
