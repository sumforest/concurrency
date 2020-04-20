package com.sen.concurrency3.juc.utils.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Sen
 * @Date: 2019/12/16 13:54
 * @Description:
 */
public class ReentrantReadWriteLockExample {

    // private static final ReentrantLock lock = new ReentrantLock();

    private static final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final Lock writeLock = readWriteLock.writeLock();

    private static final Lock readLock = readWriteLock.readLock();
    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(ReentrantReadWriteLockExample::write);
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(ReentrantReadWriteLockExample::write);
        t2.start();
    }

    private static void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " is writing");
            data.add(Thread.currentThread().getId());
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    private static void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " is reading");
            TimeUnit.SECONDS.sleep(5);
            data.forEach(System.out::println);
            System.out.println(Thread.currentThread().getName() + " ==================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
    }
}
