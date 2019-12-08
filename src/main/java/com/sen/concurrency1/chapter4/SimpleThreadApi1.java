package com.sen.concurrency1.chapter4;

import java.util.Optional;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 01:54
 * @Description:
 */
public class SimpleThreadApi1 {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> Optional.of("hello").ifPresent(System.out::println));
        thread.start();

        Optional.of(thread.getName()).ifPresent(System.out::println);
        Optional.of(thread.getId()).ifPresent(System.out::println);
        Optional.of(thread.getPriority()).ifPresent(System.out::println);
    }
}
