package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 02:11
 * @Description: 使用 {@linkplain Semaphore#drainPermits()} 排干所有共享资源，
 * 使用 {@linkplain Semaphore#tryAcquire(long, TimeUnit)} 超过等待时间后立即
 * 返回，获取共享资源失败。
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
                // 获取临界资源失败
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
