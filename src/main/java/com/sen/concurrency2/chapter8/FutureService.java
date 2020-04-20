package com.sen.concurrency2.chapter8;

import java.util.function.Consumer;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:44
 * @Description: Future设计模式核心
 */
public class FutureService {

    public <T> Future<T> submit(FutureTask<T> task) {
        AsynchronousFuture<T> asynchronizedFutrue = new AsynchronousFuture<>();
        new Thread(()->{
            T result = task.call();
            asynchronizedFutrue.done(result);
        }).start();
        return asynchronizedFutrue;
    }

    /**
     * 得到结果直接消费，主动调用get方法获取
     * @param task
     * @param consumer 消费结果
     * @param <T>
     * @return
     */
    public <T> Future<T> submit(FutureTask<T> task, Consumer<T> consumer) {
        AsynchronousFuture<T> asynchronizedFutrue = new AsynchronousFuture<>();
        new Thread(()->{
            T result = task.call();
            asynchronizedFutrue.done(result);
            consumer.accept(result);
        }).start();
        return asynchronizedFutrue;
    }
}
