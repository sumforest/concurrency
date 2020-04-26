package com.sen.concurrency3.juc.executors;

import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/18 19:38
 * @Description:
 */
public class CompletableFutureExample4 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // System.out.println(testGetNow().getNow("预先准备的结果"));
        // testComplete();
        // System.out.println(future.complete("World"));
        // CompletableFuture<String> future = testJoin();
        // TimeUnit.SECONDS.sleep(1);
        // System.out.println(future.join());
        // System.out.println("=================");
        // testObtrudeException();

        // 测试CompletableFuture异常处理
        CompletableFuture<String> future = testErrorHandler();
        System.out.println("====================" + future.get());
        Thread.currentThread().join();
    }

    /**
     * {@link CompletableFuture}设置异常处理
     * @return
     */
    private static CompletableFuture<String> testErrorHandler() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        future.thenApply(s -> {
            System.out.println("=========keep move=============");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Integer.parseInt("s");
            System.out.println("========= end ======================");
            return s + " World";
        }).exceptionally(Throwable::getMessage).thenAccept(System.out::println);
        return future;
    }

    /**
     *  future.obtrudeException(new Exception("I am error"));设置异常，调用future.get()抛出定义的异常。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void testObtrudeException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
        future.obtrudeException(new Exception("I am error"));
        System.out.println(future.get());
    }

    /**
     * 阻塞等待返回结果，不用异常处理
     * @return
     */
    private static CompletableFuture<String> testJoin() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
    }

    /**
     * complete()调用之后直接返回True并取消调用还没运行的任务同时丢弃返回的结果，调用get()获取complete()
     * 设定的值。
     * @return
     */
    private static CompletableFuture<String> testComplete(){
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
    }

    /**
     * 调用getNow()立马返回设定好的值
     * @return
     */
    private static CompletableFuture<String> testGetNow() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " in");
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });
    }
}
