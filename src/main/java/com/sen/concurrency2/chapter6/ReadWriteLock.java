package com.sen.concurrency2.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/9 19:58
 * @Description: 读写锁，只有读操作并行，读写交替操作串行，多个写操作串行
 */
public class ReadWriteLock {

    /**
     * 读线程数量
     */
    private int readingReader = 0;

    /**
     * 等待读线程数
     */
    private int waitingRead = 0;

    /**
     * 写线程数
     */
    private int writingWriter = 0;

    /**
     * 等待写线程数
     */
    private int waitingWrite = 0;

    /**
     * true-->偏向读，默认true
     */
    private boolean preferWrite;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWrite) {
        this.preferWrite = preferWrite;
    }

    /**
     * 读锁--上锁
     */
    public synchronized void readLock() {
        this.waitingRead++;
        try {
            // 有线程正在执行写操作 || 等待写线程数 > 0 && 设置为写优先挂起申请读的线程
            while (writingWriter > 0 || waitingWrite > 0 && preferWrite) {
                this.wait();
            }
            this.readingReader++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 读锁获取成功，等待读的数量 -1
            this.waitingRead--;
        }
    }

    /**
     * 释放读锁
     */
    public synchronized void readUnlock() {
        this.readingReader--;
        this.notifyAll();
    }

    /**
     * 获取写锁
     */
    public synchronized void writeLock() {
        this.waitingWrite++;
        try {
            // 有线程在读 || 有线程在写 让获取写锁的线程等待
            while (readingReader > 0 || writingWriter > 0) {
                this.wait();
            }
            this.writingWriter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 获取写锁成功，等待写线程数-1
            this.waitingWrite--;
        }
    }

    /**
     * 释放写锁
     */
    public synchronized void writeUnlock() {
        this.writingWriter--;
        notifyAll();
    }
}
