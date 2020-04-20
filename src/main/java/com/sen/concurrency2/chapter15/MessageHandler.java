package com.sen.concurrency2.chapter15;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Sen
 * @Date: 2019/12/10 23:23
 * @Description:
 */
public class MessageHandler {

    private final Executor executor = Executors.newFixedThreadPool(5);

    private final Random random = new Random(System.currentTimeMillis());

    public void request(Message message) {
        executor.execute(()->{
            String value = message.getValue();
            System.out.println(Thread.currentThread().getName() + " deal with request get message " + value);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

       /* new Thread(()->{
            String value = message.getValue();
            System.out.println(Thread.currentThread().getName() + " deal with request get message " + value);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    public void shutdown() {
        ((ExecutorService)executor).shutdown();
    }
}
