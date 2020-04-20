package com.sen.concurrency1.chapter9;

import java.util.stream.Stream;

/**
 * @Author: Sen
 * @Date: 2019/12/7 22:47
 * @Description: 区分 {@code sleep()} 与 {@code sleep()}
 * sleep：释放CPU时间片，不会释放监视器（锁）
 * wait: 释放CPU时间片，释放监视器（锁）
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
        // m2();
        // m1();

        //只有t1可执行
        /*Stream.of("T1","T2").forEach(name->new Thread(name){
            @Override
            public void run() {
                m1();
            }
        }.start());*/

        //t1、t2均可执行
       /* Stream.of("T1","T2").forEach(name->new Thread(name){
            @Override
            public void run() {
                m2();
            }
        }.start());*/

    }
}
