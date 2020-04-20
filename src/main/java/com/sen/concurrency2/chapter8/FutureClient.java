package com.sen.concurrency2.chapter8;


/**
 * @Author: Sen
 * @Date: 2019/12/10 00:57
 * @Description: Future设计模式
 * Future        --> 未来的凭据
 * FutureTask        --> 执行的逻辑代码
 * FutureService --> 桥接Future 和 FutureTask
 */
public class FutureClient {

    public static void main(String[] args) throws InterruptedException {
        FutureService futureService = new FutureService();
        futureService.submit(() -> {
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISHED";
        },System.out::println);
        System.out.println("============");
        System.out.println("do other thing");
        System.out.println("============");
        Thread.sleep(1000);
        System.out.println("main close");
    }
}
