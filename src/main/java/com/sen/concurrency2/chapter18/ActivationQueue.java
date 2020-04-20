package com.sen.concurrency2.chapter18;

import java.util.LinkedList;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:54
 * @Description:
 */
public class ActivationQueue {

    private final int DEFAULT_MAX_QUEUE_SIZE = 100;

    private final LinkedList<MethodResult> queue;

    public ActivationQueue() {
        this.queue = new LinkedList<>();
    }

    public synchronized void put(MethodResult methodResult) {
        while (queue.size() > DEFAULT_MAX_QUEUE_SIZE) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.addLast(methodResult);
        this.notifyAll();
    }

    public synchronized MethodResult take() {
        while (queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MethodResult methodResult = queue.removeFirst();
        this.notifyAll();
        return methodResult;
    }
}
