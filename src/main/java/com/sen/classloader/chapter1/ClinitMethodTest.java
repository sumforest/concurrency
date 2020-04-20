package com.sen.classloader.chapter1;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Sen
 * @Date: 2019/12/11 20:07
 * @Description: 验证<clinit>方法是由JVM保证线程安全的
 */
public class ClinitMethodTest {

    public static void main(String[] args) {
        new Thread(()->new ClinitMethodTest.Singleton()).start();
        new Thread(()->new ClinitMethodTest.Singleton()).start();
    }

    private static class Singleton{

        private static AtomicBoolean flag = new AtomicBoolean(true);

        static {
            System.out.println(Thread.currentThread().getName() + " come in <clinit>");
            while (flag.get()) {

            }
            System.out.println(Thread.currentThread().getName() + " Finished <clinit>");
        }
    }
}
