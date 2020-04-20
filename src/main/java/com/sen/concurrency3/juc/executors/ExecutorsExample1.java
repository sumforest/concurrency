package com.sen.concurrency3.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/17 01:59
 * @Description:
 */
public class ExecutorsExample1 {

    public static void main(String[] args) throws InterruptedException {
        // userCacheThreadPool();
        // userFixedThreadPool();
        userSingleThreadPool();
    }

    /**
     *  1.SingleThreadExecutor是由FinalizableDelegatedExecutorService包装实现的与ThreadPoolExecutor没由关系
     *    所以不具备监控的api
     *  2.SingleThreadExecutor线程池和单独启动一个线程的区别：
     *     2.1 单独启动的线程在执行完run方法的代码之后会被销毁，而单个线程的线程池会一直存活
     *     2.2 单个线程的线程池里面由队列维护它所需要执行的一系列任务，而单独启动的线程没有。
     * @throws InterruptedException
     */
    private static void userSingleThreadPool() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        IntStream.rangeClosed(1, 10).forEach((i) ->
                service.execute(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " in");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(3);
        service.shutdown();
    }

    /**
     * If additional tasks are submitted when all threads are active,
     * they will wait in the queue until a thread is available.
     *
     * new ThreadPoolExecutor(nThreads, nThreads,
     *     0L, TimeUnit.MILLISECONDS,
     *     new LinkedBlockingQueue<Runnable>());
     * 1.所有任务完成之后不会自动关闭，需要显示调用shutdown()来关闭
     */
    private static void userFixedThreadPool() throws InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        System.out.println(service.getActiveCount());
        IntStream.rangeClosed(1, 10).forEach((i) ->
                service.execute(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " in");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(3);
        System.out.println(service.getActiveCount());
        service.shutdown();
    }
    /**
     * These pools will typically improve the performance
     * of programs that execute many short-lived asynchronous tasks.
     * new ThreadPoolExecutor(0, Integer.MAX_VALUE,
     *          60L, TimeUnit.SECONDS,
     *          new SynchronousQueueExample<Runnable>());
     *  1.使用CacheThreadPool注意任务的数量和执行的时间，过多的任务和过长的执行时间会导致虚拟机栈溢出
     *  2.任务执行完后60s自动关闭
     */
    private static void userCacheThreadPool() throws InterruptedException {
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        System.out.println(service.getActiveCount());
        IntStream.rangeClosed(1, 100).forEach((i) ->
                service.execute(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " in");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
        );
        TimeUnit.SECONDS.sleep(1);
        System.out.println(service.getActiveCount());
    }
}
