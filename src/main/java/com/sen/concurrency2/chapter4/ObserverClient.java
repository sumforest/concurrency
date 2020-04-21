package com.sen.concurrency2.chapter4;


/**
 * @Author: Sen
 * @Date: 2019/12/9 03:11
 * @Description: 观察者设计模式
 */
public class ObserverClient {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        subject.setStatus(10);
        System.out.println("-------------------------");
        subject.setStatus(10);
        System.out.println("-------------------------");
        subject.setStatus(20);
    }
}
