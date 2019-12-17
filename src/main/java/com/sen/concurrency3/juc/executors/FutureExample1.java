package com.sen.concurrency3.juc.executors;

import java.util.concurrent.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 21:57
 * @Description:
 */
public class FutureExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // testGet();
        testGetTimeOut();
    }

    /**
     * 调用{@link Future#get()}陷入阻塞，调用interrupt()打断正在调用get()陷入阻塞的线程；
     * 线程池里面的线程正常运行
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executor.submit(() -> {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " finished");
            return 10;
        });
        System.out.println("================I will be printed fast=============");
        Thread current = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            current.interrupt();
        }).start();
        System.out.println(future.get());
    }

    /**
     * 调用{@link Future#get(long, TimeUnit)}的线程超时被打断，线程池的线程不受影响。
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    private static void testGetTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executor.submit(() -> {
            TimeUnit.SECONDS.sleep(10);
            System.out.println(Thread.currentThread().getName() + " finished");
            return 10;
        });
        System.out.println("================I will be printed fast=============");
        Integer result = future.get(3, TimeUnit.SECONDS);
        System.out.println(result);
    }
}
