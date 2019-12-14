package com.sen.concurrency3.juc.atomic;

import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 22:44
 * @Description:
 */
public class AtomicBooleanTest {

    @Test
    public void testConstructorWithoutArgument() {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        boolean result = atomicBoolean.get();
        assertFalse(result);
    }

    @Test
    public void testConstructorWithArgument() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean result = atomicBoolean.get();
        assertTrue(result);
    }

    @Test
    public void testGetAndSet() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean result = atomicBoolean.getAndSet(false);
        assertTrue(result);
        assertFalse(atomicBoolean.get());
    }

    @Test
    public void testCompareAndSet() {
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        atomicBoolean.compareAndSet(false, true);
        assertTrue(atomicBoolean.get());
    }
}
