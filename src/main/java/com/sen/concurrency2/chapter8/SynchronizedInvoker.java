package com.sen.concurrency2.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:33
 * @Description: 同步阻塞调用
 */
public class SynchronizedInvoker {

    public static void main(String[] args) {
        String result = get();
        System.out.println(result);
    }

    private static String get() {
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "FINISHED";
    }
}
