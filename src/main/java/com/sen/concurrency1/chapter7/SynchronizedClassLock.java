package com.sen.concurrency1.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 18:34
 * @Description:
 */
public class SynchronizedClassLock {

    static {
        synchronized (SynchronizedClassLock.class){
            System.out.println("static->" + Thread.currentThread().getName());

            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void m1() {
        System.out.println("m1->" + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void m2() {
        System.out.println("m2->" + Thread.currentThread().getName());
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
