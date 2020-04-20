package com.sen.concurrency3.juc.utils.exchange;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Sen
 * @Date: 2019/12/16 00:57
 * @Description:
 */
public class ExchangerExample3 {

    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        new Thread(()->{
            AtomicInteger valueA = new AtomicInteger(1);
            while (true) {
                try {
                    valueA.set(exchanger.exchange(valueA.get()));
                    System.out.println(Thread.currentThread().getName()+" has value: "+valueA.get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"=A=").start();

        new Thread(()->{
            AtomicInteger valueB = new AtomicInteger(2);
            while (true) {
                try {
                    valueB.set(exchanger.exchange(valueB.get()));
                    System.out.println(Thread.currentThread().getName()+" has value: "+valueB.get());
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"=B=").start();
    }
}
