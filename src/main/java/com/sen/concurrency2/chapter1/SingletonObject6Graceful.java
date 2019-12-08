package com.sen.concurrency2.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 22:52
 * @Description: 利用枚举实现优雅的单例模式
 */
public class SingletonObject6Graceful {

    private SingletonObject6Graceful() {

    }

    private enum Singleton {
        INSTANCE;
        private final SingletonObject6Graceful instance;

        Singleton() {
            instance = new SingletonObject6Graceful();
        }
    }

    public SingletonObject6Graceful getInstance() {
        return Singleton.INSTANCE.instance;
    }
}
