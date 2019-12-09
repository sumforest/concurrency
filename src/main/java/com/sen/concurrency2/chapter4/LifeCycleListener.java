package com.sen.concurrency2.chapter4;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 17:47
 * @Description: 观察者接口
 */
public interface LifeCycleListener {
    void onChange(ObserverRunnable.RunnableEvent event);
}
