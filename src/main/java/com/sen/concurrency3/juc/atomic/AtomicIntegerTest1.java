package com.sen.concurrency3.juc.atomic;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Sen
 * @Date: 2019/12/12 16:30
 * @Description:
 */
public class AtomicIntegerTest1 {

    private static volatile int value = 0;

    private static Set<Integer> set = new HashSet<>();

    private static AtomicInteger atomicValue = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        /*Thread t1 = new Thread(()->{
           int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + value);
                set.add(value);
                value++;
                n++;
            }
        });
        Thread t2 = new Thread(()->{
            int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + value);
                set.add(value);
                value++;
                n++;
            }
        });
        Thread t3 = new Thread(()->{
            int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + value);
                set.add(value);
                value++;
                n++;
            }
        });*/

        Thread t1 = new Thread(()->{
            int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + atomicValue.get());
                set.add(atomicValue.getAndIncrement());
                n++;
            }
        });

        Thread t2 = new Thread(()->{
            int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + atomicValue.get());
                set.add(atomicValue.getAndIncrement());
                n++;
            }
        });

        Thread t3 = new Thread(()->{
            int n = 0;
            while (n < 500) {
                System.out.println(Thread.currentThread().getName() + ":" + atomicValue.get());
                set.add(atomicValue.getAndIncrement());
                n++;
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();

        System.out.println(set.size());
    }
}
