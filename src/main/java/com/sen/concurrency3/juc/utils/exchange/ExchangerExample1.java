package com.sen.concurrency3.juc.utils.exchange;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sen
 * @Date: 2019/12/16 00:30
 * @Description: exchange：
 * 1.当前线程已经运行到exchange（）是，另外一个线程还没准备好数据给当前线程；那么当前线程就会陷入阻塞状态，
 * 直到另外一个线程准备好数据
 * 2.exchanger只能在一对线程中使用，如果在多个线程中使用发送的消息会错乱；没有获取到数据的线程会一直陷入阻
 * 塞状态
 */
public class ExchangerExample1 {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + " come in");
                String receive = exchanger.exchange("I am come from T=A");
                System.out.println(Thread.currentThread().getName() + " :" + receive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"=A=").start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + " come in");
                TimeUnit.SECONDS.sleep(1);
                String receive = exchanger.exchange("I am come from T=B");
                System.out.println(Thread.currentThread().getName() + " :" + receive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"=B=").start();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + " come in");
                TimeUnit.SECONDS.sleep(1);
                String receive = exchanger.exchange("I am come from T=C");
                System.out.println(Thread.currentThread().getName() + " :" + receive);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"=C=").start();
    }
}
