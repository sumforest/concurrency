package com.sen.concurrency2.chapter15;

import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/10 23:28
 * @Description:
 */
public class ThreadPreMessage {

    public static void main(String[] args) {
        MessageHandler messageHandler = new MessageHandler();
        IntStream.rangeClosed(1, 10)
                .forEach(i -> messageHandler.request(new Message(String.valueOf(i))));

        messageHandler.shutdown();
    }
}
