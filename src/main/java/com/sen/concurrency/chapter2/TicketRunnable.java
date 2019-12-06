package com.sen.concurrency.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 19:21
 * @Description:
 */
public class TicketRunnable implements Runnable {

    private final static int MAX = 50;

    private static   int index = 1;

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
