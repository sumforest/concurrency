package com.sen.concurrency2.chapter13;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:29
 * @Description: 消费者
 */
public class ConsumeThread extends Thread {
    private final MessageQueue messageQueue;

    private final Random random;

    public ConsumeThread(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = messageQueue.take();
                System.out.println(Thread.currentThread().getName() + " ** Consume " + message.getData());
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
