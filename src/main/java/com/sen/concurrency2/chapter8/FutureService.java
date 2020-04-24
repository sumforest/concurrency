package com.sen.concurrency2.chapter8;

import java.util.function.Consumer;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:44
 * @Description: Future设计模式核心
 */
public class FutureService {

    /**
     * 不消费结果，需要手动获取结果
     * @param task 执行逻辑
     * @param <T> 泛型
     * @return 票据--待兑现
     */
    public <T> Future<T> submit(FutureTask<T> task) {
        AsynchronousFuture<T> asynchronizedFutrue = new AsynchronousFuture<>();
        new Thread(()->{
            // 执行逻辑
            T result = task.call();
            // 执行结束通知Future
            asynchronizedFutrue.done(result);
        }).start();
        // 异步返回Future
        return asynchronizedFutrue;
    }

    /**
     * 得到结果直接消费，主动调用get方法获取
     * @param task 执行逻辑
     * @param consumer 消费结果
     * @param <T> 泛型
     * @return 票据--待兑现
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
