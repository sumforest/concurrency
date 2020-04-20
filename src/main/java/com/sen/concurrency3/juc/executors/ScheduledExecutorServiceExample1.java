package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Sen
 * @Date: 2019/12/18 01:10
 * @Description:
 */
public class ScheduledExecutorServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        // ScheduledFuture<?> future = executorService.schedule(() -> System.out.println("I am runnable"), 2, TimeUnit.SECONDS);

        /* ScheduledFuture<Integer> future = executorService.schedule(() -> 1, 2, TimeUnit.SECONDS);
        System.out.println(future.get());*/

        /**
         * {@link ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)}
         * 执行定时任务的策略与Timer的定时策略相同，当执行任务的时间大于周期时，以执行时间为周期
         * quartz/crontab/control-M是周期为先的
         */
        executorService.scheduleAtFixedRate(() -> {
            final AtomicLong stamp = new AtomicLong(0L);
            long currentTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread());
            if (stamp.get() == 0) {
                System.out.printf("I first to work at [%d]\n",currentTime);
            } else {
                System.out.printf("I am working at [%d]\n", currentTime - stamp.get());
            }
            stamp.set(currentTime);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, 2, TimeUnit.SECONDS);
    }

}
