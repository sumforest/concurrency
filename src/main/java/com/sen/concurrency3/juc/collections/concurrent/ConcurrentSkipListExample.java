package com.sen.concurrency3.juc.collections.concurrent;

import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @Author: Sen
 * @Date: 2019/12/20 02:13
 * @Description: 跳表
 */
public class ConcurrentSkipListExample {

    /**
     * 基于高并发的{@link TreeMap}的实现
     * 典型的以时间换空间的一种算法
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> ConcurrentSkipListMap<K,V> create(){
        return  new ConcurrentSkipListMap<>();
    }
}
