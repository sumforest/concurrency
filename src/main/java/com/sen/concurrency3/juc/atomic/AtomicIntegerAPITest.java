package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 21:36
 * @Description:
 */
public class AtomicIntegerAPITest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        int i = atomicInteger.get();
        System.out.println(i);

        atomicInteger.set(10);
        System.out.println(atomicInteger.get());

        AtomicInteger atomicInteger2 = new AtomicInteger(10);
        System.out.println(atomicInteger2.get());
        atomicInteger2.getAndAdd(10);
        System.out.println(atomicInteger2.get());

        AtomicInteger atomicInteger3 = new AtomicInteger(1);
        new Thread(()->{
            for (int j = 0; j < 10; j++) {
                System.out.println(atomicInteger3.getAndAdd(1));
            }
        }).start();
        new Thread(()->{
            for (int j = 0; j < 10; j++) {
                System.out.println(atomicInteger3.getAndAdd(1));
            }
        }).start();
    }
}
