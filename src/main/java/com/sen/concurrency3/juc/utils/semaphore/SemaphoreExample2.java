package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/16 01:42
 * @Description:
 */
public class SemaphoreExample2 {

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " in");
                try {
                    semaphore.acquire(2);
                    System.out.println(Thread.currentThread().getName() + " get the semaphore");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(2);
                    System.out.println(Thread.currentThread().getName() + " out");
                }
            }).start();
        }
        while (true) {
            System.out.println("AP->" + semaphore.availablePermits());
            System.out.println("QL->" + semaphore.getQueueLength());
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
