package com.sen.concurrency3.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @Author: Sen
 * @Date: 2019/12/15 03:00
 * @Description:
 */
public class AtomicIntegerFieldUpdaterInnerTest {

    private volatile int i;

    /**
     * 当前类中维护多个引用类型时：如链表结构、树；使用AtomicIntegerFieldUpdater保证原子性节省一半空间
     */
    private AtomicInteger atomicInteger = new AtomicInteger();

    private static final AtomicIntegerFieldUpdater UPDATER = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdaterInnerTest.class, "i");

    public void update(int newValue) {
        UPDATER.compareAndSet(this, 0, newValue);
    }

    public int getValue() {
        return i;
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdaterInnerTest innerTest = new AtomicIntegerFieldUpdaterInnerTest();
        innerTest.update(100);
        System.out.println(innerTest.getValue());
    }
}
