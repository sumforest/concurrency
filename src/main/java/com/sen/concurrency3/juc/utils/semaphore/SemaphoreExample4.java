package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Sen
 * @Date: 2019/12/16 02:11
 * @Description:
 */
public class SemaphoreExample4 {

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(5);

        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                semaphore.drainPermits();
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release(5);
                System.out.println(Thread.currentThread().getName() + " out");
            }
        });

        t1.start();

        Thread t2 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                semaphore.tryAcquire(2,TimeUnit.SECONDS);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " out");
            }
        });
        TimeUnit.SECONDS.sleep(1);
        t2.start();


    }
}
