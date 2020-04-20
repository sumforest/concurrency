package com.sen.concurrency1.chapter9;

/**
 * @Author: Sen
 * @Date: 2019/12/7 21:20
 * @Description: 单个生产者单个消费者模型 if-else 1.0版
 * 缺陷：多消费者下会出现程序假死，重复生产
 *
 * if (isProduce) {
 *     try {
 *          //多生产情况下，一个线程获取锁生产后isProduce=true；此时第二个线程获取锁后不判断isProduce，重复生产
 *          LOCK.wait();
 *     } catch (InterruptedException e) {
 *          e.printStackTrace();
 *     }
 * }
 */
public class ProduceConsumeVersion1 {

    private int i = 0;

    private volatile boolean isProduce = false;

    private final Object LOCK = new Object();

    public void produce(){
        synchronized (LOCK) {
            if (isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("P->" + ++i);
                isProduce = true;
                LOCK.notify();
            }
        }
    }

    public void consume(){
        synchronized (LOCK) {
            if (isProduce) {
                System.out.println("C->" + i);
                isProduce = false;
                LOCK.notify();
            } else {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProduceConsumeVersion1 pc = new ProduceConsumeVersion1();

        new Thread(()->{
            while (true) {
                pc.produce();
            }
        }).start();

        new Thread(()->{
            while (true) {
                pc.consume();
            }
        }).start();

    }
}
