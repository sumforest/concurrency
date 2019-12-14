package com.sen.concurrency3.juc.atomic;

import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @Auther: Sen
 * @Date: 2019/12/15 01:55
 * @Description:
 */
public class AtomicIntegerArrayTest {

    @Test
    public void testCreateAtomicIntegerArray() {
        AtomicIntegerArray array = new AtomicIntegerArray(10);
        int length = array.length();
        Assert.assertEquals(10, length);
    }
}
