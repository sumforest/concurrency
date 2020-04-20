package com.sen.concurrency3.juc.collections.concurrent;

import sun.java2d.pipe.SpanIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/20 03:05
 * @Description:
 */
public class ConcurrentHashMapVSConcurrentSkipListMap {

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

    private static Map<Class<?>, List<Entry>> result = new HashMap<Class<?>, List<Entry>>() {
        {
            put(ConcurrentHashMap.class, new ArrayList<>());
            put(ConcurrentSkipListMap.class, new ArrayList<>());
        }
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i = 10; i <= 100; i += 10) {
            performance(new ConcurrentHashMap<>(), i);
            performance(new ConcurrentSkipListMap<>(), i);
        }
        result.forEach((k,v) -> {
            System.out.println(k+ "测试结果：");
            v.forEach(System.out::println);
            System.out.println("******************************************************************");
        });
    }

    private static void performance(Map<String, Integer> map, int threshold) throws InterruptedException {
        System.out.println("开始测试-->" + map.getClass() + "线程数：" + threshold);
        long countTime = 0L;
        final int MAX_THRESHOLD = 500000;
        for (int i = 0; i < 5; i++) {
            final AtomicInteger count = new AtomicInteger();
            map.clear();
            long startTime = System.nanoTime();
            ExecutorService executorService = Executors.newFixedThreadPool(threshold);
            for (int k = 0; k < threshold; k++) {
                executorService.submit(() -> {
                    for (int j = 0; j < MAX_THRESHOLD && count.getAndIncrement() < MAX_THRESHOLD; j++) {
                        Integer randomNum = (int) Math.ceil(Math.random()) * 60000;
                        map.get(String.valueOf(randomNum));
                        map.put(String.valueOf(randomNum), randomNum);
                    }
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.HOURS);
            long endTime = System.nanoTime();
            long period = (endTime - startTime) / 1000000;
            countTime += period;
            System.out.println(" 第【" + (i + 1) + "】" + "结果: " + map.getClass() + " 花费： " + period + " ms");
        }
        result.get(map.getClass()).add(new Entry(threshold, countTime / 5));
        System.out.println(map.getClass() + " 平均表现: " + (countTime / 5) + "ms");
        System.out.println("============================================================================");
    }
}
