package com.sen.concurrency1.chapter5;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 02:22
 * @Description:
 */
public class ThreadJoin1 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
        });
        Thread t2 = new Thread(() -> {
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Optional.of("Everything is done").ifPresent(System.out::println);
        IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
    }
}
