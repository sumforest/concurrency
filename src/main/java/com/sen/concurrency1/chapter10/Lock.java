package com.sen.concurrency1.chapter10;

import java.util.Collection;

/**
 * @Author: Sen
 * @Date: 2019/12/8 01:16
 * @Description: 自定义一个显式锁
 */
public interface Lock {

    /**
     * 自定义等待锁超时异常
     */
    class WaitLockTimeOutException extends Exception {
        public WaitLockTimeOutException(String message) {
            super(message);
        }
    }

    /**
     * 获取锁
     * @throws InterruptedException 获取锁被打断
     */
    void lock() throws InterruptedException;

    /**
     * 指定时间内获取锁
     * @param mills 超时时间
     * @throws InterruptedException  被打断
     * @throws WaitLockTimeOutException 超时
     */
    void lock(long mills) throws InterruptedException,WaitLockTimeOutException;

    /**
     * 释放锁
     */
    void unlock();

    /**
     * 获取阻塞线程集合
     * @return 获取锁过程中被阻塞的线程
     */
    Collection<Thread> getBlockThreads();

    /**
     * 获取抢占被阻塞的线程数量
     * @return 数量
     */
    int getBlockThreadsCount();
}
