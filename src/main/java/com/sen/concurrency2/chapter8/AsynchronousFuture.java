package com.sen.concurrency2.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/10 00:47
 * @Description: Future设计模式--异步调用
 */
public class AsynchronousFuture<T> implements Future<T>{

    /**
     * 任务完成标记，true-->已完成，默认false
     */
    private volatile boolean done = false;

    private T result;

    /**
     * 执行完成回调，设置结果
     * @param result 结果
     */
    public void done(T result) {
        synchronized (this) {
            this.result = result;
            this.done = true;
            this.notify();
        }
    }

    /**
     * 同步且阻塞等待结果
     * @return T
     */
    @Override
    public T get() {
        synchronized (this) {
            while (!done) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}
