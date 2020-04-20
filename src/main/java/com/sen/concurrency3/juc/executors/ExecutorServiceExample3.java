package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/17 18:28
 * @Description:
 */
public class ExecutorServiceExample3 {

    public static void main(String[] args) throws InterruptedException {
        // test();
        // testAllowCoreThreadTimeOut();
        // testRemove();
        // testPrestartCoreThread();
        // testPrestartAllCoreThreads();
        testThreadPoolAdvice();
    }

    /**
     * 线程池创建时的初始大小时0，随着没每提交一个任务而增一次，直到最大值。
     * @throws InterruptedException
     */
    private static void test() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        System.out.println(executor.getActiveCount());
        IntStream.rangeClosed(1, 5).boxed().forEach(i ->{
            System.out.println(executor.getActiveCount());
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Finally:" + executor.getActiveCount());
    }

    /**
     * {@link ThreadPoolExecutor#allowCoreThreadTimeOut(boolean)}方法可以把核心线程在超时后回收
     * @throws InterruptedException
     */
    private static void testAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executor.setKeepAliveTime(10, TimeUnit.SECONDS);
        executor.allowCoreThreadTimeOut(true);
        System.out.println(executor.getActiveCount());
        IntStream.rangeClosed(1, 5).boxed().forEach(i ->{
            System.out.println(executor.getActiveCount());
            executor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Finally:" + executor.getActiveCount());
    }

    private static void testRemove() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
                IntStream.rangeClosed(1, 2).boxed().forEach(i->executor.execute(()-> {
            try {
                System.out.println(Thread.currentThread().getName() + " working");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        TimeUnit.MILLISECONDS.sleep(200);
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName() + " I am from Main Thread and I am never to run");
        executor.execute(runnable);
        TimeUnit.MILLISECONDS.sleep(20);
        executor.remove(runnable);
    }

    private static void testPrestartCoreThread() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartCoreThread());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartCoreThread());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartCoreThread());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartCoreThread());
    }

    private static void testPrestartAllCoreThreads() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartAllCoreThreads());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartAllCoreThreads());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartAllCoreThreads());

        System.out.println(executor.getActiveCount());
        System.out.println(executor.prestartAllCoreThreads());
    }

    private static void testThreadPoolAdvice() {
        ThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 2,
                30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());
       executor.execute(new MyRunnable(1){
           @Override
           public void run() {
               System.out.println(1 / 0);
           }
       });
    }

    private abstract static class MyRunnable implements Runnable {
        private final int no;

        protected MyRunnable(int no) {
            this.no = no;
        }

        public int getNo() {
            return no;
        }
    }

    private static class MyThreadPoolExecutor extends ThreadPoolExecutor{

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println( "MyRunnable NO. " + ((MyRunnable)r).getNo());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (t == null) {
                System.out.println("FINISHED");
            }else {
                t.printStackTrace();
            }
        }

    }
}
