package com.sen.concurrency2.chapter4;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sen
 * @Date: 2019/12/9 03:01
 * @Description: 被观察者
 */
public class Subject {
    private List<Observer> observers = new ArrayList<>();

    private int status;

    public void setStatus(int status) {
        if (status == this.status)
            return;
        this.status = status;
        notifyObservers();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public int getStatus() {
        return status;
    }
}
