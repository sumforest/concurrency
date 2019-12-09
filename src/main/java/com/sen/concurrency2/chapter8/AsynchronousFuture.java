package com.sen.concurrency2.chapter8;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 00:47
 * @Description:
 */
public class AsynchronousFuture<T> implements Future<T>{

    private volatile boolean done = false;

    private T result;

    public void done(T result) {
        synchronized (this) {
            this.result = result;
            this.done = true;
            this.notify();
        }
    }

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
