package com.sen.concurrency1.chapter9;

import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/7 21:20
 * @Description: 多生产者单多消费者模型，notifyAll(),while判断解决假死、重复生产
 */
public class ProduceConsumeVersion2 {

    private int i = 0;

    private volatile boolean isProduce = false;

    private final Object LOCK = new Object();

    public void produce() {

        synchronized (LOCK) {
            //用if判断会出现生产多次消费一次
            while (isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("P->" + ++i);
            isProduce = true;
            LOCK.notifyAll();
        }
    }

    public void consume() {

        synchronized (LOCK) {
            //用if判断会出现生产一次消费多次
            while (!isProduce) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C->" + i);
            isProduce = false;
            LOCK.notifyAll();
        }
    }

    public static void main(String[] args) {
        ProduceConsumeVersion2 pc = new ProduceConsumeVersion2();
        Stream.of("P1", "P2", "P3").forEach(n->{
            new Thread(() -> {
                while (true) {
                    pc.produce();
                }
            }).start();
        });

        Stream.of("C1", "C2", "C3", "C4").forEach(n->{
            new Thread(() -> {
                while (true) {
                    pc.consume();
                }
            }).start();
        });
    }
}
