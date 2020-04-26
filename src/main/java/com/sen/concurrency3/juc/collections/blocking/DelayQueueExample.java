package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/19 19:07
 * @Description:
 */
public class DelayQueueExample {
    /**
     * 特点:1.插入的元素是要实现{@link Delayed}接口并实现{@link Delayed#getDelay(TimeUnit)}
     * {@link Delayed#compareTo(Object)}方法
     *      2.是一个无界的，队列里面的值是按照延时时间按照一定规则排序的
     * @param <T>
     * @return
     */
    public static <T extends Delayed> DelayQueue<T> create(){
        return new DelayQueue<>();
    }
}
