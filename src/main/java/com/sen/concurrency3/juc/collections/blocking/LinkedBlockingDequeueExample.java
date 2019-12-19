package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 21:36
 * @Description:
 */
public class LinkedBlockingDequeueExample {
    /**
     * 特点:
     * 1.可无界队列
     * 2.双端队列可以从头或者尾部插入或获取数据
     * @param <T>
     * @return
     */
    public static <T> LinkedBlockingDeque<T> create(){
        return new LinkedBlockingDeque<>();
    }
}
