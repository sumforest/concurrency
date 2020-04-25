package com.sen.concurrency3.juc.executors;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/17 19:46
 * @Description: 线程池执行任务的几种方式
 */
public class ExecutorServiceExample4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // testInvokeAny();
        // testInvokeAnyTimeOut();
        // testInvokeAll();
        // testInvokeAllTimeOut();
        // testSubmit();
        testSubmitReturn();
    }

    /**
     * {@link ThreadPoolExecutor#invokeAny(Collection)}
     * 执行一系列任务，哪个先执行完哪个先返回，然后尝试取消还没执行完成的任务；总的来说就是在众多的任务中只选取一个结果
     * 有一个结果之后丢弃其他还未执行完的任务结果和取消还未开始执行的任务
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testInvokeAny() throws ExecutionException, InterruptedException {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<Callable<Integer>> callableList = IntStream.rangeClosed(1, 5)
                .boxed()
                .map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " FINISHED");
                    return i;
                })
                .collect(Collectors.toList());

        Integer value = executor.invokeAny(callableList);
        System.out.println("==========================");
        System.out.println(value);
    }

    /**
     * {@link ThreadPoolExecutor#invokeAny(Collection, long, TimeUnit)}
     * 执行超时抛出 {@link TimeoutException}
     */
    private static void testInvokeAnyTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        Integer value = executor.invokeAny(IntStream.rangeClosed(1, 5)
                .boxed()
                .map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " FINISHED");
                    return i;
                })
                .collect(Collectors.toList()), 1, TimeUnit.SECONDS);

        System.out.println("==========================");
        System.out.println(value);
    }

    /**
     * {@link ThreadPoolExecutor#invokeAll(Collection)}
     * 执行完所有的任务返回future集合，阻塞执行
     */
    private static void testInvokeAll() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        executor.invokeAll(IntStream
                .rangeClosed(1, 5)
                .boxed()
                .map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " FINISHED");
                    return i;
                })
                .collect(Collectors.toList()))
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);

        System.out.println("+++++++++++++++++++++++++++++++++++++");
    }

    /**
     * {@link ThreadPoolExecutor#invokeAny(Collection, long, TimeUnit)}
     * 执行完所有的任务返回future集合，阻塞执行
     * 抛出异常 CancellationException
     */
    private static void testInvokeAllTimeOut() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        executor.invokeAll(IntStream.rangeClosed(1, 5)
                .boxed()
                .map(i -> (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(Thread.currentThread().getName() + " FINISHED");
                    return i;
                }).collect(Collectors.toList()), 1, TimeUnit.SECONDS)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);

        System.out.println("+++++++++++++++++++++++++++++++++++++");
    }

    /**
     * {@link ThreadPoolExecutor#submit(Runnable)}
     * 调用future.get()阻塞，执行完成后释放
     */
    private static void testSubmit() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

        Future<?> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISHED";
        });

        System.out.println("Result:" + future.get());
    }

    /**
     * {@link ThreadPoolExecutor#submit(Callable)}
     * 调用future.get()阻塞，执行完成后释放
     */
    private static void testSubmitReturn() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        // 先定义结果执行完后返回
        String result = "DONE";
        Future<?> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);
        System.out.println("Result:" + future.get());
    }
}
