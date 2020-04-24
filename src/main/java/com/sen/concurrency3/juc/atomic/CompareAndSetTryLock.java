package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/14 22:14
 * @Description:
 */
public class CompareAndSetTryLock {

    private final static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static Thread currentThread;

    public void tryLock() throws GetLockFailedExeption {
        boolean success = atomicInteger.compareAndSet(0, 1);
        if (!success) {
            throw new GetLockFailedExeption(Thread.currentThread().getName() + " get tryLock failed");
        }
        currentThread = Thread.currentThread();
    }

    public void unlock() {
        if (currentThread == Thread.currentThread()) {
            atomicInteger.compareAndSet(1, 0);
        }
    }
}
