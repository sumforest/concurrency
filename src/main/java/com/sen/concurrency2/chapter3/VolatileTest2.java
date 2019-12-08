package com.sen.concurrency2.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 23:57
 * @Description:
 */
public class VolatileTest2 {

    private /*volatile */static int initValue = 0;

    private final static int MAX_VALUE = 50;

    public static void main(String[] args) {

        new Thread(() -> {
            while (initValue < MAX_VALUE) {
                    System.out.printf("T1-initValue-[%d]\n",++initValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "ADD-1").start();

        new Thread(()->{
            while (initValue < MAX_VALUE) {
                System.out.printf("T2-initValue-[%d]\n",++initValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"ADD-2").start();
    }
}
