package com.sen.concurrency1.chapter7;

/**
 * @Author: Sen
 * @Date: 2019/12/7 18:36
 * @Description: this锁的测试
 */
public class SynchronizedClassLockTest {

    public static void main(String[] args) {
        new Thread(SynchronizedClassLock::m1).start();
        new Thread(SynchronizedClassLock::m2).start();
    }
}
