package com.sen.concurrency3.juc.atomic;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Auther: Sen
 * @Date: 2019/12/14 22:56
 * @Description:
 */
public class AtomicBooleanFlag {

    public final static AtomicBoolean FLAG = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (FLAG.get()) {
                try {
                    Thread.sleep(1000);
                    System.out.println("I am working...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(5_000);
        FLAG.set(false);
    }
}
