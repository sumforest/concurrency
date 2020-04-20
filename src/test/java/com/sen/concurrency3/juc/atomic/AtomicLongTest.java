package com.sen.concurrency3.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.TestCase.assertEquals;
/**
 * @Author: Sen
 * @Date: 2019/12/14 23:17
 * @Description:
 */
public class AtomicLongTest {

    @Test
    public void createAtomicLongTest() {
        AtomicLong atomicLong = new AtomicLong(100);
        assertEquals(atomicLong.get(), 100);
    }
}
