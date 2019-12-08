package com.sen.concurrency2.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 22:48
 * @Description:
 */
public class SingletonObject5Graceful {

    private SingletonObject5Graceful() {

    }

    private static class SingletonHolder{
        private final static SingletonObject5Graceful instance = new SingletonObject5Graceful();
    }

    /**
     * 利用jvm类加载原理实现优雅的单例模式
     * @return
     */
    public SingletonObject5Graceful getInstance() {
        return SingletonHolder.instance;
    }
}
