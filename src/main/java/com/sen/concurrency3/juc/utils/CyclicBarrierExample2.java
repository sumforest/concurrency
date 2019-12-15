package com.sen.concurrency3.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 22:55
 * @Description:
 */
public class CyclicBarrierExample2 {

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(50);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " release");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " release");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " release");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

      /*  TimeUnit.SECONDS.sleep(6);
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        cyclicBarrier.reset();
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        TimeUnit.SECONDS.sleep(5);
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        cyclicBarrier.await();*/
    }
}
