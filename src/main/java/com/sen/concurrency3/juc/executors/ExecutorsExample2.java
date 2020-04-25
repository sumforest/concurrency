package com.sen.concurrency3.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Author: Sen
 * @Date: 2019/12/17 02:47
 * @Description: 线程池的使用2 {@code newWorkStealingPool} 工作窃取线程池
 */
public class ExecutorsExample2 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newWorkStealingPool();

        List<Callable<String>> callableList = IntStream.rangeClosed(1, 20)
                .boxed()
                .map(i -> (Callable<String>) () -> {
                    System.out.println("Thread:" + Thread.currentThread().getName());
                    sleepSecond(2);
                    return "task" + i;
                })
                .collect(toList());

        service.invokeAll(callableList).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
    }

    private static void sleepSecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
