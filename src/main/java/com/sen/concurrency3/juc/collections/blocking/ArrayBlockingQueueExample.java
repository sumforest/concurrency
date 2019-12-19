package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 15:04
 * @Description:
 */
public class ArrayBlockingQueueExample<T> {

    /**
     * 特点：1.有边界，大小固定；
     *       2.FIFO
     * @param capacity
     * @return
     */
    public ArrayBlockingQueue<T> create(int capacity){
        return new ArrayBlockingQueue<>(capacity);
    }
}
