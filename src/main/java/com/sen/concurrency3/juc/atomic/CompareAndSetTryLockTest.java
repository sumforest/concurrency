package com.sen.concurrency3.juc.atomic;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 22:09
 * @Description:
 */
public class CompareAndSetTryLockTest {

    public final static ComarenAndSetTryLock LOCK = new ComarenAndSetTryLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    doSomething2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (GetLockFailedExeption getLockFailedExeption) {
                    getLockFailedExeption.printStackTrace();
                }
            }).start();
        }
    }

    public static void doSomething2() throws InterruptedException, GetLockFailedExeption {
        try {
            LOCK.tryLock();
            System.out.println(Thread.currentThread().getName() + " get the lock");
            Thread.sleep(100_000);
        } finally {
            LOCK.unlock();
        }
    }

    public static void doSomething() throws InterruptedException {
        synchronized (CompareAndSetTryLockTest.class) {
            System.out.println(Thread.currentThread().getName() + " get the lock");
            Thread.sleep(100_000);
        }
    }
}
