package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: Sen
 * @Date: 2019/12/19 17:10
 * @Description:
 */
public class LinkedBlockingQueueExample {

    /**
     * {@link LinkedBlockingQueue} 可选边界阻塞队列，初始化是不指定边界默认是{@link Integer#MAX_VALUE}大小，
     * 指定边界时就是自定的大小
     * @param <T>
     * @return
     */
    public <T> LinkedBlockingQueue<T> create() {
        return new LinkedBlockingQueue<>();
    }

    public <T> LinkedBlockingQueue<T> create(int capacity) {
        return new LinkedBlockingQueue<>(capacity);
    }
}
