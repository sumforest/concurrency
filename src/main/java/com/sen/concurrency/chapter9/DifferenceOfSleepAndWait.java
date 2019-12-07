package com.sen.concurrency.chapter9;

import java.util.stream.Stream;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 22:47
 * @Description:
 */
public class DifferenceOfSleepAndWait {

    private final static Object LOCK = new Object();

    public static void m1() {
        synchronized (LOCK) {
            System.out.println(Thread.currentThread().getName() + "--enter");
            try {
                Thread.sleep(5_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2(){
        synchronized (LOCK) {
            System.out.println(Thread.currentThread().getName() + "--enter");
            try {
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // m1();
        // m2();
     /*   Stream.of("T1","T2").forEach(name->new Thread(name){
            @Override
            public void run() {
                m1();
            }
        }.start());*/

        Stream.of("T1","T2").forEach(name->new Thread(name){
            @Override
            public void run() {
                m2();
            }
        }.start());

    }
}
