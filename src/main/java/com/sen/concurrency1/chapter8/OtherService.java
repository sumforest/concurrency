package com.sen.concurrency1.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/7 19:02
 * @Description:
 */
public class OtherService {

    private final Object LOCK = new Object();

    private DeadLock deadLock;

    public void s1() {
        synchronized (LOCK){
            System.out.println("s1*********");
        }
    }

    public void s2(){
        synchronized (LOCK){
            System.out.println("s2*******");
            deadLock.m2();
        }
    }

    public void setDeadLock(DeadLock deadLock) {
        this.deadLock = deadLock;
    }
}
