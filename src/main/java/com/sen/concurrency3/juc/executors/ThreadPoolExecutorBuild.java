package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;

/**
 * @Author: Sen
 * @Date: 2019/12/16 23:51
 * @Description: 线程池构造方法
 */
public class ThreadPoolExecutorBuild {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) buildThreadPool();
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            if (activeCount != executor.getActiveCount() || queueSize != executor.getQueue().size()) {
                System.out.println("_________________________________________");
                System.out.println("CorePoolSize:" + executor.getCorePoolSize());
                System.out.println("ActiveCount:" + executor.getActiveCount());
                System.out.println("MaximumPoolSize:" + executor.getMaximumPoolSize());
                System.out.println("QueueSize:" + executor.getQueue().size());
                queueSize = executor.getQueue().size();
                activeCount = executor.getActiveCount();
                System.out.println("_________________________________________");
            }
        }
    }

    /**
     * int corePoolSize, 核心线程数
     * int maximumPoolSize, 最大线程数
     * long keepAliveTime, 最大线程数空闲时保持时间
     * TimeUnit unit, 时间单位
     * BlockingQueue<Runnable> workQueue, 任务队列，每一个可执行的任务都先存放在队列中
     * ThreadFactory threadFactory, 创建线程的方式
     * RejectedExecutionHandler handler 超过任务队列大小时提交任务的处理策略
     */
    private static Executor buildThreadPool() {
        Executor executor = new ThreadPoolExecutor(1, 2, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("finished to build ThreadPool");
        executor.execute(()->sleepSeconds(5));
        executor.execute(()->sleepSeconds(10));
        executor.execute(()->sleepSeconds(10));
        return executor;
    }

    private static void sleepSeconds(long seconds) {
        System.out.println("*" + Thread.currentThread().getName() + " *");
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
