package com.sen.concurrency1.chapter8;

/**
 * @Author: Sen
 * @Date: 2019/12/7 19:11
 * @Description: 死锁测试，使用jconsole、jstack或者jvisualvm检测
 */
public class DeadLockTest {

    public static void main(String[] args) {
        OtherService otherService = new OtherService();
        DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);

        new Thread(()->{
            while (true) {
                deadLock.m1();
            }
        }).start();

        new Thread(()->{
            while (true) {
                otherService.s2();
            }
        }).start();
    }
}
