package com.sen.concurrency3.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Author: Sen
 * @Date: 2019/12/15 02:27
 * @Description:
 */
public class AtomicIntegerFieldUpdaterTest {

    @Test(expected = RuntimeException.class)
    public void testPrivateFieldError() {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        int result = updater.getAndIncrement(testMe);
    }

    @Test(expected = ClassCastException.class)
    public void testTargetObjectIsNull() {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        updater.compareAndSet(null, 0, 10);
    }

    @Test(expected = RuntimeException.class)
    public void testFieldNameInvalid() {
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i1");
        TestMe testMe = new TestMe();
        updater.compareAndSet(testMe, 0, 10);
    }

    @Test(expected = ClassCastException.class)
    public void testFieldTypeInvalid() {
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(TestMe.class, Long.class,"i");
        TestMe testMe = new TestMe();
        updater.compareAndSet(testMe, 0, 10);
    }

    /**
     *  Must be volatile type
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFieldIsNotVolatile() {
        AtomicReferenceFieldUpdater updater = AtomicReferenceFieldUpdater.newUpdater(TestMe.class, Long.class,"i");
        TestMe testMe = new TestMe();
        updater.compareAndSet(testMe, 0L, 10L);
    }


    private static class TestMe{
         volatile Long i;
    }
}
