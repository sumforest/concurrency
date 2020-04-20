package com.sen.concurrency1.chapter11;

/**
 * @Author: Sen
 * @Date: 2019/12/8 14:47
 * @Description: 外部捕获线程运行时异常
 */
public class ThreadCatchException {

    private final static int A = 10;

    private final static int B = 0;

    public static void main(String[] args) {

        // new Test1().test();

        Thread t = new Thread(()->{
            // try {
                int c = A / B;
            // } catch (Exception e) {
            //     // e.printStackTrace();
            // }
        });
        t.start();

        t.setUncaughtExceptionHandler((thread,e)->{
            System.out.println(e);
            System.out.println(thread);
        });
    }
}
