package com.sen.concurrency.chapter3;

import java.util.Arrays;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 22:21
 * @Description:
 */
public class CreateThread1 {

    public static void main(String[] args) {
        Thread thread = new Thread(()-> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        ThreadGroup threadGroup = thread.getThreadGroup();
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);

        Arrays.asList(threads).forEach(System.out::println);
    }
}
