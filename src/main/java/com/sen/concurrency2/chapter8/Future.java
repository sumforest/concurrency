package com.sen.concurrency2.chapter8;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 00:36
 * @Description:
 */
public interface Future<T> {

   T get();
}