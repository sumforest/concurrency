package com.sen.concurrency1.chapter3;

import java.util.Arrays;

/**
 * @Author: Sen
 * @Date: 2019/12/6 22:21
 * @Description: 线程组
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
        //线程名   权重  线程组名
        //Thread[main,5,main]
        // Thread[Monitor Ctrl-Break,5,main]
        // Thread[Thread-0,5,main]
        Arrays.asList(threads).forEach(System.out::println);
    }
}
