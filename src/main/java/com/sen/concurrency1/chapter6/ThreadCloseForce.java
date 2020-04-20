package com.sen.concurrency1.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/7 15:56
 * @Description: 强制中断线程
 */
public class ThreadCloseForce {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ThreadService threadService = new ThreadService();
        threadService.execute(() -> {
            while (true) {
                System.out.println("I am working...");
            }
        });
        threadService.shutdown(5000);
        long end = System.currentTimeMillis();
        System.out.printf("中断时间：%d", end - start);
    }
}
