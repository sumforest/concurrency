package com.sen.concurrency.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 18:34
 * @Description:
 */
public class SynchronizedThisLock {

    public synchronized void m1() {
        System.out.println("m1->" + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void m2() {
        System.out.println("m2->" + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
