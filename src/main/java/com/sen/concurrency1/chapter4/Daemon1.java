package com.sen.concurrency1.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/7 00:46
 * @Description: 设置守护进程 {@code setDaemon(true)},当只有守护进程运行时JVM退出，程序结束
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
        //设置新建线程为守护线程
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        System.out.println(Thread.currentThread().getName() + "->end++");
    }
}
