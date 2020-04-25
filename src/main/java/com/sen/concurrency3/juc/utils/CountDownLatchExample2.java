package com.sen.concurrency3.juc.utils;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Sen
 * @Date: 2019/12/15 19:35
 * @Description: {@link CountDownLatch} 应用二：一个线程完成后唤醒多个线程工作
 */
public class CountDownLatchExample2 {

    private final static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("prepare to deal with data");
            try {
                Thread.sleep(1000);
                latch.await();
                System.out.println("begin to deal with data");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                latch.await();
                System.out.println("release");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            System.out.println("search data from DB");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
            }
        }).start();

        // main线程等待父线程结束，而main线程的父线程是JVM线程是不会退出的main线程回一直阻塞
        Thread.currentThread().join();
    }
}
