package com.sen.concurrency3.juc.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/18 15:17
 * @Description:
 */
public class CompletableFutureExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Future缺点1：没有回调，调用future.get()陷入阻塞
       /* ExecutorService executorService = Executors.newFixedThreadPool(5);
        Future<Integer> future = executorService.submit(() -> {
            int value = ThreadLocalRandom.current().nextInt(10);
            System.out.println(Thread.currentThread().getName() + " i will be sleep " + value);
            try {
                TimeUnit.SECONDS.sleep(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is done " + value);
            return value;
        });
        System.out.println(future.get());
        System.out.println("===========over===========");*/

        //CompletableFuture维护的线程池的线程默认是守护线程维护的线程池的线程默认是守护线程，所以main线程退出后其自身也退出
        //CompletableFuture解决Future不能回调以及调用Future.get()阻塞的缺点
        /*CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("Done"));
        Thread.currentThread().join();
        System.out.println("===========over===========");*/

        //Future缺点2：一批的任务必须分阶段执行需要等所有的任务都有返回结果之后才能返回，性能有损耗
        /*ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Callable<Integer>> callableList = IntStream.rangeClosed(1, 5).boxed().map(i -> get()).collect(Collectors.toList());
        executorService.invokeAll(callableList).stream().map(future->{
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).parallel().forEach(CompletableFutureExample1::display);
        System.out.println("===========over===========");*/

        IntStream.rangeClosed(1, 5).boxed().forEach(i -> CompletableFuture.
                supplyAsync(CompletableFutureExample1::get).
                thenAccept(CompletableFutureExample1::display).
                whenComplete((v, t) -> System.out.println(Thread.currentThread().getName() + "****Done")));
        Thread.currentThread().join();
        System.out.println("===========over===========");
    }

    private static void display(int data) {
        int value = ThreadLocalRandom.current().nextInt(10);
       /* try {
            TimeUnit.SECONDS.sleep(value);*/
            System.out.println(Thread.currentThread().getName() + " get data = " + data);
        /*} catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    private static int get() {
            int value = ThreadLocalRandom.current().nextInt(10);
            System.out.println(Thread.currentThread().getName() + " i will be sleep " + value);
            try {
                TimeUnit.SECONDS.sleep(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is done " + value);
            return value;
    }
}
