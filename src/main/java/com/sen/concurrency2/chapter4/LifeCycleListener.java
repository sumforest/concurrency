package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 17:47
 * @Description: 观察者接口
 */
public interface LifeCycleListener {

    /**
     * 响应观察者状态变更
     * @param event 生命周期事件
     */
    void onChange(ObserverRunnable.RunnableEvent event);
}
