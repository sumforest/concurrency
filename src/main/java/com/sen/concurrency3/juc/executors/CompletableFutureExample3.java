package com.sen.concurrency3.juc.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/18 18:39
 * @Description:
 */
public class CompletableFutureExample3 {

    public static void main(String[] args) throws InterruptedException {
        // testThenAcceptBoth();
        // testAcceptEither();
        // testRunAfterBoth();
        // testRunAfterEither();
        // testCombine();
        testThenCompose();
        Thread.currentThread().join();
    }

    /**
     * 后一个任务等待前一个任务的返回值作为入参，并返回新的Future
     */
    private static void testThenCompose() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Compose-1-start");
            sleepSeconds(2);
            System.out.println("Compose-1-end");
            return "Compose-1";
        }).thenCompose(i -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Compose-1-start");
            sleepSeconds(2);
            System.out.println("Compose-1-end");
            return 10 + i;
        })).whenComplete((r, e) -> System.out.println(r));
    }

    /**
     * 等待两个任务完成的结果，然后组合成新的Future并返回
     */
    private static void testCombine() {
        CompletableFuture.supplyAsync(()->{
            System.out.println("Combine-1-start");
            sleepSeconds(2);
            System.out.println("Combine-1-end");
            return "Combine-1";
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println("Combine-2-start");
            sleepSeconds(5);
            System.out.println("Combine-2-end");
            return 10;
        }),(s,i)->i-s.length()>0).whenComplete((result,e)->{
            System.out.println(result);
            System.out.println("=========over========");
        });
    }

    /**
     * 没有返回值，只关心两个任务中的其中一个任务是否执行完成，然后调用action
     */
    private static void testRunAfterEither() {
        CompletableFuture.runAsync(() -> {
            System.out.println("RunAfterEither-1-start");
            sleepSeconds(5);
            System.out.println("RunAfterEither-1-end");
        }).runAfterEither(CompletableFuture.runAsync(() -> {
            System.out.println("RunAfterEither-2-start");
            sleepSeconds(2);
            System.out.println("RunAfterEither-2-end");
        }), () -> System.out.println("Done"));
    }

    /**
     * 没有返回值，不关心返回值;两个任务是否完成，完成后调用action
     */
    private static void testRunAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("RunAfterBoth-1-start");
            sleepSeconds(5);
            System.out.println("RunAfterBoth-1-end");
            return "RunAfterBoth-1";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("RunAfterBoth-2-start");
            sleepSeconds(3);
            System.out.println("RunAfterBoth-2-end");
            return 100;
        }), () -> System.out.println("Done"));
    }

    /**
     * 返回先执行完成的结果，其他线程继续执行
     */
    private static void testAcceptEither() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("AcceptEither-1-start");
            sleepSeconds(3);
            System.out.println("AcceptEither-1-end");
            return "AcceptEither-1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            System.out.println("AcceptEither-2-start");
            sleepSeconds(5);
            System.out.println("AcceptEither-2-end");
            return "AcceptEither-2";
        }), System.out::println);
    }

    /**
     * 等待所有结果执行返程并返回
     */
    private static void testThenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("AcceptBoth-1-start");
            sleepSeconds(3);
            System.out.println("AcceptBoth-1-end");
            return "AcceptBoth-1";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("AcceptBoth-2-start");
            sleepSeconds(5);
            System.out.println("AcceptBoth-2-end");
            return 100;
        }), (s, t) -> System.out.println(s + "--" + t));
    }

    private static void sleepSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

