package com.sen.concurrency2.chapter1;

/**
 * @Author: Sen
 * @Date: 2019/12/8 22:48
 * @Description: 懒加载，线程安全，内部类
 */
public class SingletonObject5Graceful {

    private SingletonObject5Graceful() {

    }

    private static class SingletonHolder{
        private final static SingletonObject5Graceful INSTANCE = new SingletonObject5Graceful();
    }

    /**
     * 利用jvm类加载原理实现优雅的单例模式
     * @return 内部类在首次自动使用时才被JVM加载，JVM实现类加载过程的线程安全
     */
    public SingletonObject5Graceful getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
