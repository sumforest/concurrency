package com.sen.concurrency1.chapter5;

import java.util.Optional;

/**
 * @Author: Sen
 * @Date: 2019/12/7 02:30
 * @Description: {@link Thread} {@code t1.join(100,20)}设置等待的时间=100ms+20ns，超过设置的等待时间后还么完成
 * 那么父线程不再等待继续往下执行。
 */
public class ThreadJoin2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "is started");
            try {
                Thread.sleep(8_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Optional.of(Thread.currentThread().getName() + "is end").ifPresent(System.out::println);
        });
        t1.start();
        t1.join(1,20);

        Optional.of("Everything is done").ifPresent(System.out::println);
    }
}
