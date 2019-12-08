package com.sen.concurrency1.chapter8;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 19:11
 * @Description:
 */
public class DeadLockTest {

    public static void main(String[] args) {
        OtherService otherService = new OtherService();
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);

        new Thread(()->{
            while (true)
                deadLock.m1();
        }).start();

        new Thread(()->{
            while (true)
                otherService.s2();
        }).start();
    }
}
