package com.sen.concurrency1.chapter6;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/7 03:16
 * @Description: 线程中断调用 {@code interrupt()},把 {@code isInterrupted()}标记为true，线程处于
 * {@code sleep()} 或者 {@code wait()} 时被中断，会抛出 {@link InterruptedException} 但是调用中
 * 断方法不是使工作线程停止,可使用try-catch块捕获异总之工作线程。
 */
public class ThreadInterrupter {

    private static final Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        System.out.println(">>" + this.isInterrupted());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.printf("I was Interrupted, status: " + this.isInterrupted());
            }
        };
        t1.start();
        System.out.println(t1.isInterrupted());
        Thread.sleep(200);
        t1.interrupt();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(t1.isInterrupted());

        /*-------------------------------------------------------------------------------------*/

        /*Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    synchronized (monitor) {
                        try {
                            monitor.wait(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("I was Interrupted");
                }
            }
        };
        t1.start();
        System.out.println(t1.isInterrupted());
        Thread.sleep(200);
        t1.interrupt();
        System.out.println(t1.isInterrupted());*/

        /*-------------------------------------------------------------------------------------*/

        /*Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("I am working");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread main = Thread.currentThread();
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(200);
                //中断main线程
                main.interrupt();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(main.isInterrupted());
        });

        t1.start();
        t2.start();
        try {
            //等待t1
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main:" + main.isInterrupted());
        System.out.println("主线程结束");*/
    }
}
