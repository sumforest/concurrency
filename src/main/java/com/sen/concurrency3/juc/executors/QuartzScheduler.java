package com.sen.concurrency3.juc.executors;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Author: Sen
 * @Date: 2019/12/17 16:05
 * @Description: Quartz实现定时任务
 */
public class QuartzScheduler {

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        JobDetail simpleJob = newJob(SimpleJob.class)
                .withIdentity("job1", "Group1")
                .build();

        CronTrigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                // 5s执行一次
                .withSchedule(cronSchedule("0/5 * * * * ?")).build();
        scheduler.start();
        scheduler.scheduleJob(simpleJob, trigger);
    }
}
