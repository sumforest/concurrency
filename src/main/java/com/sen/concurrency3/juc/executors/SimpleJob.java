package com.sen.concurrency3.juc.executors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Author: Sen
 * @Date: 2019/12/17 16:22
 * @Description: Quartz要执行的逻辑
 */
public class SimpleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("=====================" + System.currentTimeMillis());
    }
}
