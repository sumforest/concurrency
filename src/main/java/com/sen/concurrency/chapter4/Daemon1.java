package com.sen.concurrency.chapter4;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 00:46
 * @Description:
 */
public class Daemon1 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " is running...");
                    Thread.sleep(10_000);
                    System.out.println(Thread.currentThread().getName() + " is ending!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + "->end++");
    }
}
