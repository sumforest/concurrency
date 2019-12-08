package com.sen.concurrency2.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 22:15
 * @Description:
 */
public class SingletonObject2 {

    private static SingletonObject2 instance;

    private SingletonObject2() {

    }

    /**
     * 解决了线程安全和懒加载，但是每次获取instance实例都要串行操作影响性能。
     * @return
     */
    public synchronized static SingletonObject2 getInstance() {
        if (null == instance)
            instance = new SingletonObject2();
        return instance;
    }
}
