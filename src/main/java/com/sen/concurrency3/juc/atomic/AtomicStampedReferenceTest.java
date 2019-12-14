package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 01:25
 * @Description: 解决原子类型的ABA问题
 */
public class AtomicStampedReferenceTest {

    private final static AtomicStampedReference<Integer> STAMPED_REFERENCE = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean success = STAMPED_REFERENCE.compareAndSet(100, 101, STAMPED_REFERENCE.getStamp(), STAMPED_REFERENCE.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " stamp = " +STAMPED_REFERENCE.getStamp());
            System.out.println(Thread.currentThread().getName() + " success: " +success);
            success = STAMPED_REFERENCE.compareAndSet(101, 100, STAMPED_REFERENCE.getStamp(), STAMPED_REFERENCE.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + " stamp = " +STAMPED_REFERENCE.getStamp());
            System.out.println(Thread.currentThread().getName() + " success: " +success);
        });

        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int stamp = STAMPED_REFERENCE.getStamp();
            System.out.println(Thread.currentThread().getName() + " stamp = " + stamp);
            boolean success = STAMPED_REFERENCE.compareAndSet(100, 101, stamp, stamp + 1);
            // boolean success = STAMPED_REFERENCE.compareAndSet(100, 101, STAMPED_REFERENCE.getStamp(), STAMPED_REFERENCE.getStamp() + 1);
            // System.out.println(Thread.currentThread().getName() + " stamp = " +STAMPED_REFERENCE.getStamp());
            System.out.println(Thread.currentThread().getName() + " success: " +success);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
