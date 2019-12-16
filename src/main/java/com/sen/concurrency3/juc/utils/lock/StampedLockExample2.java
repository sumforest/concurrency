package com.sen.concurrency3.juc.utils.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

/**
 * @Auther: Sen
 * @Date: 2019/12/16 16:02
 * @Description:
 */
public class StampedLockExample2 {

    private static final StampedLock LOCK = new StampedLock();

    private static final List<Long> data = new ArrayList<>();

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (; ; )
                read();
        };
        Runnable writeTask = () -> {
            for (; ; )
                write();
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
        long stamp = LOCK.tryOptimisticRead();
        if (LOCK.validate(stamp)) {
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
