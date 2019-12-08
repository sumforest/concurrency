package com.sen.concurrency1.chapter10;

import java.util.Collection;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 01:16
 * @Description: 自定义一个显式锁
 */
public interface Lock {

    //自定义等待锁超时异常
    class WaitLockTimeOutException extends Exception {
        public WaitLockTimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException,WaitLockTimeOutException;

    void unlock();

    Collection<Thread> getBlockThreads();

    int getBlockThreadsCount();
}
