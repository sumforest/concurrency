package com.sen.concurrency3.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/15 22:18
 * @Description: {@link CyclicBarrier} 调用 {@code await()}达到设定的此时，将继续执行
 */
public class CyclicBarrierExample1 {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2
                ,()-> System.out.println("All thread are finished to work"));
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " is finished to work");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " release");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + " is finished to work");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + " release");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        /*while (true) {
            System.out.println("Waiting number: " + cyclicBarrier.getNumberWaiting());
            System.out.println("CyclicBarrier Parties: " + cyclicBarrier.getParties());
            System.out.println(cyclicBarrier.isBroken());
            TimeUnit.SECONDS.sleep(2);
        }*/
    }
}
