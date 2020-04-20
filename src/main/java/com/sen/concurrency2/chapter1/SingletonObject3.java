package com.sen.concurrency2.chapter1;

/**
 * @Author: Sen
 * @Date: 2019/12/8 22:18
 * @Description: 懒汉式，双重检查
 */
public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3() {

    }

    /**
     * 双重检查，实现懒加载，解决线程安全并且解决了每次获取instance都要串行操作
     * 注意：但是会有可能出现空指针
     * 原因：JVM回对代码进行重排序，在线程一和线程二同时进入到if (null == instance)前，线程一先获取锁在实例化
     * instance过程还没完成的时候cpu停止了其执行，切换到了线程二；此时线程二判断instance!=null直接返回，线程
     * 二得到是还没实例化完成的instance，在调用过程中会出现空指针异常
     * @return instance
     */
    public synchronized static SingletonObject3 getInstance() {
        if (null == instance) {
            synchronized (SingletonObject3.class) {
                if (null == instance) {
                    instance = new SingletonObject3();
                }
            }
        }
        return instance;
    }
}
