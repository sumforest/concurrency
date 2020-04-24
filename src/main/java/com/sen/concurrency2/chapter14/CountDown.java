package com.sen.concurrency2.chapter14;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:59
 * @Description: 手动实现 {@link java.util.concurrent.CountDownLatch}
 */
public class CountDown {

    private int counter = 0;

    private final int latch;

    public CountDown(int latch) {
        this.latch = latch;
    }

    public void down() {
        synchronized (this) {
            this.counter++;
            this.notifyAll();
        }
    }

    public void await() {
        synchronized (this) {
            while (latch != counter) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
