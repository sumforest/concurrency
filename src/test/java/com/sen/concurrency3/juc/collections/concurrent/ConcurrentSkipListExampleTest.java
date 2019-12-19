package com.sen.concurrency3.juc.collections.concurrent;

import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/20 02:15
 * @Description:
 */
public class ConcurrentSkipListExampleTest {

    /**
     * {@link ConcurrentSkipListMap#ceilingKey(Object)}拿着参数作为下标，如果不存在与改下标对应的值则向当前
     * 下标的右边取一个
     */
    @Test
    public void testCeiling() {
        ConcurrentSkipListMap<Integer, String> skipListMap = ConcurrentSkipListExample.create();

        skipListMap.put(1, "java");
        skipListMap.put(5, "scala");
        skipListMap.put(10, "python");

        assertThat(skipListMap.ceilingKey(2), equalTo(5));
        assertThat(skipListMap.ceilingEntry(2).getValue(), equalTo("scala"));
    }

    /**
     * {@link ConcurrentSkipListMap#ceilingKey(Object)}拿着参数作为下标，如果不存在与改下标对应的值则向当前
     * 下标的左边取一个
     */
    @Test
    public void testFloor() {
        ConcurrentSkipListMap<Integer, String> skipListMap = ConcurrentSkipListExample.create();

        skipListMap.put(1, "java");
        skipListMap.put(5, "scala");
        skipListMap.put(10, "python");

        assertThat(skipListMap.floorKey(2), equalTo(1));
        assertThat(skipListMap.floorEntry(2).getValue(), equalTo("java"));
    }

    @Test
    public void testMerge() {
        ConcurrentSkipListMap<Integer, String> skipListMap = ConcurrentSkipListExample.create();

        skipListMap.put(1, "java");
        skipListMap.put(5, "scala");
        skipListMap.put(10, "python");

        skipListMap.merge(1, " jvm", (ov, nv) -> {
            assertThat(ov, equalTo("java"));
            assertThat(nv, equalTo(" jvm"));
            return ov + nv;
        });

        assertThat(skipListMap.floorEntry(4).getValue(), equalTo("java jvm"));
    }

    @Test
    public void testCompete() {
        ConcurrentSkipListMap<Integer, String> skipListMap = ConcurrentSkipListExample.create();

        skipListMap.put(1, "java");
        skipListMap.put(5, "scala");
        skipListMap.put(10, "python");
        skipListMap.compute(5,(k,v)->{
            assertThat(k, equalTo(5));
            assertThat(v, equalTo("scala"));
            return k + v;
        });
        assertThat(skipListMap.get(5), equalTo("5scala"));
    }
}