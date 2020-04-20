package com.sen.concurrency3.juc.collections.blocking;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author: Sen
 * @Date: 2019/12/19 17:40
 * @Description:
 */
public class SynchronousQueueExample {


    public <T> SynchronousQueue<T> create(){
        return new SynchronousQueue<>();
    }
}
