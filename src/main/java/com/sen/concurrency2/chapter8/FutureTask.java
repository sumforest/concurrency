package com.sen.concurrency2.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:37
 * @Description: 需要执行的任务
 */
public interface FutureTask<T> {

    /**
     * 执行任务
     * @return 结果
     */
    T call();
}
