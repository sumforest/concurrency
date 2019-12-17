package com.sen.concurrency3.juc.executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/17 23:46
 * @Description:
 */
public class CompletionServiceExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // futureDefect1();
        futureDefect2();
    }

    /**
     * Future 缺点1：没有回调，主动调用时会阻塞
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void futureDefect1() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executor.submit(() -> {
            sleepSeconds(3);
            return 10;
        });
        System.out.println(future.get());
    }

    /**
     * Future缺点2：先完成的任务无法做到先返回，导致性能丢失；
     * @throws InterruptedException
     */
    private static void futureDefect2() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Callable<Integer>> callables = Arrays.asList(() -> {
            sleepSeconds(10);
            System.out.println("callable:--->10");
            return 10;
        }, () -> {
            sleepSeconds(20);
            System.out.println("callable:--->20");
            return 20;
        });
        List<Future<Integer>> futures = executor.invokeAll(callables);
        futures.forEach(f-> {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    private static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
