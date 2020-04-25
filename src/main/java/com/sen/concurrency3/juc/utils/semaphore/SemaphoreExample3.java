package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 01:49
 * @Description: 调用 {@code acquireUninterruptibly()} 方法获取临界资源而陷入阻塞的线程不能被
 * {@code interrupt()} 打断
 */
public class SemaphoreExample3 {

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
         Thread t1 = new Thread(() -> {
             System.out.println(Thread.currentThread().getName() + " in");
             try {
                 semaphore.acquire();
                 System.out.println(Thread.currentThread().getName() + " get the semaphore");
                 TimeUnit.SECONDS.sleep(5);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }finally {
                 semaphore.release();
                 System.out.println(Thread.currentThread().getName() + " out");
             }
         });

        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                semaphore.acquireUninterruptibly();
                System.out.println(Thread.currentThread().getName() + " get the semaphore");
                // TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " out");
            }
        });

        t1.start();
        t2.start();

        TimeUnit.MILLISECONDS.sleep(40);
        t2.interrupt();
    }
}
