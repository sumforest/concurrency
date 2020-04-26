package com.sen.concurrency3.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/18 16:50
 * @Description:
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // testRunAfterBoth();
        // System.out.println(testRunAsync().get());
        // System.out.println(testSupplyAsync().get());
        // System.out.println("anyOf result:" + testAnyOf().get());
        testAllOf();
        // main线程阻塞
        Thread.currentThread().join();
    }

    /**
     * 应用场景：
     * <p>
     * post请求->{
     * basic;
     * details;
     * }
     * </p>
     * \----> insert basic
     * submit--\                       ----->调用Action(finished)
     * ----->insert details
     */
    public static void testSupplyAfterBoth() {
        CompletableFuture.supplyAsync(Object::new)
                .thenAcceptAsync(obj -> {
                    System.out.println("1==========start");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("1*************" + obj);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                })
                .runAfterBoth(CompletableFuture.supplyAsync(() -> "Hello")
                        .thenAcceptAsync(str -> {
                            System.out.println("2==========start");
                            try {
                                TimeUnit.SECONDS.sleep(3);
                                System.out.println("2*************" + str);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }), () -> System.out.println("===========finished=========="));
    }

    private static CompletableFuture<?> testRunAsync() {
        return CompletableFuture.runAsync(() -> {
            System.out.println("1==========start");
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("1*************Runnable");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("RunAsync finished"));
    }

    private static CompletableFuture<String> testSupplyAsync() {
        return CompletableFuture.supplyAsync(() -> "Hello");
    }

    /**
     * @return 返回任意一个执行结果，其他线程不再执行
     */
    private static Future<?> testAnyOf() {
        return CompletableFuture.anyOf(
                CompletableFuture.runAsync(
                        () -> {
                            System.out.println("1==========start");
                            try {
                                TimeUnit.SECONDS.sleep(5);
                                System.out.println("1==========End");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                ).whenComplete((v, t) -> System.out.println("-----------over-----------")),
                CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("2*************start");
                            try {
                                TimeUnit.SECONDS.sleep(3);
                                System.out.println("2*************End");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "Hello";
                        }
                ).whenComplete((v, t) -> System.out.println(v + "===========finished=========="))
        );
    }

    /**
     * 没有返回值，只关心是否执行完成
     */
    private static void testAllOf() {
        CompletableFuture.allOf(CompletableFuture.runAsync(
                () -> {
                    System.out.println("1==========start");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        System.out.println("1==========End");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ).whenComplete((v, t) -> System.out.println("-----------over-----------")),
                CompletableFuture.supplyAsync(
                        () -> {
                            System.out.println("2*************start");
                            try {
                                TimeUnit.SECONDS.sleep(3);
                                System.out.println("2*************End");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "Hello";
                        }
                ).whenComplete((v, t) -> System.out.println(v + "===========finished==========")));
    }
}
