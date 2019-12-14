package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 02:21
 * @Description: 给对象的属性实现原子性
 */
public class AtomicIntegerFieldUpdaterTest {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        new Thread(()->{
            final int MAX = 20;
            for (int i = 0; i < MAX; i++) {
                int result = updater.getAndIncrement(testMe);
                System.out.println(result);
            }
        }).start();
    }

    private static class TestMe{
        volatile int i;
    }
}
