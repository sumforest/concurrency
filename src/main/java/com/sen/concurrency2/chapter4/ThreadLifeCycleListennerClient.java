package com.sen.concurrency2.chapter4;

import java.util.Arrays;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 18:08
 * @Description:
 */
public class ThreadLifeCycleListennerClient {

    public static void main(String[] args) {
        new ThreadLifeCycleListener().checkLifeCycleStatus(Arrays.asList("t1", "t2", "t3"));
    }
}
