package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/14 22:06
 * @Description:
 */
public class CompareAndSetTest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        boolean success = atomicInteger.compareAndSet(12, 20);
        System.out.println(success);
        System.out.println(atomicInteger.get());
    }
}
