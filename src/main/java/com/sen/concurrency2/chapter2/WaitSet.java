package com.sen.concurrency2.chapter2;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 23:24
 * @Description: 多线程的休息室
 */
public class WaitSet {

    private final static Object LOCK = new Object();

    private static void test() {
        System.out.println("thread come in lock");
        synchronized (LOCK) {
            System.out.println("thread begin to wait");
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread end to wait");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(WaitSet::test).start();
        Thread.sleep(3000);
        synchronized (LOCK) {
            LOCK.notify();
        }
    }
        /*IntStream.rangeClosed(1, 10).forEach(i->new Thread(String.valueOf(i)){
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println(Thread.currentThread().getName() + " come in wait set --");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " leaves wait set ==");
                }
            }
        }.start());

        Thread.sleep(1000);

        IntStream.rangeClosed(1, 10).forEach(i->{
            synchronized (LOCK) {
                LOCK.notify();
            }
        });
    */
}
