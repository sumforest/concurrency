package com.sen.concurrency2.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 22:18
 * @Description:
 */
public class SingletonObject4 {

    //加入volatile关键字避免重排序，保证读操作之前写操作一定完成
    private volatile static SingletonObject4 instance;

    private SingletonObject4() {

    }

    /**
     * 双重检查，实现懒加载，解决线程安全并且解决了每次获取instance都要串行操作
     * 不足:加入volatile关键字解决了重排序，保证读操作之前写操作一定完成带来可能的空指针异常，但是jvm的重排序就是
     * 为了提高性能，volatile禁止重排序显得不够优雅。
     * @return
     */
    public synchronized static SingletonObject4 getInstance() {
        if (null == instance)
            synchronized (SingletonObject4.class) {
                if (null == instance)
                instance = new SingletonObject4();
            }
        return instance;
    }
}
