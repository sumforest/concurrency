package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 03:04
 * @Description: 观察者抽象类
 */
public abstract class Observer {
    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        subject.attach(this);
    }

    /**
     * 状态更新
     */
    protected abstract void update();
}
