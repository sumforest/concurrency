package com.sen.concurrency3.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 17:38
 * @Description: 线程池四大拒绝策略
 */
public class ExecutorServiceExample2 {

    public static void main(String[] args) throws InterruptedException {
        // testAbortPolicy();
        // testDiscardPolicy();
        // testCallerRunsPolicy();
        testDiscardOldestPolicy();
    }

    /**
     * 当提交的数量大于任务队列的大小抛异常
     *  RejectedExecutionException
     * @throws InterruptedException
     */
    public static void testAbortPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,2,
                30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1), Thread::new
        ,new ThreadPoolExecutor.AbortPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i->executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        executor.submit(() -> System.out.println("=================="));
    }

    /**
     * 直接丢弃策略：当提交的时任务队列已满，直接将提交的任务丢掉。
     * @throws InterruptedException
     */
    public static void testDiscardPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,2,
                30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1), Thread::new
                ,new ThreadPoolExecutor.DiscardPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i->executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() +" FINISHED");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        executor.submit(() -> System.out.println("=================="));
    }

    /**
     * 提交任务时任务队列已满，直接调用提交任务的线程执行该提交任务
     * @throws InterruptedException
     */
    public static void testCallerRunsPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,2,
                30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1), Thread::new
                ,new ThreadPoolExecutor.CallerRunsPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i->executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() +" FINISHED");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " IN");
            System.out.println("==================");
        });
    }

    /**
     * 提交任务时任务队列已满，将丢弃任务队列中最老的任务替换成新提交的任务。
     * @throws InterruptedException
     */
    public static void testDiscardOldestPolicy() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,2,
                30, TimeUnit.SECONDS,new ArrayBlockingQueue<>(1), Thread::new
                ,new ThreadPoolExecutor.DiscardOldestPolicy());
        IntStream.rangeClosed(1, 3).boxed().forEach(i->executor.execute(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() +" FINISHED");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.SECONDS.sleep(1);
        executor.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " IN");
            System.out.println("==================");
        });
    }
}
