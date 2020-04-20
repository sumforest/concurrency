package com.sen.concurrency2.chapter13;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:20
 * @Description:
 */
public class ProduceThread extends Thread {

    private final MessageQueue messageQueue;

    private final Random random;

    private final static AtomicInteger count = new AtomicInteger(0);

    public ProduceThread(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = new Message("Message--" + count.getAndIncrement());
                messageQueue.put(message);
                System.out.println(Thread.currentThread().getName() + " == Produce " + message.getData());
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
