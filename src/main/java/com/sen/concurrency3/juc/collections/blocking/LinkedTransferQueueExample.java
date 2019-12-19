package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.LinkedTransferQueue;

/**
 * @Auther: Sen
 * @Date: 2019/12/19 22:29
 * @Description:
 */
public class LinkedTransferQueueExample {

    /**
     * 特点：
     * 1.无边界的队列FIFo
     * 2.可以确保任务被执行
     * @param <T>
     * @return
     */
    public static <T> LinkedTransferQueue<T> create(){
        return new LinkedTransferQueue<>();
    }
}
