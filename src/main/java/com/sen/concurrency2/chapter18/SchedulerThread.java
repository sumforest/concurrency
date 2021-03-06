package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:25
 * @Description: 计划线程
 */
public class SchedulerThread extends Thread {

    private final ActivationQueue queue;

    public SchedulerThread(ActivationQueue queue) {
        this.queue = queue;
    }

    public void invoke(MethodResult methodResult) {
        queue.put(methodResult);
    }

    @Override
    public void run() {
        while (true) {
            queue.take().execute();
        }
    }
}
