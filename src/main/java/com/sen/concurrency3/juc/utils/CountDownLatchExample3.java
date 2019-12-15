package com.sen.concurrency3.juc.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 19:42
 * @Description:
 */
public class CountDownLatchExample3 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread main = Thread.currentThread();
        new Thread(()->{
            try {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " start");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                latch.countDown();
                // main.interrupt();
            }
        }).start();
        latch.await(1000, TimeUnit.MILLISECONDS);
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
