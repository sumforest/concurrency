package com.sen.concurrency1.chapter7;



/**
 * @Author: Sen
 * @Date: 2019/12/6 19:24
 * @Description:
 */
public class BankRunnableSynchronized {
    private final static int MAX = 50;
    private static int index = 1;

    public static void main(String[] args) {
        TicketRunnableSychronized runnable = new TicketRunnableSychronized();
        Thread thread1 = new Thread(runnable, "一号窗口");
        //java8写法
        /*Thread thread1 = new Thread(()->{
            while (index <= MAX) {
                System.out.println("当前窗口：" + Thread.currentThread().getName() + index++);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "一号窗口");*/
        thread1.start();

        Thread thread2 = new Thread(runnable, "二号窗口");
       /* Thread thread2 = new Thread(()->{
            while (index <= MAX) {
                System.out.println("当前窗口：" + Thread.currentThread().getName() + index++);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "二号窗口");*/
        thread2.start();

        Thread thread3 = new Thread(runnable, "三号窗口");
        /*Thread thread3 = new Thread(()->{
                while (index <= MAX) {
                    System.out.println("当前窗口：" + Thread.currentThread().getName() + index++);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }, "三号窗口");*/
        thread3.start();
    }
}
