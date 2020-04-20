package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Sen
 * @Date: 2019/12/18 01:45
 * @Description:
 */
public class ScheduledExecutorServiceExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // testGetContinueExistingPeriodicTasksAfterShutdownPolicy();
        testGetExecuteExistingDelayedTasksAfterShutdownPolicy();
    }

    private static void testScheduleWithFixedDelay() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        executorService.scheduleWithFixedDelay(() -> {
            final AtomicLong stamp = new AtomicLong(0L);
            long currentTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread());
            if (stamp.get() == 0) {
                System.out.printf("I first to work at [%d]\n", currentTime);
            } else {
                System.out.printf("I am working at [%d]\n", currentTime - stamp.get());
            }
            stamp.set(currentTime);
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);
    }


    private static void testGetContinueExistingPeriodicTasksAfterShutdownPolicy() throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(2);
        System.out.println(executorService.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        executorService.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        ScheduledFuture<?> future = executorService.scheduleAtFixedRate(() -> {
            System.out.println("Hello World");
        }, 1,2, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(1_200);
        executorService.shutdown();
        System.out.println(executorService.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        System.out.println("=========over=========");
    }

    private static void testGetExecuteExistingDelayedTasksAfterShutdownPolicy(){
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(2);
        System.out.println(executorService.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        executorService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executorService.scheduleWithFixedDelay(() -> {
            final AtomicLong stamp = new AtomicLong(0L);
            long currentTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread());
            if (stamp.get() == 0) {
                System.out.printf("I first to work at [%d]\n", currentTime);
            } else {
                System.out.printf("I am working at [%d]\n", currentTime - stamp.get());
            }
            stamp.set(currentTime);
            try {
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 1, 2, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println(executorService.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        System.out.println("++++++++++**over**+++++++++++");
    }
}
