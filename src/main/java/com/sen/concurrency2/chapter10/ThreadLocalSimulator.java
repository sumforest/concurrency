package com.sen.concurrency2.chapter10;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sen
 * @Date: 2019/12/10 18:05
 * @Description: key是当前线程，模拟 {@link ThreadLocal}
 */
public class ThreadLocalSimulator<T> {

    private Map<Thread,T> map = new HashMap<>();

    /**
     * 设置值，这里加锁的原因是因为，{@link HashMap} 不是一个线程安全的集合
     * @param t
     */
    public void set(T t) {
        synchronized (this){
            map.put(Thread.currentThread(), t);
        }
    }

    public T get(){
        synchronized (this){
            T t = map.get(Thread.currentThread());
            if (t == null) {
                return initValue();
            }
            return t;
        }
    }

    protected T initValue() {
        return null;
    }
}
