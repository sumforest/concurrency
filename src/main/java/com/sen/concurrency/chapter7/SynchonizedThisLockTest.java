package com.sen.concurrency.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 18:36
 * @Description: this锁的测试
 */
public class SynchonizedThisLockTest {

    public static void main(String[] args) {
        SynchronizedThisLock thisLock = new SynchronizedThisLock();
        new Thread(thisLock::m1).start();
        new Thread(thisLock::m2).start();
    }
}
