package com.sen.concurrency2.chapter13;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 22:30
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        new ProduceThread(messageQueue).start();
        new ProduceThread(messageQueue).start();
        new ProduceThread(messageQueue).start();
        new ProduceThread(messageQueue).start();
        new ProduceThread(messageQueue).start();

        new ConsumeThread(messageQueue).start();
        new ConsumeThread(messageQueue).start();
    }
}