package com.sen.concurrency2.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/9 19:58
 * @Description: 读写锁，只有读操作并行，读写交替操作串行，多个写操作串行
 */
public class ReadWriteLock {

    private int readingReader = 0;

    private int waitingRead = 0;

    private int writingWriter = 0;

    private int waitingWrite = 0;

    private boolean preferWrite;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean perferWrite) {
        this.preferWrite = perferWrite;
    }

    public synchronized void readLock() {
        this.waitingRead++;
        try {
            while (writingWriter > 0 || waitingWrite > 0 && preferWrite) {
                this.wait();
            }
            this.readingReader++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.waitingRead--;
        }
    }

    public synchronized void readUnlock() {
        this.readingReader--;
        this.notifyAll();
    }

    public synchronized void writeLock() {
        this.waitingWrite++;
        try {
            while (readingReader > 0 || writingWriter > 0) {
                this.wait();
            }
            this.writingWriter++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            this.waitingWrite--;
        }
    }

    public synchronized void writeUnlock() {
        this.writingWriter--;
        notifyAll();
    }
}
