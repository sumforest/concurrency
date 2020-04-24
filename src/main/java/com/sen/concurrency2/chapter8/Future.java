package com.sen.concurrency2.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:36
 * @Description: Future接口
 */
public interface Future<T> {

   /**
    * 获取结果
    * @return T
    */
   T get();
}
