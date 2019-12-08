package com.sen.concurrency1.chapter9;

import java.util.stream.Stream;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 21:20
 * @Description: 多个生产者消费者造成的程序假死
 * 因为notify()方法唤醒的线程是随机的
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
                while (true)
                    pc.produce();
            }).start();
        });

        Stream.of("C1","C2").forEach(n->{
            new Thread(()->{
                while (true)
                    pc.consume();
            }).start();

        });
    }
}
