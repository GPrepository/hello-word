package com.education.service.task;

import com.education.common.utils.SpringBeanManager;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:37
 */
public class TaskManager {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final Map<String, TaskListener> taskListenerMap = new ConcurrentHashMap<>();

    public TaskManager(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.registerTaskListener();
    }

    private void registerTaskListener() {
        ApplicationContext applicationContext = SpringBeanManager.getApplicationContext();
        String[] beanNames = applicationContext.getBeanNamesForType(TaskListener.class);
        for (String name : beanNames) {
            TaskListener taskListener = SpringBeanManager.getBean(name);
            taskListenerMap.put(name, taskListener);
        }
    }

    public void pushTask(TaskParam taskParam) {
        String beanName = taskParam.getTaskListenerBeanName();
        TaskListener taskListener = SpringBeanManager.getBean(beanName); //taskListenerMap.get(beanName);
        if (taskListener != null) {
            threadPoolTaskExecutor.execute(() -> {
                taskListener.onMessage(taskParam);
            });
        }
    }

}
