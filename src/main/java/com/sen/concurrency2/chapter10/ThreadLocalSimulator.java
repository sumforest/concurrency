package com.sen.concurrency2.chapter10;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sen
 * @Date: 2019/12/10 18:05
 * @Description: key是当前线程
 */
public class ThreadLocalSimulator<T> {

    private Map<Thread,T> map = new HashMap<>();

    public void set(T t) {
        synchronized (this){
            map.put(Thread.currentThread(), t);
        }
    }

    public T get(){
        synchronized (this){
            T t = map.get(Thread.currentThread());
            if (t == null)
                return initValue();
            return t;
        }
    }

    protected T initValue() {
        return null;
    }
}
