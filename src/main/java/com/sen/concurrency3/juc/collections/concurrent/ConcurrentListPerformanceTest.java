package com.sen.concurrency3.juc.collections.concurrent;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 03:05
 * @Description:
 */
public class ConcurrentListPerformanceTest {

    private static class Entry {
        private final int threshold;
        private final long ms;

        @Override
        public String toString() {
            return
                    "线程数=" + threshold +
                            ", 花费时间=" + ms + "ms";
        }

        public Entry(int threshold, long ms) {
            this.threshold = threshold;
            this.ms = ms;
        }

        public int getThreshold() {
            return threshold;
        }

        public long getMs() {
            return ms;
        }
    }

    private static Map<String, List<Entry>> result = new HashMap<>();

    /*
    SynchronizedRandomAccessList测试结果：
    线程数=10, 花费时间=9ms
    线程数=20, 花费时间=8ms
    线程数=30, 花费时间=8ms
    线程数=40, 花费时间=8ms
    线程数=50, 花费时间=8ms
    线程数=60, 花费时间=8ms
    线程数=70, 花费时间=9ms
    线程数=80, 花费时间=15ms
    线程数=90, 花费时间=10ms
    线程数=100, 花费时间=9ms
    ******************************************************************
    ConcurrentLinkedQueue测试结果：
    线程数=10, 花费时间=11ms
    线程数=20, 花费时间=10ms
    线程数=30, 花费时间=11ms
    线程数=40, 花费时间=12ms
    线程数=50, 花费时间=13ms
    线程数=60, 花费时间=12ms
    线程数=70, 花费时间=16ms
    线程数=80, 花费时间=16ms
    线程数=90, 花费时间=15ms
    线程数=100, 花费时间=14ms
    ******************************************************************
    CopyOnWriteArrayList测试结果：
    线程数=10, 花费时间=675ms
    线程数=20, 花费时间=604ms
    线程数=30, 花费时间=610ms
    线程数=40, 花费时间=603ms
    线程数=50, 花费时间=624ms
    线程数=60, 花费时间=637ms
    线程数=70, 花费时间=640ms
    线程数=80, 花费时间=654ms
    线程数=90, 花费时间=651ms
    线程数=100, 花费时间=636ms
    ******************************************************************
    */
    public static void main(String[] args) throws InterruptedException {
        for (int i = 10; i <= 100; i += 10) {
            performance(new ConcurrentLinkedQueue<>(), i);
            performance(new CopyOnWriteArrayList<>(), i);
            performance(Collections.synchronizedList(new ArrayList<>()), i);
        }
        result.forEach((k, v) -> {
            System.out.println(k + "测试结果：");
            v.forEach(System.out::println);
            System.out.println("******************************************************************");
        });
    }

    private static void performance(Collection<String> list, int threshold) throws InterruptedException {
        System.out.println("开始测试-->" + list.getClass().getSimpleName() + "线程数：" + threshold);
        long countTime = 0L;
        final int MAX_THRESHOLD = 100000;
        for (int i = 0; i < 5; i++) {
            final AtomicInteger count = new AtomicInteger();
            list.clear();
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(threshold);
            for (int k = 0; k < threshold; k++) {
                executorService.submit(() -> {
                    for (int j = 0; j < MAX_THRESHOLD && count.getAndIncrement() < MAX_THRESHOLD; j++) {
                        Integer randomNum = (int) Math.ceil(Math.random()) * 60000;
                        list.add(String.valueOf(randomNum));
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.HOURS);
            long endTime = System.nanoTime();
            long period = (endTime - startTime) / 1000000;
            countTime += period;
            System.out.println(" 第【" + (i + 1) + "】" + "结果: " + list.getClass().getSimpleName() + " 花费： " + period + " ms");
        }
        result.computeIfAbsent(list.getClass().getSimpleName(), k -> new ArrayList<>());
        result.get(list.getClass().getSimpleName()).add(new Entry(threshold, countTime / 5));
        System.out.println(list.getClass().getSimpleName() + " 平均表现: " + (countTime / 5) + "ms");
        System.out.println("============================================================================");
    }
}
