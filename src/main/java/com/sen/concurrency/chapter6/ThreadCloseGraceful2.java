package com.sen.concurrency.chapter6;

import java.util.Optional;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 15:10
 * @Description: 停止线程的方法2
 * 调用interrupt方法
 */
public class ThreadCloseGraceful2 {

    private static class Worker extends Thread{
        @Override
        public void run() {
            while (true){
                Optional.of("Thread is working").ifPresent(System.out::println);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
                // if (this.isInterrupted()) {
                //     break;//return
                // }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread work = new Worker();
        work.start();
        Thread.sleep(5_000);
        work.interrupt();
    }
}
