package com.sen.concurrency3.juc.collections.blocking;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: Sen
 * @Date: 2019/12/19 16:21
 * @Description:
 */
public class PriorityBlockingQueueExample {

    /**
     * 特点:
     * 1.无边界；
     * 2.有优先级；
     * @param capacity
     * @param <T>
     * @return
     */
    public <T> PriorityBlockingQueue<T> create(int capacity){
        return new PriorityBlockingQueue<>(capacity);
    }

    public <T> PriorityBlockingQueue create(int capacity, Comparator<T> comparator){
        return new PriorityBlockingQueue<>(capacity,comparator);
    }
}
