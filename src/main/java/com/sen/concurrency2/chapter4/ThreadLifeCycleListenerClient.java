package com.sen.concurrency2.chapter4;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2019/12/9 18:08
 * @Description: 多线程观察者模式--多线程线程生命周期
 */
public class ThreadLifeCycleListenerClient {

    public static void main(String[] args) {
        new ThreadLifeCycleListener().checkLifeCycleStatus(Arrays.asList("t1", "t2", "t3"));
    }
}
