package com.sen.concurrency1.chapter9;

import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/7 21:20
 * @Description: 生产者消费者问题if-else version:1.0 问题：多个生产者消费者造成的程序假死；重复生产
 * 因为notify()方法唤醒的线程是随机的
 * 程序执行结果如下：
 *
 * P->1
 * C->1
 * P->2
 *
 * c1唤醒的恰好就是p2那么p2生产完后就陷入了阻塞而p2生产完后唤醒的是消费者那么所有线程到进入waitSet，导致
 * 没有线程唤醒他们出现程序假死
 */
public class ProduceConsumeVersion1Problem {

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
        ProduceConsumeVersion1Problem pc = new ProduceConsumeVersion1Problem();
        Stream.of("P1","P2").forEach(n->{
            new Thread(()->{
                while (true) {
                    pc.produce();
                }
            }).start();
        });

        Stream.of("C1","C2").forEach(n->{
            new Thread(()->{
                while (true) {
                    pc.consume();
                }
            }).start();

        });
    }
}
