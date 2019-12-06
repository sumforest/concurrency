package com.sen.concurrency.chapter5;

import java.util.Optional;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 02:30
 * @Description:
 */
public class ThreadJoin2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "is started");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Optional.of(Thread.currentThread().getName() + "is end").ifPresent(System.out::println);
        });
        t1.start();
        t1.join(100,20);

        Optional.of("Everything is done").ifPresent(System.out::println);
    }
}
