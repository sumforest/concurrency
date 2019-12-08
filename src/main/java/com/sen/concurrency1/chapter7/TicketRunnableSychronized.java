package com.sen.concurrency1.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 19:21
 * @Description:
 */
public class TicketRunnableSychronized implements Runnable {

    private final static int MAX = 500;

    private int index = 1;

    //锁定的是this
    public void run() {
        while (true) {
            if (syn()) {
                break;
            }
        }
    }

    private synchronized boolean syn() {
        //get Filed
        if (index > MAX)
            return true;
        //get Filed index
        //index = index + 1;
        //put Filed index
        System.out.println("当前窗口：" + Thread.currentThread().getName() + index++);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
