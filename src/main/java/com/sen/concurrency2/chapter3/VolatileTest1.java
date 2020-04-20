package com.sen.concurrency2.chapter3;

/**
 * @Author: Sen
 * @Date: 2019/12/8 23:57
 * @Description: volatile关键字通过读写屏障使变量实现可见性，有序性；但是不备原子性
 * 输出结果：
 * Update the initValue to [1]
 * The initValue update to {1}
 * Update the initValue to [2]
 * Update the initValue to [3]
 * Update the initValue to [4]
 * Update the initValue to [5]
 *
 * 说明：Read线程读取，当执行 int currentValue = initValue时initValue=0，之后Update线程开始执行把initValue = 1;
 * 此时Read线程继续执行，输出：The initValue update to {1} 之后 initValue 的值CPU一直都从寄存器里面读取，没更新而进
 * 入死循环。Update线程一口气把 initValue 到 5 结束生命周期。
 */
public class VolatileTest1 {

    private /*volatile*/ static int initValue = 0;

    private final static int MAX_VALUE = 5;

    public static void main(String[] args) {
        //去掉volatile关键字后会进入死循环，而VolatileTest2不加volatile关键字不会进入死循环
        //因为当前代码块只有读操作，所以cpu没有从主内存更新值从开始到结束都是在寄存器里面拿数据
        new Thread(() -> {
            int currentValue = initValue;
            while (initValue < MAX_VALUE) {
                if (currentValue != initValue) {
                    System.out.printf("The initValue update to {%d}\n",initValue);
                }
                // 经行输出回使initValue的值更新
                // System.out.println("I am looping!!!");
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
