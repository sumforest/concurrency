package com.sen.concurrency2.chapter4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sen
 * @Date: 2019/12/9 03:01
 * @Description: 被观察者
 */
public class Subject {
    private final List<Observer> observers = new ArrayList<>();

    private int status;

    public void setStatus(int status) {
        // 状态相等不改变当前状态
        if (status == this.status) {
            return;
        }
        this.status = status;
        // 更新状态后通知观察者
        notifyObservers();
    }


    /**
     * 添加观察者
     * @param observer 观察者
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 唤醒观察者
     */
    private void notifyObservers() {
        // 调用观察者的更行方法
        observers.forEach(Observer::update);
    }

    /**
     * 获取被观察者状态
     * @return status
     */
    public int getStatus() {
        return status;
    }
}
