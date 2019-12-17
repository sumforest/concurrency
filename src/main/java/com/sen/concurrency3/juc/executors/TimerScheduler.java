package com.sen.concurrency3.juc.executors;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 15:29
 * @Description: 定时任务的解决方案
 * 1.Quartz
 * 2.SchedulerExecutorService
 * 3.crontab (Linux)
 * 4.Timer/TimerTask: 当执行任务的时间超过了设定时间间隔，那么会等待任务执行完之后在此执行。
 */
public class TimerScheduler {

    public static void main(String[] args) {
        final Timer timer = new Timer();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("======================" + System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }
}
