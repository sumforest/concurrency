package com.sen.concurrency3.juc.utils.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/16 16:02
 * @Description: {@link StampedLock} JDK 1.8新加入的锁是
 * {@link java.util.concurrent.locks.ReentrantLock} 的加强版，解决大多数情况是读的情况远远大于写的操作，
 * 因此可能导致写的饥饿问题。以下使用 {@link StampedLock#readLock()}、{@linkplain StampedLock#writeLock()}
 */
public class StampedLockExample1 {

    private static final StampedLock LOCK = new StampedLock();

    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; ) {
                read();
            }
        };
        Runnable writeTask = () -> {
            for (; ; ) {
                write();
            }
        };
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(readTask);
        service.submit(writeTask);
    }

    public static void read() {
        long stamp = -1;
        try {
            stamp = LOCK.readLock();
            Optional.of(data.stream().map(String::valueOf)
                    .collect(Collectors.joining("#", "R-", "")))
                    .ifPresent(System.out::println);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockRead(stamp);
        }
    }

    public static void write() {
        long stamp = -1;
        try {
            stamp = LOCK.writeLock();
            System.out.println(Thread.currentThread().getName() + " is writing");
            data.add(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }
}
