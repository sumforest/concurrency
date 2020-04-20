package com.sen.concurrency1.chapter12;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2019/12/8 15:23
 * @Description: 创建线程组
 */
public class CreateThreadGroup {

    public static void main(String[] args) {

        ThreadGroup tg1 = new ThreadGroup("tg1");
        Thread t1 = new Thread(tg1,"thread1"){
            @Override
            public void run() {
                try {
                   /* System.out.println(Thread.currentThread());
                    System.out.println(Thread.currentThread().getThreadGroup());
                    System.out.println(Thread.currentThread().getThreadGroup().getParent());*/
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup("tg2");
        Thread t2 = new Thread(tg2,"thread2"){
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread());
                    System.out.println(Thread.currentThread().getThreadGroup());
                    System.out.println(Thread.currentThread().getThreadGroup().getParent());
                    System.out.println("------------------");
                    Thread[] threads = new Thread[tg1.activeCount()];
                    tg1.enumerate(threads);
                    Arrays.asList(threads).forEach(System.out::println);
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
        // System.out.println(Thread.currentThread().getName());
        // System.out.println(Thread.currentThread().getThreadGroup().getName());
    }
}
