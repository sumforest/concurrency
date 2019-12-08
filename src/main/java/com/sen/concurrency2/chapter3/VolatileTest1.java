package com.sen.concurrency2.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 23:57
 * @Description:
 */
public class VolatileTest1 {

    private /*volatile */static int initValue = 0;

    private final static int MAX_VALUE = 5;

    public static void main(String[] args) {
        //去掉volatile关键字后会进入死循环，而VolatileTest2不加volatile关键字不会进入死循环
        //因为当前代码块只有读操作，所以cpu没有如主内存更新值从开始到结束都是在寄存器里面拿数据
        new Thread(() -> {
            int currentValue = initValue;
            while (initValue < MAX_VALUE) {
                if (currentValue != initValue)
                    System.out.printf("The initValue update to [%d]\n",initValue);
                currentValue = initValue;
            }
        }, "READ").start();

        new Thread(()->{
            while (initValue < MAX_VALUE) {
                System.out.printf("Update the initValue to [%d]\n",++initValue);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"UPDATE").start();
    }
}
