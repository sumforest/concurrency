package com.sen.concurrency2.chapter18;

import sun.security.provider.Sun;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:20
 * @Description:
 */
public class FutureResult implements Result {

    private Result result;

    private volatile  boolean isReady = false;

    public synchronized void setResult(Result result) {
        this.isReady = true;
        this.result = result;
        this.notifyAll();
    }

    @Override
    public synchronized Object getResultValue() {
        while (!isReady) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result.getResultValue();
    }
}
