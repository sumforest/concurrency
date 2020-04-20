package com.sen.concurrency2.chapter13;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:11
 * @Description:
 */
public class MessageQueue {

    private final LinkedList<Message> queue = new LinkedList<>();

    private final static int DEFAULT_MAX_SIZE = 100;


    public void put(Message message) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() > DEFAULT_MAX_SIZE) {
                queue.wait();
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }

    public Message take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            queue.notifyAll();
            return queue.removeFirst();
        }
    }
}
