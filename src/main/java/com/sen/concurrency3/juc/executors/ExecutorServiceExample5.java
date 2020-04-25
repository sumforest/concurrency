package com.sen.concurrency3.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/17 21:32
 * @Description: 直接网等待队列里面添加任务不会导致线程池动态扩容
 */
public class ExecutorServiceExample5 {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.execute(()-> System.out.println("hello"));
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(executor.getActiveCount());
        executor.getQueue().add(() -> System.out.println("I am from queue"));
        executor.getQueue().add(() -> System.out.println("I am from queue"));
        executor.getQueue().add(() -> System.out.println("I am from queue"));
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println(executor.getActiveCount());
    }
}
