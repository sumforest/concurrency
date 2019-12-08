package com.sen.concurrency2.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 22:13
 * @Description: 饿汉式
 */
public class SingletonObject1 {

    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1() {

    }

    public static SingletonObject1 getInstance() {
        return instance;
    }
}
