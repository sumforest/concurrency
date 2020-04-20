package com.sen.concurrency3.juc.utils.lock;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Sen
 * @Date: 2019/12/16 02:50
 * @Description:
 */
public class ReentrantLockExample {

    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        /*Thread t1 = new Thread(ReentrantLockExample::lockTest);
        t1.start();
        Thread t2 = new Thread(ReentrantLockExample::lockTest);
        t2.start();*/

        Thread t1 = new Thread(ReentrantLockExample::tryLockTest);
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(ReentrantLockExample::tryLockTest);
        t2.start();

    }

    private static void tryLockTest() {
        System.out.println(Thread.currentThread().getName() + " in");
        if (lock.tryLock()) {
            try {
                System.out.println(Thread.currentThread().getName() + " get the lock");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " out");
            }
        } else {
            System.out.println(Thread.currentThread().getName() + " failed to get lock");
        }
    }

    private static void lockInterruptiblyTest() {
        try {
            System.out.println(Thread.currentThread().getName() + " in");
            lock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " get the lock");
            while (true) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " out");
        }
    }

    private static void lockTest() {
        try {
            System.out.println(Thread.currentThread().getName() + " in");
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " get the lock");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " out");
        }
    }
}
