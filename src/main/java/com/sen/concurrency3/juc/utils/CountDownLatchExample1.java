package com.sen.concurrency3.juc.utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/15 18:47
 * @Description: {@link CountDownLatch} 应用一：等待所有子线程完成任务在执行父线程逻辑
 */
public class CountDownLatchExample1 {

    private final static Random RANDOM = new Random(System.currentTimeMillis());

    private final static ExecutorService SERVICE = Executors.newFixedThreadPool(2);

    private final static CountDownLatch LATCH = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < arr.length; i++) {
            SERVICE.submit(new Arr(arr, i));
            Thread.sleep(RANDOM.nextInt(500));
        }
        SERVICE.shutdown();
        // SERVICE.awaitTermination(1, TimeUnit.HOURS);
        LATCH.await();
        System.out.println("All tasks finished");
    }

    private static class Arr implements Runnable {

        private final int[] arr;

        private final int index;

        public Arr(int[] arr, int index) {
            this.arr = arr;
            this.index = index;
        }

        @Override
        public void run() {
            int value = arr[index];
            if (value % 2 == 0) {
                arr[index] = value * 2;
            } else {
                arr[index] = value + 1;
            }
            System.out.println(Thread.currentThread().getName() + " arr[" + index + "] =" + arr[index]);
            LATCH.countDown();
        }
    }
}
