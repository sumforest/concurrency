package com.sen.concurrency3.juc.utils.exchange;

import java.util.concurrent.Exchanger;

/**
 * @Auther: Sen
 * @Date: 2019/12/16 00:50
 * @Description: Exchanger两个线程间传递的是内存地址（同一个对象），一个线程对其修改会影响到另一个线程的值。
 */
public class ExchangerExample2 {

    public static void main(String[] args) {
        Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(()->{
            Object objA = new Object();
            try {
                System.out.println("objA:" + objA);
                System.out.println(Thread.currentThread().getName() + " will send objA");
                Object objB = exchanger.exchange(objA);
                System.out.println(Thread.currentThread().getName()+" receive objB: "+objB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"=A=").start();

        new Thread(()->{
            Object objB = new Object();
            try {
                System.out.println("objB:" + objB);
                System.out.println(Thread.currentThread().getName() + " will send objB");
                Object objA = exchanger.exchange(objB);
                System.out.println(Thread.currentThread().getName()+" receive objA: "+objA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"=B=").start();
    }
}
