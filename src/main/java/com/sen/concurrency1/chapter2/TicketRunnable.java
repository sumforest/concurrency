package com.sen.concurrency1.chapter2;

/**
 * @Author: Sen
 * @Date: 2019/12/6 19:21
 * @Description: 模拟票据窗口，实现{@link Runnable}重写实现{@code run()}创建线程
 */
public class TicketRunnable implements Runnable {

    private final static int MAX = 50;

    private static   int index = 1;

    @Override
    public void run() {
        while (index <= MAX) {
            System.out.println("当前窗口：" + Thread.currentThread().getName() + index++);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
