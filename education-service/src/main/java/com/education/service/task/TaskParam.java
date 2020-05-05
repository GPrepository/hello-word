package com.education.service.task;

/**
 * 封装任务参数类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:46
 */
public class TaskParam {

    private String taskListenerBeanName;
    private final long timestamp;
    private Object data;

    public String getTaskListenerBeanName() {
        return taskListenerBeanName;
    }

    public void setTaskListenerBeanName(String taskListenerBeanName) {
        this.taskListenerBeanName = taskListenerBeanName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public <T> T getData() {
        return (T) data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public TaskParam() {
        this.timestamp = System.currentTimeMillis();
    }

    public TaskParam(String taskListenerBeanName, long timestamp, Object data) {
        this.timestamp = timestamp;
        this.taskListenerBeanName = taskListenerBeanName;
        this.data = data;
    }

    public TaskParam(String taskListenerBeanName, Object data) {
        this(taskListenerBeanName, System.currentTimeMillis(), data);
    }
}
