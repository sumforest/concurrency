package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 22:30
 * @Description:
 */
public class FutureExample2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // testIsDone();
        // testCancel();
        testCancel2();
    }

    /**
     * 线程被不正常的终结、异常、取消都会返回true
     */
    private static void testIsDone() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=====================");
            return 10;
        });
        TimeUnit.SECONDS.sleep(5);
        System.out.println(future.isDone());
    }

    /**
     * {@link Future#cancel(boolean)} true 表示允许被取消，false不允许被取消
     * 1.当任务已经被完成返回false
     * 2.任务已经被取消返回false
     * 3.其他未知问题返回false
     */
    private static void testCancel() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = null;
        try {
            future = executor.submit(() -> {
                /*try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                throw new RuntimeException();
              /*  System.out.println("=====================");
                return 10;*/
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(future.cancel(true));
        System.out.println(future.cancel(true));
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());
    }

    /**
     * 取消future
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void testCancel2() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool(r->{
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        final AtomicBoolean falg = new AtomicBoolean(true);
        Future<Integer> future = executor.submit(() -> {
            /*try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("=====================");
            while (falg.get() /*!Thread.currentThread().isInterrupted()*/) {

            }
            System.out.println("=====================");
            return 10;
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(future.cancel(true));
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());
        System.out.println("result:" + future.get());
    }
}
