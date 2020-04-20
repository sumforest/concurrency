package com.sen.concurrency3.juc.utils;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Sen
 * @Date: 2019/12/15 19:35
 * @Description:
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

        Thread.currentThread().join();
    }
}
