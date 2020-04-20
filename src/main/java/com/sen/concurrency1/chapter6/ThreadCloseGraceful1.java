package com.sen.concurrency1.chapter6;

import java.util.Optional;

/**
 * @Author: Sen
 * @Date: 2019/12/7 15:10
 * @Description: 优雅停止线程的方法1
 * 标记方法
 */
public class ThreadCloseGraceful1 {

    private static class Worker extends Thread{

        private static volatile boolean start = true;

        @Override
        public void run() {
            while (start){
                Optional.of("Thread is working").ifPresent(System.out::println);
            }
        }

        private static void close(){
            start = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread work = new Worker();
        work.start();
        Thread.sleep(5_000);
        Worker.close();
    }
}
