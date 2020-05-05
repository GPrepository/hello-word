package com.education.service.task;

import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:57
 */
@Component
public class LogTaskListener implements TaskListener {
    @Override
    public void onMessage(TaskParam taskParam) {
        System.out.println(Thread.currentThread().getName() + "执行LogTaskListener：" + taskParam.getData());
    }
}
