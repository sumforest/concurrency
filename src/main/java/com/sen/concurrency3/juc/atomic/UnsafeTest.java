package com.sen.concurrency3.juc.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: Sen
 * @Date: 2019/12/15 15:33
 * @Description: 比较 {@code synchronized}、{@link ReentrantLock}、{@link AtomicLong}、{@link Unsafe}
 * 实现线程安全的效率
 */
public class UnsafeTest {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
        /*Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);*/

       /* Unsafe unsafe = getUnsafe();
        System.out.println(unsafe);*/

        /**
         * 测试结果：
         * StupidCounter
         *  Counter:9907765
         *  Takes time : 126
         * SynCounter
         *  Counter:10000000
         *  Takes time : 533
         * LockCounter
         *  Counter:10000000
         *  Takes time : 540
         * AtomicCounter
         *  Counter:10000000
         *  Takes time : 331
         * UnsafeCounter
         *  Counter:10000000
         *  Takes time : 974
         */
        ExecutorService service = Executors.newFixedThreadPool(1000);
        Counter counter = new UnsafeCounter();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            service.submit(new CounterRunnable(counter, 10000));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.println("Counter:" + counter.getCounter());
        System.out.println("Takes time : " + (end - start));
    }

    /**
     * {@link Unsafe} 实现锁
     */
    private static class UnsafeCounter implements Counter{

        private final Unsafe unsafe;

        private final long offset;

        private long count = 0;

        private UnsafeCounter() throws NoSuchFieldException {
            this.unsafe = getUnsafe();
            this.offset = unsafe.objectFieldOffset(UnsafeCounter.class.getDeclaredField("count"));
        }

        /**
         * Unsafe无锁实现线程安全
         */
        @Override
        public void increment() {
            long current = count;
            while (!unsafe.compareAndSwapLong(this, offset, current, current + 1)) {
                // 更新失败重试
                current = count;
            }
        }

        @Override
        public long getCounter() {
            return count;
        }
    }

    /**
     * 原子类实现
     */
    private static class AtomicCounter implements Counter{

        private final AtomicLong atomicLong = new AtomicLong();

        @Override
        public void increment() {
            atomicLong.incrementAndGet();
        }

        @Override
        public long getCounter() {
            return atomicLong.get();
        }
    }

    /**
     * 显示锁实现
     */
    private static class LockCounter implements Counter{

        private final Lock lock = new ReentrantLock();

        private long count = 0;

        @Override
        public synchronized void increment() {
            lock.lock();
            try {
                count++;
            }finally {
                lock.unlock();
            }
        }

        @Override
        public long getCounter() {
            return count;
        }
    }

    /**
     * Synchronized实现锁
     */
    private static class SynCounter implements Counter{

        private long count = 0;

        @Override
        public synchronized void increment() {
            count++;
        }

        @Override
        public long getCounter() {
            return count;
        }
    }

    /**
     * 无锁实现
     */
    private static class StupidCounter implements Counter{

        private long count = 0;

        @Override
        public void increment() {
            count++;
        }

        @Override
        public long getCounter() {
            return count;
        }
    }

    /**
     * 定义方法接口
     */
    private interface Counter{

        void increment();

        long getCounter();
    }

    /**
     * 用于提交任务的类
     */
    private static class CounterRunnable implements Runnable{

        private final Counter counter;

        private final int num;

        public CounterRunnable(Counter counter, int num) {
            this.counter = counter;
            this.num = num;
        }

        @Override
        public void run() {
            for (int i = 0; i < num; i++) {
                counter.increment();
            }
        }
    }

    /**
     * 获取 {@link Unsafe} 实例
     * @return 异常返回null
     */
    private static Unsafe getUnsafe(){
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
