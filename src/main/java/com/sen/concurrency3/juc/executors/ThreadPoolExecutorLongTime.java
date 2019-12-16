package com.sen.concurrency3.juc.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 01:11
 * @Description: 线程池退出--没有异常抛出
 */
public class ThreadPoolExecutorLongTime {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(10, 20, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),r->{
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());
        IntStream.rangeClosed(1, 10).boxed().forEach(i->executor.submit(()->{
            while (true){

            }
        }));

        TimeUnit.SECONDS.sleep(5);
        // executor.shutdownNow();
        System.out.println("===================All worker finished=================");
    }
}
