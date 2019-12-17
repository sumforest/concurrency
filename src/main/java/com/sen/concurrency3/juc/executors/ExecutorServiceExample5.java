package com.sen.concurrency3.juc.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 21:32
 * @Description:
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
