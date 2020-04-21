package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 03:09
 * @Description: 二进制观察者
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    protected void update() {
        System.out.println("二进制：" + Integer.toBinaryString(subject.getStatus()));
    }
}
