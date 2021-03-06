package com.sen.concurrency2.chapter3;

/**
 * @Author: Sen
 * @Date: 2019/12/8 23:57
 * @Description: 验证volatile不具备原子性
 * 输出结果：
 * T1-initValue-[46]
 * T2-initValue-[47]
 * T1-initValue-[47]
 * T2-initValue-[48]
 * T1-initValue-[49]
 * T1-initValue-[50]
 *
 * 出现连个线程读取到一样的值， T1 和 T2 同时读取到 46，同时进行 ++ 然后输出 47
 */
public class VolatileTest2 {

    private volatile static int initValue = 0;

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
