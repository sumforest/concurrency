package com.sen.concurrency3.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/15 22:55
 * @Description: {@link CyclicBarrier} 调用 {@code reset()} 把其恢复到初始状态，如果有线程调用了
 * {@code cyclicBarrier.await()} 那么在调用 {@code rest()} 时会抛出异常 {@link BrokenBarrierException}
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

        TimeUnit.SECONDS.sleep(6);
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        cyclicBarrier.reset();
        System.out.println("-------------------rest()------------------------");
        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        TimeUnit.SECONDS.sleep(5);

        System.out.println(cyclicBarrier.getNumberWaiting());
        System.out.println(cyclicBarrier.getParties());
        System.out.println(cyclicBarrier.isBroken());
        cyclicBarrier.await();
    }
}
