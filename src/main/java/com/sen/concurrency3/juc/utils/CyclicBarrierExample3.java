package com.sen.concurrency3.juc.utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/15 23:03
 * @Description: 使用 {@link CountDownLatch} 实现 {@link java.util.concurrent.CyclicBarrier} 的
 * {@code await()} 方法。
 */
public class CyclicBarrierExample3 {

    public static void main(String[] args) {
        MyCyclicBarrier myCyclicBarrier = new MyCyclicBarrier(2
                , () -> System.out.println("All threads are finished to work"));
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                myCyclicBarrier.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                myCyclicBarrier.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    private static class MyCyclicBarrier extends CountDownLatch{

        private final Runnable runnable;

        public MyCyclicBarrier(int count, Runnable runnable) {
            super(count);
            this.runnable = runnable;
        }

        @Override
        public void countDown() {
            super.countDown();
            if (super.getCount() == 0) {
                runnable.run();
            }
        }
    }
}
