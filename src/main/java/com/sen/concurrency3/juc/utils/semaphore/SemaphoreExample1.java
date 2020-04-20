package com.sen.concurrency3.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 01:18
 * @Description:
 */
public class SemaphoreExample1 {

    public static void main(String[] args) {

        final SemaphoreLock lock = new SemaphoreLock();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " start");
                try {
                lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get the lock");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                    System.out.println(Thread.currentThread().getName() + " release");
                }
            }).start();
        }
    }

    private static class SemaphoreLock{

        private final Semaphore semaphore = new Semaphore(1);

        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }
}
